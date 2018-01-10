package InventarioApp.service;

import InventarioApp.dao.EquipoDao;
import InventarioApp.dao.EquipoDaoSql;
import InventarioApp.model.Equipo;
import com.sun.codemodel.internal.JArray;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;

/**
 * class that creates the Json Object with the Equipo information
 */
public final class EquipoInfoAction implements EquipoAction {
    private EquipoDao equipoDao;

    public EquipoInfoAction() {
        equipoDao = new EquipoDaoSql();
    }

    @Override
    public String traerEquipo(String inventario) {
        System.out.println("Inside traerEquipo " + inventario);
        Equipo equipo = equipoDao.getEquipo(inventario);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("equipo", equipo.getEquipo());
        jsonObject.put("sede", equipo.getSede());
        jsonObject.put("aula", equipo.getAula());
        jsonObject.put("marca", equipo.getMarca());
        jsonObject.put("numSerie", equipo.getNumSerie());
        jsonObject.put("numInventario", equipo.getNumInventario());
        jsonObject.put("estatus", equipo.getEstatus());
        jsonObject.put("observaciones", equipo.getObservaciones());
        jsonObject.put("mensaje", equipo.getMensaje());
        return jsonObject.toString();
    }

    @Override
    public void eliminar(String inventario, String user) {
        equipoDao.eliminarEquipo(inventario, user);
    }

    @Override
    public void modificarEquipo(String a, String b, String c) {

    }

    @Override
    public String altaEquipo(Equipo equipoGuardar) {
        Equipo equipo = equipoDao.guardarEquipo(equipoGuardar);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("equipo", equipo.getEquipo());
        jsonObject.put("sede", equipo.getSede());
        jsonObject.put("aula", equipo.getAula());
        jsonObject.put("marca", equipo.getMarca());
        jsonObject.put("numSerie", equipo.getNumSerie());
        jsonObject.put("numInventario", equipo.getNumInventario());
        jsonObject.put("estatus", equipo.getEstatus());
        jsonObject.put("observaciones", equipo.getObservaciones());
        jsonObject.put("mensaje", equipo.getMensaje());
        return jsonObject.toString();


    }

    @Override
    public String consultaEquipo(String buscraPor, String valor) {
        List<Equipo> lista = equipoDao.consultarEquipo(buscraPor, valor);
        System.out.println("consultaEquipo Serivce: " + lista.size());

        JSONArray equiposTodos = new JSONArray();
        for (Equipo equipo : lista) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("equipo", equipo.getEquipo());
            jsonObject.put("sede", equipo.getSede());
            jsonObject.put("aula", equipo.getAula());
            jsonObject.put("marca", equipo.getMarca());
            jsonObject.put("numSerie", equipo.getNumSerie());
            jsonObject.put("numInventario", equipo.getNumInventario());
            jsonObject.put("estatus", equipo.getEstatus());
            jsonObject.put("observaciones", equipo.getObservaciones());
            jsonObject.put("mensaje", equipo.getMensaje());
            equiposTodos.add(jsonObject);
        }
        System.out.println(equiposTodos.toString());
        return equiposTodos.toString();
    }



    @Override
    public String modificarEquipo(Equipo equipoGuardar) {
        Equipo equipo = equipoDao.modificarEquipo(equipoGuardar);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("equipo", equipo.getEquipo());
        jsonObject.put("sede", equipo.getSede());
        jsonObject.put("aula", equipo.getAula());
        jsonObject.put("marca", equipo.getMarca());
        jsonObject.put("numSerie", equipo.getNumSerie());
        jsonObject.put("numInventario", equipo.getNumInventario());
        jsonObject.put("estatus", equipo.getEstatus());
        jsonObject.put("observaciones", equipo.getObservaciones());
        jsonObject.put("mensaje", equipo.getMensaje());
        return jsonObject.toString();
    }
}


