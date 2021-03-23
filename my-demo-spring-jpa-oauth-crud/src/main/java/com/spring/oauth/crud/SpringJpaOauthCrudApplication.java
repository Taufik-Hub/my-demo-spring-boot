package com.spring.oauth.crud;

import java.util.Objects;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.spring.oauth.crud.entity.User;


@SpringBootApplication
@EnableJpaRepositories
@EnableJpaAuditing
public class SpringJpaOauthCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringJpaOauthCrudApplication.class, args);
	}
	
	@Bean
	public AuditorAware<String> auditorAware() {
		return new AuditorAware<String>() {

			@Override
			public Optional<String> getCurrentAuditor() {
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

				if (Objects.isNull(authentication) || ! authentication.isAuthenticated()) {
					return null;
				}
				String username = ((UserDetails) authentication.getPrincipal()).getUsername();
				return Optional.ofNullable(username);
			}
		};
    }

}
