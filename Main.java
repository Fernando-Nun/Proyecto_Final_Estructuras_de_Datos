import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections; // Needed for sorting
import java.util.Arrays; // Needed for Arrays.fill() in GrafoMesas

// Clase para definir colores en la consola
class Colores {
    public static final String AZUL = "\u001B[34;1;5m";
    public static final String ROJO = "\u001B[31;1;3m";
    public static final String BLANCO = "\u001B[37;1;3m";
    public static final String VERDE = "\u001B[32;1;3m";
    public static final String MORADO = "\u001B[35;1;3m";
    public static final String CIAN = "\u001B[36;1;3m";
    public static final String AMARILLO = "\u001B[33;1;3m";
    public static final String RESET = "\u001B[0m";
}

// Clase para representar un platillo en el menú
class Platillo {
    private String nombre;
    private String descripcion;
    private double precio;
    private String categoria;

    // Constructor de la clase Platillo
    public Platillo(String nombre, String descripcion, double precio, String categoria) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.categoria = categoria;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return nombre + " - " + descripcion + " - $" + precio + " (" + categoria + ")";
    }
}

// Clase para la gestión del menú del restaurante
class Menu {
    private List<Platillo> menu;

    public Menu() {
        menu = new ArrayList<>();
        inicializarMenu();
    }

    // Método para inicializar el menú con algunos platillos
    private void inicializarMenu() {
        // Antipasti
        agregar(new Platillo(Colores.AMARILLO + "Bruschetta" + Colores.RESET,
                Colores.BLANCO + "Pan tostado con tomate, ajo y albahaca", 8.50, "Antipasti"));
        agregar(new Platillo(Colores.AMARILLO + "Antipasto Misto" + Colores.RESET,
                Colores.BLANCO + "Selección de carnes frías, quesos y aceitunas", 12.00, "Antipasti"));

        // Pasta
        agregar(new Platillo(Colores.AMARILLO + "Spaghetti Carbonara" + Colores.RESET,
                Colores.BLANCO + "Pasta con pancetta, huevo y queso parmesano", 14.50, "Pasta"));
        agregar(new Platillo(Colores.AMARILLO + "Fettuccine Alfredo" + Colores.RESET,
                Colores.BLANCO + "Pasta en salsa cremosa de mantequilla y parmesano", 13.00, "Pasta"));
        agregar(new Platillo(Colores.AMARILLO + "Lasagna della Casa" + Colores.RESET,
                Colores.BLANCO + "Lasaña tradicional con carne y bechamel", 16.00, "Pasta"));

        // Secondi
        agregar(new Platillo(Colores.AMARILLO + "Osso Buco" + Colores.RESET,
                Colores.BLANCO + "Jarrete de ternera braseado con vegetales", 22.00, "Secondi"));
        agregar(new Platillo(Colores.AMARILLO + "Pollo Parmigiana" + Colores.RESET,
                Colores.BLANCO + "Pechuga empanizada con salsa marinara y mozzarella", 18.50, "Secondi"));

        // Dolci
        agregar(new Platillo(Colores.AMARILLO + "Tiramisu" + Colores.RESET,
                Colores.BLANCO + "Postre tradicional con café y mascarpone", 7.50, "Dolci"));
        agregar(new Platillo(Colores.AMARILLO + "Panna Cotta" + Colores.RESET,
                Colores.BLANCO + "Postre cremoso con frutos rojos", 6.50, "Dolci"));
    }

    // Método para agregar un platillo al menú
    public void agregar(Platillo platillo) {
        // Definir el orden de las categorías
        String[] ordenCategorias = { "Antipasti", "Pasta", "Secondi", "Dolci" };

        // Encontrar la posición donde insertar el platillo
        int posicionInsertar = menu.size();

        for (int i = 0; i < menu.size(); i++) {
            String categoriaActual = menu.get(i).getCategoria();
            String categoriaNueva = platillo.getCategoria();

            // Obtener índices de categorías en el orden definido
            int indiceCategoriaActual = getIndicateCategoria(categoriaActual, ordenCategorias);
            int indiceCategoriaNueva = getIndicateCategoria(categoriaNueva, ordenCategorias);

            // Si encontramos una categoría que viene después en el orden
            if (indiceCategoriaActual > indiceCategoriaNueva) {
                posicionInsertar = i;
                break;
            }
        }

        menu.add(posicionInsertar, platillo);
    }

    // Método auxiliar para obtener el índice de una categoría
    private int getIndicateCategoria(String categoria, String[] ordenCategorias) {
        for (int i = 0; i < ordenCategorias.length; i++) {
            if (ordenCategorias[i].equalsIgnoreCase(categoria)) {
                return i;
            }
        }
        return ordenCategorias.length; // Si no se encuentra, va al final
    }

    // Método para agregar un platillo al menú
    public void agregarPlatillo() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nombre del platillo: ");
        String nombre = scanner.nextLine();
        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine();
        System.out.print("Precio: ");
        double precio = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Categoría: ");
        String categoria = scanner.nextLine();

        agregar(new Platillo(Colores.AMARILLO + nombre + Colores.RESET, Colores.BLANCO + descripcion, precio,
                categoria));
        System.out.println("Platillo agregado exitosamente.");
    }

    // Método para eliminar un platillo del menú
    public boolean eliminar(String nombre) {
        return menu.removeIf(
                platillo -> platillo.getNombre().replaceAll("\u001B\\[[;\\d]*m", "").equalsIgnoreCase(nombre));
    }

    // Método para buscar un platillo en el menú
    public Platillo buscar(String nombre) {
        for (Platillo platillo : menu) {
            String nombreLimpio = platillo.getNombre().replaceAll("\u001B\\[[;\\d]*m", "");
            if (nombreLimpio.equalsIgnoreCase(nombre)) {
                return platillo;
            }
        }
        return null;
    }

    // Método para mostrar el menú completo
    public void mostrarMenu() {
        String categoriaActual = "";
        System.out.println("\n=== MENÚ DOLCE ALBA ===");
        for (Platillo platillo : menu) {
            if (!platillo.getCategoria().equals(categoriaActual)) {
                categoriaActual = platillo.getCategoria();
                System.out.println(Colores.ROJO + "\n--- " + categoriaActual + " ---" + Colores.RESET);
            }
            System.out.println(platillo);
        }
        System.out.println();
    }

    // Método para mostrar los platillos de una categoría específica
    public void mostrarPorCategoria(String categoria) {
        System.out.println("\n--- " + categoria + " ---");
        for (Platillo platillo : menu) {
            if (platillo.getCategoria().equalsIgnoreCase(categoria)) {
                System.out.println(platillo);
            }
        }
        System.out.println();
    }

    public List<Platillo> getMenu() {
        return new ArrayList<>(menu);
    }
}

class Pila1 {
    private String[] data;
    private int top;
    private int capacity;

    // Constructor
    public Pila1(int capacity) {
        this.capacity = capacity;
        data = new String[capacity];
        top = -1; // Pila vacía
    }

    // Métodos básicos de la pila
    public boolean isEmpty() {
        return top == -1;
    }

    public boolean isFull() {
        return top == capacity - 1;
    }

    public String push(String x) {
        if (isFull()) {
            Main.limpiarPantalla();
            System.out
                    .println(Colores.ROJO + "\nCupo lleno, ya no se pueden agregar más insumos: " + Colores.BLANCO + x);
            return null;
        }
        Main.limpiarPantalla();
        System.out.println(Colores.VERDE + "\nInsumo agregado: " + Colores.BLANCO + x);
        return data[++top] = x;
    }

    public String pop() {
        if (isEmpty()) {
            System.out.println(Colores.ROJO + "No podemos quitar insumos, no hay ninguno." + Colores.RESET);
            return null;
        }
        return data[top--];
    }

    public String peek() {
        if (isEmpty()) {
            System.out.println(Colores.ROJO + "No hay insumos en el inventario." + Colores.RESET);
            return null;
        }
        return data[top];
    }

    public void show() {
        if (isEmpty()) {
            System.out.println(Colores.ROJO + "El inventario está vacío." + Colores.RESET);
            return;
        }
        System.out.println(Colores.AMARILLO + "Contenido del inventario: \n" + Colores.RESET);
        for (int i = top; i >= 0; i--) {
            System.out.println(Colores.CIAN + "| " + Colores.RESET + Colores.BLANCO + data[i] + Colores.RESET
                    + Colores.CIAN + " |" + Colores.RESET);
        }
        System.out.println();
    }

