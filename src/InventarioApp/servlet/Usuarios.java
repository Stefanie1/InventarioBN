package InventarioApp.servlet;

import InventarioApp.dao.LoginDaoSQL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class Usuarios extends HttpServlet {

    LoginDaoSQL loginDaoSQL;

    public Usuarios(){
        loginDaoSQL = LoginDaoSQL.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setStatus(HttpServletResponse.SC_OK);
        out.println(loginDaoSQL.traerusuarios());
    }
}
