package com.sedapal.tramite.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;

import com.sedapal.tramite.beans.AreaBean;
import com.sedapal.tramite.beans.TiposBean;


import com.sedapal.tramite.dao.StoredResumenMateriaExpedientes;

/**
 *
 * @author Admin
 */

public class EstadisticasDAO implements IEstadisticasDao  {
    
	private JdbcTemplate jdbcTemplate;
    private StoredResumenDocEntrantes storedResumenDocEntrantes;
    private StoredResumenMateriaExpedientes storedResumenMateriaExpedientes;
    private StoredResumenEstados storedresumenestados;
    private StoredResumenPorSituacionDoc storedResumenporSituacionDoc;
    
    
    @Override
	public List<TiposBean> consultaSP() {
		final List<TiposBean> results = new ArrayList<TiposBean>();
		jdbcTemplate
				.query(	
						"SELECT VCODTIPO AS CODIGO, VDESCRIPCION AS NOMBRE  FROM ATD_TIPO WHERE  VFLAT='EST' AND NESTADO=1 ORDER BY VCODTIPO",
						new RowCallbackHandler() {
							public void processRow(ResultSet rs)
									throws SQLException {
								TiposBean bean = new TiposBean();	
								bean.setVcodtipo(rs.getString("codigo"));
								bean.setVdescrip(rs.getString("nombre"));								
								results.add(bean);
							}
						});
		return results;
	}
	
	public List<AreaBean> areas(){    			
		
		final List<AreaBean> result = new ArrayList<AreaBean>();
		String sql="SELECT A.NCODAREA as codigo, upper(A.VABREVIATURA)||' - ' ||upper(A.VDESCRIPCION) as nombre "+
		"FROM AREA A WHERE A.NINDICADOR = 0 AND A.NESTADO IN (0,4) AND A.CTIPAREA IN  ('G') ORDER BY A.NCODAREA";
		
		jdbcTemplate.query(sql,new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						AreaBean bean1 = new AreaBean();
						bean1.setCodigo(rs.getInt("codigo"));
						bean1.setNombre(rs.getString("nombre"));
						result.add(bean1);
					}
				});        		
		return result;
	}
	
    

    public List getListadoResumenDocEntrada(String fechaIni, String fechaFin, int codArea) {
        Map output = getStoredResumenDocEntrantes().execute(fechaIni, fechaFin, codArea);
        List resultList = (ArrayList) output.get("resultList");
        return resultList;
    }
    
    public List getListadoResumenporsituacion(String fechaIni, String fechaFin, int codArea) {        
    	Map output = getStoredResumenporSituacionDoc().execute(fechaIni, fechaFin, codArea);
        List resultList = (ArrayList) output.get("resultList");
        return resultList;
    }
    
    
    public List getListadoResumenMateriaExpDoc(String fechaIni, String fechaFin, String tiporeporte, int ncodarea) {
    	System.out.println("queiro ver las fechas que paso al reporte");
    	System.out.println(fechaIni);
    	System.out.println(fechaFin);
    	Map output = getStoredResumenMateriaExpedientes().execute(fechaIni, fechaFin, tiporeporte, ncodarea);
    	List resultList = (ArrayList) output.get("resultList");    		         
        return resultList;	        
	    
    }
    
    
    public List getListadoResumenEstados(String fechaIni, String fechaFin, int ncodarea) {
        Map output = getStoredresumenestados().execute(fechaIni, fechaFin, ncodarea);
        List resultList = (ArrayList) output.get("resultList");
        return resultList;
    }
    
    
        
    public StoredResumenDocEntrantes getStoredResumenDocEntrantes() {
        return storedResumenDocEntrantes;
    }
   
    public void setStoredResumenDocEntrantes(StoredResumenDocEntrantes storedResumenDocEntrantes) {
        this.storedResumenDocEntrantes = storedResumenDocEntrantes;
    }

	public void setStoredResumenMateriaExpedientes(
			StoredResumenMateriaExpedientes storedResumenMateriaExpedientes) {
		this.storedResumenMateriaExpedientes = storedResumenMateriaExpedientes;
	}

	

	

	

	public StoredResumenMateriaExpedientes getStoredResumenMateriaExpedientes() {
		return storedResumenMateriaExpedientes;
	}


	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void setStoredresumenestados(StoredResumenEstados storedresumenestados) {
		this.storedresumenestados = storedresumenestados;
	}

	public StoredResumenEstados getStoredresumenestados() {
		return storedresumenestados;
	}

	public StoredResumenPorSituacionDoc getStoredResumenporSituacionDoc() {
		return storedResumenporSituacionDoc;
	}

	public void setStoredResumenporSituacionDoc(
			StoredResumenPorSituacionDoc storedResumenporSituacionDoc) {
		this.storedResumenporSituacionDoc = storedResumenporSituacionDoc;
	}

	
	
    
    

}
