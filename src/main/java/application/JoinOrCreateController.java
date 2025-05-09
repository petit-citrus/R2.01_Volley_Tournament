package application;

import javafx.fxml.FXML;

public class JoinOrCreateController {
    private String tournament;

    public void setTournament(String tournament) {
        this.tournament = tournament;
    }

    @FXML
    private void handleJoin() throws Exception {
        // Charger la scène pour rejoindre une équipe
        // Passer le nom du tournoi à la scène suivante
    }

    @FXML
    private void handleCreate() throws Exception {
        // Charger la scène pour créer une équipe
        // Passer le nom du tournoi à la scène suivante
    }
}
