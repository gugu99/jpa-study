package com.gugu.study.comm;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ErrorCode {
    UNAUTHORIZED("A001", "유효하지않은 토큰입니다."),
    TEST("T001","TEST ERROR");

    private final String code;
    private final String message;

    ErrorCode(String code, String message){
        this.code = code;
        this.message = message;
    }

    public static ErrorCode valueOfCode(String errorCode) {
        return Arrays.stream(values())
                .filter(value -> value.code.equals(errorCode))
                .findAny()
                .orElse(null);
    }
}
