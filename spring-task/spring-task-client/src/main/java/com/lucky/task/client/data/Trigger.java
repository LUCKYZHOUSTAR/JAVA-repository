package com.lucky.task.client.data;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class Trigger {
    public String Cron;

    public LocalDateTime Start;

    public LocalDateTime End;
}