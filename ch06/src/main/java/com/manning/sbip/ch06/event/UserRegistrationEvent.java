package com.manning.sbip.ch06.event;

import com.manning.sbip.ch06.model.ApplicationUser;
import org.springframework.context.ApplicationEvent;

public class UserRegistrationEvent extends ApplicationEvent {

    private static final long serialVersionUID = -2685172945219633123L;

    private ApplicationUser applicationUser;

    //들어온 오브젝트 이벤트 등록
    public UserRegistrationEvent(ApplicationUser applicationUser) {
        super(applicationUser);
        this.applicationUser = applicationUser;
    }

    public ApplicationUser getUser() {
        return applicationUser;
    }
}
