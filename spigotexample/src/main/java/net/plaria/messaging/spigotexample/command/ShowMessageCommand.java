package net.plaria.messaging.spigotexample.command;

import com.google.inject.Inject;
import net.plaria.messaging.client.MessagingService;
import net.plaria.messaging.spigotexample.FutureCallbacks;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


public class ShowMessageCommand implements CommandExecutor {

  @Inject private MessagingService messagingService;

  public boolean onCommand(
      final CommandSender commandSender, final Command command, String s, String[] strings) {
    if (strings.length == 0) {
      commandSender.sendMessage("Bitte verwende /showmessage <key>");
      return true;
    }
    String key = strings[0];
    FutureCallbacks.create(this.messagingService.findMessageByKey(key), messageResponse -> {
      commandSender.sendMessage(messageResponse.getMessage().getMessage());
    }, Throwable::printStackTrace);
    return false;
  }
}
