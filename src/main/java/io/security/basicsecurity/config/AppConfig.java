package io.security.basicsecurity.config;

import io.security.basicsecurity.repository.ResourcesRepository;
import io.security.basicsecurity.service.SecurityResourceService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class AppConfig {
    @Bean
    public SecurityResourceService securityResourceService(ResourcesRepository resourcesRepository){
        SecurityResourceService securityResourceService = new SecurityResourceService(resourcesRepository);
        return securityResourceService;
    }
}