    // 🔹 Método de gestión del inventario dentro de la misma clase
    public void gestionarInventario() {
        Scanner sc = new Scanner(System.in);
        boolean condition = true;

        while (condition) {
            System.out.println(Colores.AZUL + "\n⭐🌟     GESTIÓN DE INVENTARIO (PILAS)    🌟⭐" + Colores.RESET);
            System.out.println(Colores.AZUL + "──────────────────────" + Colores.ROJO + "୨ৎ" + Colores.AZUL
                    + "─────────────────────" + Colores.RESET);
            System.out.println(Colores.BLANCO + "╔═══════════════════════════════════════════╗" + Colores.RESET);
            System.out.println(Colores.BLANCO + "║ 1. Añadir insumo al inventario ➕         ║");
            System.out.println("║ 2. Retirar insumo ➖                      ║");
            System.out.println("║ 3. Mostrar el último insumo agregado 📃   ║");
            System.out.println("║ 4. Mostrar inventario 📋                  ║");
            System.out.println("║ 0. Volver al menú principal 🏠︎            ║" + Colores.RESET);
            System.out.println(Colores.BLANCO + "╚═══════════════════════════════════════════╝" + Colores.RESET);
            System.out.print(Colores.MORADO + "\nSeleccione una opción: " + Colores.RESET + Colores.BLANCO);
            int option = sc.nextInt();

            switch (option) {
                case 1:
                    Main.limpiarPantalla();
                    System.out.println(Colores.AMARILLO
                            + "⚠️    Recuerda agregar primero el que tenga mayor tiempo de caducidad." + Colores.RESET);
                    System.out.print(Colores.MORADO + "\nEscribe el nombre del producto para agregarlo: "
                            + Colores.RESET + Colores.BLANCO);
                    String x = sc.next();
                    push(x);
                    break;
                case 2:
                    Main.limpiarPantalla();
                    String retirado = pop();
                    if (retirado != null) {
                        System.out.println(
                                Colores.AMARILLO + "Insumo retirado: " + Colores.RESET + Colores.BLANCO + retirado);
                    }
                    break;
                case 3:
                    Main.limpiarPantalla();
                    String ultimo = peek();
                    if (ultimo != null) {
                        System.out.println(Colores.AMARILLO + "El último insumo agregado fue: " + Colores.RESET
                                + Colores.BLANCO + ultimo);
                    }
                    break;
                case 4:
                    Main.limpiarPantalla();
                    show();
                    break;
                case 0:
                    condition = false;
                    System.out.println("Regresando al menú principal...");
                    Main.limpiarPantalla();
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }
}

class Node {
    String name; // nombre del cliente
    int priority; // 1 = con reservación (mas urgente), 2 = sin reservacion
    Node next;

    public Node(String name, int priority) {
        this.name = name;
        this.priority = priority;
        this.next = null;
    }
}

// Cola con prioridad para clientes
class PriorityQueue {
    private Node front; // frente de la cola (mayor prioridad al frente)

    public PriorityQueue() {
        front = null;
    }

    // Insertar cliente en funcion de su prioridad (1 mejor que 2)
    public void enqueue(String name, int priority) {
        Node newNode = new Node(name, priority);

        if (front == null || priority < front.priority) {
            newNode.next = front;
            front = newNode;
        } else {
            Node temp = front;
            while (temp.next != null && temp.next.priority <= priority) {
                temp = temp.next;
            }
            newNode.next = temp.next;
            temp.next = newNode;
        }
        System.out.println(Colores.CIAN + "Agregado: " + Colores.BLANCO + etiqueta(newNode));
    }

    // Atiende (elimina) al cliente del frente
    public void dequeue() {
        if (front == null) {
            System.out.println(Colores.ROJO + "La cola está vacia" + Colores.RESET);
            return;
        }
        System.out.println(Colores.CIAN + "Atendido: " + Colores.BLANCO + etiqueta(front));
        front = front.next;
    }

    // Elimina por nombre (en cualquier posicion)
    public void removeByName(String name) {
        if (front == null) {
            System.out.println(Colores.ROJO + "La cola está vacia" + Colores.RESET);
            return;
        }
        if (front.name.equalsIgnoreCase(name)) {
            System.out.println(Colores.ROJO + "Eliminado: " + Colores.BLANCO + etiqueta(front));
            front = front.next;
            return;
        }
        Node cur = front;
        while (cur.next != null && !cur.next.name.equalsIgnoreCase(name)) {
            cur = cur.next;
        }
        if (cur.next == null) {
            System.out.println(Colores.AMARILLO + "No se encontro al cliente: " + Colores.BLANCO + name);
        } else {
            System.out.println("Atendido con éxito: " + etiqueta(cur.next));
            cur.next = cur.next.next;
        }
    }

    // Ver próximo a atender
    public void peek() {
        if (front == null) {
            System.out.println(Colores.ROJO + "La cola está vacia" + Colores.RESET);
        } else {
            System.out.println(Colores.AMARILLO + "Siguiente: " + Colores.BLANCO + etiqueta(front));
        }
    }

    // Mostrar toda la cola
    public void display() {
        if (front == null) {
            System.out.println(Colores.ROJO + "La cola está vacia" + Colores.RESET);
            return;
        }
        Node temp = front;
        System.out.println(Colores.CIAN + "— Cola de clientes —\n" + Colores.RESET);
        while (temp != null) {
            System.out.println("  • " + etiqueta(temp));
            temp = temp.next;
        }
    }

    public boolean isEmpty() {
        return front == null;
    }

    private String etiqueta(Node n) {
        String tipo = (n.priority == 1) ? Colores.VERDE + "Con reservación" + Colores.RESET
                : Colores.ROJO + "Sin reservación" + Colores.RESET;
        return String.format(Colores.BLANCO + "%-15s | %s" + Colores.RESET, n.name, tipo);
    }
}

class Colas {
    public static void gestionarClientes() {
        Scanner sc = new Scanner(System.in).useLocale(Locale.US);
        PriorityQueue cola = new PriorityQueue();

        while (true) {
            System.out.println(Colores.AZUL + "\n⭐🌟     GESTIÓN DE CLIENTES (COLAS)    🌟⭐" + Colores.RESET);
            System.out.println(Colores.AZUL + "──────────────────────" + Colores.ROJO + "୨ৎ" + Colores.AZUL
                    + "─────────────────────" + Colores.RESET);
            System.out.println(Colores.BLANCO + "╔═══════════════════════════════════════════╗" + Colores.RESET);
            System.out.println(Colores.BLANCO + "║ 1. Agregar cliente                        ║");
            System.out.println("║ 2. Mostrar cliente al frente de la cola   ║");
            System.out.println("║ 3. Atender cliente al frente de la cola   ║");
            System.out.println("║ 4. Eliminar cliente por nombre            ║");
            System.out.println("║ 5. Ver toda la cola                       ║");
            System.out.println("║ 0. Salir                                  ║" + Colores.RESET);
            System.out.println(Colores.BLANCO + "╚═══════════════════════════════════════════╝" + Colores.RESET);
            System.out.print(Colores.MORADO + "\nSelecciones una opción: " + Colores.RESET + Colores.BLANCO);

            String op = sc.nextLine().trim();

            switch (op) {
                case "1": { // agregar
                    Main.limpiarPantalla();
                    System.out.print(Colores.MORADO + "Nombre del cliente: " + Colores.RESET + Colores.BLANCO);
                    String nombre = sc.nextLine().trim();
                    if (nombre.isEmpty()) {
                        Main.limpiarPantalla();
                        System.out.println(Colores.ROJO + "\nEl nombre no puede estar vacio." + Colores.RESET);
                        break;
                    }
                    int prioridad = preguntarPrioridad(sc);
                    cola.enqueue(nombre, prioridad);
                    break;
                }
                case "2": // peek
                    Main.limpiarPantalla();
                    cola.peek();
                    break;

                case "3": // dequeue
                    Main.limpiarPantalla();
                    cola.dequeue();
                    break;

                case "4": { // remove by name
                    System.out.print(Colores.MORADO + "\nNombre a eliminar: " + Colores.RESET + Colores.BLANCO);
                    String nombre = sc.nextLine().trim();
                    Main.limpiarPantalla();
                    cola.removeByName(nombre);
                    break;
                }
                case "5": // display
                    Main.limpiarPantalla();
                    cola.display();
                    break;

                case "0":
                    System.out.println("Regresando...");
                    Main.limpiarPantalla();
                    return;

                default:
                    System.out.println("Opcion invalida.");
            }
        }
    }

    // Pregunta si tiene reservacion y devuelve la prioridad (1 o 2)
    private static int preguntarPrioridad(Scanner sc) {
        while (true) {
            System.out.print(Colores.AMARILLO + "\n¿Tiene reservación? (s/n): " + Colores.RESET + Colores.BLANCO);
            String s = sc.nextLine().trim().toLowerCase();
            if (s.equals("s") || s.equals("si") || s.equals("sí"))
                return 1; // más prioridad
            if (s.equals("n") || s.equals("no"))
                return 2;
            System.out.println(Colores.ROJO + "\nRespuesta inválida. Escribe 's' o 'n'." + Colores.RESET);
        }
    }
}

// Nodo del árbol para empleados
class NodoEmpleado {
    int idEmpleado;
    String nombre;
    String puesto;
    NodoEmpleado izquierdo;
    NodoEmpleado derecho;

    public NodoEmpleado(int idEmpleado, String nombre, String puesto) {
        this.idEmpleado = idEmpleado;
        this.nombre = nombre;
        this.puesto = puesto;
    }

    @Override
    public String toString() {
        return String.format(Colores.CIAN + "ID: " + Colores.BLANCO + "%d" + Colores.CIAN +
                ", Nombre: " + Colores.BLANCO + "%s" + Colores.CIAN +
                ", Puesto: " + Colores.BLANCO + "%s" + Colores.RESET,
                idEmpleado, nombre, puesto);
    }
}

// Árbol binario de búsqueda para empleados
class ArbolBinarioBusqueda {
    private NodoEmpleado raiz;

    public void insertar(int idEmpleado, String nombre, String puesto) {
        raiz = insertarRecursivo(raiz, idEmpleado, nombre, puesto);
    }

    private NodoEmpleado insertarRecursivo(NodoEmpleado actual, int idEmpleado, String nombre, String puesto) {
        if (actual == null) {
            return new NodoEmpleado(idEmpleado, nombre, puesto);
        }
        if (idEmpleado < actual.idEmpleado) {
            actual.izquierdo = insertarRecursivo(actual.izquierdo, idEmpleado, nombre, puesto);
        } else if (idEmpleado > actual.idEmpleado) {
            actual.derecho = insertarRecursivo(actual.derecho, idEmpleado, nombre, puesto);
        }
        return actual;
    }

    public NodoEmpleado buscar(int idEmpleado) {
        return buscarRecursivo(raiz, idEmpleado);
    }

    private NodoEmpleado buscarRecursivo(NodoEmpleado actual, int idEmpleado) {
        if (actual == null || actual.idEmpleado == idEmpleado) {
            return actual;
        }
        return idEmpleado < actual.idEmpleado
            ? buscarRecursivo(actual.izquierdo, idEmpleado)
            : buscarRecursivo(actual.derecho, idEmpleado);
    }

    public void eliminar(int idEmpleado) {
        raiz = eliminarRecursivo(raiz, idEmpleado);
    }

    private NodoEmpleado eliminarRecursivo(NodoEmpleado actual, int idEmpleado) {
        if (actual == null) {
            return actual;
        }

        if (idEmpleado < actual.idEmpleado) {
            actual.izquierdo = eliminarRecursivo(actual.izquierdo, idEmpleado);
        } else if (idEmpleado > actual.idEmpleado) {
            actual.derecho = eliminarRecursivo(actual.derecho, idEmpleado);
        } else {
            // Nodo encontrado - casos de eliminación
            if (actual.izquierdo == null) {
                return actual.derecho;
            } else if (actual.derecho == null) {
                return actual.izquierdo;
            }

            // Nodo con dos hijos: obtener el sucesor inorden
            actual.idEmpleado = obtenerMenorValor(actual.derecho);
            actual.nombre = obtenerNombreMenorValor(actual.derecho);
            actual.puesto = obtenerPuestoMenorValor(actual.derecho);

            // Eliminar el sucesor inorden
            actual.derecho = eliminarRecursivo(actual.derecho, actual.idEmpleado);
        }
        return actual;
    }

    private int obtenerMenorValor(NodoEmpleado nodo) {
        int menorValor = nodo.idEmpleado;
        while (nodo.izquierdo != null) {
            menorValor = nodo.izquierdo.idEmpleado;
            nodo = nodo.izquierdo;
        }
        return menorValor;
    }

    private String obtenerNombreMenorValor(NodoEmpleado nodo) {
        String nombre = nodo.nombre;
        while (nodo.izquierdo != null) {
            nombre = nodo.izquierdo.nombre;
            nodo = nodo.izquierdo;
        }
        return nombre;
    }

    private String obtenerPuestoMenorValor(NodoEmpleado nodo) {
        String puesto = nodo.puesto;
        while (nodo.izquierdo != null) {
            puesto = nodo.izquierdo.puesto;
            nodo = nodo.izquierdo;
        }
        return puesto;
    }

    public void recorridoEnOrden() {
        recorridoEnOrdenRecursivo(raiz);
    }

    private void recorridoEnOrdenRecursivo(NodoEmpleado nodo) {
        if (nodo != null) {
            recorridoEnOrdenRecursivo(nodo.izquierdo);
            System.out.println("  " + Colores.AMARILLO + "• " + Colores.RESET + nodo);
            recorridoEnOrdenRecursivo(nodo.derecho);
        }
    }
}

// Gestión de empleados por departamento
class GestionEmpleados {
    private Map<String, ArbolBinarioBusqueda> arbolesPorDepartamento = new HashMap<>();

    public void agregarEmpleado(String departamento, int idEmpleado, String nombre, String puesto) {
        ArbolBinarioBusqueda arbol = arbolesPorDepartamento.computeIfAbsent(departamento, k -> new ArbolBinarioBusqueda());
        arbol.insertar(idEmpleado, nombre, puesto);
        Main.limpiarPantalla();
        System.out.println(Colores.VERDE + "\n✅ Empleado agregado exitosamente." + Colores.RESET);
    }

    public NodoEmpleado buscarEmpleado(String departamento, int idEmpleado) {
        ArbolBinarioBusqueda arbol = arbolesPorDepartamento.get(departamento);
        return (arbol != null) ? arbol.buscar(idEmpleado) : null;
    }

    public void mostrarEmpleadosDepartamento(String departamento) {
        ArbolBinarioBusqueda arbol = arbolesPorDepartamento.get(departamento);
        if (arbol != null) {
            System.out.println(Colores.AZUL + "\n=== Organigrama del Departamento: " + Colores.AMARILLO +
                    departamento + Colores.AZUL + " ===" + Colores.RESET);
            arbol.recorridoEnOrden();
            System.out.println(Colores.AZUL + "==========================================" + Colores.RESET);
        } else {
            System.out.println(Colores.ROJO + "\nEl departamento '" + departamento +
                    "' no tiene empleados registrados." + Colores.RESET);
        }
    }

    public boolean eliminarEmpleado(String departamento, int idEmpleado) {
        ArbolBinarioBusqueda arbol = arbolesPorDepartamento.get(departamento);
        if (arbol != null) {
            NodoEmpleado empleadoExistente = arbol.buscar(idEmpleado);
            if (empleadoExistente != null) {
                arbol.eliminar(idEmpleado);
                return true;
            }
        }
        return false;
    }

    public void mostrarTodosLosEmpleados() {
        if (arbolesPorDepartamento.isEmpty()) {
            System.out.println(Colores.ROJO + "\nNo hay ningún empleado registrado en el restaurante." + Colores.RESET);
            return;
        }
        System.out.println(Colores.CIAN + "\n--- MOSTRANDO TODOS LOS DEPARTAMENTOS ---" + Colores.RESET);
        for (String departamento : arbolesPorDepartamento.keySet()) {
            mostrarEmpleadosDepartamento(departamento);
            System.out.println();
        }
    }

    // Método de gestión de empleados con interfaz visual
    public void gestionarEmpleados() {
        Scanner lector = new Scanner(System.in);
        int opcion;

        do {
            System.out.println(Colores.AZUL + "\n⭐🌟     GESTIÓN DE EMPLEADOS (ÁRBOLES)    🌟⭐" + Colores.RESET);
            System.out.println(Colores.AZUL + "───────────────────────" + Colores.ROJO + "୨ৎ" + Colores.AZUL
                    + "──────────────────────" + Colores.RESET);
            System.out.println(Colores.BLANCO + "╔═════════════════════════════════════════════╗" + Colores.RESET);
            System.out.println(Colores.BLANCO + "║ 1. Agregar empleado ➕                      ║");
            System.out.println("║ 2. Eliminar empleado ❌                     ║");
            System.out.println("║ 3. Buscar empleado por ID 🔍                ║");
            System.out.println("║ 4. Mostrar empleados de un departamento 👥  ║");
            System.out.println("║ 5. Mostrar todos los empleados 📋           ║");
            System.out.println("║ 0. Volver al menú principal 🏠              ║" + Colores.RESET);
            System.out.println(Colores.BLANCO + "╚═════════════════════════════════════════════╝" + Colores.RESET);
            System.out.print(Colores.MORADO + "\nSeleccione una opción: " + Colores.RESET + Colores.BLANCO);

            try {
                opcion = lector.nextInt();
                lector.nextLine(); // Limpiar buffer
            } catch (java.util.InputMismatchException e) {
                Main.limpiarPantalla();
                System.out.println(Colores.ROJO + "\nError: Por favor, ingrese un número válido." + Colores.RESET);
                lector.nextLine();
                opcion = -1;
                continue;
            }

            switch (opcion) {
                case 1:
                    Main.limpiarPantalla();
                    System.out.print(Colores.MORADO + "Departamento: " + Colores.RESET + Colores.BLANCO);
                    String depto = lector.nextLine();
                    System.out.print(Colores.MORADO + "ID del empleado: " + Colores.RESET + Colores.BLANCO);
                    int id = lector.nextInt();
                    lector.nextLine();
                    System.out.print(Colores.MORADO + "Nombre del empleado: " + Colores.RESET + Colores.BLANCO);
                    String nombre = lector.nextLine();
                    System.out.print(Colores.MORADO + "Puesto del empleado: " + Colores.RESET + Colores.BLANCO);
                    String puesto = lector.nextLine();
                    agregarEmpleado(depto, id, nombre, puesto);
                    break;
                case 2:
                    Main.limpiarPantalla();
                    System.out.print(Colores.MORADO + "Departamento del empleado a eliminar: " + Colores.RESET + Colores.BLANCO);
                    String deptoEliminar = lector.nextLine();
                    System.out.print(Colores.MORADO + "ID del empleado a eliminar: " + Colores.RESET + Colores.BLANCO);
                    int idEliminar = lector.nextInt();
                    lector.nextLine();
                    boolean eliminado = eliminarEmpleado(deptoEliminar, idEliminar);
                    Main.limpiarPantalla();
                    if (eliminado) {
                        System.out.println(Colores.VERDE + "\n✅ Empleado eliminado exitosamente." + Colores.RESET);
                    } else {
                        System.out.println(Colores.ROJO + "\n❌ Empleado no encontrado en ese departamento." + Colores.RESET);
                    }
                    break;
                case 3:
                    Main.limpiarPantalla();
                    System.out.print(Colores.MORADO + "Buscar en departamento: " + Colores.RESET + Colores.BLANCO);
                    String deptoBusca = lector.nextLine();
                    System.out.print(Colores.MORADO + "ID a buscar: " + Colores.RESET + Colores.BLANCO);
                    int idBusca = lector.nextInt();
                    lector.nextLine();
                    NodoEmpleado encontrado = buscarEmpleado(deptoBusca, idBusca);
                    Main.limpiarPantalla();
                    if (encontrado != null) {
                        System.out.println(Colores.VERDE + "\n✅ Empleado encontrado:" + Colores.RESET);
                        System.out.println("  " + Colores.AMARILLO + "• " + Colores.RESET + encontrado);
                    } else {
                        System.out.println(Colores.ROJO + "\n❌ Empleado no encontrado en ese departamento." + Colores.RESET);
                    }
                    break;
                case 4:
                    Main.limpiarPantalla();
                    System.out.print(Colores.MORADO + "Departamento a mostrar: " + Colores.RESET + Colores.BLANCO);
                    String deptoMuestra = lector.nextLine();
                    Main.limpiarPantalla();
                    mostrarEmpleadosDepartamento(deptoMuestra);
                    break;
                case 5:
                    Main.limpiarPantalla();
                    mostrarTodosLosEmpleados();
                    break;
                case 0:
                    System.out.println(Colores.VERDE + "\nRegresando al menú principal..." + Colores.RESET);
                    Main.limpiarPantalla();
                    break;
                default:
                    Main.limpiarPantalla();
                    System.out.println(Colores.ROJO + "\nOpción no válida. Intente de nuevo." + Colores.RESET);
                    break;
            }
        } while (opcion != 0);
    }
}

// Clase para estadísticas y optimización usando recursividad y divide y vencerás
class RecursividadDivideVenceras {

    // Estructura para representar una tarea del restaurante
    static class Tarea {
        String nombre;
        int tiempoEstimado; // en minutos
        int prioridad; // 1 = alta, 2 = media, 3 = baja
        String departamento;
        LocalDate fechaEntrega; // Added for sorting

        public Tarea(String nombre, int tiempoEstimado, int prioridad, String departamento) {
            this.nombre = nombre;
            this.tiempoEstimado = tiempoEstimado;
            this.prioridad = prioridad;
            this.departamento = departamento;
            this.fechaEntrega = null; // Initialize to null
        }

        // Overloaded constructor for tasks with delivery date
        public Tarea(String nombre, int tiempoEstimado, int prioridad, String departamento, LocalDate fechaEntrega) {
            this.nombre = nombre;
            this.tiempoEstimado = tiempoEstimado;
            this.prioridad = prioridad;
            this.departamento = departamento;
            this.fechaEntrega = fechaEntrega;
        }

        @Override
        public String toString() {
            String colorPrioridad = (prioridad == 1) ? Colores.ROJO : (prioridad == 2) ? Colores.AMARILLO : Colores.VERDE;
            String fechaStr = (fechaEntrega == null) ? "N/A" : fechaEntrega.format(DateTimeFormatter.ISO_LOCAL_DATE);
            return String.format(Colores.CIAN + "%-20s | " + Colores.BLANCO + "%3d min | " +
                    colorPrioridad + "Prioridad %d" + Colores.RESET + Colores.CIAN + " | " +
                    Colores.BLANCO + "%s" + Colores.RESET + Colores.CIAN + " | " + Colores.BLANCO + "%s" + Colores.RESET,
                    nombre, tiempoEstimado, prioridad, departamento, fechaStr);
        }
    }

    private List<Tarea> tareas;

    public RecursividadDivideVenceras() {
        tareas = new ArrayList<>();
    }

    // Método para establecer las tareas desde el gestor de tareas
    public void setTareas(List<Tarea> tareasExternas) {
        this.tareas = new ArrayList<>(tareasExternas);
    }

    // ===== MÉTODOS RECURSIVOS =====

    // Calcular tiempo total estimado usando recursividad
    public int calcularTiempoTotalRecursivo(List<Tarea> listaTareas, int indice) {
        // Caso base: si llegamos al final de la lista
        if (indice >= listaTareas.size()) {
            return 0;
        }

        // Caso recursivo: tiempo actual + tiempo del resto de la lista
        return listaTareas.get(indice).tiempoEstimado +
               calcularTiempoTotalRecursivo(listaTareas, indice + 1);
    }

    // Contar tareas por prioridad usando recursividad
    public int contarTareasPorPrioridadRecursivo(List<Tarea> listaTareas, int prioridad, int indice) {
        // Caso base
        if (indice >= listaTareas.size()) {
            return 0;
        }

        // Caso recursivo
        int count = (listaTareas.get(indice).prioridad == prioridad) ? 1 : 0;
        return count + contarTareasPorPrioridadRecursivo(listaTareas, prioridad, indice + 1);
    }

    // Buscar tarea con mayor tiempo usando recursividad
    public Tarea encontrarTareaMayorTiempoRecursivo(List<Tarea> listaTareas, int indice, Tarea maxActual) {
        // Caso base
        if (indice >= listaTareas.size()) {
            return maxActual;
        }

        // Determinar el nuevo máximo
        Tarea nuevoMax = (maxActual == null || listaTareas.get(indice).tiempoEstimado > maxActual.tiempoEstimado)
                         ? listaTareas.get(indice) : maxActual;

        // Caso recursivo
        return encontrarTareaMayorTiempoRecursivo(listaTareas, indice + 1, nuevoMax);
    }

    // ===== ALGORITMOS DIVIDE Y VENCERÁS =====

    // Ordenamiento merge sort para tareas por tiempo estimado
    public List<Tarea> ordenarPorTiempoMergeSort(List<Tarea> listaTareas) {
        if (listaTareas.size() <= 1) {
            return new ArrayList<>(listaTareas);
        }

        // Dividir
        int medio = listaTareas.size() / 2;
        List<Tarea> izquierda = new ArrayList<>(listaTareas.subList(0, medio));
        List<Tarea> derecha = new ArrayList<>(listaTareas.subList(medio, listaTareas.size()));

        // Vencer (recursión)
        izquierda = ordenarPorTiempoMergeSort(izquierda);
        derecha = ordenarPorTiempoMergeSort(derecha);

        // Combinar
        return combinarListas(izquierda, derecha);
    }

    // Método auxiliar para combinar listas ordenadas
    private List<Tarea> combinarListas(List<Tarea> izq, List<Tarea> der) {
        List<Tarea> resultado = new ArrayList<>();
        int i = 0, j = 0;

        while (i < izq.size() && j < der.size()) {
            if (izq.get(i).tiempoEstimado <= der.get(j).tiempoEstimado) {
                resultado.add(izq.get(i++));
            } else {
                resultado.add(der.get(j++));
            }
        }

        // Agregar elementos restantes
        while (i < izq.size()) resultado.add(izq.get(i++));
        while (j < der.size()) resultado.add(der.get(j++));

        return resultado;
    }

    // Algoritmo para optimizar distribución de tareas usando divide y vencerás
    public Map<String, List<Tarea>> optimizarDistribucionTareas() {
        // Dividir tareas por departamento
        Map<String, List<Tarea>> tareasPorDepartamento = new HashMap<>();

        for (Tarea tarea : tareas) {
            tareasPorDepartamento.computeIfAbsent(tarea.departamento, k -> new ArrayList<>()).add(tarea);
        }

        // Vencer: optimizar cada departamento por separado
        for (String departamento : tareasPorDepartamento.keySet()) {
            List<Tarea> tareasDepto = tareasPorDepartamento.get(departamento);
            // Ordenar por prioridad y luego por tiempo
            tareasDepto.sort((t1, t2) -> {
                if (t1.prioridad != t2.prioridad) {
                    return Integer.compare(t1.prioridad, t2.prioridad);
                }
                return Integer.compare(t1.tiempoEstimado, t2.tiempoEstimado);
            });
            tareasPorDepartamento.put(departamento, tareasDepto);
        }

        return tareasPorDepartamento;
    }

    // Búsqueda binaria recursiva para encontrar tarea por tiempo específico
    public Tarea busquedaBinariaRecursiva(List<Tarea> tareasOrdenadas, int tiempoObjetivo, int inicio, int fin) {
        // Caso base: elemento no encontrado
        if (inicio > fin) {
            return null;
        }

        int medio = inicio + (fin - inicio) / 2;
        int tiempoMedio = tareasOrdenadas.get(medio).tiempoEstimado;

        // Caso base: elemento encontrado
        if (tiempoMedio == tiempoObjetivo) {
            return tareasOrdenadas.get(medio);
        }

        // Casos recursivos
        if (tiempoObjetivo < tiempoMedio) {
            return busquedaBinariaRecursiva(tareasOrdenadas, tiempoObjetivo, inicio, medio - 1);
        } else {
            return busquedaBinariaRecursiva(tareasOrdenadas, tiempoObjetivo, medio + 1, fin);
        }
    }

    // ===== INTERFAZ DE USUARIO =====

    public void gestionarRecursividadYDivideVenceras() {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        // Obtener tareas del gestor principal
        this.tareas = Main.getGestorTareas().listar();

        do {
            System.out.println(Colores.AZUL + "\n⭐🌟 RECURSIVIDAD Y DIVIDE Y VENCERÁS 🌟⭐" + Colores.RESET);
            System.out.println(Colores.AZUL + "──────────────────────" + Colores.ROJO + "୨ৎ" + Colores.AZUL
                    + "─────────────────────" + Colores.RESET);
            System.out.println(Colores.BLANCO + "╔═══════════════════════════════════════════╗" + Colores.RESET);
            System.out.println(Colores.BLANCO + "║ 1. Calcular estadísticas recursivas 🔢    ║");
            System.out.println("║ 2. Ordenar tareas (Más Corta) 📊          ║");
            System.out.println("║ 3. Optimizar distribución de tareas 🎯    ║");
            System.out.println("║ 4. Búsqueda binaria por tiempo ⏱️          ║");
            System.out.println("║ 0. Volver al menú principal 🏠            ║" + Colores.RESET);
            System.out.println(Colores.BLANCO + "╚═══════════════════════════════════════════╝" + Colores.RESET);
            System.out.print(Colores.MORADO + "\nSeleccione una opción: " + Colores.RESET + Colores.BLANCO);

            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    Main.limpiarPantalla();
                    mostrarEstadisticasRecursivas();
                    break;
                case 2:
                    Main.limpiarPantalla();
                    mostrarTareasOrdenadas();
                    break;
                case 3:
                    Main.limpiarPantalla();
                    mostrarDistribucionOptimizada();
                    break;
                case 4:
                    realizarBusquedaBinaria(scanner);
                    break;
                case 0:
                    System.out.println(Colores.VERDE + "\nRegresando al menú principal..." + Colores.RESET);
                    Main.limpiarPantalla();
                    break;
                default:
                    Main.limpiarPantalla();
                    System.out.println(Colores.ROJO + "\nOpción no válida. Intente de nuevo." + Colores.RESET);
            }
        } while (opcion != 0);
    }

    

    private void mostrarEstadisticasRecursivas() {
        Main.limpiarPantalla();
        System.out.println(Colores.CIAN + "\n=== ESTADÍSTICAS CALCULADAS RECURSIVAMENTE ===" + Colores.RESET);

        // Tiempo total
        int tiempoTotal = calcularTiempoTotalRecursivo(tareas, 0);
        System.out.println(Colores.AMARILLO + "🕐 Tiempo total estimado: " + Colores.BLANCO +
                tiempoTotal + " minutos (" + String.format("%.2f", tiempoTotal/60.0) + " horas)" + Colores.RESET);

        // Conteo por prioridad
        for (int p = 1; p <= 3; p++) {
            int count = contarTareasPorPrioridadRecursivo(tareas, p, 0);
            String nombrePrioridad = (p == 1) ? "Alta" : (p == 2) ? "Media" : "Baja";
            String color = (p == 1) ? Colores.ROJO : (p == 2) ? Colores.AMARILLO : Colores.VERDE;
            System.out.println(color + "📌 Tareas de prioridad " + nombrePrioridad + ": " +
                    Colores.BLANCO + count + Colores.RESET);
        }

        // Tarea de mayor tiempo
        Tarea tareaMayor = encontrarTareaMayorTiempoRecursivo(tareas, 0, null);
        if (tareaMayor != null) {
            System.out.println(Colores.MORADO + "🏆 Tarea de mayor duración: " + Colores.BLANCO +
                    tareaMayor.nombre + " (" + tareaMayor.tiempoEstimado + " min)" + Colores.RESET);
        }
        System.out.println();
    }

    private void mostrarTareasOrdenadas() {
        Main.limpiarPantalla();
        System.out.println(Colores.CIAN + "\n=== TAREAS ORDENADAS POR TIEMPO (MERGE SORT) ===" + Colores.RESET);
        List<Tarea> tareasOrdenadas = ordenarPorTiempoMergeSort(tareas);

        for (Tarea tarea : tareasOrdenadas) {
            System.out.println("  " + Colores.AMARILLO + "• " + Colores.RESET + tarea);
        }
        System.out.println();
    }

    private void mostrarDistribucionOptimizada() {
        Main.limpiarPantalla();
        System.out.println(Colores.CIAN + "\n=== DISTRIBUCIÓN OPTIMIZADA DE TAREAS ===" + Colores.RESET);
        Map<String, List<Tarea>> distribucion = optimizarDistribucionTareas();

        for (String departamento : distribucion.keySet()) {
            System.out.println(Colores.AZUL + "\n--- " + departamento + " ---" + Colores.RESET);
            List<Tarea> tareasDepto = distribucion.get(departamento);
            int tiempoTotal = calcularTiempoTotalRecursivo(tareasDepto, 0);

            for (Tarea tarea : tareasDepto) {
                System.out.println("  " + Colores.AMARILLO + "• " + Colores.RESET + tarea);
            }
            System.out.println(Colores.VERDE + "  ⏱️ Tiempo total del departamento: " +
                    tiempoTotal + " minutos" + Colores.RESET);
        }
        System.out.println();
    }

    private void realizarBusquedaBinaria(Scanner scanner) {
        Main.limpiarPantalla();
        System.out.print(Colores.MORADO + "Ingrese el tiempo en minutos a buscar: " +
                Colores.RESET + Colores.BLANCO);
        int tiempoObjetivo = scanner.nextInt();

        // Primero ordenamos las tareas
        List<Tarea> tareasOrdenadas = ordenarPorTiempoMergeSort(tareas);

        // Realizamos búsqueda binaria
        Tarea encontrada = busquedaBinariaRecursiva(tareasOrdenadas, tiempoObjetivo, 0, tareasOrdenadas.size() - 1);

        Main.limpiarPantalla();
        if (encontrada != null) {
            System.out.println(Colores.VERDE + "\n✅ Tarea encontrada:" + Colores.RESET);
            System.out.println("  " + Colores.AMARILLO + "• " + Colores.RESET + encontrada);
        } else {
            System.out.println(Colores.ROJO + "\n❌ No se encontró ninguna tarea con " +
                    tiempoObjetivo + " minutos." + Colores.RESET);
        }
        System.out.println();
    }

    
}

// Clase para la gestión de tareas usando HashMap
class GestorTareas {
    private Map<Integer, RecursividadDivideVenceras.Tarea> tareasMap = new HashMap<>();
    private static final DateTimeFormatter FMT = DateTimeFormatter.ISO_LOCAL_DATE;
    private int nextId = 1; // Para asignar IDs únicos

