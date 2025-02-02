/* Copyright (c) 2022 DeflatedPickle under the MIT license */

@file:Suppress("unused", "UNUSED_PARAMETER")

package com.deflatedpickle.rawky.pixelgrid.export.text.ascii

import com.deflatedpickle.haruhi.api.plugin.Plugin
import com.deflatedpickle.haruhi.api.plugin.PluginType
import com.deflatedpickle.rawky.api.impex.Exporter
import com.deflatedpickle.rawky.api.impex.Exporter.Companion.registry
import com.deflatedpickle.rawky.pixelgrid.export.text.ascii.api.Palette
import com.deflatedpickle.rawky.pixelgrid.export.text.ascii.dialog.ExportDialog
import com.deflatedpickle.rawky.setting.RawkyDocument
import org.oxbow.swingbits.dialog.task.TaskDialog
import java.awt.Color
import java.awt.Desktop
import java.io.File
import java.io.FileOutputStream

@Plugin(
    value = "ascii",
    author = "DeflatedPickle",
    version = "1.0.1",
    description = """
        <br>
        Export ASCII files
    """,
    type = PluginType.OTHER,
    dependencies = [
        "deflatedpickle@pixel_grid#*",
    ],
)
object AsciiPlugin : Exporter {
    override val name = "ASCII"
    override val exporterExtensions = mutableMapOf(
        "Text" to listOf("txt")
    )

    init {
        registry[name] = this
    }

    override fun export(doc: RawkyDocument, file: File) {
        val dialog = ExportDialog()
        dialog.isVisible = true

        if (dialog.result == TaskDialog.StandardCommand.OK) {
            val frame = doc.children[dialog.frameSpinner.value as Int]
            val layers = frame.children
            val grid = frame.children[0].child

            val out = FileOutputStream(file, false)
            out.writer().apply {
                for (row in 0 until grid.rows) {
                    for (column in 0 until grid.columns) {
                        for (layer in layers.reversed()) {
                            write(
                                (dialog.paletteCombo.selectedItem as Palette)
                                    .char(column, row, layer.child[column, row].content as Color)
                            )
                        }
                    }
                    write("\n")
                }
            }.apply {
                flush()
                close()
            }

            if (dialog.openCheck.isSelected) {
                Desktop.getDesktop().open(file)
            }
        }
    }
}
