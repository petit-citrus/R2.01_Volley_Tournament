package application;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {
    private static final String DB_NAME = "volley_tournament.db";
    private static final String URL = "jdbc:sqlite:" + getDatabasePath();

    public static Connection getConnection() throws SQLException {
        initializeDatabase();
        return DriverManager.getConnection(URL);
    }

    private static String getDatabasePath() {
        return System.getProperty("user.dir") + File.separator + DB_NAME;
    }

    private static void initializeDatabase() {
        File dbFile = new File(getDatabasePath());
        if (!dbFile.exists()) {
            try (InputStream is = DatabaseUtil.class.getResourceAsStream("/data/" + DB_NAME);
                 OutputStream os = new FileOutputStream(dbFile)) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }
            } catch (IOException e) {
                throw new RuntimeException("Erreur lors de la copie de la base de données", e);
            }
        }
    }
}
