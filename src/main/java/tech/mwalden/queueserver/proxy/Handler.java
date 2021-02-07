package tech.mwalden.queueserver.proxy;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class Handler implements Listener {
	
    @EventHandler
    public void onPostLogin(PostLoginEvent event) {
    	ProxiedPlayer player = event.getPlayer();
    	Server server = player.getServer();
    	
    	queue.AddPlayer(player, server);
    }
    
    @EventHandler
    public void onPlayerDisconnectEvent(PlayerDisconnectEvent event) {
    	
    	ProxiedPlayer player = event.getPlayer();
    	Server server = player.getServer();
    	
    	queue.HandleLeaveEvent(player, server);
    	
    }
    
}
