package com.manning.sbip.ch06.security;

import com.manning.sbip.ch06.filter.TotpAuthFilter;
import com.manning.sbip.ch06.handler.CustomAuthenticationFailureHandler;
import com.manning.sbip.ch06.service.impl.CustomUserDetailsService;
import com.manning.sbip.ch06.service.impl.DefaultAuthenticationSuccessHandler;
import com.manning.sbip.ch06.service.impl.Oauth2AuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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

        @Autowired
        private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;


        @Autowired
        private CustomUserDetailsService customUserDetailsService;

        @Autowired
        private TotpAuthFilter totpAuthFilter;

        // http security 설정
        // SecurityFilterChain를 return 하기 위해 HttpSecurity를 설정하여 build, return 해준다.
        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http
                                             //  ,HandlerMappingIntrospector introspector
        ) throws Exception {
               // MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);

                // UsernamePasswordAuthenticationFilter 보다 앞에서 실행될 수 있게.
                // totpAuthFilter가 먼저 실행될 수 있게 설정한다.
                http.addFilterBefore(totpAuthFilter, UsernamePasswordAuthenticationFilter.class);

               http
                       .requiresChannel((channel) -> channel.anyRequest().requiresSecure()) // 모든 요청에 HTTPS 를 강제한다.
                        //인증 http 요청 설정.
                        //spring 3.x 대 이상부터는 H2 colsole enable = true 라면 Mvc 패턴과 Ant 패턴을 정확히 지정해주어야한다.
                        .authorizeHttpRequests(
                                (authz) -> authz
                                        //인증 없이 엔드포인트가 접근 가능하도록 세팅.
                                        .requestMatchers(new AntPathRequestMatcher("/login")).permitAll()
                                        .requestMatchers(new AntPathRequestMatcher("/adduser")).permitAll()
                                        .requestMatchers(new AntPathRequestMatcher("/login-error")).permitAll()
                                        .requestMatchers(new AntPathRequestMatcher("/login-verified")).permitAll()
                                        .requestMatchers(new AntPathRequestMatcher("/verify/email")).permitAll()
                                        .requestMatchers(new AntPathRequestMatcher("/login-locked")).permitAll()
                                        .requestMatchers(new AntPathRequestMatcher("/totp-login")).hasAuthority("TOTP_AUTH_AUTHORITY")
                                        .requestMatchers(new AntPathRequestMatcher("/totp-login-error")).hasAuthority("TOTP_AUTH_AUTHORITY")
                                        //.requestMatchers("/delete/**").hasRole("ADMIN") // 어드민 권한을 가진 계정만 delete 기능 사용 가능.
                                .anyRequest().authenticated()// 이외의 모든 요청은 인가 받은 유저만 접근 가능하고.
                                )
                    .httpBasic(Customizer.withDefaults())
                       .formLogin((login) -> login.loginPage("/login")
                        .successHandler(new DefaultAuthenticationSuccessHandler()).failureUrl("/login-error"))
                       .oauth2Login(oauth2 -> oauth2.loginPage("/login").successHandler(new Oauth2AuthenticationSuccessHandler()))
//                    .formLogin(// 인증 방식은 formLogin으로 설정,
//                            (login) -> login.loginPage("/login")//로그인 페이지는 /login으로.
//                            //.failureUrl("/login-error") // 실패시에는 설정한 값으로 리다이렉트
//                            .failureHandler(customAuthenticationFailureHandler))// 인증 실패 핸들러 설정.
//                       //리멤버미 기능 확설화 및 키와 쿠키 이름을 지정
//                       .rememberMe(remem -> remem.key("remember-me-key").rememberMeCookieName("course-tracker-remember-me"))
//                       //로그아웃 시 쿠키가 지워지게 설정.
//                       .logout(logout -> logout.deleteCookies("course-tracker-remember-me"))
               ;
            return http.build();
        }

        @Autowired
        protected UserDetailsService userDetailsService(){
                return this.customUserDetailsService;
        }


        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        // web security 설정
        @Bean
        public WebSecurityCustomizer webSecurityCustomizer() {
                //정적인 요소들은 항상 접근할 수 있도록 Matchers 를 설정해준다.
        return (web) -> web.ignoring()
                .requestMatchers(new AntPathRequestMatcher("/h2-console/*"))
                .requestMatchers(new AntPathRequestMatcher("/webjars/**"))
                .requestMatchers(new AntPathRequestMatcher("/images/**"))
                .requestMatchers(new AntPathRequestMatcher("/css/**"));


        }
}
