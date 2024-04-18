package com.sedapal.tramite.beans.estadisticas;

/**
 *
 * @author Admin
 */
public class DatoGraficoBarraBean {

    private String nomColumna;
    private String nomMes;
    private String nomEstado;
    private Double montoColumna;
    private int cantidadColumna;
    private String cantidad;
   

    /**
     * @return the nomColumna
     */
    public String getNomColumna() {
        return nomColumna;
    }

    /**
     * @param nomColumna the nomColumna to set
     */
    public void setNomColumna(String nomColumna) {
        this.nomColumna = nomColumna;
    }

    /**
     * @return the montoColumna
     */
    public Double getMontoColumna() {
        return montoColumna;
    }

    /**
     * @param montoColumna the montoColumna to set
     */
    public void setMontoColumna(Double montoColumna) {
        this.montoColumna = montoColumna;
    }

	public int getCantidadColumna() {
		return cantidadColumna;
	}

	public void setCantidadColumna(int cantidadColumna) {
		this.cantidadColumna = cantidadColumna;
	}

	public String getNomMes() {
		return nomMes;
	}

	public void setNomMes(String nomMes) {
		this.nomMes = nomMes;
	}

	public String getNomEstado() {
		return nomEstado;
	}

	public void setNomEstado(String nomEstado) {
		this.nomEstado = nomEstado;
	}

	public String getCantidad() {
		return cantidad;
	}

	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}

	
    
    

}
