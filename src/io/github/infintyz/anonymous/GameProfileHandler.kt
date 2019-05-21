package io.github.infintyz.anonymous

import net.minecraft.server.v1_7_R4.EntityPlayer
import net.minecraft.util.com.mojang.authlib.GameProfile
import net.minecraft.util.com.mojang.authlib.properties.Property
import org.bukkit.Bukkit
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer
import org.bukkit.entity.Player
import java.util.*

class GameProfileHandler {
    val map = mutableMapOf<UUID, Property>()

    fun disguise(player: Player){
        val entity = (player as CraftPlayer).handle
        val profile = entity.profile

        if(player.vehicle != null){
            player.vehicle.eject()
        }

        profile.properties.clear()
        profile.properties.put("textures", Property("textures", "eyJ0aW1lc3RhbXAiOjE1NTg0NTM4NTg5NzIsInByb2ZpbGVJZCI6Ijg2NjdiYTcxYjg1YTQwMDRhZjU0NDU3YTk3MzRlZWQ3IiwicHJvZmlsZU5hbWUiOiJTdGV2ZSIsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9kYzFjNzdjZThlNTQ5MjVhYjU4MTI1NDQ2ZWM1M2IwY2RkM2QwY2EzZGIyNzNlYjkwOGQ1NDgyNzg3ZWY0MDE2In0sIkNBUEUiOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS85NTNjYWM4Yjc3OWZlNDEzODNlNjc1ZWUyYjg2MDcxYTcxNjU4ZjIxODBmNTZmYmNlOGFhMzE1ZWE3MGUyZWQ2In19fQ==", ""))
        sendUpdate(entity, player)

    }

    fun unDisguiseAll(){
        Bukkit.getOnlinePlayers().parallelStream().forEach {
            val profile = (it as CraftPlayer).handle.profile
            profile.properties.clear()
        }
    }

    fun sendUpdate(entity: EntityPlayer, player: Player){

        Bukkit.getOnlinePlayers().stream()
            .filter {it != player}
            .filter {it.canSee(player)}
            .forEach {
                it.hidePlayer(player)
                it.showPlayer(player)
            }

    }
}