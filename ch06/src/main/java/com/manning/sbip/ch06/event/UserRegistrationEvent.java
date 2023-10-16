package com.manning.sbip.ch06.event;

import com.manning.sbip.ch06.model.User;
import org.springframework.context.ApplicationEvent;

public class UserRegistrationEvent extends ApplicationEvent {

    private static final long serialVersionUID = -2685172945219633123L;

    private User User;

    //들어온 오브젝트 이벤트 등록
    public UserRegistrationEvent(User User) {
        super(User);
        this.User = User;
    }

    public User getUser() {
        return User;
    }
}
