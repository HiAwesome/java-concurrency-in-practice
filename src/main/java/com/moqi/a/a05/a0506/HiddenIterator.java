package com.moqi.a.a05.a0506;

import lombok.extern.slf4j.Slf4j;
import net.jcip.annotations.GuardedBy;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * HiddenIterator
 * <p/>
 * Iteration hidden within string concatenation
 * 隐藏在字符串连接中的迭代操作（不要这么做）
 *
 * @author Brian Goetz and Tim Peierls
 */
@Slf4j
public class HiddenIterator {
    @GuardedBy("this")
    private final Set<Integer> set = new HashSet<>();

    public synchronized void add(Integer i) {
        set.add(i);
    }

    public synchronized void remove(Integer i) {
        set.remove(i);
    }

    public void addTenThings() {
        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            add(r.nextInt());
        }

        log.info("DEBUG: added ten elements to " + set);
    }
}
