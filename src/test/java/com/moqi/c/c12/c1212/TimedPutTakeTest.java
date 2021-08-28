package com.moqi.c.c12.c1212;

import com.moqi.c.c12.c1205.PutTakeTest;
import com.moqi.c.c12.c1211.BarrierTimer;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CyclicBarrier;

/**
 * TimedPutTakeTest
 * <p/>
 * Testing with a barrier-based timer
 * 采用基于栅栏的定时器进行测试
 *
 * @author Brian Goetz and Tim Peierls
 */
@Slf4j
public class TimedPutTakeTest extends PutTakeTest {
    private BarrierTimer timer = new BarrierTimer();

    public TimedPutTakeTest(int cap, int pairs, int trials) {
        super(cap, pairs, trials);
        barrier = new CyclicBarrier(nPairs * 2 + 1, timer);
    }

    /**
     * 12-13 使用 TimedPutTakeTest 的程序
     */
    public static void main(String[] args) throws Exception {
        int tpt = 100000; // trials per thread 每个线程中的测试次数
        for (int cap = 1; cap <= 1000; cap *= 10) {
            log.info("Capacity: " + cap);
            for (int pairs = 1; pairs <= 128; pairs *= 2) {
                TimedPutTakeTest t = new TimedPutTakeTest(cap, pairs, tpt);
                log.info("Pairs: " + pairs + "\t");
                t.test();
                log.info("\t");
                Thread.sleep(1000);
                t.test();
                log.info("\n");
                Thread.sleep(1000);
            }
        }
        PutTakeTest.pool.shutdown();
    }

    public void test() {
        try {
            timer.clear();
            for (int i = 0; i < nPairs; i++) {
                pool.execute(new PutTakeTest.Producer());
                pool.execute(new PutTakeTest.Consumer());
            }
            barrier.await();
            barrier.await();
            long nsPerItem = timer.getTime() / (nPairs * (long) nTrials);
            log.info("Throughput: " + nsPerItem + " ns/item");
            assertEquals(putSum.get(), takeSum.get());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
