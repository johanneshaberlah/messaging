package net.plaria.messaging.bungeecordexample.command;

import com.google.inject.Inject;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.plaria.messaging.client.MessagingService;
import net.plaria.messaging.proto.Messaging;
import net.plaria.messaging.bungeecordexample.FutureCallbacks;

public class ShowMessageCommand extends Command {

  @Inject private MessagingService messagingService;

  public ShowMessageCommand() {
    super("showmessage");
  }

  @Override
  public void execute(CommandSender sender, String[] args) {
    if (args.length == 0) {
      sender.sendMessage("Bitte verwende /showmessage <key>");
      return;
    }
    String key = args[0];
    FutureCallbacks.create(
        this.messagingService.findMessageByKey(key),
        messageResponse -> {
          if (messageResponse.getErrorState().equals(Messaging.ErrorState.ERROR)) {
            sender.sendMessage("Diese Nachricht existiert nicht!");
            return;
          }
          sender.sendMessage("Nachricht: " + ChatColor.translateAlternateColorCodes('&', messageResponse.getMessage().getMessage()));
        },
        Throwable::printStackTrace);
  }
}
