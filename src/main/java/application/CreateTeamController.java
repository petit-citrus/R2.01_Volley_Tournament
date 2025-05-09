package application;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.*;

public class CreateTeamController {
    @FXML private TextField teamNameField, nameField, firstNameField, emailField, phoneField;
    @FXML private Label messageLabel;

    private String tournament;

    public void setTournament(String tournament) {
        this.tournament = tournament;
    }

    @FXML
    private void handleCreate() {
        String teamName = teamNameField.getText();
        String nom = nameField.getText();
        String prenom = firstNameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        if (teamName.isEmpty() || nom.isEmpty() || prenom.isEmpty() || email.isEmpty()) {
            messageLabel.setText("Fill all fields.");
            return;
        }
        try (Connection conn = DatabaseUtil.getConnection()) {
            int idTournoi = -1, nbEquipes = 0, maxEquipes = 8;
            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT idTournoi, nb_equipes FROM Tournois WHERE nom = ?")) {
                ps.setString(1, tournament);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    idTournoi = rs.getInt("idTournoi");
                    maxEquipes = rs.getInt("nb_equipes");
                }
            }
            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) FROM Equipes WHERE idTournoi = ?")) {
                ps.setInt(1, idTournoi);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) nbEquipes = rs.getInt(1);
            }
            if (nbEquipes >= maxEquipes) {
                messageLabel.setText("No more teams allowed in this tournament.");
                return;
            }
            int idJoueur;
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO Joueurs(nom, prenom, email, telephone) VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, nom);
                ps.setString(2, prenom);
                ps.setString(3, email);
                ps.setString(4, phone);
                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                rs.next();
                idJoueur = rs.getInt(1);
            }
            int idEquipe;
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO Equipes(nom, idTournoi, idCapitaine) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, teamName);
                ps.setInt(2, idTournoi);
                ps.setInt(3, idJoueur);
                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                rs.next();
                idEquipe = rs.getInt(1);
            }
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO Joueurs_Equipes(idJoueur, idEquipe) VALUES (?, ?)")) {
                ps.setInt(1, idJoueur);
                ps.setInt(2, idEquipe);
                ps.executeUpdate();
            }
            messageLabel.setText("Team created and joined successfully!");
        } catch (SQLException e) {
            messageLabel.setText("Error: " + e.getMessage());
        }
    }
}
