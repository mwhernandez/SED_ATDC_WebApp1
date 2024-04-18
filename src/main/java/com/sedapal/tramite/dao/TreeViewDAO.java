package com.sedapal.tramite.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;

import com.sedapal.tramite.beans.Area;

public class TreeViewDAO {

	private JdbcTemplate jdbcTemplate;
	private static Logger logger = Logger.getLogger("R1");

	public List<Area> getDocumentos(String nroCorrelativo, String nanoLlave) {
		final List<Area> results = new ArrayList<Area>();
		String sql = "select d.ncorrelativo  "
			+ " as codigo,t.vdescripcion as descripcion from atd_doc_entr d "
			+ ",tipo t where d.vtipodoc=t.vcodtipo and t.vestado='A' and "
			+ " vobservaciones='TIPO DE DOCUMENTO ATD' and d.ncorrelativo="
			+ nroCorrelativo+ " and nano="+nanoLlave;
		System.out.println(sql);
		jdbcTemplate
				.query(sql, new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						Area bean = new Area();
						bean.setCodigo(rs.getString("codigo"));
						bean.setDescripcion(rs.getString("descripcion"));
						results.add(bean);
					}
				});
		return results;
	}

	public List<Area> getAreas(String nroCorrelativo, String nanoLlave) {
		int nano;
		nano = Integer.parseInt(nanoLlave);
		final List<Area> results = new ArrayList<Area>();
		jdbcTemplate.query(
				//"select d.ncodarea as codigo, "
				//+ "A.NCODAREA||' - ' ||upper(A.VABREVIATURA) "
				//+ "as descripcion from atd_doc_entr_dirigido d"
				//+ ", area a where d.ncodarea=a.ncodarea and d.nestado=1 and "
				//+ "A.NINDICADOR = 0 AND A.CTIPAREA IN  ('E ','G', 'D','P')"
				//+ "and d.ncorrelativo=" + nroCorrelativo,
				"select d.ncodarea as codigo, a.NCODAREA||' - ' ||upper(a.VABREVIATURA) as descripcion, upper(b.VABREVIATURA) as origen " +				
				"from atd_doc_entr_dirigido d, atd_doc_entr e, area a, area b " + 
				"where d.nano=e.nano and d.norigen=e.norigen and d.vtipodoc=e.vtipodoc and d.ncorrelativo=e.ncorrelativo and " +
				"d.ncodarea=a.ncodarea and d.nestado=1 and a.NINDICADOR = 0 AND a.CTIPAREA IN  ('E ','G', 'D','P','M') and " +
				"d.ncodarea_origen=b.ncodarea and b.NINDICADOR = 0 AND b.CTIPAREA IN  ('E ','G', 'D','P','M') AND d.ncorrelativo="
				+ nroCorrelativo+ " and d.nano=" + nano,
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						Area bean = new Area();
						bean.setCodigo(rs.getString("codigo"));
						bean.setDescripcion(rs.getString("descripcion"));
						bean.setOrigen(rs.getString("origen"));
						results.add(bean);
					}
				});
		return results;
	}
    //s.nareaderivado=t.ncodarea and
	public List<Area> getPersonas(String nroCorrelativo, String areaDerivado, String nanoLlave) {
		//System.out.println(nroCorrelativo);
		//System.out.println(areaDerivado);
		//System.out.println(nanoLlave);
		int nano;
		nano = Integer.parseInt(nanoLlave);
		System.out.println(nano);
		final List<Area> results = new ArrayList<Area>();
		jdbcTemplate
				.query("select s.nderivado as codigo,UPPER(t.VAPEPATERNO"
						+ " ||' '||t.VAPEMATERNO ||','||t.VNOMBRES) AS descripcion,e.vdescripcion as estado, s.dfeccre as fecha, s.nindicaarchivo as indicador"
						+ " from atd_doc_entr_seg s, trabajador t, tipo e where s.nderivado=t.nficha and s.vestado <> 'ESTA004' "
						+ "and t.vcodestado in ('EIRC01','EIRC03') and IND_EMAIL = 0 and s.vestado=e.vcodtipo and e.vobservaciones='ESTADO ATD' and s.ncorrelativo="
						+ nroCorrelativo + " and s.nano=" + nano + " and s.nareaderivado in ("+ areaDerivado + ",599) order by ncodseg",
						new RowCallbackHandler() {
							public void processRow(ResultSet rs)
									throws SQLException {
								Area bean = new Area();
								bean.setCodigo(rs.getString("codigo"));
								SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
								SimpleDateFormat formatoDelTexto2 = new SimpleDateFormat("dd/MM/yyyy - hh:mm:ss");
								bean.setEstado(rs.getString("estado"));	
								bean.setDescripcion(rs.getString("descripcion"));
								bean.setIndicador(rs.getInt("indicador"));
								 Date fecha = null;
								    try {

								        fecha = formatoDelTexto.parse(rs.getString("fecha"));			
								        bean.setFecha(formatoDelTexto2.format(fecha));
								     } catch (ParseException ex) {
								    	 logger.error("[TreeViewDAO.getPersonas]Date error",ex);						       

								    }						
								results.add(bean);
							}
						});
		return results;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}
