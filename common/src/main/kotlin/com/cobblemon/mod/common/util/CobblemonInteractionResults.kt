package com.cobblemon.mod.common.util

import net.minecraft.world.InteractionResult
import net.minecraft.world.item.ItemStack

/** Adapts the 1.21 item result helpers to the 26.3 interaction result API. */
object CobblemonInteractionResults {
    @JvmStatic
    fun success(stack: ItemStack): InteractionResult = InteractionResult.SUCCESS.heldItemTransformedTo(stack)

    @JvmStatic
    fun fail(@Suppress("UNUSED_PARAMETER") stack: ItemStack): InteractionResult = InteractionResult.FAIL

    @JvmStatic
    fun pass(@Suppress("UNUSED_PARAMETER") stack: ItemStack): InteractionResult = InteractionResult.PASS

    @JvmStatic
    fun consume(stack: ItemStack): InteractionResult = InteractionResult.CONSUME.heldItemTransformedTo(stack)

    @JvmStatic
    fun sidedSuccess(stack: ItemStack, isClientSide: Boolean): InteractionResult =
        (if (isClientSide) InteractionResult.SUCCESS else InteractionResult.SUCCESS_SERVER).heldItemTransformedTo(stack)

    @JvmStatic
    fun sidedSuccess(isClientSide: Boolean): InteractionResult =
        if (isClientSide) InteractionResult.SUCCESS else InteractionResult.SUCCESS_SERVER
}
