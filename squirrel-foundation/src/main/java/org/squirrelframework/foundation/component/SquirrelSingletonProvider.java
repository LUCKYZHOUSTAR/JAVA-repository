package org.squirrelframework.foundation.component;

import com.google.common.base.Preconditions;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


//单例的组件对象实现
public class SquirrelSingletonProvider implements SquirrelComponent, SquirrelSingleton {

  private static SquirrelSingletonProvider instance = new SquirrelSingletonProvider();

  public static SquirrelSingletonProvider getInstance() {
    return instance;
  }

  public static void setInstance(SquirrelSingletonProvider instance) {
    SquirrelSingletonProvider.instance = instance;
  }


  //缓存的实例注册工厂信息
  private Map<String, Object> instanceRegistry = new ConcurrentHashMap<String, Object>();

  public <T> void register(Class<T> componentClass, Object instance) {
    Preconditions.checkArgument(componentClass.isAssignableFrom(instance.getClass()));
    instanceRegistry.put(componentClass.getName(), instance);
  }

  public void unregister(Class<?> componentClass) {
    instanceRegistry.remove(componentClass.getName());
  }


  public void clearRegistry() {
    instanceRegistry.clear();
  }

  public <T> T get(Class<T> componentClass) {
    Object instance = instanceRegistry.get(componentClass.getName());
    if (instance == null) {
      try {
        //实例不存在的话，就通过反射来实例化
        instance = SquirrelProvider.getInstance().newInstance(componentClass);
      } catch (Exception e) {
        instance = null;
      }
      if (instance != null) {
        register(componentClass, instance);
      }
    }
    return instance != null ? componentClass.cast(instance) : null;
  }
}
