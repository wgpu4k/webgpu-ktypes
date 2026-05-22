# 📋 NAGA → KOTLIN : PLAN DE PORT DÉTAILLÉ
## WebGPU-KTypes Shader Transpiler

**Objectif Principal** : Porter le traducteur de shaders **Naga (Rust)** vers **Kotlin** avec un focus sur :
- **Parsing WGSL → IR** (priorité maximale)
- **Conversion IR → Metal/MSL/HLSL/GLSL** (priorité élevée)
- **Validation** via golden files Rust + validateurs natifs
- **Minimalisme des dépendances** (kotlinx.serialization, coroutines uniquement)

---

## 🗺️ STRUCTURE DU PLAN

```
.plan/
├── SUMMARY.md                          ← Ce fichier
│
├── 00_PROJET/                         ← Contexte et organisation
│   ├── 00_objectifs.md                ← Objectifs, scope, non-scope
│   ├── 01_contraintes.md               ← Contraintes techniques et fonctionnelles
│   ├── 02_dependances.md               ← Dépendances autorisées/interdites
│   └── 03_references.md                ← Références Rust (chemins absolus)
│
├── 01_FONDATIONS/                      ← Phase 1 : Core IR (4-6 semaines)
│   ├── 00_ir-structures.md             ← Module, Type, Expression, Statement
│   ├── 01_arena-system.md              ← Arena, Handle, UniqueArena
│   ├── 02_primitive-types.md            ← Scalar, Vector, Matrix, etc.
│   ├── 03_span-diagnostics.md           ← Span, SourceLocation, Diagnostic
│   └── 99_checklist.md                 ← Checklist Phase 1
│
├── 02_PARSING/                         ← Phase 2 : WGSL Parser (6-8 semaines)
│   ├── 00_wgsl-lexer.md                ← Lexer manuel WGSL
│   ├── 01_wgsl-parser.md               ← Parser récursif descendant
│   ├── 02_ast-building.md               ← Construction de l'AST IR
│   ├── 03_type-resolution.md            ← Résolution de types pendant parsing
│   ├── 04_error-handling.md             ← Gestion des erreurs de parsing
│   └── 99_checklist.md                 ← Checklist Phase 2
│
├── 03_PROCESSING/                      ← Phase 3 : Traitement IR (4-6 semaines)
│   ├── 00_constant-evaluator.md         ← Évaluation des expressions constantes
│   ├── 01_typifier.md                   ← Résolution et inférence de types
│   ├── 02_layouter.md                   ← Calcul de layout mémoire
│   ├── 03_namer.md                      ← Nommage des variables
│   ├── 04_validator.md                  ← Validation sémantique du module
│   └── 99_checklist.md                 ← Checklist Phase 3
│
├── 04_BACKENDS/                        ← Phase 4 : Générateurs (8-10 semaines)
│   ├── 00_backend-architecture.md       ← Architecture commune à tous les backends
│   ├── 01_msl-writer.md                 ← IR → Metal Shading Language
│   ├── 02_hlsl-writer.md                ← IR → High-Level Shading Language
│   ├── 03_glsl-writer.md                ← IR → OpenGL Shading Language
│   ├── 04_wgsl-writer.md                ← IR → WebGPU Shading Language
│   └── 99_checklist.md                 ← Checklist Phase 4
│
├── 05_VALIDATION/                      ← Phase 5 : Validation (2-3 semaines)
│   ├── 00_golden-files.md               ← Réutilisation des tests Rust
│   ├── 01_native-validators.md          ← Intégration spirv-val, glslangValidator
│   ├── 02_roundtrip-tests.md            ← Tests WGSL↔IR↔WGSL, etc.
│   └── 99_checklist.md                 ← Checklist Phase 5
│
├── 06_TESTS/                           ← Phase 6 : Infrastructure de test (2 semaines)
│   ├── 00_test-strategy.md              ← Tests unitaires par module
│   ├── 01_test-coverage.md              ← Tests d'intégration
│   └── 99_checklist.md                 ← Checklist Phase 6
│
├── 07_CLI/                             ← Phase 7 : Outil CLI (1-2 semaines)
│   ├── 00_cli-spec.md                   ← Design de l'interface CLI
│   ├── 01_cli-commands.md               ← Implémentation avec Clikt
│   └── 99_checklist.md                 ← Checklist Phase 7
│
└── 99_ANNEXES/                         ← Documentation complémentaire
    ├── 00_glossary.md                   ← Glossaire des termes
    ├── 01_comparison-rust-kotlin.md     ← Mapping Rust → Kotlin
    ├── 02_references.md                 ← Références et liens utiles
    ├── 03_performance-notes.md          ← Notes sur les optimisations
    ├── 04_qodana-guide.md               ← Guide récupération/analyse Qodana
    ├── PR_PROCESS.md                    ← Processus de PR standardisé
    └── 99_checklist.md                  ← Checklist des annexes
```

