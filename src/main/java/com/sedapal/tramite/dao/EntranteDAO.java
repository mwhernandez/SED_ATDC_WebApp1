package com.sedapal.tramite.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sedapal.tramite.beans.AccionBean;
import com.sedapal.tramite.beans.AnioBean;
import com.sedapal.tramite.beans.AreaBean;
import com.sedapal.tramite.beans.DocumentoDerivado;
import com.sedapal.tramite.beans.EstadosBean;
import com.sedapal.tramite.beans.GrupoBean;
import com.sedapal.tramite.beans.JefeBean;
import com.sedapal.tramite.beans.LlavesBean;
import com.sedapal.tramite.beans.OrigenBean;
import com.sedapal.tramite.beans.RemitenteBPM;
import com.sedapal.tramite.beans.RemitenteBean;
import com.sedapal.tramite.beans.EntranteBean;
import com.sedapal.tramite.beans.RepresentanteBean;
import com.sedapal.tramite.beans.SeguimientoBean;
import com.sedapal.tramite.beans.SeguimientoEntranteBean;
import com.sedapal.tramite.beans.ServidorBean;
import com.sedapal.tramite.beans.TipoConsulta;
import com.sedapal.tramite.beans.TiposBean;
import com.sedapal.tramite.beans.TiposDocumentosBean;
import com.sedapal.tramite.beans.TrabajadorBean;
import com.sedapal.tramite.beans.Usuario;
import com.sedapal.tramite.beans.consultas.AtencionDocSalidaBean;

public class EntranteDAO implements IEntranteDAO {

	private JdbcTemplate jdbcTemplate;
	private StoredEntrantes storedEntrantes;
	private StoredActualizarEnt storedActualizarEnt;
	private StoredEliminarEnt storedEliminarEnt;
	private StoredFiltrosEnt storedFiltrosEnt;
	private StoredNuevoDocumentoEntrada storednuevodocumentoentrada;
	private StoredDocumentosDerivados storedDocumentosDerivados;
	private StoredBusquedaEntrantes storedbusquedaentrante;
	private StoredCombo storedCombo;
	private StoredConsultaDocEntrantes Storedconsultadocentrantes;
	private StoredConsultaDocEntrantesSeg Storedconsultadocentrantesseg;
	private StoredBusquedaSeguimientoDocEntrantes storedbusquedaseguimientodocentrantes;
	private StoredBusquedaSeguimientoDocumentos storedbusquedaseguimientodocumentos;
	private StoredAlertaEntrante storedalertaentrante;
	private StoredListaAsuntoPopup storedlistaasuntopopub;
	private StoredBusquedaAsuntos storedbusquedaasuntos;
	private StoredEnvioEmail storedenvioemail;
	private StoredActualizarLineadigitalizacionEnt storedactualizarlineadigitalizacionEnt;

	// actualizarLineaDigitalizacion

	private StoredRemitentesSP storedremitentessp;

	// private static Logger logger = Logger.getLogger("R1");

	private StoredConsultaRemitenteBPM storedGetRemitentesBPM; // SED-FON-161
	private StoredRegistraRemitenteBPM storedNewRemitentesBPM; // SED-FON-161
	private StoredConsultaAreasByNombre storedGetAreasByNombre; // SED-FON-161

	public StoredConsultaAreasByNombre getStoredGetAreasByNombre() {
		return storedGetAreasByNombre;
	}

	public void setStoredGetAreasByNombre(StoredConsultaAreasByNombre storedGetAreasByNombre) {
		this.storedGetAreasByNombre = storedGetAreasByNombre;
	}

	public StoredRegistraRemitenteBPM getStoredNewRemitentesBPM() {
		return storedNewRemitentesBPM;
	}

	public void setStoredNewRemitentesBPM(StoredRegistraRemitenteBPM storedNewRemitentesBPM) {
		this.storedNewRemitentesBPM = storedNewRemitentesBPM;
	}

	public StoredConsultaRemitenteBPM getStoredGetRemitentesBPM() {
		return storedGetRemitentesBPM;
	}

