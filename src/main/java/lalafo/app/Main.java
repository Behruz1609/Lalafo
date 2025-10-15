package lalafo.app;

import lalafo.model.*;
import lalafo.repository.DataBase;
import lalafo.util.MyGeneratorId;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private static final DataBase db = new DataBase();
    private static final Scanner scanner = new Scanner(System.in);


    private static void menu() {
        System.out.println("""
                0.  Exit
                1.  Registration
                2.  LogIn
                """);
        System.out.print("Command: ");
    }

    private static void menuUser() {
        System.out.println("""
                0.  Logout
                1.  Get All announcements
                2.  Get announcement by name
                3.  Get announcement by id
                4.  My favorites
                5.  Update my profile
                6.  Create announcement
                7.  Add announcement to favorites
                8.  Remove favorite
                9.  Delete my announcement
                """);
        System.out.print("Command: ");
    }

    public static void main(String[] args) {
        seedDemoData();
        boolean running = true;
        while (running) {
            menu();
            String cmd = scanner.nextLine().trim();
            switch (cmd) {
                case "0" -> {
                    System.out.println("Bye!");
                    running = false;
                }
                case "1" -> registration();
                case "2" -> login();
                default -> System.out.println("Unknown command");
            }
        }
    }

    private static void registration() {
        System.out.print("First name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();
        if (db.findUserByEmail(email).isPresent()) {
            System.out.println("Email already exists!");
            return;
        }
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();
        System.out.print("Phone number: ");
        String phone = scanner.nextLine().trim();
        System.out.print("Role (USER/ADMIN): ");
        String roleStr = scanner.nextLine().trim().toUpperCase();
        Rol role = Rol.USER;
        if (roleStr.equals("ADMIN")) role = Rol.ADMIN;

        Users u = new Users(MyGeneratorId.getIdUser(), name, email, password, phone, role);
        db.saveUser(u);
        System.out.println("Registration success! Your id = " + u.getId());
    }

    private static void login() {
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();
        Optional<Users> opt = db.findUserByEmail(email);
        if (opt.isEmpty() || !opt.get().getPassword().equals(password)) {
            System.out.println("Invalid credentials");
            return;
        }
        Users user = opt.get();
        System.out.println("Welcome, " + user.getFirstName());
        userLoop(user);
    }

    private static void userLoop(Users user) {
        boolean in = true;
        while (in) {
            menuUser();
            String cmd = scanner.nextLine().trim();
            switch (cmd) {
                case "0" -> {
                    in = false;
                    System.out.println("Logged out");
                }
                case "1" -> listAllAnnouncements();
                case "2" -> findByName();
                case "3" -> findById();
                case "4" -> myFavorites(user);
                case "5" -> updateProfile(user);
                case "6" -> createAnnouncement(user);
                case "7" -> addToFavorites(user);
                case "8" -> removeFavorite(user);
                case "9" -> deleteMyAnnouncement(user);
                default -> System.out.println("Unknown command");
            }
        }
    }

    private static void listAllAnnouncements() {
        List<Announcement> list = db.getAllAnnouncements();
        if (list.isEmpty()) System.out.println("No announcements found");
        else list.forEach(a -> System.out.println(a));
    }

    private static void findByName() {
        System.out.print("Enter name or part of name: ");
        String q = scanner.nextLine().trim();
        List<Announcement> res = db.findAnnouncementsByName(q);
        if (res.isEmpty()) System.out.println("No matches");
        else res.forEach(System.out::println);
    }

    private static void findById() {
        System.out.print("Enter id: ");
        String s = scanner.nextLine().trim();
        try {
            Long id = Long.parseLong(s);
            Optional<Announcement> a = db.findAnnouncementById(id);
            a.ifPresentOrElse(System.out::println, () -> System.out.println("Not found"));
        } catch (NumberFormatException e) {
            System.out.println("Invalid id");
        }
    }

    private static void myFavorites(Users user) {
        List<Favorite> favs = db.findFavoritesByUser(user);
        if (favs.isEmpty()) System.out.println("No favorites yet");
        else favs.forEach(f -> System.out.println(f.getAnnouncement()));
    }

    private static void updateProfile(Users user) {
        System.out.println("Current: " + user);
        System.out.print("New name (enter to skip): ");
        String name = scanner.nextLine().trim();
        if (!name.isEmpty()) user.setFirstName(name);
        System.out.print("New phone (enter to skip): ");
        String phone = scanner.nextLine().trim();
        if (!phone.isEmpty()) user.setPhoneNumber(phone);
        System.out.print("New password (enter to skip): ");
        String pass = scanner.nextLine().trim();
        if (!pass.isEmpty()) user.setPassword(pass);
        System.out.println("Profile updated: " + user);
    }

    private static void createAnnouncement(Users user) {
        System.out.print("Announcement name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Description: ");
        String desc = scanner.nextLine().trim();
        System.out.print("Price: ");
        double price = 0;
        try {
            price = Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid price, set to 0");
        }
        Announcement a = new Announcement(MyGeneratorId.getIdAnnouncement(), name, desc, price, user.getEmail());
        db.saveAnnouncement(a);
        user.addAnnouncement(a);
        System.out.println("Announcement created with id=" + a.getId());
    }

    private static void addToFavorites(Users user) {
        System.out.print("Enter announcement id to favourite: ");
        String s = scanner.nextLine().trim();
        try {
            Long id = Long.parseLong(s);
            Optional<Announcement> oa = db.findAnnouncementById(id);
            if (oa.isEmpty()) {
                System.out.println("Announcement not found");
                return;
            }
            Announcement announcement = oa.get();
            if (db.existsFavorite(user, announcement)) {
                System.out.println("Already in favorites");
                return;
            }
            Favorite f = new Favorite(MyGeneratorId.getIdFavorite(), user, announcement);
            db.saveFavorite(f);
            System.out.println("Added to favorites");
        } catch (NumberFormatException e) {
            System.out.println("Invalid id");
        }
    }

    private static void removeFavorite(Users user) {
        System.out.print("Enter announcement id to remove from favorites: ");
        String s = scanner.nextLine().trim();
        try {
            Long id = Long.parseLong(s);
            List<Favorite> favs = db.findFavoritesByUser(user);
            Optional<Favorite> of = favs.stream().filter(f -> f.getAnnouncement().getId().equals(id)).findFirst();
            if (of.isEmpty()) {
                System.out.println("Favorite not found");
                return;
            }
            db.removeFavorite(of.get());
            System.out.println("Removed");
        } catch (NumberFormatException e) {
            System.out.println("Invalid id");
        }
    }

    private static void deleteMyAnnouncement(Users user) {
        System.out.print("Enter your announcement id to delete: ");
        String s = scanner.nextLine().trim();
        try {
            Long id = Long.parseLong(s);
            Optional<Announcement> oa = db.findAnnouncementById(id);
            if (oa.isEmpty()) {
                System.out.println("Announcement not found");
                return;
            }
            Announcement a = oa.get();
            if (!a.getOwner().equals(user.getEmail())) {
                System.out.println("You are not the owner");
                return;
            }
            db.removeAnnouncement(a);
            System.out.println("Deleted");
        } catch (NumberFormatException e) {
            System.out.println("Invalid id");
        }
    }

    private static void seedDemoData() {

        Users u1 = new Users(MyGeneratorId.getIdUser(), "Alice", "alice@gmail.com", "pass", "123", Rol.USER);
        Users u2 = new Users(MyGeneratorId.getIdUser(), "Bob", "bob@gmail.com", "1234", "456", Rol.USER);
        db.saveUser(u1);
        db.saveUser(u2);


        Announcement a1 = new Announcement(MyGeneratorId.getIdAnnouncement(), "iPhone X", "Used phone", 150.0, u1.getEmail());
        Announcement a2 = new Announcement(MyGeneratorId.getIdAnnouncement(), "Gaming PC", "Good condition", 700.0, u2.getEmail());
        db.saveAnnouncement(a1);
        db.saveAnnouncement(a2);
        u1.addAnnouncement(a1);
        u2.addAnnouncement(a2);
    }
}
