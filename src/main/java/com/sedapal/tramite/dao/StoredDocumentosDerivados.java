package com.sedapal.tramite.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import oracle.jdbc.driver.OracleTypes;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import com.sedapal.tramite.beans.AreaBean;
import com.sedapal.tramite.beans.DocumentoDerivado;
import com.sedapal.tramite.beans.consultas.AtencionDocSalidaBean;

public class StoredDocumentosDerivados  extends StoredProcedure implements RowMapper {

	private static final String SPROC_NAME = "S_GUIA.ATDC_CONSULTA_DOC_ENT";	
	private static final String V_TIPO_CONSULTA = "v_tipo_consulta";
	private static final String V_AREA = "v_area";
	private static final String D_FECHA_INICIAL="d_fechaInicial";
	private static final String D_FECHA_FINAL="d_fecha_final";
	private static final String V_ESTADO="v_estado";
	private static final String V_FICHA="v_ficha";
	private static final String V_AREA_LOGIN="v_area_login";
	
	private static Logger logger = Logger.getLogger("R1");
		
	public StoredDocumentosDerivados() {
		// TODO Auto-generated constructor stub
	}

	
	private String tipoConsulta;

	public StoredDocumentosDerivados(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		declareParameter(new SqlParameter(D_FECHA_INICIAL, Types.VARCHAR));
		declareParameter(new SqlParameter(D_FECHA_FINAL, Types.VARCHAR));
		declareParameter(new SqlParameter(V_TIPO_CONSULTA, Types.VARCHAR));
		declareParameter(new SqlParameter(V_AREA, Types.VARCHAR));
		declareParameter(new SqlParameter(V_ESTADO, Types.VARCHAR));
		declareParameter(new SqlParameter(V_FICHA, Types.VARCHAR));	
		declareParameter(new SqlParameter(V_AREA_LOGIN, Types.VARCHAR));	
		declareParameter(new SqlOutParameter("derivados",OracleTypes.CURSOR, this));
		compile();
	}
	public Map execute(String tipoConsulta, String area, String fechaInicial, String fechaFinal, String estado, String ficha, String areaLogin) {
		Map inputs = new HashMap();
		inputs.put(D_FECHA_INICIAL, fechaInicial);
		inputs.put(D_FECHA_FINAL, fechaFinal);
		inputs.put(V_TIPO_CONSULTA, tipoConsulta);
		inputs.put(V_AREA, area);
		inputs.put(V_ESTADO, estado);
		inputs.put(V_FICHA, ficha);
		inputs.put(V_AREA_LOGIN, areaLogin);
		System.out.println("Envio Parametros");	
		System.out.println("ATDC_CONSULTA_DOC_ENT");
		System.out.println("D_FECHA_INICIAL" + " " + fechaInicial);
		System.out.println("D_FECHA_FINAL" + " " + fechaFinal);
		System.out.println("V_TIPO_CONSULTA" + " " + tipoConsulta);
		System.out.println("V_AREA_LOGIN" + " " + areaLogin);
		System.out.println("V_ESTADO" + " " + estado);
		System.out.println("V_FICHA" + " " + ficha);
		
		//logger.debug(D_FECHA_INICIAL+":" + fechaInicial);
		//logger.debug(D_FECHA_FINAL+":" + fechaFinal);		
		//logger.debug(V_TIPO_CONSULTA+":" + tipoConsulta);
		//logger.debug(V_AREA+":" + area);
		//logger.debug(V_ESTADO+":" + estado);
		//logger.debug(V_FICHA+":" + ficha);
		//logger.debug(V_AREA_LOGIN+":" + areaLogin);
		this.setTipoConsulta(tipoConsulta);
		return super.execute(inputs);
	}
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			
	
