package model;

import java.util.List;
import java.util.Map;

public class Master extends User {
    private String nameEN;
    private String surnameEN;
    private String phoneNumber;
    private Double rating;
    private List<Category> categories;
    private Map<String, Map<String, String>> appointments;
    private List<Review> reviewsList;

    public String getNameEN() {
        return nameEN;
    }

    public void setNameEN(String nameEN) {
        this.nameEN = nameEN;
    }

    public String getSurnameEN() {
        return surnameEN;
    }

    public void setSurnameEN(String surnameEN) {
        this.surnameEN = surnameEN;
    }

    public String getFullnameUA() {
        return name + " " + surname;
    }

    public String getFullnameEN() {
        return nameEN + " " + surnameEN;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Double getRating() {
        return Math.round(rating * 10.0) / 10.0;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Map<String, Map<String, String>> getAppointments() {
        return appointments;
    }

    public void setAppointments(Map<String, Map<String, String>> appointments) {
        this.appointments = appointments;
    }

    public List<Review> getReviewsList() {
        return reviewsList;
    }

    public void setReviewsList(List<Review> reviewsList) {
        this.reviewsList = reviewsList;
    }

}
