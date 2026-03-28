package com.cms.cmsapp.file;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FileDto {
    
    private Long fileId;
    private String name;
    private String type;
    private Long size;

    public FileDto(String name, String url, String type, Long size) {
        this.name = name;
        this.type = type;
        this.size = size;
    }

}
