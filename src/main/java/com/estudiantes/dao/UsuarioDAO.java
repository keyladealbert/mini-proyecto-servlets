package com.estudiantes.dao;

import com.estudiantes.config.ConexionBD;
import com.estudiantes.model.Usuario;
import java.sql.*;

public class UsuarioDAO {
    
public Usuario validarUsuario(String username, String password) {
    Usuario usuario = null;
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    
    try {
        System.out.println("==================================");
        System.out.println("🔍 INICIANDO VALIDACIÓN DE USUARIO");
        System.out.println("📥 Username recibido: [" + username + "]");
        System.out.println("📥 Password recibido: [" + password + "]");
        System.out.println("==================================");
        
        conn = ConexionBD.getConnection();
        
        if (conn == null) {
            System.out.println("❌ ERROR: Conexión es NULL");
            return null;
        }
        
        String sql = "SELECT * FROM usuarios WHERE username = ? AND password = ?";
        System.out.println("📝 SQL a ejecutar: " + sql);
        
        ps = conn.prepareStatement(sql);
        ps.setString(1, username);
        ps.setString(2, password);
        
        System.out.println("⏳ Ejecutando consulta...");
        rs = ps.executeQuery();
        
        if (rs.next()) {
            usuario = new Usuario();
            usuario.setId(rs.getInt("id"));
            usuario.setUsername(rs.getString("username"));
            usuario.setPassword(rs.getString("password"));
            usuario.setNombreCompleto(rs.getString("nombre_completo"));
            usuario.setRol(rs.getString("rol"));
            
            System.out.println("✅ USUARIO ENCONTRADO!");
            System.out.println("👤 ID: " + usuario.getId());
            System.out.println("👤 Username BD: [" + usuario.getUsername() + "]");
            System.out.println("👤 Nombre: " + usuario.getNombreCompleto());
            System.out.println("👤 Rol: " + usuario.getRol());
        } else {
            System.out.println("❌ NO SE ENCONTRÓ NINGÚN USUARIO CON ESAS CREDENCIALES");
            System.out.println("🔍 Verificando qué usuarios existen en la BD...");
            
            // Consulta de debug para ver qué hay en la tabla
            Statement stmtDebug = conn.createStatement();
            ResultSet rsDebug = stmtDebug.executeQuery("SELECT username, password FROM usuarios");
            System.out.println("📋 USUARIOS EN LA BD:");
            while (rsDebug.next()) {
                System.out.println("   - Username: [" + rsDebug.getString("username") + "] Password: [" + rsDebug.getString("password") + "]");
            }
            rsDebug.close();
            stmtDebug.close();
        }
        System.out.println("==================================");
        
    } catch (SQLException e) {
        System.err.println("❌ Error SQL en validarUsuario");
        e.printStackTrace();
    } finally {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            ConexionBD.closeConnection(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    return usuario;
}
}