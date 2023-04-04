/**
 * @author David Ayllón Martín
 * @author Luis Abenójar Andreu
 * @version 1.0
 */

public class Casilla {

    //Atributos

    //Fila y columna asociada a la casilla
    private int fila;
    private int columna;

    //Booleano que nos sirve para saber si hay agua o un barco en la casilla
    private boolean agua;

    //Atributo que nos sirve para saber si se ha disparado o no en esta casilla
    private boolean impactado;

    //Atributo que almacena la referencia del barco asociado a esta casilla
    private Tetramino barco;

    /**
     * Constructor por defecto para los objetos Casilla
     */
    public Casilla() {

        this.fila = 0;
        this.columna = 0;
        this.agua = true;
        this.impactado = false;

    }

    /**
     * Constructor para los objetos Casilla con todos los atributos menos el barco asignado
     *
     * @param fila      Fila a la que pertenece la casilla
     * @param columna   Columna a la que pertenece la casilla
     * @param agua      Si la casilla contiene agua o un barco
     * @param impactado Si en la casilla se ha efectuado un disparo o no
     */

    public Casilla(int fila, int columna, boolean agua, boolean impactado) {
        setFila(fila);
        setColumna(columna);
        this.agua = agua;
        this.impactado = impactado;
    }

    // Constructor con todos los atributos

    /**
     * Constructor para los objetos Casilla con todos los atributos
     *
     * @param fila      Fila a la que pertenece la casilla
     * @param columna   Columna a la que pertenece la casilla
     * @param agua      Si la casilla contiene agua o un barco
     * @param impactado Si en la casilla se ha efectuado un disparo o no
     * @param barco     El barco asignado a esta casilla
     */
    public Casilla(int fila, int columna, boolean agua, boolean impactado, Tetramino barco) {
        this(fila, columna, agua, impactado);
        this.barco = barco;

    }

    // Getters y Setters

    /**
     * Getter del atributo fila
     *
     * @return Fila a la que pertenece la casilla
     */
    public int getFila() {
        return fila;
    }

    /**
     * Setter del atributo fila
     *
     * @param fila La fila que le queremos asignar
     */
    public void setFila(int fila) {
        // Obligamos a que no se pueda poner un número fuera del rango del tablero
        if (fila < 0) {
            fila = 0;
        } else if (fila > Juego.BOARD_SIZE - 1) {
            fila = Juego.BOARD_SIZE - 1;
        }

        this.fila = fila;

    }

    /**
     * Getter del atributo columna
     *
     * @return Columna a la que pertenece la casilla
     */
    public int getColumna() {

        return columna;
    }

    /**
     * Setter del atributo columna
     *
     * @param columna La columna que queremos asignar
     */
    public void setColumna(int columna) {
        // Obligamos a que no se pueda poner un número fuera del rango del tablero
        if (columna < 0) {
            columna = 0;
        } else if (columna > Juego.BOARD_SIZE - 1) {
            columna = Juego.BOARD_SIZE - 1;
        }

        this.columna = columna;
    }

    /**
     * Getter del atributo barco
     *
     * @return Objeto barco asignado a la casilla
     */
    public Tetramino getBarco() {
        return barco;
    }

    /**
     * Setter del atributo barco
     *
     * @param barco Objeto barco que queremos asignar a la casilla
     */
    public void setBarco(Tetramino barco) {
        this.barco = barco;
    }

    /**
     * Getter del atributo Agua
     *
     * @return true si es agua o false si hay un barco
     */
    public boolean isAgua() {
        return agua;
    }

    /**
     * Setter del atributo agua
     *
     * @param agua valor que queremos asignar a la casilla
     */
    public void setAgua(boolean agua) {
        this.agua = agua;
    }

    /**
     * Getter del atributo impactado
     *
     * @return true si ya se ha efectuado un disparo en la casilla y false si todavía no
     */
    public boolean isImpactado() {
        return impactado;
    }

    /**
     * Setter del atributo impactado
     *
     * @param impactado valor que queremos asignar a la casilla
     */
    public void setImpactado(boolean impactado) {
        this.impactado = impactado;
    }

    //

    /**
     * Sobreescritura del método toString para poder sacar por pantalla la información de la casilla según sus distintos estados
     *
     * @return string con información sobre el estado de la casilla
     */

    @Override
    public String toString() {
        String str;

        if (this.impactado && this.agua) {
            str = Juego.AZUL + "■" + Juego.BLANCO;
        } else if (this.impactado) {
            str = Juego.ROJO + "■" + Juego.BLANCO;
        } else {
            str = "□";
        }
        return str;
    }
}