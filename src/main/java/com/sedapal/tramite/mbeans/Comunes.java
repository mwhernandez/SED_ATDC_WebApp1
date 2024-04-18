package com.sedapal.tramite.mbeans;

public class Comunes {
	// Validación de si una cadena es un número
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
