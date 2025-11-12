import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class Cocina {
    public static void main(String[] args) {
        String basePath = System.getProperty("user.dir");
        String logPath = basePath + File.separator + "log_pedidos.txt";

        // inicializar log vacío en UTF-8
        try {
            Path p = Path.of(logPath);
            Files.write(p, new byte[0], StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            System.err.println("No se puede crear log: " + e.getMessage());
            return;
        }

        Object lock = new Object();
        List<Pedido> pedidos = new ArrayList<>();
        pedidos.add(new Pedido(1, "Calamares"));
        pedidos.add(new Pedido(2, "Huevos fritos con chorizo"));
        pedidos.add(new Pedido(3, "Lentejas"));
        pedidos.add(new Pedido(4, "Guisantes con jamon"));
        pedidos.add(new Pedido(5, "Cachopo"));
        pedidos.add(new Pedido(6, "Mejillones al vapor"));

        // al menos 3 cocineros
        Cocinero c1 = new Cocinero("Cocinero-1", pedidos, lock, logPath);
        Cocinero c2 = new Cocinero("Cocinero-2", pedidos, lock, logPath);
        Cocinero c3 = new Cocinero("Cocinero-3", pedidos, lock, logPath);

        c1.start();
        c2.start();
        c3.start();

        try {
            c1.join();
            c2.join();
            c3.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Todos los pedidos han sido procesados.");
    }
}