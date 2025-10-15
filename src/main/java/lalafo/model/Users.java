package lalafo.model;

import java.util.ArrayList;
import java.util.List;

public class Users {
    private Long id;
    private String firstName;
    private String email;
    private String password;
    private String phoneNumber;
    private Rol role;
    private List<Announcement> announcements = new ArrayList<>();

    public Users() {
    }

    public Users(Long id, String firstName, String email, String password, String phoneNumber, Rol role) {
        this.id = id;
        this.firstName = firstName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Rol getRole() {
        return role;
    }

    public List<Announcement> getAnnouncements() {
        return announcements;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setRole(Rol role) {
        this.role = role;
    }

    public void addAnnouncement(Announcement announcement) {
        announcements.add(announcement);
    }

    public boolean removeAnnouncement(Announcement announcement) {
        return announcements.remove(announcement);
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name='" + firstName + '\'' + ", email='" + email + '\'' + ", phone='" + phoneNumber + '\'' + ", role='" + role + '\'' + '}';
    }
}
