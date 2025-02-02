package com.deflatedpickle.rawky.tilecell.collection

import com.deflatedpickle.marvin.serializer.BufferedImageSerializer
import com.deflatedpickle.marvin.serializer.FileSerializer
import com.deflatedpickle.rawky.collection.Cell
import com.deflatedpickle.undulation.serializer.PointSerializer
import com.deflatedpickle.undulation.serializer.RectangleSerializer
import kotlinx.serialization.Polymorphic
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import java.awt.Color
import java.awt.Image
import java.awt.Point
import java.awt.Rectangle
import java.awt.image.BufferedImage
import java.io.File

@Serializable
@SerialName("Tile")
data class TileCell(
    override val row: Int = 0,
    override val column: Int = 0,
    override var content: @Serializable(BufferedImageSerializer::class) BufferedImage = default,
) : Cell<@Serializable(BufferedImageSerializer::class) BufferedImage>() {
    companion object {
        val default = BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB)
    }
}