    public GestorTareas() {
        inicializarTareas();
    }

    // Inicializar tareas predefinidas del restaurante
    private void inicializarTareas() {
        LocalDate hoy = LocalDate.now();
        agregarTareaInterna("Preparar mise en place", 45, 1, "Cocina", hoy);
        agregarTareaInterna("Limpiar mesas", 15, 2, "Sala", hoy);
        agregarTareaInterna("Inventario ingredientes", 30, 1, "Cocina", hoy.plusDays(2));
        agregarTareaInterna("Preparar pan", 60, 2, "Cocina", hoy.plusDays(1));
        agregarTareaInterna("Actualizar carta vinos", 25, 3, "Sala", hoy.plusDays(7));
        agregarTareaInterna("Revisar reservaciones", 20, 1, "Administración", hoy);
        agregarTareaInterna("Capacitar meseros", 90, 3, "Administración", hoy.plusDays(14));
        agregarTareaInterna("Preparar postres", 40, 2, "Cocina", hoy.plusDays(1));
    }

    private void agregarTareaInterna(String nombre, int tiempo, int prioridad, String departamento, LocalDate fechaEntrega) {
        RecursividadDivideVenceras.Tarea tarea = new RecursividadDivideVenceras.Tarea(nombre, tiempo, prioridad, departamento, fechaEntrega);
        tareasMap.put(nextId++, tarea);
    }

