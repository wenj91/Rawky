package com.deflatedpickle.rawky.collection

import com.deflatedpickle.rawky.api.relation.ChildSelector
import com.deflatedpickle.rawky.api.relation.Matrix
import kotlinx.serialization.Serializable
import java.awt.Rectangle

@Serializable
data class Grid(
    override val rows: Int = 0,
    override val columns: Int = 0,
    override var selectedIndex: Int = 0,
    override val children: MutableList<Cell> = mutableListOf()
) : Matrix<Cell>, ChildSelector {
    init {
        val width = 16
        val height = 16
        var row = 0
        var column = 0

        this.children.addAll(
            Array(this.columns * this.rows) {
                if (column + 1 == this.columns) {
                    if (row + 1 == this.rows) row = 0
                    else row++

                    column = 0
                } else {
                    column++
                }

                Cell(
                    row = row,
                    column = column,
                    polygon = Rectangle(
                        row * 16, column * 16,
                        width, height
                    )
                )
            }
        )
    }

    operator fun get(column: Int, row: Int) = children[(column * columns) + row]
    operator fun set(column: Int, row: Int, value: Cell) = children.set((column * columns) + row, value)
}