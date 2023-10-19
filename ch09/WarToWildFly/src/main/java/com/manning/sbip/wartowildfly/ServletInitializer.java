package com.manning.sbip.wartowildfly;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

// SpringBootServletInitializer 를 통하여 WebApplicationInitializer 인터페이스를 구현한 ServletInitializer
public class ServletInitializer extends SpringBootServletInitializer {

  //SpringApplicationBuilder에 실행 클래스를 포함시켜 WAR 배포 환경에서 구동이 되게 설정.
  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(WarToWildFlyApplication.class);
  }

}