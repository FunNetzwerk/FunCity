package eu.funnetzwerk.funcity.var;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

public class TeleportPoint {

	private Location location;
	private String displayName;
	
	private boolean alwaysTop = false;
	
	public TeleportPoint(String name, Location l){
		this.location = l;
		this.displayName = name;
	}
	
	public void teleportEntity(Entity e) {
		
		if(alwaysTop) {
			this.location.setY(location.getWorld().getHighestBlockYAt(location));
		}
		
		e.teleport(this.location);
	}
	
	public void setAlwaysOnTop(boolean a) {
		this.alwaysTop = a;
	}

	public String getDisplayName() {
		return displayName;
	}

}
