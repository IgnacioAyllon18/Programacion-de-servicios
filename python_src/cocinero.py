import threading
import time
import random

class Cocinero(threading.Thread):
    def __init__(self, nombre, pedidos, lock, log_path):
        super().__init__()
        self.nombre = nombre
        self.pedidos = pedidos
        self.lock = lock
        self.log_path = log_path

    def run(self):
        while True:
            with self.lock:
                if not self.pedidos:
                    break
                pedido = self.pedidos.pop(0)

            print(f"{self.nombre} está preparando: {pedido.nombre} (ID {pedido.id})")
            time.sleep(0.5 + random.random() * 0.5)

            linea = f"{self.nombre} preparó {pedido.nombre} (ID {pedido.id})\n"
            with self.lock:
                with open(self.log_path, 'a', encoding='utf-8') as f:
                    f.write(linea)

            print(f"{self.nombre} terminó: {pedido.nombre} (ID {pedido.id})")