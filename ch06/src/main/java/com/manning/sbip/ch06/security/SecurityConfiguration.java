package com.manning.sbip.ch06.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity // 커스텀 보안 설정 인식
//WebSecurityConfigurerAdapter를 확장해서 설정하는 방법은 deprecated 되었기에 새로운 방법으로 적용해야한다.
public class SecurityConfiguration {

//        @Autowired
//        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//                auth.inMemoryAuthentication()
//                        .passwordEncoder(passwordEncoder())
//                        .withUser("user")
//                        .password(passwordEncoder().encode("pass"))
//                        .roles("USER");
//        }

        // http security 설정
        // SecurityFilterChain를 return 하기 위해 HttpSecurity를 설정하여 build, return 해준다.
        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http
                 //                              ,HandlerMappingIntrospector introspector
        ) throws Exception {
               // MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);
                http
                    .requiresChannel((channel) -> channel.anyRequest().requiresSecure()) // 모든 요청에 HTTPS 를 강제한다.
                    .authorizeHttpRequests( //인증 http 요청의 설정은
                    (authz) -> authz.requestMatchers("/login","/adduser","/login-error",
                                    "/webjars/**","/images/**","/css/**","/h2-console/**"
                                    ).permitAll()
                    .requestMatchers("/delete/**").hasRole("ADMIN") // 어드민 권한을 가진 계정만 delete 기능 사용 가능.
                    .anyRequest().authenticated()) // 이외의 모든 요청은 인가 받은 유저만 접근 가능하고.
                    .httpBasic(Customizer.withDefaults())
                    .formLogin(// 인증 방식은 formLogin으로 설정,
                            (login) -> login.loginPage("/login")//로그인 페이지는 /login으로.
                            .failureUrl("/login-error") // 실패시에는 설정한 값으로 리다이렉트
            );

            return http.build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        // web security 설정
//        @Bean
//        public WebSecurityCustomizer webSecurityCustomizer() {
//                //정적인 요소들은 항상 접근할 수 있도록 Matchers 를 설정해준다.
//        return (web) -> web.ignoring()
//                .requestMatchers(new AntPathRequestMatcher("/h2-console/**"))
//                .requestMatchers(new AntPathRequestMatcher("/webjars/**"))
//                .requestMatchers(new AntPathRequestMatcher("/images/**"))
//                .requestMatchers(new AntPathRequestMatcher("/css/**"));
//
//
//        }
}