---

## 📅 JALONS PRINCIPAUX

### ✅ **Phase 0 : Préparation (1 semaine)**
- [x] Finaliser ce plan et obtenir validation
- [x] Configurer projet Gradle Kotlin multi-module
- [x] Mettre en place CI/CD de base
- [x] Cloner et étudier le dépôt Naga Rust

### ✅ **Phase 1 : Fondations IR (4-6 semaines)**
- [x] Implémenter les structures IR de base (Module, Type, Expression, Statement, Function, EntryPoint)
- [x] Implémenter le système Arena/Handle
- [x] Implémenter les types primitifs (Scalar, Vector, Matrix)
- [x] Implémenter Span et diagnostics (Span, SourceLocation, Diagnostic, DiagnosticSeverity)
- [x] **Livrable** : Module IR fonctionnel, tests unitaires basiques (36 tests)

### ✅ **Phase 2 : Parser WGSL (6-8 semaines)**
- [x] Implémenter le lexer WGSL manuel (100% complet, 113+ tests)
- [x] Implémenter le parser récursif descendant (100% complet, 113+ tests)
- [x] Construire l'AST IR à partir du parse tree (AstBuilder implémenté)
- [x] Gérer la résolution de types (TypeIndex/ModuleIndexer/TypeResolver implémentés, 16 tests)
- [x] Implémenter la gestion des erreurs (Diagnostic/ErrorRecovery/PrettyPrintError, 13 tests)
- [x] **Livrable** : 7 fichiers source, 6 fichiers de test, 113+ tests Phase 2, compilation avec -Werror

### ✅ **Phase 3 : Traitement IR (4-6 semaines)**
- [x] Implémenter ConstantEvaluator
- [x] Implémenter Typifier
- [x] Implémenter Layouter
- [x] Implémenter Namer
- [x] Implémenter Validator
- [x] **Livrable** : Module IR validé et optimisé, tests de processing (6 tests)

### ✅ **Phase 4 : Backends (8-10 semaines)**
- [x] Implémenter l'architecture commune des backends
- [x] Implémenter MSL Writer
- [x] Implémenter HLSL Writer
- [x] Implémenter GLSL Writer
- [x] Implémenter WGSL Writer
- [x] **Livrable** : IR → MSL/HLSL/GLSL/WGSL fonctionnels
- [x] **Tests** : Tous les tests de backends utilisent **Kotest FunSpec**

### 🚧 **Phase 5 : Validation (2-3 semaines)**
- [x] Configurer l'infrastructure des golden files (Classes de base et backends)
- [x] Étendre la suite de tests golden (simple.wgsl, complex.wgsl, arithmetic.wgsl opérationnels)
- [x] Intégrer l'infrastructure des validateurs natifs (GlslValidator, MetalValidator, SpirvValidator, HlslValidator implémentés)
- [x] Améliorer la validation GLSL avec support des stages
- [ ] Pipeline de validation complet (Attente de backends complets)
- [ ] Automatisation CI

### 🟢 **Phase 6 : Tests (2 semaines)**
- [x] Implémenter tests unitaires (Phase 1 : ArenaTest, DiagnosticTest, SpanTest - 3 tests)
- [x] Implémenter tests unitaires (Phase 2 : Lexer et Parser - 113+ tests)
- [ ] Implémenter tests d'intégration
- [ ] Implémenter tests de snapshot
- [x] **Livrable** : 116+ tests unitaires, compilation avec -Werror

### 🟢 **Phase 7 : CLI (1-2 semaines)**
- [ ] Designer l'interface CLI
- [ ] Implémenter avec Clikt
- [ ] **Livrable** : Outil CLI fonctionnel

### 🟣 **Phase 8 : Finalisation (2 semaines)**
- [ ] Documentation complète
- [ ] Optimisations de performance
- [ ] Benchmark vs Naga Rust
- [ ] **Livrable** : Version 1.0 stable

---

## 🎯 PRIORITÉS PAR COMPOSANT

### **Priorité Critique (🔴 Must Have - MVP)**
1. **IR Core** (Module, Type, Expression, Statement, Function, EntryPoint)
2. **Arena/Handle System** (Gestion mémoire efficace)
3. **WGSL Parser** (Frontend principal)
4. **MSL Writer** (Backend pour Metal/Apple)
5. **HLSL Writer** (Backend pour DirectX/Windows)
6. **Validator** (Validation sémantique)
7. **Validation natifs** (spirv-val, glslangValidator)

