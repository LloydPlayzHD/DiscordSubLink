package eu.lloydplayzhd.sublink;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scheduler.BukkitRunnable;

public class Main extends JavaPlugin implements PluginMessageListener {
  private static Main instance;
  
  public Main () {
    instance = this;
  }
  
  public static Main getInstance() {
    return instance;
  }
  
  public void onEnable() {
    getServer().getMessenger().registerIncomingPluginChannel((Plugin)this, "discord:link", this);
  }
  
  public void onPluginMessageReceived(String channel, final Player player, byte[] bytes) {
    if (!channel.equalsIgnoreCase("discord:link"))
      return; 
    ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
    String subChannel = in.readUTF();
    if (subChannel.equalsIgnoreCase("Command")) {
      final String data1 = in.readUTF();
      (new BukkitRunnable() {
          public void run() {
            Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getConsoleSender(), data1.replace("%player%", player.getName()));
          }
        }).runTaskLater((Plugin)this, 20L);
    } 
  }
}
