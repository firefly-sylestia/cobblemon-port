/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.util

import com.cobblemon.mod.common.CobblemonEntities
import net.minecraft.nbt.CompoundTag
import java.util.UUID

fun CompoundTag.isPokemonEntity() : Boolean {
    return this.getString("id").equals(CobblemonEntities.POKEMON_KEY.toString())
}

fun CompoundTag.putUUID(key: String, uuid: UUID) {
    val most = uuid.mostSignificantBits
    val least = uuid.leastSignificantBits
    putIntArray(key, intArrayOf((most shr 32).toInt(), most.toInt(), (least shr 32).toInt(), least.toInt()))
}

fun CompoundTag.getUUID(key: String): UUID {
    val parts = getIntArray(key).orElseThrow { IllegalStateException("Missing UUID '$key'") }
    require(parts.size == 4) { "Invalid UUID '$key': expected four integers" }
    val most = (parts[0].toLong() shl 32) or (parts[1].toLong() and 0xFFFFFFFFL)
    val least = (parts[2].toLong() shl 32) or (parts[3].toLong() and 0xFFFFFFFFL)
    return UUID(most, least)
}

fun CompoundTag.hasUUID(key: String): Boolean = getIntArray(key).map { it.size == 4 }.orElse(false)
