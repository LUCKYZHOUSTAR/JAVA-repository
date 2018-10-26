package org.squirrelframework.foundation.component;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import org.squirrelframework.foundation.component.impl.CompositePostProcessorImpl;
import org.squirrelframework.foundation.util.ClassComparator;
import org.squirrelframework.foundation.util.ReflectUtils;
import org.squirrelframework.foundation.util.TypeReference;

public class SquirrelPostProcessorProvider implements SquirrelComponent, SquirrelSingleton {

  private static SquirrelPostProcessorProvider instance = new SquirrelPostProcessorProvider();

  public static SquirrelPostProcessorProvider getInstance() {
    return instance;
  }

  public static void setInstance(SquirrelPostProcessorProvider instance) {
    SquirrelPostProcessorProvider.instance = instance;
  }


  //线程安全的类实例信息
  private Map<Class<?>, SquirrelPostProcessor<?>> postProcessorRegistry =
      new ConcurrentHashMap<Class<?>, SquirrelPostProcessor<?>>();

  /**
   * Register a new post processor class for a certain component class, note existing registration
   * are overwritten without warning.
   *
   * @param componentClass component class
   * @param postProcessor post processor to be registered for component class
   * @param <T> type of component class
   */
  @SuppressWarnings("unchecked")
  public <T> void register(Class<T> componentClass,
      SquirrelPostProcessor<? super T> postProcessor) {
    Method method = ReflectUtils.getFirstMethodOfName(postProcessor.getClass(), "postProcess");
    Class<?>[] params = method.getParameterTypes();
    Preconditions.checkArgument(params.length == 1,
        "Parameter size of method " + method.getName() + " is not match.");
    Preconditions.checkArgument(params[0].isAssignableFrom(componentClass),
        "Parameter type of method " + method.getName() + " is not correct.");

    //如果已经包含该组件的类型
    if (postProcessorRegistry.containsKey(componentClass)) {
      //查找存在的组件类型
      SquirrelPostProcessor<? super T> existedProcessor =
          (SquirrelPostProcessor<? super T>) postProcessorRegistry.get(componentClass);
      //如果是复合组件的话
      if (existedProcessor instanceof CompositePostProcessorImpl) {
        //强制转为复合组件，进行联合操作
        ((CompositePostProcessorImpl<T>) existedProcessor).compose(postProcessor);
      } else {
        //如果不是联合组件的话，进行移除，创建一个联合组件
        postProcessorRegistry.remove(componentClass);
        CompositePostProcessorImpl<T> compositeProcessor = new CompositePostProcessorImpl<T>(
            existedProcessor);
        compositeProcessor.compose(postProcessor);
        postProcessorRegistry.put(componentClass, compositeProcessor);
      }
    } else {
      //如果不存在的话，直接放置即可
      postProcessorRegistry.put(componentClass, postProcessor);
    }
  }

  public <T> void register(Class<T> componentClass,
      Class<? extends SquirrelPostProcessor<? super T>> postProcessorClass) {
    SquirrelPostProcessor<? super T> postProcessor = SquirrelProvider.getInstance()
        .newInstance(postProcessorClass);
    register(componentClass, postProcessor);
  }

  public void unregister(Class<?> componentClass) {
    postProcessorRegistry.remove(componentClass);
  }

  public void clearRegistry() {
    postProcessorRegistry.clear();
  }

  @SuppressWarnings("unchecked")
  public <T> SquirrelPostProcessor<T> getPostProcessor(Class<T> componentClass) {
    return (SquirrelPostProcessor<T>) postProcessorRegistry.get(componentClass);
  }

  @SuppressWarnings("unchecked")
  public <T> List<SquirrelPostProcessor<? super T>> getCallablePostProcessors(
      Class<T> componentClass) {
    List<SquirrelPostProcessor<? super T>> postProcessors = Lists.newArrayList();
    for (Entry<Class<?>, SquirrelPostProcessor<?>> entry : postProcessorRegistry.entrySet()) {
      if (entry.getKey().isAssignableFrom(componentClass)) {
        SquirrelPostProcessor<? super T> postProcessor = (SquirrelPostProcessor<? super T>) entry
            .getValue();
        postProcessors.add(postProcessor);
      }
    }
    return postProcessors;
  }

  public <T> SquirrelPostProcessor<? super T> getBestMatchPostProcessor(Class<T> componentClass,
      Comparator<SquirrelPostProcessor<? super T>> comparator) {
    List<SquirrelPostProcessor<? super T>> processors = getCallablePostProcessors(componentClass);
    if (processors.isEmpty()) {
      return null;
    }
    Collections.sort(processors, comparator);
    return processors.get(0);
  }

  public <T> SquirrelPostProcessor<? super T> getBestMatchPostProcessor(Class<T> componentClass) {
    return getBestMatchPostProcessor(componentClass,
        new ClassComparator<SquirrelPostProcessor<? super T>>());
  }

  public <T> void register(Class<T> componentClass,
      TypeReference<? extends SquirrelPostProcessor<? super T>> typeReference) {
    register(componentClass, typeReference.getRawType());
  }

  public <T> void register(TypeReference<T> typeRefComponent,
      SquirrelPostProcessor<? super T> postProcessor) {
    register(typeRefComponent.getRawType(), postProcessor);
  }
}
