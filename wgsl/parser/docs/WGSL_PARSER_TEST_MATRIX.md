# WGSL Parser Unit Test Matrix

## Objectif

Cette matrice transforme la suite de tests `:wgsl:parser` en suivi metier WGSL. Elle sert a relier les tests a des comportements de langage observables, pas seulement a des classes Kotlin ou a des details d'implementation.

Elle complete la baseline coverage de `WGSL_PARSER_COVERAGE.md`: le seuil Kover indique le volume de code execute, cette matrice indique les capacites WGSL que les tests doivent prouver.

## Baseline verifiee

Commande de reference:

```bash
rtk ./gradlew :wgsl:parser:jvmTest
```

Etat courant:

| Mesure | Valeur |
|--------|--------|
| Tests JVM parser | 682 tests, 0 failure |
| Couverture lignes JVM | 65.022% |
| Seuil Kover JVM | 65% |
| Couverture package parser | 54.8% |
| Couverture package lexer | 87.4% |
| Couverture package AST | 89.8% |

## Regles De Qualite

Chaque nouvelle suite de tests unitaires WGSL doit respecter ces regles:

- nommer un comportement WGSL utilisateur, par exemple `parses switch selectors`, `rejects unknown enum member`, `lowers resource binding`;
- couvrir au moins un cas valide et un cas invalide quand la grammaire ou la validation le permet;
- verifier le diagnostic, le span ou l'etage d'echec pour les cas invalides;
- eviter les assertions qui ne prouvent que la forme interne de l'AST sans intention de langage;
- mettre a jour cette matrice quand une categorie passe de `partiel` a `couvert`.

## Matrice Fonctionnelle

