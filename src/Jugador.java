import java.util.Scanner;

/**
 * @author David Ayllón Martín
 * @author Luis Abenójar Andreu
 * @version 1.0
 * La clase jugador la usaremos para guardar todo lo que el jugador necesite: un nombre, su tablero, sus barcos y los
 * barcos que le quedan.
 */

public class Jugador {

    // Un atributo para el nombre del jugador
    private String nombre;

    // El atributo tablero que guarda un objeto Tablero para colocar tus barcos y efectuar tus disparos
    private Tablero tablero;

    // Un array de tetraminos, donde se guardarán todos los barcos del jugador
    private Tetramino[] barcos;

    // Un atributo para llevar la cuenta de cuántos barcos le quedan al jugador
    private int barcosRestantes;

    //Escáner para poder llamarlo desde algunos métodos que lo piden
    static Scanner read = new Scanner(System.in);

    /**
     * Constructor por defecto
     */
    public Jugador() {

        this.nombre = "";
        this.tablero = new Tablero(Juego.BOARD_SIZE);
        this.barcos = new Tetramino[Juego.SHIP_AMOUNT];
        for (int i = 0; i < barcos.length; i++) {
            this.barcos[i] = new Tetramino();
        }
        this.barcosRestantes = barcos.length;
    }

    /**
     * Constructor con array de barcos establecido en función de la constante SHIP_AMOUNT
     *
     * @param nombre  Nombre del jugador
     * @param tablero Tablero del jugador
     */
    public Jugador(String nombre, Tablero tablero) {
        this.nombre = nombre;
        this.tablero = tablero;
        this.barcos = new Tetramino[Juego.SHIP_AMOUNT];
        rellenarArrayBarcos();
        this.barcosRestantes = Juego.SHIP_AMOUNT;
    }

    /**
     * Constructor completo
     *
     * @param nombre  Nombre del jugador
     * @param tablero Tablero del jugador
     * @param barcos  Array de barcos del jugador
     */
    public Jugador(String nombre, Tablero tablero, Tetramino[] barcos) {
        this.nombre = nombre;
        this.tablero = tablero;
        this.barcos = barcos;
        this.barcosRestantes = barcos.length;
    }

    // Getters y Setters

    /**
     * Getter del atributo nombre
     *
     * @return Nombre del jugador
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Setter del atributo nombre
     *
     * @param nombre Nombre que le queremos asignar al jugador
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Getter del atributo tablero
     *
     * @return Tablero del jugador
     */
    public Tablero getTablero() {
        return tablero;
    }

    /**
     * Setter del atributo tablero
     *
     * @param tablero Tablero que queremos asignar al jugador
     */
    public void setTablero(Tablero tablero) {
        this.tablero = tablero;
    }

    /**
     * Getter del atributo barcos
     *
     * @return Array de barcos del jugador
     */
    public Tetramino[] getBarcos() {
        return barcos;
    }

    /**
     * Setter del atributo barcos
     *
     * @param barcos Array de barcos que queremos asignar al jugador
     */
    public void setBarcos(Tetramino[] barcos) {
        this.barcos = barcos;
    }

    /**
     * Getter del atributo barcosRestantes
     *
     * @return Número de barcos que le quedan al jugador
     */
    public int getBarcosRestantes() {
        return barcosRestantes;
    }

    /**
     * Setter del atributo barcosRestantes
     *
     * @param barcosRestantes Número de barcos restantes que queremos asignarle al jugador
     */
    public void setBarcosRestantes(int barcosRestantes) {
        this.barcosRestantes = barcosRestantes;
    }

