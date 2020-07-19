package io.github.richardstartin.qb;

import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

@State(Scope.Benchmark)
public class QueueSizeState {
  @Param({"256", "1024", "2048", "4096"})
  int queueSize;
}
