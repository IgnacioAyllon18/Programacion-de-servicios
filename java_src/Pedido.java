public class Pedido {
    private final int id;
    private final String nombre;

    public Pedido(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return "Pedido{" + "id=" + id + ", nombre='" + nombre + '\'' + '}';
    }
}