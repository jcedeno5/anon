package io.github.infintyz.anonymous

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer

class CommandHandler(private val instance: Anonymous) : CommandExecutor {

    override fun onCommand(sender: CommandSender?, cmd: Command?, label: String?, args: Array<out String>?): Boolean {
        when(cmd!!.name){
            "anonymous"->{


                return true
            }
        }
        return false
    }

    private fun handleDisable(){
        val packet = instance.scoreboardUtils.removeTeam("others")

        Bukkit.getOnlinePlayers().parallelStream().forEach {
            (it as CraftPlayer).handle.playerConnection.sendPacket(packet)
            instance.gameProfileHandler.map.forEach {


            }

        }

    }

    private fun handleEnable(){

    }
}