/**
 * @author David Ayllón Martín
 * @author Luis Abenójar Andreu
 * @version 1.0
 */

public class Tetramino {

    // El tipo de pieza de tetris que se escogerá
    private int tipo;

    // La rotación que queremos que tenga
    private int rotacion;

    // Un array de objetos Casilla donde guardaremos su posición
    private Casilla[] posicion;

    // La vida que tendrá el barco
    private int vida;

    /**
     * Constructor por defecto
     */
    public Tetramino() {

        this.tipo = 0;
        this.rotacion = 0;
        this.vida = 0;
        this.posicion = new Casilla[4];
        for (int i = 0; i < posicion.length; i++) {
            this.posicion[i] = new Casilla();
        }

    }

    /**
     * Constructor con el tipo de pieza y su rotación
     *
     * @param tipo     El tipo de barco que queremos asignarle a nuestro tetramino
     * @param rotacion La rotación que establece la posición del tetramino
     */
    public Tetramino(int tipo, int rotacion) {
        setTipo(tipo);
        setRotacion(rotacion);
        this.posicion = new Casilla[4];
        for (int i = 0; i < posicion.length; i++) {
            this.posicion[i] = new Casilla();
        }
        this.vida = posicion.length;
    }

    // Constructor completo

    /**
     * Constructor completo
     *
     * @param tipo     El tipo de barco que queremos asignarle a nuestro tetramino
     * @param rotacion La rotación que establece la posición del tetramino
     * @param posicion Array de objetos casilla que determinan la posición del tetramino
     */
    public Tetramino(int tipo, int rotacion, Casilla[] posicion) {
        setTipo(tipo);
        setRotacion(rotacion);
        this.posicion = posicion;
        this.vida = posicion.length;
    }

    // Getters y Setters

    /**
     * Getter del atributo tipo
     *
     * @return Tipo de tetramino
     */
    public int getTipo() {
        return tipo;
    }

    /**
     * Setter del atributo tipo
     *
     * @param tipo Tipo que le queremos asignar al tetramino
     */
    public void setTipo(int tipo) {
        // El tipo tiene que estar entre 1 y 5 así que si se intenta introducir un valor fuera de rango forzamos que esté dentro
        if (tipo < 1) {
            tipo = 1;
        } else if (tipo > 5) {
            tipo = 5;
        }
        this.tipo = tipo;
    }

    /**
     * Getter del atributo rotacion
     *
     * @return Rotación del tetramino
     */
    public int getRotacion() {
        return rotacion;
    }

    /**
     * Setter del atributo rotacion
     *
     * @param rotacion Rotación que le queremos asignar al tetramino
     */
    public void setRotacion(int rotacion) {
        // Cada pieza tiene unas posiciones determinadas. Dependiendo de la pieza, acotamos el atributo rotación y forzamos que esté en rango
        if (rotacion < 1) {
            rotacion = 1;
        } else {
            switch (this.tipo) {
                case 1:
                    if (rotacion > 2) rotacion = 2;
                    break;
                case 2:
                    if (rotacion > 1) rotacion = 1;
                    break;
                case 3:
                    if (rotacion > 4) rotacion = 4;
                    break;
                case 4:
                    if (rotacion > 8) rotacion = 8;
                    break;
                case 5:
                    if (rotacion > 4) rotacion = 4;
                    break;
                default:
                    rotacion = 1;
            }
        }

        this.rotacion = rotacion;
    }

    /**
     * Getter del atributo vida
     *
     * @return Vida restante del tetramino
     */
    public int getVida() {
        return vida;
    }

    /**
     * Setter del atributo vida
     *
     * @param vida Vida que el queremos asignar al tetramino
     */
    public void setVida(int vida) {
        this.vida = vida;
    }

    /**
     * Getter del atributo posicion
     *
     * @return Array de casillas de posición del tetramino
     */
    public Casilla[] getPosicion() {
        return posicion;
    }

