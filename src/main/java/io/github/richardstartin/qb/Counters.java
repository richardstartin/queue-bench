package io.github.richardstartin.qb;

import org.openjdk.jmh.annotations.*;

@State(Scope.Thread)
@AuxCounters(AuxCounters.Type.EVENTS)
public class Counters {
  long offers;
  long drops;


  public long offers() {
    return offers;
  }

  public long drops() {
    return drops;
  }

  @Setup(Level.Iteration)
  public void reset() {
    offers = 0;
    drops = 0;
  }
}