    if (this.getTipoConsulta().equals("CONS005")) {
    	   System.out.println("REPORTE 111111");    	   
    	   AtencionDocSalidaBean beanADSalida = new AtencionDocSalidaBean();
	       beanADSalida.setAnnio(rs.getString("ANNIO"));
    	   beanADSalida.setCodDocSal(rs.getString("CODDOC"));    	   
    	   beanADSalida.setNroSec(rs.getString("NROSEG"));
    	   beanADSalida.setNroDocSalida(rs.getString("NUMDOC"));    	   
    	   beanADSalida.setCodTipDocumento(rs.getString("CODTIPDOC"));
    	   beanADSalida.setDescTipDocumento(rs.getString("DESCTIPDOC"));
    	   beanADSalida.setCodAreaRemitente(rs.getString("CODAREAREM"));
    	   beanADSalida.setDescAreaRemitente(rs.getString("DESCAREAREM"));
    	   beanADSalida.setAsunto(rs.getString("ASUNTO"));
    	   beanADSalida.setNroDocEntrada(rs.getString("NRODOCENTR"));
    	   beanADSalida.setFechaCrea(rs.getString("FECCRE"));
    	   beanADSalida.setFechaAct(rs.getString("FECACT"));
    	   beanADSalida.setDiasPlazo(rs.getInt("DIASPLAZO"));
    	   beanADSalida.setDiasTransc(rs.getInt("DIASTRANSCURRIDOS"));
    	   beanADSalida.setCantDiasNoLab(rs.getInt("DIASNOLABORABLES"));
    	   beanADSalida.setCantDiasLab(rs.getInt("DIASLABORABLES")); 
    	   beanADSalida.setUsuario(rs.getString("NOMBRE"));
           return beanADSalida;
            
        } else if (this.getTipoConsulta().equals("CONS002")) {
           AtencionDocSalidaBean beanADSalida = new AtencionDocSalidaBean(); 	       
           beanADSalida.setNroDocSalida(rs.getString("NUMERODOCUMENTO"));
     	   beanADSalida.setDfeccre(rs.getDate("FECHADOC"));
     	   beanADSalida.setDescAreaRemitente(rs.getString("AREADERIVADO"));
     	   beanADSalida.setDescTipDocumento(rs.getString("TIPODOC"));
     	   beanADSalida.setAsunto(rs.getString("ASUNTO"));
     	   beanADSalida.setUsuario(rs.getString("NOMBRE"));
           return beanADSalida;
        } else if (this.getTipoConsulta().equals("CONS006")) {
        	DocumentoDerivado derivado = new DocumentoDerivado();
			derivado.setAsunto(rs.getString("ASUNTO"));
			derivado.setCorrelativo(Integer.parseInt(rs.getString(("CORRELATIVO"))));
			derivado.setFechaDoc(rs.getDate("FECHADOC"));
			derivado.setAreaDerivado(rs.getString("AREADERIVADO"));
			derivado.setNumeroDocumento(rs.getString("NUMERODOCUMENTO"));
			derivado.setObservacion(rs.getString("OBSERVACION"));
			derivado.setUbicacion(rs.getString("UBICACION"));
			derivado.setFechaDerivacion(rs.getDate("FECHADERIVACION"));
			derivado.setNombre(rs.getString("NOMBRE"));
			derivado.setEstado(rs.getString("ESTADO"));
			derivado.setIndicador(rs.getString("INDICADOR"));
			if (rs.getString("INDICADOR").equals("0")) {
                derivado.setAtachment(false);
            } else {
                derivado.setAtachment(true);
            }
			return derivado;
        } else {
        	System.out.println("ENTRO A OTROS TIPOS REPORTES");
        	System.out.println(this.getTipoConsulta());
			DocumentoDerivado derivado = new DocumentoDerivado();
			derivado.setAsunto(rs.getString("ASUNTO"));
			derivado.setCorrelativo(Integer.parseInt(rs.getString(("CORRELATIVO"))));
			derivado.setFechaDoc(rs.getDate("FECHADOC"));
			derivado.setAreaDerivado(rs.getString("AREADERIVADO"));
			derivado.setNumeroDocumento(rs.getString("NUMERODOCUMENTO"));
			derivado.setObservacion(rs.getString("OBSERVACION"));
			derivado.setUbicacion(rs.getString("UBICACION"));
			derivado.setFechaDerivacion(rs.getDate("FECHADERIVACION"));
			derivado.setNombre(rs.getString("NOMBRE"));
			derivado.setEstado(rs.getString("ESTADO"));
			derivado.setIndicador(rs.getString("INDICADOR"));
			if (rs.getString("INDICADOR").equals("0")) {
                derivado.setAtachment(false);
            } else {
                derivado.setAtachment(true);
            }
			return derivado;
        }

    }
    

    /**
     * @return the tipoConsulta
     */
    public String getTipoConsulta() {
        return tipoConsulta;
    }

    /**
     * @param tipoConsulta the tipoConsulta to set
     */
    public void setTipoConsulta(String tipoConsulta) {
        this.tipoConsulta = tipoConsulta;
    }
}