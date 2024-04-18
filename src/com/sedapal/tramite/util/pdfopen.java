package com.sedapal.tramite.util;

class pdfopen {

    public static void main(String args[]) //main function public static void principal (args String []) / / funci�n principal 
    {
        try //try statement try / / try 
        {
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + "d:\\documentos\\memo939-2010-ei.pdf"); //open the file chart.pdf (). Runtime.getRuntime exec ("rundll32 Url.dll, FileProtocolHandler" + "c: \ \ chart.pdf"); / / abrir el archivo chart.pdf 

        } catch (Exception e) //catch any exceptions here } Catch (Exception e) / / atrapar excepciones aqu� 
        {
            System.out.println("Error" + e); //print the error System.out.println ("Error" + e); / / imprimir el error 
        }
    }
}
