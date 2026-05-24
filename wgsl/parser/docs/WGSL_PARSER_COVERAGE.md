# WGSL Parser Test Coverage

## Objectif

Le module `:wgsl:parser` dispose maintenant d'une baseline de couverture JVM mesurable et verifiable par Gradle. Cette baseline sert de garde-fou CI pour eviter les regressions pendant la correction du ticket #16.

L'objectif produit reste une couverture metier exhaustive du parseur WGSL. Le seuil actuel n'est donc pas une definition de "done" a 100%, mais le point de depart mesure pour les tranches suivantes.

## Baseline actuelle

| Scope | Commande | Resultat verifie |
|-------|----------|------------------|
| Tests unitaires parser JVM | `rtk ./gradlew :wgsl:parser:jvmTest` | 682 tests, 0 failure |
| Couverture lignes JVM | `rtk ./gradlew :wgsl:parser:koverLogJvm` | 65.022% |
| Seuil de verification | `rtk ./gradlew :wgsl:parser:koverVerifyJvm` | minimum 65% |

La couverture Kover est limitee aux tests JVM. Les sources communes executees par `jvmTest` sont incluses, mais les tests JS, Native, Android device et Wasm ne sont pas mesures par cette baseline.

## Commandes

Generer le rapport XML JVM:

```bash
rtk ./gradlew :wgsl:parser:koverXmlReportJvm
```

Generer le rapport HTML JVM:

```bash
rtk ./gradlew :wgsl:parser:koverHtmlReportJvm
```

Afficher la couverture dans les logs:

```bash
rtk ./gradlew :wgsl:parser:koverLogJvm
```

Verifier le seuil CI:

```bash
rtk ./gradlew :wgsl:parser:koverVerifyJvm
```

Le task `:wgsl:parser:check` depend aussi de `:wgsl:parser:koverVerifyJvm`.

## Strategie vers 100%

Les prochaines corrections doivent privilegier des tests unitaires avec une dimension metier:

- lexer: tokens WGSL spec, erreurs lexicales, commentaires, nombres, identifiants, attributs;
- parser: declarations, types, expressions, statements, diagnostics et recovery;
- resolver: noms, types, enums, predeclared enumerants, abstract numeric types;
- lowering: absence de fallback silencieux, erreurs explicites, preservation des types et bindings;
- integration: execution de tous les goldens WGSL disponibles et comparaison reproductible.

Chaque hausse de couverture doit relever le seuil `koverVerifyJvm` au niveau mesure, arrondi prudemment a l'entier inferieur, pour rendre la progression non regressionnelle.
