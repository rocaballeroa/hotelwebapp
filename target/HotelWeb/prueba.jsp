<%-- 
    Document   : prueba
    Created on : 2 jul. 2025, 19:15:52
    Author     : User
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    request.setAttribute("contenido", "dashboardAdmin.jsp");  // Contenido dinámico
    RequestDispatcher rd = request.getRequestDispatcher("dashboardLayout.jsp"); // Layout base
    rd.forward(request, response);
%>
