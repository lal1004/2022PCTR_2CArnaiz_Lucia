package practica2C;

public class SistemaLanzador {
	
	public  SistemaLanzador() {
		
	}

    public static void main(String[] args) {

    	int cantidadEnemigos = 5;
        Juego nuevaPartida = new Juego(cantidadEnemigos);

        for (int i=0; i < cantidadEnemigos; i++) {
                Thread enemigoThread = new Thread(new ActividadEnemiga(i, nuevaPartida)); 
                Thread aliadoThread = new Thread(new ActividadAliada(i, nuevaPartida));

                enemigoThread.start();
                aliadoThread.start();
        }
    }
}
