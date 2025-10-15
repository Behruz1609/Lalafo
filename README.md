# Lalafo (console)

Java 17 console application imitation of a classifieds app (Lalafo).

## How to open in IntelliJ
1. Download and unzip `Lalafo.zip`.
2. In IntelliJ IDEA: File → Open... → select the `Lalafo` folder.
3. Make sure Project SDK is set to **Java 17** (File → Project Structure → Project SDK).
4. Run `lalafo.app.Main` (right-click the file → Run).

## What is included
- `src/main/java/lalafo/app/Main.java` — entry point with `main()` and menus.
- `src/main/java/lalafo/model/*` — model classes: Users, Announcement, Favorite, Rol.
- `src/main/java/lalafo/repository/DataBase.java` — in-memory repository.
- `src/main/java/lalafo/util/MyGeneratorId.java` — id generator.

This is a plain Java project (no build system). You can run directly from IntelliJ.
