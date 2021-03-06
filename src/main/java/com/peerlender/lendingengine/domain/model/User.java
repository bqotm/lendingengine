package com.peerlender.lendingengine.domain.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.Objects;


@Entity
public final class User {

    @Id
    private String username;
    private String firstName;
    private String lastName;
    private int age;
    private String occupation;
    @OneToOne(cascade = CascadeType.ALL)
    private Balance balance;


    public User(String username, String firstName, String lastName, int age, String occupation, Balance balance) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.occupation = occupation;
        this.username=username;
        this.balance=balance;
    }

    public User(){}

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public String getOccupation() {
        return occupation;
    }

    public Balance getBalance() {
        return balance;
    }

    public void topUp(final Money money){
        balance.topUp(money);
    }

    public void withDraw(final Money money){
        balance.withdraw(money);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return username == user.username && age == user.age && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(occupation, user.occupation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, firstName, lastName, age, occupation);
    }
}
