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
import java.util.ArrayList;
import java.util.List;


public class EquipoDaoSql implements EquipoDao {

    private static final String TIPO = "tipo";
    private static final String SERIE = "num_serie";
    private static final String INVENTARIO = "num_inventario";
    private static final String ESTATUS = "estatus";
    private static final String AULA = "aula";
    private static final String MARCA = "marca";
    private static final String SEDE = "sede";

    private static final String GET_EQUIPO = "select e.num_inventario as Inventario , e.tipo as Tipo , m.marca as Marca , e.num_serie as NoSerie , es.estatus as Estatus, a.aula as Aula , s.sede as Sede, e.Observaciones as Observaciones , u.usuario as \tultimo_usuario\n" +
            "from Equipos e, Estatus es, Aulas a, Sede s, Marca m, Usuario u\n" +
            "\t\twhere e.idEstatus = es.idEstatus and \n" +
            "            e.idAulas    = a.idAulas  and \n" +
            "            e.idMarca  = m.idMarca  and \n" +
            "            e.Usuario =u.idUsuario and\n" +
            "            a.idSede = s.idSede\n" +
            "\t\t\t\thaving ( e.num_inventario= ?)";

    private static final String ELIMINAR_EQUIPO = "UPDATE Equipos SET eliminado = true , UsuarioElinimar=(SELECT idUsuario FROM Usuario WHERE usuario = ?) WHERE num_inventario = ?";

    private static final String MODIFICAR_EQUIP = "";

    private static final String GUARDAR_EQUIPO = " \n" +
            "INSERT INTO Equipos(tipo,num_serie,num_inventario,idEstatus,idAulas,idMarca,eliminado,Usuario, observaciones) values (?, ?, ?,  (SELECT  idEstatus FROM  Estatus WHERE estatus =?), (SELECT  idAulas FROM  Aulas WHERE aula =?),  (SELECT  idMarca FROM  Marca WHERE  marca=?\n" +
            "),? ,(SELECT  idUsuario FROM  Usuario WHERE  Usuario=?),?);";

    private static final String CONSULTAR_EQUIPO = " \n" +
            "select e.num_inventario as Inventario , e.tipo as Tipo , m.marca as Marca , e.num_serie as NoSerie , es.estatus as Estatus, a.aula as Aula , s.sede as Sede, e.Observaciones as Observaciones , u.usuario as ultimo_usuario\n" +
            "from Equipos e, Estatus es, Aulas a, Sede s, Marca m, Usuario u\n" +
            "            where e.idEstatus = es.idEstatus and \n" +
            "                    e.idAulas    = a.idAulas  and \n" +
            "                      e.idMarca  = m.idMarca  and \n" +
            "                      e.Usuario =u.idUsuario and\n" +
            "                    a.idSede = s.idSede\n" +
            "\t\thaving (tipo=?)";

    private static final String CONSULTAR_AULA = " \n" +
            "select e.num_inventario as Inventario , e.tipo as Tipo , m.marca as Marca , e.num_serie as NoSerie , es.estatus as Estatus, a.aula as Aula , s.sede as Sede, e.Observaciones as Observaciones , u.usuario as ultimo_usuario\n" +
            "from Equipos e, Estatus es, Aulas a, Sede s, Marca m, Usuario u\n" +
            "            where e.idEstatus = es.idEstatus and \n" +
            "                    e.idAulas    = a.idAulas  and \n" +
            "                      e.idMarca  = m.idMarca  and \n" +
            "                      e.Usuario =u.idUsuario and\n" +
            "                    a.idSede = s.idSede\n" +
            "\t\thaving (aula=?)";

    private static final String CONSULTAR_SEDE = " \n" +
            "select e.num_inventario as Inventario , e.tipo as Tipo , m.marca as Marca , e.num_serie as NoSerie , es.estatus as Estatus, a.aula as Aula , s.sede as Sede, e.Observaciones as Observaciones , u.usuario as ultimo_usuario\n" +
            "from Equipos e, Estatus es, Aulas a, Sede s, Marca m, Usuario u\n" +
            "            where e.idEstatus = es.idEstatus and \n" +
            "                    e.idAulas    = a.idAulas  and \n" +
            "                      e.idMarca  = m.idMarca  and \n" +
            "                      e.Usuario =u.idUsuario and\n" +
            "                    a.idSede = s.idSede\n" +
            "\t\thaving (sede=?)";

