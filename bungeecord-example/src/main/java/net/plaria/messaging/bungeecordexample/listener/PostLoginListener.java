package net.plaria.messaging.bungeecordexample.listener;

import com.google.inject.Inject;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.plaria.messaging.bungeecordexample.FutureCallbacks;
import net.plaria.messaging.client.MessagingService;

public class PostLoginListener implements Listener {

  @Inject private MessagingService messagingService;

  @EventHandler
  public void onPostLogin(PostLoginEvent event){
    FutureCallbacks.create(this.messagingService.getMessage("greeting", event.getPlayer().getDisplayName()), s -> {
      event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', s));
    }, Throwable::printStackTrace);
  }

}
