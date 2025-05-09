package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TeamOverview {
    private final StringProperty teamName;
    private final StringProperty captain;
    private final StringProperty players;

    public TeamOverview(String teamName, String captain, String players) {
        this.teamName = new SimpleStringProperty(teamName);
        this.captain = new SimpleStringProperty(captain);
        this.players = new SimpleStringProperty(players);
    }

    public StringProperty teamNameProperty() { return teamName; }
    public StringProperty captainProperty() { return captain; }
    public StringProperty playersProperty() { return players; }
}