### **Priorité Élevée (🟡 Should Have - V1.0)**
1. **Constant Evaluator** (Optimisation des constantes)
2. **Typifier** (Inférence de types)
3. **Layouter** (Layout mémoire)
4. **GLSL Writer** (Backend pour OpenGL)
5. **WGSL Writer** (Backend pour WebGPU)
6. **Golden Files Tests** (Validation contre Rust)

### **Priorité Moyenne (🟢 Nice to Have - V1.1)**
1. **GLSL Parser** (Frontend GLSL)
2. **SPIR-V Parser/Writer** (Format binaire Vulkan)
3. **Namer** (Nommage automatique)
4. **CLI Tool** (Outil en ligne de commande)
5. **Performance Optimizations** (Object pooling, etc.)

### **Priorité Faible (⚪ Future)**
1. **SPIR-V Binary Support** (Si besoin Vulkan natif)
2. **Android Support** (Kotlin Native)
3. **WebAssembly Support** (Kotlin/JS)

---

## 📊 RÉPARTITION DES EFFORTS

| Phase | Durée | % du Temps | Équipe Recommandée |
|-------|-------|------------|-------------------|
| Phase 0 | 1 semaine | 2% | 1 senior |
| Phase 1 | 5 semaines | 12% | 1 senior |
| Phase 2 | 7 semaines | 17% | 1 senior + 1 junior |
| Phase 3 | 5 semaines | 12% | 1 senior |
| Phase 4 | 9 semaines | 22% | 2 seniors |
| Phase 5 | 3 semaines | 7% | 1 senior |
| Phase 6 | 2 semaines | 5% | 1 junior |
| Phase 7 | 2 semaines | 5% | 1 junior |
| Phase 8 | 2 semaines | 5% | 1 senior |
| **Total** | **36 semaines** | **100%** | **2-3 devs** |

**Durée totale estimée** : **8-9 mois** avec une équipe de 2-3 développeurs

---

## 🚀 LIVRABLES PAR PHASE

| Phase | Livrable Principal | Validation |
|-------|-------------------|------------|
| Phase 1 | Module IR fonctionnel | Tests unitaires IR |
| Phase 2 | Parser WGSL fonctionnel | WGSL → IR → validation manuelle |
| Phase 3 | Traitement IR complet | IR traité → validation sémantique |
| Phase 4 | Backends fonctionnels | IR → MSL/HLSL/GLSL → validation natifs |
| Phase 5 | Pipeline de validation | Tous golden files passent |
| Phase 6 | Suite de tests complète | Couverture > 80% |
| Phase 7 | Outil CLI | CLI fonctionnel avec toutes les options |
| Phase 8 | Version 1.0 | Benchmark, documentation, release |

---

## 🔗 RÉFÉRENCES PRINCIPALES (Rust Naga)

### **Core IR**
- `/Users/chaos/RustroverProjects/wgpu/naga/src/ir/mod.rs` (2900+ lignes) ← **COEUR ABSOLU**
- `/Users/chaos/RustroverProjects/wgpu/naga/src/arena/mod.rs` ← Système Arena/Handle
- `/Users/chaos/RustroverProjects/wgpu/naga/src/lib.rs` ← Exports publics

### **Frontend WGSL**
- `/Users/chaos/RustroverProjects/wgpu/naga/src/front/wgsl/` ← **À étudier en priorité**
  - `mod.rs` ← Point d'entrée
  - `parser.rs` ← Parser principal
  - `lexer.rs` ← Lexer WGSL
  - `type_gen.rs` ← Génération de types

### **Processing**
- `/Users/chaos/RustroverProjects/wgpu/naga/src/proc/mod.rs` ← Traitement IR
- `/Users/chaos/RustroverProjects/wgpu/naga/src/proc/constant_evaluator.rs` ← Évaluateur constantes
- `/Users/chaos/RustroverProjects/wgpu/naga/src/proc/typifier.rs` ← Résolution de types
- `/Users/chaos/RustroverProjects/wgpu/naga/src/proc/layouter.rs` ← Layout mémoire

### **Backends**
- `/Users/chaos/RustroverProjects/wgpu/naga/src/back/msl/` ← MSL Writer
- `/Users/chaos/RustroverProjects/wgpu/naga/src/back/hlsl/` ← HLSL Writer
- `/Users/chaos/RustroverProjects/wgpu/naga/src/back/glsl/` ← GLSL Writer
- `/Users/chaos/RustroverProjects/wgpu/naga/src/back/wgsl/` ← WGSL Writer

### **Validation**
- `/Users/chaos/RustroverProjects/wgpu/naga/src/valid/mod.rs` ← Validator
- `/Users/chaos/RustroverProjects/wgpu/naga/src/valid/expression.rs` ← Validation expressions
- `/Users/chaos/RustroverProjects/wgpu/naga/src/valid/function.rs` ← Validation fonctions

