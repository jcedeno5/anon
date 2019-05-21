package io.github.infintyz.anonymous

import org.bukkit.Bukkit
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerKickEvent
import org.bukkit.event.player.PlayerQuitEvent

class JoinHandler(private val instance: Anonymous) : Listener {

    @EventHandler
    fun onJoin(event: PlayerJoinEvent){
        if(!instance.enabled)return
        val player = event.player

        instance.scoreboardUtils.createTeamAndAddSelf(player, "own")
        instance.scoreboardUtils.createTeam(player, "others", "&c&k")

        val map = mutableListOf<String>()

        Bukkit.getOnlinePlayers().stream()
            .filter { it != player }
            .forEach {
                instance.scoreboardUtils.addUserToFakeTeam(it, player.name, "others")
                instance.gameProfileHandler.disguise(it)
                map.add(it.name)
            }

        if(map.isEmpty())return
        instance.scoreboardUtils.addMultipleUsers(player, map, "others")
        instance.gameProfileHandler.disguise(player)
    }

    fun onLeave(player:Player){
        if(!instance.enabled)return
        Bukkit.getOnlinePlayers().forEach { instance.scoreboardUtils.removeUserFromFakeTeam(it, player.name, "others") }
    }

    @EventHandler
    fun onDisconnect(event: PlayerQuitEvent){
        onLeave(event.player)
    }
    @EventHandler
    fun onKick(event: PlayerKickEvent){
        onLeave(event.player)
    }
}