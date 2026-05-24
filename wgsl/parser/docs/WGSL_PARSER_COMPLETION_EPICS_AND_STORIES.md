# WGSL Parser Completion - Milestone Plan

*Mise a jour: 2026-05-24*

---

## Contexte & Objectifs

### Situation actuelle

Le parser WGSL du projet `webgpu-ktypes` dispose deja d'une base fonctionnelle solide. Les couches principales sont en place: lexer, parser, AST, indexation des declarations, resolution de types, lowering vers IR et tests dedies.

Les sujets precedemment listes comme a faire pour les enums, les types numeriques abstraits et les predeclared enumerants sont maintenant couverts par le code et par les tests. Le plan de completion doit donc se concentrer sur la robustesse de l'API publique, la qualite des diagnostics, le durcissement du lowering et la verification de conformite sur un corpus WGSL plus large.

### Objectif global

Stabiliser le parser comme composant reutilisable du pipeline WGSL, avec une surface d'API claire, des erreurs exploitables, un lowering explicite et une mesure de conformite recalculable.

---

## Metriques Actuelles

| Domaine | Etat observe | Commentaire |
|---------|--------------|-------------|
| Lexer | Fonctionnel | Couverture dediee sur mots-cles, commentaires, litteraux, operateurs et cas limites numeriques. |
| Parser AST | Fonctionnel | Declarations globales, fonctions, structs, enums, directives, attributs, expressions et control-flow couverts. |
| Types avances | Fonctionnel | Enums, abstract numeric types et predeclared enumerants sont presents et testes. |
| Resolution | Fonctionnelle | `TypeResolver`, `TypeIndex` et `ModuleIndexer` couvrent les references principales et plusieurs cas d'erreur. |
| Lowering AST -> IR | Fonctionnel mais a durcir | Plusieurs cas non supportes echouent explicitement, mais certains fallbacks silencieux restent a clarifier. |
| Diagnostics publics | Partiel | `Parser.errors`, `DiagnosticCollection` et `ErrorRecovery` existent, mais l'API publique `parseWgsl()` ne retourne pas les erreurs. |
| Tests JVM parser | Vert | `:wgsl:parser:jvmTest` passe avec 668 tests, 0 failure, 0 error, 0 skipped. |
| Conformite WGSL globale | A recalculer | Aucun chiffre de conformite final ne doit etre annonce avant une passe dediee sur corpus et spec. |

---

## Milestones

---

### M0 - Baseline actuelle

**Description** :
Documenter l'etat reel du parser et figer une baseline fiable avant les travaux de durcissement.

**Objectif** :
Disposer d'un point de depart verifie, base sur le code actuel et les tests verts, sans reprendre les anciens objectifs obsoletes.

**Livrables** :
- Etat des composants principaux du parser.
- Liste des fonctionnalites deja couvertes.
- Resultat de test de reference pour `:wgsl:parser:jvmTest`.
- Identification des limites connues: API d'erreurs, diagnostics publics, fallbacks du lowering, conformite a recalculer.

**Criteres d'acceptation** :
- [ ] Le document ne presente plus enums, abstract numeric types ou predeclared enumerants comme des travaux a faire.
- [ ] Le resultat JVM `668 tests, 0 failure` est mentionne.
- [ ] Les risques restants sont exprimes comme milestones de stabilisation, pas comme fonctionnalites syntaxiques manquantes.

**Priorite** : Haute  
**Estimation** : 0.5 jour  
**Dependances** : Aucune  
**Etat** : Termine pour cette revision documentaire

---

### M1 - API de parsing et diagnostics

**Description** :
Rendre l'API publique de parsing exploitable par les consommateurs sans devoir instancier directement `Parser` pour recuperer les erreurs.

**Objectif** :
Fournir un resultat de parsing structure, incluant l'AST et les erreurs/diagnostics, tout en conservant une compatibilite raisonnable avec l'API existante.

**Livrables** :
- Une API publique de type resultat, par exemple `ParseResult`, contenant la `TranslationUnit`, les erreurs et un indicateur de succes.
- Une strategie claire entre `ParseError`, `DiagnosticCollection` et `ErrorRecovery`.
- Mise a jour du CLI pour utiliser la nouvelle API et afficher les erreurs de parsing avant resolution/lowering.
- Tests unitaires et integration CLI minimale sur erreur de syntaxe.

**Criteres d'acceptation** :
- [ ] Un appel public permet de parser et de recuperer toutes les erreurs.
- [ ] `parseWgsl()` reste disponible ou dispose d'un chemin de migration documente.
- [ ] Le CLI ne contient plus de commentaire indiquant qu'il ne sait pas recuperer les erreurs de parsing.
- [ ] Les messages d'erreur conservent les spans utiles.
- [ ] Les tests existants continuent de passer.

**Priorite** : Haute  
**Estimation** : 2-3 jours  
**Dependances** : M0  
**Etat** : A faire

---

### M2 - Robustesse du lowering

**Description** :
Durcir la conversion AST -> IR pour eviter que des types ou expressions inconnus soient convertis silencieusement en valeurs par defaut.

**Objectif** :
Faire du lowering une phase explicite: les cas supportes produisent un IR correct, les cas non supportes produisent une erreur claire.

**Livrables** :
- Inventaire des fallbacks silencieux dans `Lowerer`.
- Remplacement des fallbacks ambigus par des erreurs explicites ou par des types opaques documentes.
- Tests couvrant les types inconnus, templates incomplets, tableaux dynamiques et expressions non supportees.
- Verification que les generators ne dependent pas de comportements implicites non documentes.

