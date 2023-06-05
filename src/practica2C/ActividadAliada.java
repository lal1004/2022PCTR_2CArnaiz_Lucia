package practica2C;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ActividadAliada implements Runnable {

	private int tipoEnemigo;
	private IJuego juego;

	public ActividadAliada(int tipoEnemigo, IJuego juego) {
		this.tipoEnemigo = tipoEnemigo;
		this.juego = juego;
	}

	@Override
	public void run() {
		Random aleatorio = new Random();
		for (int i = 0; i < 15; i++) {
			try {
				juego.eliminarEnemigo(tipoEnemigo); //Elimina un enemigo 
				int tiempoSleep = aleatorio.nextInt(5) * 1000; // Genera un tiempo de pausa aleatorio (en milisegundos)
				TimeUnit.MILLISECONDS.sleep(tiempoSleep); // Pausa la ejecución del hilo durante el tiempo especificado arriba
			} catch (InterruptedException e) {
				Logger.getGlobal().log(Level.INFO, "Interrumpe la generación de enemigos");
				Logger.getGlobal().log(Level.INFO, e.toString());
				return;
			}
		}
	}
}
