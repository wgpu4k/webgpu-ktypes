# WebGPU Kotlin Toolkit - Deployment Guide

**Date:** 2026-05-19  
**Language:** English  
**Target Audience:** DevOps, Maintainers, CI/CD Engineers  

---

## Overview

This guide covers deployment configuration, CI/CD pipelines, and artifact publishing for the WebGPU Kotlin Toolkit monorepo.

### Artifact Publishing

The project publishes artifacts to:
- **Maven Central** - Primary repository for JVM/Android artifacts
- **GitHub Packages** - Optional (not currently configured)

### Release Process

1. Create GitHub Release → Triggers publish workflow
2. Workflow builds and publishes to Maven Central
3. Version is set via GitHub Release tag

---

## CI/CD Pipelines

### GitHub Actions Workflows

All workflows are located in `.github/workflows/`:

| Workflow | Trigger | Purpose |
|----------|---------|---------|
| `test.yml` | Push/Pull Request | Run tests on all platforms |
| `publish.yml` | Release Created | Build and publish artifacts |
| `snapshot.yml` | Push to master | Publish snapshot builds |
| `codeql.yml` | Push/Pull Request | Security code analysis |
| `qodana_code_quality.yml` | Push/Pull Request | Code quality analysis |

---

## Test Workflow (`test.yml`)

### Trigger Conditions

```yaml
on:
  push:
    branches: [ master, 'release/**', 'feature/**', 'fix/**']
  pull_request:
    branches: [ master, 'release/**', 'feature/**', 'fix/**']
```

### Execution Matrix

| OS | Targets | Native Tools Required |
|----|---------|------------------------|
| macOS | All (JVM, JS, Native) | Xcode CLT |
| Ubuntu | All (JVM, JS, Native) | glslang-tools, spirv-tools |
| Windows | JVM, JS | None |

**Note:** Native targets on Ubuntu require additional package installation.

### Workflow Steps

```yaml
jobs:
  tests:
    strategy:
      fail-fast: false
      matrix:
        os: [ macos-latest, ubuntu-latest, windows-latest ]
    runs-on: ${{ matrix.os }}
    steps:
      - uses: actions/checkout@v4
      - name: Set up JDK 25
        uses: actions/setup-java@v4
        with:
          distribution: 'temurin'
          java-version: 25
          cache: 'gradle'
      - name: Setup Gradle
        uses: gradle/actions/setup-gradle@v6
      - name: Install Native Validators (Ubuntu)
        if: matrix.os == 'ubuntu-latest'
        run: |
          sudo apt-get update
          sudo apt-get install -y glslang-tools spirv-tools
      - name: Cache Gradle packages
        uses: actions/cache@v4
        with:
          path: ~/.gradle/caches
          key: ${{ runner.os }}-gradle-${{ hashFiles('**/*.gradle.kts') }}
      - name: Build & Test
        run: ./gradlew check
```

### Environment Requirements

- **Java:** Temurin JDK 25
- **Gradle:** 9.5.0 (via wrapper)
- **Ubuntu Native:** glslang-tools, spirv-tools
- **macOS Native:** Xcode Command Line Tools

---

## Publish Workflow (`publish.yml`)

### Trigger Conditions

```yaml
on:
  release:
    types: [created]
```

**Trigger:** GitHub Release creation

### Workflow Steps

```yaml
jobs:
  build:
    runs-on: macos-latest
    steps:
      - uses: actions/checkout@v4
      - name: Set up JDK 25
        uses: actions/setup-java@v4
        with:
          distribution: 'temurin'
          java-version: 25
          cache: 'gradle'
      - name: Build and publish with Gradle
        run: |
          ./gradlew build
          ./gradlew publish publishToCentralPortal --info
        env:
          SNAPSHOT: "false"
          VERSION: ${{ github.event.release.tag_name }}
          MAVENCENTRAL_USERNAME: ${{ secrets.SONATYPE_LOGIN }}
          MAVENCENTRAL_PASSWORD: ${{ secrets.SONATYPE_PASSWORD }}
          GPG_SECRET_KEY: ${{ secrets.PGP_PRIVATE }}
          GPG_PASSPHRASE: ${{ secrets.PGP_PASSPHRASE }}
```

