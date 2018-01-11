package InventarioApp.dao;

import InventarioApp.DatabaseHelper.DatabaseConnector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles all database-related actions. Uses singleton design pattern. Example of Prof. Engle
 */
public class LoginDaoSQL {

    /**
     * A {@link Logger log4j} logger for debugging.
     */
    private static Logger log = LogManager.getLogger();

    /**
     * Makes sure only one database handler is instantiated.
     */
    private static LoginDaoSQL singleton = new LoginDaoSQL();

    /**
     * Used to insert a new user into the database.
     */
    private static final String REGISTER_SQL =
            "INSERT INTO Usuario (Usuario, password) " +
                    "VALUES (?, ?)";

    /**
     * Used to determine if a username already exists.
     */
    private static final String USER_SQL =
            "SELECT Usuario FROM Usuario WHERE Usuario = ?";

    /**
     * Used to authenticate a user.
     */
    private static final String AUTH_SQL =
    "Select u.Usuario, r.rol \n"+
            "from Usuario u natural join Rol r\n"+
            "where Usuario = ? AND password = ?";

    /**
     * Used to authenticate a user.
     */
    private static final String ROL_CHANGE_SQL = "update Usuario SET idRol=? where Usuario=?";

    private static final String TRAERUSUARIOS_SQL = "Select u.Usuario, r.rol from Usuario u natural join Rol r ";

    /**
     * Used to configure connection to database.
     */
    private DatabaseConnector db;

    public LoginDaoSQL() {
        try {
            db = new DatabaseConnector("database.properties");
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        }
    }

    /**
     * Gets the single instance of the database handler.
     *
     * @return instance of the database handler
     */
    public static LoginDaoSQL getInstance() {
        return singleton;
    }

    /**
     * Tests if a user already exists in the database. Requires an active database connection.
     *
     * @param user       - username to check
     * @return Status.OK if user does not exist in database
     */
    private String duplicateUser(String user) {
        try (
                Connection connection = db.getConnection();
                PreparedStatement statement = connection.prepareStatement(USER_SQL);
        ) {
            statement.setString(1, user);
            ResultSet results = statement.executeQuery();
            return results.getString("Usuario");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return "";
    }


    /**
     * Registers a new user, placing the username, password hash, and salt into the database if the
     * username does not already exist.
     *
     * @param newuser - username of new user
     * @param newpass - password of new user
     */
    public String registerUser( String newuser, String newpass) {
        try (
                Connection connection = db.getConnection();
                PreparedStatement statement = connection.prepareStatement(REGISTER_SQL);
        ) {
            statement.setString(1, newuser);
            statement.setString(2, newpass);

            statement.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("registerUser "+ex.getMessage());
        }
        return "Guardado";
    }


    /**
     * Checks if the provided username and password match what is stored in the database. Requires an
     * active database connection.
     *
     * @param username - username to authenticate
     * @param password - password to authenticate
     */
    public List<String> authenticateUser(String username, String password) throws SQLException {
        try (
                Connection connection = db.getConnection();
                PreparedStatement statement = connection.prepareStatement(AUTH_SQL);
        ) {
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                List<String> result = new ArrayList<>();
                result.add(results.getString("Usuario"));
                result.add(results.getString("rol"));
                return result;
            }
        } catch (SQLException e) {
            System.out.println("authenticateUser "+e.getMessage());
        }
        return new ArrayList<>();
    }


    /**
     * Checks if the provided username and password match what is stored in the database. Requires an
     * active database connection.
     *
     * @param username - username to authenticate
     * @param rol - rol of the user
     */
    public void changeUserRol(String username, String rol) throws SQLException {
        try (
                Connection connection = db.getConnection();
                PreparedStatement statement = connection.prepareStatement(ROL_CHANGE_SQL);
        ) {
            statement.setString(2, username);
            statement.setString(1, rol);
            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("changeUserRol "+e.getMessage());
        }

    }

    public String traerusuarios () {
        try (
                Connection connection = db.getConnection();
                PreparedStatement statement = connection.prepareStatement(TRAERUSUARIOS_SQL);
        ) {
            ResultSet results = statement.executeQuery();

            JSONArray usuariosTodos = new JSONArray();
            while (results.next()){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("usuario", results.getString("Usuario"));
                jsonObject.put("rol",results.getString("rol"));
                usuariosTodos.add(jsonObject);
            }

            return usuariosTodos.toString();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return "[]";
    }
}
