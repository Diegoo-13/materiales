package utng.gtid2.dab.dao;

import utng.gtid2.dab.conexion.ConexionBD;
import utng.gtid2.dab.modelo.Material;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase DAO (Data Access Object) es la única encargada de hablar con la base de datos.
 * Su misión es ejecutar las operaciones CRUD (Crear, Leer, Actualizar, Borrar).
 */
public class MaterialDAO {

    /**
     * Consulta todos los materiales de la tabla y los convierte en una lista de objetos Material.
     */
    public List<Material> listarTodos() throws SQLException {
        List<Material> lista = new ArrayList<>();
        String sql = "SELECT * FROM materiales ORDER BY nombre";
        
        // Usamos try-with-resources para que la consulta se cierre sola al terminar
        try (Statement st = ConexionBD.getInstancia().getConexion().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            
            // Recorremos fila por fila el resultado de la base de datos
            while (rs.next()) {
                Material m = new Material();
                // Pasamos los datos de las columnas a los atributos del objeto
                m.setIdMaterial(rs.getInt("id_material"));
                m.setNombre(rs.getString("nombre"));
                m.setStockMinimo(rs.getInt("stock_minimo"));
                m.setCantidad(rs.getInt("cantidad"));
                lista.add(m);
            }
        }
        return lista;
    }
    
    /**
     * Toma un objeto Material y lo guarda como un nuevo registro en la base de datos.
     */
    public void insertar(Material m) throws SQLException {
        String sql = "INSERT INTO materiales (nombre, stock_minimo, cantidad) VALUES (?, ?, ?)";
        
        // Usamos PreparedStatement para evitar inyecciones SQL usando los signos "?"
        try (PreparedStatement ps = ConexionBD.getInstancia().getConexion().prepareStatement(sql)) {
            ps.setString(1, m.getNombre());
            ps.setInt(2, m.getStockMinimo());
            ps.setInt(3, m.getCantidad());
            ps.executeUpdate(); // Ejecutamos la inserción
        }
    }
    
    /**
     * Actualiza los datos de un material existente identificándolo por su ID.
     */
    public void actualizar(Material material) throws SQLException {
        String sql = "UPDATE materiales SET nombre = ?, stock_minimo = ?, cantidad = ? WHERE id_material = ?";
        
        try (Connection conn = ConexionBD.getInstancia().getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            // Asignamos los nuevos valores a los campos "?"
            ps.setString(1, material.getNombre());
            ps.setInt(2, material.getStockMinimo());
            ps.setInt(3, material.getCantidad());
            ps.setInt(4, material.getIdMaterial()); // El ID es vital para saber qué actualizar
            
            ps.executeUpdate();
        }
    }

    /**
     * Elimina un registro de la base de datos basándose en su ID.
     */
    public void eliminar(int idMaterial) throws SQLException {
        String sql = "DELETE FROM materiales WHERE id_material = ?";
        
        try (Connection conn = ConexionBD.getInstancia().getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idMaterial);
            ps.executeUpdate(); // Ejecutamos el borrado
        }
    }
}