### Required Secrets

| Secret | Description | Repository Setting |
|--------|-------------|-------------------|
| `SONATYPE_LOGIN` | Maven Central username | `secrets.SONATYPE_LOGIN` |
| `SONATYPE_PASSWORD` | Maven Central password | `secrets.SONATYPE_PASSWORD` |
| `PGP_PRIVATE` | GPG private key for signing | `secrets.PGP_PRIVATE` |
| `PGP_PASSPHRASE` | GPG key passphrase | `secrets.PGP_PASSPHRASE` |

### Publishing Targets

The workflow publishes to:
1. **Maven Local** (`./gradlew publish`) - For verification
2. **Maven Central Portal** (`./gradlew publishToCentralPortal`) - Production

### Version Management

```bash
# Version is automatically set from GitHub Release tag
export VERSION=${{ github.event.release.tag_name }}

# Example: Creating release v0.0.10 sets VERSION=0.0.10
```

---

## Snapshot Workflow (`snapshot.yml`)

### Trigger Conditions

```yaml
on:
  push:
    branches: [ master ]
```

**Trigger:** Push to master branch

### Workflow Configuration

```yaml
jobs:
  build:
    runs-on: macos-latest
    steps:
      - uses: actions/checkout@v4
      - name: Set up JDK
        uses: actions/setup-java@v4
        with:
          distribution: 'temurin'
          java-version: 25
      - name: Publish Snapshot
        run: |
          ./gradlew build
          ./gradlew publish
        env:
          SNAPSHOT: "true"
          VERSION: 0.0.9-SNAPSHOT
          MAVENCENTRAL_USERNAME: ${{ secrets.SONATYPE_LOGIN }}
          MAVENCENTRAL_PASSWORD: ${{ secrets.SONATYPE_PASSWORD }}
```

### Snapshot Versioning

- **Pattern:** `0.0.9-SNAPSHOT`
- **Repository:** Maven Central snapshot repository
- **Usage:** Development and testing

---

## Security Analysis (`codeql.yml`)

### Trigger Conditions

```yaml
on:
  push:
    branches: [ master, 'release/**' ]
  pull_request:
    branches: [ master, 'release/**' ]
```

### Analysis Configuration

```yaml
jobs:
  analyze:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - name: Initialize CodeQL
        uses: github/codeql-action/init@v3
        with:
          languages: 'kotlin'
      - name: Autobuild
        uses: github/codeql-action/autobuild@v3
      - name: Perform CodeQL Analysis
        uses: github/codeql-action/analyze@v3
```

**Languages Analyzed:** Kotlin

---

## Code Quality (`qodana_code_quality.yml`)

### Trigger Conditions

```yaml
on:
  push:
    branches: [ master ]
  pull_request:
    branches: [ master ]
```

### Analysis Configuration

Uses JetBrains Qodana for static code analysis:

```yaml
jobs:
  qodana:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - name: 'Qodana Scan'
        uses: JetBrains/qodana-action@v2024.1
        with:
          cache-dir: .qodana
```

---

## Local Deployment

### Prerequisites

```bash
# Install required tools
# - Java 25 (for builds)
# - GPG (for signing)
# - Maven Central account

# On Ubuntu/Debian
sudo apt-get install -y gpg openjdk-25-jdk
```

### Manual Publishing

#### 1. Configure Maven Central Credentials

Create or update `~/.m2/settings.xml`:

```xml
<settings>
  <servers>
    <server>
      <id>ossrh</id>
      <username>${env.MAVENCENTRAL_USERNAME}</username>
      <password>${env.MAVENCENTRAL_PASSWORD}</password>
    </server>
  </servers>
</settings>
```

Or use Gradle properties:

```properties
# gradle.properties
mavenCentralUsername=user
mavenCentralPassword=password
```

#### 2. Configure GPG Signing

```bash
# Import your GPG key
gpg --import private.key

# List keys
gpg --list-secret-keys

# Configure Gradle signing
mkdir -p ~/.gradle
cat > ~/.gradle/gradle.properties << EOF
signing.keyId=YOUR_KEY_ID
signing.password=YOUR_PASSPHRASE
signing.secretKeyRingFile=/path/to/secring.gpg
EOF
```

#### 3. Build and Publish

