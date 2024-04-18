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
import com.sedapal.tramite.beans.TiposBean;
import com.sedapal.tramite.beans.Usuario;

public class ConsultaDAO implements IConsultaDAO{
	
	private JdbcTemplate jdbcTemplate;		
	private static Logger logger = Logger.getLogger("R1");	
	
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
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
    
	
	

	
	public List<TiposBean> consulta_anno() {
		final List<TiposBean> results = new ArrayList<TiposBean>();
		jdbcTemplate
				.query(	
						"SELECT VCODTIPO AS CODIGO, VDESCRIPCION AS NOMBRE  FROM SGEJ_TIPO WHERE VOBSERVACIONES='ESTADISTICAS_ANNO' AND VESTADO='A' ORDER BY VCODTIPO",
						new RowCallbackHandler() {
							public void processRow(ResultSet rs)
									throws SQLException {
								TiposBean bean = new TiposBean();	
								bean.setVcodtipo(rs.getString("nombre"));
								//bean.setVdescrip(rs.getString("nombre"));								
								results.add(bean);
							}
						});
		return results;
	}

	


	

	
	
}