    public boolean agregar(RecursividadDivideVenceras.Tarea tarea) {
        tareasMap.put(nextId++, tarea);
        return true;
    }

    public boolean eliminarPorId(int id) {
        return tareasMap.remove(id) != null;
    }

    public List<RecursividadDivideVenceras.Tarea> listar() {
        return new ArrayList<>(tareasMap.values());
    }

    public List<RecursividadDivideVenceras.Tarea> ordenarPorPrioridadAltoABajo() {
        List<RecursividadDivideVenceras.Tarea> lista = listar();
        lista.sort((t1, t2) -> Integer.compare(t1.prioridad, t2.prioridad));
        return lista;
    }

    public List<RecursividadDivideVenceras.Tarea> ordenarPorFechaMasProxima() {
        List<RecursividadDivideVenceras.Tarea> lista = listar();
        lista.sort((t1, t2) -> {
            if (t1.fechaEntrega == null && t2.fechaEntrega == null) return 0;
            if (t1.fechaEntrega == null) return 1; // null dates go last
            if (t2.fechaEntrega == null) return -1; // null dates go last
            return t1.fechaEntrega.compareTo(t2.fechaEntrega);
        });
        return lista;
    }

    public RecursividadDivideVenceras.Tarea buscarPorId(int id) {
        return tareasMap.get(id);
    }

