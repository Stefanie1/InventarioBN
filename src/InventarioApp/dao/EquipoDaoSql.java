package InventarioApp.dao;

import InventarioApp.DatabaseHelper.DatabaseConnector;
import InventarioApp.DatabaseHelper.Status;
import InventarioApp.model.Equipo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class EquipoDaoSql implements EquipoDao {

    private static final String GET_EQUIPO = "select e.num_inventario as Inventario , e.tipo as Tipo , m.marca as Marca , e.num_serie as NoSerie , es.estatus as Estatus, a.aula as Aula , s.sede as Sede, es.observaciones as Observaciones , u.usuario as \tultimo_usuario\n" +
            "from Equipos e, Estatus es, Aulas a, Sede s, Marca m, Usuario u\n" +
            "\t\twhere e.idEstatus = es.idEstatus and \n" +
            "            e.idAulas    = a.idAulas  and \n" +
            "            e.idMarca  = m.idMarca  and \n" +
            "            e.Usuario =u.idUsuario and\n" +
            "            a.idSede = s.idSede\n" +
            "\t\t\t\thaving ( e.num_inventario= ?)";


    private DatabaseConnector db;
    private static Logger log = LogManager.getLogger();

    public EquipoDaoSql() {
        Status status = Status.OK;
        try {
            db = new DatabaseConnector("database.properties");
        } catch (FileNotFoundException e) {
            status = Status.MISSING_CONFIG;
        } catch (IOException e) {
            status = Status.MISSING_VALUES;
        }
        if (status != Status.OK) {
            log.fatal(status.message());
        }
    }

    public List<Equipo> getAll(String inventario) {

        System.out.printf("Inventario DAO "+ inventario);
        /*try (
                Connection connection = db.getConnection();
                PreparedStatement statement = connection.prepareStatement(GET_HOTELS);
        ) {

            statement.setString(1,city);

            ResultSet results = statement.executeQuery();
            while (results.next()) {



            }
        } catch (SQLException ex) {
            log.debug(ex.getMessage(), ex);
        }*/
        return null;
    }

    public Equipo getEquipo(String inventario) {
        Equipo equipo = new Equipo();

        try (
                Connection connection = db.getConnection();
                PreparedStatement statement = connection.prepareStatement(GET_EQUIPO);
        ) {

            System.out.println("getEquipo DAO "+ inventario);
            statement.setString(1,inventario);

            ResultSet results = statement.executeQuery();
            while (results.next()) {
                equipo.setEquipo(results.getString("tipo"));
                equipo.setAula(results.getString("aula"));
                equipo.setEstatus(results.getString("estatus"));
                equipo.setMarca(results.getString("marca"));
                equipo.setSede(results.getString("sede"));
                equipo.setObservaciones(results.getString("observaciones"));
                equipo.setNumSerie(results.getString("NoSerie"));
                equipo.setNumInventario(results.getString("Inventario"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return equipo;
    }

    public void eliminarEquipo (String inventario) {
        System.out.println("eliminado "+ inventario );

    }
}
