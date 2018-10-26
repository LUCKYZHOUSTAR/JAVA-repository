package org.squirrelframework.foundation.component;

/**
 * Composite post processor together and being processed orderly.
 *
 * @param <T> bean type
 *
 * 组合传递的流程对象，当触发的时候，按照顺序来依次执行
 * @author Henry.He
 */
public interface CompositePostProcessor<T> extends SquirrelPostProcessor<T> {

  /**
   * Compose new processor to composite processor list. Not allowed to composite duplicate
   * processor.
   *
   * @param processor new processor
   *
   * 用来组合新的流程对象来形成一个链式的操作，不允许来组合重复的处理对象
   */
  void compose(SquirrelPostProcessor<? super T> processor);

  /**
   * Decompose old processor from composite processor list.
   *
   * @param processor old processor
   * 移除无用的处理对象
   */
  void decompose(SquirrelPostProcessor<? super T> processor);

  /**
   * Clear all the processors in the composite processor list.
   *
   * 清除所有的流程对象
   */
  void clear();
}
