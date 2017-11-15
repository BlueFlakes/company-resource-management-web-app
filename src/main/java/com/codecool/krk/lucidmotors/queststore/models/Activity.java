package com.codecool.krk.lucidmotors.queststore.models;

public class Activity {
    Integer httpStatusCode;
    String answer;

    public Activity(Integer httpStatusCode, String answer) {
        this.httpStatusCode = httpStatusCode;
        this.answer = answer;
    }

    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }

    public String getAnswer() {
        return answer;
    }
}
