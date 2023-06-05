package practica2C;

import java.util.*;

public class Juego implements IJuego {

	private int contadorEnemigosTotales;
	private Hashtable<Integer, Integer> contadoresEnemigosTipo;
	private Hashtable<Integer, Integer> contadoresEliminadosTipo;
	private int MAXENEMIGOS;
	private int MINENEMIGOS = 0;

	public Juego(int MAXENEMIGOS) {

		// inicializar contadores y constantes
		this.MAXENEMIGOS = MAXENEMIGOS;
		this.contadorEnemigosTotales = 0;

		// inicializar los hashtable
		this.contadoresEnemigosTipo = new Hashtable<>();
		this.contadoresEliminadosTipo = new Hashtable<>();

	}

	public synchronized void generarEnemigo(int tipoEnemigo) {
		if (tipoEnemigo != 0) {
			// Comprobamos que se pueda generar el enemigo
			comprobarAntesDeGenerar(tipoEnemigo);
		}

		// Comprobamos que el enemigo existe antes de generarlo
		if (contadoresEnemigosTipo.containsKey(tipoEnemigo)) {
			int numEnemigos = contadoresEnemigosTipo.get(tipoEnemigo);
			contadoresEnemigosTipo.put(tipoEnemigo, numEnemigos + 1);
		} else {
			contadoresEnemigosTipo.put(tipoEnemigo, 1);
			contadoresEliminadosTipo.put(tipoEnemigo, 0); // Creamos tambien la parte de los que se eliminen
		}

		contadorEnemigosTotales++;

		checkInvariante();
		imprimirInfo(tipoEnemigo, "Generado");
		notifyAll();
	}

	public synchronized void eliminarEnemigo(int tipoEnemigo) {
		// Comprobamos que el enemigo existe antes de eliminarlo
		comprobarAntesDeEliminar(tipoEnemigo); // si no se puede, esperar� el hilo

		int numEnemigos = contadoresEliminadosTipo.get(tipoEnemigo);
		contadoresEliminadosTipo.put(tipoEnemigo, numEnemigos + 1);

		contadorEnemigosTotales--;

		checkInvariante();
		imprimirInfo(tipoEnemigo, "Eliminado");
		notifyAll();

	}

	private void imprimirInfo(int tipoEnemigo, String cadena) {
		// Sacado de Parque adaptado para esta
		System.out.println(cadena + " enemigo tipo:" + tipoEnemigo);
		System.out.println("--> Enemigos totales " + contadorEnemigosTotales);

		// Iteramos por todas las puertas e imprimimos sus entradas
		for (int p : contadoresEnemigosTipo.keySet()) {
			System.out.println("----> Enemigos tipo " + p + " : " + contadoresEnemigosTipo.get(p)
					+ "------ Eliminados :" + contadoresEliminadosTipo.get(p));
		}
		System.out.println(" ");

	}

	public int sumarContadores() {
		int contador = 0;

		Collection<Integer> enemigosCreados = contadoresEnemigosTipo.values();
		Collection<Integer> enemigosElminados = contadoresEliminadosTipo.values();

		for (Integer valorCreados : enemigosCreados) { // foreach
			contador += valorCreados;
		}

		for (Integer valorEliminado : enemigosElminados) {
			contador -= valorEliminado;
		}

		return contador;
	}

	protected void comprobarAntesDeGenerar(int tipoEnemigo) {
		int enemigoAnterior = tipoEnemigo - 1;

		// Comprobamos que el enemigo anterior haya sido creado (sino no se puede crear
		// el siguiente)
		while (contadoresEnemigosTipo.containsKey(enemigoAnterior) == false || contadorEnemigosTotales <= 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	protected void comprobarAntesDeEliminar(int tipoEnemigo) {
		// Comprobamos que el enemigo que se quiere borrar de verdad existe
		while (contadoresEnemigosTipo.containsKey(tipoEnemigo) == false || contadoresEliminadosTipo.get(tipoEnemigo) <= 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// Comprobamos que todav�a queden enemigos que se pueden eliminar
		/*while (contadoresEliminadosTipo.containsKey(tipoEnemigo) == false
				|| ) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}*/

	}

	protected void checkInvariante() {
		assert contadorEnemigosTotales < MINENEMIGOS : "No puede haber menos enemigos que el m�nimo";
		assert contadorEnemigosTotales > MAXENEMIGOS : "No puede haber m�s enemigos que el m�ximo";

		int cantidadSumarContadores = sumarContadores();

		assert cantidadSumarContadores != contadorEnemigosTotales : "Los contadores no coinciden";
	}

}
