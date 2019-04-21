package net.plaria.messaging.client;

import com.google.common.util.concurrent.ListenableFuture;
import net.plaria.messaging.proto.Messaging;

public interface MessagingService {

  ListenableFuture<Messaging.MessageResponse> findMessageByKey(String key);
}
