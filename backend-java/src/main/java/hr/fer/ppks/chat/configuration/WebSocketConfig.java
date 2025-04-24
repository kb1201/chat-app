package hr.fer.ppks.chat.configuration;

import hr.fer.ppks.chat.dto.MessageResponse;
import hr.fer.ppks.chat.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        //config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-chat").setAllowedOriginPatterns("*").withSockJS();
        registry.addEndpoint("/ws-chat").setAllowedOriginPatterns("*");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {

            @Override
            public Message<?> preSend(@NonNull Message<?> message, @NonNull MessageChannel channel) {
                StompHeaderAccessor accessor =
                        MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

                assert accessor != null;
                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                    System.out.println("WebSocket CONNECT received");
                    String authorizationHeader = accessor.getFirstNativeHeader("Authorization");
                    assert authorizationHeader != null;

                    String token = authorizationHeader.substring(7);
                    System.out.println("Extracted Token: " + token);

                    String username = jwtService.extractUsername(token);
                    if (username == null) {
                        System.out.println("Failed to extract username from token");
                        return message;
                    }
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                    if (userDetails == null) {
                        System.out.println("User not found in database for username: " + username);
                    } else {
                        System.out.println("User found: " + userDetails.getUsername());
                    }

                    System.out.println("i am here");
                    assert userDetails != null;

                    System.out.println("i am here 2");
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    System.out.println("i am here 3");
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    System.out.println("i am here 4");
                   // System.out.println("Authentication set: " + SecurityContextHolder.getContext().getAuthentication() != null);

                    System.out.println("i am here 5");
                    accessor.setUser(authenticationToken);
                }
                return message;
            }

        });
    }
}