**Criteres d'acceptation** :
- [ ] Aucun type inconnu n'est converti silencieusement en `f32` sans justification documentee.
- [ ] Les erreurs de lowering indiquent le type d'AST concerne et le contexte utile.
- [ ] Les shaders deja couverts par les tests continuent de produire le meme IR attendu, sauf changement volontaire documente.
- [ ] Les tests parser/lowering restent verts.

**Priorite** : Haute  
**Estimation** : 3-5 jours  
**Dependances** : M1 recommande pour exposer les erreurs proprement  
**Etat** : A faire

---

### M3 - Conformite WGSL reelle

**Description** :
Mesurer la conformite sur des cas derives de la specification et sur des shaders reels, au lieu de maintenir un pourcentage manuel.

**Objectif** :
Remplacer l'ancien chiffre de conformite par un processus reproductible qui indique ce qui passe, ce qui echoue et pourquoi.

**Livrables** :
- Corpus de conformite WGSL organise par categorie: lexer, declarations, types, expressions, statements, attributs, directives, erreurs attendues.
- Tests de non-regression sur shaders reels deja presents dans `tests/golden/inputs`.
- Rapport de conformite genere ou documente, avec liste des ecarts connus.
- Regles de mise a jour du corpus lorsque la specification WGSL evolue.

**Criteres d'acceptation** :
- [ ] La conformite est mesuree par tests ou rapport reproductible, pas par estimation statique.
- [ ] Les cas valides et invalides sont representes.
- [ ] Les ecarts connus sont classes par severite et composant impacte.
- [ ] Le rapport distingue parsing, resolution, lowering et generation.

**Priorite** : Moyenne  
**Estimation** : 4-7 jours  
**Dependances** : M1, M2  
**Etat** : A faire

---

### M4 - Documentation et maintenance

**Description** :
Aligner la documentation du parser avec l'etat reel du code et installer une pratique de maintenance.

**Objectif** :
Eviter le retour d'une documentation obsolescente et rendre les limites connues visibles pour les utilisateurs et mainteneurs.

**Livrables** :
- README du module parser mis a jour avec l'API de parsing recommandee.
- Section limitations connues et comportements non supportes.
- Documentation du pipeline lexer -> parser -> resolver -> lowerer -> IR.
- Historique de maintenance du plan et lien vers le rapport de conformite.

**Criteres d'acceptation** :
- [ ] La documentation ne promet pas de conformite totale sans rapport de preuve.
- [ ] Les exemples d'utilisation montrent la recuperation des diagnostics.
- [ ] Les limites du lowering et de la generation sont explicites.
- [ ] Les references relatives sont valides.

**Priorite** : Moyenne  
**Estimation** : 1-2 jours  
**Dependances** : M1, M3 recommande  
**Etat** : A faire

---

## Roadmap Recommandee

| Ordre | Milestone | Objectif | Estimation | Priorite | Dependances | Etat |
|-------|-----------|----------|------------|----------|-------------|------|
| 1 | M0 - Baseline actuelle | Acter l'etat reel et supprimer l'ancien cadrage obsolete | 0.5 jour | Haute | Aucune | Termine pour cette revision |
| 2 | M1 - API de parsing et diagnostics | Exposer proprement les erreurs de parsing | 2-3 jours | Haute | M0 | A faire |
| 3 | M2 - Robustesse du lowering | Supprimer les fallbacks ambigus et clarifier les echecs | 3-5 jours | Haute | M1 recommande | A faire |
| 4 | M3 - Conformite WGSL reelle | Mesurer la conformite avec un corpus reproductible | 4-7 jours | Moyenne | M1, M2 | A faire |
| 5 | M4 - Documentation et maintenance | Garder docs, limitations et exemples alignes | 1-2 jours | Moyenne | M1, M3 recommande | A faire |

### Sequence conseillee

1. Finaliser M0 avec ce document et conserver le resultat de test comme baseline.
2. Implementer M1 avant toute extension importante, car les consommateurs doivent pouvoir lire les erreurs.
3. Traiter M2 avant d'annoncer une conformite large, pour eviter les succes apparents produits par des fallbacks.
4. Construire M3 avec un corpus partage entre parser, resolver, lowering et generators.
5. Terminer par M4, puis maintenir ce plan a chaque evolution majeure du parser.

---

## Criteres de Succes Globaux

- [ ] Le parser expose une API publique qui retourne AST et diagnostics.
- [ ] Les erreurs de parsing, resolution et lowering sont distinguables.
- [ ] Les fallbacks silencieux du lowering sont supprimes ou documentes avec justification.
- [ ] La conformite WGSL est mesuree par un corpus reproductible.
- [ ] Tous les tests existants du module parser restent verts.
- [ ] La documentation utilisateur decrit l'etat reel, les limites et le chemin recommande.

---

## Documents de Reference

1. **Specification WGSL**: https://www.w3.org/TR/WGSL/
2. **Resume WGSL local**: [../../../docs/WGSL_SPECIFICATION_SUMMARY.md](../../../docs/WGSL_SPECIFICATION_SUMMARY.md)
3. **README du parser**: [../README.md](../README.md)
4. **Code source parser**: [../src/commonMain/kotlin/](../src/commonMain/kotlin/)
5. **Tests parser**: [../src/commonTest/kotlin/](../src/commonTest/kotlin/)

---

## Historique

| Date | Version | Auteur | Changements |
|------|---------|--------|-------------|
| 2025-05-19 | 1.0 | Mistral Vibe | Creation initiale du plan de completion. |
| 2026-05-24 | 2.0 | Codex | Remplacement de l'ancien cadrage par un plan milestones aligne sur l'etat actuel du parser. |