### **Tests**
- `/Users/chaos/RustroverProjects/wgpu/naga/tests/` ← Tests Rust
  - `naga/` ← Tests unitaires
  - `in/` ← Fichiers d'entrée (golden inputs)
  - `out/` ← Fichiers de sortie attendus (golden outputs)

---

## 📌 NOTES IMPORTANTES

1. **Approche incrémentale** : Commencer par le core IR, puis le parser WGSL, puis un backend (MSL recommandé)
2. **Validation continue** : À chaque étape, valider avec les golden files Rust
3. **Minimalisme** : Éviter les dépendances externes sauf kotlinx.serialization et coroutines
4. **Performance** : Optimiser uniquement après avoir une version fonctionnelle
5. **Documentation** : Documenter chaque module avec des exemples d'utilisation
6. **Lisibilité des Tests** : Décomposer les tests complexes en fichiers thématiques pour faciliter la review

---

## 🧪 STRUCTURATION DES TESTS

### **Framework Standard : Kotest FunSpec**

**Tous les nouveaux tests doivent utiliser Kotest FunSpec** pour maintenir la cohérence dans le codebase.

**Structure de base :**
```kotlin
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class MonModuleTest : FunSpec({
    test("nom du test simple") {
        // Code de test
        result shouldBe expected
    }

    context("description du contexte") {
        test("comportement spécifique") {
            // Code de test dans un contexte
        }
    }
})
```

**Bonnes pratiques :**
- Utiliser `test("...")` pour les tests individuels
- Utiliser `context("...")` pour regrouper les tests par scénario
- Préférer les matchers Kotest (`shouldBe`, `shouldHaveSize`, etc.)
- Nommer les tests de manière descriptive

**Exemple complet :**
```kotlin
class ParserTest : FunSpec({
    context("Parsing des expressions") {
        test("parse une addition simple") {
            val result = parse("1 + 2")
            result shouldBe Add(Constant(1), Constant(2))
        }

        test("parse une multiplication imbriquée") {
            val result = parse("(1 + 2) * 3")
            result shouldBe Multiply(Add(Constant(1), Constant(2)), Constant(3))
        }
    }

    context("Gestion des erreurs") {
        test("échec sur syntaxe invalide") {
            shouldThrow<ParseError> {
                parse("1 +*")
            }
        }
    }
})
```

**Migration des tests existants :**
- Convertir les tests `kotlin.test` (@Test) vers FunSpec
- Convertir les tests FreeSpec vers FunSpec pour uniformisation
- Conserver la convention de nommage : `<Nom>Test.kt`

---

## 🔧 OUTILS DE QUALITÉ DE CODE

- **Qodana** : Analyse statique via GitHub Actions (Workflow: `.github/workflows/qodana_code_quality.yml`)
  - **Guide de récupération** : Voir [`.plan/QODANA_GUIDE.md`](./QODANA_GUIDE.md) pour extraire et analyser les rapports
  - **Configuration** : `upload-result: true` activé pour récupérer les artifacts
  - **Artifacts** : `qodana.sarif.json`, `report/index.html`, `qodana-report/`

---

## 🔄 PROCHAINES ÉTAPES

1. Lire et comprendre ce plan en détail
2. **Phase 0 terminée** ✅ : Configuration du projet
3. **Phase 1 terminée ✅** : Fondations IR complètes
   - ✅ Structures IR (Module, Type, Expression, Statement, Function, EntryPoint)
   - ✅ Arena/Handle system
   - ✅ Types primitifs (Scalar, Vector, Matrix, etc.)
   - ✅ Span et diagnostics (Span, SourceLocation, Diagnostic, ShaderError)
   - ✅ Tests unitaires (36 tests passant)
4. **Phase 2 terminée ✅** : Parser WGSL complet
   - ✅ Lexer manuel (113+ tests)
   - ✅ Parser récursif descendant
   - ✅ Construction de l'AST
   - ✅ Résolution de types
   - ✅ Gestion d'erreurs et récupération
5. **Phase 3 terminée ✅** : Traitement IR complet (ConstantEvaluator, Typifier, Layouter, Namer, Validator)
6. **Phase 4 terminée ✅** : Backends (Architecture, MSL, HLSL, GLSL, WGSL)
7. **Phase 5** : Validation (En cours)
8. Valider chaque étape avec les tests correspondants

**Fichier à consulter en premier** : `/Users/chaos/RustroverProjects/wgpu/naga/src/front/wgsl/mod.rs`

**Processus de PR** : Voir [`.plan/PR_PROCESS.md`](./PR_PROCESS.md) pour les conventions de contributeur.
