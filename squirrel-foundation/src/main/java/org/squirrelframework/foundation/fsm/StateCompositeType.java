package org.squirrelframework.foundation.fsm;

/**
 * Composite state is defined as state that has substates (nested states). Substates could be sequential (disjoint) 
 * or parallel (orthogonal). {@code StateCompositeType} defines the type of composite state.
 * 
 * @author Henry.He
 */
public enum StateCompositeType {
    /**
     * The child states are mutually exclusive and an initial state must
     * be set by calling MutableState.setInitialState()
     * 字状态是互斥的，就顺序执行
     */
    SEQUENTIAL,

    /**
     * The child states are parallel. When the parent state is entered,
     * all its child states are entered in parallel.
     *
     * 子状态是并行的
     */
    PARALLEL
}
