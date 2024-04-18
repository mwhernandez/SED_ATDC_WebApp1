package com.sedapal.tramite.beans.estadisticas;

/**
 *
 * @author Admin
 */
public class EstadisticaDocEntrandaBean {
    private String nomArea;
    private String nomColumna;
    private String valColumna;
    private String mes;
    private String estado;
    private String cantidad;
    private int valor;

    /**
     * @return the nomArea
     */
    public String getNomArea() {
        return nomArea;
    }

    /**
     * @param nomArea the nomArea to set
     */
    public void setNomArea(String nomArea) {
        this.nomArea = nomArea;
    }

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
     * @return the valColumna
     */
    public String getValColumna() {
        return valColumna;
    }

    /**
     * @param valColumna the valColumna to set
     */
    public void setValColumna(String valColumna) {
        this.valColumna = valColumna;
    }

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCantidad() {
		return cantidad;
	}

	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

    

}