    private static final String CONSULTAR_SERIE = " \n" +
            "select e.num_inventario as Inventario , e.tipo as Tipo , m.marca as Marca , e.num_serie as NoSerie , es.estatus as Estatus, a.aula as Aula , s.sede as Sede, e.Observaciones as Observaciones , u.usuario as ultimo_usuario\n" +
            "from Equipos e, Estatus es, Aulas a, Sede s, Marca m, Usuario u\n" +
            "            where e.idEstatus = es.idEstatus and \n" +
            "                    e.idAulas    = a.idAulas  and \n" +
            "                      e.idMarca  = m.idMarca  and \n" +
            "                      e.Usuario =u.idUsuario and\n" +
            "                    a.idSede = s.idSede\n" +
            "\t\thaving (num_serie=?)";

    private static final String CONSULTAR_INVENTARIO = " \n" +
            "select e.num_inventario as Inventario , e.tipo as Tipo , m.marca as Marca , e.num_serie as NoSerie , es.estatus as Estatus, a.aula as Aula , s.sede as Sede, e.Observaciones as Observaciones , u.usuario as ultimo_usuario\n" +
            "from Equipos e, Estatus es, Aulas a, Sede s, Marca m, Usuario u\n" +
            "            where e.idEstatus = es.idEstatus and \n" +
            "                    e.idAulas    = a.idAulas  and \n" +
            "                      e.idMarca  = m.idMarca  and \n" +
            "                      e.Usuario =u.idUsuario and\n" +
            "                    a.idSede = s.idSede\n" +
            "\t\thaving (num_inventario=?)";

    private static final String CONSULTAR_ESTATUS = " \n" +
            "select e.num_inventario as Inventario , e.tipo as Tipo , m.marca as Marca , e.num_serie as NoSerie , es.estatus as Estatus, a.aula as Aula , s.sede as Sede, e.Observaciones as Observaciones , u.usuario as ultimo_usuario\n" +
            "from Equipos e, Estatus es, Aulas a, Sede s, Marca m, Usuario u\n" +
            "            where e.idEstatus = es.idEstatus and \n" +
            "                    e.idAulas    = a.idAulas  and \n" +
            "                      e.idMarca  = m.idMarca  and \n" +
            "                      e.Usuario =u.idUsuario and\n" +
            "                    a.idSede = s.idSede\n" +
            "\t\thaving (estatus=?)";

    private static final String CONSULTAR_MARCA = " \n" +
            "select e.num_inventario as Inventario , e.tipo as Tipo , m.marca as Marca , e.num_serie as NoSerie , es.estatus as Estatus, a.aula as Aula , s.sede as Sede, e.Observaciones as Observaciones , u.usuario as ultimo_usuario\n" +
            "from Equipos e, Estatus es, Aulas a, Sede s, Marca m, Usuario u\n" +
            "            where e.idEstatus = es.idEstatus and \n" +
            "                    e.idAulas    = a.idAulas  and \n" +
            "                      e.idMarca  = m.idMarca  and \n" +
            "                      e.Usuario =u.idUsuario and\n" +
            "                    a.idSede = s.idSede\n" +
            "\t\thaving (marca=?)";


