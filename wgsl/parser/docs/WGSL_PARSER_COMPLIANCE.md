# WGSL Parser Compliance Report

*Mise a jour: 2026-05-24*

## Objectif

Ce rapport remplace les anciens pourcentages manuels par une base de verification reproductible. La conformite est suivie par un corpus de tests qui distingue les etapes du pipeline:

- Parsing: le source WGSL produit une `TranslationUnit` et des erreurs syntaxiques exploitables.
- Resolution: les references de types, valeurs et fonctions sont verifiees par `TypeResolver`.
- Lowering: l'AST resolu ou parse est converti en IR, ou echoue explicitement si le cas n'est pas supporte.

## Corpus initial

Le corpus executable est defini dans `wgsl/parser/src/commonTest/kotlin/parser/ComplianceCorpusTest.kt`.

| Etape | Cas couverts | Intention |
|-------|--------------|-----------|
| Parsing | fonction valide, declarations struct/alias, attribut orphelin invalide | Verifier les succes syntaxiques de base et la remontee d'erreurs. |
| Resolution | reference de type forward valide, type de parametre inconnu invalide | Verifier que le resolver accepte les references declaratives et rejette les inconnues. |
| Lowering | retour scalaire valide, type inconnu invalide, constructeur `array()` vide invalide | Verifier que le lowering termine avec un module IR non vide pour un cas valide ou echoue sans fallback silencieux. |

## Interpretation

Ce corpus ne prouve pas une conformite complete a WGSL. Il installe une structure extensible pour mesurer les ecarts par categorie et par phase. Tout nouveau cas doit indiquer:

- le fragment WGSL teste;
- l'etape responsable (`parse`, `resolve`, `lower`);
- le resultat attendu;
- la raison si l'echec est volontaire ou lie a une limitation connue.

## Commande de verification

```bash
rtk ./gradlew :wgsl:parser:jvmTest --tests io.ygdrasil.wgsl.parser.ComplianceCorpusTest
```

## Ecarts connus

- Le corpus reste minimal et doit etre etendu avec des exemples derives de la specification WGSL.
- Les generators GLSL/HLSL/MSL/WGSL ont leurs propres golden tests et ne sont pas encore integres a ce rapport.
- Les erreurs de lowering sont maintenant explicites pour plusieurs fallbacks, mais l'inventaire complet doit rester suivi dans le plan milestones.