    public List<RecursividadDivideVenceras.Tarea> buscarPorNombre(String nombre) {
        List<RecursividadDivideVenceras.Tarea> resultado = new ArrayList<>();
        for (RecursividadDivideVenceras.Tarea t : tareasMap.values()) {
            if (t.nombre.equalsIgnoreCase(nombre)) {
                resultado.add(t);
            }
        }
        return resultado;
    }

    public void mostrarTodasLasTareas() {
        List<RecursividadDivideVenceras.Tarea> tareas = listar();
        if (tareas.isEmpty()) {
            System.out.println(Colores.ROJO + "No hay tareas registradas." + Colores.RESET);
            return;
        }
        System.out.println(Colores.CIAN + "\n=== LISTA DE TAREAS DEL RESTAURANTE ===" + Colores.RESET);
        for (RecursividadDivideVenceras.Tarea tarea : tareas) {
            System.out.println("  " + Colores.AMARILLO + "• " + Colores.RESET + tarea);
        }
        System.out.println();
    }
}

// Clase para manejo de grafos - Sistema de navegación del restaurante
class GrafoMesas {
    private static final int INF = 1_000_000_000;
    private final Map<String, Integer> idx = new HashMap<>();
    private final List<String> nombres = new ArrayList<>();
    private int[][] dist;
    private int[][] next;

