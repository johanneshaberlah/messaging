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
    System.out.println("Connecting to " + inetSocketAddress.getHostName() + "...");
    this.channel =
        NettyChannelBuilder.forAddress(
                this.inetSocketAddress.getHostName(), this.inetSocketAddress.getPort())
            .usePlaintext()
            .keepAliveWithoutCalls(true)
            .build();
    this.messagingService = GRpcMessagingService.create(channel);
    System.out.println(
        "Connected to "
            + this.inetSocketAddress.getHostName()
            + " on "
            + this.inetSocketAddress.getPort()
            + ".");
    return this;
  }

  public ManagedChannel getChannel() {
    return channel;
  }

  public MessagingService getMessagingService() {
    return messagingService;
  }

  public static MessagingClient create(InetSocketAddress inetSocketAddress) {
    Preconditions.checkNotNull(inetSocketAddress);
    return new MessagingClient(inetSocketAddress);
  }
}
