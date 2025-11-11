import os
from pedido import Pedido
from cocinero import Cocinero
import threading

if __name__ == '__main__':
    base_path = os.getcwd()
    log_path = os.path.join(base_path, 'log_pedidos.txt')

    # inicializar log vacío
    with open(log_path, 'w', encoding='utf-8') as f:
        f.write('')

    lock = threading.Lock()
    pedidos = [
        Pedido(1, 'Lubina a la sal'),
        Pedido(2, 'Filete de ternera'),
        Pedido(3, 'Carne de cerdo a la barbacoa'),
        Pedido(4, 'Vieira gratinada'),
        Pedido(5, 'Langosta a la plancha'),
        Pedido(6, 'Gamba a la gabardina'),
    ]

    cocineros = [
        Cocinero('Cocinero-1', pedidos, lock, log_path),
        Cocinero('Cocinero-2', pedidos, lock, log_path),
        Cocinero('Cocinero-3', pedidos, lock, log_path),
    ]

    for c in cocineros:
        c.start()

    for c in cocineros:
        c.join()

    print('Todos los pedidos han sido procesados.')