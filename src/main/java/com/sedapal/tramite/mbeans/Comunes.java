package com.sedapal.tramite.mbeans;

public class Comunes {
	// Validaci�n de si una cadena es un n�mero
		public static boolean isNumeric(String cadena){
			try {
				//Integer.parseInt(cadena);
				Long.parseLong(cadena);
				return true;
			} catch (NumberFormatException nfe){		
				return false;
			}
		}
}
