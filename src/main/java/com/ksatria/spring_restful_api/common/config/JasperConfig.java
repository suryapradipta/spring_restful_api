package com.ksatria.spring_restful_api.common.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class JasperConfig {

    @Value("${jasper.file.path}")
    private String jasperFilePath;

}
