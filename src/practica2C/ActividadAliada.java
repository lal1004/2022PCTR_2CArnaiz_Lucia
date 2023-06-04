package practica2C;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ActividadAliada implements Runnable {

	private int M;//número máximo de enemigos al mismo tiempo
	private int tipoEnemigo;
	private IJuego juego;
	
	public ActividadAliada(int tipoEnemigo,IJuego juego) {
		this.tipoEnemigo = tipoEnemigo;
		this.juego = juego;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Random aleatorio = new Random();
		for (int i = 0; i < M; i ++) {
			try {
				juego.eliminarEnemigo(tipoEnemigo);
				int tiempoSleep = aleatorio.nextInt(5) * 1000;
				TimeUnit.MILLISECONDS.sleep(tiempoSleep);
			}catch (InterruptedException e) {
				Logger.getGlobal().log(Level.INFO, "Interrumpe la generación de enemigos");
				Logger.getGlobal().log(Level.INFO, e.toString());
				return;
			}
		}
	}
}
