package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ObservationController {
    @FXML private ComboBox<String> tournamentCombo;
    @FXML private TableView<TeamOverview> teamsTable;
    @FXML private TableColumn<TeamOverview, String> teamNameColumn, captainColumn, playersColumn, playerCountColumn;
    @FXML private Label messageLabel;

    @FXML
    public void initialize() {
        List<String> tournaments = new ArrayList<>();
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT nom FROM Tournois")) {
            while (rs.next()) tournaments.add(rs.getString("nom"));
        } catch (SQLException e) { }
        tournamentCombo.setItems(FXCollections.observableArrayList(tournaments));
        tournamentCombo.setOnAction(e -> loadTeams());
        teamNameColumn.setCellValueFactory(data -> data.getValue().teamNameProperty());
        captainColumn.setCellValueFactory(data -> data.getValue().captainProperty());
        playersColumn.setCellValueFactory(data -> data.getValue().playersProperty());
        playerCountColumn.setCellValueFactory(data -> data.getValue().playerCountProperty());
    }

    private void loadTeams() {
        String tournament = tournamentCombo.getValue();
        if (tournament == null) return;
        ObservableList<TeamOverview> list = FXCollections.observableArrayList();
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT e.nom as team, j.nom as captain, e.idEquipe " +
                             "FROM Equipes e " +
                             "JOIN Joueurs j ON e.idCapitaine = j.idJoueur " +
                             "JOIN Tournois t ON e.idTournoi = t.idTournoi " +
                             "WHERE t.nom = ?")) {
            ps.setString(1, tournament);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int idEquipe = rs.getInt("idEquipe");
                StringBuilder players = new StringBuilder();
                int playerCount = 0;
                try (PreparedStatement ps2 = conn.prepareStatement(
                        "SELECT j.nom, j.prenom FROM Joueurs_Equipes je JOIN Joueurs j ON je.idJoueur = j.idJoueur WHERE je.idEquipe = ?")) {
                    ps2.setInt(1, idEquipe);
                    ResultSet rs2 = ps2.executeQuery();
                    while (rs2.next()) {
                        if (players.length() > 0) players.append(", ");
                        players.append(rs2.getString("prenom")).append(" ").append(rs2.getString("nom"));
                        playerCount++;
                    }
                }
                list.add(new TeamOverview(rs.getString("team"), rs.getString("captain"), players.toString(), String.valueOf(playerCount)));
            }
        } catch (SQLException e) {
            messageLabel.setText("Error: " + e.getMessage());
        }
        teamsTable.setItems(list);
    }
}
