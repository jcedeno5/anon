package io.github.infintyz.anonymous

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class Anonymous : JavaPlugin() {

    val enabled: Boolean = true
    val scoreboardUtils = ScoreboardUtils()
    val gameProfileHandler = GameProfileHandler()

    override fun onEnable() {
        Bukkit.getPluginManager().registerEvents(JoinHandler(this), this)
        super.onEnable()
    }

    override fun onDisable() {
        super.onDisable()
    }
}