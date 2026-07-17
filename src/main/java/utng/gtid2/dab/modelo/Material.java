package utng.gtid2.dab.modelo;

/**
 * Esta clase funciona como un "contenedor de datos".
 * Su propósito es representar cada material de nuestra base de datos 
 * como un objeto dentro de nuestro programa Java.
 */
public class Material {
    // Estos atributos guardan la información de cada material
    private int idMaterial;
    private String nombre;
    private int stockMinimo;
    private int cantidad;

    // Constructor vacío: necesario para que herramientas como el FXMLLoader 
    // o algunas librerías de bases de datos puedan crear objetos sin datos iniciales.
    public Material() {}

    // Constructor parcial: lo usamos cuando solo necesitamos identificar 
    // un material por su nombre.
    public Material(String nombre) {
        this.nombre = nombre;
    }

    // Constructor completo: lo usamos cuando vamos a crear un material nuevo 
    // (insertar) y ya tenemos todos sus datos listos.
    public Material(String nombre, int stockMinimo, int cantidad) {
        this.nombre = nombre;
        this.stockMinimo = stockMinimo;
        this.cantidad = cantidad;
    }

    // Los "getters" permiten obtener el valor de un atributo desde fuera de la clase.
    // Los "setters" permiten cambiar el valor de un atributo desde fuera de la clase.
    // Esto protege nuestros datos y nos permite controlarlos mejor.
    public int getIdMaterial() { return idMaterial; }
    public void setIdMaterial(int idMaterial) { this.idMaterial = idMaterial; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getStockMinimo() { return stockMinimo; }
    public void setStockMinimo(int stockMinimo) { this.stockMinimo = stockMinimo; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
}