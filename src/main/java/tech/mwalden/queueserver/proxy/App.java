package tech.mwalden.queueserver.proxy;

import java.util.TimerTask;

import net.md_5.bungee.api.Callback;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.plugin.Plugin;

public class App extends Plugin {
    
	public queue queue = new queue();
	
	@Override
	public void onEnable() {
        getProxy().getPluginManager().registerListener(this, new Handler());
        queue.setMainServer(getProxy().getServersCopy().get("main"));
        
        java.util.Timer t = new java.util.Timer();
        t.schedule(new TimerTask() {
                    @Override
                    public void run() {
                    	tech.mwalden.queueserver.proxy.queue.updateQueue();

                    }
                }, 5000, 5000);
        
        
	    getProxy().getServersCopy().get("main").ping(new Callback<ServerPing>() {
	    	@Override
            public void done(ServerPing result, Throwable error) {

            	int maxPlayers = result.getPlayers().getMax();
            	queue.setMaxPlayers(maxPlayers);
            }
        });
	    
	    
        
	}
	
}
