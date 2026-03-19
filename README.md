# JavaWeb - Dashboard CRUD Servlet

## 📋 Description
Une application Java EE légère utilisant uniquement des Servlets (sans JSP) pour gérer un catalogue de produits. Elle implémente un tableau de bord complet pour le cycle de vie des données (CRUD) avec une persistance locale SQLite.

## 🚀 Fonctionnalités
- **Tableau de Bord** : Visualisation centralisée des produits.
- **Opérations CRUD** : Insertion, Lecture, Modification et Suppression de produits.
- **Persistance Locale** : Utilisation d'une base de données SQLite (`produits.db`).
- **Nomenclature Française** : Code source, variables et méthodes nommés intégralement en Français.
- **Seeding Automatique** : Initialisation de la base avec des données de test au premier lancement.

## 🛠️ Stack Technique
- **Backend** : Java 17+, Jakarta Servlet 6.0.
- **Serveur** : Apache Tomcat 10.1 (via Maven Cargo Plugin).
- **Base de Données** : SQLite JDBC.
- **Build System** : Maven.

## 📦 Prérequis
- Java Development Kit (JDK) 17 ou plus.
- Maven 3.8+.

## ⚙️ Installation
1. Clonez le dépôt :
   ```bash
   git clone https://github.com/BenSira99/JavaWeb.git
   ```
2. Accédez au dossier :
   ```bash
   cd JavaWeb
   ```

## 🔧 Configuration (.env)
Aucune configuration externe n'est requise, la base de données SQLite est créée localement à la racine.

## 🏃 Lancement
Utilisez le script Maven wrapper fourni pour compiler et lancer le serveur :
```cmd
.\mvnw.cmd clean package cargo:run
```
L'application sera accessible sur : [http://localhost:8080/JavaJ2ee/dashboard](http://localhost:8080/JavaJ2ee/dashboard)

## 🧪 Tests
Pas de suite de tests automatisés pour le moment. Tests manuels effectués sur les opérations CRUD.

## 🐳 Docker
*(Non configuré pour ce projet)*

## 📁 Structure du Projet
```text
src/main/java/com/bensira/javaj2ee/
├── BaseDonnees.java         # Gestion JDBC et CRUD
├── Produit.java             # Modèle de données
├── TableauBordServlet.java  # Page principale
├── FormulaireServlet.java   # Ajout de produits
├── ModificationServlet.java # Édition de produits
└── SuppressionServlet.java   # Retrait de produits
```

## 🔒 Sécurité
- Utilisation de `PreparedStatement` pour prévenir les injections SQL.
- Séparation des responsabilités entre Servlets.

## 📄 Licence
Distribué sous la licence MIT. Voir le fichier `LICENSE` pour plus d'informations.

## 👤 Auteur — BenSira99
**Ben Sira** - Ingénieur Logiciel Senior Full-Stack, DevOps, Sécurité & IA
