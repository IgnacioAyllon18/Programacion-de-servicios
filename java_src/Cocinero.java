import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Cocinero extends Thread {
    private final List<Pedido> pedidos;
    private final String nombre;
    private final Object lock;
    private final String logPath;

    public Cocinero(String nombre, List<Pedido> pedidos, Object lock, String logPath) {
        this.nombre = nombre;
        this.pedidos = pedidos;
        this.lock = lock;
        this.logPath = logPath;
    }

    @Override
    public void run() {
        while (true) {
            Pedido pedido;
            synchronized (lock) {
                if (pedidos.isEmpty()) {
                    break;
                }
                pedido = pedidos.remove(0);
            }

            // preparar (simular)
            System.out.println(nombre + " esta preparando: " + pedido.getNombre() + " (ID " + pedido.getId() + ")");
            try {
                Thread.sleep(500 + (int)(Math.random() * 500));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            String linea = nombre + " preparo " + pedido.getNombre() + " (ID " + pedido.getId() + ")\n";
            // escribir en log en UTF-8 bajo el mismo lock
            synchronized (lock) {
                try (OutputStreamWriter osw = new OutputStreamWriter(
                        new FileOutputStream(logPath, true), StandardCharsets.UTF_8)) {
                    osw.write(linea);
                } catch (IOException e) {
                    System.err.println("Error escribiendo log: " + e.getMessage());
                }
            }

            System.out.println(nombre + " termino: " + pedido.getNombre() + " (ID " + pedido.getId() + ")");
        }
    }
}