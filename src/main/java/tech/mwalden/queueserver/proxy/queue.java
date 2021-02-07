package tech.mwalden.queueserver.proxy;

import java.util.ArrayList;
import java.util.Iterator;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;

public class queue {

    private static ArrayList < ProxiedPlayer > QueuedPlayers = new ArrayList < ProxiedPlayer > ();
    private static ArrayList < ProxiedPlayer > PriorityQueuedPlayers = new ArrayList < ProxiedPlayer > ();
    private static int maxPlayers = 0;
    private static int joinRatioAt = 0;
    private static ServerInfo mainServer = null;

    public void setMaxPlayers(int max) {
        maxPlayers = max;
    }
    public void setMainServer(ServerInfo server) {
        mainServer = server;
    }
    
    private static void handleNonPriorityQueue() {
    	Iterator<ProxiedPlayer> i = QueuedPlayers.iterator();
		while (i.hasNext()) {
			ProxiedPlayer player = i.next();
	        int positionInQueue = QueuedPlayers.indexOf(player);
	        if (positionInQueue == 0 && mainServer.getPlayers().size() < maxPlayers) {
	                TextComponent message = new TextComponent("You're being connected, please wait...");
	                message.setColor(net.md_5.bungee.api.ChatColor.GOLD);
	
	                player.sendMessage(message);
	                player.connect(mainServer);
	                i.remove();
	        } else {
	            TextComponent addedMessage = new TextComponent("You are " + QueuedPlayers.indexOf(player) + " in the queue.");
	            addedMessage.setColor(net.md_5.bungee.api.ChatColor.GOLD);
	
	            player.sendMessage(addedMessage);
	        }
		}
    }
    private static void handlePriorityQueue() {
    	Iterator<ProxiedPlayer> i = PriorityQueuedPlayers.iterator();
		while (i.hasNext()) {
			ProxiedPlayer player = i.next();
	        int positionInQueue = PriorityQueuedPlayers.indexOf(player);
	        if (positionInQueue == 0 && mainServer.getPlayers().size() < maxPlayers) {
	                TextComponent message = new TextComponent("You're being connected, please wait...");
	                message.setColor(net.md_5.bungee.api.ChatColor.GOLD);
	
	                player.sendMessage(message);
	                player.connect(mainServer);
	                i.remove();
	        } else {
	            TextComponent addedMessage = new TextComponent("You are " + PriorityQueuedPlayers.indexOf(player) + " in the queue.");
	            addedMessage.setColor(net.md_5.bungee.api.ChatColor.GOLD);
	
	            player.sendMessage(addedMessage);
	        }
		}
    }

    public static void updateQueue() {
    	if (PriorityQueuedPlayers.size() == 0 ) {
    		handleNonPriorityQueue();
    	}else {
    		joinRatioAt = joinRatioAt+1;
    		if (joinRatioAt == 3) {
    			joinRatioAt = 0;
    			handleNonPriorityQueue();
    		}else {
    			handlePriorityQueue();
    		}
    	}
    	

    }

    public static void AddPlayer(ProxiedPlayer player, Server server) {
    	if (player.hasPermission("queue.priority") == true) {
    		PriorityQueuedPlayers.add(player);
	        TextComponent addedMessage = new TextComponent("You are " + PriorityQueuedPlayers.indexOf(player) + " in the queue.");
	        addedMessage.setColor(net.md_5.bungee.api.ChatColor.GOLD);
	
	        player.sendMessage(addedMessage);
	        updateQueue();
    	}else {
	        QueuedPlayers.add(player);
	        TextComponent addedMessage = new TextComponent("You are " + QueuedPlayers.indexOf(player) + " in the queue.");
	        addedMessage.setColor(net.md_5.bungee.api.ChatColor.GOLD);
	
	        player.sendMessage(addedMessage);
	        updateQueue();
    	}
    }
    
    public static void HandleLeaveEvent(ProxiedPlayer player, Server server) {
        String serverName = server.getInfo().getName();
        if (serverName == "queue") {
            QueuedPlayers.remove(player);
        }
        updateQueue();
    }


}