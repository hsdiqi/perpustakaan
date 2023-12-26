package perpus;

public class DataSesi {
    public static String nama;
    public static String password;
    public static String email;
    public static String username;
    public static int userId;

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        DataSesi.username = username;
    }

    public static int getUserId() {
        return userId;
    }

    public static void setUserId(int userId) {
        DataSesi.userId = userId;
    }
}
