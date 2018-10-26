package org.squirrelframework.foundation.fsm;

/**
 * A constraint which must evaluate to true after the trigger occurs in order for the transition to complete.
 * 
 * @author Henry.He
 *
 * @param <C> type of context
 */
public interface Condition<C> {
    /**
     * @param context context object
     * @return whether the context satisfied current condition
     * 是否当前上下文，满足当前的条件
     */
    boolean isSatisfied(C context);
    
    String name();
}
