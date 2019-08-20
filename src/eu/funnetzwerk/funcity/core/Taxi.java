package eu.funnetzwerk.funcity.core;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import eu.funnetzwerk.funcity.FunCity;
import eu.funnetzwerk.funcity.var.TeleportPoint;

public class Taxi {
	
	private ArrayList<TeleportPoint> taxi_stops = new ArrayList<TeleportPoint>();
	
	private ArrayList<Player> taxi_cooldown = new ArrayList<Player>();
	
	private int costPerTeleport = 1;
	private int cooldown = 0;
	
	public Taxi() {
		
		FileConfiguration cfg = FunCity.getCfg_taxi();
		
		for(String s : cfg.getConfigurationSection("").getKeys(false)) {
			
			Location current;
			TeleportPoint tPoint;
			
			double x = cfg.getDouble(s + ".x");
			double y = cfg.getDouble(s + ".y");
			double z = cfg.getDouble(s + ".z");
			float yaw = (float) cfg.getDouble(s + ".yaw");
			float pitch = (float) cfg.getDouble(s + ".pitch");
			String worldname = cfg.getString(s + ".world");
			World w = Bukkit.getWorld(worldname);
			
			current = new Location(w, x, y, z, yaw, pitch);
			tPoint = new TeleportPoint(firstCaps(s), current);
			
			
			tPoint.setAlwaysOnTop(false);
			
			taxi_stops.add(tPoint);
		}
		
		costPerTeleport = FunCity.getCfg_main().getInt("options.taxi.costPerUse");
		cooldown = FunCity.getCfg_main().getInt("options.taxi.cooldown");
		
		System.out.println("TAXI_STOPS="+taxi_stops.size());
		
	}
	
	public void reload() {
		
		taxi_stops.clear();
		
		FileConfiguration cfg = FunCity.getCfg_taxi();
		
		for(String s : cfg.getConfigurationSection("").getKeys(false)) {
			
			Location current;
			TeleportPoint tPoint;
			
			double x = cfg.getDouble(s + ".x");
			double y = cfg.getDouble(s + ".y");
			double z = cfg.getDouble(s + ".z");
			float yaw = (float) cfg.getDouble(s + ".yaw");
			float pitch = (float) cfg.getDouble(s + ".pitch");
			String worldname = cfg.getString(s + ".world");
			World w = Bukkit.getWorld(worldname);
			
			current = new Location(w, x, y, z, yaw, pitch);
			tPoint = new TeleportPoint(firstCaps(s), current);
			
			
			tPoint.setAlwaysOnTop(false);
			
			taxi_stops.add(tPoint);
		}
		
		costPerTeleport = FunCity.getCfg_main().getInt("options.taxi.costPerUse");
		cooldown = FunCity.getCfg_main().getInt("options.taxi.cooldown");
		
	}
	
	public void addPoint(String name, Location loc) {
		
		FileConfiguration cfg = FunCity.getCfg_taxi();
		
		name = name.toLowerCase();
		
		cfg.set(name + ".x", loc.getX());
		cfg.set(name + ".y", loc.getY());
		cfg.set(name + ".z", loc.getZ());
		
		cfg.set(name + ".yaw", loc.getYaw());
		cfg.set(name + ".pitch", loc.getPitch());
		
		cfg.set(name + ".world",loc.getWorld().getName());
		
		FunCity.setCfg_taxi(cfg);
		
		reload();
		
	}
	
	public void delPoint(String name) {
		
		FileConfiguration cfg = FunCity.getCfg_taxi();
		
		cfg.set(name.toLowerCase(), null);
		
		FunCity.setCfg_taxi(cfg);
		
		reload();
		
	}
	
	public void teleport(Player p, TeleportPoint tp) {
		
		if(canTeleport(p)) {
		
			tp.teleportEntity(p);
			
			if(cooldown != 0) {
				
				taxi_cooldown.add(p);
				
				Bukkit.getScheduler().scheduleSyncDelayedTask(FunCity.getPlugin(), new Runnable() {

					@Override
					public void run() {
						taxi_cooldown.remove(p);			
					}
					
				}, cooldown*20);
				
			}	
			
		} else {
			System.err.println("Failed to Teleport User to Taxi-Stop! [U=" + p.getName() + ",N=" + tp.getDisplayName() + ",ERR=COOLDOWN]");
		}
		
	}

	public boolean canTeleport(Player p) {
		
		if(!taxi_cooldown.contains(p)) {
			return true;
		}
		
		return false;
	}
	
	public ArrayList<TeleportPoint> getStops() {
		return taxi_stops;
	}
	
	private String firstCaps(String text) {
		
		String mod_text;
		String firstletter = text.substring(0, 1);
		
		mod_text = firstletter.toUpperCase() + text.substring(1, text.length());		
		return mod_text;
	}

	public int getCostPerTeleport() {
		return costPerTeleport;
	}

}
