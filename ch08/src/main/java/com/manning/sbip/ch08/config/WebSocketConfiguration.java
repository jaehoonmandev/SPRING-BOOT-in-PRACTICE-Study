package com.manning.sbip.ch08.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker // 웹소켓 메세지를 중간에서 핸들링하기 위한 설정.
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

	//웹소켓 엔드포인트(/ws)에 STOMP 엔드포인트 등록
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/ws")
				// SockJS 폴백 옵션 활성화.
				// 이를통해 웹소켓 프로토콜을 지원하지 않는 브라우저에서도 웹소켓이 동작하도록한다.
				.withSockJS();
	}

	//메시지 브로커 옵션 설정.
	//메시지를 주고 받을 한 개 이상의 목적지를 가진 임메모리 메시지 브로커를 생성.
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.enableSimpleBroker("/topic"); // 목적지 생성.
		registry.setApplicationDestinationPrefixes("/app"); // 목적지 접두어(prefix)
		// 위의 설정들로 @MessagingMapping 애너테이션이 붙어있는 메서드의 목적지를 필터링 할 수 있다.
	}
}
