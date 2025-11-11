class Pedido:
    def __init__(self, id, nombre):
        self.id = id
        self.nombre = nombre

    def __repr__(self):
        return f"Pedido(id={self.id}, nombre='{self.nombre}')"