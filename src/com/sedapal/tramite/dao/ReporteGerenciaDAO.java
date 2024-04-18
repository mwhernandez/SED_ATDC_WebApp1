package com.sedapal.tramite.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sedapal.tramite.beans.AreaBean;
import com.sedapal.tramite.beans.CentroBean;
import com.sedapal.tramite.beans.EntranteBean;
import com.sedapal.tramite.beans.EstadosBean;
import com.sedapal.tramite.beans.ReporteGerencialBean;
import com.sedapal.tramite.beans.SeguimientoEntranteBean;
import com.sedapal.tramite.beans.TipoConsulta;
import com.sedapal.tramite.beans.TiposBean;
import com.sedapal.tramite.beans.TiposDocumentosBean;
import com.sedapal.tramite.beans.Usuario;

public class ReporteGerenciaDAO implements ICentroDAO{
	
	private JdbcTemplate jdbcTemplate;
	private StoredCentros StoredCentros;
    private StoredBusquedaGerenciaPorTrabajador storedbusquedagerenciaportrabajador;
    private StoredBusquedaGerenciaPorEquipo storedbusquedagerenciaporequipo;
    private StoredBusquedaGerenciaPorGDI storedbusquedagerenciaporGDI;
    
    
	private static Logger logger = Logger.getLogger("R1");	
	
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
	public List<ReporteGerencialBean> BusquedaGerenciaPorTrabajador(int area, String fechaInicial, String fechaFinal, int ficha) {
		Map output = storedbusquedagerenciaportrabajador.execute(area, fechaInicial, fechaFinal, ficha);
		List gerencial = (ArrayList)output.get("reportegerenciaportrabajador");
		System.out.println("vuelvo de SP");
		System.out.println(gerencial);
		return gerencial;		
		
	}
	
	public List<ReporteGerencialBean> BusquedaGerenciaPorEquipo(int area, String fechaInicial, String fechaFinal) {
		Map output = storedbusquedagerenciaporequipo.execute(area, fechaInicial, fechaFinal);
		List gerencial = (ArrayList)output.get("reportegerenciaporequipo");
		System.out.println("vuelvo de SP");
		System.out.println(gerencial);
		return gerencial;		
		
	}
	
	public List<ReporteGerencialBean> BusquedaGerenciaPorGDI(int area, String fechaInicial, String fechaFinal) {
		Map output = storedbusquedagerenciaporGDI.execute(area, fechaInicial, fechaFinal);
		List gerencial = (ArrayList)output.get("reportegerencialgdi");
		System.out.println("vuelvo de SP");
		System.out.println(gerencial);
		return gerencial;		
		
	}
	
		
	public void setStoredbusquedagerenciaportrabajador(StoredBusquedaGerenciaPorTrabajador storedbusquedagerenciaportrabajador) {
		this.storedbusquedagerenciaportrabajador = storedbusquedagerenciaportrabajador;
	}
	
	


	public void setStoredbusquedagerenciaporequipo(StoredBusquedaGerenciaPorEquipo storedbusquedagerenciaporequipo) {
		this.storedbusquedagerenciaporequipo = storedbusquedagerenciaporequipo;
	}


	@Override
	public List<CentroBean> centroSP() {
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
		long nowIn =0, nowOut=0, nowDif =0;
		nowIn = System.currentTimeMillis();	
		Map output = StoredCentros.execute();
		nowOut = System.currentTimeMillis();
		nowDif = nowOut - nowIn;
		logger.debug("["+usuario+"]"+"Stored S_GUIA.ATDC_CENTRO Duracion:" + nowDif);
		List centros = (ArrayList)output.get("centro");
		return centros;
	}
	
	
	
