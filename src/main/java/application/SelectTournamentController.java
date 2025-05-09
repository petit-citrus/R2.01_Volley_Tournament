package application;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SelectTournamentController {
    @FXML private ComboBox<String> tournamentCombo;

    @FXML
    public void initialize() {
        List<String> tournaments = new ArrayList<>();
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT nom FROM Tournois")) {
            while (rs.next()) tournaments.add(rs.getString("nom"));
        } catch (SQLException e) { e.printStackTrace(); }
        tournamentCombo.setItems(FXCollections.observableArrayList(tournaments));
    }

    @FXML
    private void handleNext() throws Exception {
        String selectedTournament = tournamentCombo.getValue();
        if (selectedTournament != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("JoinOrCreate.fxml"));
            Scene scene = new Scene(loader.load());
            JoinOrCreateController controller = loader.getController();
            controller.setTournament(selectedTournament);
            Stage stage = (Stage) tournamentCombo.getScene().getWindow();
            stage.setScene(scene);
        }
    }

    @FXML
    private void handleObserve() throws Exception {
        // Charger la scène d’observation (à créer)
    }
}
