package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;

public class JoinOrCreateController {
    private String tournament;

    public void setTournament(String tournament) {
        this.tournament = tournament;
    }

    @FXML
    private void handleJoin(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("JoinTeam.fxml"));
        Scene scene = new Scene(loader.load());
        JoinTeamController controller = loader.getController();
        controller.setTournament(tournament);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    private void handleCreate(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateTeam.fxml"));
        Scene scene = new Scene(loader.load());
        CreateTeamController controller = loader.getController();
        controller.setTournament(tournament);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }
}
