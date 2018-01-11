package InventarioApp.servlet;

import InventarioApp.dao.LoginDaoSQL;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

public class Login extends HttpServlet{
    LoginDaoSQL loginDaoSQL;

    public Login(){
        loginDaoSQL = LoginDaoSQL.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        //response.setContentType("application/json");
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setStatus(HttpServletResponse.SC_OK);

        String usuario = request.getParameter("username");
        String rol = request.getParameter("rol");

        System.out.println(usuario);
        System.out.println(rol);

        try {
            loginDaoSQL.changeUserRol(usuario,rol);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setStatus(HttpServletResponse.SC_OK);

        String body = request.getReader().lines()
                .reduce("", (accumulator, actual) -> accumulator + actual);
        JSONParser parser = new JSONParser();
        JSONObject json = null;
        try {
            json = (JSONObject) parser.parse(body);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String usuario = (String) json.get("username");
        String password = (String) json.get("password");

        try {
            List<String> list = loginDaoSQL.authenticateUser(usuario,password);
            System.out.println(list.size());
            if (list.size() > 0) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("usuario", list.get(0));
                jsonObject.put("rol", list.get(1));
                out.println(jsonObject.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
