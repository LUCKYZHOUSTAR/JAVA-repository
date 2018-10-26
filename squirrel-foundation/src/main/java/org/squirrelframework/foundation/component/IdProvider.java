package org.squirrelframework.foundation.component;

import java.util.UUID;
import org.apache.commons.lang3.RandomStringUtils;


/***
 * id生成器
 */
public interface IdProvider {

  String get();


  /**
   * 默认的id生成器
   */
  public class Default implements IdProvider, SquirrelSingleton {

    private static IdProvider instance = new Default();

    public static IdProvider getInstance() {
      return instance;
    }

    public static void setInstance(IdProvider instance) {
      Default.instance = instance;
    }

    @Override
    public String get() {

      /***
       *生成指定长度的字母和数字的随机组合字符串
       */
      return RandomStringUtils.randomAlphanumeric(10);
    }
  }

  public class UUIDProvider implements IdProvider, SquirrelSingleton {

    private static IdProvider instance = new UUIDProvider();

    public static IdProvider getInstance() {
      return instance;
    }

    public static void setInstance(IdProvider instance) {
      UUIDProvider.instance = instance;
    }

    @Override
    public String get() {
      return UUID.randomUUID().toString();
    }
  }


}
