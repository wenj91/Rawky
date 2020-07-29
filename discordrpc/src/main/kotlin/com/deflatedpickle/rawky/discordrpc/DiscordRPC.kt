package com.deflatedpickle.rawky.discordrpc

import com.deflatedpickle.haruhi.api.plugin.Plugin
import com.deflatedpickle.haruhi.api.plugin.PluginType
import com.deflatedpickle.rawky.discordrpc.util.DiscordRP
import com.deflatedpickle.rawky.event.specific.EventRawkyInit
import com.deflatedpickle.rawky.event.specific.EventRawkyShutdown
import com.deflatedpickle.rawky.event.specific.EventWindowShown
import com.deflatedpickle.rawky.ui.window.Window
import com.deflatedpickle.rawky.util.ConfigUtil
import net.arikia.dev.drpc.DiscordRichPresence

// This plugin only exists to be a dependency
@Plugin(
    value = "discord_rpc",
    author = "DeflatedPickle",
    version = "1.0.0",
    description = """
        <br>
        Adds Discord RCP integration
    """,
    type = PluginType.API,
    dependencies = [
        "deflatedpickle@core#1.0.0"
    ],
    settings = DiscordRPCSettings::class
)
@Suppress("unused")
object DiscordRPC {
    init {
        EventRawkyInit.addListener {
            val settings = ConfigUtil.getSettings<DiscordRPCSettings>(
                "deflatedpickle@discord_rpc#1.0.0"
            )
            val enabled = settings.enabled

            if (enabled) {
                // Connect to Discord RCP
                this.start()
            }

            if (enabled) {
                EventWindowShown.addListener {
                    if (it is Window) {
                        DiscordRP.stack.push(
                            DiscordRichPresence
                                .Builder("")
                                .setDetails("Hanging around, doing nothing")
                                .setStartTimestamps(System.currentTimeMillis())
                                .build()
                        )
                    }
                }
            }
        }

        EventRawkyShutdown.addListener {
            // Shutdown Discord RCP
            this@DiscordRPC.stop()
        }
    }

    private fun start() {
        DiscordRP.initializeRCP()
        DiscordRP.timer.start()
    }

    private fun stop() {
        DiscordRP.shutdownRCP()
        DiscordRP.timer.stop()
    }
}