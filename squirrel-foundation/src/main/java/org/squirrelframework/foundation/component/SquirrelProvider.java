package org.squirrelframework.foundation.component;

import java.lang.reflect.Constructor;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.squirrelframework.foundation.util.ReflectUtils;
import org.squirrelframework.foundation.util.TypeReference;

/**
 * Central factory class for components used by squirrel-foundation.
 *
 * @author Henry.He
 *
 * 整个框架的核心工厂类，组件生产者
 */
public class SquirrelProvider implements SquirrelSingleton {

  private static SquirrelProvider instance = new SquirrelProvider();

  public static SquirrelProvider getInstance() {
    return instance;
  }

  public static void setInstance(SquirrelProvider instance) {
    SquirrelProvider.instance = instance;
  }


  //注册的具体实现类，线程安全的map信息
  private Map<Class<?>, Class<?>> implementationRegistry = new ConcurrentHashMap<Class<?>, Class<?>>();

  //根据类来通过反射来创建组件信息
  public <T> T newInstance(TypeReference<T> typeRef) {
    return newInstance(typeRef, null, null);
  }

  public <T> T newInstance(TypeReference<T> typeRef, Class<?>[] argTypes, Object[] args) {
    //获取组件的原生类信息
    Class<T> clz = typeRef.getRawType();
    return clz.cast(newInstance(clz, argTypes, args));
  }

  /**
   * Create a new instance of the requested class using the internal registry.
   *
   * @param clz class of new instance
   * @param <T> type of new instance
   * @return new instance
   */
  public <T> T newInstance(Class<T> clz) {
    return newInstance(clz, null, null);
  }

  /**
   * Create a new instance of the requested class using the internal registry.
   *
   * @param clz class of new instance
   * @param argTypes arguments type of new instance constructor
   * @param args arguments of new instance constructor
   * @param <T> type of new instance
   * @return new instance
   */
  public <T> T newInstance(Class<T> clz, Class<?>[] argTypes, Object[] args) {

    //获取实现类信息
    Class<T> implementationClass = getImplementation(clz);

    //反射调用无参数的构造函数
    if (args == null) {
      return postProcess(clz, ReflectUtils.newInstance(implementationClass));
    }
    //反射调用带有参数的构造函数
    Constructor<T> constructor = ReflectUtils.getConstructor(implementationClass, argTypes);
    return postProcess(clz, ReflectUtils.newInstance(constructor, args));
  }

  private <T> T postProcess(Class<T> clz, T component) {
    SquirrelPostProcessor<T> postProcessor =
        SquirrelPostProcessorProvider.getInstance().getPostProcessor(clz);
    if (postProcessor != null && component != null) {
      //Post process created component,执行创建的组件流程信息
      postProcessor.postProcess(component);
    }
    return component;
  }

  /**
   * Register the implementation class for a certain class. Note, if there is already an entry in
   * the registry for the class, then it will be overwritten.
   *
   * @param clazz register class
   * @param implementationClass implementation class of register class
   */
  public void register(Class<?> clazz, Class<?> implementationClass) {
    // TODO: handle the case that there is already an entry...
    implementationRegistry.put(clazz, implementationClass);
  }

  public void unregister(Class<?> clazz) {
    implementationRegistry.remove(clazz);
  }

  public void clearRegistry() {
    implementationRegistry.clear();
  }

  /**
   * Return the current registered implementation. If class has register a implementation class,
   * return register implementation class. If register class or implement class is an interface, try
   * to find corresponding implementation class over naming convention. (implementation class simple
   * name = interface class simple name + "Impl") First try to find the implementation class with
   * conventional naming under the same package as interface class. If still not exist, try to find
   * implementation class in (interface class package + ".impl").
   *
   * @param clz registered class
   * @return current registered implementation
   *
   * 返回当前注册的实现类 如果这个类有注册的实现，那么立即返回即可，如果注册的类或者实现类是一个接口，那么尝试寻找符合的实现通过名称约定（impl），先通过相同的包
   * 名称路径来寻找，如果仍然不存在，那么尽力寻找在接口的实现类包路径中
   */
  public <T> Class<T> getImplementation(Class<T> clz) {
    return resolveImplIfInterface(clz, new HashSet<Class<?>>());
  }


  /**
   * 或者类的实现类信息
   */
  private <T> Class<T> resolveImplIfInterface(Class<T> clz, Set<Class<?>> visited) {
    //通过set来保证不允许循环注册
    if (!visited.add(clz)) {
      throw new IllegalStateException("Registration cycles: " + visited);
    }

    //如果注册的类不是接口
    if (!clz.isInterface()) {
      //如果类注册是接口，去本地注册寻找
      Class<T> possibleImpl = fromRegistry(clz);
      //当两者都不为空的时候，进行返回找到的注册类信息，有的话，久返回，没有的话，就返回本身
      if (possibleImpl != null && !possibleImpl.isInterface()) {
        clz = possibleImpl;
      }
      return clz;
    }

    //本地缓存寻找对应的实现类信息
    Class<T> possibleImpl = fromRegistry(clz);
    if (possibleImpl == null) {
      //没有找到的话，并且是接口的话，就通过名称进行寻找实现信息
      possibleImpl = findImplementationClass(clz);
      // We only register actual implementations so cannot introduce
      // cycles through this...
      //注册
      register(clz, possibleImpl);
    }

    //再进行二次寻找，第一次是添加
    return resolveImplIfInterface(possibleImpl, visited);
  }

  private <T> Class<T> fromRegistry(Class<T> clz) {
    @SuppressWarnings("unchecked")
    Class<T> impl = (Class<T>) implementationRegistry.get(clz);
    return impl;
  }

  // find implementation class name according to programming convention

  //根据名称和包路径进行匹配寻找
  @SuppressWarnings("unchecked")
  private <T> Class<T> findImplementationClass(Class<T> interfaceClass) {
    Class<?> implementationClass = null;
    String implClassName = interfaceClass.getName() + "Impl";
    try {
      implementationClass = Class.forName(implClassName);
    } catch (ClassNotFoundException e) {
      implClassName = ReflectUtils.getPackageName(interfaceClass.getName()) +
          ".impl." + interfaceClass.getSimpleName() + "Impl";
      implementationClass = ReflectUtils.getClass(implClassName);
    }
    return (Class<T>) implementationClass;
  }
}
