package model;

public class User extends Person {

    int userId;
    int roleId;
    int teamId;

    public User(int userId,
                String name,
                String email,
                int roleId,
                int teamId) {

        super(name, email);

        this.userId = userId;
        this.roleId = roleId;
        this.teamId = teamId;
    }


    @Override
    public String toString() {

        return "User ID: " + userId +
                "\n Name: " + name +
                "\n Email: " + email +
                "\n Role ID: " + roleId +
                "\n Team ID: " + teamId;
    }

}