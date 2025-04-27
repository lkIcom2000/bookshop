package dk.au.userservice.dto;

import java.sql.Date;

public class UserDTO{
  private String name;
  private Date birth;
  private String role;
  private String adress;
  private int phoneNumber;
  private String payment;

    public UserDTO(String name, Date birth, String role, String adress, int phoneNumber, String payment) {
        this.name = name;
        this.birth = birth;
        this.role = role;
        this.adress = adress;
        this.phoneNumber = phoneNumber;
        this.payment = payment;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "UserDTO{" +
                "name='" + name + '\'' +
                ", birth=" + birth +
                ", role='" + role + '\'' +
                ", adress='" + adress + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", payment='" + payment + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }
}