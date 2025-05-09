package application;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JoinTeamController {
    @FXML private ComboBox<String> teamCombo;
    @FXML private TextField nameField, firstNameField, emailField, phoneField;
    @FXML private Label messageLabel;

    private String tournament;

    public void setTournament(String tournament) {
        this.tournament = tournament;
        loadTeams();
    }

    private void loadTeams() {
        List<String> teams = new ArrayList<>();
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT e.nom FROM Equipes e " +
                             "JOIN Tournois t ON e.idTournoi = t.idTournoi " +
                             "WHERE t.nom = ? AND " +
                             "(SELECT COUNT(*) FROM Joueurs_Equipes je WHERE je.idEquipe = e.idEquipe) < ?")) {
            ps.setString(1, tournament);
            ps.setInt(2, 8); // à adapter selon la config du tournoi
            ResultSet rs = ps.executeQuery();
            while (rs.next()) teams.add(rs.getString("nom"));
        } catch (SQLException e) { e.printStackTrace(); }
        teamCombo.setItems(FXCollections.observableArrayList(teams));
    }

    @FXML
    private void handleJoin() {
        String team = teamCombo.getValue();
        String nom = nameField.getText();
        String prenom = firstNameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        if (team == null || nom.isEmpty() || prenom.isEmpty() || email.isEmpty()) {
            messageLabel.setText("Fill all fields.");
            return;
        }
        try (Connection conn = DatabaseUtil.getConnection()) {
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
            int idEquipe = -1;
            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT e.idEquipe FROM Equipes e JOIN Tournois t ON e.idTournoi = t.idTournoi WHERE e.nom = ? AND t.nom = ?")) {
                ps.setString(1, team);
                ps.setString(2, tournament);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) idEquipe = rs.getInt("idEquipe");
            }
            if (idEquipe != -1) {
                try (PreparedStatement ps = conn.prepareStatement(
                        "INSERT INTO Joueurs_Equipes(idJoueur, idEquipe) VALUES (?, ?)")) {
                    ps.setInt(1, idJoueur);
                    ps.setInt(2, idEquipe);
                    ps.executeUpdate();
                }
                messageLabel.setText("Joined team successfully!");
            }
        } catch (SQLException e) {
            messageLabel.setText("Error: " + e.getMessage());
        }
    }
}
