package InventarioApp.servlet;

import InventarioApp.service.EquipoInfoAction;
import org.apache.commons.lang3.StringEscapeUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class EquipoEditarServlet extends HttpServlet {

    private EquipoInfoAction action;
    // private EquipoDao hotelDao;

    public EquipoEditarServlet() {
        action = new EquipoInfoAction();
        // hotelDao = new EquipoDaoSql();
    }

    @Override
    protected void doGet(HttpServletRequest solicitud, HttpServletResponse respuesta)
            throws IOException {
        PrintWriter out = respuesta.getWriter();
        respuesta.setContentType("application/json");
        respuesta.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");

        String inventario = solicitud.getParameter("inventario");
        System.out.println("Inventario" + inventario);

        respuesta.setStatus(HttpServletResponse.SC_OK);
        inventario = StringEscapeUtils.escapeHtml4(inventario); // need to "clean up" whatever
        action.eliminar(inventario);

    }


    @Override
    protected void doPost(HttpServletRequest solicitud, HttpServletResponse respuesta) throws IOException {
        PrintWriter out = respuesta.getWriter();
        respuesta.setContentType("application/json");
        respuesta.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");

        System.out.println("doPost Inventario");

        respuesta.setStatus(HttpServletResponse.SC_OK);

    }
}
