package InventarioApp.servlet;

import InventarioApp.model.Equipo;
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

public class Modificar extends HttpServlet{
    private EquipoInfoAction action;

    public Modificar() {
        action = new EquipoInfoAction();
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setStatus(HttpServletResponse.SC_OK);

        String body = request.getReader().lines()
                .reduce("", (accumulator, actual) -> accumulator + actual);

        System.out.println("body: "+ body);

        String jsonString = "Your JSON string";
        JSONParser parser = new JSONParser();

        Equipo equipo = new Equipo();
        try {
            JSONObject json = (JSONObject) parser.parse(body);
            equipo.setNumInventario ((String) json.get("numInventario"));
            equipo.setNumSerie ((String) json.get( "numSerie"));
            equipo.setEstatus((String) json.get( "estatus"));
            equipo.setSede ( (String) json.get( "sede"));
            equipo.setAula ( (String) json.get( "aula"));
            equipo.setObservaciones ( (String) json.get( "observaciones"));
            equipo.setUser ((String) json.get( "user"));

            out.println(action.modificarEquipo(equipo));

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
