package io.github.infintyz.anonymous

import net.md_5.bungee.api.ChatColor
import net.minecraft.server.v1_7_R4.PacketPlayOutScoreboardTeam
import net.minecraft.server.v1_7_R4.Scoreboard
import net.minecraft.server.v1_7_R4.ScoreboardTeam
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer
import org.bukkit.entity.Player
import java.util.*
import org.bukkit.WorldCreator.name




class ScoreboardUtils {
    fun createTeamAndAddSelf(player: Player, teamName: String, prefix: String = "&a", suffix: String = "&c") {
        //Create a fake team creation packet
        val packet = PacketPlayOutScoreboardTeam()
        setField(packet, "a", teamName)
        setField(packet, "b", "")
        setField(packet, "c", ChatColor.translateAlternateColorCodes('&', prefix))// Team Prefix
        setField(packet, "d", ChatColor.translateAlternateColorCodes('&', suffix))//Team Suffix
        setField(packet, "g", 0)
        setField(packet, "f", 0)
        //Create a fake user join team packet
        val packet2 = PacketPlayOutScoreboardTeam(
            ScoreboardTeam(Scoreboard(), teamName),
            Collections.singleton(player.name),
            3
        )

        //Obtain the user's connection and send the packet
        val connection = (player as CraftPlayer).handle.playerConnection
        connection.sendPacket(packet)
        connection.sendPacket(packet2)
    }

    fun createTeam(player: Player, teamName: String, prefix: String = "&a", suffix: String = "&c") {
        val packet = PacketPlayOutScoreboardTeam()
        setField(packet, "a", teamName)
        setField(packet, "b", "")
        setField(packet, "c", ChatColor.translateAlternateColorCodes('&', prefix))// Team Prefix
        setField(packet, "d", ChatColor.translateAlternateColorCodes('&', suffix))//Team Suffix
        setField(packet, "g", 0)
        setField(packet, "f", 0)

        (player as CraftPlayer).handle.playerConnection.sendPacket(packet)
    }

    fun removeTeam(teamName: String): PacketPlayOutScoreboardTeam {
        val packet = PacketPlayOutScoreboardTeam()
        setField(packet, "a", teamName)
        setField(packet, "f", 1)
        return packet
    }

    //Self explanatory function
    fun addUserToFakeTeam(player: Player, playerToBeAdded: String, teamName: String) {
        val packet = PacketPlayOutScoreboardTeam(
            ScoreboardTeam(Scoreboard(), teamName),
            Collections.singleton(playerToBeAdded),
            3
        )
        (player as CraftPlayer).handle.playerConnection.sendPacket(packet)
    }

    fun addMultipleUsers(player: Player, playersToBeAdded: List<String>, teamName: String) {
        val packet = PacketPlayOutScoreboardTeam(
            ScoreboardTeam(Scoreboard(), teamName),
            playersToBeAdded,
            3
        )
        (player as CraftPlayer).handle.playerConnection.sendPacket(packet)

    }

    //Names are self explanatory
    fun removeUserFromFakeTeam(player: Player, toBeRemoved: String, teamName: String) {
        val packet =
            PacketPlayOutScoreboardTeam(ScoreboardTeam(Scoreboard(), teamName), Collections.singleton(toBeRemoved), 4)
        (player as CraftPlayer).handle.playerConnection.sendPacket(packet)
    }

    fun setField(edit: Any, fieldName: String, value: Any) {
        try {
            val field = edit.javaClass.getDeclaredField(fieldName)
            field.isAccessible = true
            field.set(edit, value)
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }

    }
}