package practica2C;

public class SistemaLanzador {
	
	public  SistemaLanzador() { //Constructor
		
	}

    public static void main(String[] args) {

    	int cantidadEnemigos = 4; //Número de enemigos que va a generar
        Juego nuevaPartida = new Juego(cantidadEnemigos, 4); // Instancia de Juego con la cantidad de enemigos especificada arriba

     // Hilos para las actividades de enemigos y aliados
        for (int i=0; i < cantidadEnemigos; i++) {
                Thread enemigoThread = new Thread(new ActividadEnemiga(i, nuevaPartida)); 
                Thread aliadoThread = new Thread(new ActividadAliada(i, nuevaPartida));
                //Inicio los hilos
                enemigoThread.start();
                aliadoThread.start();
        }
    }
}
