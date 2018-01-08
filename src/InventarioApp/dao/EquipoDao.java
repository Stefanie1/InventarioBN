package InventarioApp.dao;

import InventarioApp.model.Equipo;

import java.util.List;

public interface EquipoDao {

    List<Equipo> getAll(String inventario);
    Equipo getEquipo(String inventario);
    void eliminarEquipo (String inventario);
}
