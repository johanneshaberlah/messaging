package net.plaria.messaging.bungeecordexample;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import javax.annotation.Nullable;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class FutureCallbacks {

  public static <T> void create(
      ListenableFuture<T> listenableFuture,
      Consumer<T> consumer,
      Consumer<Throwable> throwableConsumer) {
    Futures.addCallback(
        listenableFuture,
        new FutureCallback<T>() {
          @Override
          public void onSuccess(@Nullable T result) {
            consumer.accept(result);
          }

          @Override
          public void onFailure(Throwable t) {
            throwableConsumer.accept(t);
          }
        }, Executors.newSingleThreadExecutor());
  }
}
