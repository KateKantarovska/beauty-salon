package model;

import java.util.List;
import java.util.Objects;

public class Client extends User {
    private String phoneNumber;
    private Integer balance;
    private List<Review> reviewsList;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public List<Review> getReviewsList() {
        return reviewsList;
    }

    public void setReviewsList(List<Review> reviewsList) {
        this.reviewsList = reviewsList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;
        if (!super.equals(o)) return false;
        Client client = (Client) o;
        return phoneNumber.equals(client.phoneNumber) && balance.equals(client.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), phoneNumber, balance);
    }
}
