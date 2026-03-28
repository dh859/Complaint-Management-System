package com.cms.cmsapp.complaint.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.cms.cmsapp.common.Enums.Category;
import com.cms.cmsapp.common.Enums.Priority;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreateComplaintDto {

    @NotBlank
    private String subject;

    @NotBlank
    private String description;

    @NotNull
    private Category category;

    private Priority priority;

    private List<MultipartFile> files;

    public CreateComplaintDto(String subject,String description,String category){
        this.subject=subject;
        this.description=description;
        this.category=Category.fromString(category);
        this.files=null;
    }
}

