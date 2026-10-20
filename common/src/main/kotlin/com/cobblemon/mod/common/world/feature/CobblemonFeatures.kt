/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.world.feature

import com.cobblemon.mod.common.platform.PlatformRegistry
import com.cobblemon.mod.common.world.feature.structure.CobblemonFeature
import com.mojang.serialization.MapCodec
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.levelgen.feature.Feature

/**
 * Registers the codec of each Cobblemon feature as a feature type.
 *
 * Since 26.3 features are datapack entries in `worldgen/feature`, so the mod contributes the
 * `type` codecs to [BuiltInRegistries.FEATURE_TYPE] rather than feature instances.
 */
object CobblemonFeatures : PlatformRegistry<Registry<MapCodec<out Feature>>, ResourceKey<Registry<MapCodec<out Feature>>>, MapCodec<out Feature>>() {

    override val registry: Registry<MapCodec<out Feature>> = BuiltInRegistries.FEATURE_TYPE
    override val resourceKey: ResourceKey<Registry<MapCodec<out Feature>>> = Registries.FEATURE_TYPE

    @JvmField
    val APRICORN_TREE = create("apricorn_tree", ApricornTreeFeature.CODEC)
    @JvmField
    val SACCHARINE_TREE = create("saccharine_tree", SaccharineTreeFeature.CODEC)
    @JvmField
    val MINT = create("mint", MintBlockFeature.CODEC)
    @JvmField
    val BERRY_GROVE = create("berry_grove", BerryGroveFeature.CODEC)
    @JvmField
    val COBBLEMON_FEATURE = create("structure", CobblemonFeature.CODEC)
    @JvmField
    val TYPE_GEM = create("type_gem", TypeGemFeature.CODEC)
}
