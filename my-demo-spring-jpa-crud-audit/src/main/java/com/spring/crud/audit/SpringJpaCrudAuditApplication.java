package com.spring.crud.audit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.spring.crud.audit.config.AuditorAwareImpl;

@SpringBootApplication
@EnableJpaRepositories
@EnableJpaAuditing
public class SpringJpaCrudAuditApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringJpaCrudAuditApplication.class, args);
	}
	
	@Bean
    public AuditorAware<String> auditorAware() {
        return new AuditorAwareImpl();
    }

}