    public GrafoMesas(List<String> vertices) {
        int n = vertices.size();
        for (int i = 0; i < n; i++) {
            idx.put(vertices.get(i), i);
            nombres.add(vertices.get(i));
        }
        dist = new int[n][n];
        next = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dist[i], INF);
            Arrays.fill(next[i], -1);
            dist[i][i] = 0;
            next[i][i] = i;
        }
    }

    public void agregarArista(String u, String v, int peso, boolean dirigido) {
        int i = idx.get(u), j = idx.get(v);
        dist[i][j] = Math.min(dist[i][j], peso);
        next[i][j] = j;
        if (!dirigido) {
            dist[j][i] = Math.min(dist[j][i], peso);
            next[j][i] = i;
        }
    }

    public void floydWarshall() {
        int n = dist.length;
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                if (dist[i][k] == INF) continue;
                for (int j = 0; j < n; j++) {
                    if (dist[k][j] == INF) continue;
                    int nuevo = dist[i][k] + dist[k][j];
                    if (nuevo < dist[i][j]) {
                        dist[i][j] = nuevo;
                        next[i][j] = next[i][k];
                    }
                }
            }
        }
    }

    public List<String> reconstruirCamino(String origen, String destino) {
        Integer io = idx.get(origen);
        Integer id = idx.get(destino);
        if (io == null || id == null || next[io][id] == -1) return List.of();
        List<String> camino = new ArrayList<>();
        int u = io;
        while (u != id) {
            camino.add(nombres.get(u));
            u = next[u][id];
            if (u == -1) return List.of();
        }
        camino.add(nombres.get(id));
        return camino;
    }

    public int costo(String origen, String destino) {
        Integer io = idx.get(origen);
        Integer id = idx.get(destino);
        if (io == null || id == null) return INF;
        return dist[io][id];
    }
    
    public void imprimirMatriz() {
        int n = dist.length;
        System.out.print("         ");
        for (String name : nombres) {
            System.out.printf("%-9s", name);
        }
        System.out.println("\n" + "-".repeat(n * 9 + 9));
        for (int i = 0; i < n; i++) {
            System.out.printf("%-8s|", nombres.get(i));
            for (int j = 0; j < n; j++) {
                System.out.printf("%-9s", dist[i][j] >= INF / 2 ? "∞" : String.valueOf(dist[i][j]));
            }
            System.out.println();
        }
    }

    public List<String> getUbicaciones() {
        return new ArrayList<>(nombres);
    }

    // Método para gestionar la navegación del restaurante
    public void gestionarNavegacion() {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println(Colores.AZUL + "\n⭐🌟   NAVEGACIÓN DEL RESTAURANTE (GRAFOS)   🌟⭐" + Colores.RESET);
            System.out.println(Colores.AZUL + "─────────────────────────" + Colores.ROJO + "୨ৎ" + Colores.AZUL + "────────────────────────" + Colores.RESET);
            System.out.println(Colores.BLANCO + "╔═════════════════════════════════════════════════╗" + Colores.RESET);
            System.out.println(Colores.BLANCO + "║ 1. Calcular la ruta más corta 🗺️                 ║");
            System.out.println("║ 2. Mostrar todas las ubicaciones 📍             ║");
            System.out.println("║ 3. Ver matriz completa de costos 📊             ║");
            System.out.println("║ 0. Volver al menú principal 🏠                  ║" + Colores.RESET);
            System.out.println(Colores.BLANCO + "╚═════════════════════════════════════════════════╝" + Colores.RESET);
            System.out.print(Colores.MORADO + "\nSeleccione una opción: " + Colores.RESET + Colores.BLANCO);

            try {
                opcion = scanner.nextInt();
                scanner.nextLine(); // consumir el salto de línea
            } catch (java.util.InputMismatchException e) {
                Main.limpiarPantalla();
                System.out.println(Colores.ROJO + "❌ Error: Por favor, ingrese un número válido." + Colores.RESET);
                scanner.nextLine();
                opcion = -1;
                continue;
            }

            switch (opcion) {
                case 1:
                    Main.limpiarPantalla();
                    System.out.print(Colores.MORADO + "📍 Punto de partida: " + Colores.RESET + Colores.BLANCO);
                    String origen = scanner.nextLine();
                    System.out.print(Colores.MORADO + "🏁 Punto de destino: " + Colores.RESET + Colores.BLANCO);
                    String destino = scanner.nextLine();

                    List<String> camino = reconstruirCamino(origen, destino);
                    int costo = costo(origen, destino);

                    Main.limpiarPantalla();
                    if (camino.isEmpty() || costo >= INF) {
                        System.out.println(Colores.ROJO + "\n❌ No se pudo encontrar una ruta. Verifique que los nombres sean correctos." + Colores.RESET);
                    } else {
                        System.out.println(Colores.VERDE + "\n✅ Ruta Óptima Encontrada:" + Colores.RESET);
                        System.out.println(Colores.CIAN + "   Recorrido: " + Colores.BLANCO + String.join(" -> ", camino) + Colores.RESET);
                        System.out.println(Colores.AMARILLO + "   Costo total: " + Colores.BLANCO + costo + " pasos." + Colores.RESET);
                    }
                    break;
                case 2:
                    Main.limpiarPantalla();
                    System.out.println(Colores.CIAN + "\n--- Ubicaciones en 'Dolce Alba' ---" + Colores.RESET);
                    for (String loc : getUbicaciones()) {
                        System.out.println(Colores.AMARILLO + "• " + Colores.BLANCO + loc + Colores.RESET);
                    }
                    break;
                case 3:
                    Main.limpiarPantalla();
                    System.out.println(Colores.CIAN + "\n--- Matriz de Costos Mínimos ---" + Colores.RESET);
                    imprimirMatriz();
                    break;
                case 0:
                    System.out.println(Colores.VERDE + "\nRegresando al menú principal..." + Colores.RESET);
                    Main.limpiarPantalla();
                    break;
                default:
                    Main.limpiarPantalla();
                    System.out.println(Colores.ROJO + "\nOpción no válida. Intente de nuevo." + Colores.RESET);
                    break;
            }
        } while (opcion != 0);
    }
}