```bash
# Clean build
export SNAPSHOT=false
export VERSION=0.0.10
./gradlew clean build

# Dry run (verify without publishing)
./gradlew publishToMavenLocal

# Publish to Maven Central
./gradlew publish publishToCentralPortal

# Verify published artifacts
./gradlew closeAndReleaseRepository
```

### Snapshot Publishing

```bash
# Snapshot build
export SNAPSHOT=true
export VERSION=0.0.9-SNAPSHOT
./gradlew clean build

# Publish snapshot
./gradlew publish
```

---

## Deployment Configuration Files

### Gradle Publishing Configuration

Each module's `build.gradle.kts` contains publishing configuration:

```kotlin
publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "io.ygdrasil"
            artifactId = "webgpu-ktypes"
            version = project.version
            
            from(components["kotlin"])
            
            pom {
                name.set("WebGPU Kotlin Types")
                description.set("Kotlin types for WebGPU")
                url.set("https://github.com/ygdrasil-oss/webgpu-ktypes")
                licenses {
                    license {
                        name.set("Apache-2.0")
                    }
                }
                developers {
                    developer {
                        id.set("ygdrasil-oss")
                        name.set("Ygdrasil OSS")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/ygdrasil-oss/webgpu-ktypes.git")
                    developerConnection.set("scm:git:ssh://git@github.com/ygdrasil-oss/webgpu-ktypes.git")
                    url.set("https://github.com/ygdrasil-oss/webgpu-ktypes")
                }
            }
        }
    }
    
    repositories {
        maven {
            name = "MavenCentral"
            url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
                username = System.getenv("MAVENCENTRAL_USERNAME") ?: project.findProperty("mavenCentralUsername")
                password = System.getenv("MAVENCENTRAL_PASSWORD") ?: project.findProperty("mavenCentralPassword")
            }
        }
    }
}

signing {
    sign(publishing.publications)
}
```

---

## Artifact Coordinates

### Published Artifacts

| Module | Group ID | Artifact ID | Description |
|--------|----------|------------|-------------|
| Core | io.ygdrasil | webgpu-ktypes | WebGPU core bindings |
| Descriptors | io.ygdrasil | webgpu-ktypes-descriptors | Descriptor types |
| Specifications | io.ygdrasil | webgpu-ktypes-specifications | Specification types |
| Web | io.ygdrasil | webgpu-ktypes-web | Web/JS specific types |
| WGSL Core | io.ygdrasil | wgsl-core | WGSL core processing |
| WGSL Parser | io.ygdrasil | wgsl-parser | WGSL parser |
| WGSL Generator | io.ygdrasil | wgsl-generator | Code generation |
| WGSL CLI | io.ygdrasil | wgsl-cli | Command-line interface |

### Maven Dependency Usage

```kotlin
// build.gradle.kts
dependencies {
    implementation("io.ygdrasil:webgpu-ktypes:0.0.9-SNAPSHOT")
    implementation("io.ygdrasil:webgpu-ktypes-descriptors:0.0.9-SNAPSHOT")
    implementation("io.ygdrasil:wgsl-parser:0.0.9-SNAPSHOT")
}
```

---

## Release Process

### Standard Release

1. **Prepare Changes**
   ```bash
   git checkout master
   git pull origin master
   ```

2. **Update Version**
   - Update version in build scripts if needed
   - Or rely on release tag for version

3. **Create GitHub Release**
   - Go to GitHub → Releases → Draft new release
   - Tag: `v0.0.10` (or appropriate version)
   - Title: `Release 0.0.10`
   - Description: Release notes
   - **Publish release** (triggers publish workflow)

4. **Monitor Workflow**
   - Check `publish.yml` workflow execution
   - Verify all steps pass
   - Check Maven Central for published artifacts

5. **Verify Release**
   ```bash
   # Check Maven Central
   # https://oss.sonatype.org/#nexus-search;gav~io.ygdrasil~webgpu-ktypes~~0.0.10~
   
   # Or search on https://search.maven.org/
   ```

### Emergency Hotfix Release

1. Create hotfix branch
   ```bash
   git checkout -b hotfix/v0.0.9-fix-1 master
   ```

2. Make minimal fixes