| Domaine WGSL | Comportements attendus | Suites existantes | Statut | Prochain gap |
|--------------|------------------------|-------------------|--------|--------------|
| Lexing - identifiants | identifiants ASCII, underscores, mots-cles prefixes, caracteres non WGSL | `LexerBasicTest`, `LexerIdentifierTest`, `LexerKeywordTest` | couvert | Ajouter uniquement les nouveaux cas spec |
| Lexing - commentaires | commentaires ligne/bloc/doc, nesting, EOF unterminated | `LexerCommentTest`, `LexerCommentEdgeCaseTest` | couvert | Aucun gap prioritaire |
| Lexing - nombres et litteraux | entiers, floats, suffixes, hex floats, underscores, invalid numerics, strings | `LexerLiteralTest`, `LexerNumericEdgeCaseTest`, `LiteralSuffixTest` | couvert | Relier les cas invalides aux diagnostics parser si necessaire |
| Lexing - operateurs et templates | maximal munch, shifts, assignments, punctuation, `<>`, `>>` | `LexerOperatorTest`, `LexerOperatorEdgeCaseTest` | partiel | Ajouter cas parser templates imbriques et ambiguity `>>` |
| Directives globales | `enable`, `requires`, `diagnostic` | `DirectiveParserTest` | partiel | Cas invalides et spans de diagnostic |
| Declarations globales | `fn`, `struct`, `alias/type`, `const`, `override`, `var`, `const_assert` | `GlobalDeclParserTest`, `IntegrationTest`, `ControlFlowParserTest` | partiel | Matrice explicite par declaration avec succes/echec |
| Attributs - entrees/sorties et ressources | `@location`, `@builtin`, `@binding`, `@group`, `@interpolate`, `@invariant` | `AttributeValidationTest`, `LexerShaderIntegrationTest` | partiel | Contraintes par emplacement, combinaison et diagnostics invalides |
| Attributs - entrypoints et pipeline | `@vertex`, `@fragment`, `@compute`, `@workgroup_size`, `@must_use`, `@id`, `@diagnostic` | `AttributeValidationTest`, `LexerShaderIntegrationTest`, `DirectiveParserTest` | partiel | `@id` pour `override`, stages invalides, formes `@diagnostic` invalides |
| Attributs - layout memoire | `@align`, `@size` | aucune suite dediee actuelle | a couvrir | Ajouter parsing et diagnostics sur membres de struct; `MemoryLayoutTest` couvre seulement les anciens tokens `packed` / `aligned` |
| Types scalaires et composes | scalaires, vectors, matrices, arrays, atomics, pointers, samplers, textures | `TypeParserTest`, `TypeIndexTest`, `PointerAccessModeTest`, `Texture1DArrayTest` | partiel | Arrays avec tailles/overrides, pointeurs invalides, texture parameterization |
| Abstract numeric types | `abstract int`, `abstract float`, compatibilite, parsing, lowering | `AbstractNumericKeywordTest`, `AbstractTypeAllTests`, `AbstractTypeParserTest`, `TypeResolverAbstractTypeTest`, `AbstractTypeLoweringTest` | couvert | Maintenir en regression |
| Enums et enumerants | user enums, member access, predeclared enumerants, invalid values | `EnumParserTest`, `EnumIntegrationTest`, `TypeResolverEnumTest`, `PredeclaredEnumerantAllTests`, `PredeclaredEnumerantValidationTest` | couvert | Maintenir en regression |
| Expressions | precedence, calls, constructors, member access, indexing, bitcast | `ExpressionParserTest`, `LexerExpressionTest`, `PredeclaredEnumerantParserTest` | partiel | Table de precedence, rejet des formes non WGSL comme `?:`, et diagnostics par operateur |
| Statements | blocks, if, switch, loop, while, for, break, continue, return, discard, phony assignment | `StatementParserTest`, `ControlFlowParserTest` | partiel | Cas invalides par statement et recovery |
| Diagnostics et recovery | erreurs syntaxiques, erreurs multiples, sync points, pretty print | `DiagnosticTest`, `ErrorRecoveryTest`, `ParserTest` | partiel | Spans metier sur erreurs reelles de grammaire WGSL |
| Resolver - types et scopes | types connus/inconnus, forward refs, scopes locaux, enum references | `TypeResolverTest`, `TypeResolverEnumTest`, `TypeIndexTest`, `ModuleIndexerTest` | partiel | Scopes locaux, shadowing, fonctions, cycles et builtins |
| Lowering - types | preservation scalar/vector/matrix/struct, arrays, nested structs | `TypeLoweringTest`, `AbstractTypeLoweringTest` | partiel | Matrices, arrays, pointers et texture/sampler IR |
| Lowering - expressions | literals, binary ops, constructors, member access | `ExpressionLoweringTest`, `MemberAccessTest` | partiel | Calls, indexing, unary ops, bitcast, mixed numeric |
| Lowering - statements | return, block, variables, assignments, loops, switch, discard | `StatementLoweringTest`, `LoopLoweringTest`, `SymbolResolutionTest` | partiel | If/else, break/continue, continuing, error paths |
| Lowering - globals et entrypoints | globals, bindings, entrypoint stages | `GlobalLoweringTest`, `FunctionLoweringTest`, `IntegrationLoweringTest` | partiel | Workgroup size, overrides, resource spaces |
| Corpus de conformite unitaire | cas spec representatifs par phase parse/resolve/lower | `ComplianceCorpusTest` | partiel | Etendre avec cas WGSL spec et shaders reels reduits |

## Priorites De Remediation

1. Parser/resolver/lowering: le package `io/ygdrasil/wgsl/parser` est le plus bas en couverture ligne et porte la majorite du risque metier.
2. Expressions et statements: ces categories structurent la plupart des shaders reels et doivent couvrir precedence, invalides et recovery.
3. Resolver scopes/builtins: les echecs goldens Phase 0 montrent beaucoup de problemes de resolution de noms et builtins.
4. Lowering sans fallback silencieux: chaque type ou expression non supporte doit produire une erreur explicite testee.
5. Lexer: deja bien couvert, a etendre seulement pour les nouveaux cas de specification ou regressions.

## Definition De Couverture Metier

Une categorie peut passer a `couvert` quand:

- tous les comportements listes ont au moins un test positif;
- les formes invalides principales ont un test negatif;
- le test negatif verifie l'erreur ou l'etage d'echec attendu;
- les tests sont nommes par comportement WGSL;
- le seuil Kover est releve si la couverture ligne progresse.

`100%` signifie ici: toutes les categories supportees par le parseur disposent d'un test metier explicite, et le code concerne est couvert par Kover sauf exclusion rare, documentee et revue.

## Commandes De Suivi

Tests unitaires parser:

```bash
rtk ./gradlew :wgsl:parser:jvmTest
```

Coverage et verification:

```bash
rtk ./gradlew :wgsl:parser:koverLogJvm :wgsl:parser:koverVerifyJvm
```

Check complet module parser:

```bash
rtk ./gradlew :wgsl:parser:check
```
