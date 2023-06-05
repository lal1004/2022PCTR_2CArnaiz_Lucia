package practica2C;

public class SistemaLanzador {
	
	public  SistemaLanzador() { //Constructor
		
	}

    public static void main(String[] args) {

    	int cantidadEnemigos = 5; //N�mero de enemigos que va a generar
        Juego nuevaPartida = new Juego(cantidadEnemigos); // Instancia de Juego con la cantidad de enemigos especificada arriba

        for (int i=0; i < cantidadEnemigos; i++) {
                Thread enemigoThread = new Thread(new ActividadEnemiga(i, nuevaPartida)); 
                Thread aliadoThread = new Thread(new ActividadAliada(i, nuevaPartida));

                enemigoThread.start();
                aliadoThread.start();
        }
    }
}
