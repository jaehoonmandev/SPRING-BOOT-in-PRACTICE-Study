package com.sbip.ch04.listener;

import com.sbip.ch04.exeception.UrlNotAccessibleException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component // 스프링 컴포넌트로 인식할 수 있도록.
public class UrlAccesibliityHandler {

    @Value("${api.url:https://dog.ceo/}")
    private String url;

    //@EventListener(classes = ContextRefreshedEvent.class) // 초기화 단계에서 컨텍스트의 새로고침이 일어날 때.
    public void listen() {
        //항상 예외를 던지도록 작성.
        throw new UrlNotAccessibleException(url);
    }

}
