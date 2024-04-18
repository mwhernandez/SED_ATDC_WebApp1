package com.sedapal.tramite.mbeans;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
 
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class javaIOUtils {
	
	/**
     * Method to <b>copy</b> a file from a source origin (<code>fromFile</code>)
     * to a destination(<code>toFile</code>). Método para <b>copiar</b> un
     * fichero desde un origen (<code>fromFile</code>) a un destino
     * (<code>toFile</code>).
     *
     * @param fromFile <code>String</code> source file path (ruta del fichero
     * origen).
     * @param toFile <code>String</code> destination file path (ruta del fichero
     * destino).
     * @return <code>boolean</code> It returns true if they could copy the file
     * false on error (devuelve true si se pude copiar el fichero false en caso
     * de error).
     */
    public boolean copyFile(String fromFile, String toFile) {
        File origin = new File(fromFile);
        File destination = new File(toFile);
        if (origin.exists()) {
            try {
                InputStream in = new FileInputStream(origin);
                OutputStream out = new FileOutputStream(destination);
                // We use a buffer for the copy (Usamos un buffer para la copia).
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                in.close();
                out.close();
                return true;
            } catch (IOException ioe) {
                ioe.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }
 
    public boolean moveFile(String fromFile, String toFile) {
	    File origin = new File(fromFile);
	    File destination = new File(toFile);
	    if (origin.exists()) {
	        try {
	            InputStream in = new FileInputStream(origin);
	            OutputStream out = new FileOutputStream(destination);
	            byte[] buf = new byte[1024];
	            int len;
	            while ((len = in.read(buf)) > 0) {
	                out.write(buf, 0, len);
	            }
	            in.close();
	            out.close();
	            return origin.delete();
	        } catch (IOException ioe) {
	            ioe.printStackTrace();
	            return false;
	        }
	    } else {
	        return false;
	    }
	}

}
