package eu.funnetzwerk.funcity;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import eu.funnetzwerk.funcity.cmd.TaxiCommand;
import eu.funnetzwerk.funcity.cmd.delTaxiCommand;
import eu.funnetzwerk.funcity.cmd.setTaxiCommand;
import eu.funnetzwerk.funcity.core.CmdBlocker;
import eu.funnetzwerk.funcity.core.Taxi;
import eu.funnetzwerk.funcity.listener.PlayerInventoryClickListener;
import net.milkbowl.vault.economy.Economy;

public class FunCity extends JavaPlugin {

	private static Plugin plugin;
	
	private static Taxi taxiClass;
	private CmdBlocker cmdBlocker;
	
	public static Economy econ = null;
	
	private static File file_main = new File("plugins/FunCity", "config.yml");
	private static FileConfiguration cfg_main = YamlConfiguration.loadConfiguration(file_main);
	
	private static File file_blockedcmd = new File("plugins/FunCity", "blocked_commands.yml");
	private static FileConfiguration cfg_blockedcmd = YamlConfiguration.loadConfiguration(file_blockedcmd);
	
	private static File file_taxi = new File("plugins/FunCity/locations", "taxistops.yml");
	private static FileConfiguration cfg_taxi = YamlConfiguration.loadConfiguration(file_taxi);
	
	@Override
	public void onEnable() {
		
		plugin = this;
		
		if(!file_main.exists()) {
			
			cfg_main.set("features.taxi", true);
			cfg_main.set("features.cmd_blocker", true);
				
			//TAXI OPTIONS
			cfg_main.set("options.taxi.costPerUse", 5);
			cfg_main.set("options.taxi.cooldown", 0);
			
			setCfg_main(cfg_main);
			
		} else { //IF IT'S A UPDATE
			
			FileConfiguration cfg = getCfg_main();
			
			cfg_main.set("features.cmd_blocker", true);
			
			setCfg_main(cfg);
			
		}
		
		
		System.out.println("FunCity loaded! Activating features...");
		
		
		if(PluginFeatures.CMD_BLOCKER.isActive()) {
			
			System.out.println("CMD Blocker => ENABLED");
			
			if(!file_blockedcmd.exists()) {
				
				try {
					file_blockedcmd.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
			
		} else {
			System.out.println("CMD Blocker => DISABLED");
		}
		
		if(PluginFeatures.Taxi.isActive()) {
		
			System.out.println("Taxis => ENABLED");
			
			if(!file_taxi.exists()) {
				
				try {
					file_taxi.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
			
		} else {
			System.out.println("Taxis => DISABLED");
		}
		
		
		//Register Classes
		taxiClass = new Taxi();
		cmdBlocker = new CmdBlocker();
		
		
		Bukkit.getPluginManager().registerEvents(new PlayerInventoryClickListener(), this);
		
		getCommand("taxi").setExecutor(new TaxiCommand());
		getCommand("settaxi").setExecutor(new setTaxiCommand());
		getCommand("deltaxi").setExecutor(new delTaxiCommand());
		
		setupEconomy();
		
	}
	
	public static Plugin getPlugin() {
		return plugin;
	}
	

	public static Taxi getTaxiClass() {
		return taxiClass;
	}

	public CmdBlocker getCmdBlockerClass() {
		return cmdBlocker;
	}
	
	
	public static FileConfiguration getCfg_taxi() {
		return cfg_taxi;
	}
	
	public static FileConfiguration getCfg_main() {
		return cfg_main;
	}
	
	public static FileConfiguration getCfg_blockedcmd() {
		return cfg_blockedcmd;
	}
	
	
	public static void setCfg_taxi(FileConfiguration cfg) {
		
		FunCity.cfg_taxi = cfg;
		
		try {
			cfg_taxi.save(FunCity.file_taxi);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public static void setCfg_main(FileConfiguration cfg_main) {
		FunCity.cfg_main = cfg_main;
		
		try {
			cfg_main.save(file_main);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public static void setCfg_blockedcmd(FileConfiguration cfg_blockedcmd) {
		FunCity.cfg_blockedcmd = cfg_blockedcmd;
		
		try {
			cfg_blockedcmd.save(file_blockedcmd);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            econ = economyProvider.getProvider();
        }

        return (econ != null);
    }
	
}
