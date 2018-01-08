package InventarioApp.servlet;

import InventarioApp.service.EquipoInfoAction;
import com.oracle.javafx.jmx.json.JSONException;
import org.apache.commons.lang3.StringEscapeUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;

public class EquipoInfoServlet extends HttpServlet {

    private EquipoInfoAction action;
    // private EquipoDao hotelDao;

    public EquipoInfoServlet() {
        action = new EquipoInfoAction();
        // hotelDao = new EquipoDaoSql();
    }

    /**
     * A method that gets executed when the get request is sent to the
     * HelloServlet from the path /hotelInfo
     */
    @Override
    protected void doGet(HttpServletRequest solicitud, HttpServletResponse respuesta)
            throws  IOException {
        PrintWriter out = respuesta.getWriter();
        respuesta.setContentType("application/json");
        respuesta.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");

        String inventario = solicitud.getParameter("inventario");
        System.out.println("Inventario doGET " + inventario);

        respuesta.setStatus(HttpServletResponse.SC_OK);
        inventario = StringEscapeUtils.escapeHtml4(inventario); // need to "clean up" whatever
        out.println(action.traerEquipo(inventario));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");



        String body = request.getReader().lines()
                .reduce("", (accumulator, actual) -> accumulator + actual);

        System.out.println("body: "+ body);

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
            String observaciones = (String) json.get( "observaciones");

            System.out.println("equipo: "+equipo);
            System.out.println("numInventario: "+numInventario);
            System.out.println(numSerie);
            System.out.println(marca);
            System.out.println(estatus);
            System.out.println(sede);
            System.out.println(aula);
            System.out.println(observaciones);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        response.setStatus(HttpServletResponse.SC_OK);
        JSONObject jsonObject = new JSONObject();
        if(false){
            jsonObject.put("mensaje", "Guardado");
        } else{
            jsonObject.put("mensaje", "Ya existe ese Numero de Serie");
        }
        out.println(jsonObject);

    }
}