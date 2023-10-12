package com.manning.sbip.ch05.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity // 커스텀 보안 설정 인식
//WebSecurityConfigurerAdapter를 확장해서 설정하는 방법은 deprecated 되었기에 새로운 방법으로 적용해야한다.
public class SecurityConfiguration {

        @Autowired
        private AccessDeniedHandler customAccessDeniedHandler;

        // http security 설정
        // SecurityFilterChain를 return 하기 위해 HttpSecurity를 설정하여 build, return 해준다.
        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http.authorizeHttpRequests( //인증 http 요청의 설정은
                    (authz) -> authz.requestMatchers("/login").permitAll() // /login의 엔드포인트는 항상 허용된다.
                    .requestMatchers("/delete/**").hasRole("ADMIN") //
                    .anyRequest().authenticated()) // 이외의 모든 요청은 인가 받은 유저만 접근 가능하고.
                    .httpBasic(Customizer.withDefaults())
                    .formLogin((login) -> login.loginPage("/login")// 인증 방식은 formLogin으로 설정, 로그인 페이지는 /login으로.
            ).exceptionHandling((ex) -> ex.accessDeniedHandler(customAccessDeniedHandler)); // 실패할 시에 AccessDeniedHandler를 통해 에러 도출
            return http.build();
        }

        //InMemoryUserDetailManger의 user 계정의 비밀번호를 랜던 UUID에서 고정 비밀번호로 사용하도록 설정
        @Bean
        public UserDetailsService authenticationManager() {

                //유저
                UserDetails theUser = User.withUsername("user")
                        .passwordEncoder(passwordEncoder()::encode)
                        .password("p@ssw0rd").roles("USER").build();

                //어드민
                UserDetails theAdmin = User.withUsername("admin")
                        .passwordEncoder(passwordEncoder()::encode)
                        .password("pa$$w0rd").roles("ADMIN").build();

                //인메모리 유저 상세 설정.
                InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();

                // 설정한 UserDetails 등록
                userDetailsManager.createUser(theUser);
                userDetailsManager.createUser(theAdmin);

                return userDetailsManager;

        }

        //비밀번호 암호화 메서드
        @Bean
        public PasswordEncoder passwordEncoder(){
                return new BCryptPasswordEncoder();
        }

        // web security 설정
        @Bean
        public WebSecurityCustomizer webSecurityCustomizer() {
                //정적인 요소들은 항상 접근할 수 있도록 Matchers 를 설정해준다.
        return (web) -> web.ignoring().requestMatchers("/webjars/**", "/images/**", "/css/**", "/h2-console/**");
        }
}
