package com.lucky.task.client.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class Result {
    public boolean Success;

    public String ErrorInfo;

    public Result() {
    }

    public Result(boolean success, String error) {
        this.Success = success;
        this.ErrorInfo = error;
    }
}

