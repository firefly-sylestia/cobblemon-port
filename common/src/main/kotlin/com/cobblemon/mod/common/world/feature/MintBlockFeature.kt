/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.world.feature

import com.cobblemon.mod.common.block.MintBlock
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.core.BlockPos
import net.minecraft.tags.BlockTags
import net.minecraft.util.RandomSource
import net.minecraft.world.level.WorldGenLevel
import net.minecraft.world.level.block.Block.UPDATE_CLIENTS
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.chunk.ChunkGenerator
import net.minecraft.world.level.levelgen.feature.Feature

class MintBlockFeature(val state: BlockState) : Feature {

    override fun codec(): MapCodec<MintBlockFeature> = CODEC

    override fun place(worldGenLevel: WorldGenLevel, chunkGenerator: ChunkGenerator, random: RandomSource, origin: BlockPos): Boolean {
        val blockPos = origin
        val blockState = state
        val floor = blockPos.below()

        if (!worldGenLevel.getBlockState(floor).`is`(BlockTags.DIRT)) return false

        // Attempt to get at least one other valid position for the crop
        val validPlacements = getValidPositions(worldGenLevel, blockPos)
        if (validPlacements.isEmpty()) return false

        val minAge = MintBlock.MATURE_AGE - 2
        val maxAge = MintBlock.MATURE_AGE

        // Generate the blocks
        worldGenLevel.setBlock(blockPos, blockState.setValue(MintBlock.AGE, random.nextIntBetweenInclusive(minAge, maxAge)), UPDATE_CLIENTS)
        validPlacements.shuffled().take(2).forEach { position ->
            worldGenLevel.setBlock(position, blockState.setValue(MintBlock.AGE, random.nextIntBetweenInclusive(minAge, maxAge)), UPDATE_CLIENTS)
        }
        return true
    }

    private fun getValidPositions(world: WorldGenLevel, origin: BlockPos): List<BlockPos> {
        val validPositions = mutableListOf<BlockPos>()

        for (x in -1..1) {
            for (y in -1..1) {
                for (z in -1..1) {
                    if (x == 0 && z == 0) continue
                    val offsetPos = origin.offset(x, y, z)
                    val floorBlockState = world.getBlockState(offsetPos.below())
                    if (world.isEmptyBlock(offsetPos) && floorBlockState.`is`(BlockTags.DIRT)) {
                        validPositions.add(offsetPos)
                    }
                }
            }
        }

        return validPositions
    }

    companion object {
        val CODEC: MapCodec<MintBlockFeature> = RecordCodecBuilder.mapCodec { instance ->
            instance.group(
                BlockState.CODEC.fieldOf("state").forGetter { it.state }
            ).apply(instance, ::MintBlockFeature)
        }
    }
}