// Clase principal del programa
public class Main {
    private static Menu menu = new Menu();
    private static Scanner scanner = new Scanner(System.in);
    private static GestionEmpleados gestionEmpleados = new GestionEmpleados();
    private static RecursividadDivideVenceras recursividad = new RecursividadDivideVenceras();
    private static GestorTareas gestorTareas = new GestorTareas(); // Instance for task management
    private static GrafoMesas grafoMesas; // Instance for restaurant navigation

    // Método para acceder al gestor de tareas desde otras clases
    public static GestorTareas getGestorTareas() {
        return gestorTareas;
    }

    public static void main(String[] args) {
        menu = new Menu();
        inicializarDatosEmpleados();
        inicializarGrafoMesas();
        limpiarPantalla();
        mostarInterfaz();
    }

    // Método para inicializar el grafo de mesas del restaurante
    private static void inicializarGrafoMesas() {
        List<String> ubicaciones = List.of(
            "Entrada", "Mesa1", "Mesa2", "Mesa3", "Mesa4", "Barra", "Cocina", "Baño"
        );
        grafoMesas = new GrafoMesas(ubicaciones);

        // Definir las conexiones directas y sus costos
        grafoMesas.agregarArista("Entrada", "Mesa1", 5, false);
        grafoMesas.agregarArista("Entrada", "Barra", 8, false);
        grafoMesas.agregarArista("Mesa1", "Mesa2", 4, false);
        grafoMesas.agregarArista("Mesa2", "Barra", 3, false);
        grafoMesas.agregarArista("Barra", "Mesa3", 4, false);
        grafoMesas.agregarArista("Barra", "Cocina", 7, false);
        grafoMesas.agregarArista("Mesa3", "Mesa4", 5, false);
        grafoMesas.agregarArista("Mesa4", "Cocina", 3, false);
        grafoMesas.agregarArista("Barra", "Baño", 6, false);
        
        // Calcular todas las rutas óptimas
        grafoMesas.floydWarshall();
    }

    // Método para inicializar datos de empleados
    private static void inicializarDatosEmpleados() {
        gestionEmpleados.agregarEmpleado("Cocina", 102, "Marco Rossi", "Chef de Partie");
        gestionEmpleados.agregarEmpleado("Sala", 201, "Sofia Colombo", "Mesera");
        gestionEmpleados.agregarEmpleado("Cocina", 101, "Giovanni Bruno", "Sous Chef");
        gestionEmpleados.agregarEmpleado("Cocina", 103, "Lucia Ferrari", "Chef Ejecutivo");
        gestionEmpleados.agregarEmpleado("Sala", 202, "Alessandro Conti", "Sommelier");
        gestionEmpleados.agregarEmpleado("Administración", 301, "Elena Bianchi", "Gerente General");
    }

    // Método para mostrar la interfaz principal del programa
    private static void mostarInterfaz() {
        int opcion;

        do {
            System.out.println(Colores.AZUL + "┌───────────────────────────────────────────┐" + Colores.RESET);
            System.out.println(Colores.AZUL + "│            ⭐🌟 DOLCE ALBA 🌟⭐           │" + Colores.RESET);
            System.out.println(Colores.AZUL + "└───────────────────────────────────────────┘" + Colores.RESET);
            System.out.println(Colores.BLANCO + "╔═══════════════════════════════════════════╗" + Colores.RESET);
            System.out.println(Colores.BLANCO + "║ 1. Gestión de Menú        📖 (Listas)     ║");
            System.out.println("║ 2. Gestión de Clientes    🙋 (Colas)      ║");
            System.out.println("║ 3. Gestion de Inventario  📝 (Pilas)      ║");
            System.out.println("║ 4. Gestion de Empleados   💼 (Arboles)    ║");
            System.out.println("║ 5. Recursividad y D&V     🔄 (Algoritmos) ║");
            System.out.println("║ 6. Gestión de Tareas      📋 (HashMap)    ║");
            System.out.println("║ 7. Navegación Restaurante 🗺️  (Grafos)     ║");
            System.out.println("║ 0. Salir 🚪                               ║" + Colores.RESET);
            System.out.println(Colores.BLANCO + "╚═══════════════════════════════════════════╝" + Colores.RESET);
            System.out.print(Colores.MORADO + "\nSeleccione una opción: " + Colores.RESET + Colores.BLANCO);

            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    limpiarPantalla();
                    gestionarMenu();
                    break;
                case 2:
                    limpiarPantalla();
                    Colas.gestionarClientes();
                    break;
                case 3:
                    limpiarPantalla();
                    System.out.print(
                            Colores.MORADO + "Inserta la capacidad del inventario (número de objetos almacenables): "
                                    + Colores.RESET + Colores.BLANCO);
                    int capacity = scanner.nextInt();
                    Pila1 pila = new Pila1(capacity);
                    limpiarPantalla();
                    pila.gestionarInventario();
                    break;
                case 4:
                    limpiarPantalla();
                    gestionEmpleados.gestionarEmpleados();
                    break;
                case 5:
                    limpiarPantalla();
                    recursividad.gestionarRecursividadYDivideVenceras();
                    break;
                case 6: // Task Management
                    limpiarPantalla();
                    gestionarTareas();
                    break;
                case 7: // Graph Navigation
                    limpiarPantalla();
                    grafoMesas.gestionarNavegacion();
                    break;
                case 0:
                    System.out.println(Colores.VERDE + "\n¡Gracias por usar el sistema de Dolce Alba!" + Colores.RESET);
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 0);
    }

