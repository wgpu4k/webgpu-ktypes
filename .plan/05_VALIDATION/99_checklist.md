# Validation - Checklist

## Phase 5 : Validation
**Objectif** : Mettre en place un système complet de validation pour garantir la qualité et la cohérence du code généré par WebGPU-KTypes.

---

## 📋 Structure

- [ ] **00_golden-files.md** - Documentation des tests par fichiers golden ✅
- [ ] **01_native-validators.md** - Documentation des validateurs natifs (spirv-val, glslangValidator, Metal, DXC/FXC) ✅
- [ ] **02_roundtrip-tests.md** - Documentation des tests de round-trip (WGSL → IR → WGSL) ✅
- [ ] **99_checklist.md** - Checklist complète pour la Phase 5 ✅

---

## 🎯 Objectifs de la Phase

### Validation Fonctionnelle
- [ ] Valider que le parser WGSL produit une IR correcte
- [ ] Valider que les backends (MSL, HLSL, GLSL, WGSL) génèrent du code valide
- [ ] Valider que le round-trip (WGSL → IR → WGSL) préserve la sémantique
- [ ] Valider que les golden files sont cohérents avec les sources Rust

### Validation Technique
- [ ] Intégrer spirv-val pour la validation SPIR-V
- [ ] Intégrer glslangValidator pour la validation GLSL
- [ ] Intégrer le compilateur Metal pour la validation MSL
- [ ] Intégrer DXC/FXC pour la validation HLSL
- [ ] Mettre en place les tests de round-trip

### Validation de Régression
- [ ] Détecter les régressions dans le parser
- [ ] Détecter les régressions dans les backends
- [ ] Détecter les régressions dans la sérialisation
- [ ] Intégrer avec le pipeline CI

---

## 📁 Structure des Répertoires

### Répertoires de Test
- [X] Créer `tests/golden/` pour les fichiers golden
  - [X] `tests/golden/inputs/` pour les fichiers WGSL d'entrée
  - [X] `tests/golden/outputs/` pour les fichiers de sortie générés
    - [ ] `tests/golden/outputs/ir/` pour la sérialisation IR
    - [X] `tests/golden/outputs/msl/` pour le code MSL
- [X] `tests/golden/outputs/hlsl/` pour le code HLSL
    - [X] `tests/golden/outputs/glsl/` pour le code GLSL
    - [X] `tests/golden/outputs/wgsl/` pour le round-trip WGSL
  - [ ] `tests/golden/reports/` pour les rapports de validation

- [ ] Créer `tests/roundtrip/` pour les tests de round-trip
  - [ ] `tests/roundtrip/inputs/` pour les fichiers WGSL
  - [ ] `tests/roundtrip/outputs/` pour les fichiers générés
  - [ ] `tests/roundtrip/reports/` pour les rapports

### Répertoires de Code
- [X] Créer `wgsl:tests` module dans `wgsl/tests/`
- [X] Créer `GoldenTestBase.kt` dans `wgsl/tests/src/jvmTest/kotlin/io/ygdrasil/wgsl/tests/`
- [ ] Créer `src/test/kotlin/dev/gfxrs/naga/test/validator/` pour les validateurs natifs
- [ ] Créer `src/test/kotlin/dev/gfxrs/naga/test/roundtrip/` pour les tests de round-trip

---

## 📄 Fichiers de Test (Golden Files)

### Fichiers WGSL à Importer depuis Rust

Les fichiers suivants doivent être copiés depuis `/Users/chaos/RustroverProjects/wgpu/naga/tests/in/wgsl/` :

#### Core Language
- [x] `const-exprs.wgsl` - Expressions constantes
- [ ] `conv-bvec.wgsl` - Conversion de vecteurs booléens (Non trouvé dans Naga)
- [x] `const_assert.wgsl` - Assertions de compilation
- [x] `separate-entry-points.wgsl` - Points d'entrée séparés
- [x] `globals.wgsl` - Variables globales
- [x] `local-const.wgsl` - Constantes locales (Remplacé local-variable.wgsl)
- [ ] `matrix.wgsl` - Opérations matricielles (Remplacé par math-functions.wgsl)
- [x] `pointers.wgsl` - Pointeurs
- [ ] `structs.wgsl` - Structures (Remplacé par struct-layout.wgsl)
- [x] `type-inference.wgsl` - Inférence de type

