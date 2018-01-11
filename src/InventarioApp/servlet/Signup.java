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

public class Signup extends HttpServlet{
    LoginDaoSQL loginDaoSQL;

    public Signup(){
        loginDaoSQL = LoginDaoSQL.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/plian");
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

        out.println(loginDaoSQL.registerUser(usuario, password));
    }
}
