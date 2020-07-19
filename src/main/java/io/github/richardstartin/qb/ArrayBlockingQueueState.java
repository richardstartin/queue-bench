package io.github.richardstartin.qb;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

@State(Scope.Benchmark)
public class ArrayBlockingQueueState extends QueueSizeState implements DroppingOffer {

  ArrayBlockingQueue<Object> queue;
  Thread drainer;

  @Setup(Level.Trial)
  public void setupQueue() {
    queue = new ArrayBlockingQueue<>(queueSize);
  }

  @Override
  public boolean offer(Object m) {
    return queue.offer(m);
  }

  @Setup(Level.Iteration)
  public void setupDrainer(Blackhole bh) {
    drainer = new Thread(() -> {
      List<Object> batch = new ArrayList<>(256);
      try {
        while (!Thread.currentThread().isInterrupted()) {
          Object next = queue.take();
          batch.add(next);
          queue.drainTo(batch);
          bh.consume(batch);
          batch.clear();
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
