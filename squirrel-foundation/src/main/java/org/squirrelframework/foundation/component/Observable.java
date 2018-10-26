package org.squirrelframework.foundation.component;

import com.google.common.base.Predicate;
import java.lang.reflect.Method;
import org.squirrelframework.foundation.event.ListenerMethod;
import org.squirrelframework.foundation.event.SquirrelEvent;

/**
 * This interface represents an observable object, or "data" in the subject-observer paradigm. It
 * can be implemented to represent an object that the observer wants to have listened.
 *
 * @author Henry.He
 *
 *
 * 观察者的接口信息，接口用来观察一个对象，监听一个观察者的对象 当有事件变更的时候，需要立即通知到所有的监听者
 */
public interface Observable {

  /**
   * @return whether the subject is notifiable 是否通知到
   */
  boolean isNotifiable();

  /**
   * Set notifiable of subject
   */
  void setNotifiable(boolean notifiable);

  /**
   * Add listener to observable subject.
   *
   * @param eventType type of event
   * @param listener listener object
   * @param method listener method
   */
  void addListener(Class<?> eventType, Object listener, Method method);

  /**
   * Add listener to observable subject.
   *
   * @param eventType type of event
   * @param listener listener object
   * @param methodName name of listener method
   */
  void addListener(Class<?> eventType, Object listener, String methodName);

  void removeListener(Predicate<ListenerMethod> predicate);

  /**
   * Remove listener from observable subject.
   *
   * @param eventType type of event
   * @param listener listener object
   * @param method listener method
   */
  void removeListener(Class<?> eventType, Object listener, Method method);

  /**
   * Remove listener to observable subject.
   *
   * @param eventType type of event
   * @param listener listener object
   * @param methodName name of listener method
   */
  void removeListener(Class<?> eventType, Object listener, String methodName);

  /**
   * Add listener to observable subject.
   *
   * @param eventType type of event
   * @param listener listener object
   */
  void removeListener(Class<?> eventType, Object listener);

  /**
   * Remove all listeners
   */
  void removeAllListeners();

  /**
   * Fire event to notify all observers
   *
   * 出发事件去通知所有的观察者
   *
   * @param event based event
   */
  void fireEvent(SquirrelEvent event);

  int getListenerSize();
}
