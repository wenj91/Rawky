/* Copyright (c) 2022 DeflatedPickle under the MIT license */

package com.deflatedpickle.rawky.pixelgrid.mode.keyboard

import com.deflatedpickle.haruhi.api.config.Config
import com.deflatedpickle.rawky.pixelgrid.mode.keyboard.serializer.KeySerializer
import com.deflatedpickle.rawky.pixelgrid.mode.keyboard.util.KeyCombo
import kotlinx.serialization.Serializable
import java.awt.event.KeyEvent.VK_DOWN
import java.awt.event.KeyEvent.VK_ENTER
import java.awt.event.KeyEvent.VK_LEFT
import java.awt.event.KeyEvent.VK_RIGHT
import java.awt.event.KeyEvent.VK_UP

@Serializable
data class KeyboardSettings(
    override val version: Int = 2,
    var negativeY: @Serializable(KeySerializer::class) KeyCombo = KeyCombo(key = VK_UP),
    var positiveY: @Serializable(KeySerializer::class) KeyCombo = KeyCombo(key = VK_DOWN),
    var negativeX: @Serializable(KeySerializer::class) KeyCombo = KeyCombo(key = VK_LEFT),
    var positiveX: @Serializable(KeySerializer::class) KeyCombo = KeyCombo(key = VK_RIGHT),
    var useTool: @Serializable(KeySerializer::class) KeyCombo = KeyCombo(key = VK_ENTER),
) : Config
