package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;

import java.util.ArrayList;
import java.util.List;

public class SelectTournamentController {
    @FXML private ComboBox<String> tournamentCombo;

    @FXML
    public void initialize() {
        List<String> tournaments = new ArrayList<>();
        try (var conn = DatabaseUtil.getConnection();
             var stmt = conn.createStatement();
             var rs = stmt.executeQuery("SELECT nom FROM Tournois")) {
            while (rs.next()) tournaments.add(rs.getString("nom"));
        } catch (Exception e) { e.printStackTrace(); }
        tournamentCombo.setItems(javafx.collections.FXCollections.observableArrayList(tournaments));
    }

    @FXML
    private void handleNext(ActionEvent event) throws Exception {
        String selectedTournament = tournamentCombo.getValue();
        if (selectedTournament != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("JoinOrCreate.fxml"));
            Scene scene = new Scene(loader.load());
            JoinOrCreateController controller = loader.getController();
            controller.setTournament(selectedTournament);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        }
    }

    @FXML
    private void handleObserve(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Observation.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }
}