	public void setStoredGetRemitentesBPM(StoredConsultaRemitenteBPM storedGetRemitentesBPM) {
		this.storedGetRemitentesBPM = storedGetRemitentesBPM;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<TiposBean> asuntos_estandares() {
		// storedlistaasunto
		Map output = storedlistaasuntopopub.execute();
		List asuntos = (ArrayList) output.get("asunto");
		return asuntos;
	}

	public List<TiposBean> busqueda_asuntos_estandar(String opcion_asunto, String detalle_asunto) {
		Map output = storedbusquedaasuntos.execute(opcion_asunto, detalle_asunto);
		List busquedaasunto = (ArrayList) output.get("busquedaasunto");
		return busquedaasunto;
	}

	public String confirmaGrupo() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		int perfil = usuario.getNcodperfil();
		// String sql = "SELECT COUNT(*) NAREAS FROM ATD_GRUPO_AREA WHERE NCODAREA=?";
		String sql = "select ncodperfil from perfil_sistema where ncodsistema=30 and vestado='A' and ncodperfil=?";
		String confirm = (String) this.jdbcTemplate.queryForObject(sql, new Object[] { perfil }, new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				String confi = new String();
				confi = rs.getString(1);
				return confi;
			}
		});
		return confirm;
	}

	public List<EntranteBean> AlertasEntradaSP(String area) {
		Map output = storedalertaentrante.execute(area);
		List listadocalertas = (ArrayList) output.get("entradaalerta");
		int lista = listadocalertas.size();
		System.out.println("Quiero ver el numero documentos");
		System.out.println(lista);
		return listadocalertas;
	}

	// AD CF 16-06-2011
	public String entrantesNuevos(String grupo, String fechaActual)// '2011-06-15 19:30
	{
		String sql = null;
		ResourceBundle bundle = ResourceBundle.getBundle("com.sedapal.tramite.files.parametros");
		String minutos = bundle.getString("refresh");
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		int area = 0;
		area = usuario.getNcodarea();
		// logger.debug("[EntranteDAO.entrantesNuevos]Grupo:+" + grupo);
		if (Integer.parseInt(grupo) == 6 || Integer.parseInt(grupo) == 10 || Integer.parseInt(grupo) == 15)// perfil grupo
		{
			sql = "SELECT COUNT(*) MENSAJES FROM (SELECT  a.NCODAREA , a.NCORRELATIVO FROM ATD_DOC_ENTR a, AREA b, ATD_GRUPO_AREA c "
					+ " WHERE   a.NCODAREA = b.NCODAREA AND b.NCODAREA=c.NCODAREA AND c.NCODAREA=" + area
					+ " AND (24* 60* (to_date(to_char(SYSDATE, 'YYYY-MM-DD hh24:mi'), 'YYYY-MM-DD hh24:mi') - to_date(to_char(a.DFECCRE, 'YYYY-MM-DD hh24:mi'), "
					+ " 'YYYY-MM-DD hh24:mi')))<=" + minutos
					+ " AND a.VESTADO='ESTA001' UNION SELECT  a.NCODAREA , a.NCORRELATIVO "
					+ " FROM ATD_DOC_ENTR_DIRIGIDO a, AREA b, ATD_GRUPO_AREA c WHERE   a.NCODAREA = b.NCODAREA "
					+ " AND b.NCODAREA=c.NCODAREA AND c.NCODAREA=" + area + " AND "
					+ "(24* 60* (to_date(to_char(SYSDATE, 'YYYY-MM-DD hh24:mi') , 'YYYY-MM-DD hh24:mi') - to_date(to_char(a.DFECCRE, 'YYYY-MM-DD hh24:mi'), "
					+ " 'YYYY-MM-DD hh24:mi')))<=" + minutos + ")";
		}
		if (Integer.parseInt(grupo) == 2 || Integer.parseInt(grupo) == 7 || Integer.parseInt(grupo) == 8
				|| Integer.parseInt(grupo) == 1 || Integer.parseInt(grupo) == 5 || Integer.parseInt(grupo) == 3) // perfil
																													// mantenimiento,
																													// derivado,
																													// administrador
																													// y
																													// consulta
		{
			sql = "SELECT COUNT(*) MENSAJES FROM (SELECT  a.NCODAREA , a.NCORRELATIVO FROM ATD_DOC_ENTR a, AREA b"
					+ " WHERE   a.NCODAREA = b.NCODAREA AND b.NCODAREA=" + area
					+ " AND (24* 60* (to_date(to_char(SYSDATE, 'YYYY-MM-DD hh24:mi'), 'YYYY-MM-DD hh24:mi') - to_date(to_char(a.DFECCRE, 'YYYY-MM-DD hh24:mi'), "
					+ " 'YYYY-MM-DD hh24:mi')))<=" + minutos
					+ " AND a.VESTADO='ESTA001' UNION SELECT  a.NCODAREA , a.NCORRELATIVO "
					+ " FROM ATD_DOC_ENTR_DIRIGIDO a, AREA b WHERE   a.NCODAREA = b.NCODAREA " + " AND b.NCODAREA="
					+ area + " AND "
					+ "(24* 60* (to_date(to_char(SYSDATE, 'YYYY-MM-DD hh24:mi'), 'YYYY-MM-DD hh24:mi') - to_date(to_char(a.DFECCRE, 'YYYY-MM-DD hh24:mi'), "
					+ " 'YYYY-MM-DD hh24:mi')))<=" + minutos + ")";
		}
		if (Integer.parseInt(grupo) == 4 || Integer.parseInt(grupo) == 13 || Integer.parseInt(grupo) == 14) // perfil
																											// mesa de
																											// partes
																											// solo para
																											// que
																											// devuelva
																											// valor 0
																											// linea de
																											// digitalizacion
		{
			sql = "select count(ncod_parametro) from atd_parametros where valor='ATD0001'";
		}
		// logger.debug("[EntranteDAO.entrantesNuevos]"+ sql );
		System.out.println("[EntranteDAO.entrantesNuevos]" + sql);
		String confirm = (String) this.jdbcTemplate.queryForObject(sql, new Object[] {}, new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				String confi = new String();
				confi = rs.getString(1);
				return confi;
			}
		});
		return confirm;
	}

	public List<EntranteBean> entrantesSP(String ncodarea) {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		ncodarea = String.valueOf(usuario.getCodarea());
		long nowIn = 0, nowOut = 0, nowDif = 0;
		nowIn = System.currentTimeMillis();
		// System.out.println(ncodarea);
		// System.out.println("S_GUIA.ATDC_DOC_ENTRAN_ALL");
		Map output = storedEntrantes.execute(ncodarea);
		// nowOut = System.currentTimeMillis();
		// nowDif = nowOut - nowIn;
		// logger.debug("["+usuario+"]"+"Stored S_GUIA.ATDC_DOC_ENTRAN_ALL Duracion:" +
		// nowDif);
		List entrante = (ArrayList) output.get("entrantes");
		return entrante;
	}

	public List<AreaBean> areas() {
		long nowIn = 0, nowOut = 0, nowDif = 0;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		nowIn = System.currentTimeMillis();
		// logger.debug("Inicio ENTRANTEDAO AREA:" + nowIn);
		final List<AreaBean> result = new ArrayList<AreaBean>();

		// String sql="SELECT A.NCODAREA as codigo, A.NCODAREA||' - '
		// ||upper(A.VABREVIATURA) as nombre "+
		// "FROM AREA A WHERE A.NINDICADOR = 0 AND A.CTIPAREA NOT IN ('SE', 'CC') ORDER
		// BY A.NCODAREA";
		String sql = "SELECT A.NCODAREA as codigo, upper(A.VABREVIATURA)||' - ' ||upper(A.VDESCRIPCION) as nombre "
				+ "FROM AREA A WHERE A.NINDICADOR = 0 AND A.NESTADO IN (0,4) AND A.CTIPAREA IN  ('E ','G', 'D','P') ORDER BY A.VABREVIATURA";
		// logger.debug("[EntranteDAO.areas]["+usuario+"]"+sql);
		Date inicio = new Date();
		// logger.debug("[EntranteDAO.areas]["+usuario+"]Inicio sql:" + inicio);
		jdbcTemplate.query(sql, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				AreaBean bean1 = new AreaBean();
				bean1.setCodigo(rs.getInt("codigo"));
				bean1.setNombre(rs.getString("nombre"));
				result.add(bean1);
			}
		});
		nowOut = System.currentTimeMillis();
		// logger.debug("[EntranteDAO.areas]["+usuario+"]"+"Final ENTRANTEDAO AREA:" +
		// nowOut);
		nowDif = nowOut - nowIn;
		// logger.debug("[EntranteDAO.areas]["+usuario+"]"+"Duracion ENTRANTEDAO AREA:"
		// + nowDif);
		return result;
	}

	public List<TiposDocumentosBean> tipo_doc() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		final List<TiposDocumentosBean> result = new ArrayList<TiposDocumentosBean>();
		String sql = "SELECT VCODTIPO as codigo, upper(VDESCRIPCION) as nombre "
				+ "FROM ATD_TIPO A WHERE VFLAT='DOC' AND NESTADO=1";

		jdbcTemplate.query(sql, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				TiposDocumentosBean bean1 = new TiposDocumentosBean();
				bean1.setCodigo(rs.getString("codigo"));
				bean1.setDescripcion(rs.getString("nombre"));
				result.add(bean1);
			}
		});

		return result;
	}

	public List<AnioBean> anio() {

		final List<AnioBean> results = new ArrayList<AnioBean>();
		jdbcTemplate.query(
				"SELECT VCODTIPO AS CODIGO, VDESCRIPCION AS DESCRIPCION  FROM ATD_TIPO WHERE VFLAT='ANO' AND NESTADO=1 AND NINDICADOR=1 ORDER BY VCODTIPO DESC",
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

	public List<TiposDocumentosBean> prioridad() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		final List<TiposDocumentosBean> result = new ArrayList<TiposDocumentosBean>();
		String sql = "SELECT VCODTIPO AS codigo, VDESCRIPCION AS nombre FROM ATD_TIPO "
				+ "WHERE VFLAT='PRI' AND NESTADO=1 AND NINDICADOR=1";

		jdbcTemplate.query(sql, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				TiposDocumentosBean bean1 = new TiposDocumentosBean();
				bean1.setCodigo(rs.getString("codigo"));
				bean1.setDescripcion(rs.getString("nombre"));
				result.add(bean1);
			}
		});

		return result;
	}

	public List<AreaBean> areasA(int codigo) {
		long nowIn = 0, nowOut = 0, nowDif = 0;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		nowIn = System.currentTimeMillis();
		// logger.debug("Inicio ENTRANTEDAO AREA:" + nowIn);
		final List<AreaBean> result = new ArrayList<AreaBean>();

		// String sql="SELECT A.NCODAREA as codigo, A.NCODAREA||' - '
		// ||upper(A.VABREVIATURA) as nombre "+
		// "FROM AREA A WHERE A.NINDICADOR = 0 AND A.CTIPAREA NOT IN ('SE', 'CC') ORDER
		// BY A.NCODAREA";
		String sql = "SELECT A.NCODAREA as codigo, upper(A.VABREVIATURA)||' - ' ||upper(A.VDESCRIPCION) as nombre "
				+ "FROM AREA A WHERE A.NINDICADOR = 0 AND A.NESTADO IN (0,4) AND A.CTIPAREA IN  ('E ','G', 'D','P') AND A.NCODAREA="
				+ codigo + " ";
		// logger.debug("[EntranteDAO.areas]["+usuario+"]"+sql);
		Date inicio = new Date();
		// logger.debug("[EntranteDAO.areas]["+usuario+"]Inicio sql:" + inicio);
		jdbcTemplate.query(sql, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				AreaBean bean1 = new AreaBean();
				bean1.setCodigo(rs.getInt("codigo"));
				bean1.setNombre(rs.getString("nombre"));
				result.add(bean1);
			}
		});
		nowOut = System.currentTimeMillis();
		// logger.debug("[EntranteDAO.areas]["+usuario+"]"+"Final ENTRANTEDAO AREA:" +
		// nowOut);
		nowDif = nowOut - nowIn;
		// logger.debug("[EntranteDAO.areas]["+usuario+"]"+"Duracion ENTRANTEDAO AREA:"
		// + nowDif);
		return result;
	}

	@Transactional(propagation = Propagation.NEVER, readOnly = true)
	public List<OrigenBean> origen() {
		long nowIn = 0, nowOut = 0, nowDif = 0;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		nowIn = System.currentTimeMillis();
		// logger.debug("Inicio SALIDADAO ORIGEN:" + nowIn);
		final List<OrigenBean> results8 = new ArrayList<OrigenBean>();
		jdbcTemplate.query(
				// "SELECT VCODTIPO codigo,VDESCRIPCION descripcion FROM TIPO WHERE VESTADO='A'
				// AND VOBSERVACIONES='ORIGEN ATD' ORDER BY VCODTIPO",
				"SELECT VCODTIPO codigo,VDESCRIPCION descripcion FROM ATD_TIPO WHERE VFLAT='MAN' AND NESTADO=1",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						OrigenBean bean8 = new OrigenBean();
						bean8.setCodigo(rs.getString("codigo"));
						bean8.setDescripcion(rs.getString("descripcion"));
						results8.add(bean8);
					}
				});
		nowOut = System.currentTimeMillis();
		// logger.debug("["+usuario+"]"+"Final SALIDADAO ORIGEN:" + nowOut);
		nowDif = nowOut - nowIn;
		// logger.debug("["+usuario+"]"+"Duracion SALIDADAO ORIGEN:" + nowDif);
		return results8;

	}

	public List<OrigenBean> origenA() {
		long nowIn = 0, nowOut = 0, nowDif = 0;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		nowIn = System.currentTimeMillis();
		// logger.debug("Inicio SALIDADAO ORIGEN:" + nowIn);
		final List<OrigenBean> results8 = new ArrayList<OrigenBean>();
		jdbcTemplate.query(
				// "SELECT VCODTIPO codigo,VDESCRIPCION descripcion FROM TIPO WHERE VESTADO='A'
				// AND VOBSERVACIONES='ORIGEN ATD' ORDER BY VCODTIPO",
				"SELECT VCODTIPO codigo,VDESCRIPCION descripcion FROM ATD_TIPO WHERE VFLAT='MAN' ",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						OrigenBean bean8 = new OrigenBean();
						bean8.setCodigo(rs.getString("codigo"));
						bean8.setDescripcion(rs.getString("descripcion"));
						results8.add(bean8);
					}
				});
		nowOut = System.currentTimeMillis();
		// logger.debug("["+usuario+"]"+"Final SALIDADAO ORIGEN:" + nowOut);
		nowDif = nowOut - nowIn;
		// logger.debug("["+usuario+"]"+"Duracion SALIDADAO ORIGEN:" + nowDif);
		return results8;

	}

	public List<AreaBean> areas_seleccionadas(int anio, int origen, String tipodoc, long correlativo) {
		long nowIn = 0, nowOut = 0, nowDif = 0;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		nowIn = System.currentTimeMillis();
		// logger.debug("Inicio ENTRANTEDAODAO AREA_SELECCIONADAS:" + nowIn);
		// String sql = "SELECT D.NCODAREA as codigo,D.VTIPO||' - '
		// ||upper(A.VABREVIATURA)||' - ' ||upper(A.VDESCRIPCION) as nombre "+
		// "FROM ATD_DOC_ENTR_DIRIGIDO D, AREA A "+
		// "WHERE D.NANO="+anio+" AND D.NORIGEN="+origen+" AND D.VTIPODOC='"+tipodoc+"'
		// AND D.NCORRELATIVO="+correlativo+" " +
		// "AND D.NCODAREA=A.NCODAREA AND D.NESTADO=1 ORDER BY D.VTIPO";
		String sql = "SELECT D.NCODAREA as codigo,D.VTIPO as tipo,upper(A.VABREVIATURA)||' - ' ||upper(A.VDESCRIPCION) as nombre "
				+ "FROM ATD_DOC_ENTR_DIRIGIDO D, AREA A " + "WHERE  D.NANO=" + anio + " AND D.NORIGEN=" + origen
				+ " AND D.VTIPODOC='" + tipodoc + "' AND D.NCORRELATIVO=" + correlativo + " "
				+ "AND D.NCODAREA=A.NCODAREA AND D.NESTADO=1 ORDER BY D.VTIPO,NOMBRE";

		final List<AreaBean> result = new ArrayList<AreaBean>();
		jdbcTemplate.query(
				// "SELECT D.NCODAREA as codigo,A.NCODAREA||' - ' ||upper(A.VABREVIATURA) as
				// nombre "+
				// "FROM ATD_DOC_ENTR_DIRIGIDO D, AREA A "+
				// "WHERE D.NANO="+anio+" AND D.NORIGEN="+origen+" AND D.VTIPODOC='"+tipodoc+"'
				// AND D.NCORRELATIVO="+correlativo+" " +
				// "AND D.NCODAREA=A.NCODAREA AND D.NESTADO=1",
				sql, new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						AreaBean bean1 = new AreaBean();
						bean1.setCodigo(rs.getInt("codigo"));
						bean1.setTipo(rs.getString("tipo"));
						bean1.setNombre(rs.getString("nombre"));
						result.add(bean1);
					}
				});
		nowOut = System.currentTimeMillis();
		System.out.println("Areas Seleccionadas:" + sql);
		// logger.debug("["+usuario+"]"+"Final ENTRANTEDAO AREA_SELECCIONADAS:" +
		// nowOut);
		nowDif = nowOut - nowIn;
		// logger.debug("["+usuario+"]"+"Duracion ENTRANTEDAO AREA_SELECCIONADAS:" +
		// nowDif);
		return result;
	}
	/*
	 * public List<AreaBean> areas_seleccionadasOrigen(int anio, int origen, long
	 * correlativo, int nCodAreaOrigen){ long nowIn =0, nowOut=0, nowDif =0;
	 * HttpSession session =
	 * (HttpSession)FacesContext.getCurrentInstance().getExternalContext().
	 * getSession(false); Usuario usuario = null;
	 * usuario=(Usuario)session.getAttribute("sUsuario"); nowIn =
	 * System.currentTimeMillis(); String sql =
	 * "SELECT NCODAREA as codigo FROM ATD_DOC_ENTR_DIRIGIDO WHERE NCORRELATIVO="
	 * +correlativo
	 * +" AND NCODAREA_ORIGEN="+nCodAreaOrigen+" AND NANO="+anio+" AND NORIGEN="
	 * +origen; final List<AreaBean> result = new ArrayList<AreaBean>();
	 * jdbcTemplate.query(
	 * //"SELECT D.NCODAREA as codigo,A.NCODAREA||' - ' ||upper(A.VABREVIATURA) as nombre "
	 * + //"FROM ATD_DOC_ENTR_DIRIGIDO D, AREA A "+
	 * //"WHERE  D.NANO="+anio+" AND D.NORIGEN="+origen+" AND D.VTIPODOC='"
	 * +tipodoc+"' AND D.NCORRELATIVO="+correlativo+" " + //
	 * "AND D.NCODAREA=A.NCODAREA AND D.NESTADO=1", sql, new RowCallbackHandler() {
	 * public void processRow(ResultSet rs) throws SQLException { AreaBean bean1 =
	 * new AreaBean(); bean1.setCodigo(rs.getInt("codigo")); result.add(bean1); }
	 * }); nowOut = System.currentTimeMillis();
	 * System.out.println("Areas Seleccionadas Origen:"+ sql); nowDif = nowOut -
	 * nowIn;
	 * logger.debug("["+usuario+"]"+"Duracion ENTRANTEDAO AREA_SELECCIONADAS:" +
	 * nowDif); return result; }
	 */

	// ADD CF AGO 2011 REPORT
	public List<TrabajadorBean> trabajadorSolo(long ficha) {
		long nowIn = 0, nowOut = 0, nowDif = 0;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		nowIn = System.currentTimeMillis();
		// logger.debug("Inicio ENTRANTEDAO TRABAJADOR:" + nowIn);
		final List<TrabajadorBean> results1 = new ArrayList<TrabajadorBean>();
		String sql = "select nficha as ficha, UPPER(VAPEPATERNO ||' '||VAPEMATERNO ||','||VNOMBRES) AS nombre "
				+ "from trabajador where vcodestado in ('EIRC01','EIRC02','EIRC03') and nficha=" + ficha;
		// logger.debug("ENTRANTEDAO TRABAJADOR (sql:"+ sql);
		jdbcTemplate.query(sql,
				// "select nficha as ficha, UPPER(VAPEPATERNO ||' '||VAPEMATERNO
				// ||','||VNOMBRES) AS nombre "+
				// "from atd_jefes_equipos where vcodestado='EIRC01' and ncodarea= "+area+"order
				// by VAPEPATERNO",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						TrabajadorBean bean2 = new TrabajadorBean();
						bean2.setFicha(rs.getLong("ficha"));
						bean2.setNombre_completo(rs.getString("nombre"));
						results1.add(bean2);
					}
				});
		nowOut = System.currentTimeMillis();
		// logger.debug("["+usuario+"]"+"Final ENTRANTEDAO TRABAJADOR:" + nowOut);
		nowDif = nowOut - nowIn;
		// logger.debug("["+usuario+"]"+"Duracion ENTRANTEDAO TRABAJADOR:" + nowDif);
		return results1;
	}

	public List<RemitenteBean> remitentes() {
		long nowIn = 0, nowOut = 0, nowDif = 0;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		nowIn = System.currentTimeMillis();
		// logger.debug("Inicio ENTRANTEDAO REMITENTE:" + nowIn);
		final List<RemitenteBean> result1 = new ArrayList<RemitenteBean>();
		jdbcTemplate.query(
				// "SELECT NCODREMITENTE AS codigo, UPPER(VDESCRIPCION) AS nombre "+
				// "FROM ATD_REMITENTE WHERE VESTADO = 'A' AND NINDICADOR = 1 ORDER BY
				// VDESCRIPCION",
				"SELECT NCODREMITENTE AS codigo, UPPER(VDESCRIPCION) AS nombre "
						+ "FROM ATD_REMITENTE WHERE VESTADO = 'A' AND NINDICADOR = 1 ORDER BY NCODREMITENTE",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						RemitenteBean bean2 = new RemitenteBean();
						bean2.setCodigo(rs.getInt("codigo"));
						bean2.setDescripcion(rs.getString("nombre"));
						result1.add(bean2);
					}
				});
		nowOut = System.currentTimeMillis();
		// logger.debug("["+usuario+"]"+"Final ENTRANTEDAO AREA_SELECCIONADAS:" +
		// nowOut);
		nowDif = nowOut - nowIn;
		// logger.debug("["+usuario+"]"+"Duracion ENTRANTEDAO AREA_SELECCIONADAS:" +
		// nowDif);
		return result1;
	}

	public List<RemitenteBean> remitentesA(int codigo) {
		long nowIn = 0, nowOut = 0, nowDif = 0;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		// nowIn = System.currentTimeMillis();
		// logger.debug("Inicio ENTRANTEDAO REMITENTE:" + nowIn);
		final List<RemitenteBean> result1 = new ArrayList<RemitenteBean>();
		jdbcTemplate.query(
				// "SELECT NCODREMITENTE AS codigo, UPPER(VDESCRIPCION) AS nombre "+
				// "FROM ATD_REMITENTE WHERE VESTADO = 'A' AND NINDICADOR = 1 ORDER BY
				// VDESCRIPCION",
				"SELECT NCODREMITENTE AS codigo, UPPER(VDESCRIPCION) AS nombre "
						+ "FROM ATD_REMITENTE WHERE VESTADO = 'A' AND NINDICADOR = 1 AND NCODREMITENTE=" + codigo + " ",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						RemitenteBean bean2 = new RemitenteBean();
						bean2.setCodigo(rs.getInt("codigo"));
						bean2.setDescripcion(rs.getString("nombre"));
						result1.add(bean2);
					}
				});
		// nowOut = System.currentTimeMillis();
		// logger.debug("["+usuario+"]"+"Final ENTRANTEDAO AREA_SELECCIONADAS:" +
		// nowOut);
		// nowDif = nowOut - nowIn;
		// logger.debug("["+usuario+"]"+"Duracion ENTRANTEDAO AREA_SELECCIONADAS:" +
		// nowDif);
		return result1;
	}

	// SED-FON-161
	public List<RemitenteBean> remitentesBPM(int codigo) {
		long nowIn = 0, nowOut = 0, nowDif = 0;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		final List<RemitenteBean> result1 = new ArrayList<RemitenteBean>();
		jdbcTemplate.query("select ncorrelativo as codigo, UPPER(vnombre) as nombre  "
				+ "from atd_remitente_bpm where ncorrelativo=" + codigo + " ", new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						RemitenteBean bean2 = new RemitenteBean();
						bean2.setCodigo(rs.getInt("codigo"));
						bean2.setDescripcion(rs.getString("nombre"));
						result1.add(bean2);
					}
				});
		return result1;
	}

	public List<TrabajadorBean> trabajadorunico(long ficha) {
		final List<TrabajadorBean> results1 = new ArrayList<TrabajadorBean>();
		String sql = "select nficha as ficha, UPPER(VAPEPATERNO ||' '||VAPEMATERNO ||','||VNOMBRES) AS nombre "
				+ "from trabajador where vcodestado in ('EIRC01') and ind_email=0 and nficha=" + ficha;

		jdbcTemplate.query(sql, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				TrabajadorBean bean2 = new TrabajadorBean();
				bean2.setFicha(rs.getLong("ficha"));
				bean2.setNombre_completo(rs.getString("nombre"));
				results1.add(bean2);
			}
		});
		return results1;
	}

	@Override
	public List<RemitenteBean> personaExterna(String cadenaDigitada) {

		Map output = storedremitentessp.execute(cadenaDigitada);
		List remitentesactivos = (ArrayList) output.get("remitentesactivos");
		System.out.println("Estamos Aqui Eli- Remitentes");
		System.out.println(remitentesactivos);
		return remitentesactivos;
	}

	public void setStoredremitentessp(StoredRemitentesSP storedremitentessp) {
		this.storedremitentessp = storedremitentessp;
	}

	public List<RemitenteBean> personaIntena(String cadenaDigitada) {
		long nowIn = 0, nowOut = 0, nowDif = 0;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		nowIn = System.currentTimeMillis();
		// logger.debug("Inicio ENTRANTEMESADAO REMITENTES:" + nowIn);
		final List<RemitenteBean> result1 = new ArrayList<RemitenteBean>();
		jdbcTemplate.query(
				// "SELECT NCODREMITENTE AS codigo, UPPER(VDESCRIPCION) AS nombre "+
				// "FROM ATD_REMITENTE WHERE VESTADO = 'A' AND NINDICADOR = 1 AND
				// UPPER(VDESCRIPCION) LIKE UPPER('"+cadenaDigitada+"%')",
				"SELECT A.NCODAREA as codigo, upper(A.VABREVIATURA)||' - ' ||upper(A.VDESCRIPCION) as nombre "
						+ "FROM AREA A WHERE A.NINDICADOR = 0 AND A.NESTADO IN (0,4) AND A.CTIPAREA IN  ('E ','G', 'D','P') AND UPPER(A.VABREVIATURA) LIKE UPPER('"
						+ cadenaDigitada + "%')",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						RemitenteBean bean2 = new RemitenteBean();
						bean2.setCodigo(rs.getInt("codigo"));
						bean2.setDescripcion(rs.getString("nombre"));
						result1.add(bean2);
					}
				});
		nowOut = System.currentTimeMillis();
		// logger.debug("["+usuario+"]"+"Final ENTRANTEMESADAO REMITENTES:" + nowOut);
		nowDif = nowOut - nowIn;
		// logger.debug("["+usuario+"]"+"Duracion ENTRANTEMESADAO REMITENTES:" +
		// nowDif);
		return result1;
	}

	public List<TiposBean> tipos() {
		long nowIn = 0, nowOut = 0, nowDif = 0;
		int area;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		area = usuario.getCodarea();
		nowIn = System.currentTimeMillis();
		// logger.debug("Inicio ENTRANTEDAO TIPOS:" + nowIn);
		final List<TiposBean> results = new ArrayList<TiposBean>();
		jdbcTemplate.query(
				"SELECT VCODTIPO codigo,VDESCRIPCION descripcion FROM TIPO  "
						+ "WHERE VESTADO='A' AND VOBSERVACIONES='TIPO DE DOCUMENTO ATD' ORDER BY VDESCRIPCION",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						TiposBean bean2 = new TiposBean();
						bean2.setTipo(rs.getString("codigo"));
						bean2.setDescripcion(rs.getString("descripcion"));
						results.add(bean2);
					}
				});
		nowOut = System.currentTimeMillis();
		// logger.debug("["+usuario+"]"+"Final ENTRANTEDAO AREA_SELECCIONADAS:" +
		// nowOut);
		nowDif = nowOut - nowIn;
		// logger.debug("["+usuario+"]"+"Duracion ENTRANTEDAO AREA_SELECCIONADAS:" +
		// nowDif);
		return results;
	}

	public List<TiposBean> tipoconsulta() {

		final List<TiposBean> results = new ArrayList<TiposBean>();
		jdbcTemplate.query("select vcodtipo as codigo, vdescripcion as descripcion from atd_tipo "
				+ "where vflat='FLT' and nestado=1 ORDER BY codigo", new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						TiposBean bean2 = new TiposBean();
						bean2.setTipo(rs.getString("codigo"));
						bean2.setDescripcion(rs.getString("descripcion"));
						results.add(bean2);
					}
				});

		return results;
	}

	public List<EntranteBean> entrada(int area) {
		long nowIn = 0, nowOut = 0, nowDif = 0;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		nowIn = System.currentTimeMillis();
		// logger.debug("Inicio ENTRANTEDAO ENTRADA EMAIL:" + nowIn);
		final List<EntranteBean> results5 = new ArrayList<EntranteBean>();
		jdbcTemplate.query(
				"SELECT (CASE VESTADO WHEN  'ESTA001' THEN  'PENDIENTE' WHEN  'ESTA002' THEN  'DERIVADO' WHEN  'ESTA003' THEN  'ATENDIDO' "
						+ "WHEN  'ESTA004' THEN  'ELIMINADO'     END)  AS estado, COUNT(NCORRELATIVO) as cantidad "
						+ "FROM ATD_DOC_ENTR WHERE NCODAREA=" + area + " GROUP BY NCODAREA, VESTADO ",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						EntranteBean bean5 = new EntranteBean();
						bean5.setVestado(rs.getString("estado"));
						bean5.setCantidad(rs.getInt("cantidad"));
						results5.add(bean5);
					}
				});
		nowOut = System.currentTimeMillis();
		// logger.debug("["+usuario+"]"+"Final ENTRANTEDAO ENTRADA CORREO:" + nowOut);
		nowDif = nowOut - nowIn;
		// logger.debug("["+usuario+"]"+"Duracion ENTRADA CORREO:" + nowDif);
		return results5;
	}

	public List<ServidorBean> servidor() {
		long nowIn = 0, nowOut = 0, nowDif = 0;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		nowIn = System.currentTimeMillis();
		// logger.debug("Inicio ENTRANTEDAO SERVIDOR:" + nowIn);
		final List<ServidorBean> results3 = new ArrayList<ServidorBean>();
		jdbcTemplate.query(
				// "SELECT VALOR1 AS DESCRIPCION FROM ATD_PARAMETROS WHERE NVALOR=2 AND
				// NESTADO=1 AND NVALOR2=1",
				// "SELECT VALOR1 AS DESCRIPCION FROM ATD_PARAMETROS WHERE NVALOR=2 AND
				// NESTADO=1 AND NVALOR2=5",
				"SELECT VALOR1 AS DESCRIPCION FROM ATD_PARAMETROS WHERE NVALOR=2 AND NESTADO=1 AND NVALOR2=7",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						ServidorBean bean4 = new ServidorBean();
						bean4.setDescripcion(rs.getString("descripcion"));
						results3.add(bean4);
					}
				});
		nowOut = System.currentTimeMillis();
		// logger.debug("["+usuario+"]"+"Final ENTRANTEDAO SERVIDOR:" + nowOut);
		nowDif = nowOut - nowIn;
		// logger.debug("["+usuario+"]"+"Duracion ENTRANTEDAO SERVIDOR:" + nowDif);
		return results3;
	}

	public List<LlavesBean> codigo() {
		final List<LlavesBean> results = new ArrayList<LlavesBean>();
		jdbcTemplate.query("select max(ncodremitente)+ 1 as codigo from atd_remitente", new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				LlavesBean bean = new LlavesBean();
				bean.setCodigo(rs.getInt("codigo"));
				results.add(bean);
			}
		});
		return results;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
	public void UpdateEmpresa(int codigo, String empresa, int area, String estado, int indicador, String login) {

		String sql = "insert into atd_remitente (ncodremitente, vdescripcion, ncodarea, vestado, nindicador, vrescre)"
				+ " values(?,?,?,?,?,?)";
		Object[] parametros = new Object[] { codigo, empresa, area, estado, indicador, login };
		int rs = jdbcTemplate.update(sql, parametros);

	}

	public void grabarCombo(int codigoRemitente, String empresa, int dirigido, String estado, int indicadorEmpresa,
			String login) {

	}

	public List<LlavesBean> codigo_representante(int codempresa) {
		final List<LlavesBean> results = new ArrayList<LlavesBean>();
		jdbcTemplate.query("select count(ncodrepresentante)+ 1 as codigo from atd_representante where ncodremitente= "
				+ codempresa + "", new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						LlavesBean bean = new LlavesBean();
						bean.setCodigo_representante(rs.getInt("codigo"));
						results.add(bean);
					}
				});
		return results;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
	public void UpdateRepresentante(int codigo, int codrepresentante, String nombre, String estado, int indicador,
			String login) {

		String sql = "insert into atd_representante (ncodremitente, ncodrepresentante, vnombre, vestado, nindicador, vrescre)"
				+ " values(?,?,?,?,?,?)";
		Object[] parametros = new Object[] { codigo, codrepresentante, nombre, estado, indicador, login };
		int rs1 = jdbcTemplate.update(sql, parametros);

	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
	public boolean updatedirigido(long correlativo, int area) {
		String sql = "update atd_doc_entr_dirigido set nestado=2 where ncorrelativo= " + correlativo + " and  ncodarea="
				+ area + "";
		System.out.println("Parametros:" + correlativo + "   " + area);
		System.out.println("Update dirigdo:" + sql);
		int rs = jdbcTemplate.update(sql);
		if (rs > 0)
			return true;
		return false;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
	public boolean updatedirigidoCancel(long correlativo, int area) {
		String sql = "update atd_doc_entr_dirigido set nestado=1 where ncorrelativo= " + correlativo + " and  ncodarea="
				+ area + "";
		System.out.println("Parametros:" + correlativo + "   " + area);
		System.out.println("Update dirigido cancel:" + sql);
		int rs = jdbcTemplate.update(sql);
		if (rs > 0)
			return true;
		return false;
	}

	public List<TrabajadorBean> trabajador(int area) {
		long nowIn = 0, nowOut = 0, nowDif = 0;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		nowIn = System.currentTimeMillis();
		// logger.debug("Inicio ENTRANTEDAO TRABAJADOR:" + nowIn);
		final List<TrabajadorBean> results1 = new ArrayList<TrabajadorBean>();
		jdbcTemplate.query(
				"select nficha as ficha, UPPER(VAPEPATERNO ||' '||VAPEMATERNO ||','||VNOMBRES) AS nombre "
						+ "from trabajador where vcodestado in ('EIRC01','EIRC02','EIRC03') and ncodarea= " + area
						+ "order by VAPEPATERNO",
				// "select nficha as ficha, UPPER(VAPEPATERNO ||' '||VAPEMATERNO
				// ||','||VNOMBRES) AS nombre "+
				// "from atd_jefes_equipos where vcodestado='EIRC01' and ncodarea= "+area+"order
				// by VAPEPATERNO",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						TrabajadorBean bean2 = new TrabajadorBean();
						bean2.setFicha(rs.getLong("ficha"));
						bean2.setNombre_completo(rs.getString("nombre"));
						results1.add(bean2);
					}
				});
		nowOut = System.currentTimeMillis();
		// logger.debug("["+usuario+"]"+"Final ENTRANTEDAO TRABAJADOR:" + nowOut);
		nowDif = nowOut - nowIn;
		// logger.debug("["+usuario+"]"+"Duracion ENTRANTEDAO TRABAJADOR:" + nowDif);
		return results1;
	}

	public List<TrabajadorBean> trabajador_derivador(int area) {
		long nowIn = 0, nowOut = 0, nowDif = 0;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		nowIn = System.currentTimeMillis();
		// logger.debug("Inicio ENTRANTEDAO TRABAJADOR_DERIVADOR:" + nowIn);
		final List<TrabajadorBean> results3 = new ArrayList<TrabajadorBean>();
		jdbcTemplate.query("select nficha as ficha, UPPER(VAPEPATERNO ||' '||VAPEMATERNO ||','||VNOMBRES) AS nombre "
				+ "from trabajador where vcodestado in ('EIRC01','EIRC03') and vcodtipo in ('TIPT001','TIPT005') and ncodarea= "
				+ area + "order by VAPEPATERNO", new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						TrabajadorBean bean3 = new TrabajadorBean();
						bean3.setFicha(rs.getLong("ficha"));
						bean3.setNombre_completo(rs.getString("nombre"));
						results3.add(bean3);
					}
				});
		nowOut = System.currentTimeMillis();
		// logger.debug("["+usuario+"]"+"Final ENTRANTEDAO TRABAJADOR_DERIVADOR:" +
		// nowOut);
		nowDif = nowOut - nowIn;
		// logger.debug("["+usuario+"]"+"Duracion ENTRANTEDAO TRABAJADOR_DERIVADOR:" +
		// nowDif);
		return results3;
	}

	public List<RepresentanteBean> representante(int codigo) {
		long nowIn = 0, nowOut = 0, nowDif = 0;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		nowIn = System.currentTimeMillis();
		// logger.debug("Inicio ENTRANTEDAO REPRESENTANTE :" + nowIn);
		final List<RepresentanteBean> results5 = new ArrayList<RepresentanteBean>();
		jdbcTemplate.query(
				"SELECT NCODREPRESENTANTE AS codigo, UPPER(VNOMBRE) AS representante "
						+ "FROM ATD_REPRESENTANTE WHERE VESTADO = 'A' AND NCODREMITENTE=" + codigo + "ORDER BY VNOMBRE",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						RepresentanteBean bean4 = new RepresentanteBean();
						bean4.setCodrepresentante(rs.getInt("codigo"));
						bean4.setVnombre(rs.getString("representante"));
						results5.add(bean4);
					}
				});
		nowOut = System.currentTimeMillis();
		// logger.debug("["+usuario+"]"+"Final ENTRANTEDAO REPRESENTANTE:" + nowOut);
		nowDif = nowOut - nowIn;
		// logger.debug("["+usuario+"]"+"Duracion ENTRANTEDAO REPRESENTANTE:" + nowDif);
		return results5;
	}

	// SED-REQ-00001
	public List<RepresentanteBean> representanteBPM(int codigo) {
		long nowIn = 0, nowOut = 0, nowDif = 0;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		nowIn = System.currentTimeMillis();
		// logger.debug("Inicio ENTRANTEDAO REPRESENTANTE :" + nowIn);
		final List<RepresentanteBean> results5 = new ArrayList<RepresentanteBean>();
		jdbcTemplate.query("SELECT NCODREPRESENTANTE AS codigo, UPPER(VNOMBRE) AS representante "
				+ "FROM ATD_REPRESENTANTE_BPM WHERE VESTADO = 'A' AND NCODREMITENTE=" + codigo + " ORDER BY VNOMBRE",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						RepresentanteBean bean4 = new RepresentanteBean();
						bean4.setCodrepresentante(rs.getInt("codigo"));
						bean4.setVnombre(rs.getString("representante"));
						results5.add(bean4);
					}
				});
		nowOut = System.currentTimeMillis();
		// logger.debug("["+usuario+"]"+"Final ENTRANTEDAO REPRESENTANTE:" + nowOut);
		nowDif = nowOut - nowIn;
		// logger.debug("["+usuario+"]"+"Duracion ENTRANTEDAO REPRESENTANTE:" + nowDif);
		return results5;
	}

	public List<EstadosBean> estados() {
		long nowIn = 0, nowOut = 0, nowDif = 0;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		nowIn = System.currentTimeMillis();
		// logger.debug("Inicio ENTRANTEDAO ESTADO :" + nowIn);
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
		// logger.debug("["+usuario+"]"+"Final ENTRANTEDAO ESTADO:" + nowOut);
		nowDif = nowOut - nowIn;
		// logger.debug("["+usuario+"]"+"Duracion ENTRANTEDAO ESTADO:" + nowDif);
		return results6;
	}

	public List<JefeBean> jefe(int area) {
		long nowIn = 0, nowOut = 0, nowDif = 0;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		nowIn = System.currentTimeMillis();
		// logger.debug("Inicio SALIDADAO JEFE:" + nowIn);
		final List<JefeBean> results9 = new ArrayList<JefeBean>();
		jdbcTemplate.query(
				"select nficha as ficha from atd_jefes_equipos where vcodestado='EIRC01' and ncodarea= " + area + " ",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						JefeBean bean = new JefeBean();
						bean.setFicha(rs.getInt("ficha"));
						results9.add(bean);
					}
				});
		nowOut = System.currentTimeMillis();
		// logger.debug("["+usuario+"]"+"Final SALIDADAO JEFE:" + nowOut);
		nowDif = nowOut - nowIn;
		// logger.debug("["+usuario+"]"+"Duracion SALIDADAO JEFE:" + nowDif);
		return results9;
	}

	@Override
	public String actualizaCombos(String nombreEmpresa, String codArea, String login) {
		long nowIn = 0, nowOut = 0, nowDif = 0;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		nowIn = System.currentTimeMillis();
		Map output = storedCombo.execute(nombreEmpresa, codArea, login);
		String out = (String) output.get("out");
		nowOut = System.currentTimeMillis();
		nowDif = nowOut - nowIn;
		// logger.debug("["+usuario+"]"+"Duracion S_GUIA.ATDC_NUEVO_REMITENTE_DOC:" +
		// nowDif);
		return out;

	}

	public void setStoredEntrantes(StoredEntrantes storedEntrantes) {

		this.storedEntrantes = storedEntrantes;
	}

	@Override
	public String actualizarSPEnt(EntranteBean entranteBean) {
		long nowIn = 0, nowOut = 0, nowDif = 0;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		nowIn = System.currentTimeMillis();
		Map output = storedActualizarEnt.execute(entranteBean);
		String out = (String) output.get("out");
		nowOut = System.currentTimeMillis();
		nowDif = nowOut - nowIn;
		// logger.debug("["+usuario+"]"+"Duracion S_GUIA.ATDC_UPDATE_DOC_ENTR:" +
		// nowDif);
		return out;
	}

	@Override
	public String nuevoSPEnt(EntranteBean entranteBean) {
		Map output = storednuevodocumentoentrada.execute(entranteBean);
		String out = (String) output.get("out");
		return out;

	}

	@Override
	public String eliminarSPEnt(String anno, String origen, String tipodoc, String codigo, String login) {
		Map output = storedEliminarEnt.execute(anno, origen, tipodoc, codigo, login);
		String out = (String) output.get("out");
		return out;
	}

	@Override
	public String actualizarLineaDigitalizacion(String anno, String correlativo, String ubicacion, String nloteN,
			String login) {
		Map output = storedactualizarlineadigitalizacionEnt.execute(anno, correlativo, ubicacion, nloteN, login);
		String out = (String) output.get("out");
		return out;
	}

	public List<EntranteBean> BusquedaSP(String area, String opcion, String detalle) {
		long nowIn = 0, nowOut = 0, nowDif = 0;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		nowIn = System.currentTimeMillis();
		Map output = storedbusquedaentrante.execute(area, opcion, detalle);
		nowOut = System.currentTimeMillis();
		nowDif = nowOut - nowIn;
		// logger.debug("["+usuario+"]"+"Duracion Stored ATDC_BUSQUEDA_DOC_ENT:" +
		// nowDif);
		List busqueda = (ArrayList) output.get("busquedas_entrantes");
		System.out.println("vuelvo de SP");
		System.out.println(busqueda);
		return busqueda;
	}

	public List<EntranteBean> ConsultaSP(String registro, String anodocumento) {
		long nowIn = 0, nowOut = 0, nowDif = 0;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		nowIn = System.currentTimeMillis();
		Map output = Storedconsultadocentrantes.execute(registro, anodocumento);
		nowOut = System.currentTimeMillis();
		nowDif = nowOut - nowIn;
		// logger.debug("["+usuario+"]"+"Duracion Stored ATDC_BUSQUEDA_DOC_ENT:" +
		// nowDif);
		List consulta = (ArrayList) output.get("consulta_entrantes");
		System.out.println("vuelvo de SP");
		System.out.println(consulta);
		return consulta;
	}

	public List<EntranteBean> BusquedaSeguimientoSP(String anno, String area, String tipodoc, String numdoc) {

		Map output = storedbusquedaseguimientodocentrantes.execute(anno, area, tipodoc, numdoc);
		List consulta = (ArrayList) output.get("consulta_seguimiento");
		System.out.println("vuelvo de SP");
		System.out.println(consulta);
		return consulta;
	}

	public void setStoredbusquedaseguimientodocentrantes(
			StoredBusquedaSeguimientoDocEntrantes storedbusquedaseguimientodocentrantes) {
		this.storedbusquedaseguimientodocentrantes = storedbusquedaseguimientodocentrantes;
	}

	public List<EntranteBean> BusquedaSeguimientoDocumentosSP(String area, String anno, String ncorrelativo) {

		Map output = storedbusquedaseguimientodocumentos.execute(area, anno, ncorrelativo);
		List consulta = (ArrayList) output.get("consulta_seguimiento");
		System.out.println("vuelvo de SP");
		System.out.println(consulta);
		return consulta;
	}

	public void setStoredbusquedaseguimientodocumentos(
			StoredBusquedaSeguimientoDocumentos storedbusquedaseguimientodocumentos) {
		this.storedbusquedaseguimientodocumentos = storedbusquedaseguimientodocumentos;
	}

	public List<SeguimientoEntranteBean> ConsultaSeguimiento(String registro, String anodocumento) {
		long nowIn = 0, nowOut = 0, nowDif = 0;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		nowIn = System.currentTimeMillis();
		Map output = Storedconsultadocentrantesseg.execute(registro, anodocumento);
		nowOut = System.currentTimeMillis();
		nowDif = nowOut - nowIn;
		// logger.debug("["+usuario+"]"+"Duracion Stored ATDC_BUSQUEDA_DOC_ENT:" +
		// nowDif);
		List seguimiento = (ArrayList) output.get("consulta_seguimiento");
		System.out.println("vuelvo de SP");
		System.out.println(seguimiento);
		return seguimiento;
	}

	@Override
	public List<EntranteBean> entrantes() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<EntranteBean> filtrosSP(String fecha1, String fecha2, String tipoopcion, String area,
			String item2Seleccionado, String areaseleccionado) {
		long nowIn = 0, nowOut = 0, nowDif = 0;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		nowIn = System.currentTimeMillis();

		Map output = storedFiltrosEnt.execute(fecha1, fecha2, tipoopcion, area, item2Seleccionado, areaseleccionado);
		nowOut = System.currentTimeMillis();
		nowDif = nowOut - nowIn;
		// logger.debug("["+usuario+"]"+"Duracion Stored S_GUIA.ATDC_FILTRO_ENTRANTE:" +
		// nowDif);
		List documentos = (ArrayList) output.get("filtros");
		return documentos;
	}

	public List<DocumentoDerivado> derivadosSP(String tipoConsulta, String area, String fechaInicial, String fechaFinal,
			String estado, String ficha, String areaLogin) {
		Map map = this.storedDocumentosDerivados.execute(tipoConsulta, area, fechaInicial, fechaFinal, estado, ficha,
				areaLogin);
		List derivados = (ArrayList) map.get("derivados");
		return derivados;
	}

	public List<AtencionDocSalidaBean> listadoAtencionDocssalida(String tipConsulta, String area, String fecIni,
			String fecFin, String estado) {
		Map resultMap = this.storedDocumentosDerivados.execute(tipConsulta, area, fecIni, fecFin, estado, "", "");
		List<AtencionDocSalidaBean> resultList = (ArrayList) resultMap.get("derivados");
		return resultList;
	}

	public List<TipoConsulta> getTiposConsulta() {
		// String sql= "SELECT VCODTIPO,VDESCRIPCION FROM TIPO WHERE
		// VOBSERVACIONES='CONSULTA ATD' AND VESTADO='A' ORDER BY VCODTIPO";
		String sql = "SELECT VCODTIPO,VDESCRIPCION FROM ATD_TIPO WHERE VOBSERVACIONES='CONSULTA ATD' AND NESTADO=1 ORDER BY VCODTIPO";
		System.out.println(sql);
		final List<TipoConsulta> result = new ArrayList<TipoConsulta>();
		jdbcTemplate.query(sql, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				TipoConsulta consulta = new TipoConsulta();
				consulta.setTipo(rs.getString("VCODTIPO"));
				consulta.setDescripcion(rs.getString("VDESCRIPCION"));
				result.add(consulta);
			}
		});
		return result;

	}

	public List<TiposBean> tipos_salida() {
		long nowIn = 0, nowOut = 0, nowDif = 0;
		int area;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		area = usuario.getCodarea();
		nowIn = System.currentTimeMillis();
		// logger.debug("Inicio SALIDADAO TIPOS:" + nowIn);
		final List<TiposBean> results = new ArrayList<TiposBean>();
		jdbcTemplate.query("SELECT VCODTIPO CODIGO,VDESCRIPCION DESCRIPCION FROM ATD_TIPO_DOC_AREA WHERE NCODAREA=1 "
				+ "UNION SELECT VCODTIPO CODIGO,VDESCRIPCION DESCRIPCION FROM ATD_TIPO_DOC_AREA WHERE NESTADO=1 AND NCODAREA="
				+ area + " ", new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						TiposBean bean2 = new TiposBean();
						bean2.setTipo(rs.getString("codigo"));
						bean2.setDescripcion(rs.getString("descripcion"));
						results.add(bean2);
					}
				});
		nowOut = System.currentTimeMillis();
		// logger.debug("["+usuario+"]"+"Final SALIDADAO TIPOS:" + nowOut);
		nowDif = nowOut - nowIn;
		// logger.debug("["+usuario+"]"+"Duracion SALIDADAO TIPOS:" + nowDif);
		return results;
	}

	public void setStoredNuevoEnt(StoredNuevoDocumentoEntrada storednuevodocumentoentrada) {
		this.storednuevodocumentoentrada = storednuevodocumentoentrada;
	}

	public void setStoredActualizarEnt(StoredActualizarEnt storedActualizarEnt) {
		this.storedActualizarEnt = storedActualizarEnt;
	}

	public void setStoredFiltrosEnt(StoredFiltrosEnt storedFiltrosEnt) {
		this.storedFiltrosEnt = storedFiltrosEnt;
	}

	public void setStoredEliminarEnt(StoredEliminarEnt storedEliminarEnt) {
		this.storedEliminarEnt = storedEliminarEnt;
	}

	public void setStoredbusquedaentrante(StoredBusquedaEntrantes storedbusquedaentrante) {
		this.storedbusquedaentrante = storedbusquedaentrante;
	}

	public StoredCombo getStoredCombo() {
		return storedCombo;
	}

	public void setStoredCombo(StoredCombo storedCombo) {
		this.storedCombo = storedCombo;
	}

	public void setStoredDocumentosDerivados(StoredDocumentosDerivados storedDocumentosDerivados) {
		this.storedDocumentosDerivados = storedDocumentosDerivados;
	}

	public void setStorednuevodocumentoentrada(StoredNuevoDocumentoEntrada storednuevodocumentoentrada) {
		this.storednuevodocumentoentrada = storednuevodocumentoentrada;
	}

	/* Agregado el 16/11/2011 - Alfredo Panitz */
	/* pasamos el email de email en SP */
	public List<EntranteBean> getListadoDocEntrUrgentes(int codArea) {

		Map map = this.storedenvioemail.execute(codArea);
		List resultList = (ArrayList) map.get("email");
		return resultList;
		/*
		 * List<Map> auxResultList = new ArrayList(); List<EntranteBean> resultList =
		 * new ArrayList();
		 * 
		 * auxResultList = jdbcTemplate.queryForList(
		 * "SELECT  DISTINCT ade.ncorrelativo ncorrelativo, "+
		 * "ade.vnumdoc vnumdoc,     "+ "ade.vtipodoc vtipodoc, "+
		 * "ade.dfecplazo fecplazo, "+ "ade.vasunto vasunto, "+
		 * "(SELECT tip.vdescripcion FROM tipo tip WHERE tip.vcodtipo = ade.vtipodoc) tipodoc, "
		 * + "ade.nremitente nremitente, "+ "CASE WHEN ADE.NORIGEN = 1 THEN "+
		 * "(SELECT  UPPER(D.VDESCRIPCION) FROM AREA D WHERE  D.NCODAREA = ADE.NDIRIGIDO) "
		 * + "ELSE "+
		 * "(SELECT UPPER(D.VDESCRIPCION) FROM ATD_REMITENTE D WHERE  D.NCODREMITENTE  = ADE.NDIRIGIDO) END vremitente, "
		 * + "ade.ndirigido ndirigido, "+ "UPPER((SELECT ar.vdescripcion "+
		 * "FROM area ar "+
		 * "WHERE ar.nindicador = 0 and ar.nestado in (0,4) AND ar.ctiparea IN  ('E ','G', 'D','P') and ar.ncodarea = ade.ndirigido)) vdirigido "
		 * + "FROM atd_doc_entr ade, "+ "atd_doc_entr_seg seg  "+
		 * "WHERE ade.nano=seg.nano "+ "and ade.ncorrelativo= seg.ncorrelativo "+
		 * "and ade.ncodarea = seg.narearemitente "+ "and seg.nplazo=1 "+
		 * "and ade.ncodarea = "+ codArea +" "+ "and ade.vestado <> 'ESTA004' "+
		 * "AND trunc(ade.dfecplazo) >= "+ "trunc(sysdate) "+
		 * "AND trunc(ade.dfecplazo) <= "+ "trunc(sysdate + 2)");
		 * 
		 * for (Map auxItem : auxResultList) { EntranteBean beanEntrante = new
		 * EntranteBean(); beanEntrante.setVnumdoc((String)auxItem.get("vnumdoc"));
		 * beanEntrante.setNcorrelativo(Long.parseLong(((BigDecimal)auxItem.get(
		 * "ncorrelativo")).toString()));
		 * beanEntrante.setVtipodoc((String)auxItem.get("vtipodoc"));
		 * beanEntrante.setTipodoc((String)auxItem.get("tipodoc"));
		 * beanEntrante.setVasunto((String)auxItem.get("vasunto"));
		 * beanEntrante.setNremitente(Integer.parseInt(((BigDecimal)auxItem.get(
		 * "nremitente")).toString()));
		 * beanEntrante.setVremitente((String)auxItem.get("vremitente"));
		 * beanEntrante.setNdirigido(Integer.parseInt(((BigDecimal)auxItem.get(
		 * "ndirigido")).toString()));
		 * beanEntrante.setVdirigido((String)auxItem.get("vdirigido")); SimpleDateFormat
		 * formateador = new SimpleDateFormat("dd/MM/yyyy");
		 * //beanEntrante.setDfecplazo(formateador.format((Date)
		 * auxItem.get("fecplazo")));
		 * beanEntrante.setFechaplazo(formateador.format((Date)
		 * auxItem.get("fecplazo"))); resultList.add(beanEntrante); }
		 * 
		 * return resultList;
		 */
	}

	/* Actualizar estado=3 26-10-2020 */

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
	public boolean updatevistaPDF(long correlativo, int nano) {
		Usuario usuario = null;
		String logeo;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		usuario = (Usuario) session.getAttribute("sUsuario");
		logeo = usuario.getLogin();
		String sql = "update atd_doc_entr set nrecepcion=3, dfecrecepcion=sysdate, vresrecepcion='" + logeo
				+ "' where ncorrelativo= " + correlativo + " and nano=" + nano + " ";

		int rs = jdbcTemplate.update(sql);
		if (rs > 0)
			return true;
		return false;
	}

	public void setStoredconsultadocentrantes(StoredConsultaDocEntrantes storedconsultadocentrantes) {
		Storedconsultadocentrantes = storedconsultadocentrantes;
	}

	public void setStoredconsultadocentrantesseg(StoredConsultaDocEntrantesSeg storedconsultadocentrantesseg) {
		Storedconsultadocentrantesseg = storedconsultadocentrantesseg;
	}

	public void setStoredalertaentrante(StoredAlertaEntrante storedalertaentrante) {
		this.storedalertaentrante = storedalertaentrante;
	}

	public void setStoredlistaasuntopopub(StoredListaAsuntoPopup storedlistaasuntopopub) {
		this.storedlistaasuntopopub = storedlistaasuntopopub;
	}

	public StoredBusquedaAsuntos getStoredbusquedaasuntos() {
		return storedbusquedaasuntos;
	}

	public void setStoredbusquedaasuntos(StoredBusquedaAsuntos storedbusquedaasuntos) {
		this.storedbusquedaasuntos = storedbusquedaasuntos;
	}

	public void setStoredenvioemail(StoredEnvioEmail storedenvioemail) {
		this.storedenvioemail = storedenvioemail;
	}

	public void setStoredactualizarlineadigitalizacionEnt(
			StoredActualizarLineadigitalizacionEnt storedactualizarlineadigitalizacionEnt) {
		this.storedactualizarlineadigitalizacionEnt = storedactualizarlineadigitalizacionEnt;
	}

	// SED-FON-161
	public List<TiposDocumentosBean> getTiposDocumentos() {
		long nowIn = 0, nowOut = 0, nowDif = 0;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		nowIn = System.currentTimeMillis();
		// logger.debug("Inicio ENTRANTEDAO REPRESENTANTE :" + nowIn);
		final List<TiposDocumentosBean> results5 = new ArrayList<TiposDocumentosBean>();
		jdbcTemplate.query("select vcodtipo, vdescripcion,vdato1 from tipo where vobservaciones = 'TIPO DOCUMENTO' "
				+ " and vestado = 'A' and vdato1 <> 'D' ", new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						TiposDocumentosBean bean4 = new TiposDocumentosBean();
						bean4.setCodigo(rs.getString("vcodtipo"));
						bean4.setDescripcion(rs.getString("vdescripcion"));
						bean4.setAbreviatura(rs.getString("vdato1"));
						results5.add(bean4);
					}
				});
		nowOut = System.currentTimeMillis();
		// logger.debug("["+usuario+"]"+"Final ENTRANTEDAO REPRESENTANTE:" + nowOut);
		nowDif = nowOut - nowIn;
		// logger.debug("["+usuario+"]"+"Duracion ENTRANTEDAO REPRESENTANTE:" + nowDif);
		return results5;
	}

	public List<RemitenteBPM> consultaRemitentesBPM(String tipo, String valor) {
		Map output = storedGetRemitentesBPM.execute(tipo, valor);
		List<RemitenteBPM> remitentes = new ArrayList<RemitenteBPM>();
		remitentes = (ArrayList) output.get("CUR_DATOS");
		return remitentes;
	}

	public List<RemitenteBPM> consultaAreas(String valor) {
		Map output = storedGetAreasByNombre.execute(valor);
		List<RemitenteBPM> remitentes = new ArrayList<RemitenteBPM>();
		remitentes = (ArrayList) output.get("CUR_DATOS");
		return remitentes;
	}

	public Integer registraRemitentesBPM(RemitenteBPM remitente) {
		Map output = storedNewRemitentesBPM.execute(remitente);
		Integer o_correlativo = (Integer) output.get("o_correlativo");
		String o_mensaje = (String) output.get("o_mensaje");
		Integer o_retorno = (Integer) output.get("o_retorno");
		if (o_retorno > 0) {
			System.out.println("error: " + o_mensaje);
			return 0;
		} else {
			return o_correlativo;
		}
	}

	public List<EntranteBean> obtenerEntrantesNuevos(String grupo, String fechaActual, int anio) {
		String sql = null;
		ResourceBundle bundle = ResourceBundle.getBundle("com.sedapal.tramite.files.parametros");
		String minutos = bundle.getString("refresh");
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		int area = 0;
		area = usuario.getNcodarea();
		ArrayList<EntranteBean> entrantes = new ArrayList<EntranteBean>();
		List<EntranteBean> documentos = new ArrayList<EntranteBean>();

		if (Integer.parseInt(grupo) == 6 || Integer.parseInt(grupo) == 10) {
			sql = "SELECT * FROM (SELECT  a.NCODAREA , a.NCORRELATIVO FROM ATD_DOC_ENTR a, AREA b, ATD_GRUPO_AREA c "
					+ " WHERE   a.NCODAREA = b.NCODAREA AND b.NCODAREA=c.NCODAREA AND c.NCODAREA=" + area
					+ " AND (24* 60* (to_date(to_char(SYSDATE, 'YYYY-MM-DD hh24:mi'), 'YYYY-MM-DD hh24:mi') - to_date(to_char(a.DFECCRE, 'YYYY-MM-DD hh24:mi'), "
					+ " 'YYYY-MM-DD hh24:mi')))<=" + minutos
					+ " AND a.VESTADO='ESTA001' UNION SELECT  a.NCODAREA , a.NCORRELATIVO "
					+ " FROM ATD_DOC_ENTR_DIRIGIDO a, AREA b, ATD_GRUPO_AREA c WHERE   a.NCODAREA = b.NCODAREA "
					+ " AND b.NCODAREA=c.NCODAREA AND c.NCODAREA=" + area + " AND "
					+ "(24* 60* (to_date(to_char(SYSDATE, 'YYYY-MM-DD hh24:mi') , 'YYYY-MM-DD hh24:mi') - to_date(to_char(a.DFECCRE, 'YYYY-MM-DD hh24:mi'), "
					+ " 'YYYY-MM-DD hh24:mi')))<=" + minutos + ")";
		}
		if (Integer.parseInt(grupo) == 2 || Integer.parseInt(grupo) == 7 || Integer.parseInt(grupo) == 8
				|| Integer.parseInt(grupo) == 1 || Integer.parseInt(grupo) == 5 || Integer.parseInt(grupo) == 3) {
			sql = "SELECT * FROM (SELECT  a.NCODAREA , a.NCORRELATIVO FROM ATD_DOC_ENTR a, AREA b"
					+ " WHERE   a.NCODAREA = b.NCODAREA AND b.NCODAREA=" + area
					+ " AND (24* 60* (to_date(to_char(SYSDATE, 'YYYY-MM-DD hh24:mi'), 'YYYY-MM-DD hh24:mi') - to_date(to_char(a.DFECCRE, 'YYYY-MM-DD hh24:mi'), "
					+ " 'YYYY-MM-DD hh24:mi')))<=" + minutos
					+ " AND a.VESTADO='ESTA001' UNION SELECT  a.NCODAREA , a.NCORRELATIVO "
					+ " FROM ATD_DOC_ENTR_DIRIGIDO a, AREA b WHERE   a.NCODAREA = b.NCODAREA " + " AND b.NCODAREA="
					+ area + " AND "
					+ "(24* 60* (to_date(to_char(SYSDATE, 'YYYY-MM-DD hh24:mi'), 'YYYY-MM-DD hh24:mi') - to_date(to_char(a.DFECCRE, 'YYYY-MM-DD hh24:mi'), "
					+ " 'YYYY-MM-DD hh24:mi')))<=" + minutos + ")";
		}

		this.jdbcTemplate.query(sql, new RowMapper() {
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				EntranteBean bean = new EntranteBean();
				bean.setNcorrelativo(rs.getInt("NCORRELATIVO"));
				bean.setNcodarea(rs.getInt("NCODAREA"));
				entrantes.add(bean);
				return bean;
			}
		});

		if (!entrantes.isEmpty()) {
			for (EntranteBean bean : entrantes) {
				EntranteBean nBean = obtenerEntrantesNuevosPorCorrelativo((int) bean.getNcorrelativo(), anio, area);
				if (nBean != null) {
					documentos.add(nBean);
				}
			}
		}

		return documentos;
	}

	private EntranteBean obtenerEntrantesNuevosPorCorrelativo(int codigo, int anio, int area) {
		ArrayList<EntranteBean> entrante = new ArrayList<EntranteBean>();

		String sql = "SELECT a.NCODAREA, a.NCORRELATIVO, a.vnumdoc, a.vnumexp, a.vasunto, "
				+ " CASE WHEN a.NORIGEN = 1 THEN "
				+ "        (SELECT  UPPER(D.VDESCRIPCION) FROM AREA D WHERE D.NCODAREA = a.NDIRIGIDO ) "
				+ "        ELSE "
				+ "        CASE NINDREMITENTE "
				+ "            WHEN 2 THEN "
				+ "                (SELECT UPPER(VNOMBRE) FROM ATD_REMITENTE_BPM DB WHERE DB.NCORRELATIVO = a.NDIRIGIDO) "
				+ "            ELSE "
				+ "                (SELECT UPPER(D.VDESCRIPCION) FROM ATD_REMITENTE D WHERE  D.NCODREMITENTE  = a.NDIRIGIDO) "
				+ "            END "
				+ "        END AREADIRIGIDO "
				+ " FROM ATD_DOC_ENTR a WHERE a.NCORRELATIVO = "
				+ codigo + " AND a.NANO = " + anio;

		this.jdbcTemplate.query(sql, new RowMapper() {

			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				EntranteBean doc = new EntranteBean();
				doc.setNcorrelativo(rs.getInt("NCORRELATIVO"));
				doc.setNcodarea(rs.getInt("NCODAREA"));
				doc.setVdirigido(rs.getString("AREADIRIGIDO"));
				doc.setVnumexp(rs.getString("vnumexp"));
				doc.setVasunto(rs.getString("vasunto"));
				doc.setVnumdoc(rs.getString("vnumdoc"));
				getEntradaDerivado((int) doc.getNcorrelativo(), anio, area, doc);
				entrante.add(doc);
				return doc;
			}

		});

		return !entrante.isEmpty() ? entrante.get(0) : null;
	}
	
	private void getEntradaDerivado(int codigo, int anio, int area, EntranteBean bean) {
		String sql = "select VOBSERVACION from atd_doc_entr_seg where ncorrelativo = " + codigo + " and nano = " + anio + " and nareaderivado = " + area;
		
		this.jdbcTemplate.query(sql, new RowMapper() {

			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				if (existeDocSalPorEntradaDerivado(rs.getString("VOBSERVACION"))) {
					bean.setVnumdoc(rs.getString("VOBSERVACION"));
				}
				return null;
			}

		});
	}
	
	private boolean existeDocSalPorEntradaDerivado(String numDoc) {
		String sql = "select count(*) as cantidad from ATD_DOC_SAL where vnumdoc = '" + numDoc + "'";
		int cantidad = this.jdbcTemplate.queryForInt(sql);
		return cantidad > 0;
	}

}
