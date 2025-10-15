package lalafo.repository;

import lalafo.model.*;

import java.util.*;
import java.util.stream.Collectors;

public class DataBase {
    private final List<Users> users = new ArrayList<>();
    private final List<Announcement> announcements = new ArrayList<>();
    private final List<Favorite> favorites = new ArrayList<>();

    // Users
    public List<Users> getAllUsers() {
        return users;
    }

    public Optional<Users> findUserByEmail(String email) {
        return users.stream().filter(u -> u.getEmail().equalsIgnoreCase(email)).findFirst();
    }

    public boolean saveUser(Users user) {
        return users.add(user);
    }

    public boolean removeUser(Users user) {
        List<Announcement> toRemove = announcements.stream().filter(a -> a.getOwner().equals(user.getEmail())).collect(Collectors.toList());
        for (Announcement a : toRemove) removeAnnouncement(a);
        favorites.removeIf(f -> f.getUsers().getId().equals(user.getId()));
        return users.remove(user);
    }

    // Announcements
    public List<Announcement> getAllAnnouncements() {
        return announcements;
    }

    public boolean saveAnnouncement(Announcement announcement) {
        return announcements.add(announcement);
    }

    public boolean removeAnnouncement(Announcement announcement) {
        users.forEach(u -> u.removeAnnouncement(announcement));
        favorites.removeIf(f -> f.getAnnouncement().getId().equals(announcement.getId()));
        return announcements.remove(announcement);
    }

    public Optional<Announcement> findAnnouncementById(Long id) {
        return announcements.stream().filter(a -> a.getId().equals(id)).findFirst();
    }

    public List<Announcement> findAnnouncementsByName(String name) {
        return announcements.stream().filter(a -> a.getName().toLowerCase().contains(name.toLowerCase())).collect(Collectors.toList());
    }

    public List<Favorite> getAllFavorites() {
        return favorites;
    }

    public boolean saveFavorite(Favorite f) {
        boolean added = favorites.add(f);
        if (added) {
            f.getAnnouncement().addFavorite(f);
        }
        return added;
    }

    public boolean removeFavorite(Favorite f) {
        f.getAnnouncement().removeFavorite(f);
        return favorites.remove(f);
    }

    public List<Favorite> findFavoritesByUser(Users user) {
        List<Favorite> list = new ArrayList<>();
        for (Favorite f : favorites) {
            if (f.getUsers().getId().equals(user.getId())) list.add(f);
        }
        return list;
    }

    public boolean existsFavorite(Users user, Announcement announcement) {
        return favorites.stream().anyMatch(f -> f.getUsers().getId().equals(user.getId()) && f.getAnnouncement().getId().equals(announcement.getId()));
    }
}
