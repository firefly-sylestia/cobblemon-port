/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.client.gui

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.gui.components.ObjectSelectionList
import net.minecraft.util.Mth

/**
 * Entry contract used by Cobblemon's scrolling widgets. 26.3's selection list entries
 * render themselves through [ObjectSelectionList.Entry.extractContent]; this interface
 * keeps the older row-oriented signature available so the existing entry rendering code
 * can position itself from the row geometry supplied by [ScrollingWidget].
 */
interface CobblemonScrollEntry {
    fun extractRenderState(
        context: GuiGraphicsExtractor,
        index: Int,
        y: Int,
        x: Int,
        entryWidth: Int,
        entryHeight: Int,
        mouseX: Int,
        mouseY: Int,
        hovering: Boolean,
        partialTick: Float
    )
}

abstract class ScrollingWidget<T : ObjectSelectionList.Entry<T>>(
    top: Int = 0,
    val left: Int = 0,
    width: Int = 10,
    height: Int = 10,
    val slotHeight: Int = 10,
    val scrollBarWidth: Int = 5
) : ObjectSelectionList<T>(
    Minecraft.getInstance(),
    width, // Width
    height, // Height
    top, // Top
    slotHeight // Slot Height
), CobblemonRenderable {
    init {
        setX(left)
        setY(top)
        setWidth(width)
        setHeight(height)
    }

    override fun extractListBackground(context: GuiGraphicsExtractor) {}
    override fun extractSelection(context: GuiGraphicsExtractor, entry: T, y: Int) {}

    override fun extractItem(
        context: GuiGraphicsExtractor,
        mouseX: Int,
        mouseY: Int,
        delta: Float,
        entry: T
    ) {
        val index = children().indexOf(entry)
        val hovered = getHovered() == entry
        val legacy = entry as? CobblemonScrollEntry
        if (legacy != null) {
            legacy.extractRenderState(
                context,
                index,
                getRowTop(index),
                getRowLeft(),
                getRowWidth(),
                slotHeight,
                mouseX,
                mouseY,
                hovered,
                delta
            )
        } else {
            entry.extractContent(context, mouseX, mouseY, hovered, delta)
        }
    }

    override fun extractScrollbar(context: GuiGraphicsExtractor, mouseX: Int, mouseY: Int) {
        renderScrollbar(context, mouseX, mouseY, 0F)
    }

    open fun renderScrollbar(context: GuiGraphicsExtractor, mouseX: Int, mouseY: Int, delta: Float) {
        val xLeft = scrollBarX()
        val xRight = xLeft + scrollBarWidth
        val top = getY()
        val bottom = getBottom()
        val barHeight = bottom - top

        var j2 = ((barHeight * barHeight).toFloat() / contentHeight().toFloat()).toInt()
        j2 = Mth.clamp(j2, 32, barHeight - 8)
        var k1 = scrollAmount().toInt() * (barHeight - j2) / maxScrollAmount() + top
        if (k1 < top) {
            k1 = top
        }

        context.fill(xLeft, top, xRight, bottom, -16777216)
        context.fill(xLeft, k1, xRight, k1 + j2, -8355712)
        context.fill(xLeft, k1, xRight - 1, k1 + j2 - 1, -4144960)
    }

    override fun getRowLeft(): Int = getX()

    override fun getRowRight(): Int = getRowLeft() + getRowWidth()

    override fun getRowWidth(): Int = getWidth()

    override fun getRowTop(index: Int): Int = getY() - scrollAmount().toInt() + index * slotHeight

    override fun getRowBottom(index: Int): Int = getRowTop(index) + slotHeight

    override fun scrollBarX(): Int = left + width - scrollBarWidth

    abstract class Slot<T : Slot<T>>() : Entry<T>(), CobblemonScrollEntry
}
