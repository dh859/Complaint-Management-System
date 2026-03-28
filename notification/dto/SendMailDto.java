package com.cms.cmsapp.notification.dto;

import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendMailDto {

    @NotEmpty
    private List<@Email String> to;

    @NotBlank
    private String subject;

    @NotBlank
    private String message;

    @Builder.Default
    private String title = "Notification";

}
