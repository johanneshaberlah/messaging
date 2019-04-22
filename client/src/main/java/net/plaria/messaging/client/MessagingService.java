package net.plaria.messaging.client;

import com.google.common.util.concurrent.ListenableFuture;
import net.plaria.messaging.proto.Messaging;

import java.util.concurrent.ExecutionException;

public interface MessagingService {

  ListenableFuture<Messaging.MessageResponse> findMessageByKey(String key);

  ListenableFuture<String> getMessage(String key, String... args);

  @Deprecated
  String getMessageBlocking(String key, String... args)
      throws ExecutionException, InterruptedException;

  void shutdown();
}
