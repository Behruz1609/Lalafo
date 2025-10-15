package lalafo.model;

public class Favorite {
    private Long id;
    private Users users;
    private Announcement announcement;

    public Favorite() {}

    public Favorite(Long id, Users users, Announcement announcement) {
        this.id = id;
        this.users = users;
        this.announcement = announcement;
    }

    public Long getId() { return id; }
    public Users getUsers() { return users; }
    public Announcement getAnnouncement() { return announcement; }

    public void setUsers(Users users) { this.users = users; }
    public void setAnnouncement(Announcement announcement) { this.announcement = announcement; }

    @Override
    public String toString() {
        return "Favorite{" + "id=" + id + ", user='" + (users != null ? users.getFirstName() : "null") + '\'' + ", announcement='" + (announcement != null ? announcement.getName() : "null") + '\'' + '}';
    }
}
