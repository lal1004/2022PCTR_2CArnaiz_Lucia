package practica2C;

import java.util.*;

/**
 * Clase Juego. Es la clase principal.
 * @author Luc�a Arnaiz L�pez
 *
 */
public class Juego implements IJuego {

	private int contadorEnemigosTotales;
	private Hashtable<Integer, Integer> contadoresEnemigosTipo;
	private Hashtable<Integer, Integer> contadoresEliminadosTipo;
	private int MAXENEMIGOS;
	private int MINENEMIGOS = 0;

	public Juego(int MAXENEMIGOS, int numTipos) {

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

		checkInvariante();
		// Comprobamos que el enemigo existe antes de generarlo
		if (contadoresEnemigosTipo.containsKey(tipoEnemigo)) {
			int numEnemigos = contadoresEnemigosTipo.get(tipoEnemigo);
			contadoresEnemigosTipo.put(tipoEnemigo, numEnemigos + 1);
		} else {
			contadoresEnemigosTipo.put(tipoEnemigo, 1);
			contadoresEliminadosTipo.put(tipoEnemigo, 0); // Creamos tambien la parte de los que se eliminen
		}

		contadorEnemigosTotales++;
	
		
		imprimirInfo(tipoEnemigo, "Generado");
		notifyAll();
	}

	public synchronized void eliminarEnemigo(int tipoEnemigo) {
		// Comprobamos que el enemigo existe antes de eliminarlo
		comprobarAntesDeEliminar(tipoEnemigo); // si no se puede, esperar� el hilo
		checkInvariante();
		
		contadoresEnemigosTipo.put(tipoEnemigo, contadoresEnemigosTipo.get(tipoEnemigo) - 1);
        contadoresEliminadosTipo.put(tipoEnemigo, contadoresEliminadosTipo.get(tipoEnemigo) + 1);
        contadorEnemigosTotales--;

		imprimirInfo(tipoEnemigo, "Eliminado");
		notifyAll();

	}

	private void imprimirInfo(int tipoEnemigo, String cadena) {
		// Sacado de Parque adaptado para esta
		System.out.println(cadena + " enemigo tipo:" + tipoEnemigo);
		System.out.println("--> Enemigos totales " + contadorEnemigosTotales);

		// Iterar y mostrar la cantidad de enemigos y eliminados por cada tipo
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

		for (Integer valorCreados : enemigosCreados) { // Iteraci�n sobre la colecci�n de enemigos creados
			contador += valorCreados; // Suma del valor de cada enemigo creado al contador
		}

		for (Integer valorEliminado : enemigosElminados) { // Iteraci�n sobre la colecci�n de enemigos eliminados
			contador -= valorEliminado; // Resta dwel valor de cada enemigo eliminado al contador
		}

		return contador; 
	}

	protected void comprobarAntesDeGenerar(int tipoEnemigo) {
		int enemigoAnterior = tipoEnemigo - 1;

		// Comprobamos que el enemigo anterior haya sido creado (sino no se puede crear el siguiente)
		while (contadoresEnemigosTipo.containsKey(enemigoAnterior) == false || contadorEnemigosTotales >= MAXENEMIGOS || contadorEnemigosTotales <= 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	protected void comprobarAntesDeEliminar(int tipoEnemigo) {
		// Comprobamos que el enemigo que se quiere borrar de verdad existe
		while (contadoresEnemigosTipo.containsKey(tipoEnemigo) == false || contadoresEnemigosTipo.get(tipoEnemigo) <= 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	protected void checkInvariante() {
		assert contadorEnemigosTotales <= MINENEMIGOS : "No puede haber menos enemigos que el m�nimo"; // Verifica que el contador de enemigos totales no sea menor que el m�nimo permitido
		assert contadorEnemigosTotales > MAXENEMIGOS : "No puede haber m�s enemigos que el m�ximo"; // Verifica que el contador de enemigos totales no sea mayor que el m�ximo permitido


		int cantidadSumarContadores = sumarContadores(); // Obtiene la cantidad sumada de los contadores de enemigos

		assert cantidadSumarContadores != contadorEnemigosTotales : "Los contadores no coinciden"; // Verifica que la cantidad sumada de los contadores no coincida con el contador de enemigos totales
	}

}