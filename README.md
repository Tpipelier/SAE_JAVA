# SAE S2 : Aide Décisionnelle pour Centres de Santé (BUT Informatique 1ère année)

Projet réalisé dans le cadre de la **SAE S2 (2025-2026)** du BUT Informatique de l'IUT Lyon 1 (Université Claude Bernard Lyon 1). Ce projet mobilise les compétences acquises en développement orienté objet, algorithmique des graphes et gestion de projet.

## 📋 Description du projet

L'application MedMap est un outil d'aide à la décision pour le responsable d'un district de santé en zone défavorisée. Elle permet d'optimiser les flux logistiques (médicaments, patients, personnel) entre des centres de santé (Maternités, Blocs opératoires, Centres de nutrition) en tenant compte de la fiabilité, de la distance et de la durée des trajets.

## 🛠 Stack Technique

* **Langage** : Java (JDK 21 LTS)


* **Interface Graphique** : Librairie *Graphstream* pour la visualisation des graphes


* **Environnement de développement** : NetBeans IDE



## 🚀 Fonctionnalités principales

* **Gestion de graphes** : Génération aléatoire de districts ou importation via fichiers `.csv`.


* **Visualisation interactive** : Représentation ergonomique du district avec filtres de lecture.


* **Recherche d'itinéraires** : Calcul du chemin le plus court et du chemin optimisé selon la fiabilité des routes.


* **Analyse détaillée** : Consultation des statistiques de proximité pour chaque centre (via clic-droit).


* **Optimisation des tournées** : Résolution du problème de livraison avec 1 ou 2 camions.



## 🧠 Stratégies Algorithmiques

Nous avons mis en œuvre plusieurs algorithmes pour répondre au défi de l'optimisation des tournées :

* **Approche Gloutonne** : Algorithme de base pour une construction rapide de solutions.


* **Algorithme de Prim** : Pour la gestion des structures d'arbres couvrants.


* **Algorithme de Christofides** : Approximation pour l'optimisation des tournées.


* **Recuit Simulé** : Meta-heuristique pour optimiser les performances logistiques.



## 🛠 Instructions d'installation

1. **Prérequis** : JDK 21 installé.
2. **Clonage** : Cloner le dépôt sur votre machine.
3. **Configuration** : Configurer le projet sous NetBeans avec le JDK 21.


4. **Données** : Placer les fichiers `.csv` des districts dans le dossier `/data` pour les charger via l'interface.



## 🏗 Qualité et Organisation

* **Architecture** : Conception en packages respectant les principes du modèle MVC.


* **Documentation** : Javadoc générée pour toutes les méthodes publiques.


* **Tests** : Suite de tests unitaires intégrée pour valider la robustesse de l'application.



## 👥 Équipe

* Pipelier Theo G3S2B
* Chazal Alexis G3S2A
* Ferchach Walid G3S2A

---

Projet universitaire réalisé à l'IUT Lyon 1 - BUT Informatique 2026.
