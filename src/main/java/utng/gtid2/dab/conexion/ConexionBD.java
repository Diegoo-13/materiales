package utng.gtid2.dab.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Esta clase es el puente entre Java y la base de datos PostgreSQL.
 * Utiliza el patrón "Singleton" para garantizar que no abramos conexiones de más.
 */
public class ConexionBD {
    
    // Guardamos la única copia de esta clase aquí mismo
    private static ConexionBD instancia;
    
    // Datos necesarios para entrar a tu base de datos
    private static final String URL = "jdbc:postgresql://localhost:5432/insumosDB";
    private static final String USUARIO = "postgres"; 
    private static final String PASSWORD = "1225100314"; 
    
    // Objeto que representa la conexión física a la base de datos
    private Connection conexion;

    // Constructor privado: nadie fuera de esta clase puede crear una conexión directamente
    private ConexionBD() throws SQLException {
        conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
    }

    /**
     * Esta es la forma en la que el resto del programa pide la conexión.
     * Si no existe o se cerró, la crea; si ya existe, nos da la que ya tenemos.
     */
    public static synchronized ConexionBD getInstancia() throws SQLException {
        if (instancia == null || instancia.conexion == null || instancia.conexion.isClosed()) {
            instancia = new ConexionBD();
        }
        return instancia;
    }

    /**
     * Devuelve el objeto de conexión para que los DAOs puedan hacer consultas.
     */
    public Connection getConexion() {
        return conexion;
    }

    /**
     * Se asegura de cerrar la conexión de forma segura cuando terminamos de usarla
     * para no dejar procesos abiertos en la base de datos.
     */
    public static void cerrar() {
        try {
            if (instancia != null && instancia.conexion != null && !instancia.conexion.isClosed()) {
                instancia.conexion.close();
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}