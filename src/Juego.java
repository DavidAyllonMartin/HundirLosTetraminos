import java.util.Scanner;

/**
 * @author David Ayllón Martín
 * @author Luis Abenójar Andreu
 * @version 1.0
 */

class Juego {

    public static final String AZUL = "\u001B[34m";
    public static final String BLANCO = "\u001B[0m";
    public static final String TURQUESA = "\u001B[36m";
    public static final String ROJO = "\u001B[31m";
    /* Constantes para el tamaño del tablero y el número de barcos. Las dos tienen que ser positivas
    Originalmente el juego está pensado y optimizado para tableros 10x10 y 5 barcos pero el método toString de tablero
    está generalizado e imprime decentemente tableros de hasta 100x100. */
    public static int BOARD_SIZE = 10;
    public static int SHIP_AMOUNT = 5;
    static Scanner read = new Scanner(System.in);

    /**
     * @param args No le vamos a pasar argumentos
     *             En nuestro main, colocaremos 2 tableros, uno para el jugador 1 y otro para el jugador 2 (en principio es la cpu)
     *             Crearemos un array de tetramino donde guardaremos nuestros barcos, por defecto dejaremos que sea de 5, pero
     *             podremos cambiarlo por el número que queramos de barcos, en nuestro tablero disparos (el tablero de los barcos del
     *             rival) se lo asignaremos al contrario de tal manera que tú verás 2 tableros, el tuyo con tus barcos y el de tu
     *             rival, aparentemente vacío porque los barcos del rival están ocultos. Introduciremos un nombre para el jugador 1
     *             y le pediremos que escoja una pieza, ya sea aleatoria o la coloque él mismo. Cuando estén todos los barcos
     *             colocados, la partida dará comienzo. Empezará siempre el jugador 1. Cada vez que uno dispare, cambiará de turno.
     *             Por último, comprobará si no le quedan barcos a alguno de los jugadores. Si se acaban los barcos, mostrará al ganador
     */
    public static void main(String[] args) {

        // Tableros para jugador 1 y 2 (deben de ser del mismo tamaño, para que uno no tenga más o menos casillas que el otro)
        Tablero tableroJ1 = new Tablero(BOARD_SIZE);
        Tablero tableroJ2 = new Tablero(BOARD_SIZE);

        // Establecemos como tablero de disparos el tablero de barcos del otro jugador
        tableroJ1.setTableroDisparos(tableroJ2.getTableroBarcos());
        tableroJ2.setTableroDisparos(tableroJ1.getTableroBarcos());

        // Aquí creamos los jugadores, y les asignamos sus barcos y su tablero
        System.out.println("Introduce tu nombre para comenzar la partida:");

        Jugador jugador1 = new Jugador(read.nextLine(), tableroJ1);
        Jugador jugador2 = new Jugador("CPU", tableroJ2);


        // El jugador 1 coloca sus barcos en su tablero
        System.out.println(jugador1.getNombre() + ", te toca colocar tus barcos\n");
        System.out.println(tableroJ1);
        jugador1.colocarArrayBarcos();

        // El ordenador coloca sus barcos de manera aleatoria
        for (Tetramino barco : jugador2.getBarcos()) {
            tableroJ2.colocarBarcoAleatorio(barco);
        }

        System.out.println("LA PARTIDA COMIENZA\n");

        // El turno por defecto lo dejamos en true, comienza siempre el jugador 1
        boolean turno = true;

        // Mientras los barcos de cada jugador no hayan llegado a 0, la partida seguirá
        while (jugador1.getBarcosRestantes() != 0 && jugador2.getBarcosRestantes() != 0) {

            //Turno del jugador 1
            if (turno) {
                //Le enseñamos su tablero y un mensaje de que es su turno
                System.out.println(tableroJ1);
                System.out.println(jugador1.getNombre() + " está atacando\n");

                //Try catch para gestionar la excepción InterruptedException que puede generar Thread.sleep
                try {
                    //El disparo del jugador 1 al jugador 2
                    jugador1.disparar(jugador2);
                    //Una pequeña espera para que el jugador pueda procesar la información
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                turno = false;

                // Si el jugador 2 se ha quedado sin barcos se termina la partida
                if (jugador2.getBarcosRestantes() == 0) {
                    System.out.println("¡" + jugador1.getNombre() + " ha ganado!");
                    break;
                }

                // Es el turno del jugador 2, se repite lo anterior pero para el jugador 2
            } /*else {
                                System.out.println(tableroJ2);
                                jugador2.disparar(jugador1);
                                turno = true;
                                if (jugador1.getBarcosRestantes() == 0) {
                                        System.out.println("¡" + jugador2.getNombre() + " ha ganado!");
                                        break;
                                }*/

            // Para jugar con el ordenador
            else {
                //Mensaje para que el jugador sepa que lo están atacando
                System.out.println(jugador2.getNombre() + " está atacando\n");

                //Try catch para gestionar la excepción InterruptedException que puede generar Thread.sleep
                try {
                    //Un poco de tiempo para procesar el flujo del juego
                    Thread.sleep(1000);
                    //El disparo aleatorio del ordenador al jugador 1
                    jugador1.recibirDisparoAleatorio();
                    //Un poco de tiempo para procesar el flujo del juego
                    Thread.sleep(2000);
                    turno = true;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                // Si el jugador 1 se ha quedado sin barcos se termina la partida
                if (jugador1.getBarcosRestantes() == 0) {
                    System.out.println(jugador2.getNombre() + " ha ganado");
                    break;
                }
            }
        }
    }
}