3. Test thoroughly
   ```bash
   ./gradlew check
   ```

4. Create release from hotfix branch
   - Tag: `v0.0.9-fix-1`
   - This triggers the publish workflow

5. Merge to master
   ```bash
   git checkout master
   git merge hotfix/v0.0.9-fix-1
   git push origin master
   ```

---

## Docker Deployment (Optional)

### Dockerfile Example

While the project doesn't include a Dockerfile, here's a recommended configuration:

```dockerfile
# Build stage
FROM eclipse-temurin:25-jdk as build
WORKDIR /app
COPY . .
RUN ./gradlew build

# Runtime stage (for JVM artifacts)
FROM eclipse-temurin:25-jre
WORKDIR /app
COPY --from=build /app/build/libs/ /app/libs/
CMD ["java", "-cp", "/app/libs/*", "io.ygdrasil.webgpu.MainKt"]
```

### Building Docker Image

```bash
docker build -t ygdrasil/webgpu-ktypes:0.0.9 .
docker push ygdrasil/webgpu-ktypes:0.0.9
```

---

## Monitoring and Maintenance

### CI/CD Monitoring

- **GitHub Actions:** https://github.com/ygdrasil-oss/webgpu-ktypes/actions
- **Check workflow runs** for all pushes and PRs
- **Investigate failures** in test workflow for platform-specific issues

### Maven Central Monitoring

- **Sonatype Nexus:** https://oss.sonatype.org/
- **Maven Central:** https://search.maven.org/
- **Sync Status:** Can take 10-30 minutes for artifacts to appear

### Build Health

```bash
# Check Gradle build cache stats
./gradlew build --build-cache --scan

# Open build scan
# (Follow link from output)
```

---

## Troubleshooting Deployment

### Publishing Failures

**Symptom:** `Authentication failed`

**Solution:**
- Verify `SONATYPE_LOGIN` and `SONATYPE_PASSWORD` secrets are correct
- Check Maven Central account has publish permissions
- Regenerate API token if expired

**Symptom:** ` GPG signing failed`

**Solution:**
- Verify GPG key is imported: `gpg --list-secret-keys`
- Check passphrase is correct
- Ensure key is not expired

**Symptom:** `Artifact already exists`

**Solution:**
- Delete existing staging repository on Sonatype
- Or increment version number
- Wait for sync to Maven Central (can take hours)

### Test Workflow Failures

**Symptom:** Native tests fail on Ubuntu

**Solution:**
- Verify glslang-tools and spirv-tools are installed
- Check Docker image has required packages

**Symptom:** JS tests fail

**Solution:**
- Verify Node.js is available in workflow
- Check Kotlin/JS compiler version compatibility

---

## Infrastructure as Code

### Recommended Terraform (for self-hosting)

```hcl
# Example for self-hosted runner
resource "github_actions_runner" "linux" {
  runner_group_id = github_actions_runner_group.main.id
  name             = "webgpu-ubuntu"
  labels           = ["self-hosted", "linux", "webgpu"]
}
```

---

## Summary

| Aspect | Configuration |
|--------|---------------|
| **CI/CD** | GitHub Actions |
| **Primary Pipeline** | `test.yml` (macOS, Ubuntu, Windows) |
| **Publishing** | `publish.yml` (Maven Central) |
| **Snapshots** | `snapshot.yml` (master pushes) |
| **Security** | `codeql.yml` (CodeQL analysis) |
| **Quality** | `qodana_code_quality.yml` (Qodana) |
| **Repository** | Maven Central (Sonatype OSSRH) |
| **Artifact Signing** | GPG (required) |
| **Versioning** | GitHub Release tags |

---

## References

- [Maven Central Publishing Guide](https://central.sonatype.org/publish/publish-guide/)
- [GitHub Actions Documentation](https://docs.github.com/en/actions)
- [Gradle Publishing Maven](https://docs.gradle.org/current/userguide/publishing_maven.html)
- [Sonatype OSSRH Guide](https://central.sonatype.org/pages/ossrh-guide.html)
- [GPG Key Generation](https://central.sonatype.org/pages/working-with-pgp-signatures.html)

---

*Generated by BMAD document-project workflow - Step 6*  
*Date: 2026-05-19T20:44:00Z*
