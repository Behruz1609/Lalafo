package lalafo.util;

public class MyGeneratorId {
    private static long idUser = 0L;
    private static long idAnnouncement = 0L;
    private static long idFavorite = 0L;

    public static synchronized Long getIdUser(){ return ++idUser; }
    public static synchronized Long getIdAnnouncement(){ return ++idAnnouncement; }
    public static synchronized Long getIdFavorite(){ return ++idFavorite; }
}
