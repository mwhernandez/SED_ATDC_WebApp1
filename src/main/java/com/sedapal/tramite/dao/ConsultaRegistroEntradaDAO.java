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

import com.sedapal.tramite.beans.AnioBean;
import com.sedapal.tramite.beans.AreaBean;
import com.sedapal.tramite.beans.CentroBean;
import com.sedapal.tramite.beans.EntranteBean;
import com.sedapal.tramite.beans.SeguimientoEntranteBean;
import com.sedapal.tramite.beans.TiposDocumentosBean;
import com.sedapal.tramite.beans.Usuario;

public class ConsultaRegistroEntradaDAO implements ICentroDAO {

	private JdbcTemplate jdbcTemplate;
	private StoredCentros StoredCentros;
	private StoredBusquedaRegDocEntrantes Storedbusquedaregdocentrantes;
	private StoredBusquedaRegEntrantesSeg Storedbusquedaregentrantesseg;
	private StoredBusquedaDocumentosExternos storedbusquedadocumentosexternos;
	private static Logger logger = Logger.getLogger("R1");

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<CentroBean> centroSP() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		long nowIn = 0, nowOut = 0, nowDif = 0;
		nowIn = System.currentTimeMillis();
		Map output = StoredCentros.execute();
		nowOut = System.currentTimeMillis();
		nowDif = nowOut - nowIn;
		logger.debug("[" + usuario + "]"
				+ "Stored S_GUIA.ATDC_CENTRO Duracion:" + nowDif);
		List centros = (ArrayList) output.get("centro");
		return centros;
	}
	
	public List<EntranteBean> BusquedaSP(String opcion, String detalle, String tipo, String nano) {
		
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");               
		Map output = storedbusquedadocumentosexternos.execute(opcion, detalle, tipo, nano);		
		List busqueda = (ArrayList)output.get("busquedas");		
		return busqueda;
	}
	

	public List<EntranteBean> BusquedaRegistroSP(String valor) {

		
		final List<EntranteBean> result = new ArrayList<EntranteBean>();
		String sql = " SELECT A.NANO, A.NORIGEN, (CASE A.NORIGEN       WHEN 1 THEN 'INTERNO'  WHEN 2 THEN 'EXTERNO' END) ORIGEN, "
				+ " A.VTIPODOC, T.VDESCRIPCION AS TIPODOC, A.NCORRELATIVO, A.VNUMDOC,  A.VMESAPARTE, A.NREMITENTE, A.VREMITENTE, "
				+ " A.DFECDOC, A.DFECREGISTRO, A.NDIRIGIDO, UPPER(D.VDESCRIPCION) AS AREADIRIGIDO, A.VASUNTO, A.VREFERENCIA, A.VOBSERVACION, "
				+ " A.VPRIORIDAD, A.VACCION, A.VUBIARCHIVO, A.DFECPLAZO, A.NDIASPLAZO, A.VESTADO, UPPER(E.VDESCRIPCION) AS ESTADO, A.NINDICADOR, "
				+ " A.DFECCRE, A.DFECACT, A.VRESCRE, A.VRESACT, A.NCODAREA, UPPER(R.VDESCRIPCION) AS AREA, A.VUBIARCHIVO, A.NFICHA_DIRIGIDO, A.NCODAREA_ORIGEN, "
				+ " UPPER(O.VDESCRIPCION) AS AREAORIGEN, A.NCODCENTRO, UPPER(C.VNOMBRE) AS CENTRO FROM ATD_DOC_ENTR A, TIPO T, ATD_REMITENTE D, TIPO E, "
				+ " AREA R, AREA O, CENTRO C "
				+ " WHERE A.VTIPODOC=T.VCODTIPO AND T.VOBSERVACIONES ='TIPO DE DOCUMENTO ATD' AND  T.VESTADO = 'A' AND A.NDIRIGIDO=D.NCODREMITENTE AND "
				+ " D.VESTADO='A' AND A.VESTADO=E.VCODTIPO AND E.VOBSERVACIONES ='ESTADO ATD' AND E.VESTADO = 'A' AND A.NCODAREA=R.NCODAREA AND "
				+ " R.CTIPAREA IN  ('E ','G', 'D','P','M') AND R.NINDICADOR=0 AND  A.NCODAREA_ORIGEN=O.NCODAREA AND O.CTIPAREA IN  ('E ','G', 'D','P','M') AND "
				+ " O.NINDICADOR=0 AND A.NCODCENTRO=C.NCODCENTRO AND  C.NESTADO=1 AND A.VESTADO<>'ESTA004' AND  A.VNUMDOC='"
				+ valor
				+ "' "
				+ " UNION   SELECT A.NANO, A.NORIGEN,  (CASE A.NORIGEN WHEN 1 THEN 'INTERNO' WHEN 2 THEN 'EXTERNO' END) ORIGEN, A.VTIPODOC, T.VDESCRIPCION AS TIPODOC, "
				+ " A.NCORRELATIVO, A.VNUMDOC, A.VMESAPARTE, A.NREMITENTE, A.VREMITENTE, A.DFECDOC, A.DFECREGISTRO, A.NDIRIGIDO AS NDIRIGIDO, UPPER(D.VDESCRIPCION) AS AREADIRIGIDO, "
				+ " A.VASUNTO, A.VREFERENCIA, A.VOBSERVACION, A.VPRIORIDAD,  A.VACCION, A.VUBIARCHIVO, A.DFECPLAZO, A.NDIASPLAZO, A.VESTADO, UPPER(E.VDESCRIPCION) AS ESTADO, "
				+ " A.NINDICADOR, A.DFECCRE, A.DFECACT, A.VRESCRE, A.VRESACT, A.NCODAREA, UPPER(R.VDESCRIPCION) AS AREA, A.VUBIARCHIVO, A.NFICHA_DIRIGIDO, A.NCODAREA_ORIGEN, "
				+ " UPPER(O.VDESCRIPCION) AS AREAORIGEN, A.NCODCENTRO, UPPER(C.VNOMBRE) AS CENTRO FROM ATD_DOC_ENTR A, TIPO T, AREA D, TIPO E,  AREA R, AREA O, CENTRO C "
				+ " WHERE A.VTIPODOC=T.VCODTIPO AND T.VOBSERVACIONES ='TIPO DE DOCUMENTO ATD' AND T.VESTADO = 'A' AND A.NDIRIGIDO=D.NCODAREA AND D.CTIPAREA IN  ('E ','G', 'D','P') AND "
				+ " D.NINDICADOR=0 AND A.VESTADO=E.VCODTIPO AND E.VOBSERVACIONES ='ESTADO ATD' AND E.VESTADO = 'A' AND A.NCODAREA=R.NCODAREA AND R.CTIPAREA IN  ('E ','G', 'D','P','M') AND "
				+ " R.NINDICADOR=0 AND A.NCODAREA_ORIGEN=O.NCODAREA AND O.CTIPAREA IN  ('E ','G', 'D','P','M') AND O.NINDICADOR=0 AND  A.NCODCENTRO=C.NCODCENTRO AND C.NESTADO=1 AND "
				+ " A.VESTADO<>'ESTA004' AND A.VNUMDOC= '" + valor + "' ";
		Date inicio = new Date();

		jdbcTemplate.query(sql, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				EntranteBean bean = new EntranteBean();
				bean.setNano(rs.getInt("nano"));
				bean.setNorigen(rs.getInt("norigen"));
				bean.setOrigen(rs.getString("origen"));
				bean.setVtipodoc(rs.getString("vtipodoc"));
				bean.setTipodoc(rs.getString("tipodoc"));
				bean.setNcorrelativo(rs.getInt("ncorrelativo"));
				bean.setVnumdoc(rs.getString("vnumdoc"));
				bean.setVmesaparte(rs.getString("vmesaparte"));
				bean.setNremitente(rs.getInt("nremitente"));
				bean.setVremitente(rs.getString("vremitente"));
				bean.setDfecdoc(rs.getDate("dfecdoc"));
				bean.setDfecregistro(rs.getDate("dfecregistro"));
				bean.setNdirigido(rs.getInt("ndirigido"));
				bean.setVdirigido(rs.getString("areadirigido"));
				bean.setVasunto(rs.getString("vasunto"));
				bean.setVreferencia(rs.getString("vreferencia"));
				bean.setVobservacion(rs.getString("vobservacion"));
				bean.setVprioridad(rs.getString("vprioridad"));
				bean.setVaccion(rs.getString("vaccion"));
				// bean.setAccion(rs.getString("accion"));
				bean.setVubiarchivo(rs.getString("vubiarchivo"));
				bean.setDfecplazo(rs.getDate("dfecplazo"));
				bean.setNdiasplazo(rs.getInt("ndiasplazo"));
				bean.setNestado(rs.getString("vestado"));
				bean.setVestado(rs.getString("estado"));
				bean.setDfeccre(rs.getDate("dfeccre"));
				bean.setDfecact(rs.getDate("dfecact"));
				bean.setVrescre(rs.getString("vrescre"));
				bean.setVresact(rs.getString("vresact"));
				bean.setNcodarea(rs.getInt("ncodarea"));
				bean.setArea(rs.getString("area"));
				// bean.setFicha_dirigido(rs.getLong("nficha_dirigido"));
				bean.setFicha_dirigido(rs.getLong("nremitente"));
				bean.setNcodarea_origen(rs.getInt("ncodarea_origen"));
				bean.setArea_origen(rs.getString("areaorigen"));
				bean.setNcodcentro(rs.getInt("ncodcentro"));
				bean.setCentro(rs.getString("centro"));
				bean.setNindicador(rs.getInt("nindicador"));
				result.add(bean);
			}
		});
		return result;
	}

	public List<EntranteBean> BusquedaRegistroExterno(String vtipodoc, String nano, String vnumdoc) {
		
		int ano = Integer.valueOf(nano);
		final List<EntranteBean> result = new ArrayList<EntranteBean>();
		
		
		String sql = " SELECT A.NANO, A.NORIGEN, (CASE A.NORIGEN WHEN 1 THEN 'INTERNO'  WHEN 2 THEN 'EXTERNO' END) ORIGEN, "
				+ " A.VTIPODOC, T.VDESCRIPCION AS TIPODOC, A.NCORRELATIVO, A.VNUMDOC,  A.VMESAPARTE, A.NREMITENTE, A.VREMITENTE, "
				+ " A.DFECDOC, A.DFECREGISTRO, A.NDIRIGIDO, UPPER(D.VDESCRIPCION) AS AREADIRIGIDO, A.VASUNTO, A.VREFERENCIA, A.VOBSERVACION, "
				+ " A.VPRIORIDAD, A.VACCION, A.VUBIARCHIVO, A.DFECPLAZO, A.NDIASPLAZO, A.VESTADO, UPPER(E.VDESCRIPCION) AS ESTADO, A.NINDICADOR, "
				+ " A.DFECCRE, A.DFECACT, A.VRESCRE, A.VRESACT, A.NCODAREA, UPPER(R.VDESCRIPCION) AS AREA, A.VUBIARCHIVO, A.NFICHA_DIRIGIDO, A.NCODAREA_ORIGEN, "
				+ " UPPER(O.VDESCRIPCION) AS AREAORIGEN, A.NCODCENTRO, UPPER(C.VNOMBRE) AS CENTRO FROM ATD_DOC_ENTR A, TIPO T, ATD_REMITENTE D, TIPO E, "
				+ " AREA R, AREA O, CENTRO C "
				+ " WHERE A.VTIPODOC=T.VCODTIPO AND T.VOBSERVACIONES ='TIPO DE DOCUMENTO ATD' AND  T.VESTADO = 'A' AND A.NDIRIGIDO=D.NCODREMITENTE AND "
				+ " D.VESTADO='A' AND A.VESTADO=E.VCODTIPO AND E.VOBSERVACIONES ='ESTADO ATD' AND E.VESTADO = 'A' AND A.NCODAREA=R.NCODAREA AND "
				+ " R.CTIPAREA IN  ('E ','G', 'D','P','M') AND R.NINDICADOR=0 AND  A.NCODAREA_ORIGEN=O.NCODAREA AND O.CTIPAREA IN  ('E ','G', 'D','P','M') AND  "
				+ " O.NINDICADOR=0 AND A.NCODCENTRO=C.NCODCENTRO AND  A.NORIGEN=2 AND C.NESTADO=1 AND A.VESTADO<>'ESTA004' AND A.NANO='"+ano+"' AND "
				+ "A.VTIPODOC='" +vtipodoc+"' AND A.VNUMDOC LIKE '%" + vnumdoc + "%'";
		
		
		Date inicio = new Date();

		jdbcTemplate.query(sql, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				EntranteBean bean = new EntranteBean();
				bean.setNano(rs.getInt("nano"));
				bean.setNorigen(rs.getInt("norigen"));
				bean.setOrigen(rs.getString("origen"));
				bean.setVtipodoc(rs.getString("vtipodoc"));
				bean.setTipodoc(rs.getString("tipodoc"));
				bean.setNcorrelativo(rs.getInt("ncorrelativo"));
				bean.setVnumdoc(rs.getString("vnumdoc"));
				bean.setVmesaparte(rs.getString("vmesaparte"));
				bean.setNremitente(rs.getInt("nremitente"));
				bean.setVremitente(rs.getString("vremitente"));
				bean.setDfecdoc(rs.getDate("dfecdoc"));
				bean.setDfecregistro(rs.getDate("dfecregistro"));
				bean.setNdirigido(rs.getInt("ndirigido"));
				bean.setVdirigido(rs.getString("areadirigido"));
				bean.setVasunto(rs.getString("vasunto"));
				bean.setVreferencia(rs.getString("vreferencia"));
				bean.setVobservacion(rs.getString("vobservacion"));
				bean.setVprioridad(rs.getString("vprioridad"));
				bean.setVaccion(rs.getString("vaccion"));
				// bean.setAccion(rs.getString("accion"));
				bean.setVubiarchivo(rs.getString("vubiarchivo"));
				bean.setDfecplazo(rs.getDate("dfecplazo"));
				bean.setNdiasplazo(rs.getInt("ndiasplazo"));
				bean.setNestado(rs.getString("vestado"));
				bean.setVestado(rs.getString("estado"));
				bean.setDfeccre(rs.getDate("dfeccre"));
				bean.setDfecact(rs.getDate("dfecact"));
				bean.setVrescre(rs.getString("vrescre"));
				bean.setVresact(rs.getString("vresact"));
				bean.setNcodarea(rs.getInt("ncodarea"));
				bean.setArea(rs.getString("area"));
				// bean.setFicha_dirigido(rs.getLong("nficha_dirigido"));
				bean.setFicha_dirigido(rs.getLong("nremitente"));
				bean.setNcodarea_origen(rs.getInt("ncodarea_origen"));
				bean.setArea_origen(rs.getString("areaorigen"));
				bean.setNcodcentro(rs.getInt("ncodcentro"));
				bean.setCentro(rs.getString("centro"));
				bean.setNindicador(rs.getInt("nindicador"));
				result.add(bean);
			}
		});
		return result;
	}
	
		public List<EntranteBean> BusquedaRegistroExternoPorSeguimiento(String vtipodoc, String nano, String vnumdoc) {
		
		int ano = Integer.valueOf(nano);
		String numeroanno = vnumdoc + nano ;
		final List<EntranteBean> result = new ArrayList<EntranteBean>();
		
		
		String sql = " SELECT A.NANO, A.NORIGEN, (CASE A.NORIGEN WHEN 1 THEN 'INTERNO'  WHEN 2 THEN 'EXTERNO' END) ORIGEN, "
				+ " A.VTIPODOC, T.VDESCRIPCION AS TIPODOC, A.NCORRELATIVO, A.VNUMDOC,  A.VMESAPARTE, A.NREMITENTE, A.VREMITENTE, "
				+ " A.DFECDOC, A.DFECREGISTRO, A.NDIRIGIDO, UPPER(D.VDESCRIPCION) AS AREADIRIGIDO, A.VASUNTO, A.VREFERENCIA, A.VOBSERVACION, "
				+ " A.VPRIORIDAD, A.VACCION, A.VUBIARCHIVO, A.DFECPLAZO, A.NDIASPLAZO, A.VESTADO, UPPER(E.VDESCRIPCION) AS ESTADO, A.NINDICADOR, "
				+ " A.DFECCRE, A.DFECACT, A.VRESCRE, A.VRESACT, A.NCODAREA, UPPER(R.VDESCRIPCION) AS AREA, A.VUBIARCHIVO, A.NFICHA_DIRIGIDO, A.NCODAREA_ORIGEN, "
				+ " UPPER(O.VDESCRIPCION) AS AREAORIGEN, A.NCODCENTRO, UPPER(C.VNOMBRE) AS CENTRO FROM ATD_DOC_ENTR A, ATD_DOC_ENTR_SEG S, TIPO T, ATD_REMITENTE D, TIPO E, "
				+ " AREA R, AREA O, CENTRO C WHERE A.VTIPODOC=T.VCODTIPO AND T.VOBSERVACIONES ='TIPO DE DOCUMENTO ATD' AND "  
				+ " T.VESTADO = 'A' AND A.NDIRIGIDO=D.NCODREMITENTE AND D.VESTADO='A' AND A.VESTADO=E.VCODTIPO AND E.VOBSERVACIONES ='ESTADO ATD' AND " 
				+ " E.VESTADO = 'A' AND A.NCODAREA=R.NCODAREA AND R.CTIPAREA IN  ('E ','G', 'D','P','M') AND R.NINDICADOR=0 AND  A.NCODAREA_ORIGEN=O.NCODAREA AND "
				+ " O.CTIPAREA IN  ('E ','G', 'D','P','M') AND O.NINDICADOR=0 AND A.NCODCENTRO=C.NCODCENTRO AND  A.NORIGEN=2 AND C.NESTADO=1 AND A.VESTADO<>'ESTA004' AND "
				+ " A.NANO= S.NANO AND A.NORIGEN=S.NORIGEN AND A.NCORRELATIVO=S.NCORRELATIVO AND S.NANO='"+ano+"' AND S.VOBSERVACION LIKE '%" + numeroanno + "%' ORDER BY A.NCORRELATIVO";
		
			
		
		Date inicio = new Date();

		jdbcTemplate.query(sql, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				EntranteBean bean = new EntranteBean();
				bean.setNano(rs.getInt("nano"));
				bean.setNorigen(rs.getInt("norigen"));
				bean.setOrigen(rs.getString("origen"));
				bean.setVtipodoc(rs.getString("vtipodoc"));
				bean.setTipodoc(rs.getString("tipodoc"));
				bean.setNcorrelativo(rs.getInt("ncorrelativo"));
				bean.setVnumdoc(rs.getString("vnumdoc"));
				bean.setVmesaparte(rs.getString("vmesaparte"));
				bean.setNremitente(rs.getInt("nremitente"));
				bean.setVremitente(rs.getString("vremitente"));
				bean.setDfecdoc(rs.getDate("dfecdoc"));
				bean.setDfecregistro(rs.getDate("dfecregistro"));
				bean.setNdirigido(rs.getInt("ndirigido"));
				bean.setVdirigido(rs.getString("areadirigido"));
				bean.setVasunto(rs.getString("vasunto"));
				bean.setVreferencia(rs.getString("vreferencia"));
				bean.setVobservacion(rs.getString("vobservacion"));
				bean.setVprioridad(rs.getString("vprioridad"));
				bean.setVaccion(rs.getString("vaccion"));
				// bean.setAccion(rs.getString("accion"));
				bean.setVubiarchivo(rs.getString("vubiarchivo"));
				bean.setDfecplazo(rs.getDate("dfecplazo"));
				bean.setNdiasplazo(rs.getInt("ndiasplazo"));
				bean.setNestado(rs.getString("vestado"));
				bean.setVestado(rs.getString("estado"));
				bean.setDfeccre(rs.getDate("dfeccre"));
				bean.setDfecact(rs.getDate("dfecact"));
				bean.setVrescre(rs.getString("vrescre"));
				bean.setVresact(rs.getString("vresact"));
				bean.setNcodarea(rs.getInt("ncodarea"));
				bean.setArea(rs.getString("area"));
				// bean.setFicha_dirigido(rs.getLong("nficha_dirigido"));
				bean.setFicha_dirigido(rs.getLong("nremitente"));
				bean.setNcodarea_origen(rs.getInt("ncodarea_origen"));
				bean.setArea_origen(rs.getString("areaorigen"));
				bean.setNcodcentro(rs.getInt("ncodcentro"));
				bean.setCentro(rs.getString("centro"));
				bean.setNindicador(rs.getInt("nindicador"));
				result.add(bean);
			}
		});
		return result;
	}
	
	
	public List<EntranteBean> BusquedaRegistroExternoSeguimiento(String vtipodoc, String nano, String vnumdoc) {
		
		int ano = Integer.valueOf(nano);		

		final List<EntranteBean> result = new ArrayList<EntranteBean>();		
		
		String sql = " SELECT  DISTINCT A.NANO AS nano, S.NCODSEG,A.NCORRELATIVO as ncorrelativo, A.NANO, A.NORIGEN as norigen, (CASE A.NORIGEN WHEN 1 THEN 'INTERNO' WHEN 2 THEN 'EXTERNO'END) as origen, "
				+ " S.VTIPODOC as vtipodoc, (SELECT T.VDESCRIPCION FROM TIPO T WHERE T.VCODTIPO=S.VTIPODOC) as tipodoc, S.VOBSERVACION, A.VNUMDOC as vnumdoc, A.VMESAPARTE, "
				+ " A.NREMITENTE as nremitente, A.VREMITENTE as vremitente, A.DFECDOC as dfecdoc,A.DFECREGISTRO as dfecregistro, A.VOBSERVACION as vobservacion, "
				+ " A.NDIRIGIDO as ndirigido, CASE WHEN A.NORIGEN = 1 THEN (SELECT  UPPER(D.VDESCRIPCION) FROM AREA D WHERE  D.NCODAREA = A.NDIRIGIDO ) ELSE "
				+ " (SELECT UPPER(D.VDESCRIPCION) FROM ATD_REMITENTE D WHERE  D.NCODREMITENTE  = A.NDIRIGIDO) END areadirigido, A.VASUNTO as vasunto, A.VREFERENCIA as vreferencia, "
				+ " A.VPRIORIDAD as vprioridad, A.VACCION as vaccion, A.VUBIARCHIVO as vubiarchivo, A.DFECPLAZO as dfecplazo, A.NDIASPLAZO, S.VESTADO vestado, "
				+ " (SELECT UPPER(E.VDESCRIPCION)  FROM TIPO E WHERE E.VCODTIPO  = S.VESTADO) ESTADO, "
				+ " A.NINDICADOR, A.DFECCRE as dfeccre, A.DFECACT as dfecact, A.VRESCRE as vrescre, A.VRESACT as vresact, A.NCODAREA, (SELECT UPPER(R.VDESCRIPCION) FROM AREA R WHERE R.NCODAREA = A.NCODAREA) as area, "
				+ " A.VUBIARCHIVO, A.NFICHA_DIRIGIDO FROM ATD_DOC_ENTR A, ATD_DOC_ENTR_SEG S, ATD_TIPO E, TIPO T, CENTRO C, AREA R, AREA O WHERE A.VTIPODOC=T.VCODTIPO AND "
				+ " A.NCODCENTRO = C.NCODCENTRO AND A.NCODAREA=R.NCODAREA AND A.NCODAREA_ORIGEN=O.NCODAREA AND A.VESTADO=E.VCODTIPO AND E.VFLAT='ENT' AND A.NANO = S.NANO  AND "
				+ " A.NORIGEN= S.NORIGEN AND A.NCORRELATIVO= S.NCORRELATIVO AND A.VESTADO  <>  'ESTA004'  AND S.VESTADO  <>  'ESTA004'  AND A.NCODAREA = S.NAREADERIVADO AND "
				+ " S.NANO='"+ano+"' AND S.VTIPODOC='" +vtipodoc+"' AND S.VOBSERVACION LIKE '%" + vnumdoc + "%'";
				
				
		
		Date inicio = new Date();

		jdbcTemplate.query(sql, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				EntranteBean bean = new EntranteBean();
				bean.setNano(rs.getInt("nano"));
				bean.setNorigen(rs.getInt("norigen"));
				bean.setOrigen(rs.getString("origen"));
				bean.setVtipodoc(rs.getString("vtipodoc"));
				bean.setTipodoc(rs.getString("tipodoc"));
				bean.setNcorrelativo(rs.getInt("ncorrelativo"));
				bean.setVnumdoc(rs.getString("vnumdoc"));
				//bean.setVmesaparte(rs.getString("vmesaparte"));
				bean.setNremitente(rs.getInt("nremitente"));
				bean.setVremitente(rs.getString("vremitente"));
				bean.setDfecdoc(rs.getDate("dfecdoc"));
				bean.setDfecregistro(rs.getDate("dfecregistro"));
				bean.setNdirigido(rs.getInt("ndirigido"));
				bean.setVdirigido(rs.getString("areadirigido"));
				bean.setVasunto(rs.getString("vasunto"));
				bean.setVreferencia(rs.getString("vreferencia"));
				bean.setVobservacion(rs.getString("vobservacion"));
				bean.setVprioridad(rs.getString("vprioridad"));
				bean.setVaccion(rs.getString("vaccion"));
				// bean.setAccion(rs.getString("accion"));
				bean.setVubiarchivo(rs.getString("vubiarchivo"));
				bean.setDfecplazo(rs.getDate("dfecplazo"));
				bean.setNdiasplazo(rs.getInt("ndiasplazo"));
				bean.setNestado(rs.getString("vestado"));
				bean.setVestado(rs.getString("estado"));
				bean.setDfeccre(rs.getDate("dfeccre"));
				bean.setDfecact(rs.getDate("dfecact"));
				bean.setVrescre(rs.getString("vrescre"));
				bean.setVresact(rs.getString("vresact"));
				bean.setNcodarea(rs.getInt("ncodarea"));
				bean.setArea(rs.getString("area"));
				// bean.setFicha_dirigido(rs.getLong("nficha_dirigido"));
				//bean.setFicha_dirigido(rs.getLong("nremitente"));
				//bean.setNcodarea_origen(rs.getInt("ncodarea_origen"));
				//bean.setArea_origen(rs.getString("areaorigen"));
				//bean.setNcodcentro(rs.getInt("ncodcentro"));
				//bean.setCentro(rs.getString("centro"));
				bean.setNindicador(rs.getInt("nindicador"));
				result.add(bean);
			}
		});
		return result;
	}
	
	

	public List<TiposDocumentosBean> tipodocexterno() {
		long nowIn = 0, nowOut = 0, nowDif = 0;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		nowIn = System.currentTimeMillis();
		logger.debug("Inicio ENTRANTEDAO AREA:" + nowIn);
		final List<TiposDocumentosBean> result = new ArrayList<TiposDocumentosBean>();

		String sql = "SELECT VCODTIPO as codigo, upper(VDESCRIPCION) as nombre  FROM ATD_TIPO A WHERE VFLAT='EXT' AND NESTADO=1 ORDER BY VCODTIPO";

		logger.debug("[EntranteDAO.areas][" + usuario + "]" + sql);
		Date inicio = new Date();
		logger.debug("[EntranteDAO.areas][" + usuario + "]Inicio sql:" + inicio);
		jdbcTemplate.query(sql, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				TiposDocumentosBean bean1 = new TiposDocumentosBean();
				bean1.setCodigo(rs.getString("codigo"));
				bean1.setDescripcion(rs.getString("nombre"));
				result.add(bean1);
			}
		});
		nowOut = System.currentTimeMillis();
		logger.debug("[EntranteDAO.areas][" + usuario + "]"
				+ "Final ENTRANTEDAO TIPO DOC:" + nowOut);
		nowDif = nowOut - nowIn;
		logger.debug("[EntranteDAO.areas][" + usuario + "]"
				+ "Duracion ENTRANTEDAO TIPO DOC:" + nowDif);
		return result;
	}

	private String LPAD(String registro, int i, String string) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<TiposDocumentosBean> tipo_doc() {
		long nowIn = 0, nowOut = 0, nowDif = 0;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		nowIn = System.currentTimeMillis();
		logger.debug("Inicio ENTRANTEDAO AREA:" + nowIn);
		final List<TiposDocumentosBean> result = new ArrayList<TiposDocumentosBean>();

		// String
		// sql="SELECT A.NCODAREA as codigo, A.NCODAREA||' - ' ||upper(A.VABREVIATURA) as nombre "+
		// "FROM AREA A WHERE A.NINDICADOR = 0 AND A.CTIPAREA NOT IN ('SE', 'CC') ORDER BY A.NCODAREA";

		String sql = "SELECT VOBSERVACIONES as codigo, upper(VDESCRIPCION) as nombre "
				+ "FROM ATD_TIPO A WHERE VFLAT='DOC' AND NESTADO=1 ORDER BY VCODTIPO";

		logger.debug("[EntranteDAO.areas][" + usuario + "]" + sql);
		Date inicio = new Date();
		logger.debug("[EntranteDAO.areas][" + usuario + "]Inicio sql:" + inicio);
		jdbcTemplate.query(sql, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				TiposDocumentosBean bean1 = new TiposDocumentosBean();
				bean1.setCodigo(rs.getString("codigo"));
				bean1.setDescripcion(rs.getString("nombre"));
				result.add(bean1);
			}
		});
		nowOut = System.currentTimeMillis();
		logger.debug("[EntranteDAO.areas][" + usuario + "]"
				+ "Final ENTRANTEDAO TIPO DOC:" + nowOut);
		nowDif = nowOut - nowIn;
		logger.debug("[EntranteDAO.areas][" + usuario + "]"
				+ "Duracion ENTRANTEDAO TIPO DOC:" + nowDif);
		return result;
	}

	public List<SeguimientoEntranteBean> BusquedaSeguimientoReg(String valor) {
		
		final List<SeguimientoEntranteBean> result = new ArrayList<SeguimientoEntranteBean>();
		String sql = " SELECT  S.NCORRELATIVO AS CORRELATIVO, S.NAREAREMITENTE AS AREAREMITENTE,UPPER(A.VABREVIATURA) as REMITENTE,S.NCODSEG AS SEGUIMIENTO, "
				+ "S.NDERIVADO AS DERIVADO,   W.VNOMBRES ||','||W.VAPEPATERNO ||' '||W.VAPEMATERNO AS NOMBRE_DERIVADO, S.DFECDERIVACION AS FECDERIVACION, S.DFECPLAZO AS FECPLAZO, "
				+ "T.VDESCRIPCION AS ESTADO,  S.VESTADO AS VESTADO, S.VOBSERVACION AS COMENTARIO, S.NAREADERIVADO AS AREADERIVADO, UPPER(B.VABREVIATURA) as NOMBREDERIVADO, "
				+ "S.NFICHAREMITENTE AS FICHAREMITENTE, C.VNOMBRES ||','||C.VAPEPATERNO ||' '||C.VAPEMATERNO AS NOMBRE_REMITENTE, S.DFECDERIVACION FROM ATD_DOC_ENTR_SEG S, "
				+ "ATD_DOC_ENTR E, TIPO T, TRABAJADOR W, AREA A, AREA B, TRABAJADOR C WHERE S.NANO=E.NANO AND S.NORIGEN=E.NORIGEN AND S.NCORRELATIVO=E.NCORRELATIVO AND "
				+ "S.VESTADO=T.VCODTIPO AND T.VOBSERVACIONES='ESTADO ATD' AND T.VESTADO='A' AND S.NDERIVADO=W.NFICHA AND W.VCODESTADO IN ('EIRC01','EIRC02','EIRC03') AND "
				+ "S.NAREADERIVADO=W.NCODAREA AND S.NAREAREMITENTE = A.NCODAREA AND A.NINDICADOR = 0 AND A.CTIPAREA IN  ('E ','G', 'D','P') AND S.NAREADERIVADO = B.NCODAREA AND "
				+ "B.NINDICADOR = 0 AND B.CTIPAREA IN  ('E ','G', 'D','P') AND S.NFICHAREMITENTE=C.NFICHA AND S.NAREAREMITENTE=C.NCODAREA AND C.VCODESTADO IN ('EIRC01','EIRC02','EIRC03') "
				+ "AND E.VNUMDOC = '" + valor + "' ORDER BY S.NCODSEG DESC";
		Date inicio = new Date();

		jdbcTemplate.query(sql, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				SeguimientoEntranteBean bean = new SeguimientoEntranteBean();
				bean.setCorrelativo(rs.getInt("correlativo"));
				bean.setSeguimiento(rs.getInt("seguimiento"));
				bean.setFecderivado(rs.getDate("fecderivacion"));
				bean.setEstado(rs.getString("estado"));
				bean.setAbrevremite(rs.getString("remitente"));
				bean.setNombre_remitente(rs.getString("nombre_remitente"));
				bean.setAbrevderivado(rs.getString("nombrederivado"));
				bean.setNombre_derivado(rs.getString("nombre_derivado"));
				bean.setComentario(rs.getString("comentario"));
				result.add(bean);
			}
		});
		return result;
	}
	
	public List<AnioBean> anio(){    		
		
		final List<AnioBean> results = new ArrayList<AnioBean>();
		jdbcTemplate.query(
				"SELECT VCODTIPO AS CODIGO, VDESCRIPCION AS DESCRIPCION  FROM ATD_TIPO WHERE VFLAT='ANO' AND NESTADO=1 AND NINDICADOR=1 ORDER BY VCODTIPO",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						AnioBean bean2 = new AnioBean();
						bean2.setCodigo(rs.getString("codigo"));
						bean2.setDescripcion(rs.getString("descripcion"));
						results.add(bean2);
					}
				});
		
		return results;		
	}
	
	public List<TiposDocumentosBean> tiporesporte(){    		
		
		final List<TiposDocumentosBean> results = new ArrayList<TiposDocumentosBean>();
		jdbcTemplate.query(
				"SELECT VCODTIPO AS CODIGO,VDESCRIPCION AS NOMBRE FROM ATD_TIPO WHERE NESTADO=1 AND VFLAT='EGI' ORDER BY VCODTIPO",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						TiposDocumentosBean bean2 = new TiposDocumentosBean();
						bean2.setCodigo(rs.getString("CODIGO"));
						bean2.setDescripcion(rs.getString("NOMBRE"));
						results.add(bean2);
					}
				});
		
		return results;		
	}
	
	

	public List<AreaBean> areas() {
		long nowIn = 0, nowOut = 0, nowDif = 0;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		nowIn = System.currentTimeMillis();
		logger.debug("Inicio ENTRANTEDAO AREA:" + nowIn);
		final List<AreaBean> result = new ArrayList<AreaBean>();

		// String
		// sql="SELECT A.NCODAREA as codigo, A.NCODAREA||' - ' ||upper(A.VABREVIATURA) as nombre "+
		// "FROM AREA A WHERE A.NINDICADOR = 0 AND A.CTIPAREA NOT IN ('SE', 'CC') ORDER BY A.NCODAREA";
		String sql = "SELECT A.NCODAREA as codigo, upper(A.VABREVIATURA)||' - ' ||upper(A.VDESCRIPCION) as nombre "
				+ "FROM AREA A WHERE A.NINDICADOR = 0 AND A.NESTADO IN (0,4) AND A.CTIPAREA IN  ('E ','G', 'D','P') ORDER BY A.VABREVIATURA";
		logger.debug("[EntranteDAO.areas][" + usuario + "]" + sql);
		Date inicio = new Date();
		logger.debug("[EntranteDAO.areas][" + usuario + "]Inicio sql:" + inicio);
		jdbcTemplate.query(sql, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				AreaBean bean1 = new AreaBean();
				bean1.setCodigo(rs.getInt("codigo"));
				bean1.setNombre(rs.getString("nombre"));
				result.add(bean1);
			}
		});
		nowOut = System.currentTimeMillis();
		logger.debug("[EntranteDAO.areas][" + usuario + "]"
				+ "Final ENTRANTEDAO AREA:" + nowOut);
		nowDif = nowOut - nowIn;
		logger.debug("[EntranteDAO.areas][" + usuario + "]"
				+ "Duracion ENTRANTEDAO AREA:" + nowDif);
		return result;
	}

	public void setStoredbusquedaregdocentrantes(
			StoredBusquedaRegDocEntrantes storedbusquedaregdocentrantes) {
		Storedbusquedaregdocentrantes = storedbusquedaregdocentrantes;
	}

	public void setStoredbusquedaregentrantesseg(
			StoredBusquedaRegEntrantesSeg storedbusquedaregentrantesseg) {
		Storedbusquedaregentrantesseg = storedbusquedaregentrantesseg;
	}

	public void setStoredCentro(StoredCentros storedCentros) {
		this.StoredCentros = storedCentros;
	}
	
	

	public StoredBusquedaDocumentosExternos getStoredbusquedadocumentosexternos() {
		return storedbusquedadocumentosexternos;
	}

	public void setStoredbusquedadocumentosexternos(
			StoredBusquedaDocumentosExternos storedbusquedadocumentosexternos) {
		this.storedbusquedadocumentosexternos = storedbusquedadocumentosexternos;
	}

	@Override
	public List<CentroBean> centros() {
		// TODO Auto-generated method stub
		return null;
	}

}
