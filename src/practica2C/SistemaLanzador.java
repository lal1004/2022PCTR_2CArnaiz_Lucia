package practica2C;

public class SistemaLanzador {
	
	public  SistemaLanzador() {
		
	}

    public static void main(String[] args) {

        Juego nuevaPartida= new Juego(1);

        for (int i=0; i < Integer.parseInt(args[0]); i++) {
                Thread enemigoThread = new Thread(new ActividadEnemiga(i, nuevaPartida)); 
                enemigoThread.start();
                Thread aliadoThread = new Thread(new ActividadAliada(i, nuevaPartida));
                aliadoThread.start();
        }
    }
}
