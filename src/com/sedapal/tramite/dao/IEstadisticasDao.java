package com.sedapal.tramite.dao;

import java.util.List;

import com.sedapal.tramite.beans.TiposBean;

/**
 *
 * @author Admin
 */
public interface IEstadisticasDao {

    
	public List getListadoResumenDocEntrada(String fechaIni, String fechaFin, int codArea);    
    
	public List getListadoResumenMateriaExpDoc(String fechaIni, String fechaFin, String tiporeporte, int vcodarea);

	public List<TiposBean> consultaSP();
	
    
    
    

}
