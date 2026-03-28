package com.cms.cmsapp.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
@Builder
public class AuthResponseDto {
    private String accessToken;
    private String refreshToken;
}
