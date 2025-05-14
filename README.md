# Volley Tournament Registration

Ce projet est une application JavaFX développée avec Maven permettant la gestion et l’inscription à un tournoi de volley.

## Fonctionnalités

- Interface graphique moderne (JavaFX)
- Consultation des tournois existants
- Création et inscription d’équipes
- Ajout de joueurs aux équipes
- Observation des équipes et de leurs membres

## Base de données

- **Type :** SQLite (`.db` local, inclus dans le projet)
- **Aucune connexion à un serveur SQL externe n’est nécessaire**
- Le fichier `volley_tournament.db` est déjà prêt à l’emploi et se trouve dans `src/main/resources/data/`

## Prérequis

- **Java 17 ou supérieur**
- **Maven** pour la gestion du projet et des dépendances
- **Aucune installation de serveur de base de données requise**
- **SQLite** est embarqué via la dépendance `sqlite-jdbc` (aucune installation séparée nécessaire)

## Installation et lancement

1. **Cloner le dépôt :**
git clone <url-du-depot>
cd Volley

text

2. **Compiler et lancer l’application :**
mvn clean javafx:run

text

3. **C’est tout !**
- L’application s’ouvre et la base de données locale est automatiquement utilisée.
- Vous pouvez inscrire des équipes, ajouter des joueurs et consulter les informations du tournoi.

## Structure du projet

```text
mon-projet/
├── pom.xml
└── src/
├── main/
│ ├── java/
│ │ └── application/
│ │ ├── Main.java
│ │ ├── DatabaseUtil.java
│ │ └── ... (autres classes)
│ └── resources/
│ ├── data/
│ |└── volley_tournament.db
```

## Remarques

- **Aucune configuration de base de données n’est nécessaire** : tout fonctionne en local.
- Si vous souhaitez réinitialiser les données, remplacez simplement le fichier `.db` par une sauvegarde ou une nouvelle version.

## Licence

Projet pédagogique - libre d’utilisation pour l’apprentissage.

---


# Volley Tournament Registration

This project is a JavaFX application developed with Maven for managing and registering teams in a volleyball tournament.

## Features

- Modern graphical interface (JavaFX)
- View existing tournaments
- Create and register teams
- Add players to teams
- Observe teams and their members

## Database

- **Type:** SQLite (local `.db` file included in the project)
- **No external SQL server connection required**
- The `volley_tournament.db` file is ready to use and located in `src/main/resources/data/`

## Prerequisites

- **Java 17 or higher**
- **Maven** for project and dependency management
- **No database server installation required**
- **SQLite** is embedded via the `sqlite-jdbc` dependency (no separate installation needed)

## Installation and Launch

1. **Clone the repository:**
git clone <repository-url>
cd Volley

text

2. **Build and run the application:**
mvn clean javafx:run

text

3. **That's it!**
- The application will launch and automatically use the local database.
- You can register teams, add players, and view tournament information.

## Project Structure

```text
mon-projet/
├── pom.xml
└── src/
├── main/
│ ├── java/
│ │ └── application/
│ │ ├── Main.java
│ │ ├── DatabaseUtil.java
│ │ └── ... (others classes)
│ └── resources/
│ ├── data/
│ │ └── volley_tournament.db
```

text

## Notes

- **No database configuration is required:** everything works locally.
- If you want to reset the data, simply replace the `.db` file with a backup or a new version.

## License

Educational project - free to use for learning purposes.
