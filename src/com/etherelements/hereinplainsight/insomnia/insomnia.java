package com.etherelements.hereinplainsight.insomnia;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class insomnia extends JavaPlugin implements Listener {
	PluginDescriptionFile pdfFile;
	
	public void onEnable(){
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this, this);
		
		pdfFile = getDescription();
		System.out.println("[" + pdfFile.getName() + "] v." + pdfFile.getVersion() + ": Coffee found.");
	}
	
	public void onDisable(){
		System.out.println("[" + pdfFile.getName() + "] Coffee's empty.");
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		return false;
	}

	@EventHandler
	public void onPlayerBedEnter(PlayerBedEnterEvent sleep){
		List<Player> worldPlayers = sleep.getPlayer().getWorld().getPlayers();
		Player[] onlinePlayers = Bukkit.getOnlinePlayers();
		List<Player> players = new LinkedList<Player>();
		for (int x = 0; x < worldPlayers.size(); x++)
			for (int y = 0; y < onlinePlayers.length; y++)
				if (worldPlayers.get(x).equals(onlinePlayers[y])){
					players.add(worldPlayers.get(x));
				}
		if (players.size() == 1)
			return;
		String sleeping = "";
		String notsleeping = "";
		for (int i = 0; i < players.size(); i++){
			if (players.get(i).isSleeping() || (players.get(i) == sleep.getPlayer())){
				if (sleeping.length() <= 0){
					sleeping = sleeping + players.get(i).getName();
				}else{
					sleeping = sleeping + ", " + players.get(i).getName();
				}
			}else{
				if (notsleeping.length() <= 0){
					notsleeping = notsleeping + players.get(i).getName();
				}else{
					notsleeping = notsleeping + ", " + players.get(i).getName();
				}
			}
		}
		for (int i = 0; i < players.size(); i++){
			players.get(i).sendMessage(ChatColor.GRAY + "Sleeping players: " + ChatColor.GREEN + sleeping + ((sleeping.length() == 0 || notsleeping.length() == 0) ? "" : ", ") + ChatColor.YELLOW + notsleeping);
		}
	}
}
