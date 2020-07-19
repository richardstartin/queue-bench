package io.github.richardstartin.qb;

import org.jctools.queues.MpscCompoundQueue;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

@State(Scope.Benchmark)
public class CompoundQueueState extends QueueSizeState  implements DroppingOffer {

  Thread drainer;

  MpscCompoundQueue<Object> queue;

  @Override
  public boolean offer(Object o) {
    return queue.offer(o);
  }

  @Setup(Level.Trial)
  public void createQueue() {
    queue = new MpscCompoundQueue<>(queueSize, Runtime.getRuntime().availableProcessors());
  }


  @Setup(Level.Iteration)
  public void setupDrainer(Blackhole bh) {
    drainer = new Thread(() -> {
      try {
        int backoff = 1;
        int misses = 0;
        while (!Thread.currentThread().isInterrupted()) {
          Object o = queue.poll();
          if (null != o) {
            bh.consume(o);
            misses = 0;
            backoff = 1;
          } else {
            ++misses;
            if (misses >= 5) {
              Thread.sleep(backoff);
              backoff = Math.max(backoff * 2, 10);
            }
          }
        }
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    });
    drainer.start();
  }


  @TearDown(Level.Iteration)
  public void clearQueue() {
    drainer.interrupt();
    queue.clear();
  }
}
