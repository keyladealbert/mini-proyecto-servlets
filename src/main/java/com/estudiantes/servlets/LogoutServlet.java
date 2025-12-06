package com.estudiantes.servlets;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet(name = "LogoutServlet", urlPatterns = {"/logout"})
public class LogoutServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Destruir la sesión
        HttpSession session = request.getSession(false);
        if (session != null) {
            String usuario = (String) session.getAttribute("nombreCompleto");
            session.invalidate();
            System.out.println("🚪 Sesión cerrada para: " + usuario);
        }
        
        // Redirigir al login
        response.sendRedirect("index.html");
    }
}