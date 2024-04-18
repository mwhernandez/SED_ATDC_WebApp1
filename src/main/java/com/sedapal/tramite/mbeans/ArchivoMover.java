package com.sedapal.tramite.mbeans;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ArchivoMover {
	
	final static Logger LOGGER = Logger.getAnonymousLogger();
	 
	 public static void moverArchivo(Path origin, Path destiny) {
	        try {
	            Files.move(origin, destiny, StandardCopyOption.REPLACE_EXISTING);
	        } catch (FileNotFoundException ex) {
	            LOGGER.log(Level.SEVERE, ex.getMessage());
	        } catch (IOException ex) {
	            LOGGER.log(Level.SEVERE, ex.getMessage()); 
	        }
	    }
	 
	 
	 
	 

}
