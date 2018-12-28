package com.lucky.design.patterns.document.abstrac;

import com.lucky.design.patterns.document.abstrac.domain.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2018/12/24 15:02
 * @Description:
 */
public class App {

    public App() {

        Map<String, Object> carProperties = new HashMap<>();
        carProperties.put(HasModel.PROPERTY, "300SL");
        carProperties.put(HasPrice.PROPERTY, 10000L);

        Map<String, Object> wheelProperties = new HashMap<>();
        wheelProperties.put(HasType.PROPERTY, "wheel");
        wheelProperties.put(HasModel.PROPERTY, "15C");
        wheelProperties.put(HasPrice.PROPERTY, 100L);

        Map<String, Object> doorProperties = new HashMap<>();
        doorProperties.put(HasType.PROPERTY, "door");
        doorProperties.put(HasModel.PROPERTY, "Lambo");
        doorProperties.put(HasPrice.PROPERTY, 300L);
        carProperties.put(HasParts.PROPERTY, Arrays.asList(wheelProperties, doorProperties));
        Car car = new Car(carProperties);

    }

    /**
     * Program entry point
     *
     * @param args command line args
     */
    public static void main(String[] args) {
        new App();
    }
}
