/* Copyright (c) 2022 DeflatedPickle under the MIT license */

package com.deflatedpickle.rawky.collection

import com.deflatedpickle.rawky.api.relation.SingleParent
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class Layer(
    override var child: Grid = Grid(),
    var name: String = "",
    var visible: Boolean = true,
    var lock: Boolean = false,
) : SingleParent<Grid> {
    @Transient lateinit var frame: Frame

    init {
        child.layer = this
    }

    override fun toString() = name
}