	private String LPAD(String registro, int i, String string) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<TipoConsulta> getTiposConsulta()
	{
		String sql= "SELECT VCODTIPO, VDESCRIPCION FROM ATD_TIPO  WHERE VFLAT='GER' AND NESTADO=1 ORDER BY VCODTIPO";
		System.out.println(sql);
		final List<TipoConsulta> result = new ArrayList<TipoConsulta>();
		jdbcTemplate.query(sql,				
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						TipoConsulta consulta = new TipoConsulta();
						consulta.setTipo(rs.getString("VCODTIPO"));
						consulta.setDescripcion(rs.getString("VDESCRIPCION"));
						result.add(consulta);
					}
				});
		return result;
		
	}
	
	 public List<AreaBean> areas(){
	    	
			final List<AreaBean> result = new ArrayList<AreaBean>();
			String sql="SELECT A.NCODAREA as codigo, upper(A.VABREVIATURA)||' - ' ||upper(A.VDESCRIPCION) as nombre "+
			"FROM AREA A WHERE A.NINDICADOR = 0 AND A.NESTADO IN (0,4) AND A.CTIPAREA IN  ('E ','G', 'D','P') ORDER BY A.VABREVIATURA";
									
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
	 
	 public List<AreaBean> gerencias(){    			
			
			final List<AreaBean> result = new ArrayList<AreaBean>();
			
			
			String sql="SELECT A.NCODAREA as codigo, upper(A.VABREVIATURA)||' - ' ||upper(A.VDESCRIPCION) as nombre "+
			"FROM AREA A WHERE A.NINDICADOR = 0 AND A.NESTADO IN (0,4) AND A.CTIPAREA IN  ('G') UNION "+
			"SELECT 0 codigo, 'SELECCIONAR' nombre FROM DUAL";
			
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

	public List<TiposDocumentosBean> tipo_doc(){
    	long nowIn =0, nowOut=0, nowDif =0;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
        nowIn = System.currentTimeMillis();
        logger.debug("Inicio ENTRANTEDAO AREA:" + nowIn);
		final List<TiposDocumentosBean> result = new ArrayList<TiposDocumentosBean>();
		
		//String sql="SELECT A.NCODAREA as codigo, A.NCODAREA||' - ' ||upper(A.VABREVIATURA) as nombre "+
		//"FROM AREA A WHERE A.NINDICADOR = 0 AND A.CTIPAREA NOT IN ('SE', 'CC') ORDER BY A.NCODAREA";
		
		String sql="SELECT VOBSERVACIONES as codigo, upper(VDESCRIPCION) as nombre "+
		"FROM ATD_TIPO A WHERE VFLAT='DOC' AND NESTADO=1 ORDER BY VCODTIPO";
		
		logger.debug("[EntranteDAO.areas]["+usuario+"]"+sql);
		Date inicio = new Date();
		logger.debug("[EntranteDAO.areas]["+usuario+"]Inicio sql:" + inicio);
		jdbcTemplate.query(sql,new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						TiposDocumentosBean bean1 = new TiposDocumentosBean();
						bean1.setCodigo(rs.getString("codigo"));
						bean1.setDescripcion(rs.getString("nombre"));						
						result.add(bean1);
					}
				});
        nowOut = System.currentTimeMillis();
        logger.debug("[EntranteDAO.areas]["+usuario+"]"+"Final ENTRANTEDAO TIPO DOC:" + nowOut);
        nowDif = nowOut - nowIn;
        logger.debug("[EntranteDAO.areas]["+usuario+"]"+"Duracion ENTRANTEDAO TIPO DOC:" + nowDif);		
		return result;
	}
	
	
	
	
	public List<EstadosBean> estados(){
    	long nowIn =0, nowOut=0, nowDif =0;
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
        nowIn = System.currentTimeMillis();
        //logger.debug("Inicio ENTRANTEDAO ESTADO :" + nowIn);
		final List<EstadosBean> results6 = new ArrayList<EstadosBean>();
		
		jdbcTemplate.query(
				
				"SELECT VCODTIPO AS codigo,VDESCRIPCION AS descripcion FROM TIPO WHERE VOBSERVACIONES='ESTADO ATD' AND VESTADO='A'",
				
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						EstadosBean bean6 = new EstadosBean();
						bean6.setCodigo(rs.getString("codigo"));
						bean6.setDescripcion(rs.getString("descripcion"));						
						results6.add(bean6);
					}
				});
		nowOut = System.currentTimeMillis();
	    //logger.debug("["+usuario+"]"+"Final ENTRANTEDAO ESTADO:" + nowOut);
	    nowDif = nowOut - nowIn;
	    //logger.debug("["+usuario+"]"+"Duracion ENTRANTEDAO ESTADO:" + nowDif);
		return results6;
	}
	
	 public List<TiposBean> tipos_salida(){
	    	long nowIn =0, nowOut=0, nowDif =0;
	    	int area;
	    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
			Usuario usuario = null;
			usuario=(Usuario)session.getAttribute("sUsuario");
			area= usuario.getCodarea();
	        nowIn = System.currentTimeMillis();
	        //logger.debug("Inicio SALIDADAO TIPOS:" + nowIn);
			final List<TiposBean> results = new ArrayList<TiposBean>();
			jdbcTemplate.query(					
					"SELECT VCODTIPO CODIGO,VDESCRIPCION DESCRIPCION FROM ATD_TIPO_DOC_AREA WHERE NCODAREA=1 " +
					"UNION SELECT VCODTIPO CODIGO,VDESCRIPCION DESCRIPCION FROM ATD_TIPO_DOC_AREA WHERE NESTADO=1 AND NCODAREA="+area+" ",
					new RowCallbackHandler() {
						public void processRow(ResultSet rs) throws SQLException {
							TiposBean bean2 = new TiposBean();
							bean2.setTipo(rs.getString("codigo"));
							bean2.setDescripcion(rs.getString("descripcion"));
							results.add(bean2);
						}
					});
			nowOut = System.currentTimeMillis();
		    //logger.debug("["+usuario+"]"+"Final SALIDADAO TIPOS:" + nowOut);
		    nowDif = nowOut - nowIn;
		    //logger.debug("["+usuario+"]"+"Duracion SALIDADAO TIPOS:" + nowDif);
			return results;		
		}
	

	

	public void setStoredCentro(StoredCentros storedCentros) {
		this.StoredCentros = storedCentros;
	}

	@Override
	public List<CentroBean> centros() {
		// TODO Auto-generated method stub
		return null;
	}


	public void setStoredbusquedagerenciaporGDI(
			StoredBusquedaGerenciaPorGDI storedbusquedagerenciaporGDI) {
		this.storedbusquedagerenciaporGDI = storedbusquedagerenciaporGDI;
	}

	
	

	
	
	


	

	
	
}
