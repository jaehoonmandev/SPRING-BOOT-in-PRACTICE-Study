package com.example.springbootinpracticestudy;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.List;

/**
 * Spring에서 유효성 검사를 마치고 application.properties에 등록된
 * app.sbip.ct의 prefix를 읽어온다.
 * 또한 프로퍼티의 타입 안정성 또하 보장받을 수 있다.
 * */
@ConfigurationProperties("app.sbip.ct")
public class AppProperties {


    /**
     * Application Name
     */
    private final String name;

    /**
     * Application IP
     */
    private final String ip;

    /**
     * Application IP
     */
    private final int port;

    /**
     * Application Security configuration
     */
    private final Security security;

    public String getName() {
        return name;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public Security getSecurity() {
        return security;
    }
    //생성자를 통한 프로퍼티 정보값 설정.(불변성 보장)
    @ConstructorBinding
    //@DefaultValue를 통하여 port 값이 지정되지 않았다면 기본으로 8080의 값을 넣어준다.
    public AppProperties(String name, String ip, @DefaultValue("8080") int port, Security security) {
        this.name = name;
        this.ip = ip;
        this.port = port;
        this.security = security;
    }

    @Override
    public String toString() {
        return "AppProperties{" +
                "name='" + name + '\'' +
                ", ip='" + ip + '\'' +
                ", port='" + port + '\'' +
                ", security=" + security +
                '}';
    }

    public static class Security {

        /**
         * Enable Security. Possible values true/false
         */
        private boolean enabled;

        /**
         * Token Value
         */
        private final String token;

        /**
         * Available roles
         */
        private final List<String> roles;

        public Security(boolean enabled, String token, List<String> roles) {
            this.enabled = enabled;
            this.token = token;
            this.roles = roles;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public String getToken() {
            return token;
        }

        public List<String> getRoles() {
            return roles;
        }

        @Override
        public String toString() {
            return "Security{" +
                    "enabled=" + enabled +
                    ", token='" + token + '\'' +
                    ", roles=" + roles +
                    '}';
        }
    }


}