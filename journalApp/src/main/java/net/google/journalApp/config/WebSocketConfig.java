package net.google.journalApp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
	    registry.enableSimpleBroker("/topic", "/queue", "/user"); // Supports private messages
	    registry.setApplicationDestinationPrefixes("/app"); // Prefix for sending messages
	    registry.setUserDestinationPrefix("/user"); // Prefix for private messages
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/ws").setAllowedOriginPatterns("*") // Allows all origins (CORS support)
			  .withSockJS();
	}

}
