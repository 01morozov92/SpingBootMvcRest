package com.myApp.exceptions.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserExceptionInfo {

    private String ex;
    private String url;

    public UserExceptionInfo(StringBuffer url, RuntimeException ex) {
        this.url = String.valueOf(url);
        this.ex = ex.getLocalizedMessage();
    }
}
