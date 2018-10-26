package org.squirrelframework.foundation.component;

/**
 * Allows for deferred execution of logic, useful when trying to get multiple components to coordinate behavior. 
 * A component may add a command to be executed "{@linkplain #execute() at the end of the heartbeat}". 
 * Also, Heartbeats can be nested.
 *
 *
 * 逻辑的执行是可以延迟执行的，当需要获取多个组件的时候，可以用该类
 * 每一个组件可以添加一个命令去调用execute（）的方法
 */
public interface Heartbeat {
    /**
     * Begins a new Heartbeat. Heartbeats nest. Every call to begin() should be matched by a call to {@link #execute()}.
     *
     * 创建一个heartbeat，并开始执行，
     */
    void begin();

    /**
     * Executes all commands since the most recent {@link #begin()}.
     */
    void execute();

    /**
     * Adds a new command to the current Heartbeat. The command will be executed by {@link #execute()}.
     * 
     * @param command
     *            command to be executed at the end of the heartbeat
     */
    void defer(Runnable command);
}