#### Expressions
- [x] `access.wgsl` - Accès aux membres
- [ ] `arithmetic.wgsl` - Opérations arithmétiques (Remplacé par math-functions.wgsl)
- [x] `array-in-ctor.wgsl` - Tableaux dans les constructeurs
- [x] `bits.wgsl` - Opérations binaires (bitwise)
- [x] `bitcast.wgsl` - Opérations de bitcast
- [x] `operators.wgsl` - Opérateurs généraux
- [ ] `bool.wgsl` - Opérations booléennes (Inclus dans d'autres tests)
- [ ] `builtin.wgsl` - Fonctions builtin (Remplacé par math-functions.wgsl)
- [ ] `comparison.wgsl` - Comparaisons (Inclus dans d'autres tests)
- [x] `functions.wgsl` - Appels de fonction
- [x] `conversions.wgsl` - Conversions et initialisations (init.wgsl)

#### Control Flow
- [x] `break-if.wgsl` - break if (boucle loop)
- [x] `control-flow.wgsl` - Flux de contrôle général
- [ ] `discard.wgsl` - Instruction discard (Inclus dans d'autres tests)
- [ ] `for.wgsl` - Boucles for (Inclus dans control-flow.wgsl)
- [ ] `if.wgsl` - Conditionnelles if (Inclus dans control-flow.wgsl)
- [ ] `loop.wgsl` - Boucles (Inclus dans control-flow.wgsl)
- [ ] `switch.wgsl` - Instructions switch (Inclus dans control-flow.wgsl)
- [ ] `while.wgsl` - Boucles while (Inclus dans control-flow.wgsl)

#### Shader Stages
- [x] `separate-entry-points.wgsl` - Couvre Vertex, Fragment, Compute
- [ ] `compute.wgsl` - Inclus dans separate-entry-points.wgsl
- [ ] `fragment.wgsl` - Inclus dans separate-entry-points.wgsl
- [ ] `vertex.wgsl` - Inclus dans separate-entry-points.wgsl

#### Avancé
- [x] `atomicOps.wgsl` - Opérations atomiques
- [x] `binding-arrays.wgsl` - Bindings de ressources
- [x] `derivative.wgsl` - Dérivées (standard.wgsl)
- [x] `image.wgsl` - Accès aux images
- [x] `interpolate.wgsl` - Interpolation
- [x] `modf.wgsl` - Fonction modf (math-functions.wgsl)
- [x] `packed-vec3-bitcast.wgsl` - Packing (remplace packing.wgsl)
- [x] `sample-cube-array-depth-lod.wgsl` - Échantillonnage (remplace sampler.wgsl)
- [x] `push-constants.wgsl` - Classes de stockage (push_constant)
- [x] `struct-layout.wgsl` - Layout des structures
- [x] `storage-textures.wgsl` - Accès aux textures
- [x] `type-alias.wgsl` - Alias de type
- [x] `workgroup-uniform-load.wgsl` - Variables de workgroup
- [x] `abstract-types-const.wgsl` - Types abstraits (const)
- [x] `abstract-types-texture.wgsl` - Types abstraits (texture)
- [x] `overrides.wgsl` - Constantes de spécialisation
- [x] `lexical-scopes.wgsl` - Portées lexicales

### Génération Initiale des Golden Files
- [x] Copier les fichiers WGSL depuis Rust
- [x] Exécuter les tests avec `GOLDEN_UPDATE=true` pour générer les fichiers de référence
- [ ] Valider manuellement les fichiers générés
- [ ] Commiter les golden files initiaux

---

## 🔧 Implémentation des Validateurs

### Validateur SPIR-V
- [x] Créer `SpirvValidator.kt` dans `src/test/kotlin/dev/gfxrs/naga/test/validator/`
- [x] Implémenter la détection de `spirv-val`
- [x] Implémenter la validation des fichiers SPIR-V
- [x] Intégrer avec `ValidatorFactory`

### Validateur GLSL
- [x] Créer `GlslValidator.kt` dans `src/test/kotlin/dev/gfxrs/naga/test/validator/`
- [x] Implémenter la détection de `glslangValidator`
- [x] Implémenter la validation des fichiers GLSL
- [x] Gérer les différentes versions de GLSL (450, 460, etc.)
- [x] Gérer les différents stages (vertex, fragment, compute)
- [x] Intégrer avec `ValidatorFactory`

### Validateur MSL
- [x] Créer `MetalValidator.kt` dans `src/test/kotlin/dev/gfxrs/naga/test/validator/`
- [x] Implémenter la détection du compilateur Metal
- [x] Implémenter la validation des fichiers MSL
- [x] Gérer les différentes plateformes (macOS, iOS, simulator)
- [x] Intégrer avec `ValidatorFactory`

### Validateur HLSL
- [x] Créer `HlslValidator.kt` dans `src/test/kotlin/dev/gfxrs/naga/test/validator/`
- [x] Implémenter la détection de DXC/FXC
- [x] Implémenter la validation des fichiers HLSL
- [ ] Gérer les différents shader models (ps_5_0, ps_6_0, etc.)
- [x] Préférer DXC sur FXC
- [x] Intégrer avec `ValidatorFactory`

### Factory de Validateurs
- [x] Créer `BackendValidator.kt` (interface commune)
- [x] Créer `ValidatorFactory.kt`
- [x] Implémenter la détection automatique des validateurs disponibles
- [x] Fournir des méthodes pour obtenir les validateurs par type de backend

---

## 🔄 Tests de Round-Trip

### Normaliseur WGSL
- [ ] Créer `WgslNormalizer.kt` dans `src/test/kotlin/dev/gfxrs/naga/test/roundtrip/`
- [ ] Implémenter `removeComments()` pour supprimer les commentaires
- [ ] Implémenter `normalizeWhitespace()` pour normaliser les espaces
- [ ] Implémenter `normalizeIdentifiers()` pour normaliser les identifiants générés
- [ ] Implémenter `normalizeLiterals()` pour normaliser les littéraux numériques
- [ ] Implémenter `normalizeForSemanticComparison()` pour la comparaison sémantique

### Analyseur de Différences
- [ ] Créer `DifferenceAnalyzer.kt` dans `src/test/kotlin/dev/gfxrs/naga/test/roundtrip/`
- [ ] Implémenter `Difference` data class
- [ ] Implémenter `DifferenceType` enum
- [ ] Implémenter `DifferencePattern` data class
- [ ] Implémenter `analyze()` pour analyser les différences
- [ ] Implémenter la détection des différences acceptables
- [ ] Implémenter la détection des différences problématiques

### Validateur de Round-Trip
- [ ] Créer `RoundTripValidator.kt` dans `src/test/kotlin/dev/gfxrs/naga/test/roundtrip/`
- [ ] Implémenter l'interface `BackendValidator`
- [ ] Implémenter `validate()` pour valider le round-trip
- [ ] Intégrer avec `ValidatorFactory`

### Tests Concrets
- [ ] Créer `RoundTripTests.kt` pour les tests paramétrés
- [ ] Créer `TargetedRoundTripTests.kt` pour les tests ciblés
- [ ] Créer `CrossBackendConsistencyTests.kt` pour la cohérence multi-backend
- [ ] Configurer les tests avec JUnit 5
- [ ] Utiliser `@ParameterizedTest` pour les tests en masse
- [ ] Utiliser `@ValueSource` pour fournir les noms de fichiers

### Configuration
- [ ] Créer `RoundTripConfig.kt` pour la configuration
- [ ] Définir les `acceptablePatterns`
- [ ] Définir les `problematicPatterns`
- [ ] Implémenter `isAcceptableDifference()`
- [ ] Implémenter `isProblematicDifference()`

---

## 📊 Rapports et Metrics

### Génération de Rapports
- [ ] Créer `RoundTripReport.kt` dans `src/test/kotlin/dev/gfxrs/naga/test/roundtrip/`
- [ ] Implémenter `generateTextReport()` pour les rapports texte
- [ ] Implémenter `generateHtmlReport()` pour les rapports HTML
- [ ] Implémenter `saveToFile()` pour sauvegarder les rapports
- [ ] Implémenter `generateSummaryReport()` pour les rapports de synthèse

### Collecte de Metrics
- [ ] Créer `RoundTripMetrics.kt` dans `src/test/kotlin/dev/gfxrs/naga/test/roundtrip/`
- [ ] Implémenter `recordResult()` pour enregistrer les résultats
- [ ] Implémenter `getStatistics()` pour obtenir les statistiques
- [ ] Implémenter `getMostCommonDifferences()` pour les différences les plus fréquentes
- [ ] Implémenter `clear()` pour réinitialiser les metrics
- [ ] Implémenter `RoundTripStatistics` data class

### Listener JUnit
- [ ] Créer `RoundTripTestListener.kt` pour collecter les metrics
- [ ] Intégrer avec JUnit Platform
- [ ] Enregistrer automatiquement les résultats des tests

---

## 🔌 Intégration CI

### GitHub Actions (macOS)
- [ ] Créer `.github/workflows/validation-macos.yml`
- [ ] Configurer l'environnement macOS
- [ ] Installer les dépendances (JDK, glslangValidator)
- [ ] Exécuter les tests de validation
- [ ] Upload les rapports en cas d'échec

### GitHub Actions (Windows)
- [ ] Créer `.github/workflows/validation-windows.yml`
- [ ] Configurer l'environnement Windows
- [ ] Installer les dépendances (JDK, Vulkan SDK)
- [ ] Exécuter les tests de validation
- [ ] Upload les rapports en cas d'échec

### GitHub Actions (Linux)
- [ ] Créer `.github/workflows/validation-linux.yml`
- [ ] Configurer l'environnement Linux
- [ ] Installer les dépendances (JDK, Vulkan Tools, glslang-tools, spirv-tools)
- [ ] Exécuter les tests de validation
- [ ] Upload les rapports en cas d'échec

### Workflow Principal
- [ ] Créer `.github/workflows/test.yml` ou mettre à jour l'existant
- [ ] Intégrer les tests de validation
- [ ] Configurer les conditions d'exécution
- [ ] Optimiser le temps d'exécution

---

## ⚙️ Configuration et Scripts

### Scripts Gradle
- [ ] Configurer `build.gradle.kts` pour les tests
- [ ] Ajouter les dépendances de test (JUnit 5, AssertJ)
- [ ] Configurer le plugin `kotlinx-serialization` si nécessaire
- [ ] Configurer les tâches de test

### Variables d'Environnement
- [ ] Documenter `GOLDEN_UPDATE=true` pour la mise à jour des golden files
- [ ] Documenter `VULKAN_SDK` pour la détection des outils Vulkan
- [ ] Documenter les chemins des exécutables

### Documentation
- [ ] Documenter la procédure de mise à jour des golden files
- [ ] Documenter la configuration des validateurs
- [ ] Documenter l'exécution des tests localement
- [ ] Documenter l'interprétation des rapports

---

## 🧪 Tests Unitaires

### Tests des Validateurs
- [ ] Créer `NativeValidatorTests.kt`
- [ ] Tester la détection des validateurs
- [ ] Tester la validation MSL avec des shaders simples
- [ ] Tester la validation GLSL avec des shaders simples
- [ ] Tester la validation HLSL avec des shaders simples
- [ ] Tester la validation SPIR-V avec des shaders simples

### Tests des Golden Files
- [ ] Créer `IrGoldenTests.kt` pour les tests IR
- [X] Créer `MslGoldenTests.kt` pour les tests MSL
- [X] Créer `HlslGoldenTests.kt` pour les tests HLSL
- [X] Créer `GlslGoldenTests.kt` pour les tests GLSL
- [X] Créer `WgslRoundTripTests.kt` pour les tests de round-trip

### Tests des Composants
- [ ] Tester `WgslNormalizer` avec différents cas
- [ ] Tester `DifferenceAnalyzer` avec différentes différences
- [ ] Tester `RoundTripValidator` avec différents scénarios
- [ ] Tester `RoundTripConfig` pour la configuration
- [ ] Tester `RoundTripReport` pour la génération de rapports
- [ ] Tester `RoundTripMetrics` pour la collecte de metrics

---

## 📈 Coverage des Tests

### Coverage par Catégorie
- [ ] Expressions constantes (100%)
- [ ] Types (100%)
- [ ] Variables (100%)
- [ ] Fonctions (100%)
- [ ] Structures (100%)
- [ ] Tableaux (100%)
- [ ] Vecteurs et Matrices (100%)
- [ ] Flux de contrôle (100%)
- [ ] Points d'entrée (100%)
- [ ] Bindings (100%)
- [ ] Builtins (100%)

### Objectifs de Coverage
- [ ] Coverage des instructions : > 95%
- [ ] Coverage des expressions : > 95%
- [ ] Coverage des types : > 95%
- [ ] Coverage des backends : > 90%

---

## 🎉 Critères d'Acceptation

### Pour la Phase 5
- [ ] Tous les fichiers de documentation créés
- [ ] Tous les fichiers WGSL importés depuis Rust
- [ ] Tous les golden files générés pour les backends cibles (MSL, HLSL, GLSL, WGSL)
- [ ] Tous les validateurs natifs intégrés (glslangValidator, Metal Compiler, DXC)
- [ ] Tous les tests de round-trip implémentés et passants
- [ ] Intégration CI configurée et fonctionnelle
- [ ] Documentation complète des procédures

### Pour chaque Backend
- [ ] MSL : Validation avec Metal Compiler sur macOS
- [ ] HLSL : Validation avec DXC sur Windows
- [ ] GLSL : Validation avec glslangValidator sur toutes les plateformes
- [ ] SPIR-V : Validation avec spirv-val (optionnel)
- [ ] WGSL : Round-trip validation sur toutes les plateformes

### Pour chaque Catégorie de Test
- [ ] Parsing : Tous les fichiers WGSL parsés avec succès
- [ ] Génération : Tous les backends génèrent du code valide
- [ ] Round-trip : Tous les fichiers passent le test de round-trip
- [ ] Régression : Détection automatique des régressions

---

## 📅 Planning

### Semaine 1-2 : Préparation
- [ ] Finaliser la structure des répertoires
- [ ] Importer les fichiers WGSL depuis Rust
- [ ] Créer les classes de base pour les tests
- [ ] Implémenter les interfaces communes

### Semaine 3-4 : Golden Files
- [ ] Implémenter `GoldenTestBase`
- [ ] Créer les tests golden pour chaque backend
- [ ] Générer les golden files initiaux
- [ ] Valider et commiter les golden files

### Semaine 5-6 : Validateurs Natifs
- [ ] Implémenter `GlslValidator`
- [ ] Implémenter `MetalValidator`
- [ ] Implémenter `HlslValidator`
- [ ] Implémenter `SpirvValidator`
- [ ] Implémenter `ValidatorFactory`

### Semaine 7-8 : Round-Trip
- [ ] Implémenter `WgslNormalizer`
- [ ] Implémenter `DifferenceAnalyzer`
- [ ] Implémenter `RoundTripValidator`
- [ ] Créer les tests de round-trip
- [ ] Configurer les listes blanches de différences

### Semaine 9-10 : Intégration
- [ ] Implémenter `RoundTripReport`
- [ ] Implémenter `RoundTripMetrics`
- [ ] Configurer l'intégration CI
- [ ] Finaliser la documentation

---

## 🔍 Vérification

### Vérification des Fichiers
- [ ] Vérifier que tous les fichiers de documentation existent
- [ ] Vérifier que tous les fichiers de code existent
- [ ] Vérifier que tous les tests compilent
- [ ] Vérifier que tous les tests passent

### Vérification des Golden Files
- [ ] Vérifier que tous les fichiers WGSL sont présents
- [ ] Vérifier que tous les golden files sont générés
- [ ] Vérifier que les golden files sont cohérents

### Vérification CI
- [ ] Vérifier que les workflows CI existent
- [ ] Vérifier que les workflows CI passent
- [ ] Vérifier que les artefacts sont générés en cas d'échec

---

## 📚 Documentation

### Documentation Technique
- [ ] Documenter l'architecture des tests de validation
- [ ] Documenter l'intégration des validateurs natifs
- [ ] Documenter les tests de round-trip
- [ ] Documenter la configuration CI

### Documentation Utilisateur
- [ ] Documenter comment exécuter les tests localement
- [ ] Documenter comment mettre à jour les golden files
- [ ] Documenter comment interpréter les rapports
- [ ] Documenter comment ajouter de nouveaux tests

---

## ✅ État Actuel

| Élément | État | Date | Notes |
|---------|------|------|-------|
| 00_golden-files.md | ✅ | 2024-XX-XX | Complété |
| 01_native-validators.md | ✅ | 2024-XX-XX | Complété |
| 02_roundtrip-tests.md | ✅ | 2024-XX-XX | Complété |
| 99_checklist.md | ✅ | 2026-05-16 | Mise à jour infrastructure |
| Structure des répertoires | ✅ | 2026-05-16 | Créé (inputs/outputs) |
| Fichiers WGSL importés | ✅ | 2026-05-17 | 20 fichiers importés depuis Naga |
| Validateurs natifs | ✅ | 2026-05-16 | Infrastructure implémentée (GLSL, MSL) |
| Tests de round-trip | [/] | 2026-05-16 | Infrastructure prête (backend WGSL) |
| Intégration CI | ⬜ | - | À configurer |

---

## 🎯 Prochaines Étapes

1. [ ] **Phase 6 - Tests** : Créer les documents pour la stratégie de test et la couverture
2. [ ] **Phase 7 - CLI** : Créer les documents pour l'interface en ligne de commande
3. [ ] **Phase 99 - Annexes** : Créer les documents annexes (glossaire, comparaison Rust/Kotlin)

---

## 📞 Contacts et Ressources

### Ressources Internes
- Projet Rust : `/Users/chaos/RustroverProjects/wgpu/naga/`
- Fichiers golden Rust : `/Users/chaos/RustroverProjects/wgpu/naga/tests/`
- Documentation WGSL : `docs/wgsl/`
- Spécification WebGPU : [https://gpuweb.github.io/gpuweb/](https://gpuweb.github.io/gpuweb/)

### Outils Externes
- SPIRV-Tools : [https://github.com/KhronosGroup/SPIRV-Tools](https://github.com/KhronosGroup/SPIRV-Tools)
- glslang : [https://github.com/KhronosGroup/glslang](https://github.com/KhronosGroup/glslang)
- DirectX Shader Compiler : [https://github.com/microsoft/DirectXShaderCompiler](https://github.com/microsoft/DirectXShaderCompiler)
- Vulkan SDK : [https://vulkan.lunarg.com/sdk/home](https://vulkan.lunarg.com/sdk/home)

### Documentation de Référence
- [WGSL Specification](https://gpuweb.github.io/gpuweb/wgsl/)
- [Metal Shading Language](https://developer.apple.com/metal/Metal-Shading-Language-Specification.pdf)
- [HLSL Documentation](https://docs.microsoft.com/en-us/windows/win32/directxhlsl/dx-graphics-hlsl)
- [GLSL Specification](https://www.khronos.org/opengl/wiki/Core_Language_(GLSL))

---

## 📝 Notes

### Notes d'Implémentation
- Utiliser `kotlinx.serialization` pour la sérialisation JSON de l'IR
- Utiliser les coroutines Kotlin pour les opérations I/O asynchrones
- Suivre les conventions de nommage Kotlin
- Utiliser les data classes pour les structures de données
- Utiliser les sealed classes pour les hiérarchies fermées
- Utiliser les extension functions pour ajouter des fonctionnalités

### Notes de Performance
- Les tests de validation peuvent être lents (appels aux compilateurs natifs)
- Prévoir un cache pour les golden files
- Exécuter les tests de validation en parallèle quand possible
- Limiter les tests de validation aux fichiers modifiés

### Notes de Compatibilité
- Les validateurs natifs peuvent ne pas être disponibles sur toutes les plateformes
- Prévoir des fallbacks (skip des tests) quand les outils ne sont pas disponibles
- Documenter clairement les prérequis pour chaque validateur

---

**Dernière mise à jour** : 2026-05-17
**Responsable** : Équipe WebGPU-KTypes
**Statut** : En cours (Import massif Naga terminé)
