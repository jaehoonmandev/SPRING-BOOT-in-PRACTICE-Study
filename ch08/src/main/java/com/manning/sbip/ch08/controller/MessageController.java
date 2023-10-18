package com.manning.sbip.ch08.controller;

import java.time.Clock;
import java.time.Instant;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.manning.sbip.ch08.model.InputMessage;
import com.manning.sbip.ch08.model.OutputMessage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MessageController {

	@MessageMapping("/chat") // 엔드포인트 정의.
	@SendTo("/topic/messages") // /topic/messages 를 구독하는 모든 클라이언트에게 메시지 브로드케스팅.
	public OutputMessage message(InputMessage message) {
		log.info("Input Message "+message);
		return OutputMessage.builder()
				.time(Instant.now(Clock.systemDefaultZone())) // 생성된 시간
				.content(message.getContent()) // 내용
				.build();
	}
}
