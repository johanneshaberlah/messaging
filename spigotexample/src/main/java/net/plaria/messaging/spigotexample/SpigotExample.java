package net.plaria.messaging.spigotexample;

import com.google.inject.Guice;
import com.google.inject.Injector;
import net.plaria.messaging.spigotexample.command.ShowMessageCommand;
import net.plaria.messaging.spigotexample.injection.MessagingServiceInjector;
import org.bukkit.plugin.java.JavaPlugin;

public class SpigotExample extends JavaPlugin {

  private Injector injector;

  @Override
  public void onEnable() {
    this.injector = Guice.createInjector(MessagingServiceInjector.create());
    this.getCommand("showmessage").setExecutor(this.injector.getInstance(ShowMessageCommand.class));
  }

  @Override
  public void onDisable() {
    super.onDisable();
  }
}
