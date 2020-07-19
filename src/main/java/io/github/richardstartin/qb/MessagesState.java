package io.github.richardstartin.qb;

import org.openjdk.jmh.annotations.*;

import java.util.Arrays;

@State(Scope.Thread)
public class MessagesState {

  @Param({"0", "5", "10", "20"})
  int backoff;

  Object[] inputs = new Object[4096];
  int pos = 0;

  @Setup(Level.Trial)
  public void preallocateMessages() {
    Arrays.setAll(inputs, i -> new Object());
  }

  public Object nextMessage() {
    return inputs[pos++ & (inputs.length - 1)];
  }
}
