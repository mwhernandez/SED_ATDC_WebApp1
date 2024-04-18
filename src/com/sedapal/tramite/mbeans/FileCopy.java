package com.sedapal.tramite.mbeans;
import java.io.*;

public class FileCopy {
    public FileCopy(String sourceFile, String destinationFile) {
        System.out.println("Desde: " + sourceFile);
        System.out.println("Hacia: " + destinationFile);

        try {
            File inFile = new File(sourceFile);
            File outFile = new File(destinationFile);

            FileInputStream in = new FileInputStream(inFile);
            FileOutputStream out = new FileOutputStream(outFile);

            int c;
            while( (c = in.read() ) != -1)
                out.write(c);

            in.close();
            out.close();
        } catch(IOException e) {
            System.err.println("Hubo un error de entrada/salida!!!");
        }
    }

    public static void main(String args[]) {
        if(args.length == 2)
            new FileCopy(args[0], args[1]);
        else
            System.out.println("Debe ingresar dos parametros");
    }
}
