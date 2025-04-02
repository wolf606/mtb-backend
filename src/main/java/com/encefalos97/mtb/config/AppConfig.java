package com.encefalos97.mtb.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppConfig {
    @Value("${mtb.adminPassword}")
    private String adminPassword;

    public String getAdminPassword() {
        return adminPassword;
    }
}
