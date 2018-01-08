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

        try {
            JSONObject json = (JSONObject) parser.parse(body);
            String equipo = (String) json.get("equipo");
            String numInventario = (String) json.get("numInventario");
            String numSerie = (String) json.get( "numSerie");
            String marca = (String) json.get( "marca");
            String estatus = (String) json.get( "estatus");
            String sede = (String) json.get( "sede");
            String aula = (String) json.get( "aula");


            System.out.println("equipo: "+equipo);
            System.out.println("numInventario: "+numInventario);
            System.out.println(numSerie);
            System.out.println(marca);
            System.out.println(estatus);
            System.out.println(sede);
            System.out.println(aula);
            out.println(action.traerEquipo(numInventario));

        } catch (ParseException e) {
            e.printStackTrace();
        }


    }
}
