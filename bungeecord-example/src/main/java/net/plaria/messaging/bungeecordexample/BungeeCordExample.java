package net.plaria.messaging.bungeecordexample;

import com.google.inject.Guice;
import com.google.inject.Injector;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import net.plaria.messaging.client.MessagingService;
import net.plaria.messaging.bungeecordexample.command.ShowMessageCommand;
import net.plaria.messaging.bungeecordexample.injection.MessagingServiceInjector;

public class BungeeCordExample extends Plugin {

  private Injector injector;

  @Override
  public void onEnable() {
    this.injector = Guice.createInjector(MessagingServiceInjector.create());
    PluginManager pluginManager = this.getProxy().getPluginManager();
    pluginManager.registerCommand(this, this.injector.getInstance(ShowMessageCommand.class));
  }

  @Override
  public void onDisable() {
    this.injector.getInstance(MessagingService.class).shutdown();
  }
}
