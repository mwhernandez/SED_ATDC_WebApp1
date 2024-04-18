package com.sedapal.tramite.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

import com.google.gson.Gson;
import com.sedapal.tramite.beans.Anexo;
import com.sedapal.tramite.beans.AnioBean;
import com.sedapal.tramite.beans.AreaBean;
import com.sedapal.tramite.beans.DocumentoSalidaBean;
import com.sedapal.tramite.beans.EntranteBean;
import com.sedapal.tramite.beans.EstadosBean;
import com.sedapal.tramite.beans.GrupoBean;
import com.sedapal.tramite.beans.JefeBean;
import com.sedapal.tramite.beans.LlavesBean;
import com.sedapal.tramite.beans.OrigenBean;
import com.sedapal.tramite.beans.RegistroEntrada;
import com.sedapal.tramite.beans.RemitenteBPM;
import com.sedapal.tramite.beans.RemitenteBean;
import com.sedapal.tramite.beans.RepresentanteBean;
import com.sedapal.tramite.beans.SecretariaBean;
import com.sedapal.tramite.beans.ServidorBean;
import com.sedapal.tramite.beans.SignetParametro;
import com.sedapal.tramite.beans.TiposBean;
import com.sedapal.tramite.beans.TrabajadorBean;
import com.sedapal.tramite.beans.Usuario;
import com.sedapal.tramite.beans.Visante;
import com.sedapal.tramite.dao.IDocumentoSalidaDAO;
import com.sedapal.tramite.beans.Seguir;

public class DocumentoSalidaDAO implements IDocumentoSalidaDAO {

	private JdbcTemplate jdbcTemplate;
	private StoredDocumentoSaliente storeddocumentosaliente;
	private StoredActualizarDocumentoSalida storedactualizarDocumentoSalida;
	private StoredNuevoDocumentoSaliente storednuevoDocumentoSalida;
	private StoredFiltrosSalientes storedFiltrosSalientes;
	private StoredDeleteDocumentoSalida storeddeleteDocumentoSalida;
	private StoredBusquedaDocSalientes StoredBusquedaDocSalientes;
	private StoredCriteriosSalientes storedCriteriosSalientes;
	private StoredCombo storedCombo;
	private StoredEntrateSalida storedentratesalida;
	private StoredBusquedaEntrateSalida storedbusquedaentratesalida;
	private StoredRemitentesSP storedremitentessp;
	private StoredListaAsuntoPopup storedlistaasuntopopub;
	private StoredBusquedaAsuntos storedbusquedaasuntos;
	private StoredConsultaDocumentoBPM storedConsultaDocumentoBPM; // SED-REQ-00001
	private StoredTipoDocumentosSalida storedtipodocumentosalida;
	private StoredVisadoDocumentoSalida storedVisadoDocumentoSalida;
	private StoredFirmadoDocumentoSalida storedFirmadoDocumentoSalida;
	private StoredObservadoDocumentoSalida storedObservadoDocumentoSalida;
	private StoredAnexoDocumento storedAnexoDocumento;
	private StoredEliminarAnexoDocumento storedEliminarAnexoDocumento;
	private StoredAcuseDocumentoSalida storedAcuseDocumentoSalida;
	private StoredVisualizacionDocumentoSalida storedVisualizacionDocumentoSalida;
	private StoredDocSalRegEntrada storedDocSalRegEntrada;

	// private static Logger logger = Logger.getLogger("R1");

