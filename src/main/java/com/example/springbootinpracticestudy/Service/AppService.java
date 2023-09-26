package com.example.springbootinpracticestudy.Service;

import com.example.springbootinpracticestudy.AppProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppService {
    private final AppProperties appProperties;

    @Autowired
    public AppService(AppProperties appProperties){
        this.appProperties = appProperties;
    }

    public AppProperties getAppProperties(){
        return this.appProperties;
    }
}
