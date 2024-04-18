package com.sedapal.tramite.util;

import org.apache.log4j.Logger;

public class AspectLog
{
    public void log(Exception dataAccessEx) 
    {
        System.out.println("LOG AOP ERROR !!!!!");
        Logger logger = Logger.getLogger("R1");
        logger.error(dataAccessEx.getMessage());
		logger.error("LOG ERROR CAPTURADO POR AOP !!");
       
    }
}

