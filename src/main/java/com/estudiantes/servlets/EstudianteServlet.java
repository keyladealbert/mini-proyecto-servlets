package com.estudiantes.servlets;

import com.estudiantes.dao.EstudianteDAO;
import com.estudiantes.model.Estudiante;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet(name = "EstudianteServlet", urlPatterns = {"/estudiantes"})
public class EstudianteServlet extends HttpServlet {
    
    private EstudianteDAO estudianteDAO = new EstudianteDAO();
    private Gson gson = new Gson();
    
    // 📋 LISTAR (GET)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if ("obtener".equals(action)) {
            // Obtener un estudiante específico
            int id = Integer.parseInt(request.getParameter("id"));
            Estudiante estudiante = estudianteDAO.obtenerEstudiante(id);
            
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(gson.toJson(estudiante));
            
        } else {
            // Listar todos los estudiantes
            List<Estudiante> estudiantes = estudianteDAO.listarEstudiantes();
            
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(gson.toJson(estudiantes));
            
            System.out.println("📋 Lista de estudiantes enviada: " + estudiantes.size() + " registros");
        }
    }
    
    // ➕ CREAR (POST)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Obtener datos del formulario
        String codigo = request.getParameter("codigo");
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String carrera = request.getParameter("carrera");
        int semestre = Integer.parseInt(request.getParameter("semestre"));
        String email = request.getParameter("email");
        
        // Crear objeto estudiante
        Estudiante estudiante = new Estudiante();
        estudiante.setCodigo(codigo);
        estudiante.setNombre(nombre);
        estudiante.setApellido(apellido);
        estudiante.setCarrera(carrera);
        estudiante.setSemestre(semestre);
        estudiante.setEmail(email);
        
        // Guardar en BD
        boolean exito = estudianteDAO.crearEstudiante(estudiante);
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        if (exito) {
            response.getWriter().write("{\"success\": true, \"mensaje\": \"Estudiante creado exitosamente\"}");
            System.out.println("✅ Estudiante creado: " + nombre + " " + apellido);
        } else {
            response.getWriter().write("{\"success\": false, \"mensaje\": \"Error al crear estudiante\"}");
            System.out.println("❌ Error al crear estudiante");
        }
    }
    
    // ✏️ ACTUALIZAR (PUT)
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Leer parámetros (en PUT vienen en el body)
        int id = Integer.parseInt(request.getParameter("id"));
        String codigo = request.getParameter("codigo");
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String carrera = request.getParameter("carrera");
        int semestre = Integer.parseInt(request.getParameter("semestre"));
        String email = request.getParameter("email");
        
        // Crear objeto estudiante
        Estudiante estudiante = new Estudiante();
        estudiante.setId(id);
        estudiante.setCodigo(codigo);
        estudiante.setNombre(nombre);
        estudiante.setApellido(apellido);
        estudiante.setCarrera(carrera);
        estudiante.setSemestre(semestre);
        estudiante.setEmail(email);
        
        // Actualizar en BD
        boolean exito = estudianteDAO.actualizarEstudiante(estudiante);
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        if (exito) {
            response.getWriter().write("{\"success\": true, \"mensaje\": \"Estudiante actualizado exitosamente\"}");
            System.out.println("✅ Estudiante actualizado: " + nombre + " " + apellido);
        } else {
            response.getWriter().write("{\"success\": false, \"mensaje\": \"Error al actualizar estudiante\"}");
        }
    }
    
    // 🗑️ ELIMINAR (DELETE)
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        int id = Integer.parseInt(request.getParameter("id"));
        
        boolean exito = estudianteDAO.eliminarEstudiante(id);
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        if (exito) {
            response.getWriter().write("{\"success\": true, \"mensaje\": \"Estudiante eliminado exitosamente\"}");
            System.out.println("✅ Estudiante eliminado: ID " + id);
        } else {
            response.getWriter().write("{\"success\": false, \"mensaje\": \"Error al eliminar estudiante\"}");
        }
    }
}