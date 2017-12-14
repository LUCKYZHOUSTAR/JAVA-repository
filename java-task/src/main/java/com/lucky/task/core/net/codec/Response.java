package com.lucky.task.core.net.codec;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.io.Serializable;

@Getter
@Setter
@ToString
public class Response  implements Serializable{
    public boolean Success;

    public String ErrorInfo;

    public Response() {
    }

    public Response(boolean success, String error) {
        this.Success = success;
        this.ErrorInfo = error;
    }
}

