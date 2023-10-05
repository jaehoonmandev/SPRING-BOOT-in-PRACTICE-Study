package com.sbip.ch04.exeception;

import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;


public class UrlNotAccessibleFailureAnalyzer
        extends AbstractFailureAnalyzer<UrlNotAccessibleException> {

    //UrlNotAccessibleException 에서 예외가 발생한다면 FailureAnalysis 인스턴스 호출로 실패 분석기를 생성한다.
    @Override
    protected FailureAnalysis analyze(Throwable rootFailure, UrlNotAccessibleException cause) {
        return new FailureAnalysis("Unable to access the URL "+cause.getUrl(),
                "Validate the URL and ensure it is accessible", cause);
    }

}
