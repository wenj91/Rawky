/* Copyright (c) 2022 DeflatedPickle under the MIT license */

@file:Suppress("SpellCheckingInspection")

package com.deflatedpickle.rawky.tilepalette

import com.deflatedpickle.haruhi.Haruhi
import com.deflatedpickle.haruhi.api.constants.MenuCategory
import com.deflatedpickle.haruhi.api.plugin.Plugin
import com.deflatedpickle.haruhi.api.plugin.PluginType
import com.deflatedpickle.haruhi.api.util.ComponentPosition
import com.deflatedpickle.haruhi.event.EventProgramFinishSetup
import com.deflatedpickle.haruhi.util.PluginUtil
import com.deflatedpickle.haruhi.util.RegistryUtil
import com.deflatedpickle.marvin.extensions.div
import com.deflatedpickle.marvin.registry.Registry
import com.deflatedpickle.monocons.MonoIcon
import com.deflatedpickle.rawky.api.impex.Importer
import com.deflatedpickle.rawky.api.palette.PaletteParser
import com.deflatedpickle.sniffle.swingsettings.event.EventChangeTheme
import com.deflatedpickle.undulation.functions.extensions.add
import com.deflatedpickle.undulation.functions.extensions.updateUIRecursively
import org.oxbow.swingbits.dialog.task.TaskDialog
import java.awt.Color
import java.awt.Image
import java.io.File
import javax.swing.JFileChooser
import javax.swing.JMenu
import javax.swing.filechooser.FileNameExtensionFilter

@Plugin(
    value = "tile_palette",
    author = "DeflatedPickle",
    version = "1.0.0",
    description = """
        <br>
        Provides a picker for colour palettes
    """,
    type = PluginType.COMPONENT,
    component = TilePalettePanel::class,
    componentVisible = false,
    componentMinimizedPosition = ComponentPosition.WEST,
)
object TilePalettePlugin {
    val folder = (File(".") / "tilemap").apply { mkdirs() }
    val registry = Registry<String, PaletteParser<Image>>()

    private val chooser = JFileChooser(File(".")).apply {
        EventProgramFinishSetup.addListener {
            for ((k, v) in registry) {
                addChoosableFileFilter(
                    FileNameExtensionFilter(
                        "${v.name} (*.$k)",
                        k
                    )
                )
            }
        }
    }

    init {
        EventChangeTheme.addListener {
            TilePalettePanel.updateUIRecursively()
        }

        EventProgramFinishSetup.addListener {
            RegistryUtil.get(MenuCategory.MENU.name)?.apply {
                (get(MenuCategory.FILE.name) as JMenu).apply {
                    add("Import Tile Palette", MonoIcon.FOLDER_NEW) { importTilePalette() }
                }
            }
        }
    }

    private fun importTilePalette() {
        if (chooser.showOpenDialog(Haruhi.window) == JFileChooser.APPROVE_OPTION) {
            val i = chooser.selectedFile

            if (i.isFile) {
                registry[i.extension]?.let { pp ->
                    TilePalettePanel.combo.addItem(pp.parse(i))
                }
            }
        }
    }
}
