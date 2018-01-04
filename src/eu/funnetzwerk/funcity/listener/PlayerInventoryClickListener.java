package eu.funnetzwerk.funcity.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;

import eu.funnetzwerk.funcity.FunCity;
import eu.funnetzwerk.funcity.var.TeleportPoint;
import net.milkbowl.vault.economy.EconomyResponse;

public class PlayerInventoryClickListener implements Listener {

	@EventHandler
	public void onClickTaxiItem(InventoryClickEvent e) {
		
		Player p = (Player) e.getWhoClicked();
		
		
			
			if(FunCity.getTaxiClass().canTeleport(p)) {
				
				if(e.getInventory().getName().equals("§e§lTaxi-Haltestellen")) {
					
					e.setCancelled(true);
					
					if(e.getSlotType() != SlotType.OUTSIDE) {
						
						if(e.getCurrentItem() != null) {
							
							if(e.getCurrentItem().getType() == Material.MINECART) {
							
								if(e.getCurrentItem().hasItemMeta()) {
								
									for(TeleportPoint tp : FunCity.getTaxiClass().getStops()) {
										
										if(e.getCurrentItem().getItemMeta().getDisplayName() == tp.getDisplayName()) {
										
											if(p.hasPermission("FunCity.Taxi.Use")) {
											
												EconomyResponse r = FunCity.econ.withdrawPlayer(Bukkit.getOfflinePlayer(p.getUniqueId()), FunCity.getTaxiClass().getCostPerTeleport());
												
												if(r.transactionSuccess()) {
													p.closeInventory();
													tp.teleportEntity(p);
													p.sendMessage("§7Du wurdest zur Haltestelle §6" + tp.getDisplayName() + "§7für §6" + FunCity.getTaxiClass().getCostPerTeleport() + "§7 teleportiert!");
												} else {
													p.closeInventory();
													p.sendMessage("§7Du besitzt nicht genügend Geld!");
												}
												
											} else {
												p.sendMessage("�7Du kannst kein Taxi verwenden.");
											}
											
										}
										
									}
									
								}
								
							}
							
						}
	
					}
					
				}
				
			} else {
				p.closeInventory();
				p.sendMessage("�7Bitte warte einen Moment bevor du wieder ein Taxi verwendest!");
			}
			
		
		
	}
	
}
