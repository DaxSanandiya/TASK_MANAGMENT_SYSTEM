package model;

public class Team {

    int teamId;
    String teamName;

    public Team(int teamId, String teamName) {

        this.teamId = teamId;
        this.teamName = teamName;
    }

    @Override
    public String toString() {

        return "Team ID: " + teamId +
                " | Team Name: " + teamName;
    }
}