package com.estudiantes.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    
    // Método para leer variables de entorno con valor por defecto
    private static String getEnv(String key, String defaultValue) {
        String value = System.getenv(key);
        return value != null ? value : defaultValue;
    }
    
    // Configuración que funciona tanto local como en la web
    private static final String HOST = getEnv("MYSQL_HOST", "localhost");
    private static final String PORT = getEnv("MYSQL_PORT", "3306");
    private static final String DATABASE = getEnv("MYSQL_DATABASE", "db_estudiantes");
    private static final String USER = getEnv("MYSQL_USER", "root");
    private static final String PASSWORD = getEnv("MYSQL_PASSWORD", "");
    
    private static final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE;
    
    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Conexión exitosa a la base de datos");
        } catch (ClassNotFoundException e) {
            System.err.println("❌ Error: Driver no encontrado");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("❌ Error al conectar a la base de datos");
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