/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.world.gamerules

import com.cobblemon.mod.common.Cobblemon

object CobblemonGameRules {

    @JvmField
    val BATTLE_INVULNERABILITY: Any = Cobblemon.implementation.registerGameRule("battleInvulnerability", "PLAYER", false)
    @JvmField
    val DO_POKEMON_SPAWNING: Any = Cobblemon.implementation.registerGameRule("doPokemonSpawning", "SPAWNING", true)
    @JvmField
    val DO_POKEMON_LOOT: Any = Cobblemon.implementation.registerGameRule("doPokemonLoot", "DROPS", true)
    @JvmField
    val MOB_TARGET_IN_BATTLE: Any = Cobblemon.implementation.registerGameRule("mobTargetInBattle", "MOBS", true)
    @JvmField
    val SHINY_STARTERS: Any = Cobblemon.implementation.registerGameRule("doShinyStarters", "MISC", false)
    @JvmField
    val HEALERS_HEAL_PC: Any = Cobblemon.implementation.registerGameRule("healersHealPC", "MISC", false)
    @JvmField
    val DO_POKEMON_PREDATION: Any = Cobblemon.implementation.registerGameRule("doPokemonPredation", "MOBS", true)
}