	public List<AreaBean> tipodocumentosalida(int li_ano, int li_codarea, long li_codigo) {

		Map output = storedtipodocumentosalida.execute(li_ano, li_codarea, li_codigo);
		final List tipodocumentosalida = (ArrayList) output.get("tipodocumentosalida");

		System.out.println("Estamos Aqui Eli- Tipos de Documentos");
		System.out.println(tipodocumentosalida);
		return tipodocumentosalida;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<LlavesBean> indicador(int nano, int correlativo) {
		final List<LlavesBean> results = new ArrayList<LlavesBean>();
		jdbcTemplate.query("select nindicador as codigo from atd_doc_entr where nano=" + nano + " and ncorrelativo = "
				+ correlativo + " ", new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						LlavesBean bean = new LlavesBean();
						bean.setCodigo(rs.getInt("codigo"));
						results.add(bean);
					}
				});
		return results;
	}

	public List<RepresentanteBean> representanteatdc(int codigo) {
		long nowIn = 0, nowOut = 0, nowDif = 0;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		nowIn = System.currentTimeMillis();
		// logger.debug("Inicio ENTRANTEDAO REPRESENTANTE :" + nowIn);
		final List<RepresentanteBean> results5 = new ArrayList<RepresentanteBean>();
		jdbcTemplate.query(
				// SED-FON-161
				// "SELECT NCODREPRESENTANTE AS codigo, UPPER(VNOMBRE) AS representante "+
				// "FROM ATD_REPRESENTANTE WHERE VESTADO = 'A' AND NCODREMITENTE="+codigo+"",
				"SELECT NCODREPRESENTANTE AS codigo, UPPER(VNOMBRE) AS representante "
						+ "FROM ATD_REPRESENTANTE WHERE VESTADO = 'A' AND NCODREMITENTE=" + codigo + "",
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

	@Override
	public List<DocumentoSalidaBean> documentoSP(String area, int fichaUsuario) {
		/// acediendo a sesion http
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		// String area;
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		area = String.valueOf(usuario.getCodarea());
		long nowIn = 0, nowOut = 0, nowDif = 0;
		nowIn = System.currentTimeMillis();
		// area="313";
		Map output = storeddocumentosaliente.execute(area, fichaUsuario);
		nowOut = System.currentTimeMillis();
		nowDif = nowOut - nowIn;
		// logger.debug("["+usuario+"]"+"Stored ATDC_DOCUMENTO_SALIENTE Duracion:" +
		// nowDif);
		List documento = (ArrayList) output.get("documento");
		return documento;
	}

	@Override
	public List<EntranteBean> documentoentradaSP(String area) {
		Map output = storedentratesalida.execute(area);
		List entradasalida = (ArrayList) output.get("entradasalida");
		return entradasalida;
	}

	public List<RemitenteBean> personaExterna(String cadenaDigitada) {

		Map output = storedremitentessp.execute(cadenaDigitada);
		List remitentesactivos = (ArrayList) output.get("remitentesactivos");
		System.out.println("Estamos Aqui Eli- Remitentes");
		System.out.println(remitentesactivos);
		return remitentesactivos;
	}

	public List<TiposBean> busqueda_asuntos_estandar(String opcion_asunto, String detalle_asunto) {
		Map output = storedbusquedaasuntos.execute(opcion_asunto, detalle_asunto);
		List busquedaasunto = (ArrayList) output.get("busquedaasunto");
		return busquedaasunto;
	}

	public List<TiposBean> tipoconsulta() {

		final List<TiposBean> results = new ArrayList<TiposBean>();
		jdbcTemplate.query(
				// "select vcodtipo as codigo, vdescripcion as descripcion from atd_tipo "
				// + "where vflat='FLT' and nestado=1 ORDER BY codigo",
				"select vcodtipo as codigo, vdescripcion as descripcion from atd_tipo "
						+ "where vflat='FIL' and nestado=1 ORDER BY codigo",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						TiposBean bean2 = new TiposBean();
						bean2.setTipo(rs.getString("codigo"));
						bean2.setDescripcion(rs.getString("descripcion"));
						results.add(bean2);
					}
				});

		return results;
	}

	public List<SecretariaBean> secretaria(int area) {
		long nowIn = 0, nowOut = 0, nowDif = 0;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		nowIn = System.currentTimeMillis();
		// logger.debug("Inicio SALIDADAO JEFE:" + nowIn);
		final List<SecretariaBean> results9 = new ArrayList<SecretariaBean>();
		jdbcTemplate.query(
				"select nficha as ficha, UPPER(VAPEPATERNO ||' '||VAPEMATERNO ||','||VNOMBRES) AS nombre, vcorreo as correo from atd_secretarias where vcodestado='EIRC01' and ncodarea= "
						+ area + " ",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						SecretariaBean bean = new SecretariaBean();
						bean.setFicha(rs.getInt("ficha"));
						bean.setVnombre(rs.getString("nombre"));
						bean.setVcorreo(rs.getString("correo"));
						results9.add(bean);
					}
				});
		nowOut = System.currentTimeMillis();
		// logger.debug("["+usuario+"]"+"Final SALIDADAO JEFE:" + nowOut);
		nowDif = nowOut - nowIn;
		// logger.debug("["+usuario+"]"+"Duracion SALIDADAO JEFE:" + nowDif);
		return results9;
	}

	public List<EntranteBean> expediente(int anno, long ncorrelativo) {
		long nowIn = 0, nowOut = 0, nowDif = 0;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		nowIn = System.currentTimeMillis();
		// logger.debug("Inicio SALIDADAO JEFE:" + nowIn);
		final List<EntranteBean> results11 = new ArrayList<EntranteBean>();
		jdbcTemplate.query(
				"select vnumexp from atd_doc_entr where nano=" + anno + " and ncorrelativo=" + ncorrelativo + "",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						EntranteBean bean = new EntranteBean();
						bean.setVnumexp(rs.getString("vnumexp"));
						results11.add(bean);
					}
				});
		nowOut = System.currentTimeMillis();
		// logger.debug("["+usuario+"]"+"Final SALIDADAO JEFE:" + nowOut);
		nowDif = nowOut - nowIn;
		// logger.debug("["+usuario+"]"+"Duracion SALIDADAO JEFE:" + nowDif);
		return results11;
	}

	public void setStoredremitentessp(StoredRemitentesSP storedremitentessp) {
		this.storedremitentessp = storedremitentessp;
	}

	public List<TiposBean> asuntos_estandares() {
		// storedlistaasunto
		Map output = storedlistaasuntopopub.execute();
		List asuntos = (ArrayList) output.get("asunto");
		return asuntos;
	}

	@Override
	public List<EntranteBean> busquedadocumentoentradaSP(String area, String opcion_entrada, String detalle_entrada) {
		Map output = storedbusquedaentratesalida.execute(area, opcion_entrada, detalle_entrada);
		List busquedaentradasalida = (ArrayList) output.get("busquedaentradasalida");
		return busquedaentradasalida;
	}

	@Transactional(propagation = Propagation.NEVER, readOnly = true)
	public List<AreaBean> areas() {
		long nowIn = 0, nowOut = 0, nowDif = 0;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		nowIn = System.currentTimeMillis();
		// logger.debug("Inicio SALIDADAO AREAS:" + nowIn);
		final List<AreaBean> result = new ArrayList<AreaBean>();
		jdbcTemplate.query(
				// "SELECT A.NCODAREA as codigo, A.NCODAREA||' - ' ||upper(A.VABREVIATURA) as
				// nombre "+
				// "FROM AREA A WHERE A.NINDICADOR = 0 AND A.CTIPAREA NOT IN ('SE', 'CC') ORDER
				// BY A.NCODAREA",
				"SELECT A.NCODAREA as codigo, upper(A.VABREVIATURA)||' - ' ||upper(A.VDESCRIPCION) as nombre "
						+ "FROM AREA A WHERE A.NINDICADOR = 0 AND A.NESTADO IN (0,4) AND A.CTIPAREA NOT IN ('SE', 'CC') ORDER BY A.VABREVIATURA",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						AreaBean bean1 = new AreaBean();
						bean1.setCodigo(rs.getInt("codigo"));
						bean1.setNombre(rs.getString("nombre"));
						result.add(bean1);
					}
				});
		nowOut = System.currentTimeMillis();
		// logger.debug("["+usuario+"]"+"Final SALIDADAO AREA:" + nowOut);
		nowDif = nowOut - nowIn;
		// logger.debug("["+usuario+"]"+"Duracion SALIDADAO AREA:" + nowDif);
		return result;

	}

	@Transactional(propagation = Propagation.NEVER, readOnly = true)
	public List<TiposBean> tipos() {
		long nowIn = 0, nowOut = 0, nowDif = 0;
		int area;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		area = usuario.getCodarea();
		nowIn = System.currentTimeMillis();
		// logger.debug("Inicio SALIDADAO TIPOS:" + nowIn);
		final List<TiposBean> results = new ArrayList<TiposBean>();
		jdbcTemplate.query(
				// "SELECT VCODTIPO codigo,VDESCRIPCION descripcion FROM TIPO WHERE VESTADO='A'
				// AND" +
				// " VOBSERVACIONES='TIPO DE DOCUMENTO ATD' ORDER BY VCODTIPO",
				"SELECT VCODTIPO CODIGO,VDESCRIPCION DESCRIPCION FROM ATD_TIPO_DOC_AREA WHERE NCODAREA=1 "
						+ "UNION SELECT VCODTIPO CODIGO,VDESCRIPCION DESCRIPCION FROM ATD_TIPO_DOC_AREA WHERE NESTADO=1 AND NCODAREA="
						+ area + " ",
				new RowCallbackHandler() {
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

	@Transactional(propagation = Propagation.NEVER, readOnly = true)
	public List<TiposBean> estadomantenimiento() {
		long nowIn = 0, nowOut = 0, nowDif = 0;
		int area;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		area = usuario.getCodarea();
		nowIn = System.currentTimeMillis();
		// logger.debug("Inicio SALIDADAO TIPOS:" + nowIn);
		final List<TiposBean> results = new ArrayList<TiposBean>();
		jdbcTemplate.query(
				// "SELECT VCODTIPO codigo,VDESCRIPCION descripcion FROM TIPO WHERE VESTADO='A'
				// AND" +
				// " VOBSERVACIONES='TIPO DE DOCUMENTO ATD' ORDER BY VCODTIPO",
				"SELECT VCODTIPO AS CODIGO, VDESCRIPCION AS DESCRIPCION FROM ATD_TIPO WHERE NESTADO=1  AND VFLAT='PSM' ORDER BY VCODTIPO",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						TiposBean bean2 = new TiposBean();
						bean2.setTipo(rs.getString("codigo"));
						bean2.setDescripcion(rs.getString("descripcion"));
						results.add(bean2);
					}
				});
		nowOut = System.currentTimeMillis();
		// logger.debug("["+usuario+"]"+"Final SALIDADAO ESTADO MANTENIMIENTO:" +
		// nowOut);
		nowDif = nowOut - nowIn;
		// logger.debug("["+usuario+"]"+"Duracion SALIDADAO ESTADO MANTENIMIENTO:" +
		// nowDif);
		return results;
	}

	@Transactional(propagation = Propagation.NEVER, readOnly = true)
	public List<TiposBean> estadoderivador() {
		long nowIn = 0, nowOut = 0, nowDif = 0;
		int area;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		area = usuario.getCodarea();
		nowIn = System.currentTimeMillis();
		// logger.debug("Inicio SALIDADAO TIPOS:" + nowIn);
		final List<TiposBean> results = new ArrayList<TiposBean>();
		jdbcTemplate.query(
				// "SELECT VCODTIPO codigo,VDESCRIPCION descripcion FROM TIPO WHERE VESTADO='A'
				// AND" +
				// " VOBSERVACIONES='TIPO DE DOCUMENTO ATD' ORDER BY VCODTIPO",
				"SELECT VCODTIPO AS CODIGO, VDESCRIPCION AS DESCRIPCION FROM ATD_TIPO WHERE NESTADO=1 AND VFLAT='PSD' ORDER BY VCODTIPO",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						TiposBean bean = new TiposBean();
						bean.setTipo(rs.getString("codigo"));
						bean.setDescripcion(rs.getString("descripcion"));
						results.add(bean);
					}
				});
		nowOut = System.currentTimeMillis();
		// logger.debug("["+usuario+"]"+"Final SALIDADAO ESTADO DERIVADOR:" + nowOut);
		nowDif = nowOut - nowIn;
		// logger.debug("["+usuario+"]"+"Duracion SALIDADAO ESTADO DERIVADOR:" +
		// nowDif);
		return results;
	}

	@Transactional(propagation = Propagation.NEVER, readOnly = true)
	public List<TiposBean> estadojefe() {
		long nowIn = 0, nowOut = 0, nowDif = 0;
		int area;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		area = usuario.getCodarea();
		nowIn = System.currentTimeMillis();
		// logger.debug("Inicio SALIDADAO TIPOS:" + nowIn);
		final List<TiposBean> results = new ArrayList<TiposBean>();
		jdbcTemplate.query(
				// "SELECT VCODTIPO codigo,VDESCRIPCION descripcion FROM TIPO WHERE VESTADO='A'
				// AND" +
				// " VOBSERVACIONES='TIPO DE DOCUMENTO ATD' ORDER BY VCODTIPO",
				"SELECT VCODTIPO AS CODIGO, VDESCRIPCION AS DESCRIPCION FROM ATD_TIPO WHERE NESTADO=1 AND VFLAT='PSJ' ORDER BY VCODTIPO",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						TiposBean bean = new TiposBean();
						bean.setTipo(rs.getString("codigo"));
						bean.setDescripcion(rs.getString("descripcion"));
						results.add(bean);
					}
				});
		nowOut = System.currentTimeMillis();
		// logger.debug("["+usuario+"]"+"Final SALIDADAO ESTADO DERIVADOR:" + nowOut);
		nowDif = nowOut - nowIn;
		// logger.debug("["+usuario+"]"+"Duracion SALIDADAO ESTADO DERIVADOR:" +
		// nowDif);
		return results;
	}

	public List<AnioBean> anio() {

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

	@Override
	public void eliminarSP(String codigo, String anno, String origen, String tipodoc, String area, String login) {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		long nowIn = 0, nowOut = 0, nowDif = 0;
		nowIn = System.currentTimeMillis();
		Map output = storeddeleteDocumentoSalida.execute(codigo, anno, origen, tipodoc, area, login);
		nowOut = System.currentTimeMillis();
		nowDif = nowOut - nowIn;
		// logger.debug("["+usuario+"]"+"Stored ATDC_DELETE_DOCUMENTO_SALIENTE
		// Duracion:" + nowDif);
	}

	@Transactional(propagation = Propagation.NEVER, readOnly = true)
	public List<TrabajadorBean> trabajador(int area) {
		long nowIn = 0, nowOut = 0, nowDif = 0;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		nowIn = System.currentTimeMillis();
		// logger.debug("Inicio SALIDADAO TRABAJADOR:" + nowIn);
		final List<TrabajadorBean> results1 = new ArrayList<TrabajadorBean>();
		jdbcTemplate.query(
				// "select nficha as ficha, VAPEPATERNO ||' '||VAPEMATERNO||','||VNOMBRES AS
				// nombre from trabajador " +
				// "where vcodestado in ('EIRC01','EIRC03') and ind_email='0' and vcodtipo in
				// ('TIPT001','TIPT005') and ncodarea= "+area+"order by VAPEPATERNO",
				"select nficha as ficha, UPPER(VAPEPATERNO ||' '||VAPEMATERNO ||','||VNOMBRES) AS nombre "
						+ "from trabajador where vcodestado in ('EIRC01','EIRC03') and IND_EMAIL in (0) and ncodarea= "
						+ area + "order by VAPEPATERNO",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						TrabajadorBean bean2 = new TrabajadorBean();
						bean2.setFicha(rs.getLong("ficha"));
						bean2.setNombre_completo(rs.getString("nombre"));
						results1.add(bean2);
					}
				});
		nowOut = System.currentTimeMillis();
		// logger.debug("["+usuario+"]"+"Final SALIDADAO TRABAJADOR:" + nowOut);
		nowDif = nowOut - nowIn;
		// logger.debug("["+usuario+"]"+"Duracion SALIDADAO TRABAJADOR:" + nowDif);
		return results1;

	}

	@Transactional(propagation = Propagation.NEVER, readOnly = true)

	public List<AreaBean> areas_seleccionadas(int anio, int origen, String tipodoc, int area, int correlativo) {

		long nowIn = 0, nowOut = 0, nowDif = 0;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		nowIn = System.currentTimeMillis();
		// logger.debug("Inicio SALIDADAO AREAS_SELECCIONADAS:" + nowIn);
		final List<AreaBean> result = new ArrayList<AreaBean>();
		jdbcTemplate.query(

				/* QUERY DE MANUEL COCA 01.11.2023 */
				/*
				 * "SELECT TIPO1, NSECUENCIAL, CODIGO, NOMBRE, FICHA, NOMBRECOMPLETO, TIPO " +
				 * "FROM ( " + "SELECT  'externo' tipo1, " + "S.NSECUENCIAL NSECUENCIAL, " +
				 * "D.NDIRIGIDO as codigo,  " + "CASE WHEN D.NINDREMITENTE = 2 THEN " +
				 * "(SELECT UPPER(VNOMBRE) FROM ATD_REMITENTE_BPM DB WHERE DB.NCORRELATIVO = S.NAREADIRIGIDO AND D.NORIGEN = 2) "
				 * + "WHEN S.NINDREMITENTE = 1 THEN " +
				 * "(SELECT UPPER(D.VDESCRIPCION) FROM ATD_REMITENTE D where D.NCODREMITENTE  = S.NAREADIRIGIDO AND D.NORIGEN = 2) "
				 * +
				 * "WHEN S.NINDREMITENTE IS NULL THEN (SELECT UPPER(A.VDESCRIPCION) FROM AREA A where A.NCODAREA  = S.NAREADIRIGIDO AND D.NORIGEN = 2) "
				 * + "END nombre, " + "CASE WHEN D.NINDREMITENTE = 2 THEN " +
				 * "(SELECT DB.NCORRELATIVO FROM ATD_REMITENTE_BPM DB WHERE DB.NCORRELATIVO = S.NAREADIRIGIDO AND D.NORIGEN = 2) "
				 * + "WHEN S.NINDREMITENTE = 1 THEN " +
				 * "(SELECT D.NCODREMITENTE FROM ATD_REMITENTE D where D.NCODREMITENTE  = S.NAREADIRIGIDO AND D.NORIGEN = 2) "
				 * + "WHEN S.NINDREMITENTE IS NULL THEN " +
				 * "(SELECT  TR.NFICHA FROM TRABAJADOR TR WHERE  S.NFICHADERIVADO=TR.NFICHA AND TR.IND_EMAIL IN (0) AND  TR.VCODESTADO IN ('EIRC01','EIRC02','EIRC03')) "
				 * + "END FICHA, " + "CASE WHEN D.NINDREMITENTE = 2 THEN " +
				 * "(SELECT UPPER(DB.VNOMBRE) FROM ATD_REPRESENTANTE_BPM DB WHERE DB.NCODREMITENTE = S.NAREADIRIGIDO AND D.NORIGEN = 2) "
				 * + "WHEN S.NINDREMITENTE = 1 THEN " +
				 * "(SELECT UPPER(D.VDESCRIPCION) FROM ATD_REMITENTE D where D.NCODREMITENTE  = S.NAREADIRIGIDO AND D.NORIGEN = 2) "
				 * + "WHEN S.NINDREMITENTE IS NULL THEN " +
				 * "(SELECT  TR.VNOMBRES ||' ' || TR.VAPEPATERNO ||' ' ||TR.VAPEMATERNO FROM TRABAJADOR TR WHERE  S.NFICHADERIVADO=TR.NFICHA AND TR.IND_EMAIL IN (0) AND  TR.VCODESTADO IN ('EIRC01','EIRC02','EIRC03')) "
				 * +
				 * "END nombrecompleto, s.vtipo as tipo FROM ATD_DOC_SAL D, ATD_DOC_SAL_DIRIGIDO S, AREA R, TIPO T, ATD_TIPO P "
				 * + "WHERE  D.NCODAREA="+area+" AND D.NANO="+anio+" AND D.NCODSAL="
				 * +correlativo+" AND D.NCODAREA= R.NCODAREA  AND R.CTIPAREA IN  ('E ','G', 'D','P') AND D.VTIPODOC=T.VCODTIPO AND D.VESTADO=P.VCODTIPO AND "
				 * +
				 * "P.VFLAT='SAL' AND  D.VESTADO<>'ESTA004' AND D.NCODAREA= S.NCODAREA AND D.NANO = S.NANO AND D.NCODSAL = S.NCODSAL AND S.NESTADO=1 AND S.VTIPO IN ('CC','AA') AND D.NORIGEN = 2 "
				 * + "UNION " +
				 * "SELECT   'interno' tipo1, S.NSECUENCIAL NSECUENCIAL, S.NAREADIRIGIDO as codigo,  (SELECT  UPPER(D.VDESCRIPCION) FROM AREA D WHERE  D.NCODAREA = S.NAREADIRIGIDO AND D.NORIGEN = 1 ) nombre, "
				 * +
				 * "(SELECT  TR.NFICHA FROM TRABAJADOR TR WHERE  S.NFICHADERIVADO=TR.NFICHA AND TR.IND_EMAIL IN (0) AND  TR.VCODESTADO IN ('EIRC01','EIRC02','EIRC03')) FICHA, "
				 * +
				 * "(SELECT  TR.VNOMBRES ||' ' || TR.VAPEPATERNO ||' ' ||TR.VAPEMATERNO FROM TRABAJADOR TR WHERE  S.NFICHADERIVADO=TR.NFICHA AND TR.IND_EMAIL IN (0) AND  TR.VCODESTADO IN ('EIRC01','EIRC02','EIRC03')) nombrecompleto, "
				 * +
				 * "s.vtipo as tipo FROM ATD_DOC_SAL D, ATD_DOC_SAL_DIRIGIDO S, AREA R, TIPO T, ATD_TIPO P "
				 * + "WHERE  D.NCODAREA="+area+" AND D.NANO="+anio+" AND D.NCODSAL="
				 * +correlativo+" AND D.NCODAREA= R.NCODAREA  AND R.CTIPAREA IN  ('E ','G', 'D','P') AND D.VTIPODOC=T.VCODTIPO AND "
				 * +
				 * "D.VESTADO=P.VCODTIPO AND P.VFLAT='SAL' AND  D.VESTADO<>'ESTA004' AND D.NCODAREA= S.NCODAREA AND D.NANO = S.NANO AND D.NCODSAL = S.NCODSAL AND S.NESTADO=1 AND "
				 * + "S.VTIPO IN ('CC','AA') AND D.NORIGEN = 1) ORDER BY NSECUENCIAL " ,
				 */

				"SELECT TIPO1,NSECUENCIAL,CODIGO,NOMBRE,FICHA,NOMBRECOMPLETO, "
						+ "TIPO FROM (SELECT  'externo' tipo1, S.NSECUENCIAL NSECUENCIAL, D.NDIRIGIDO as codigo, "
						+ "CASE WHEN D.NINDREMITENTE = 2 THEN (SELECT UPPER(VNOMBRE) FROM ATD_REMITENTE_BPM DB WHERE DB.NCORRELATIVO = S.NAREADIRIGIDO AND D.NORIGEN = 2 AND S.VTIPO='AA') "
						+ "WHEN D.NINDREMITENTE = 1 THEN (SELECT UPPER(D.VDESCRIPCION) FROM ATD_REMITENTE D where D.NCODREMITENTE  = S.NAREADIRIGIDO AND D.NORIGEN = 2 AND S.VTIPO='AA') END nombre, "
						+ "CASE WHEN D.NINDREMITENTE = 2 THEN (SELECT DB.NCORRELATIVO FROM ATD_REMITENTE_BPM DB WHERE DB.NCORRELATIVO = S.NAREADIRIGIDO AND D.NORIGEN = 2) "
						+ "WHEN D.NINDREMITENTE = 1 THEN (SELECT D.NCODREPRESENTANTE FROM ATD_REPRESENTANTE D where D.NCODREMITENTE  = S.NAREADIRIGIDO AND NCODREPRESENTANTE=1 AND D.NORIGEN = 2 AND S.VTIPO='AA')  END FICHA, "
						+ "CASE WHEN D.NINDREMITENTE = 2 THEN (SELECT UPPER(DB.VNOMBRE) FROM ATD_REPRESENTANTE_BPM DB WHERE DB.NCODREMITENTE = S.NAREADIRIGIDO AND D.NORIGEN = 2) "
						+ "WHEN D.NINDREMITENTE = 1 THEN (SELECT UPPER(D.VDESCRIPCION) FROM ATD_REMITENTE D where D.NCODREMITENTE  = S.NAREADIRIGIDO AND D.NORIGEN = 2 AND S.VTIPO='AA') "
						+ "END nombrecompleto, " + "s.vtipo as tipo "
						+ "FROM ATD_DOC_SAL D, ATD_DOC_SAL_DIRIGIDO S, AREA R, TIPO T, ATD_TIPO P WHERE D.NCODAREA="
						+ area + " AND D.NANO=" + anio + " AND " + "D.NCODSAL=" + correlativo
						+ " AND D.NCODAREA= R.NCODAREA  AND R.CTIPAREA IN  ('E ','G', 'D','P') AND D.VTIPODOC=T.VCODTIPO AND D.VESTADO=P.VCODTIPO AND P.VFLAT='SAL' AND "
						+ "D.VESTADO<>'ESTA004' AND D.NCODAREA= S.NCODAREA AND D.NANO = S.NANO AND D.NCODSAL = S.NCODSAL AND S.NESTADO=1 AND S.VTIPO IN ('AA') AND D.NORIGEN = 2 "
						+ "UNION " + "SELECT   'interno' tipo1, S.NSECUENCIAL NSECUENCIAL, S.NAREADIRIGIDO as codigo,  "
						+ "(SELECT  UPPER(D.VDESCRIPCION) FROM AREA D WHERE  D.NCODAREA = S.NAREADIRIGIDO AND D.NORIGEN = 1 ) nombre, "
						+ "(SELECT  TR.NFICHA FROM TRABAJADOR TR WHERE  S.NFICHADERIVADO=TR.NFICHA AND TR.IND_EMAIL IN (0) AND  TR.VCODESTADO IN ('EIRC01','EIRC02','EIRC03')) FICHA, "
						+ "(SELECT  TR.VNOMBRES ||' ' || TR.VAPEPATERNO ||' ' ||TR.VAPEMATERNO FROM TRABAJADOR TR WHERE  S.NFICHADERIVADO=TR.NFICHA AND TR.IND_EMAIL IN (0) AND  TR.VCODESTADO IN ('EIRC01','EIRC02','EIRC03')) nombrecompleto, "
						+ "s.vtipo as tipo FROM ATD_DOC_SAL D, ATD_DOC_SAL_DIRIGIDO S, AREA R, TIPO T, ATD_TIPO P "
						+ "WHERE  D.NCODAREA=" + area + " AND D.NANO=" + anio + " AND D.NCODSAL=" + correlativo
						+ " AND D.NCODAREA= R.NCODAREA  AND R.CTIPAREA IN  ('E ','G', 'D','P') AND D.VTIPODOC=T.VCODTIPO AND "
						+ "D.VESTADO=P.VCODTIPO AND P.VFLAT='SAL' AND  D.VESTADO<>'ESTA004' AND D.NCODAREA= S.NCODAREA AND D.NANO = S.NANO AND D.NCODSAL = S.NCODSAL AND S.NESTADO=1 AND "
						+ "S.VTIPO IN ('CC','AA') AND D.NORIGEN = 1 " + "UNION " + "SELECT " + "'externo' tipo3, "
						+ "S.NSECUENCIAL NSECUENCIAL, " + "S.NAREADIRIGIDO as codigo, "
						+ "CASE WHEN D.NINDREMITENTE IN (1,2) THEN (SELECT  UPPER(D.VDESCRIPCION) FROM AREA D WHERE  D.NCODAREA = S.NAREADIRIGIDO AND D.NORIGEN = 2 ) END nombre, "
						+ "CASE WHEN D.NINDREMITENTE IN (1,2) THEN (SELECT  TR.NFICHA FROM TRABAJADOR TR WHERE  S.NFICHADERIVADO=TR.NFICHA AND TR.IND_EMAIL IN (0) AND  TR.VCODESTADO IN ('EIRC01','EIRC02','EIRC03'))  END FICHA, "
						+ "CASE WHEN D.NINDREMITENTE IN (1,2) THEN (SELECT  TR.VNOMBRES ||' ' || TR.VAPEPATERNO ||' ' ||TR.VAPEMATERNO FROM TRABAJADOR TR WHERE  S.NFICHADERIVADO=TR.NFICHA AND TR.IND_EMAIL IN ('0') AND "
						+ "TR.VCODESTADO IN ('EIRC01','EIRC02','EIRC03') ) END nombrecompleto, "
						+ "s.vtipo as tipo FROM ATD_DOC_SAL D, ATD_DOC_SAL_DIRIGIDO S, AREA R, TIPO T, ATD_TIPO P WHERE D.NCODAREA="
						+ area + " AND D.NANO=" + anio + " AND  " + "D.NCODSAL=" + correlativo
						+ " AND D.NCODAREA= R.NCODAREA  AND R.CTIPAREA IN  ('E ','G', 'D','P') AND D.VTIPODOC=T.VCODTIPO AND D.VESTADO=P.VCODTIPO AND P.VFLAT='SAL' AND "
						+ "D.VESTADO<>'ESTA004' AND D.NCODAREA= S.NCODAREA AND D.NANO = S.NANO AND D.NCODSAL = S.NCODSAL AND S.NESTADO=1 AND S.VTIPO IN ('CC') AND D.NORIGEN = 2)  ORDER BY NSECUENCIAL ",

				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						AreaBean bean1 = new AreaBean();
						bean1.setCodigo(rs.getInt("codigo"));
						bean1.setTipo(rs.getString("tipo"));
						bean1.setNombre(rs.getString("nombre"));
						bean1.setAbreviatura(rs.getString("nombrecompleto"));
						bean1.setFicha(rs.getLong("ficha"));
						result.add(bean1);
					}
				});
		nowOut = System.currentTimeMillis();
		// logger.debug("["+usuario+"]"+"Final SALIDADAO AREAS_SELECCIONADAS:" +
		// nowOut);
		nowDif = nowOut - nowIn;
		// logger.debug("["+usuario+"]"+"Duracion SALIDADAO AREAS_SELECCIONADAS:" +
		// nowDif);

		return result;

	}

	@Transactional(propagation = Propagation.NEVER, readOnly = true)
	public List<RemitenteBean> remitentes_seleccionadas(int anio, int origen, String tipodoc, int area,
			int correlativo) {
		long nowIn = 0, nowOut = 0, nowDif = 0;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		nowIn = System.currentTimeMillis();
		// logger.debug("Inicio SALIDADAO REMITENTES_SELECCIONADAS:" + nowIn);
		final List<RemitenteBean> result = new ArrayList<RemitenteBean>();
		jdbcTemplate.query("SELECT S.NAREADIRIGIDO as codigo,upper(A.VDESCRIPCION) as nombre "
				+ "FROM ATD_DOC_SAL_DIRIGIDO S, ATD_REMITENTE A "
				+ "WHERE S.NAREADIRIGIDO=A.NCODREMITENTE AND A.VESTADO='A' AND S.NANO=" + anio + " AND " + " S.NORIGEN="
				+ origen + " AND S.VTIPODOC='" + tipodoc + "' AND S.NCODAREA=" + area + " AND S.NCODSAL=" + correlativo,

				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						RemitenteBean bean1 = new RemitenteBean();
						bean1.setCodigo(rs.getInt("codigo"));
						bean1.setDescripcion(rs.getString("nombre"));
						result.add(bean1);
					}
				});
		nowOut = System.currentTimeMillis();
		// logger.debug("["+usuario+"]"+"Final SALIDADAO REMITENTES_SELECCIONADAS:" +
		// nowOut);
		nowDif = nowOut - nowIn;
		// logger.debug("["+usuario+"]"+"Duracion SALIDADAO REMITENTES_SELECCIONADAS:" +
		// nowDif);
		return result;
	}

	@Transactional(propagation = Propagation.NEVER, readOnly = true)
	public List<EntranteBean> entrante(int areas) {
		long nowIn = 0, nowOut = 0, nowDif = 0;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		nowIn = System.currentTimeMillis();
		// logger.debug("Inicio SALIDADAO ENTRANTE:" + nowIn);
		final List<EntranteBean> results2 = new ArrayList<EntranteBean>();
		jdbcTemplate.query("SELECT D.NCORRELATIVO AS CORRELATIVO,E.VNUMDOC AS NUMDOC, E.VASUNTO AS ASUNTO "
				+ "FROM ATD_DOC_ENTR_DIRIGIDO D,ATD_DOC_ENTR E " + "WHERE D.NCODAREA=" + areas
				+ " AND D.NANO=E.NANO AND D.NORIGEN=E.NORIGEN AND D.VTIPODOC=E.VTIPODOC AND "
				+ "D.NCORRELATIVO=E.NCORRELATIVO AND E.VESTADO='ESTA003' AND E.NINDICA_SALIDA=0 ORDER BY D.NCORRELATIVO DESC",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						EntranteBean bean3 = new EntranteBean();
						bean3.setNcorrelativo(rs.getInt("correlativo"));
						bean3.setVnumdoc(rs.getString("numdoc"));
						bean3.setVasunto(rs.getString("asunto"));
						results2.add(bean3);
					}
				});
		nowOut = System.currentTimeMillis();
		// logger.debug("["+usuario+"]"+"Final SALIDADAO ENTRANTE:" + nowOut);
		nowDif = nowOut - nowIn;
		// logger.debug("["+usuario+"]"+"Duracion SALIDADAO ENTRANTE:" + nowDif);
		return results2;
	}

	@Transactional(propagation = Propagation.NEVER, readOnly = true)
	public List<EntranteBean> entrante_buscar(int areas, long correlativo) {
		long nowIn = 0, nowOut = 0, nowDif = 0;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		nowIn = System.currentTimeMillis();
		// logger.debug("Inicio SALIDADAO ENTRANTE:" + nowIn);
		final List<EntranteBean> results2 = new ArrayList<EntranteBean>();
		jdbcTemplate.query("SELECT D.NCORRELATIVO AS CORRELATIVO,E.VNUMDOC AS NUMDOC, E.VASUNTO AS ASUNTO "
				+ "FROM ATD_DOC_ENTR_DIRIGIDO D,ATD_DOC_ENTR E " + "WHERE D.NCODAREA=" + areas
				+ " AND D.NANO=E.NANO AND D.NORIGEN=E.NORIGEN AND D.VTIPODOC=E.VTIPODOC AND "
				+ "D.NCORRELATIVO=E.NCORRELATIVO AND E.VESTADO='ESTA003' AND E.NINDICA_SALIDA=0 AND D.NCORRELATIVO= "
				+ correlativo + " ORDER BY D.NCORRELATIVO DESC", new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						EntranteBean bean3 = new EntranteBean();
						bean3.setNcorrelativo(rs.getInt("correlativo"));
						bean3.setVnumdoc(rs.getString("numdoc"));
						bean3.setVasunto(rs.getString("asunto"));
						results2.add(bean3);
					}
				});
		nowOut = System.currentTimeMillis();
		// logger.debug("["+usuario+"]"+"Final SALIDADAO ENTRANTE:" + nowOut);
		nowDif = nowOut - nowIn;
		// logger.debug("["+usuario+"]"+"Duracion SALIDADAO ENTRANTE:" + nowDif);
		return results2;

	}

	@Transactional(propagation = Propagation.NEVER, readOnly = true)
	public List<TiposBean> criterio() {
		long nowIn = 0, nowOut = 0, nowDif = 0;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		nowIn = System.currentTimeMillis();
		// logger.debug("Inicio SALIDADAO TIPO:" + nowIn);
		final List<TiposBean> results3 = new ArrayList<TiposBean>();
		jdbcTemplate.query(
				"SELECT VCODTIPO codigo,VDESCRIPCION descripcion FROM TIPO  WHERE VESTADO='A' AND VOBSERVACIONES='CRITERIO' ORDER BY VCODTIPO",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						TiposBean bean4 = new TiposBean();
						bean4.setTipo(rs.getString("codigo"));
						bean4.setDescripcion(rs.getString("descripcion"));
						results3.add(bean4);
					}
				});
		nowOut = System.currentTimeMillis();
		// logger.debug("["+usuario+"]"+"Final SALIDADAO TIPO:" + nowOut);
		nowDif = nowOut - nowIn;
		// logger.debug("["+usuario+"]"+"Duracion SALIDADAO TIPO:" + nowDif);
		return results3;
	}

	@Transactional(propagation = Propagation.NEVER, readOnly = true)
	public List<TrabajadorBean> trabajador_remitente(int area_origen) {
		long nowIn = 0, nowOut = 0, nowDif = 0;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		nowIn = System.currentTimeMillis();
		// logger.debug("Inicio SALIDADAO TRABAJADOR_REMITENTE:" + nowIn);
		final List<TrabajadorBean> results5 = new ArrayList<TrabajadorBean>();
		jdbcTemplate.query(
				// "select nficha as ficha, UPPER(VAPEPATERNO ||' '||VAPEMATERNO
				// ||','||VNOMBRES) AS nombre from trabajador where " +
				// "vcodestado IN ('EIRC01','EIRC02','EIRC03') and ncodarea=
				// "+area_origen+"order by VAPEPATERNO",
				"select nficha as ficha, UPPER(VAPEPATERNO ||' '||VAPEMATERNO ||','||VNOMBRES) AS nombre from trabajador where "
						+ "vcodestado IN ('EIRC01','EIRC02','EIRC03') order by VAPEPATERNO",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						TrabajadorBean bean5 = new TrabajadorBean();
						bean5.setFicha(rs.getLong("ficha"));
						bean5.setNombre_completo(rs.getString("nombre"));
						results5.add(bean5);
					}
				});
		nowOut = System.currentTimeMillis();
		// logger.debug("["+usuario+"]"+"Final SALIDADAO TRABAJADOR_REMITENTE:" +
		// nowOut);
		nowDif = nowOut - nowIn;
		// logger.debug("["+usuario+"]"+"Duracion SALIDADAO TRABAJADOR_REMITENTE:" +
		// nowDif);
		return results5;
	}

	@Transactional(propagation = Propagation.NEVER, readOnly = true)
	public List<RemitenteBean> remitentes() {
		long nowIn = 0, nowOut = 0, nowDif = 0;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		nowIn = System.currentTimeMillis();
		// logger.debug("Inicio SALIDADAO REMITENTES:" + nowIn);
		final List<RemitenteBean> result1 = new ArrayList<RemitenteBean>();
		jdbcTemplate.query(
				// SED-FON-161
				// "SELECT NCODREMITENTE AS codigo, UPPER(VDESCRIPCION) AS nombre "+
				// "FROM ATD_REMITENTE WHERE VESTADO = 'A' AND NINDICADOR = 1 ORDER BY
				// VDESCRIPCION",
				"SELECT NCORRELATIVO AS codigo, UPPER(VNOMBRE) AS nombre "
						+ "FROM ATD_REMITENTE_BPM  ORDER BY VNOMBRE ",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						RemitenteBean bean2 = new RemitenteBean();
						bean2.setCodigo(rs.getInt("codigo"));
						bean2.setDescripcion(rs.getString("nombre"));
						result1.add(bean2);
					}
				});
		nowOut = System.currentTimeMillis();
		// logger.debug("["+usuario+"]"+"Final SALIDADAO REMITENTES:" + nowOut);
		nowDif = nowOut - nowIn;
		// logger.debug("["+usuario+"]"+"Duracion SALIDADAO REMITENTES:" + nowDif);
		return result1;
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
				"SELECT VCODTIPO codigo,VDESCRIPCION descripcion FROM ATD_TIPO WHERE NESTADO=1 AND VFLAT='ORI' ORDER BY VCODTIPO",
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

	public List<TiposBean> tipodoc(int area, String ntipodoc) {
		long nowIn = 0, nowOut = 0, nowDif = 0;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		nowIn = System.currentTimeMillis();
		// logger.debug("Inicio SALIDADAO ORIGEN:" + nowIn);
		final List<TiposBean> results9 = new ArrayList<TiposBean>();
		jdbcTemplate.query("SELECT VDESCRIPCION FROM ATD_TIPO_DOC_AREA WHERE NESTADO = 1 AND NCODAREA=" + area
				+ " AND VCODTIPO='" + ntipodoc + "'", new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						TiposBean bean9 = new TiposBean();
						bean9.setDescripcion(rs.getString("vdescripcion"));
						results9.add(bean9);
					}
				});
		nowOut = System.currentTimeMillis();
		// logger.debug("["+usuario+"]"+"Final SALIDADAO ORIGEN:" + nowOut);
		nowDif = nowOut - nowIn;
		// logger.debug("["+usuario+"]"+"Duracion SALIDADAO ORIGEN:" + nowDif);
		return results9;

	}

	@Transactional(propagation = Propagation.NEVER, readOnly = true)
	public List<ServidorBean> servidor() {
		long nowIn = 0, nowOut = 0, nowDif = 0;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		nowIn = System.currentTimeMillis();
		// logger.debug("Inicio SALIDADAO SERVIDOR:" + nowIn);
		final List<ServidorBean> results9 = new ArrayList<ServidorBean>();
		jdbcTemplate.query(
				// "SELECT VALOR1 AS DESCRIPCION FROM ATD_PARAMETROS WHERE NVALOR=2 AND
				// NESTADO=1 AND NVALOR2=2",
				// "SELECT VALOR1 AS DESCRIPCION FROM ATD_PARAMETROS WHERE NVALOR=2 AND
				// NESTADO=1 AND NVALOR2=6",
				"SELECT VALOR1 AS DESCRIPCION FROM ATD_PARAMETROS WHERE VOBSERVACIONES='SERVIDORNEW' AND NVALOR=2 AND NESTADO=1 AND NVALOR2=6",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						ServidorBean bean9 = new ServidorBean();
						bean9.setDescripcion(rs.getString("descripcion"));
						results9.add(bean9);
					}
				});
		nowOut = System.currentTimeMillis();
		// logger.debug("["+usuario+"]"+"Final SALIDADAO SERVIDOR:" + nowOut);
		nowDif = nowOut - nowIn;
		// logger.debug("["+usuario+"]"+"Duracion SALIDADAO SERVIDOR:" + nowDif);
		return results9;
	}

	@Transactional(propagation = Propagation.NEVER, readOnly = true)
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

	@Transactional(propagation = Propagation.NEVER, readOnly = true)
	public List<JefeBean> jefe_grupo(int area) {
		long nowIn = 0, nowOut = 0, nowDif = 0;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		nowIn = System.currentTimeMillis();
		// logger.debug("Inicio SALIDADAO JEFE:" + nowIn);
		final List<JefeBean> results9 = new ArrayList<JefeBean>();
		jdbcTemplate.query(
				// "select nficha as ficha from atd_jefes_equipos where vcodestado='EIRC01' and
				// ncodarea= "+area+" ",
				// "select ncodarea as ncodarea, nficha as ficha, UPPER(VAPEPATERNO ||'
				// '||VAPEMATERNO ||','||VNOMBRES) AS nombre from atd_jefes_equipos where
				// ncodarea = "+area+" ",
				"select j.ncodarea as ncodarea, upper(a.VABREVIATURA)||' - ' ||upper(a.VDESCRIPCION) as nombrearea, j.nficha as ficha, UPPER(j.VAPEPATERNO ||' '||j.VAPEMATERNO ||','||j.VNOMBRES) AS nombre "
						+ "from atd_jefes_equipos j, area a where j.ncodarea=a.ncodarea and a.NINDICADOR = 0 AND a.CTIPAREA IN  ('E ','G', 'D','P') AND a.NESTADO IN (0,4) and "
						+ "j.ncodarea = " + area + " ",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						JefeBean bean = new JefeBean();
						bean.setFicha(rs.getInt("ficha"));
						bean.setNombre(rs.getString("nombre"));
						bean.setNcodarea(rs.getInt("ncodarea"));
						bean.setNombrearea(rs.getString("nombrearea"));
						results9.add(bean);
					}
				});
		nowOut = System.currentTimeMillis();
		// logger.debug("["+usuario+"]"+"Final SALIDADAO JEFE:" + nowOut);
		nowDif = nowOut - nowIn;
		// logger.debug("["+usuario+"]"+"Duracion SALIDADAO JEFE:" + nowDif);
		return results9;
	}

	@Transactional(propagation = Propagation.NEVER, readOnly = true)
	public List<EstadosBean> estados() {
		long nowIn = 0, nowOut = 0, nowDif = 0;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		nowIn = System.currentTimeMillis();
		// logger.debug("Inicio DOCUMENTOSALIDADAO ESTADOS:" + nowIn);
		final List<EstadosBean> results6 = new ArrayList<EstadosBean>();
		jdbcTemplate.query(
				// "SELECT VCODTIPO AS codigo,VDESCRIPCION AS descripcion FROM TIPO WHERE
				// VOBSERVACIONES='ESTADO ATD' AND VESTADO='A'",
				"SELECT VCODTIPO AS codigo,VDESCRIPCION AS descripcion FROM ATD_TIPO WHERE VFLAT='CRS' AND NESTADO=1 AND VOBSERVACIONES='CRITERIO ATD SALIDA'",

				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						EstadosBean bean6 = new EstadosBean();
						bean6.setCodigo(rs.getString("codigo"));
						bean6.setDescripcion(rs.getString("descripcion"));
						results6.add(bean6);
					}
				});
		nowOut = System.currentTimeMillis();
		// logger.debug("["+usuario+"]"+"Final DOCUMENTOSALIDADAO ESTADOS:" + nowOut);
		nowDif = nowOut - nowIn;
		// logger.debug("["+usuario+"]"+"Duracion DOCUMENTOSALIDADAO ESTADOS:" +
		// nowDif);
		return results6;
	}

	public List<RemitenteBean> persona(String cadenaDigitada) {
		long nowIn = 0, nowOut = 0, nowDif = 0;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		nowIn = System.currentTimeMillis();
		// logger.debug("Inicio ENTRANTEMESADAO REMITENTES:" + nowIn);
		final List<RemitenteBean> result1 = new ArrayList<RemitenteBean>();
		jdbcTemplate.query("SELECT NCODREMITENTE AS codigo, UPPER(VDESCRIPCION) AS nombre "
				+ "FROM ATD_REMITENTE WHERE VESTADO = 'A' AND NINDICADOR = 1 AND UPPER(VDESCRIPCION) LIKE UPPER('"
				+ cadenaDigitada + "%')", new RowCallbackHandler() {
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

	public List<RemitenteBean> personaIntena(String cadenaDigitada) {
		long nowIn = 0, nowOut = 0, nowDif = 0;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		nowIn = System.currentTimeMillis();
		// logger.debug("Inicio ENTRANTEMESADAO REMITENTES:" + nowIn);
		final List<RemitenteBean> result1 = new ArrayList<RemitenteBean>();
		jdbcTemplate.query(
				// ELI COMENTA CODIGO ORIGINAL
				// "SELECT A.NCODAREA as codigo, upper(A.VABREVIATURA)||' - '
				// ||upper(A.VDESCRIPCION) as nombre "+
				// "FROM AREA A WHERE A.NINDICADOR = 0 AND A.CTIPAREA IN ('E ','G', 'D','P') AND
				// NESTADO IN (0,4) AND UPPER(A.VABREVIATURA) LIKE
				// UPPER('"+cadenaDigitada+"%')",
				"SELECT A.NCODAREA as codigo, upper(A.VABREVIATURA)||' - ' ||upper(A.VDESCRIPCION) as nombre, A.NINDICADOR as indicador "
						+ "FROM AREA A WHERE A.NINDICADOR = 0 AND A.CTIPAREA IN  ('E ','G', 'D','P') AND NESTADO IN (0,4) "
						+ "AND UPPER(A.VABREVIATURA) LIKE UPPER('" + cadenaDigitada + "%') " + "UNION "
						+ "SELECT G.NCODGRUPODESTINO as codigo, upper(G.VDESCRIPCION) as nombre, G.NINDICADOR as indicador "
						+ "FROM ATD_GRUPO_DESTINATARIOS G WHERE G.NESTADO=1 AND G.NINDICADOR=1 "
						+ "AND UPPER(G.VDESCRIPCION) LIKE UPPER('" + cadenaDigitada + "%')",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						RemitenteBean bean2 = new RemitenteBean();
						bean2.setCodigo(rs.getInt("codigo"));
						bean2.setDescripcion(rs.getString("nombre"));
						bean2.setIndicador(rs.getInt("indicador"));
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

	public List<RemitenteBean> personaIntenaA(String cadenaDigitada) {
		long nowIn = 0, nowOut = 0, nowDif = 0;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		nowIn = System.currentTimeMillis();
		// logger.debug("Inicio ENTRANTEMESADAO REMITENTES:" + nowIn);
		final List<RemitenteBean> result1 = new ArrayList<RemitenteBean>();
		jdbcTemplate.query(
				// ELI COMENTA CODIGO ORIGINAL
				// "SELECT A.NCODAREA as codigo, upper(A.VABREVIATURA)||' - '
				// ||upper(A.VDESCRIPCION) as nombre "+
				// "FROM AREA A WHERE A.NINDICADOR = 0 AND A.CTIPAREA IN ('E ','G', 'D','P') AND
				// NESTADO IN (0,4) AND UPPER(A.VABREVIATURA) LIKE
				// UPPER('"+cadenaDigitada+"%')",
				"SELECT A.NCODAREA as codigo, upper(A.VABREVIATURA)||' - ' ||upper(A.VDESCRIPCION) as nombre, A.NINDICADOR as indicador "
						+ "FROM AREA A WHERE A.NINDICADOR = 0 AND A.CTIPAREA IN  ('E ','G', 'D','P') AND NESTADO IN (0,4) "
						+ "AND UPPER(A.VABREVIATURA) LIKE UPPER('" + cadenaDigitada + "%') ",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						RemitenteBean bean2 = new RemitenteBean();
						bean2.setCodigo(rs.getInt("codigo"));
						bean2.setDescripcion(rs.getString("nombre"));
						bean2.setIndicador(rs.getInt("indicador"));
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

	public List<GrupoBean> grupo(int codigo) {
		long nowIn = 0, nowOut = 0, nowDif = 0;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		nowIn = System.currentTimeMillis();
		// logger.debug("Inicio LISTA DE GRUPO" + nowIn);
		final List<GrupoBean> lista_grupo = new ArrayList<GrupoBean>();
		jdbcTemplate.query(
				// "select nficha as ficha from atd_jefes_equipos where vcodestado='EIRC01' and
				// ncodarea= "+area+" ",
				"select  ncoddestinatarios as codigo, vdescripcion as nombre from atd_destinatarios where nestado=1 and ncodgrupodestino= "
						+ codigo + " ",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						GrupoBean bean = new GrupoBean();
						bean.setCodigo(rs.getInt("codigo"));
						bean.setNombre(rs.getString("nombre"));
						lista_grupo.add(bean);
					}
				});
		nowOut = System.currentTimeMillis();
		// logger.debug("["+usuario+"]"+"Final LISTA DE GRUPO" + nowOut);
		nowDif = nowOut - nowIn;
		// logger.debug("["+usuario+"]"+"Duracion LISTA DE GRUPO" + nowDif);
		return lista_grupo;
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
				// SED-FON-161
				// "SELECT NCODREPRESENTANTE AS codigo, UPPER(VNOMBRE) AS representante "+
				// "FROM ATD_REPRESENTANTE WHERE VESTADO = 'A' AND NCODREMITENTE="+codigo+"",
				"SELECT NCODREPRESENTANTE AS codigo, UPPER(VNOMBRE) AS representante "
						+ "FROM ATD_REPRESENTANTE_BPM WHERE VESTADO = 'A' AND NCODREMITENTE=" + codigo + "",
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

	public List<RepresentanteBean> getRemitenteBPM(int codigo) {
		long nowIn = 0, nowOut = 0, nowDif = 0;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		nowIn = System.currentTimeMillis();
		// logger.debug("Inicio ENTRANTEDAO REPRESENTANTE :" + nowIn);
		final List<RepresentanteBean> results5 = new ArrayList<RepresentanteBean>();
		jdbcTemplate.query("SELECT ncorrelativo AS codigo, UPPER(VNOMBRE) AS representante   "
				+ "FROM atd_remitente_bpm WHERE ncorrelativo=" + codigo + "", new RowCallbackHandler() {
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

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
	public boolean updateentrada(long correlativo) {
		System.out.println("Evento Update Doc Entrada");
		System.out.println(correlativo);
		// String sql = "update atd_secuencial set ncorrelativo = " +correlativo+" where
		// norigen=1 and nano=2011 and vcodtipo='"+tipodoc+"' and nestado=1";
		String sql = "update atd_doc_entr set nindica_salida = 1 where ncorrelativo= " + correlativo + "";

		int rs = jdbcTemplate.update(sql);
		if (rs > 0)
			return true;
		return false;
	}

	@Override
	public Map actualizarDocumentoSalidaSP(DocumentoSalidaBean documentosalidaBean) {
		Map output = storedactualizarDocumentoSalida.execute(documentosalidaBean);
		// String out = (String) output.get("out");
		return output;

	}

	@Override
	public Map nuevoSP(DocumentoSalidaBean documentosalidaBean) {
		Map output = storednuevoDocumentoSalida.execute(documentosalidaBean);
		// String out = (String) output.get("out");
		return output;
	}

	@Override
	public List<DocumentoSalidaBean> documento() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EntranteBean> entradasalida() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<DocumentoSalidaBean> filtrosSP(String fecha1, String fecha2, String opcion, String area, String vtipo,
			String areaseleccionado, String estadoSeleccionado, int fichaVisador) {
		Map output = storedFiltrosSalientes.execute(fecha1, fecha2, opcion, area, vtipo, areaseleccionado,
				estadoSeleccionado, fichaVisador);

		List documento = (ArrayList) output.get("filtros");
		System.out.println("Cantidad de registro del filtro");
		System.out.println(documento.size());
		return documento;
	}

	// date1,date2,this.getItemSeleccionado(),this.getItem3Seleccionado(),area,detallecriterio
	public List<DocumentoSalidaBean> criteriosSP(String fecha1, String fecha2, String tipos, String criterios,
			String area, String detallecriterio) {
		long nowIn = 0, nowOut = 0, nowDif = 0;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		nowIn = System.currentTimeMillis();

		Map output = storedCriteriosSalientes.execute(fecha1, fecha2, tipos, criterios, area, detallecriterio);

		nowOut = System.currentTimeMillis();
		nowDif = nowOut - nowIn;
		// logger.debug("["+usuario+"]"+"Duracion Stored ATDC_CRITERIO_SALIENTE:" +
		// nowDif);

		List documentos = (ArrayList) output.get("criterios");
		return documentos;
	}

	public List<DocumentoSalidaBean> BusquedaSP(String area, String opcion, String detalle) {
		long nowIn = 0, nowOut = 0, nowDif = 0;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		nowIn = System.currentTimeMillis();

		Map output = StoredBusquedaDocSalientes.execute(area, opcion, detalle);

		nowOut = System.currentTimeMillis();
		nowDif = nowOut - nowIn;
		// logger.debug("["+usuario+"]"+"Duracion Stored ATDC_BUSQUEDA_DOCUMENTOS:" +
		// nowDif);

		List documentos = (ArrayList) output.get("busquedassalida");
		return documentos;
	}

	public List<DocumentoSalidaBean> BusquedaEntradaSP(String area, String opcion, String detalle) {
		long nowIn = 0, nowOut = 0, nowDif = 0;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		nowIn = System.currentTimeMillis();

		Map output = StoredBusquedaDocSalientes.execute(area, opcion, detalle);

		nowOut = System.currentTimeMillis();
		nowDif = nowOut - nowIn;
		// logger.debug("["+usuario+"]"+"Duracion Stored ATDC_BUSQUEDA_DOCUMENTOS
		// ENTRADA:" + nowDif);

		List entrada = (ArrayList) output.get("busquedasentrada");
		return entrada;
	}

	public void setStoredNuevoDocumentoSalida(StoredNuevoDocumentoSaliente storednuevoDocumentoSalida) {
		this.storednuevoDocumentoSalida = storednuevoDocumentoSalida;
	}

	public void setStoredActualizarDocumentoSalida(StoredActualizarDocumentoSalida storedactualizarDocumentoSalida) {
		this.storedactualizarDocumentoSalida = storedactualizarDocumentoSalida;
	}

	public void setStoredBusqueda(StoredBusquedaDocSalientes storedBuquedaDocSalientes) {
		this.StoredBusquedaDocSalientes = storedBuquedaDocSalientes;
	}

	public void setStoredBusquedaDocSalientes(StoredBusquedaDocSalientes storedBusquedaDocSalientes) {
		StoredBusquedaDocSalientes = storedBusquedaDocSalientes;
	}

	public void setStoredCriteriosSalientes(StoredCriteriosSalientes storedCriteriosSalientes) {
		this.storedCriteriosSalientes = storedCriteriosSalientes;
	}

	public void setStoredDeleteDocumentoSalida(StoredDeleteDocumentoSalida storeddeleteDocumentoSalida) {
		this.storeddeleteDocumentoSalida = storeddeleteDocumentoSalida;
	}

	public void setStoredDocumentoSalida(StoredDocumentoSaliente storeddocumentosaliente) {
		this.storeddocumentosaliente = storeddocumentosaliente;
	}

	public void setStoredentratesalida(StoredEntrateSalida storedentratesalida) {
		this.storedentratesalida = storedentratesalida;
	}

	public StoredCombo getStoredCombo() {
		return storedCombo;
	}

	public void setStoredCombo(StoredCombo storedCombo) {
		this.storedCombo = storedCombo;
	}

	public void setStoredbusquedaentratesalida(StoredBusquedaEntrateSalida storedbusquedaentratesalida) {
		this.storedbusquedaentratesalida = storedbusquedaentratesalida;
	}
	/*
	 * public void setStoredremitentessp(StoredRemitentesSP storedremitentessp) {
	 * this.storedremitentessp = storedremitentessp; }
	 */

	public void setStoredlistaasuntopopub(StoredListaAsuntoPopup storedlistaasuntopopub) {
		this.storedlistaasuntopopub = storedlistaasuntopopub;
	}

	public StoredBusquedaAsuntos getStoredbusquedaasuntos() {
		return storedbusquedaasuntos;
	}

	public void setStoredbusquedaasuntos(StoredBusquedaAsuntos storedbusquedaasuntos) {
		this.storedbusquedaasuntos = storedbusquedaasuntos;
	}

	public void setStoreddocumentosaliente(StoredDocumentoSaliente storeddocumentosaliente) {
		this.storeddocumentosaliente = storeddocumentosaliente;
	}

	public void setStoredactualizarDocumentoSalida(StoredActualizarDocumentoSalida storedactualizarDocumentoSalida) {
		this.storedactualizarDocumentoSalida = storedactualizarDocumentoSalida;
	}

	public void setStorednuevoDocumentoSalida(StoredNuevoDocumentoSaliente storednuevoDocumentoSalida) {
		this.storednuevoDocumentoSalida = storednuevoDocumentoSalida;
	}

	public void setStoreddeleteDocumentoSalida(StoredDeleteDocumentoSalida storeddeleteDocumentoSalida) {
		this.storeddeleteDocumentoSalida = storeddeleteDocumentoSalida;
	}

	public void setStoredFiltrosSalientes(StoredFiltrosSalientes storedFiltrosSalientes) {
		this.storedFiltrosSalientes = storedFiltrosSalientes;
	}

	// SED-FON-161
	public List<Seguir> personaBPM(Integer correlativo, Integer anio) {
		long nowIn = 0, nowOut = 0, nowDif = 0;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		nowIn = System.currentTimeMillis();
		// logger.debug("Inicio ENTRANTEMESADAO REMITENTES:" + nowIn);
		final List<Seguir> result1 = new ArrayList<Seguir>();
		jdbcTemplate.query(
				"select b.ncorrelativo, UPPER(b.vnombre) as vnombre, d.nindremitente, " + " b.vtipdoc, b.vnumdoc "
						+ "from atd_doc_entr d " + "inner join atd_remitente_bpm b on d.ndirigido = b.ncorrelativo "
						+ "where d.ncorrelativo=" + correlativo + " and d.nano=" + anio + " and  d.nindremitente=2",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						Seguir bean2 = new Seguir();
						bean2.setEstado("AA");
						bean2.setArea(rs.getString("ncorrelativo"));
						bean2.setCodArea(rs.getString("vnombre"));
						bean2.setNombreTrabajador(rs.getString("vnombre"));
						bean2.setnIndRemitente(rs.getInt("nindremitente"));
						bean2.setVnumdoc(rs.getString("vnumdoc"));
						bean2.setVtipdoc(rs.getString("vtipdoc"));
						bean2.setCodArea("");
						result1.add(bean2);
					}
				});
		nowOut = System.currentTimeMillis();
		nowDif = nowOut - nowIn;
		return result1;
	}

	// SED-FON-161
	@Transactional(propagation = Propagation.NEVER, readOnly = true)
	public RemitenteBean getRemitenteByNombre(String nombre) {
		long nowIn = 0, nowOut = 0, nowDif = 0;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		nowIn = System.currentTimeMillis();
		final RemitenteBean result1 = new RemitenteBean();
		jdbcTemplate.query("SELECT NCODREMITENTE AS codigo, UPPER(VDESCRIPCION) AS nombre  " + "FROM ATD_REMITENTE  "
				+ " WHERE VDESCRIPCION='" + nombre + "'", new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						result1.setCodigo(rs.getInt("codigo"));
						result1.setDescripcion(rs.getString("nombre"));
					}
				});
		nowOut = System.currentTimeMillis();
		nowDif = nowOut - nowIn;
		return result1;
	}

	// SED-FON-161
	public List<RepresentanteBean> representanteInterno(int codigo) {
		long nowIn = 0, nowOut = 0, nowDif = 0;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("sUsuario");
		nowIn = System.currentTimeMillis();
		// logger.debug("Inicio ENTRANTEDAO REPRESENTANTE :" + nowIn);
		final List<RepresentanteBean> results5 = new ArrayList<RepresentanteBean>();
		jdbcTemplate.query(
				"SELECT NCODREMITENTE AS codigo, UPPER(VNOMBRE) AS representante "
						+ "FROM ATD_REPRESENTANTE WHERE VESTADO = 'A' AND NCODREMITENTE=" + codigo + "",
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

	public RemitenteBPM consultaDocumentoBPM(Long correlativo) {
		Map output = storedConsultaDocumentoBPM.execute(correlativo);
		List<RemitenteBPM> remitentes = new ArrayList<RemitenteBPM>();
		remitentes = (ArrayList) output.get("CUR_DATOS");
		if (remitentes.size() > 0) {
			return remitentes.get(0);
		}
		return null;
	}

	public StoredConsultaDocumentoBPM getStoredConsultaDocumentoBPM() {
		return storedConsultaDocumentoBPM;
	}

	public void setStoredConsultaDocumentoBPM(StoredConsultaDocumentoBPM storedConsultaDocumentoBPM) {
		this.storedConsultaDocumentoBPM = storedConsultaDocumentoBPM;
	}

	public void setStoredtipodocumentosalida(StoredTipoDocumentosSalida storedtipodocumentosalida) {
		this.storedtipodocumentosalida = storedtipodocumentosalida;
	}

	public List<TiposBean> tipoEstados() {
		final List<TiposBean> results = new ArrayList<TiposBean>();
		jdbcTemplate.query("select vcodtipo as codigo, vdescripcion as descripcion from atd_tipo "
				+ "where vflat='SAL' and nestado=1 ORDER BY codigo", new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						TiposBean bean2 = new TiposBean();
						bean2.setTipo(rs.getString("codigo"));
						bean2.setDescripcion(rs.getString("descripcion"));
						results.add(bean2);
					}
				});

		return results;
	}

	public boolean validarFirmante(int codigoPerfil) {
		final List<Integer> results = new ArrayList<Integer>();
		jdbcTemplate.query("SELECT VALOR AS DESCRIPCION FROM ATD_PARAMETROS WHERE VALOR1='ATD_FIRMANTE' AND NESTADO=1",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						results.add(Integer.parseInt(rs.getString("descripcion")));
					}
				});
		for (Integer perfil : results) {
			if (perfil == codigoPerfil) {
				return true;
			}
		}
		return false;
	}

	public boolean validarGeneradorFirma(int codigoPerfil) {
		final List<Integer> results = new ArrayList<Integer>();
		jdbcTemplate.query("SELECT VALOR AS DESCRIPCION FROM ATD_PARAMETROS WHERE VALOR1='ATD_FIRMA' AND NESTADO=1",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						results.add(Integer.parseInt(rs.getString("descripcion")));
					}
				});
		for (Integer perfil : results) {
			if (perfil == codigoPerfil) {
				return true;
			}
		}
		return false;
	}

	public List<Visante> visantes(int codArea, String cadenaDigitada) {
		System.out.println("Buscando visantes en el área: " + codArea + " - " + cadenaDigitada);
		final List<Visante> results = new ArrayList<Visante>();
		jdbcTemplate.query("SELECT u.vcodusuario login,u.vdescripcion nombre,u.ncodficha ficha FROM usuario u "
				+ "inner join usuario_perfil_sistema up on up.vcodusuario = u.vcodusuario " + "where u.ncodarea= "
				+ codArea + "  and u.nestado = 1 and up.ncodperfil = 7 and upper(u.vdescripcion) like upper('%"
				+ cadenaDigitada + "%') and rownum < 20", new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						Visante sp = new Visante();
						sp.setNombre(rs.getString("nombre"));
						sp.setFicha(rs.getString("ficha"));
						results.add(sp);
					}
				});
		return results;
	}

	public List<SignetParametro> getSignetParametros() {
		final List<SignetParametro> results = new ArrayList<SignetParametro>();
		jdbcTemplate.query(
				"SELECT VALOR1 AS CODIGO, VALOR2 AS DESCRIPCION FROM ATD_PARAMETROS WHERE VALOR1 LIKE 'ATD_SIGNET_%' AND NESTADO=1",
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						SignetParametro sp = new SignetParametro();
						sp.setCodigo(rs.getString("codigo"));
						sp.setDescripcion(rs.getString("descripcion"));
						results.add(sp);
					}
				});
		return results;
	}

	public String getUsernamePorFicha(int ficha) {
		final List<String> results = new ArrayList<String>();
		jdbcTemplate.query("SELECT u.vdescripcion nombre FROM usuario u WHERE u.ncodficha = " + ficha,
				new RowCallbackHandler() {

					@Override
					public void processRow(ResultSet rs) throws SQLException {
						results.add(rs.getString("nombre"));
					}
				});
		return results.isEmpty() ? "" : results.get(0);
	}

	public int getFichaJefeEquipo(int codArea) {
		final List<Integer> results = new ArrayList<Integer>();
		jdbcTemplate.query(
				"SELECT u.vdescripcion nombre, u.ncodficha ficha FROM usuario u "
						+ "inner join usuario_perfil_sistema up on up.vcodusuario = u.vcodusuario "
						+ "where u.ncodarea= " + codArea + " and up.ncodperfil = 10 and u.nestado = 1",
				new RowCallbackHandler() {

					@Override
					public void processRow(ResultSet rs) throws SQLException {
						results.add(rs.getInt("ficha"));
					}
				});
		return results.isEmpty() ? 0 : results.get(0);
	}

	public List<Anexo> getAnexos(int codSal, int anio) {
		final List<Anexo> results = new ArrayList<Anexo>();
		jdbcTemplate
				.query("select NCODANX, vubicarchivo, vnombarchivo from atd_doc_anexos where nestado = 'I' and nano = "
						+ anio + " and ncodsal = " + codSal, new RowCallbackHandler() {
							@Override
							public void processRow(ResultSet rs) throws SQLException {
								Anexo anexo = new Anexo();
								anexo.setCodigo(rs.getInt("NCODANX"));
								anexo.setArchivo(rs.getString("vubicarchivo"));
								anexo.setNombreArchivo(rs.getString("vnombarchivo"));
								results.add(anexo);
							}
						});
		return results;
	}
	
	public boolean getAcuse(int codSal, int anio, int area) {
		final List<Integer> results = new ArrayList<Integer>();
		jdbcTemplate
				.query("select NACUSERECIBO from ATD_DOC_SAL where NCODSAL = " + codSal + " and NCODAREA = " + area + " and NANO = "
						+ anio, new RowCallbackHandler() {
							@Override
							public void processRow(ResultSet rs) throws SQLException {
								results.add(rs.getInt("NACUSERECIBO"));
							}
						});
		return !results.isEmpty() ? results.get(0) == 0 : false;
	}

	@Override
	public void visarSP(String codigo, String anno, String origen, String tipodoc, String area, String login) {
		storedVisadoDocumentoSalida.execute(codigo, anno, origen, tipodoc, area, login);
	}

	@Override
	public void firmarSP(String codigo, String anno, String origen, String tipodoc, String area, String login) {
		storedFirmadoDocumentoSalida.execute(codigo, anno, origen, tipodoc, area, login);
	}

	@Override
	public void observarDocumento(String codigo, String anno, String origen, String tipodoc, String area, String login,
			String observacion) {
		storedObservadoDocumentoSalida.execute(codigo, anno, origen, tipodoc, area, login, observacion);
	}

	@Override
	public void guardarAnexo(int codigo, int anno, String login, String anexo, String anexoNombre) {
		storedAnexoDocumento.execute(codigo, anno, login, anexo, anexoNombre);
	}

	@Override
	public void eliminarAnexo(int codigo, int anno, String login, int anexo) {
		storedEliminarAnexoDocumento.execute(codigo, anno, login, anexo);
	}

	public void guardarAcuseRecibo(int codigo, int anno, String origen, String tipodoc, String area, String login, 
			boolean recibido, int correlativo, int correlativoAnio, int corrCodArea) {
		this.storedAcuseDocumentoSalida.execute(codigo, anno, origen, tipodoc, area, login, recibido, 
				correlativo, correlativoAnio, corrCodArea);
	}
	
	public void guardarVisualizacion(int codigo, int anno, String origen, String tipodoc, String area, String login) {
		this.storedVisualizacionDocumentoSalida.execute(codigo, anno, origen, tipodoc, area, login);
	}
	
	@Override
	public void guardarRegistroEntradaPorDocSal(int registro, int anio, int codigo, int docAnio, int docArea,
			String login) {
		this.storedDocSalRegEntrada.execute(registro, anio, codigo, docAnio, docArea, login);
	}
	
	public List<RegistroEntrada> getRegistrosEntrada(int codSal, int anio, int codArea) {
		final List<RegistroEntrada> results = new ArrayList<RegistroEntrada>();
		jdbcTemplate
				.query("select vnumero_reg, nanio from atd_doc_sal_reg_entrada where vestado = 1 "
						+ "and ndoc_sal = " + codSal + " and ndoc_sal_anio = " + anio +  " AND ncod_area = " + codArea, new RowCallbackHandler() {
							@Override
							public void processRow(ResultSet rs) throws SQLException {
								RegistroEntrada re = new RegistroEntrada();
								re.setNumero(String.valueOf(rs.getInt("vnumero_reg")));
								re.setAnio(String.valueOf(rs.getInt("nanio")));
								results.add(re);
							}
						});
		return results;
	}
	
	public EntranteBean getDocEntrada(String numDoc) {
		final List<EntranteBean> results = new ArrayList<EntranteBean>();
		jdbcTemplate
				.query("select nano, ncorrelativo, ncodarea from atd_doc_entr where vnumdoc = '" + numDoc + "'", new RowCallbackHandler() {
							@Override
							public void processRow(ResultSet rs) throws SQLException {
								EntranteBean e = new EntranteBean();
								e.setNano(rs.getInt("nano"));
								e.setNcorrelativo(rs.getLong("ncorrelativo"));
								e.setNcodarea(rs.getInt("ncodarea"));
								results.add(e);
							}
						});
		return !results.isEmpty() ? results.get(0) : null;
	}
	
	public EntranteBean getDocEntrada(int correlativo, int anio) {
		final List<EntranteBean> results = new ArrayList<EntranteBean>();
		jdbcTemplate
				.query("select nano, ncodarea from atd_doc_entr where ncorrelativo = " + correlativo + " "
						+ "AND nano = " + anio, new RowCallbackHandler() {
							@Override
							public void processRow(ResultSet rs) throws SQLException {
								EntranteBean e = new EntranteBean();
								e.setNano(rs.getInt("nano"));
								e.setNcorrelativo(correlativo);
								e.setNcodarea(rs.getInt("ncodarea"));
								results.add(e);
							}
						});
		return !results.isEmpty() ? results.get(0) : null;
	}

	public void setStoredVisadoDocumentoSalida(StoredVisadoDocumentoSalida storedVisadoDocumentoSalida) {
		this.storedVisadoDocumentoSalida = storedVisadoDocumentoSalida;
	}

	public void setStoredFirmadoDocumentoSalida(StoredFirmadoDocumentoSalida storedFirmadoDocumentoSalida) {
		this.storedFirmadoDocumentoSalida = storedFirmadoDocumentoSalida;
	}

	public void setStoredObservadoDocumentoSalida(StoredObservadoDocumentoSalida storedObservadoDocumentoSalida) {
		this.storedObservadoDocumentoSalida = storedObservadoDocumentoSalida;
	}

	public void setStoredAnexoDocumento(StoredAnexoDocumento storedAnexoDocumento) {
		this.storedAnexoDocumento = storedAnexoDocumento;
	}

	public void setStoredEliminarAnexoDocumento(StoredEliminarAnexoDocumento storedEliminarAnexoDocumento) {
		this.storedEliminarAnexoDocumento = storedEliminarAnexoDocumento;
	}

	public void setStoredAcuseDocumentoSalida(StoredAcuseDocumentoSalida storedAcuseDocumentoSalida) {
		this.storedAcuseDocumentoSalida = storedAcuseDocumentoSalida;
	}

	public void setStoredVisualizacionDocumentoSalida(
			StoredVisualizacionDocumentoSalida storedVisualizacionDocumentoSalida) {
		this.storedVisualizacionDocumentoSalida = storedVisualizacionDocumentoSalida;
	}

	public void setStoredDocSalRegEntrada(StoredDocSalRegEntrada storedDocSalRegEntrada) {
		this.storedDocSalRegEntrada = storedDocSalRegEntrada;
	}
}
