# Cobblemon Fabric 26.3 Port

This repository is a work in progress to port Cobblemon to Minecraft 26.3 on Fabric. It reuses the upstream Cobblemon source tree and adds a separate `fabric-26.3` Gradle target. The port does **not** build successfully yet, so there is no working mod JAR to install.

## Current status

GitHub Actions reaches Kotlin compilation, but compilation currently fails with thousands of Minecraft API and type errors across common, Fabric, rendering, and GUI code. The latest supplied CI log is summarized in [`docs/26.3-port-progress.md`](docs/26.3-port-progress.md). The full build log and a generated diagnostic summary are retained as the `fabric-26.3-build-log` workflow artifact for 14 days.

## Target and requirements

- Minecraft: 26.3
- Loader: Fabric Loader 0.19.5
- Fabric API: 0.163.0+26.3
- Java: 25
- Gradle: use the checked-in Gradle wrapper

## Build

From the repository root, with JDK 25 installed and available as `JAVA_HOME` or on `PATH`:

```sh
./gradlew -p fabric-26.3 build --stacktrace --console=plain
```

On Windows, use `gradlew.bat` with the same arguments. A successful build should create the mod JAR under `fabric-26.3/build/libs/`. GitHub Actions runs this build on pushes, pull requests, and manual dispatches. A JAR is uploaded only after the build succeeds.

## Repository layout

- `common/`: platform-independent Cobblemon code and resources
- `fabric/`: existing Fabric integration code and resources
- `fabric-26.3/`: Gradle target and 26.3-specific code/resources
- `neoforge/`: NeoForge integration, not the target for this port
- `.github/workflows/fabric-26.3.yml`: build, log capture, and artifact upload
- [`PLAN.md`](PLAN.md): remaining port milestones and acceptance criteria
- [`AGENTS.md`](AGENTS.md): instructions for investigating and fixing port/build failures

The 26.3 target compiles selected common and Fabric source directories together; read `fabric-26.3/build.gradle.kts` before moving or excluding code.

## Important

This is an experimental port, not a release. A successful compile is necessary but not sufficient: the resulting JAR still needs client/server startup and gameplay checks before it can be called working.
