package com.cms.cmsapp.file;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cms.cmsapp.application.Security.Utils.UserDetailsDto;
import com.cms.cmsapp.complaint.entity.Attatchment;


@RestController
@RequestMapping("/files")
@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER','ROLE_MANAGER','ROLE_AGENT')")
public class FileController {

	@Autowired
	private FileService fileService;

	@GetMapping("/{fileId}/content")
	public ResponseEntity<byte[]> getFile(@AuthenticationPrincipal UserDetailsDto user, @PathVariable Long fileId) {
		Attatchment queryFile = fileService.getFileById(fileId);
		File file = fileService.getFile(queryFile.getFilePath());
		byte[] fileContent = fileService.readFileBytes(file);

		if (checkUser(user, queryFile)) {
			if (queryFile.getFileType().startsWith("image/")) {
				return ResponseEntity.ok()
						.header(HttpHeaders.CONTENT_DISPOSITION,
								"inline; filename=\"" + queryFile.getFileName() + "\"")
						.contentType(MediaType.parseMediaType(queryFile.getFileType()))
						.body(fileContent);
			} else {
				return ResponseEntity.ok()
						.header(HttpHeaders.CONTENT_DISPOSITION,
								"attachment; filename=\"" + queryFile.getFileName()
										+ "\"")
						.contentType(MediaType.parseMediaType(queryFile.getFileType()))
						.body(fileContent);
			}
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
	}

	private boolean checkUser(UserDetailsDto user, Attatchment file) {
		if (user == null || file == null || file.getComplaint() == null) {
			return false;
		}

		String role = user.getRole();
		Long userId = user.getUserId();

		return switch (role) {
			case "ROLE_ADMIN", "ROLE_MANAGER" -> true;

			case "ROLE_AGENT" ->
				file.getComplaint().getAssignedToUser() != null &&
						file.getComplaint().getAssignedToUser().getUserId().equals(userId);

			case "ROLE_USER" ->
				file.getComplaint().getRaisedByUser() != null &&
						file.getComplaint().getRaisedByUser().getUserId().equals(userId);

			default -> false;
		};
	}
}