    private static final String MODIFICAR_EQUIPO ="UPDATE  Equipos\n" +
            "SET idEstatus = (SELECT  idEstatus FROM  Estatus WHERE estatus =?),\n" +
            "\t\tobservaciones = ?,\n" +
            "        idAulas = (SELECT  idAulas FROM  Aulas a WHERE aula = s? and a.idSede = (Select idSede from Sede where sede = ?))\n" +
            "\t\tWHERE  num_inventario= ?";




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
        System.out.printf("Inventario DAO " + inventario);
        return null;
    }

    @Override
    public Equipo getEquipo(String inventario) {
        Equipo equipo = new Equipo();

        try (
                Connection connection = db.getConnection();
                PreparedStatement statement = connection.prepareStatement(GET_EQUIPO);
        ) {

            System.out.println("getEquipo DAO " + inventario);
            statement.setString(1, inventario);

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
                equipo.setMensaje("Guardado");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return equipo;
    }

    @Override
    public int eliminarEquipo(String inventario, String user) {
        try (
                Connection connection = db.getConnection();
                PreparedStatement statement = connection.prepareStatement(ELIMINAR_EQUIPO);
        ) {
            statement.setString(1, user);
            statement.setString(2, inventario);
            return statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return 0;
    }

    @Override
    public Equipo guardarEquipo(Equipo equipo) {
        try (
                Connection connection = db.getConnection();
                PreparedStatement statement = connection.prepareStatement(GUARDAR_EQUIPO);
        ) {
            statement.setString(1, equipo.getEquipo());
            statement.setString(2, equipo.getNumSerie());
            statement.setString(3, equipo.getNumInventario());
            statement.setString(4, equipo.getEstatus());
            statement.setString(5, equipo.getAula());
            statement.setString(6, equipo.getMarca());
            statement.setBoolean(7, false);
            statement.setString(8, equipo.getUser());
            statement.setString(9, equipo.getObservaciones());

            statement.executeUpdate();
            return getEquipo(equipo.getNumInventario());

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return new Equipo();
    }

    @Override
    public List<Equipo> consultarEquipo(String buscraPor, String valor) {
        try (
                Connection connection = db.getConnection();
        ) {
            PreparedStatement statement;
            switch (buscraPor) {
                case "equipo":
                    statement = connection.prepareStatement(CONSULTAR_EQUIPO);
                    break;
                case "sede":
                    statement = connection.prepareStatement(CONSULTAR_SEDE);
                    break;
                case "marca":
                    statement = connection.prepareStatement(CONSULTAR_MARCA);
                    break;
                case "aula":
                    statement = connection.prepareStatement(CONSULTAR_AULA);
                    break;
                case "numSerie":
                    statement = connection.prepareStatement(CONSULTAR_SERIE);
                    break;
                case "numInventario":
                    statement = connection.prepareStatement(CONSULTAR_INVENTARIO);
                    break;
                case "estatus":
                    statement = connection.prepareStatement(CONSULTAR_ESTATUS);
                    break;
                default:
                    System.out.println("No such key");
                    statement = null;
            }

            statement.setString(1, valor);

            ResultSet resultado = statement.executeQuery();
            List<Equipo> lisEquipos = new ArrayList<>();
            while (resultado.next()) {
                System.out.println("Equipo " + resultado.getString("tipo"));

                Equipo completo = new Equipo();
                completo.setEquipo(resultado.getString("tipo"));
                completo.setAula(resultado.getString("aula"));
                completo.setEstatus(resultado.getString("estatus"));
                completo.setMarca(resultado.getString("marca"));
                completo.setSede(resultado.getString("sede"));
                completo.setObservaciones(resultado.getString("observaciones"));
                completo.setNumSerie(resultado.getString("NoSerie"));
                completo.setNumInventario(resultado.getString("Inventario"));
                completo.setMensaje("Guardado");

                lisEquipos.add(completo);
            }
            return lisEquipos;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return new ArrayList<Equipo>();
    }


    @Override
    public Equipo modificarEquipo (Equipo equipo) {
        try (
                Connection connection = db.getConnection();
                PreparedStatement statement = connection.prepareStatement(MODIFICAR_EQUIPO);
        ) {
            System.out.println("Num Inventario DAO: "+equipo.getNumInventario());

            statement.setString(1, equipo.getEstatus());
            statement.setString(2, equipo.getObservaciones());
            statement.setString(3, equipo.getAula());
            statement.setString(4, equipo.getSede());
            statement.setString(5, equipo.getNumInventario());

            statement.executeUpdate();

            return getEquipo(equipo.getNumInventario());

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return new Equipo();
    }
    }