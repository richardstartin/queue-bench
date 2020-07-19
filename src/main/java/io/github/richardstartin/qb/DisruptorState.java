package io.github.richardstartin.qb;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

@State(Scope.Benchmark)
public class DisruptorState extends QueueSizeState  implements DroppingOffer {


  Disruptor<Message> disruptor;

  @Override
  public boolean offer(Object m) {
    return disruptor.getRingBuffer().tryPublishEvent(MessageTranslator.INSTANCE, m);
  }

  @Setup(Level.Iteration)
  public void createDisruptor(Blackhole bh) {
    disruptor = new Disruptor<>(Message.FACTORY, queueSize, DaemonThreadFactory.INSTANCE, ProducerType.MULTI, new BlockingWaitStrategy());
    disruptor.handleEventsWith((message, l, b) -> bh.consume(message.m));
    disruptor.start();
  }

  @TearDown(Level.Iteration)
  public void haltDisruptor() {
    disruptor.halt();
  }


  public static class Message {

    public static final EventFactory<Message> FACTORY = Message::new;

    Object m;
  }


  public static class MessageTranslator implements EventTranslatorOneArg<Message, Object> {

    public static MessageTranslator INSTANCE = new MessageTranslator();

    @Override
    public void translateTo(Message message, long l, Object o) {
      message.m = o;
    }
  }


}
