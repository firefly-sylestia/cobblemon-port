/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.client.gui

import com.cobblemon.mod.common.mixin.accessor.EntryListWidgetAccessor
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.gui.components.ObjectSelectionList
import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.renderer.rendertype.RenderType
import net.minecraft.client.renderer.entity.layers.RenderLayer
import net.minecraft.util.Mth

abstract class ScrollingWidget<T : ObjectSelectionList.Entry<T>>(
    top: Int = 0,
    val left: Int = 0,
    width: Int = 10,
    height: Int = 10,
    slotHeight: Int = 10,
    val scrollBarWidth: Int = 5
) : ObjectSelectionList<T>(
    Minecraft.getInstance(),
    width, // Width
    height, // Height
    top, // Top
    slotHeight // Slot Height
), CobblemonRenderable {
    override fun extractListBackground(guiGraphics: GuiGraphicsExtractor) {}


    init {
        setRectangle(width, height, top, top + height)
        setLeft(left)
    }

    final override fun setRectangle(width: Int, height: Int, top: Int, bottom: Int) {
        this.width = width
        this.height = height
        this.y = bottom
        this.x = left + width
    }

    fun setLeft(left: Int){
        this.x = left
    }

    override fun extractWidgetRenderState(context: GuiGraphicsExtractor, mouseX: Int, mouseY: Int, delta: Float) {
        super.extractWidgetRenderState(context, mouseX, mouseY, delta)
    }

    override fun extractScrollbar(context: GuiGraphicsExtractor, mouseX: Int, mouseY: Int) {
        if (maxScrollAmount() > 0) {
            renderScrollbar(context, mouseX, mouseY, 0F)
        }
    }

    open fun renderScrollbar(context: GuiGraphicsExtractor, mouseX: Int, mouseY: Int, delta: Float){
        val xLeft = this.scrollBarX()
        val xRight = xLeft + scrollBarWidth

        val barHeight = this.bottom - this.y

        var j2 = ((barHeight * barHeight).toFloat() / this.contentHeight().toFloat()).toInt()
        j2 = Mth.clamp(j2, 32, barHeight - 8)
        var k1 = scrollAmount.toInt() * (barHeight - j2) / this.maxScrollAmount() + this.y
        if (k1 < this.y) {
            k1 = this.y
        }

        context.fill(xLeft, this.y, xRight, this.bottom, -16777216)
        context.fill(xLeft, k1, xRight, k1 + j2, -8355712)
        context.fill(xLeft ,k1, xRight - 1, k1 + j2 - 1, -4144960)
    }

    open fun renderHorizontalShadows(context: GuiGraphicsExtractor, mouseX: Int, mouseY: Int, delta: Float){
        context.setColor(0.25f, 0.25f, 0.25f, 1.0f)
        context.blit(
            Screen.MENU_BACKGROUND,
            this.left, 0, 0.0f, 0.0f,
            this.width,
            this.y, 32, 32
        )
        context.blit(
            Screen.MENU_BACKGROUND,
            this.left,
            this.bottom, 0.0f,
            bottom.toFloat(),
            this.width,
            this.height - this.bottom, 32, 32
        )
        context.setColor(1.0f, 1.0f, 1.0f, 1.0f)
        context.fillGradient(
            RenderType.guiOverlay(),
            this.left,
            this.y,
            this.right,
            this.y + 4, -16777216, 0, 0
        )
        context.fillGradient(
            RenderType.guiOverlay(),
            this.left,
            this.bottom - 4,
            this.right,
            this.bottom, 0, -16777216, 0
        )
    }

    override fun getRowLeft(): Int {
        return this.left
    }

    override fun getRowRight(): Int {
        return this.rowLeft + this.rowWidth
    }

    override fun getRowWidth(): Int {
        return this.width
    }

    override fun getRowTop(index: Int): Int {
        return this.y - scrollAmount.toInt() + (index * this.itemHeight)
    }

    override fun getRowBottom(index: Int): Int {
        return this.getRowTop(index) + this.itemHeight
    }

    override fun scrollBarX(): Int = this.left + this.width - this.scrollBarWidth

    abstract class Slot<T : Slot<T>>(): Entry<T>() {
        // Override render to show each individual element
    }
}
