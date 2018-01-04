package eu.funnetzwerk.funcity.cmd;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import eu.funnetzwerk.funcity.FunCity;
import eu.funnetzwerk.funcity.var.TeleportPoint;

public class TaxiCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		
		if(sender instanceof Player) {
			
			Player p = (Player) sender;
			
			if(p.hasPermission("FunCity.Taxi.Use")) {
				
				int size = FunCity.getTaxiClass().getStops().size();
				Inventory menu = Bukkit.createInventory(null, calculateInventorySize(size), "§e§lTaxi-Haltestellen");
				
				for(TeleportPoint tp : FunCity.getTaxiClass().getStops()) {
					
					ItemStack item = new ItemStack(Material.MINECART, 1, (short) 0);
					ItemMeta meta = item.getItemMeta();
					meta.setDisplayName(tp.getDisplayName());
					ArrayList<String> lore = new ArrayList<String>();
					lore.add("§7Klicke zum teleportieren!");
					lore.add("§6Preis: §6" + FunCity.getTaxiClass().getCostPerTeleport());
					meta.setLore(lore);
					item.setItemMeta(meta);
					
					menu.addItem(item);
					
				}
				
				p.openInventory(menu);
				
			} else {
				p.sendMessage("§7Du kannst kein Taxi verwenden.");
			}
			
		}
		
		return true;
	}
	
	private int calculateInventorySize(int size) {
		
		if(size < 10) {
			return 1*9;
		} else if(size > 9 && size < 2*9) {
			return 2*9;
		} else if(size > 9 && size < 3*9) {
			return 3*9;
		} else if(size > 9 && size < 4*9) {
			return 4*9;
		} else if(size > 9 && size < 5*9) {
			return 5*9;
		}
		
		return 6*9;
	}

}