    /**
     * Método que disparará en la posición que se escoja.
     *
     * @param jugador El jugador sobre el que se efectúa el disparo
     * @throws InterruptedException Excepción que se puede producir por el uso de Thread.sleep
     *                              El usuario introduce la información de fila y columna por teclado, se comprueba que es una posición correcta
     *                              comprobando que no se salga del tablero ni seleccione una posición ya atacada y se efectúa el disparo
     */
    public void disparar(Jugador jugador) throws InterruptedException {
        //Un booleano para repetir el bucle en caso de que se introduzcan datos erróneos
        boolean repetir;
        //Las variables fila y columna que se van a atacar
        int fila, columna;
        //Un bucle para recoger la información del ataque del usuario y asegurarnos de que es correcta
        do {
            repetir = false;
            System.out.println("Selecciona una fila:");
            fila = read.nextInt();
            // Si se elige una fila fuera de rango se pide de nuevo
            while (fila > Juego.BOARD_SIZE || fila < 1) {
                System.err.println("La fila se sale del tablero. Elige una fila entre 1 y " + Juego.BOARD_SIZE + ":");
                fila = read.nextInt();
            }
            System.out.println("Selecciona una columna:");
            columna = read.nextInt();
            // Si se elige una columna fuera de rango se pide de nuevo
            while (columna > Juego.BOARD_SIZE || columna < 1) {
                System.err.println("La columna se sale del tablero. Elige una fila entre 1 y " + Juego.BOARD_SIZE + ":");
                columna = read.nextInt();
            }
            //Si ya se ha disparado en esa casilla hay que repetir el bucle para elegir unas coordenadas que valgan
            Casilla casilla = tablero.getTableroDisparos()[fila - 1][columna - 1];
            if (casilla.isImpactado()) {
                System.err.println("Ya has disparado en esta casilla");
                repetir = true;
            }
        } while (repetir);

        //Una pequeña espera para darle tensión
        System.out.print("\n. ");
        Thread.sleep(750);
        System.out.print(". ");
        Thread.sleep(750);
        System.out.print(".");
        Thread.sleep(750);
        System.out.println("\n");

        //Cuando sale del bucle se han comprobado que las coordenadas son válidas y ya se puede efectuar el disparo
        jugador.recibirDisparo(fila, columna);
    }

    /**
     * Método para recibir un disparo en el tablero de barcos propio
     *
     * @param fila    fila sobre la que se efectúa el disparo
     * @param columna columna sobre la que se efectúa el disparo
     * @throws InterruptedException Excepción que se puede producir por el uso de Thread.sleep
     *                              Este método recibe la información de la fila y la columna donde se está realizando el disparo, comprueba qué
     *                              había en la casilla involucrada y actualiza su información.
     *                              En caso de que hubiera un barco, actualiza la información del barco reduciendo su vida y del número de barcos
     *                              restantes si el barco fue hundido.
     *                              También emite mensajes en pantalla de lo que sucede.
     */
    public void recibirDisparo(int fila, int columna) throws InterruptedException {
        Casilla casilla = tablero.getTableroBarcos()[fila - 1][columna - 1];
        //Cambiamos el estado de la casilla a impactado para marcarla como disparada
        casilla.setImpactado(true);

        // Si el disparo cae en agua, escribimos por pantalla que ha caído en agua
        if (casilla.isAgua()) {
            System.out.println(Juego.AZUL + "El disparo ha caído en agua" + Juego.BLANCO + "\n");
        } else {
            //En caso de que la casilla no sea de agua, el disparo habrá alcanzado un barco. Guardamos ese barco en una variable
            Tetramino barco = casilla.getBarco();

            // Llamamos al método tocado que resta una vida al barco
            barco.tocado();
            System.out.println(Juego.ROJO + "¡TOCADO!" + Juego.BLANCO + "\n");
            // Comprobamos si el barco se ha hundido, en cuyo caso habrá que restarle uno a los barcos del jugador
            if (barco.esHundido()) {
                this.barcosRestantes = barcosRestantes - 1;
                //Retrasamos un poco el mensaje de hundido
                Thread.sleep(2000);
                System.out.println(Juego.TURQUESA + "¡Y HUNDIDO!" + Juego.BLANCO + "\n");
            }
        }
    }

