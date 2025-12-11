package com.estudiantes.config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    
    private static String getEnv(String key, String defaultValue) {
        String value = System.getenv(key);
        return value != null ? value : defaultValue;
    }
    
    // Detecta automáticamente si es PostgreSQL o MySQL
    private static final String DB_TYPE = getEnv("DB_TYPE", "mysql");
    private static final String HOST = getEnv("DB_HOST", "localhost");
    private static final String PORT = getEnv("DB_PORT", DB_TYPE.equals("postgresql") ? "5432" : "3306");
    private static final String DATABASE = getEnv("DB_NAME", "db_estudiantes");
    private static final String USER = getEnv("DB_USER", "root");
    private static final String PASSWORD = getEnv("DB_PASSWORD", "");
    
    private static final String URL = DB_TYPE.equals("postgresql") 
        ? "jdbc:postgresql://" + HOST + ":" + PORT + "/" + DATABASE
        : "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE;
    
    public static Connection getConnection() {
        Connection conn = null;
        try {
            if (DB_TYPE.equals("postgresql")) {
                Class.forName("org.postgresql.Driver");
            } else {
                Class.forName("com.mysql.cj.jdbc.Driver");
            }
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Conexión exitosa a " + DB_TYPE);
        } catch (ClassNotFoundException e) {
            System.err.println("❌ Driver no encontrado");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("❌ Error conexión BD");
            e.printStackTrace();
        }
        return conn;
    }
    
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("🔒 Conexión cerrada");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}