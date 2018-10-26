package org.squirrelframework.foundation.event;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import org.squirrelframework.foundation.component.SquirrelComponent;

/**
 * @Author:chaoqiang.zhou
 * @Date:Create in 下午4:06 2018/10/26 时间分发器
 */
public class PolymEventDispatcher implements SquirrelComponent {


  private LinkedHashSet<ListenerMethod> listeners = null;


  public void register(Class<?> eventType, Object listener, Method method) {
    if (listeners == null) {
      listeners = new LinkedHashSet<ListenerMethod>();
    }
    listeners.add(new ListenerMethod(eventType, listener, method));
  }


  public void unregister(Predicate<ListenerMethod> predicate) {
    if (listeners != null) {
      Iterators.removeIf(listeners.iterator(), predicate);
    }
  }

  public void unregister(final Class<?> eventType, final Object target) {
    if (listeners != null) {
      Iterators.removeIf(listeners.iterator(), new Predicate<ListenerMethod>() {
        @Override
        public boolean apply(ListenerMethod m) {
          return m.matches(eventType, target);
        }
      });
    }
  }

  public void unregister(final Class<?> eventType, final Object target, final Method method) {
    if (listeners != null) {
      ListenerMethod toBeRemove = Iterators
          .find(listeners.iterator(), new Predicate<ListenerMethod>() {
            @Override
            public boolean apply(ListenerMethod m) {
              return m.matches(eventType, target, method);
            }
          });
      if (toBeRemove != null) {
        listeners.remove(toBeRemove);
      }
    }
  }

  public void unregisterAll() {
    listeners = null;
  }


  /***
   * 触发事件
   * @param event
   */
  public void fireEvent(Object event) {
    if (listeners == null) {
      return;
    }
    ListenerMethod[] listenerArray = listeners.toArray(new ListenerMethod[listeners.size()]);
    for (int i = 0; i < listenerArray.length; i++) {
      if (listenerArray[i].getEventType().isAssignableFrom(event.getClass())) {
        listenerArray[i].invokeMethod(event);
      }
    }
  }

  public int getListenerSize() {
    return listeners != null ? listeners.size() : 0;
  }

}
