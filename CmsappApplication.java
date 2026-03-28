package com.cms.cmsapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.cms.cmsapp.application.config.BannerConfig;

@SpringBootApplication
@EnableScheduling
public class CmsappApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(CmsappApplication.class);
        app.setBanner(new BannerConfig());
        app.run(args);
	}

}
