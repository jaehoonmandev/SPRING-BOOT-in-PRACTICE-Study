package com.sbip.ch04.exeception;

//URL 연결이 이루어지지 않으면 예외 발생.
public class UrlNotAccessibleException extends RuntimeException {


    private String url;

    public String getUrl() {
        return url;
    }

    public UrlNotAccessibleException(String url){
        this(url, null);
    }

    public UrlNotAccessibleException(String url, Throwable cause) {
        //RuntimeException의 에러 출력 메서드를 이용하는 super
        super("URL " + url + " is not accessible", cause);
        this.url = url;
    }
}
