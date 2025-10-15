package lalafo.model;

import java.util.ArrayList;
import java.util.List;

public class Announcement {
    private Long id;
    private String name;
    private String description;
    private double price;
    private String owner; // owner's email
    private List<Favorite> favorites = new ArrayList<>();

    public Announcement() {}

    public Announcement(Long id, String name, String description, double price, String owner) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.owner = owner;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public String getOwner() { return owner; }
    public List<Favorite> getFavorites() { return favorites; }

    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setPrice(double price) { this.price = price; }
    public void setOwner(String owner) { this.owner = owner; }

    public void addFavorite(Favorite f){ favorites.add(f); }
    public void removeFavorite(Favorite f){ favorites.remove(f); }

    @Override
    public String toString() {
        return "Announcement{" + "id=" + id + ", name='" + name + '\'' + ", description='" + description + '\'' + ", price=" + price + ", owner='" + owner + '\'' + '}';
    }
}
