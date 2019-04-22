package net.plaria.messaging.bungeecordexample.injection;

import com.google.inject.AbstractModule;
import net.plaria.messaging.client.MessagingClient;
import net.plaria.messaging.client.MessagingService;

import java.net.InetSocketAddress;

public class MessagingServiceInjector extends AbstractModule {

  private MessagingServiceInjector() {}

  @Override
  protected void configure() {
    this.bind(MessagingService.class)
        .toInstance(
            MessagingClient.create(InetSocketAddress.createUnresolved("localhost", 6565))
                .connect()
                .createCached());
  }

  public static MessagingServiceInjector create() {
    return new MessagingServiceInjector();
  }
}
