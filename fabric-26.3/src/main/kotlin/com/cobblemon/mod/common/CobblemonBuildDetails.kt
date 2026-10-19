/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common

object CobblemonBuildDetails {
    const val MOD_ID = "cobblemon"
    const val VERSION = "1.8.3+26.3-port"
    const val SNAPSHOT = true
    const val GIT_COMMIT = "port-in-progress"
    const val BRANCH = "codex/minecraft-26.3-fabric"

    fun smallCommitHash(): String = GIT_COMMIT.take(7)
}
