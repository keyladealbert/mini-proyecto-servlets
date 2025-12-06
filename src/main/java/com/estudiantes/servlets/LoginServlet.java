package com.estudiantes.servlets;

import com.estudiantes.dao.UsuarioDAO;
import com.estudiantes.model.Usuario;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
    
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    
    @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    
    // Validación simple directa
    if ("admin".equals(username) && "12345".equals(password)) {
        // Login exitoso
        HttpSession session = request.getSession(true);
        session.setAttribute("usuario", username);
        session.setAttribute("nombreCompleto", "Administrador");
        session.setAttribute("rol", "ADMIN");
        
        response.getWriter().write("{\"success\": true, \"mensaje\": \"Bienvenido Administrador\", \"redirect\": \"dashboard.html\"}");
    } else {
        // Login fallido
        response.getWriter().write("{\"success\": false, \"mensaje\": \"Usuario o contraseña incorrectos\"}");
    }
}
}