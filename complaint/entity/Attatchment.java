package com.cms.cmsapp.complaint.entity;

import java.time.LocalDateTime;

import com.cms.cmsapp.file.FileDto;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "queryfiles")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Attatchment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long fileId;

	private String fileName;
	private String fileType;
	private String filePath;
	private Long fileSize;

	private LocalDateTime uploadedAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "complaint_id")
	private Complaint complaint;

	public Attatchment(String fileName, String fileType, String filePath, Long fileSize) {
		this.fileName = fileName;
		this.fileType = fileType;
		this.filePath = filePath;
		this.fileSize = fileSize;
		this.uploadedAt = LocalDateTime.now();
	}

	public FileDto toFileDto() {
		FileDto fileDto = new FileDto();
		fileDto.setFileId(fileId);
		fileDto.setName(fileName);
		fileDto.setType(fileType);
		fileDto.setSize(fileSize);
		return fileDto;
	}

}
