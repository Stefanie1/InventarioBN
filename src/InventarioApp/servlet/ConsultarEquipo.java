package InventarioApp.servlet;

import InventarioApp.service.EquipoInfoAction;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ConsultarEquipo extends HttpServlet {
    private EquipoInfoAction action;

    public ConsultarEquipo() {
        action = new EquipoInfoAction();

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        resp.setStatus(HttpServletResponse.SC_OK);

        out.println("Hola");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        resp.setStatus(HttpServletResponse.SC_OK);

        String body = req.getReader().lines()
                .reduce("", (accumulator, actual) -> accumulator + actual);

        String jsonString = "Your JSON string";
        JSONParser parser = new JSONParser();

        String buscarPor="", valor="";
        try {
            JSONObject json = (JSONObject) parser.parse(body);
            buscarPor = (String) json.get("buscarPor");
            valor = (String) json.get("valor");

            System.out.println("buscarPor: "+buscarPor);
            System.out.println("Valor: "+valor);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        out.println(action.consultaEquipo(buscarPor,valor));
    }
}
