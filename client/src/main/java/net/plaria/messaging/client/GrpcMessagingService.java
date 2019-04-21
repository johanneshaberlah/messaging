package net.plaria.messaging.client;

import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import io.grpc.ManagedChannel;
import net.plaria.messaging.proto.MessageServiceGrpc;
import net.plaria.messaging.proto.Messaging;

import java.util.concurrent.Executors;

public class GrpcMessagingService implements MessagingService {

  private final ListeningExecutorService listeningExecutorService =
      MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());

  private ManagedChannel managedChannel;
  private MessageServiceGrpc.MessageServiceFutureStub messageServiceFutureStub;

  private GrpcMessagingService(ManagedChannel managedChannel) {
    this.managedChannel = managedChannel;
    this.messageServiceFutureStub =
        MessageServiceGrpc.newFutureStub(managedChannel).withExecutor(listeningExecutorService);
  }

  @Override
  public ListenableFuture<Messaging.MessageResponse> findMessageByKey(String key) {
    return this.messageServiceFutureStub.findMessageByKey(Messaging.ProtoString.newBuilder().setValue(key).build());
  }

  public static GrpcMessagingService create(ManagedChannel managedChannel) {
    return new GrpcMessagingService(managedChannel);
  }
}
