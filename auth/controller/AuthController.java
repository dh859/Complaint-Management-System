package com.cms.cmsapp.auth.controller;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cms.cmsapp.application.Security.Utils.JwtUtils;
import com.cms.cmsapp.application.Security.Utils.UserDetailsDto;
import com.cms.cmsapp.auth.dto.LoginRequest;
import com.cms.cmsapp.auth.dto.RegisterRequest;
import com.cms.cmsapp.auth.dto.TokenRefreshRequest;
import com.cms.cmsapp.auth.dto.TokenResponse;
import com.cms.cmsapp.auth.entity.RefreshToken;
import com.cms.cmsapp.auth.service.RefreshTokenService;
import com.cms.cmsapp.common.Enums.EmailType;
import com.cms.cmsapp.common.Enums.Role;
import com.cms.cmsapp.common.exceptions.InvalidOperationException;
import com.cms.cmsapp.common.exceptions.TokenRefreshException;
import com.cms.cmsapp.notification.aop.SendMail;
import com.cms.cmsapp.user.dto.UserDto;
import com.cms.cmsapp.user.entity.User;
import com.cms.cmsapp.user.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UserService userService;

	@Autowired
	private RefreshTokenService refreshTokenService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtils jwtUtils;

	@Operation(summary = "Authenticate a user", description = "Authenticates a user with username and password.")
	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							loginRequest.getUsername(),
							loginRequest.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);

			String jwt = jwtUtils.generateJwtToken(authentication);

			User user = userService.getByUsername(loginRequest.getUsername());

			RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getUserId());

			return ResponseEntity.ok(new TokenResponse(jwt, refreshToken.getToken()));
		} catch (AuthenticationException ex) {
			return ResponseEntity
					.status(HttpStatus.UNAUTHORIZED)
					.body(Map.of("error", "Invalid username or password"));
		}
	}

	@Operation(summary = "Logout user", description = "Invalidates session if any. Stateless logout for JWT.")
	@ApiResponse(responseCode = "200", description = "User logged out successfully")
	@GetMapping("/logout")
	public ResponseEntity<String> logout(HttpServletRequest req) {
		req.getSession().invalidate();
		return ResponseEntity.ok("Logged out successfully");
	}

	@Operation(summary = "Register a new user", description = "Registers a user with username, email, and password.")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "User registered successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid input data")
	})
	@PostMapping("/register")
	@SendMail(type = EmailType.WELCOME_EMAIL)
	public User register(@RequestBody RegisterRequest req) {
		if (req.getPassword() == null || req.getPassword().isBlank()) {
			throw new IllegalArgumentException("Password cannot be null or empty");
		}

		User newUser = User.builder()
				.username(req.getUsername())
				.fullname(req.getFullname())
				.email(req.getEmail())
				.password(passwordEncoder.encode(req.getPassword()))
				.role(Role.fromString("user"))
				.isActive(true)
				.createdAt(LocalDateTime.now())
				.build();

		return userService.createUser(newUser);
	}

	@PostMapping("/refresh-token")
	public ResponseEntity<?> refreshToken(@RequestBody TokenRefreshRequest request) {
		String requestRefreshToken = request.getRefreshToken();

		return refreshTokenService.findByToken(requestRefreshToken)
				.map(refreshTokenService::verifyExpiration)
				.map(RefreshToken::getUser)
				.map(user -> {
					String token = jwtUtils.generateJwtToken(user);
					return ResponseEntity.ok(new TokenResponse(token, requestRefreshToken));
				})
				.orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
						"Refresh token not found"));
	}

	@PostMapping("/validate-token")
	public ResponseEntity<?> validateToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			String token = authHeader.substring(7);
			if (jwtUtils.validateToken(token)) {
				return ResponseEntity.ok(Map.of("valid", true));
			}
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("valid", false));
	}

	@GetMapping("/me")
	public UserDto getUser(@AuthenticationPrincipal UserDetailsDto userDetailDto) {
		return userService.getByUsername(userDetailDto.getUsername()).toUserDto();
	}

	@PutMapping("/update")
	public UserDto update(@RequestBody UserDto userDto, @AuthenticationPrincipal UserDetailsDto userDetailsDto) {
		User user = userService.getByUsername(userDetailsDto.getUsername());
		user.setEmail(userDto.getEmail());
		user.setFullname(userDto.getFullname());
		user.setAddress(userDto.getAddress());
		user.setContactNumber(userDto.getContactNumber());
		user.setState(userDto.getState());
		user.setCity(userDto.getCity());
		user.setPincode(userDto.getPincode());
		return userService.update(user);
	}

	@PutMapping("/changePassword")
	public String changePassword(@RequestParam String oldPassword, @RequestParam String newPassword,
			@AuthenticationPrincipal UserDetailsDto userDetailsDto) {
		if (newPassword.isBlank()) {
			throw new InvalidOperationException("Password cannot be null or empty");
		} else if (passwordEncoder.encode(newPassword).equals(userDetailsDto.getPassword())) {
			throw new InvalidOperationException("New Password cannot be same as Old Password");
		} else {
			if (userService.validateUser(userDetailsDto.getUsername(), oldPassword) != null) {
				User user = userService.getByUsername(userDetailsDto.getUsername());
				user.setPassword(passwordEncoder.encode(newPassword));
				return "Password updated Succesfully for User:"
						+ userService.update(user).getUsername();
			}
			return "Failed to change Password";
		}
	}

}
