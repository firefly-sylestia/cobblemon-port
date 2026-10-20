/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

/*


package com.cobblemon.mod.common.item

import com.cobblemon.mod.common.entity.throwable.ScatterBangEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.entity.projectile.Snowball
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.sounds.SoundSource
import net.minecraft.sounds.SoundEvents
import net.minecraft.stats.Stats
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.level.Level


class ScatterBangItem(settings: Settings?) : Item(settings) {
    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        val itemStack = user.getStackInHand(hand)
        world.playSound(
            null as PlayerEntity?,
            user.x,
            user.y,
            user.z,
            SoundEvents.ENTITY_SNOWBALL_THROW,
            SoundCategory.NEUTRAL,
            0.5f,
            0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f)
        )
        if (!world.isClient) {
            val scatterBangEntity = ScatterBangEntity(world, user)
            scatterBangEntity.setItem(itemStack)
            scatterBangEntity.setVelocity(user, user.pitch, user.yaw, 0.0f, 1.5f, 1.0f)
            world.spawnEntity(scatterBangEntity)
        }

        user.incrementStat(Stats.USED.getOrCreateStat(this))
        if (!user.abilities.creativeMode) {
            itemStack.decrement(1)
        }

        return TypedActionResult.success(itemStack, world.isClient())
    }
}*/
