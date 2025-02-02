/* Copyright (c) 2023 DeflatedPickle under the MIT license */

@file:Suppress("UNCHECKED_CAST")

package com.deflatedpickle.rawky.pixelgrid.layer.toolpaint

import com.deflatedpickle.haruhi.api.plugin.Plugin
import com.deflatedpickle.haruhi.api.plugin.PluginType
import com.deflatedpickle.haruhi.util.ConfigUtil
import com.deflatedpickle.rawky.RawkyPlugin
import com.deflatedpickle.rawky.api.Painter
import com.deflatedpickle.rawky.api.Tool
import com.deflatedpickle.rawky.collection.Grid
import com.deflatedpickle.rawky.pixelgrid.PixelGridPanel
import com.deflatedpickle.rawky.pixelgrid.api.Layer
import com.deflatedpickle.rawky.pixelgrid.api.PaintLayer
import com.deflatedpickle.rawky.pixelgrid.api.PaintLayer.Companion.registry
import com.deflatedpickle.rawky.setting.RawkyDocument
import com.deflatedpickle.rawky.util.DrawUtil
import kotlinx.serialization.ExperimentalSerializationApi
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics2D
import java.awt.Stroke

@ExperimentalSerializationApi
@Plugin(
    value = "pixel_grid_toolpaint_layer",
    author = "DeflatedPickle",
    version = "1.0.0",
    description = """
        <br>
        Paints tool's paint method to the pixel grid
    """,
    type = PluginType.OTHER,
    dependencies = [
        "deflatedpickle@core#*",
        "deflatedpickle@pixelgrid#*",
    ],
)
@Suppress("unused")
object PixelGridToolPaintLayerPlugin : PaintLayer {
    override val name = "ToolPaint"
    override val layer = Layer.OVER_DECO

    init {
        registry["toolpaint"] = this
    }

    override fun paint(g2d: Graphics2D) {
        RawkyPlugin.document?.let { doc ->
            if (doc.selectedIndex >= doc.children.size) return

            DrawUtil.paintHoverCell(PixelGridPanel.selectedCells, g2d)

            if (Tool.isToolValid()) {
                val tool = Tool.current
                if (PixelGridPanel.selectedCells.size > 0 && tool is Painter<*>) {
                    tool.paint(PixelGridPanel.selectedCells.first(), g2d)
                }
            }
        }
    }
}