    // Método para limpiar la pantalla de la consola
    public static void limpiarPantalla() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // Método para gestionar el menú del restaurante
    private static void gestionarMenu() {
        int opcion;

        do {
            System.out.println(Colores.AZUL + "\n⭐🌟    GESTIÓN DE MENÚ (LISTAS)   🌟⭐" + Colores.RESET);
            System.out.println(Colores.AZUL + "───────────────────" + Colores.ROJO + "୨ৎ" + Colores.AZUL
                    + "──────────────────" + Colores.RESET);
            System.out.println(Colores.BLANCO + "╔═════════════════════════════════════╗" + Colores.RESET);
            System.out.println(Colores.BLANCO + "║ 1. Mostrar menú completo 📋         ║");
            System.out.println("║ 2. Mostrar por categoría 📚         ║");
            System.out.println("║ 3. Buscar platillo 🔍               ║");
            System.out.println("║ 4. Agregar platillo ➕              ║");
            System.out.println("║ 5. Eliminar platillo 🗑️              ║");
            System.out.println("║ 0. Volver al menú principal 🚪      ║" + Colores.RESET);
            System.out.println(Colores.BLANCO + "╚═════════════════════════════════════╝" + Colores.RESET);
            System.out.print(Colores.MORADO + "\nSeleccione una opción: " + Colores.RESET + Colores.BLANCO);

            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    limpiarPantalla();
                    menu.mostrarMenu();
                    break;
                case 2:
                    limpiarPantalla();
                    System.out.print(Colores.MORADO + "Ingrese la categoría (Antipasti/Pasta/Secondi/Dolci): "
                            + Colores.RESET + Colores.BLANCO);
                    String categoria = scanner.nextLine();
                    menu.mostrarPorCategoria(categoria);
                    break;
                case 3:
                    limpiarPantalla();
                    System.out.print(Colores.MORADO + "Ingrese el nombre del platillo a buscar: " + Colores.RESET
                            + Colores.BLANCO);
                    String nombre = scanner.nextLine();
                    Platillo platillo = menu.buscar(nombre);
                    if (platillo != null) {
                        System.out.println("Platillo encontrado: " + platillo);
                    } else {
                        System.out.println("Platillo no encontrado.");
                    }
                    break;
                case 4:
                    limpiarPantalla();
                    menu.agregarPlatillo();
                    break;
                case 5:
                    limpiarPantalla();
                    menu.mostrarMenu();
                    System.out.print("\nIngrese el nombre del platillo a eliminar: ");
                    String nombreEliminar = scanner.nextLine();
                    if (menu.eliminar(nombreEliminar)) {
                        System.out.println("Platillo eliminado exitosamente.");
                    } else {
                        System.out.println("Platillo no encontrado.");
                    }
                    break;
                case 0:
                    limpiarPantalla();
                    break;
                default:
            }
        } while (opcion != 0);
    }

    // Helper method to print a list of tasks
    private static void imprimirListaTareas(List<RecursividadDivideVenceras.Tarea> ls) {
        if (ls.isEmpty()) {
            System.out.println(Colores.AMARILLO + "No hay tareas." + Colores.RESET);
            return;
        }
        System.out.println(Colores.CIAN + "\n— Tareas —" + Colores.RESET);
        for (RecursividadDivideVenceras.Tarea t : ls) System.out.println("  • " + t);
    }

    // Method to manage tasks using HashMap
    private static void gestionarTareas() {
        int op;
        final DateTimeFormatter FMT = DateTimeFormatter.ISO_LOCAL_DATE; // Define formatter here

        do {
            System.out.println(Colores.AZUL + "\n⭐🌟   GESTIÓN DE TAREAS (HASHMAP)   🌟⭐" + Colores.RESET);
            System.out.println(Colores.AZUL + "─────────────────────" + Colores.ROJO + "୨ৎ" + Colores.AZUL + "────────────────────" + Colores.RESET);
            System.out.println(Colores.BLANCO + "╔══════════════════════════════════════════╗" + Colores.RESET);
            System.out.println(Colores.BLANCO + "║ 1. Agregar Tarea ➕                      ║");
            System.out.println("║ 2. Eliminar Tarea por ID 🗑️               ║");
            System.out.println("║ 3. Listar todas las Tareas 📋            ║");
            System.out.println("║ 4. Ordenar por Prioridad (alta→baja) 📊  ║");
            System.out.println("║ 5. Ordenar por Fecha (próxima 1º) 📅     ║");
            System.out.println("║ 6. Buscar por ID (O(1) HashMap) 🔍       ║");
            System.out.println("║ 7. Buscar por Nombre 🔎                  ║");
            System.out.println("║ 0. Volver al menú principal 🏠           ║" + Colores.RESET);
            System.out.println(Colores.BLANCO + "╚══════════════════════════════════════════╝" + Colores.RESET);
            System.out.print(Colores.MORADO + "\nSeleccione una opción: " + Colores.RESET + Colores.BLANCO);

            try {
                op = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println(Colores.ROJO + "Error: ingrese un número válido." + Colores.RESET);
                op = -1;
            }

            switch (op) {
                case 1: { // Agregar
                    try {
                        Main.limpiarPantalla();
                        System.out.print(Colores.MORADO + "Nombre: " + Colores.RESET + Colores.BLANCO);
                        String nombre = scanner.nextLine();

                        System.out.print(Colores.MORADO + "Tiempo estimado (minutos): " + Colores.RESET + Colores.BLANCO);
                        int tiempo = Integer.parseInt(scanner.nextLine().trim());

                        System.out.print(Colores.MORADO + "Prioridad (1=Alta,2=Media,3=Baja): " + Colores.RESET + Colores.BLANCO);
                        int prioridad = Integer.parseInt(scanner.nextLine().trim());

                        System.out.print(Colores.MORADO + "Departamento: " + Colores.RESET + Colores.BLANCO);
                        String departamento = scanner.nextLine();

                        System.out.print(Colores.MORADO + "Fecha entrega (YYYY-MM-DD, opcional deja vacío): " + Colores.RESET + Colores.BLANCO);
                        String f = scanner.nextLine().trim();
                        LocalDate fecha = f.isEmpty() ? null : LocalDate.parse(f, FMT);

                        RecursividadDivideVenceras.Tarea newTarea = new RecursividadDivideVenceras.Tarea(nombre, tiempo, prioridad, departamento, fecha);

                        boolean ok = gestorTareas.agregar(newTarea); 
                        Main.limpiarPantalla();
                        System.out.println(ok ? Colores.VERDE + "✅ Tarea agregada exitosamente." + Colores.RESET
                                              : Colores.ROJO + "❌ Error al agregar la tarea." + Colores.RESET);
                    } catch (Exception e) {
                        Main.limpiarPantalla();
                        System.out.println(Colores.ROJO + "❌ Datos inválidos: " + e.getMessage() + Colores.RESET);
                    }
                    break;
                }
                case 2: { // Eliminar
                    System.out.print(Colores.MORADO + "ID a eliminar: " + Colores.RESET + Colores.BLANCO);
                    try {
                        int id = Integer.parseInt(scanner.nextLine().trim());
                        boolean ok = gestorTareas.eliminarPorId(id);
                        System.out.println(ok ? Colores.VERDE + "Eliminada." + Colores.RESET
                                              : Colores.ROJO + "No existe esa tarea." + Colores.RESET);
                    } catch (NumberFormatException e) {
                        System.out.println(Colores.ROJO + "ID inválido." + Colores.RESET);
                    }
                    break;
                }
                case 3: // Listar
                    Main.limpiarPantalla();
                    gestorTareas.mostrarTodasLasTareas();
                    break;

                case 4: // Ordenar por prioridad
                    Main.limpiarPantalla();
                    imprimirListaTareas(gestorTareas.ordenarPorPrioridadAltoABajo());
                    break;

                case 5: // Ordenar por fecha
                    Main.limpiarPantalla();
                    imprimirListaTareas(gestorTareas.ordenarPorFechaMasProxima());
                    break;

                case 6: { // Buscar por ID (HashMap O(1))
                    System.out.print(Colores.MORADO + "ID a buscar: " + Colores.RESET + Colores.BLANCO);
                    try {
                        int id = Integer.parseInt(scanner.nextLine().trim());
                        RecursividadDivideVenceras.Tarea t = gestorTareas.buscarPorId(id);
                        Main.limpiarPantalla();
                        System.out.println(t != null ? (Colores.VERDE + "✅ Tarea encontrada: " + Colores.RESET + "\n  • " + t)
                                                     : (Colores.ROJO + "❌ No existe una tarea con ese ID." + Colores.RESET));
                    } catch (NumberFormatException e) {
                        Main.limpiarPantalla();
                        System.out.println(Colores.ROJO + "❌ ID inválido." + Colores.RESET);
                    }
                    break;
                }

                case 7: { // Buscar por Nombre
                    System.out.print(Colores.MORADO + "Nombre exacto a buscar: " + Colores.RESET + Colores.BLANCO);
                    String nombre = scanner.nextLine();
                    List<RecursividadDivideVenceras.Tarea> res = gestorTareas.buscarPorNombre(nombre);
                    Main.limpiarPantalla();
                    if (res.isEmpty()) {
                        System.out.println(Colores.ROJO + "❌ No se encontraron tareas con ese nombre." + Colores.RESET);
                    } else {
                        System.out.println(Colores.VERDE + "✅ Tareas encontradas:" + Colores.RESET);
                        imprimirListaTareas(res);
                    }
                    break;
                }

                case 0:
                    System.out.println("Regresando...");
                    limpiarPantalla();
                    break;

                default:
                    System.out.println(Colores.ROJO + "Opción no válida." + Colores.RESET);
            }

        } while (op != 0);
    }
}