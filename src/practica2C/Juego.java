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
		
	}
	
	public synchronized void eliminarEnemigo(int tipoEnemigo) {
		
	}

	private void imprimirInfo(int tipoEnemigo, String cadena ) {
	
	}
	
	public int sumarContadores() {
		int contador = 0;
		
		Collection<Integer> enemigosCreados =contadoresEnemigosTipo.values();
		Collection<Integer> enemigosElminados =contadoresEliminadosTipo.values();
		
		for(Integer valorCreados : enemigosCreados) { // foreach
			contador += valorCreados;
		}
		
		for(Integer valorEliminado : enemigosElminados) {
			contador -= valorEliminado;
		}
		
		return contador;
	}
	
	protected void comprobarAntesDeGenerar(int tipoEnemigo) {
		
	}
	
	protected void comprobarAntesDeEliminar(int tipoEnemigo) {
		
	}
	
	protected void checkInvariante() {
		assert contadorEnemigosTotales < MINENEMIGOS : "No puede haber menos enemigos que el mínimo";
		assert contadorEnemigosTotales > MAXENEMIGOS : "No puede haber más enemigos que el máximo";
		
		int cantidadSumarContadores = sumarContadores();
		
		assert cantidadSumarContadores != contadorEnemigosTotales : "Los contadores no coinciden";   
	}
	
}
