package net.plaria.messaging.client;

import com.google.common.base.Preconditions;
import io.grpc.ManagedChannel;
import io.grpc.netty.NettyChannelBuilder;

import java.net.InetSocketAddress;

public class MessagingClient {

  private final InetSocketAddress inetSocketAddress;
  private ManagedChannel channel;
  private MessagingService messagingService;

  private MessagingClient(InetSocketAddress inetSocketAddress) {
    this.inetSocketAddress = inetSocketAddress;
  }

  public MessagingClient connect() {
    this.channel =
        NettyChannelBuilder.forAddress(
                this.inetSocketAddress.getHostName(), this.inetSocketAddress.getPort())
            .usePlaintext()
            .keepAliveWithoutCalls(true)
            .build();
    return this;
  }

  public MessagingService createCached() {
    return CachedMessagingService.create(this.createdUncached());
  }

  public ManagedChannel getChannel() {
    return channel;
  }

  public MessagingService createdUncached() {
    return GRpcMessagingService.create(channel);
  }

  public static MessagingClient create(InetSocketAddress inetSocketAddress) {
    Preconditions.checkNotNull(inetSocketAddress);
    return new MessagingClient(inetSocketAddress);
  }
}