    /**
     * Setter para la posición de un barco en función de su tipo y rotación
     *
     * @param tableroBarcos tablero donde se están asignando las posiciones
     * @param fila          La fila donde se va a colocar la primera casilla del barco
     * @param columna       La columna donde se va a colocar la primera casilla del barco
     */
    public void setPosicion(Casilla[][] tableroBarcos, int fila, int columna) {

        switch (tipo) {
            case 1:
                switch (rotacion) {
                    case 1:

                        posicion = new Casilla[]{tableroBarcos[fila - 1][columna - 1], tableroBarcos[fila - 1][columna], tableroBarcos[fila - 1][columna + 1], tableroBarcos[fila - 1][columna + 2]};
                        break;
                    case 2:
                        posicion = new Casilla[]{tableroBarcos[fila - 1][columna - 1], tableroBarcos[fila][columna - 1], tableroBarcos[fila + 1][columna - 1], tableroBarcos[fila + 2][columna - 1]};
                        break;
                }
                break;
            case 2:
                posicion = new Casilla[]{tableroBarcos[fila - 1][columna - 1], tableroBarcos[fila][columna - 1], tableroBarcos[fila - 1][columna], tableroBarcos[fila][columna]};
                break;
            case 3:
                switch (rotacion) {
                    case 1:
                        posicion = new Casilla[]{tableroBarcos[fila - 1][columna - 1], tableroBarcos[fila - 1][columna], tableroBarcos[fila - 1][columna + 1], tableroBarcos[fila][columna]};
                        break;
                    case 2:
                        posicion = new Casilla[]{tableroBarcos[fila - 1][columna], tableroBarcos[fila][columna - 1], tableroBarcos[fila][columna], tableroBarcos[fila + 1][columna]};
                        break;
                    case 3:
                        posicion = new Casilla[]{tableroBarcos[fila - 1][columna], tableroBarcos[fila][columna - 1], tableroBarcos[fila][columna], tableroBarcos[fila][columna + 1]};
                        break;
                    case 4:
                        posicion = new Casilla[]{tableroBarcos[fila - 1][columna - 1], tableroBarcos[fila][columna - 1], tableroBarcos[fila + 1][columna - 1], tableroBarcos[fila][columna]};
                        break;
                }
                break;
            case 4:
                switch (rotacion) {
                    case 1:
                        posicion = new Casilla[]{tableroBarcos[fila - 1][columna - 1], tableroBarcos[fila - 1][columna], tableroBarcos[fila - 1][columna + 1], tableroBarcos[fila][columna - 1]};
                        break;
                    case 2:
                        posicion = new Casilla[]{tableroBarcos[fila - 1][columna - 1], tableroBarcos[fila - 1][columna], tableroBarcos[fila][columna], tableroBarcos[fila + 1][columna]};
                        break;
                    case 3:
                        posicion = new Casilla[]{tableroBarcos[fila - 1][columna + 1], tableroBarcos[fila][columna - 1], tableroBarcos[fila][columna], tableroBarcos[fila][columna + 1]};
                        break;
                    case 4:
                        posicion = new Casilla[]{tableroBarcos[fila - 1][columna - 1], tableroBarcos[fila][columna - 1], tableroBarcos[fila + 1][columna - 1], tableroBarcos[fila + 1][columna]};
                        break;
                    case 5:
                        posicion = new Casilla[]{tableroBarcos[fila - 1][columna - 1], tableroBarcos[fila - 1][columna], tableroBarcos[fila - 1][columna + 1], tableroBarcos[fila][columna + 1]};
                        break;
                    case 6:
                        posicion = new Casilla[]{tableroBarcos[fila + 1][columna - 1], tableroBarcos[fila - 1][columna], tableroBarcos[fila][columna], tableroBarcos[fila + 1][columna]};
                        break;
                    case 7:
                        posicion = new Casilla[]{tableroBarcos[fila - 1][columna - 1], tableroBarcos[fila][columna - 1], tableroBarcos[fila][columna], tableroBarcos[fila][columna + 1]};
                        break;
                    case 8:
                        posicion = new Casilla[]{tableroBarcos[fila - 1][columna - 1], tableroBarcos[fila][columna - 1], tableroBarcos[fila + 1][columna - 1], tableroBarcos[fila - 1][columna]};
                        break;
                }
                break;
            case 5:
                switch (rotacion) {
                    case 1:
                        posicion = new Casilla[]{tableroBarcos[fila - 1][columna - 1], tableroBarcos[fila - 1][columna], tableroBarcos[fila][columna], tableroBarcos[fila][columna + 1]};
                        break;
                    case 2:
                        posicion = new Casilla[]{tableroBarcos[fila - 1][columna], tableroBarcos[fila][columna - 1], tableroBarcos[fila][columna], tableroBarcos[fila + 1][columna - 1]};
                        break;
                    case 3:
                        posicion = new Casilla[]{tableroBarcos[fila - 1][columna], tableroBarcos[fila - 1][columna + 1], tableroBarcos[fila][columna - 1], tableroBarcos[fila][columna]};
                        break;
                    case 4:
                        posicion = new Casilla[]{tableroBarcos[fila - 1][columna - 1], tableroBarcos[fila][columna - 1], tableroBarcos[fila][columna], tableroBarcos[fila + 1][columna]};
                        break;
                }
                break;
        }
    }

    /**
     * Método para restar una vida cuando un barco ha sido tocado
     */

    public void tocado() {

        this.vida -= 1;

    }

    /**
     * Método para comprobar que un barco ha sido hundido
     *
     * @return true si ha sido hundido y false si no
     */
    public boolean esHundido() {

        return getVida() == 0;

    }
}

