# Fabric 26.3 Port Plan

## Goal

Produce a Cobblemon Fabric mod for Minecraft 26.3 that builds in CI, launches on a Fabric client and dedicated server, and passes basic gameplay checks. Do not call the port complete or publish a working JAR until those checks pass.

## Progress

- [x] Add a separate `fabric-26.3` Gradle target using Java 25 and the 26.3 Fabric dependencies.
- [x] Add a GitHub Actions workflow that builds the target and retains the build log on success or failure.
- [x] Apply an initial API migration batch covering NBT accessors, game-rule APIs, resource-manager selection, and several Fabric registration/data-generation APIs. See commit `ec54000fa0248360b9b4f83ad5fc100d6297cae3`.
- [x] Apply the 26.3 access widener in the official namespace and inject the `Bone` interface into `ModelPart`.
- [x] Move the posable-model skeleton APIs to `ModelPart` and rename GUI callbacks to the extraction API.
- [ ] Get Kotlin and Java compilation clean. This is the current blocker (4,913 diagnostics across 598 files in run `39293135480`).
- [ ] Produce the remapped Fabric JAR in CI.
- [ ] Launch a client and dedicated server with the JAR; fix startup, mixin, and registry failures.
- [ ] Check core gameplay: create/load a world, register Cobblemon content, catch and release a Pokémon, and save/reload.
- [ ] Publish the JAR only after the above checks succeed.

## Next work

1. Start from the newest CI log and confirm which commit it built.
2. Group diagnostics by root cause and affected API area. The current log shows broad 26.3 changes in GUI/rendering APIs, model types, overrides, and Kotlin types; fix shared signatures and abstractions before chasing dependent errors in individual call sites.
3. Make a focused migration batch, search for remaining usages of the old API, then build the complete `fabric-26.3` target again.
4. Repeat until compilation and packaging pass. Keep CI logs and summaries attached to each run so the next batch is based on fresh diagnostics.
5. Move on to client/server runtime checks only after CI produces the JAR.

## Completion checks

- The `Fabric 26.3 build` GitHub Actions workflow succeeds on `main`.
- The workflow uploads a JAR from `fabric-26.3/build/libs/`.
- The JAR loads on Minecraft 26.3 with Fabric Loader and Fabric API versions declared by the target.
- Client and dedicated-server startup complete without fatal errors; basic world save/reload and Cobblemon gameplay work.
- The README and this plan report the verified state, not an intended or assumed state.
