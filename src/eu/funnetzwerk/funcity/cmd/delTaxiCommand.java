package eu.funnetzwerk.funcity.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import eu.funnetzwerk.funcity.FunCity;

public class delTaxiCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		
		if(sender instanceof Player) {
		
			Player p = (Player) sender;
		
			if(p.hasPermission("FunCity.Taxi.Admin.Set")) {
				
				if(args.length == 1) {
					
					String name = args[0];
					
					FunCity.getTaxiClass().delPoint(name);
					
					p.sendMessage("§7Haltestelle §6" + name + "§7 wurde entfernt!");
					
				} else {
					p.sendMessage("§cSyntax: /setTaxi <Name>");
				}
				
			} else {
				p.sendMessage("§7Du hast keine Berechtigung dies zu tun!");
			}
			
		}
		
		return true;
	}

}
