package com.codecool.krk.lucidmotors.queststore.models;

public class Activity {
    Integer httpStatusCode;
    String answer;
    String headerName;
    String headerContent;

    public Activity(Integer httpStatusCode, String answer) {
        this.httpStatusCode = httpStatusCode;
        this.answer = answer;
    }

    public Activity(Integer httpStatusCode, String answer, String headerName, String headerContent) {
        this.httpStatusCode = httpStatusCode;
        this.answer = answer;
        this.headerName = headerName;
        this.headerContent = headerContent;
    }

    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }

    public String getAnswer() {
        return answer;
    }

    public Boolean hasHeader() {
        return headerContent != null && headerContent != null;
    }

    public String getHeaderName() {
        return headerName;
    }

    public String getHeaderContent() {
        return headerContent;
    }
}
