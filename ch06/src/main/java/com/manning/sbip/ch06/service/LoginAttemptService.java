package com.manning.sbip.ch06.service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

@Service
public class LoginAttemptService {
    private static final int MAX_ATTEMPTS_COUNT = 3; //허용 횟수
    private LoadingCache<String, Integer> loginAttemptCache; // username(String)을 키로 설정한다.(구아바 캐시)

    //생성 후 하루(24시간) 뒤 만료되는 캐시를 생성한다.
    public LoginAttemptService() {
        loginAttemptCache = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.DAYS)
                .build(new CacheLoader<String, Integer>() {
                    @Override
                    public Integer load(final String key) {
                        return 0;
                    }
                });
    }

    //로그인 성공 시 캐시를 무효화 시킨다.
    public void loginSuccess(String username) {
        loginAttemptCache.invalidate(username);
    }
    // 실패 시 횟수를 증가시켜 값을 등록/갱신 한다.
    public void loginFailed(String username) {
        int failedAttemptCounter = 0;

        try {
            failedAttemptCounter = loginAttemptCache.get(username);
        }
        catch (ExecutionException e) {
            failedAttemptCounter = 0;
        }
        failedAttemptCounter++;
        loginAttemptCache.put(username, failedAttemptCounter);
    }

    // 유저의 로그인 실패 횟수를 읽어와 정해진 실패 횟수와 비교하여 크면 로그인 할 수 없게.
    public boolean isBlocked(String username) {
        try {
            return loginAttemptCache.get(username) >= MAX_ATTEMPTS_COUNT;
        }
        catch (ExecutionException e) {
            return false;
        }
    }
}
