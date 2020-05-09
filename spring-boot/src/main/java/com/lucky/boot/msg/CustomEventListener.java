package com.lucky.boot.msg;

import java.util.List;

import com.google.common.eventbus.Subscribe;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @description:
 * @author: lucky
 * @created: 2020/05/09 10:18
 */
@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomEventListener {

    private List<String> listenedMessageList;

    @Subscribe
    public void onEvent(CustomEvent event) {
        System.out.println(event.getMessage());
        listenedMessageList.add(event.getMessage());
    }

}
