package model;

public class Role {

    int roleId;
    String roleName;

    public Role(int roleId, String roleName) {

        this.roleId = roleId;
        this.roleName = roleName;
    }

    @Override
    public String toString() {

        return "Role ID: " + roleId +
                " | Role Name: " + roleName;
    }
}