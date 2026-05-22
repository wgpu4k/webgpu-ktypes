# Validation par Fichiers Golden

## Sommaire

1. [Principe](#principe)
2. [Structure des Fichiers](#structure-des-fichiers)
3. [Intégration Kotlin](#intégration-kotlin)
4. [Génération des Snapshots](#génération-des-snapshots)
5. [Validation des Résultats](#validation-des-résultats)
6. [Maintenance](#maintenance)

---

## Principe

### Concept

Les **fichiers golden** (ou *golden master files*) sont une technique de test de régression qui consiste à :

1. **Capturer** la sortie d'un processus (sérialisation IR, code généré MSL/HLSL/GLSL)
2. **Stocker** cette sortie dans un fichier de référence
3. **Comparer** les exécutions futures contre ces références

Tout écart signale une régression ou une modification intentionnelle du comportement.

### Avantages

- **Détection de régression** : Changement de comportement involontaire immédiatement identifié
- **Documentation vivante** : Les fichiers golden servent de documentation concrète
- **Validation croisée** : Permet de vérifier la cohérence entre backends
- **Intégration CI** : Exécutable automatiquement dans les pipelines

### Cas d'Usage

| Scénario | Fichier Source | Fichier Golden | Backend |
|----------|---------------|----------------|---------|
| Parsing WGSL | `input.wgsl` | `output.ir.json` | IR Sérialisée |
| Génération MSL | `input.wgsl` | `output.msl` | MSL |
| Génération HLSL | `input.wgsl` | `output.hlsl` | HLSL |
| Génération GLSL | `input.wgsl` | `output.glsl` | GLSL |
| Round-trip WGSL | `input.wgsl` | `output.wgsl` | WGSL |

---

## Structure des Fichiers

### Organisation des Répertoires

```
.webgpu-ktypes/
├── tests/
│   ├── golden/
│   │   ├── inputs/
│   │   │   ├── expressions.wgsl
│   │   │   ├── functions.wgsl
│   │   │   ├── types.wgsl
│   │   │   ├── control-flow.wgsl
│   │   │   ├── builtins.wgsl
│   │   │   └── ...
│   │   └── outputs/
│   │       ├── ir/
│   │       │   └── *.json
│   │       ├── msl/
│   │       │   └── *.metal
│   │       ├── hlsl/
│   │       │   └── *.hlsl
│   │       ├── glsl/
│   │       │   └── *.glsl
│   │       └── wgsl/
│   │           └── *.wgsl
└── wgsl/
    └── tests/
        └── src/jvmTest/kotlin/io/ygdrasil/wgsl/tests/
            ├── GoldenTestBase.kt
            ├── MslGoldenTest.kt
            └── ...
```

### Répertoire Source Rust

Les fichiers golden de référence sont situés dans :
```
/Users/chaos/RustroverProjects/wgpu/naga/tests/
├── in/
│   └── wgsl/
│       ├── const-exprs.wgsl
│       ├── conv-bvec.wgsl
│       ├── entry-point-args.wgsl
│       ├── global-variable.wgsl
│       ├── local-variable.wgsl
│       ├── matrix.wgsl
│       ├── pointers.wgsl
│       ├── structs.wgsl
│       ├── type-inference.wgsl
│       └── ... (50+ fichiers)
└── out/
    └── wgsl/
        └── *.wgsl (snapshots de sortie)
```

### Catégories de Tests

#### 1. Tests de Parsing (WGSL → IR)
Fichiers : `tests/golden/inputs/wgsl/*.wgsl`
Sortie : `tests/golden/outputs/ir/*.json`

Valide que le parser produit la bonne représentation IR.

#### 2. Tests de Génération MSL
Fichiers : `tests/golden/inputs/wgsl/*.wgsl`
Sortie : `tests/golden/outputs/msl/*.msl`

Valide que le backend MSL produit du code Metal valide.

#### 3. Tests de Génération HLSL
Fichiers : `tests/golden/inputs/wgsl/*.wgsl`
Sortie : `tests/golden/outputs/hlsl/*.hlsl`

Valide que le backend HLSL produit du code DirectX valide.

#### 4. Tests de Génération GLSL
Fichiers : `tests/golden/inputs/wgsl/*.wgsl`
Sortie : `tests/golden/outputs/glsl/*.glsl`

Valide que le backend GLSL produit du code OpenGL valide.

#### 5. Tests Round-Trip (WGSL → IR → WGSL)
Fichiers : `tests/golden/inputs/wgsl/*.wgsl`
Sortie : `tests/golden/outputs/wgsl/*.wgsl`

Valide que la sérialisation WGSL est cohérente avec l'entrée.

### Nommage des Fichiers

- **Input** : `{feature}-{scenario}.wgsl` (ex: `const-exprs.wgsl`, `matrix-multiplication.wgsl`)
- **Output IR** : `{feature}-{scenario}.ir.json`
- **Output MSL** : `{feature}-{scenario}.msl`
- **Output HLSL** : `{feature}-{scenario}.hlsl`
- **Output GLSL** : `{feature}-{scenario}.glsl`
- **Output WGSL** : `{feature}-{scenario}.wgsl`

---

## Intégration Kotlin

### Dépendances

Les tests utilisent **Kotest** et sont localisés dans le module `:wgsl:tests`.

```kotlin
// wgsl/tests/build.gradle.kts
dependencies {
    implementation(project(":wgsl:core"))
    implementation(project(":wgsl:msl"))
    // ... autres backends
    
    testImplementation(libs.bundles.kotest)
}
```

### Classe de Base pour les Tests Golden

```kotlin
// wgsl/tests/src/jvmTest/kotlin/io/ygdrasil/wgsl/tests/GoldenTestBase.kt

package io.ygdrasil.wgsl.tests

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.ygdrasil.wgsl.back.BackendRegistry
import io.ygdrasil.wgsl.parser.Lowerer
import io.ygdrasil.wgsl.parser.TypeResolver
import io.ygdrasil.wgsl.parser.parseWgsl
import java.nio.file.Files
import java.nio.file.Paths

abstract class GoldenTestBase(val backendName: String) : FunSpec({

    registerAllBackends()
    val goldenUpdate = System.getenv("GOLDEN_UPDATE")?.toBoolean() ?: false
    val rootDir = findProjectRoot()
    val inputDir = rootDir.resolve("tests/golden/inputs")
    val outputBaseDir = rootDir.resolve("tests/golden/outputs")

    context("$backendName Golden Tests") {
        val inputFiles = Files.list(inputDir)
            .filter { it.toString().endsWith(".wgsl") }
            .toList()

        inputFiles.forEach { inputFile ->
            val fileName = inputFile.fileName.toString()
            test("Golden test: $fileName") {
                val source = Files.readString(inputFile)
                
                // 1. Parse
                val unit = parseWgsl(source)
                
                // 2. Resolve types
                val resolver = TypeResolver()
                val resolutionResult = resolver.resolve(unit)
                
                // 3. Lower to IR
                val lowerer = Lowerer()
                val module = lowerer.lower(resolutionResult.resolvedUnit)
                
                // 4. Generate backend code
                val writer = BackendRegistry.DEFAULT.get(backendName) 
                val output = writer.write(module, io.ygdrasil.wgsl.valid.ModuleInfo())
                
                // 5. Compare or Update
                val outputFile = outputBaseDir.resolve(backendName)
                    .resolve(fileName.replace(".wgsl", getExtension(backendName)))
                
                if (goldenUpdate || !Files.exists(outputFile)) {
                    Files.writeString(outputFile, output)
                } else {
                    val expected = Files.readString(outputFile)
                    output shouldBe expected
                }
            }
        }
    }
})
```
```

### Tests Concrets

Toutes les classes de test de backend héritent de `GoldenTestBase` :

#### Test de Génération MSL
```kotlin
// wgsl/tests/src/jvmTest/kotlin/io/ygdrasil/wgsl/tests/MslGoldenTest.kt
package io.ygdrasil.wgsl.tests

class MslGoldenTest : GoldenTestBase("msl")
```

#### Test de Génération HLSL
```kotlin
// wgsl/tests/src/jvmTest/kotlin/io/ygdrasil/wgsl/tests/HlslGoldenTest.kt
package io.ygdrasil.wgsl.tests

class HlslGoldenTest : GoldenTestBase("hlsl")
```

#### Test de Génération GLSL
```kotlin
// wgsl/tests/src/jvmTest/kotlin/io/ygdrasil/wgsl/tests/GlslGoldenTest.kt
package io.ygdrasil.wgsl.tests

class GlslGoldenTest : GoldenTestBase("glsl")
```

#### Test Round-Trip WGSL
```kotlin
// wgsl/tests/src/jvmTest/kotlin/io/ygdrasil/wgsl/tests/WgslGoldenTest.kt
package io.ygdrasil.wgsl.tests

class WgslGoldenTest : GoldenTestBase("wgsl")
```

---

## Génération des Snapshots

### Initialisation

1. **Créer la structure de répertoires** :
```bash
mkdir -p tests/golden/{inputs/wgsl,outputs/{ir,msl,hlsl,glsl,wgsl}}
```

2. **Copier les fichiers WGSL depuis Rust** :
```bash
# Depuis le répertoire wgpu
cp /Users/chaos/RustroverProjects/wgpu/naga/tests/in/wgsl/*.wgsl \
   /Users/chaos/IdeaProjects/webgpu-ktypes/tests/golden/inputs/wgsl/
```

### Génération Initiale

Exécuter les tests avec `GOLDEN_UPDATE=true` pour générer les fichiers de référence :

```bash
# macOS/Linux
GOLDEN_UPDATE=true ./gradlew test --tests "io.ygdrasil.wgsl.test.golden.*"

# Windows (PowerShell)
$env:GOLDEN_UPDATE="true"
./gradlew test --tests "io.ygdrasil.wgsl.test.golden.*"
```

### Mise à Jour des Snapshots

Lorsqu'un changement intentionnel est apporté (correction de bug, nouvelle feature) :

1. Mettre à jour le code
2. Exécuter avec `GOLDEN_UPDATE=true`
3. Vérifier les diffs dans git
4. Valider les changements

```bash
# Voir les fichiers modifiés
git diff tests/golden/outputs/

# Valider les changements
git add tests/golden/outputs/
git commit -m "Update golden files for [reason]"
```

---

## Validation des Résultats

### Vérification Manuelle

Pour valider un fichier MSL généré :

```bash
# Compiler avec Metal Shader Compiler (macOS)
xcrun -sdk macosx metal /path/to/output.msl -o /dev/null

# Voir les erreurs
xcrun -sdk macosx metal --print-ast /path/to/output.msl
```

Pour valider un fichier HLSL :

```bash
# Utiliser DXC (Windows)
dxc /path/to/output.hlsl -T ps_6_0 /Zi /Fd output.pdb /Fe output.bin

# Ou FXC (legacy)
fxc /path/to/output.hlsl /T ps_5_0 /Fo output.obj
```

### Intégration CI

```yaml
# .github/workflows/test.yml
name: Test

on: [push, pull_request]

jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      
      - name: Set up JDK
        uses: actions/setup-java@v3
        with:
          java-version: '17'
          distribution: 'temurin'
      
      - name: Run Golden Tests
        run: ./gradlew test --tests "io.ygdrasil.wgsl.test.golden.*"
      
      - name: Update Golden Files (if changed)
        if: github.ref == 'refs/heads/main'
        run: |
          GOLDEN_UPDATE=true ./gradlew test --tests "io.ygdrasil.wgsl.test.golden.*"
          git config --global user.name "GitHub Actions"
          git config --global user.email "actions@github.com"
          git add tests/golden/outputs/
          git commit -m "Update golden files [skip ci]" || echo "No changes to commit"
          git push
```

---

## Maintenance

### Ajout d'un Nouveau Test

1. **Créer le fichier WGSL** :
   - Placer dans `tests/golden/inputs/wgsl/{name}.wgsl`
   - Suivre les conventions de nommage

2. **Ajouter au test** :
   - Ajouter le nom au `@ValueSource` dans le test approprié

3. **Générer les golden files** :
   ```bash
   GOLDEN_UPDATE=true ./gradlew test --tests "io.ygdrasil.wgsl.test.golden.*" -k "{name}"
   ```

4. **Valider les résultats** :
   - Vérifier que le code généré est correct
   - Valider avec les compilateurs natifs si nécessaire

### Suppression d'un Test

1. Supprimer le fichier WGSL
2. Supprimer les fichiers golden associés
3. Supprimer le nom du `@ValueSource`

### Migration des Fichiers Rust

Pour importer de nouveaux fichiers depuis le projet Rust :

```bash
#!/bin/bash
RUST_DIR="/Users/chaos/RustroverProjects/wgpu/naga/tests/in/wgsl"
KTYPE_DIR="/Users/chaos/IdeaProjects/webgpu-ktypes/tests/golden/inputs/wgsl"

# Copier les nouveaux fichiers
rsync -av --ignore-existing "$RUST_DIR/" "$KTYPE_DIR/"

# ou copier tous les fichiers (écrase les existants)
cp "$RUST_DIR"/*.wgsl "$KTYPE_DIR/"
```

### Bonnes Pratiques

1. **Commits atomiques** : Un commit par changement logique
2. **Messages clairs** : Expliquer POURQUOI les golden files ont changé
3. **Revue de code** : Toujours review les changements de golden files
4. **Tests ciblés** : Exécuter seulement les tests concernés lors du développement

```bash
# Exécuter un test spécifique
./gradlew test --tests "io.ygdrasil.wgsl.test.golden.MslGoldenTests.test MSL generation:matrix"
```

---

## Références

- [Naga Rust Tests](https://github.com/gfx-rs/naga/tree/trunk/tests)
- [Golden Master Testing Pattern](https://martinfowler.com/bliki/GoldenMaster.html)
- [JUnit 5 Parameterized Tests](https://junit.org/junit5/docs/current/user-guide/#writing-tests-parameterized-tests)
- [AssertJ Assertions](https://assertj.github.io/doc/#assertj-core)
