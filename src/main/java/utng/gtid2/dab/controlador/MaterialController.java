package utng.gtid2.dab.controlador;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import utng.gtid2.dab.dao.MaterialDAO;
import utng.gtid2.dab.modelo.Material;
import java.sql.SQLException;

/**
 * Esta clase es el "cerebro" de la pantalla.
 * Se encarga de conectar lo que el usuario ve (la interfaz) 
 * con la lógica de nuestra base de datos.
 */
public class MaterialController {

    // Referencias a los elementos visuales que definimos en el archivo FXML
    @FXML private TableView<Material> tablaMateriales;
    @FXML private TableColumn<Material, Integer> colId;
    @FXML private TableColumn<Material, String> colNombre;
    @FXML private TableColumn<Material, Integer> colStock;
    @FXML private TableColumn<Material, Integer> colCantidad;
    
    @FXML private TextField txtNombre;
    @FXML private TextField txtStock;
    @FXML private TextField txtCantidad;

    // Herramienta para comunicarnos con la base de datos
    private MaterialDAO dao = new MaterialDAO();

    /**
     * Se ejecuta automáticamente al abrir la ventana.
     * Aquí preparamos la tabla y definimos qué sucede al seleccionar un elemento.
     */
    @FXML
    public void initialize() {
        // Configuramos cada columna para que sepa qué dato de la clase Material mostrar
        colId.setCellValueFactory(new PropertyValueFactory<>("idMaterial"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("stockMinimo"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        
        // Cuando el usuario hace clic en una fila, pasamos los datos a los cuadros de texto
        tablaMateriales.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtNombre.setText(newSelection.getNombre());
                txtStock.setText(String.valueOf(newSelection.getStockMinimo()));
                txtCantidad.setText(String.valueOf(newSelection.getCantidad()));
            }
        });
        
        // Cargamos la lista inicial al iniciar
        cargarDatos();
    }

    /**
     * Trae los materiales desde la base de datos y los pone en la tabla.
     */
    private void cargarDatos() {
        try {
            ObservableList<Material> lista = FXCollections.observableArrayList(dao.listarTodos());
            tablaMateriales.setItems(lista);
        } catch (SQLException e) {
            mostrarAlerta("Error", "No se pudo cargar la lista: " + e.getMessage());
        }
    }

    /**
     * Se activa al presionar el botón de Guardar.
     * Recoge los datos, los valida y los envía al DAO para guardarlos en la base de datos.
     */
    @FXML
    private void handleGuardar() {
        try {
            String nombre = txtNombre.getText();
            int stock = Integer.parseInt(txtStock.getText());
            int cantidad = Integer.parseInt(txtCantidad.getText());
            
            // Verificamos que los datos sean lógicos antes de intentar guardarlos
            if (!nombre.isEmpty() && stock >= 0 && cantidad >= 0) {
                Material nuevo = new Material(nombre, stock, cantidad);
                dao.insertar(nuevo); 
                cargarDatos();
                limpiarCampos();
            } else {
                mostrarAlerta("Error", "Verifica que el nombre no esté vacío y los números sean >= 0.");
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Error de formato", "Stock y Cantidad deben ser números enteros.");
        } catch (Exception e) {
            mostrarAlerta("Error", "Ocurrió un error al guardar: " + e.getMessage());
        }
    }

    /**
     * Método auxiliar para mostrar mensajes de error al usuario.
     */
    private void mostrarAlerta(String titulo, String contenido) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }

    /**
     * Borra el contenido de los cuadros de texto para dejarlos listos para un nuevo registro.
     */
    private void limpiarCampos() {
        txtNombre.clear();
        txtStock.clear();
        txtCantidad.clear();
    }
    
    /**
     * Permite modificar un material que ya existe en la base de datos.
     */
    @FXML
    private void handleEditar() {
        Material seleccionado = tablaMateriales.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            try {
                seleccionado.setNombre(txtNombre.getText());
                seleccionado.setStockMinimo(Integer.parseInt(txtStock.getText()));
                seleccionado.setCantidad(Integer.parseInt(txtCantidad.getText()));
                
                dao.actualizar(seleccionado); 
                cargarDatos();
                limpiarCampos();
            } catch (Exception e) {
                mostrarAlerta("Error", "No se pudo actualizar: " + e.getMessage());
            }
        }
    }

    /**
     * Elimina el material seleccionado de la tabla y de la base de datos.
     */
    @FXML
    private void handleEliminar() {
        Material seleccionado = tablaMateriales.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            try {
                dao.eliminar(seleccionado.getIdMaterial()); 
                cargarDatos();
                limpiarCampos();
            } catch (SQLException e) {
                mostrarAlerta("Error", "No se pudo eliminar el registro: " + e.getMessage());
            }
        } else {
            mostrarAlerta("Aviso", "Selecciona un material de la tabla para eliminar.");
        }
    }
}