    /**
     * Método para realizar un disparo aleatorio
     *
     * @throws InterruptedException Excepción que se puede producir por el uso de Thread.sleep
     *                              Este método genera unas coordenadas aleatorias, comprueba que no se haya efectuado ya un disparo en ellas y
     *                              efectúa un disparo en ellas.
     *                              En caso de que hubiera un barco, actualiza la información del barco reduciendo su vida y del número de barcos
     *                              restantes si el barco fue hundido.
     *                              También emite mensajes en pantalla de lo que sucede.
     */
    public void recibirDisparoAleatorio() throws InterruptedException {
        Casilla casilla;
        //Un bucle para seleccionar aleatoriamente las coordenadas de una casilla en la que no se haya disparado
        do {
            int fila = (int) (Math.random() * Juego.BOARD_SIZE + 1);
            int columna = (int) (Math.random() * Juego.BOARD_SIZE + 1);
            casilla = tablero.getTableroBarcos()[fila - 1][columna - 1];
        } while (casilla.isImpactado());

        //Una vez encontrada una casilla válida cambiamos su estado a impactado
        casilla.setImpactado(true);

        //Información para que el jugador sepa en qué casilla está recibiendo el disparo
        System.out.println("Efectuando el disparo en fila " + (casilla.getFila() + 1) + " y columna " + (casilla.getColumna() + 1));

        //Una pequeña espera para darle tensión
        System.out.print("\n. ");
        Thread.sleep(750);
        System.out.print(". ");
        Thread.sleep(750);
        System.out.print(".");
        Thread.sleep(750);
        System.out.println("\n");

        // Si el disparo cae en agua, escribimos por pantalla que ha caído en agua
        if (casilla.isAgua()) {
            System.out.println("\n" + Juego.AZUL + "El disparo de la CPU ha caído en agua" + Juego.BLANCO + "\n");
        } else {
            //En caso de que la casilla no sea de agua, el disparo habrá alcanzado un barco. Guardamos ese barco en una variable
            Tetramino barco = casilla.getBarco();

            // Llamamos al método tocado que resta una vida al barco
            barco.tocado();
            System.out.println(Juego.ROJO + "¡La CPU te ha dado!" + Juego.BLANCO + "\n");

            // Comprobamos si el barco se ha hundido, en cuyo caso habrá que restarle uno a los barcos del jugador
            if (barco.esHundido()) {
                this.barcosRestantes = barcosRestantes - 1;
                //Retrasamos un poquito el mensaje de hundido
                Thread.sleep(750);
                System.out.println(Juego.TURQUESA + "¡La CPU te ha hundido!" + Juego.BLANCO + "\n");
            }
        }
    }

    /**
     * Método par rellenar un array de tetraminos con distintos tipos de tetraminos
     */
    public void rellenarArrayBarcos() {
        if (Juego.SHIP_AMOUNT > 5) {
            for (int i = 0; i < 5; i++) {
                barcos[i] = new Tetramino(i + 1, 1);
            }
            for (int i = 5; i < Juego.SHIP_AMOUNT; i++) {
                barcos[i] = new Tetramino((int) (Math.random() * 5 + 1), 1);
            }
        } else {
            for (int i = 0; i < Juego.SHIP_AMOUNT; i++) {
                barcos[i] = new Tetramino(i + 1, 1);
            }
        }
    }


    /**
     * Un método que coge el array de barcos del jugador y lo guía para ir colocándolos de manera manual u aleatoria
     */
    public void colocarArrayBarcos() {
        //Recorremos el array de barcos
        for (Tetramino barco : this.barcos) {
            //Establecemos una variable de control para la selección del usuario y le pedimos que nos diga si quiere colocarlo
            //de manera manual o aleatoria
            String eleccion;
            System.out.println("¿Quieres colocar el siguiente barco de manera aleatoria? (S/N)");
            eleccion = read.nextLine().trim().toUpperCase();
            //Le volvemos a pedir que introduzca la información si no lo ha hecho bien
            while (!eleccion.equals("S") && !eleccion.equals("N")) {
                System.err.println("Por favor, escriba S para sí o N para no: ");
                eleccion = read.nextLine().trim().toUpperCase();
            }
            //Un if else para diferenciar si quiere colocarlo manual o aleatorio
            if (eleccion.equals("S")) {
                this.tablero.colocarBarcoAleatorio(barco);
            } else {
                this.tablero.colocarBarco(barco);
            }
            System.out.println(this.tablero);
        }
    }

}
