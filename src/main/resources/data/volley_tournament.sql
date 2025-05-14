-- Base de données : volley_tournament

DROP TABLE IF EXISTS equipes;
DROP TABLE IF EXISTS joueurs;
DROP TABLE IF EXISTS joueurs_equipes;
DROP TABLE IF EXISTS tournois;

CREATE TABLE IF NOT EXISTS Tournois (
  idTournoi INTEGER PRIMARY KEY AUTOINCREMENT,
  nom TEXT NOT NULL,
  date TEXT NOT NULL,
  duree INTEGER NOT NULL,
  nb_equipes INTEGER NOT NULL,
  configuration TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS Joueurs (
  idJoueur INTEGER PRIMARY KEY AUTOINCREMENT,
  nom TEXT NOT NULL,
  prenom TEXT NOT NULL,
  email TEXT NOT NULL UNIQUE,
  telephone TEXT
);

CREATE TABLE IF NOT EXISTS Equipes (
  idEquipe INTEGER PRIMARY KEY AUTOINCREMENT,
  nom TEXT NOT NULL,
  idTournoi INTEGER NOT NULL,
  idCapitaine INTEGER NOT NULL,
  UNIQUE(nom, idTournoi)
  -- Les clés étrangères peuvent être ajoutées si tu veux
);

CREATE TABLE IF NOT EXISTS joueurs_equipes (
  idJoueur INTEGER NOT NULL,
  idEquipe INTEGER NOT NULL,
  PRIMARY KEY (idJoueur, idEquipe)
);

-- Données

INSERT INTO equipes (idEquipe, nom, idTournoi, idCapitaine) VALUES
(1, 'Les Aigles', 1, 1),
(2, 'Les Tigres', 1, 2),
(3, 'Les Dauphins', 2, 3);

INSERT INTO joueurs (idJoueur, nom, prenom, email, telephone) VALUES
(1, 'Martin', 'Lucas', 'lucas.martin@email.com', '0601020304'),
(2, 'Dupont', 'Sophie', 'sophie.dupont@email.com', '0611223344'),
(3, 'Bernard', 'Paul', 'paul.bernard@email.com', '0622334455'),
(4, 'Leroy', 'Emma', 'emma.leroy@email.com', '0633445566'),
(5, 'Petit', 'Hugo', 'hugo.petit@email.com', '0644556677'),
(6, 'Roux', 'Julie', 'julie.roux@email.com', '0655667788');

INSERT INTO joueurs_equipes (idJoueur, idEquipe) VALUES
(1, 1),
(2, 2),
(3, 1),
(3, 3),
(4, 1),
(4, 3),
(5, 2),
(6, 2);

INSERT INTO tournois (idTournoi, nom, date, duree, nb_equipes, configuration) VALUES
(1, 'Tournoi Printemps', '2024-06-15', 6, 8, '6vs6'),
(2, 'Tournoi Été', '2024-07-20', 8, 8, '8vs8');
