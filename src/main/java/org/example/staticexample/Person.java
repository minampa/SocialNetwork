package org.example.staticexample;

public class Person{
    public static int counter = 0 ;
    public String firstName;
    public String lastName;

    public Person(String firstName, String lastName) {
        counter++;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public static void test(){
        int t = 0;
        counter++;
        String firstName = "mina";
    }

    @Override
    protected Person clone(){
        return new Person(this.firstName, this.lastName);
    }
}
