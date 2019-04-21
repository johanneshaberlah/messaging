package net.plaria.messaging.spigotexample.injection;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import net.plaria.messaging.client.MessagingClient;
import net.plaria.messaging.client.MessagingService;

import java.net.InetSocketAddress;

public class MessagingServiceInjector extends AbstractModule {

  private MessagingServiceInjector() {}

  @Override
  protected void configure() {}

  @Provides
  MessagingService messagingService() {
    return MessagingClient.create(InetSocketAddress.createUnresolved("localhost", 35743))
        .connect()
        .getMessagingService();
  }

  public static MessagingServiceInjector create() {
    return new MessagingServiceInjector();
  }
}
