package org.squirrelframework.foundation.component;

/**
 * Post process object created by {@link SquirrelProvider}
 *
 * @param <T> type of object to be processed
 *
 * 用来传递被SquirrelProvider产生的流程对象 流程传递对象，SquirrelProvider生产的对象
 * @author Henry.He
 */
public interface SquirrelPostProcessor<T> {

  /**
   * Post process created component
   *
   * @param component created component
   */
  void postProcess(T component);
}
