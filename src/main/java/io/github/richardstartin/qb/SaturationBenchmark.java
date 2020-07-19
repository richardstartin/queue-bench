package io.github.richardstartin.qb;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.CompilerControl;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.infra.Blackhole;

public class SaturationBenchmark {

  @Benchmark
  @Threads(1)
  @CompilerControl(CompilerControl.Mode.DONT_INLINE)
  public void offerArrayBlockingQueue1(ArrayBlockingQueueState qState, MessagesState mState, Counters counters) {
    offerToFixedCapacityQueue(qState, mState, counters);
  }

  @Benchmark
  @Threads(2)
  @CompilerControl(CompilerControl.Mode.DONT_INLINE)
  public void offerArrayBlockingQueue2(ArrayBlockingQueueState qState, MessagesState mState, Counters counters) {
    offerToFixedCapacityQueue(qState, mState, counters);
  }

  @Benchmark
  @Threads(4)
  @CompilerControl(CompilerControl.Mode.DONT_INLINE)
  public void offerArrayBlockingQueue4(ArrayBlockingQueueState qState, MessagesState mState, Counters counters) {
    offerToFixedCapacityQueue(qState, mState, counters);
  }


  @Benchmark
  @Threads(8)
  @CompilerControl(CompilerControl.Mode.DONT_INLINE)
  public void offerArrayBlockingQueue8(ArrayBlockingQueueState qState, MessagesState mState, Counters counters) {
    offerToFixedCapacityQueue(qState, mState, counters);
  }

  @Benchmark
  @Threads(1)
  @CompilerControl(CompilerControl.Mode.DONT_INLINE)
  public void offerManyToOneArrayQueue1(ManyToOneConcurrentArrayQueueState qState, MessagesState mState, Counters counters) {
    offerToFixedCapacityQueue(qState, mState, counters);
  }

  @Benchmark
  @Threads(2)
  @CompilerControl(CompilerControl.Mode.DONT_INLINE)
  public void offerManyToOneArrayQueue2(ManyToOneConcurrentArrayQueueState qState, MessagesState mState, Counters counters) {
    offerToFixedCapacityQueue(qState, mState, counters);
  }

  @Benchmark
  @Threads(4)
  @CompilerControl(CompilerControl.Mode.DONT_INLINE)
  public void offerManyToOneArrayQueue4(ManyToOneConcurrentArrayQueueState qState, MessagesState mState, Counters counters) {
    offerToFixedCapacityQueue(qState, mState, counters);
  }


  @Benchmark
  @Threads(8)
  @CompilerControl(CompilerControl.Mode.DONT_INLINE)
  public void offerManyToOneArrayQueue8(ManyToOneConcurrentArrayQueueState qState, MessagesState mState, Counters counters) {
    offerToFixedCapacityQueue(qState, mState, counters);
  }

  @Benchmark
  @Threads(1)
  @CompilerControl(CompilerControl.Mode.DONT_INLINE)
  public void offerLinkedBlockingQueue1(LinkedBlockingQueueState qState, MessagesState mState, Counters counters) {
    offerToFixedCapacityQueue(qState, mState, counters);
  }

  @Benchmark
  @Threads(2)
  @CompilerControl(CompilerControl.Mode.DONT_INLINE)
  public void offerLinkedBlockingQueue2(LinkedBlockingQueueState qState, MessagesState mState, Counters counters) {
    offerToFixedCapacityQueue(qState, mState, counters);
  }

  @Benchmark
  @Threads(4)
  @CompilerControl(CompilerControl.Mode.DONT_INLINE)
  public void offerLinkedBlockingQueue4(LinkedBlockingQueueState qState, MessagesState mState, Counters counters) {
    offerToFixedCapacityQueue(qState, mState, counters);
  }


  @Benchmark
  @Threads(8)
  @CompilerControl(CompilerControl.Mode.DONT_INLINE)
  public void offerLinkedBlockingQueue8(LinkedBlockingQueueState qState, MessagesState mState, Counters counters) {
    offerToFixedCapacityQueue(qState, mState, counters);
  }


  @Benchmark
  @Threads(1)
  @CompilerControl(CompilerControl.Mode.DONT_INLINE)
  public void offerCompoundQueue1(CompoundQueueState qState, MessagesState mState, Counters counters) {
    offerToFixedCapacityQueue(qState, mState, counters);
  }

  @Benchmark
  @Threads(2)
  @CompilerControl(CompilerControl.Mode.DONT_INLINE)
  public void offerCompoundQueue2(CompoundQueueState qState, MessagesState mState, Counters counters) {
    offerToFixedCapacityQueue(qState, mState, counters);
  }

  @Benchmark
  @Threads(4)
  @CompilerControl(CompilerControl.Mode.DONT_INLINE)
  public void offerCompoundQueue4(CompoundQueueState qState, MessagesState mState, Counters counters) {
    offerToFixedCapacityQueue(qState, mState, counters);
  }


//  @Benchmark
//  @Threads(8)
//  @CompilerControl(CompilerControl.Mode.DONT_INLINE)
//  public void offerCompoundQueue8(CompoundQueueState qState, MessagesState mState, Counters counters) {
//    offerToFixedCapacityQueue(qState, mState, counters);
//  }


  @Benchmark
  @Threads(1)
  @CompilerControl(CompilerControl.Mode.DONT_INLINE)
  public void offerDisruptor1(DisruptorState qState, MessagesState mState, Counters counters) {
    offerToFixedCapacityQueue(qState, mState, counters);
  }

  @Benchmark
  @Threads(2)
  @CompilerControl(CompilerControl.Mode.DONT_INLINE)
  public void offerDisruptor2(DisruptorState qState, MessagesState mState, Counters counters) {
    offerToFixedCapacityQueue(qState, mState, counters);
  }

  @Benchmark
  @Threads(4)
  @CompilerControl(CompilerControl.Mode.DONT_INLINE)
  public void offerDisruptor4(DisruptorState qState, MessagesState mState, Counters counters) {
    offerToFixedCapacityQueue(qState, mState, counters);
  }


  @Benchmark
  @Threads(8)
  @CompilerControl(CompilerControl.Mode.DONT_INLINE)
  public void offerDisruptor8(DisruptorState qState, MessagesState mState, Counters counters) {
    offerToFixedCapacityQueue(qState, mState, counters);
  }



  private void offerToFixedCapacityQueue(DroppingOffer qState, MessagesState mState, Counters counters) {
    if (qState.offer(mState.nextMessage())) {
      counters.offers++;
    } else {
      counters.drops++;
    }
    Blackhole.consumeCPU(mState.backoff);
  }
}
