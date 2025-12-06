package com.estudiantes.dao;

import com.estudiantes.config.ConexionBD;
import com.estudiantes.model.Estudiante;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstudianteDAO {
    
    // 📋 LISTAR todos los estudiantes
    public List<Estudiante> listarEstudiantes() {
        List<Estudiante> lista = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = ConexionBD.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM estudiantes ORDER BY id DESC";
            rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                Estudiante est = new Estudiante();
                est.setId(rs.getInt("id"));
                est.setCodigo(rs.getString("codigo"));
                est.setNombre(rs.getString("nombre"));
                est.setApellido(rs.getString("apellido"));
                est.setCarrera(rs.getString("carrera"));
                est.setSemestre(rs.getInt("semestre"));
                est.setEmail(rs.getString("email"));
                est.setFechaRegistro(rs.getTimestamp("fecha_registro"));
                
                lista.add(est);
            }
            
            System.out.println("✅ Se encontraron " + lista.size() + " estudiantes");
            
        } catch (SQLException e) {
            System.err.println("❌ Error en listarEstudiantes");
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                ConexionBD.closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return lista;
    }
    
    // 🔍 BUSCAR estudiante por ID
    public Estudiante obtenerEstudiante(int id) {
        Estudiante est = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conn = ConexionBD.getConnection();
            String sql = "SELECT * FROM estudiantes WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                est = new Estudiante();
                est.setId(rs.getInt("id"));
                est.setCodigo(rs.getString("codigo"));
                est.setNombre(rs.getString("nombre"));
                est.setApellido(rs.getString("apellido"));
                est.setCarrera(rs.getString("carrera"));
                est.setSemestre(rs.getInt("semestre"));
                est.setEmail(rs.getString("email"));
                est.setFechaRegistro(rs.getTimestamp("fecha_registro"));
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error en obtenerEstudiante");
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
        
        return est;
    }
    
    // ➕ CREAR nuevo estudiante
    public boolean crearEstudiante(Estudiante est) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean exito = false;
        
        try {
            conn = ConexionBD.getConnection();
            String sql = "INSERT INTO estudiantes (codigo, nombre, apellido, carrera, semestre, email) VALUES (?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, est.getCodigo());
            ps.setString(2, est.getNombre());
            ps.setString(3, est.getApellido());
            ps.setString(4, est.getCarrera());
            ps.setInt(5, est.getSemestre());
            ps.setString(6, est.getEmail());
            
            int filasAfectadas = ps.executeUpdate();
            exito = (filasAfectadas > 0);
            
            if (exito) {
                System.out.println("✅ Estudiante creado exitosamente");
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error en crearEstudiante");
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                ConexionBD.closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return exito;
    }
    
    // ✏️ ACTUALIZAR estudiante
    public boolean actualizarEstudiante(Estudiante est) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean exito = false;
        
        try {
            conn = ConexionBD.getConnection();
            String sql = "UPDATE estudiantes SET codigo=?, nombre=?, apellido=?, carrera=?, semestre=?, email=? WHERE id=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, est.getCodigo());
            ps.setString(2, est.getNombre());
            ps.setString(3, est.getApellido());
            ps.setString(4, est.getCarrera());
            ps.setInt(5, est.getSemestre());
            ps.setString(6, est.getEmail());
            ps.setInt(7, est.getId());
            
            int filasAfectadas = ps.executeUpdate();
            exito = (filasAfectadas > 0);
            
            if (exito) {
                System.out.println("✅ Estudiante actualizado exitosamente");
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error en actualizarEstudiante");
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                ConexionBD.closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return exito;
    }
    
    // 🗑️ ELIMINAR estudiante
    public boolean eliminarEstudiante(int id) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean exito = false;
        
        try {
            conn = ConexionBD.getConnection();
            String sql = "DELETE FROM estudiantes WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            
            int filasAfectadas = ps.executeUpdate();
            exito = (filasAfectadas > 0);
            
            if (exito) {
                System.out.println("✅ Estudiante eliminado exitosamente");
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error en eliminarEstudiante");
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                ConexionBD.closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return exito;
    }
}