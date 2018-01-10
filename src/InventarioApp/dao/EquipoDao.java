package InventarioApp.dao;

import InventarioApp.model.Equipo;

import java.util.List;

public interface EquipoDao {

    List<Equipo> getAll(String inventario);
    Equipo getEquipo(String inventario);
    int eliminarEquipo (String inventario, String user);
    Equipo guardarEquipo (Equipo equipo);
    List<Equipo> consultarEquipo (String buscraPor,String valor );
    Equipo modificarEquipo (Equipo equipo);
}
