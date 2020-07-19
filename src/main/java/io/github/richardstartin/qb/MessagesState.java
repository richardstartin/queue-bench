package io.github.richardstartin.qb;

import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import java.util.Arrays;

@State(Scope.Thread)
public class MessagesState {

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
