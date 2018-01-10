package InventarioApp.service;

import InventarioApp.model.Equipo;

import javax.servlet.http.HttpServletRequest;

/**
 * Specifies the behavior of the Equipo action to be performed.
 */
public interface EquipoAction {

    /**
     * Performs the desired action for a given {@link HttpServletRequest}. And produces the desired
     * result as a {@link String}.
     */
    String traerEquipo(String inventario);
    void eliminar (String inventario, String user);
    void modificarEquipo (String a, String b, String c);
    String altaEquipo(Equipo equipo);
    String consultaEquipo(String buscraPor,String valor );
     String modificarEquipo(Equipo equipoGuardar);
}
