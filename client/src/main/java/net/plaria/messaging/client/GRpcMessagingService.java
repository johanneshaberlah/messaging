package net.plaria.messaging.client;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import io.grpc.ManagedChannel;
import net.plaria.messaging.proto.MessageServiceGrpc;
import net.plaria.messaging.proto.Messaging;

import java.util.concurrent.Executors;

public class GRpcMessagingService implements MessagingService {

  private final ListeningExecutorService listeningExecutorService =
      MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());

  private ManagedChannel managedChannel;
  private MessageServiceGrpc.MessageServiceFutureStub messageServiceFutureStub;

  private GRpcMessagingService(ManagedChannel managedChannel) {
    this.managedChannel = managedChannel;
    this.messageServiceFutureStub =
        MessageServiceGrpc.newFutureStub(this.managedChannel)
            .withExecutor(listeningExecutorService);
  }


  @Override
  public ListenableFuture<Messaging.MessageResponse> findMessageByKey(String key) {
    return this.messageServiceFutureStub.findMessageByKey(
        Messaging.ProtoString.newBuilder().setValue(key).build());
  }

  @Override
  public void shutdown() {
    this.managedChannel.shutdown();
    this.listeningExecutorService.shutdown();
  }

  public static GRpcMessagingService create(ManagedChannel managedChannel) {
    return new GRpcMessagingService(managedChannel);
  }
}
