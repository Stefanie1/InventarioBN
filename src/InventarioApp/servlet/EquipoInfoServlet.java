package InventarioApp.servlet;

import InventarioApp.model.Equipo;
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

        Equipo equipo = new Equipo();
        try {
            JSONObject json = (JSONObject) parser.parse(body);
            equipo.setEquipo((String) json.get("equipo"));
            equipo.setNumInventario ((String) json.get("numInventario"));
            equipo.setNumSerie ((String) json.get( "numSerie"));
            equipo.setMarca ((String) json.get( "marca"));
            equipo.setEstatus((String) json.get( "estatus"));
            equipo.setSede ( (String) json.get( "sede"));
            equipo.setAula ( (String) json.get( "aula"));
            equipo.setObservaciones ( (String) json.get( "observaciones"));
            equipo.setUser ((String) json.get( "user"));

            out.println(action.altaEquipo(equipo));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        response.setStatus(HttpServletResponse.SC_OK);

    }
}