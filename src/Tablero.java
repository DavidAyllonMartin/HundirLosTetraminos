import java.util.Scanner;

/**
 * @author David Ayllón Martín
 * @author Luis Abenójar Andreu
 * @version 1.0
 */

public class Tablero {

    //Array bidimensional de objetos Casilla para crear un tablero donde mostraremos los barcos
    private Casilla[][] tableroBarcos;

    //Array bidimensional de objetos Casilla para crear un tablero donde mostraremos los disparos
    private Casilla[][] tableroDisparos;

    //Scanner propio para la clase
    Scanner read = new Scanner(System.in);

    /**
     * Constructor por defecto
     */
    public Tablero() {
        this(1);
    }

    //Constructor

    /**
     * Constructor completo
     *
     * @param dimension Tamaño de las filas y las columnas de los tableros del jugador
     */
    public Tablero(int dimension) {
        //Creamos tablero de barcos
        Casilla[][] tableroBarcos = new Casilla[dimension][dimension];
        //Inicializaremos todas las casillas en agua y sin impactar
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                tableroBarcos[i][j] = new Casilla(i, j, true, false);
            }
        }
        //Repetimos con tablero disparos
        Casilla[][] tableroDisparos = new Casilla[dimension][dimension];

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                tableroDisparos[i][j] = new Casilla(i, j, false, false);
            }
        }

        this.tableroBarcos = tableroBarcos;
        this.tableroDisparos = tableroDisparos;
    }

    //Getters y Setters

    /**
     * Getter del atributo tableroBarcos
     *
     * @return Matriz de objetos casilla del tablero de barcos del jugador
     */
    public Casilla[][] getTableroBarcos() {
        return tableroBarcos;
    }

    /**
     * Setter del atributo tableroBarcos
     *
     * @param tableroBarcos tablero de barcos que queremos asignarle al jugador
     */
    public void setTableroBarcos(Casilla[][] tableroBarcos) {
        this.tableroBarcos = tableroBarcos;
    }

    /**
     * Getter del atributo tableroDisparos
     *
     * @return Matriz de objetos casilla del tablero de disparos del jugador
     */
    public Casilla[][] getTableroDisparos() {
        return tableroDisparos;
    }

    /**
     * Setter del atributo tableroBarcos
     *
     * @param tableroDisparos tablero de disparos que queremos asignarle al jugador
     */
    public void setTableroDisparos(Casilla[][] tableroDisparos) {
        this.tableroDisparos = tableroDisparos;
    }

    /**
     * Método para colocar un barco en el tablero de barcos del jugador.
     *
     * @param barco El barco que queremos colocar
     *              Este método coloca el barco seleccionado con la rotación y las coordenadas que elija el usuario.
     *              Comprueba que las coordenadas sean correctas y actualiza la información de todas las casillas involucradas
     */
    public void colocarBarco(Tetramino barco) {
        //Booleano para obligar al usuario a repetir la selección si se le sale el barco del tablero o lo pone en un sitio inválido
        boolean repetir;

        //Bucle para establecer la posición del barco en función de la información introducida por el usuario
        do {
            //Establecemos repetir en false para salir del bucle si está todo bien
            repetir = false;

            //Le enseñamos al usuario las posibilidades que tiene para colocar ese tipo de barco y le pedimos que elija una
            System.out.println("Estas son todas las posibles rotaciones para tu barco\n");
            mostrarRotaciones(barco);
            System.out.println("¿Qué rotación quieres para tu barco?");
            barco.setRotacion(read.nextInt());
            System.out.println("¿En qué coordenadas quieres colocar tu barco?");

            //Establecemos nuestras variables con los datos del usuario
            System.out.println("Selecciona fila");
            int fila = read.nextInt();
            System.out.println("Selecciona columna");
            int columna = read.nextInt();
            try {
                //Con la información que nos ha dado el usuario intentamos seleccionar los objetos Casilla del tablero que le correspondan
                barco.setPosicion(this.tableroBarcos, fila, columna);
                //Si alguna de las casillas del tablero que engloban el barco no es válida repetimos el bucle
                if (!esPosicionValida(barco)) {
                    System.err.println("La posición que intentas elegir no es válida");
                    repetir = true;
                }
                //En caso de que se salga del tablero, imprimimos un mensaje de error y pasamos a true la variable repetir para repetir el bucle
            } catch (ArrayIndexOutOfBoundsException e) {
                System.err.println("El barco se sale del tablero");
                repetir = true;
            }
        } while (repetir);

        //Una vez elegida una posición válida seleccionamos todos los objetos Casilla implicados y les añadimos la información del barco
        for (Casilla casilla : barco.getPosicion()) {
            casilla.setAgua(false);
            casilla.setBarco(barco);
        }
    }

    /**
     * Método para colocar un barco en el tablero de barcos del jugador de manera aleatoria.
     *
     * @param barco El barco que queremos colocar
     *              Este método coloca el barco seleccionado con una rotación y unas coordenadas aleatorias.
     *              Comprueba que las coordenadas sean correctas y actualiza la información de todas las casillas involucradas
     */
    public void colocarBarcoAleatorio(Tetramino barco) {
        boolean repetir;

        do {
            //Establecemos repetir en false para salir del bucle si está todo bien
            repetir = false;

            //Comprobamos el tipo de barco para darle una rotación aleatoria de entre todas sus posibilidades
            int tipo = barco.getTipo();
            int rotacion = barco.getRotacion();
            switch (tipo) {
                case 1:
                    rotacion = (int) (Math.random() * 2 + 1);
                    break;
                case 2:
                    rotacion = 1;
                    break;
                case 3:
                case 5:
                    rotacion = (int) (Math.random() * 4 + 1);
                    break;
                case 4:
                    rotacion = (int) (Math.random() * 8 + 1);
                    break;
            }
            barco.setRotacion(rotacion);

            //Establecemos la fila y la columna aleatoriamente
            int fila = (int) (Math.random() * (Juego.BOARD_SIZE - 1) + 1);
            int columna = (int) (Math.random() * (Juego.BOARD_SIZE - 1) + 1);
            try {
                //Intentamos seleccionar los objetos Casilla del tablero que correspondan a la fila y la columna
                barco.setPosicion(this.tableroBarcos, fila, columna);
                //Si alguna de las casillas del tablero que engloba el barco no es válida repetimos el bucle
                if (!esPosicionValida(barco)) {
                    repetir = true;
                }
                //En caso de que se salga del tablero pasamos a true la variable repetir para repetir el bucle
            } catch (ArrayIndexOutOfBoundsException e) {
                repetir = true;
            }
        } while (repetir);

        //Ya con una posición válida seleccionamos todos los objetos Casilla implicados y les añadimos la información del barco
        for (Casilla casilla : barco.getPosicion()) {
            casilla.setAgua(false);
            casilla.setBarco(barco);
        }
    }

    /**
     * Este método comprueba que no haya ya un barco en las casillas asignadas a un barco ni en las casillas adyacentes
     *
     * @param barco el barco que el usuario intenta colocar
     * @return true si la posición es válida para poner el barco o false si no lo es
     * El método comprueba que las casillas de posición de un barco no se superpongan ni sean adyacentes a ninguna otra casilla
     * que ya contenga un barco.
     */
    public boolean esPosicionValida(Tetramino barco) {
        //Recorremos todas las casillas asociadas al barco
        for (Casilla casilla : barco.getPosicion()) {
            //Extraemos la información de la fila y la columna de la casilla
            int fila = casilla.getFila();
            int columna = casilla.getColumna();
            //Recorremos la matriz 3x3 que forman la casilla y sus adyacentes
            for (int i = fila - 1; i <= fila + 1; i++) {
                for (int j = columna - 1; j <= columna + 1; j++) {
                    //Para evitar salirnos del tablero, cuando la fila y la columna no estén en rango no hacemos nada
                    if (i >= 0 && j >= 0 && i < Juego.BOARD_SIZE && j < Juego.BOARD_SIZE) {
                        //Cuando sí estén en rango comprobamos si hay barco en la casilla y si lo hay rompemos el bucle
                        //y mandamos la información de que la posición no es válida
                        if (!tableroBarcos[i][j].isAgua()) {
                            return false;
                        }
                    }
                }
            }
        }
        //Si hemos llegado hasta aquí significa que está todo correcto
        return true;
    }

    /**
     * Método que imprime en pantalla una ayuda visual para que el usuario identifique las distintas rotaciones de un tipo de barco
     *
     * @param barco el barco que el usuario quiere colocar
     *              En este método imprimimos en pantalla todas las posibles rotaciones del tipo de barco que el usuario está intentando colocar
     */

    public void mostrarRotaciones(Tetramino barco) {
        int tipo = barco.getTipo();
        String str = "";
        switch (tipo) {
            case 1:
                str = "Número 1  Número 2  \n■ ■ ■ ■   ■       \n          ■       \n          ■       \n          ■       \n";
                break;
            case 2:
                str = "Número 1  \n■ ■     \n■ ■     \n";
                break;
            case 3:
                str = "Número 1  Número 2  Número 3  Número 4  \n■ ■ ■       ■         ■       ■       \n  ■       ■ ■       ■ ■ ■     ■ ■     \n            ■                 ■       \n";
                break;
            case 4:
                str = "Número 1  Número 2  Número 3  Número 4  Número 5  Número 6  Número 7  Número 8  \n■ ■ ■     ■ ■           ■     ■         ■ ■ ■       ■       ■         ■ ■     \n■           ■       ■ ■ ■     ■             ■       ■       ■ ■ ■     ■       \n            ■                 ■ ■                 ■ ■                 ■       \n";
                break;
            case 5:
                str = "Número 1  Número 2  Número 3  Número 4  \n■ ■         ■         ■ ■     ■       \n  ■ ■     ■ ■       ■ ■       ■ ■     \n          ■                     ■     \n";
                break;
        }
        System.out.println(str);
    }

    //Todos estos métodos fueron la idea inicial para mostrar las rotaciones, pero entendemos que es más sencillo y óptimo
    //hacerlo a piñón solo con Strings que creando tantos objetos y complicando el proceso
    /*public void mostrarRotaciones(Tetramino barco) {
        int tipo = barco.getTipo();
        Casilla[][] expositor;
        Tetramino rot1, rot2, rot3, rot4, rot5, rot6, rot7, rot8;
        switch (tipo){
            case 1:
                expositor = new Casilla[4][9];
                rellenarExpositor(expositor);
                rot1 = new Tetramino(tipo, 1);
                colocarBarcoEnExpositor(rot1, expositor, 1);
                rot2 = new Tetramino(tipo, 2);
                colocarBarcoEnExpositor(rot2, expositor, 6);
                ImprimirExpositor(expositor);
                break;

            case 2:
                expositor = new Casilla[4][4];
                rellenarExpositor(expositor);
                rot1 = new Tetramino(tipo, 1);
                colocarBarcoEnExpositor(rot1, expositor, 1);
                ImprimirExpositor(expositor);
                break;
            case 3:
            case 5:
                expositor = new Casilla[4][19];
                rellenarExpositor(expositor);
                rot1 = new Tetramino(tipo, 1);
                colocarBarcoEnExpositor(rot1, expositor, 1);
                rot2 = new Tetramino(tipo, 2);
                colocarBarcoEnExpositor(rot2, expositor, 6);
                rot3 = new Tetramino(tipo, 3);
                colocarBarcoEnExpositor(rot3, expositor, 11);
                rot4 = new Tetramino(tipo, 4);
                colocarBarcoEnExpositor(rot4, expositor, 16);
                ImprimirExpositor(expositor);
                break;

            case 4:
                expositor = new Casilla[4][39];
                rellenarExpositor(expositor);
                rot1 = new Tetramino(tipo, 1);
                colocarBarcoEnExpositor(rot1, expositor, 1);
                rot2 = new Tetramino(tipo, 2);
                colocarBarcoEnExpositor(rot2, expositor, 6);
                rot3 = new Tetramino(tipo, 3);
                colocarBarcoEnExpositor(rot3, expositor, 11);
                rot4 = new Tetramino(tipo, 4);
                colocarBarcoEnExpositor(rot4, expositor, 16);
                rot5 = new Tetramino(tipo, 5);
                colocarBarcoEnExpositor(rot5, expositor, 21);
                rot6 = new Tetramino(tipo, 6);
                colocarBarcoEnExpositor(rot6, expositor, 26);
                rot7 = new Tetramino(tipo, 7);
                colocarBarcoEnExpositor(rot7, expositor, 31);
                rot8 = new Tetramino(tipo, 8);
                colocarBarcoEnExpositor(rot8, expositor, 36);
                ImprimirExpositor(expositor);
                break;
        }
    }*/

    /*private static void rellenarExpositor(Casilla[][] expositor) {
        for (int i = 0; i < expositor.length; i++) {
            for (int j = 0; j < expositor[0].length; j++) {
                expositor[i][j] = new Casilla();
            }
        }
    }*/

    /*private static void ImprimirExpositor(Casilla[][] expositor) {
        StringBuilder strb = new StringBuilder();

        for (int i = 0; i < expositor[0].length/5 + 1; i++) {
            strb.append("Número ").append(i + 1).append("  ");
        }
        strb.append("\n");

        for (Casilla[] casillas : expositor) {
            for (int j = 0; j < expositor[0].length; j++) {
                if (casillas[j].isAgua()) {
                    strb.append("  ");
                } else {
                    strb.append("■ ");
                }
            }
            strb.append("\n");
        }
        System.out.println(strb);
    }*/

    /*private static void colocarBarcoEnExpositor(Tetramino barco, Casilla[][] expositor, int columna) {
        barco.setPosicion(expositor, 1, columna);
        for (Casilla casilla : barco.getPosicion()) {
            casilla.setAgua(false);
        }
    }*/

    /**
     * Sobreescritura del método toString para que se pueda mostrar por pantalla la información de los tableros
     *
     * @return string con la información de los tableros de disparos y de barcos
     */

    @Override
    public String toString() {
        StringBuilder strb = new StringBuilder();
        /*strb.append("      TUS DISPAROS        |         TUS BARCOS\n");
        strb.append("   1 2 3 4 5 6 7 8 9 10   |     1 2 3 4 5 6 7 8 9 10\n");*/

        strb.append(" ".repeat(Math.max(0, Juego.BOARD_SIZE - 3)));
        strb.append("TUS DISPAROS");

        strb.append(" ".repeat(Math.max(0, Juego.BOARD_SIZE - 3)));
        strb.append("|");

        strb.append(" ".repeat(Math.max(0, Juego.BOARD_SIZE)));
        strb.append("TUS BARCOS\n");

        strb.append("   ");
        for (int i = 0; i < Juego.BOARD_SIZE; i++) {
            if (i < 9) {
                strb.append(i + 1).append(" ");
            } else {
                strb.append(i + 1);
            }
        }

        strb.append("   |     ");
        for (int i = 0; i < Juego.BOARD_SIZE; i++) {
            if (i < 9) {
                strb.append(i + 1).append(" ");
            } else {
                strb.append(i + 1);
            }
        }
        strb.append("\n");

        //Aquí dibujamos el tablero de disparos
        for (int i = 0; i < Juego.BOARD_SIZE; i++) {

            if (i < 9) {
                strb.append(" ").append(i + 1).append(" ");
            } else {
                strb.append(i + 1).append(" ");
            }

            for (int j = 0; j < Juego.BOARD_SIZE; j++) {
                strb.append(tableroDisparos[i][j].toString()).append(" ");
            }
            //Una línea en medio para separar ambos tableros
            strb.append("   |  ");

            if (i < 9) {
                strb.append(" ").append(i + 1).append(" ");
            } else {
                strb.append(i + 1).append(" ");
            }

            //Bucle para dibujar cuando ha sido agua, si hay algún barco, cuando ha sido impactado, y si no hay nada en alguna casilla
            for (int j = 0; j < Juego.BOARD_SIZE; j++) {
                if (getTableroBarcos()[i][j].isImpactado()) {
                    if (getTableroBarcos()[i][j].isAgua()) {
                        strb.append(Juego.AZUL + "x " + Juego.BLANCO);
                    } else {
                        strb.append(Juego.ROJO + "■ " + Juego.BLANCO);
                    }
                } else {
                    if (getTableroBarcos()[i][j].isAgua()) {
                        strb.append("  ");
                    } else {
                        strb.append("■ ");
                    }
                }
            }
            strb.append("\n");
        }

        return strb.toString();
    }

}
