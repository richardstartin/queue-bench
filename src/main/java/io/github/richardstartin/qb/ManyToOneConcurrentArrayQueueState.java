package io.github.richardstartin.qb;

import org.agrona.concurrent.ManyToOneConcurrentArrayQueue;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

@State(Scope.Benchmark)
public class ManyToOneConcurrentArrayQueueState extends QueueSizeState implements DroppingOffer {

  ManyToOneConcurrentArrayQueue<Object> queue;
  Thread drainer;

  @Setup(Level.Trial)
  public void setupQueue() {
    queue = new ManyToOneConcurrentArrayQueue<>(queueSize);
  }

  @Override
  public boolean offer(Object m) {
    return queue.offer(m);
  }

  @Setup(Level.Iteration)
  public void setupDrainer(Blackhole bh) {
    drainer = new Thread(() -> {
      while (!Thread.currentThread().isInterrupted()) {
        queue.drain(bh::consume, 256);
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
