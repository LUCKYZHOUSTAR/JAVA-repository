package org.squirrelframework.foundation.component;

/**
 * Interface to flag a class which has a static setInstance/getInstance method which can be used by an external software
 * to set a specific implementation of a component, or interface which has a static field named INSTANCE.
 * 
 * @author Henry.He
 *
 *
 * 就是一个标记类，当一个类需要用单例模式来表示的时候，就可以继承这个类，并且单例的类有一个instance的实例，也有getInstance的方法
 *
 */
public interface SquirrelSingleton {
}
