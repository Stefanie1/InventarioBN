package InventarioApp.service;

import InventarioApp.dao.EquipoDao;
import InventarioApp.dao.EquipoDaoSql;
import InventarioApp.model.Equipo;
import org.json.simple.JSONObject;

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
        System.out.println("Inside traerEquipo "+inventario);
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
        jsonObject.put("mensaje",equipo.getMensaje());
        return jsonObject.toString();
    }

    @Override
    public void eliminar(String inventario) {
        equipoDao.eliminarEquipo(inventario);
    }
}
