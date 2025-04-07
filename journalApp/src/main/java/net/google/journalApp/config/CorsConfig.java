package net.google.journalApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

	   @Bean
	    public CorsFilter corsFilter() {
	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        CorsConfiguration config = new CorsConfiguration();
	        config.setAllowCredentials(true); // Allow cookies and credentials
	        config.addAllowedOriginPattern("*"); // Use specific origins in production
	        config.addAllowedHeader("*"); // Allow all headers
	        config.addAllowedMethod("*"); // Allow all HTTP methods
	        source.registerCorsConfiguration("/**", config); 
	        return new CorsFilter(source);   
	}
}
