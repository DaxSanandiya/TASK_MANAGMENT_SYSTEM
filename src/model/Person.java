package model;

public abstract class Person {

    protected String name;
    protected String email;

    public Person(String name, String email) {

        this.name = name;
        this.email = email;
    }

}