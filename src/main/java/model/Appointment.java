package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Objects;

public class Appointment {
    private Integer id;
    private Integer serviceID;
    private Integer masterID;
    private LocalDate date;
    private LocalTime timeslot;
    private String status;
    private Integer clientID;

    private String serviceNameUA;
    private String serviceNameEN;
    private String masterFullnameUA;
    private String masterFullnameEN;
    private String clientFullname;
    private String dateString;
    private String timeslotString;

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getServiceID() {
        return serviceID;
    }

    public void setServiceID(Integer serviceID) {
        this.serviceID = serviceID;
    }

    public Integer getMasterID() {
        return masterID;
    }

    public void setMasterID(Integer masterID) {
        this.masterID = masterID;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
        setDateString(date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)));
    }

    public LocalTime getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(LocalTime timeslot) {
        this.timeslot = timeslot;
        setTimeslotString(timeslot.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)));
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getClientID() {
        return clientID;
    }

    public void setClientID(Integer clientID) {
        this.clientID = clientID;
    }

    public String getServiceNameUA() {
        return serviceNameUA;
    }

    public void setServiceNameUA(String serviceNameUA) {
        this.serviceNameUA = serviceNameUA;
    }

    public String getServiceNameEN() {
        return serviceNameEN;
    }

    public void setServiceNameEN(String serviceNameEN) {
        this.serviceNameEN = serviceNameEN;
    }

    public String getMasterFullnameUA() {
        return masterFullnameUA;
    }

    public void setMasterFullnameUA(String masterFullnameUA) {
        this.masterFullnameUA = masterFullnameUA;
    }

    public String getMasterFullnameEN() {
        return masterFullnameEN;
    }

    public void setMasterFullnameEN(String masterFullnameEN) {
        this.masterFullnameEN = masterFullnameEN;
    }

    public String getClientFullname() {
        return clientFullname;
    }

    public void setClientFullname(String clientFullname) {
        this.clientFullname = clientFullname;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public String getTimeslotString() {
        return timeslotString;
    }

    public void setTimeslotString(String timeslotString) {
        this.timeslotString = timeslotString;

    }

    public String getDateTimeString() {
        return dateString + " " + timeslotString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Appointment that = (Appointment) o;
        return Objects.equals(serviceID, that.serviceID) && Objects.equals(masterID, that.masterID) && Objects.equals(clientID, that.clientID) && date.equals(that.date) && timeslot.equals(that.timeslot) && status.equals(that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceID, masterID, date, timeslot, status, clientID);
    }
}
