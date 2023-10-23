package com.manning.sbip.ch10.security

import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.beans
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class KotlinSecurityConfiguration : ApplicationContextInitializer<GenericApplicationContext> {

    //코틀린 DSL로 간단하게 passwordEncoder, InMemoryUserDetailsManager Bean 등록
    val beans =
        beans {
            bean("passwordEncoder") {
                BCryptPasswordEncoder()
            }
            bean {
                //user 인스턴스 정의
                fun user(user : String, password: String, vararg  roles : String)
                = User.builder()
                    .username(user)
                    .password(
                        ref<PasswordEncoder>().encode(password) // 위에서 정의한 passwordEncoder Bean을 ref 키워드를 통해 참조.
                    )
                    .roles(*roles)
                    .build()

                InMemoryUserDetailsManager(
                    user("user", "password", "USER"),
                    user("admin", "password", "ADMIN")
                )
            }
    }
    //ApplicationContextInitializer 의 인터페이스 메서드 재정의.
    //위에서 정의한 빈을 초기화할 때 사용.
    override fun initialize(applicationContext: GenericApplicationContext) {
        beans.initialize(applicationContext)
    }

    //코틀린으로 SecurityFilterChain 설정.
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.invoke {
            authorizeRequests {
                authorize("/login", permitAll)
                authorize("/login-error", permitAll)
                authorize(anyRequest, authenticated)
            }
            formLogin {
                loginPage = "/login"
                failureUrl = "/login-error"
            }
            httpBasic { }
        }
        return http.build()


    }


}