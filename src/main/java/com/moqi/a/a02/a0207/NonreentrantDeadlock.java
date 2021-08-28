package com.moqi.a.a02.a0207;

import lombok.extern.slf4j.Slf4j;

/**
 * NonreentrantDeadlock
 * <p/>
 * Code that would deadlock if intrinsic locks were not reentrant
 * 如果内置锁不是可重入的，那么这段代码将发生死锁
 *
 * @author Brian Goetz and Tim Peierls
 */

class Widget {
    public synchronized void doSomething() {
    }
}

@Slf4j
class LoggingWidget extends Widget {
    @Override
    public synchronized void doSomething() {
        log.info(": calling doSomething");
        super.doSomething();
    }
}
