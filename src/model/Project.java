package model;

public class Project  {

    int projectId;
    String projectName;
    String status;
    String description;

    public Project(int projectId, String projectName, String status, String description) {

        this.projectId = projectId;
        this.projectName = projectName;
        this.status = status;
        this.description = description;
    }


    @Override
    public String toString() {

        return "Project ID: " + projectId +
                " | Project: " + projectName +
                " | Status: " + status;
    }
}