package com.sedapal.tramite.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sedapal.tramite.beans.LlavesBean;
import com.sedapal.tramite.beans.MarquesitaBean;
import com.sedapal.tramite.beans.TiposDocumentosBean;
import com.sedapal.tramite.beans.UsersBean;
import com.sedapal.tramite.beans.Usuario;

public class UsuarioDAO implements IUsuariosDAO, Serializable {

	protected static final Object Usuario = null;
	private JdbcTemplate jdbcTemplate;
	private Date fecha = new Date();
	private StoredNuevoLogIngreso storednuevologingreso;

	@Override
	public Usuario autenticar(String login, String clave) {
		Object[] parametros = new Object[] { login, clave };
		Usuario usuario = null;
		
		try{
			usuario = (Usuario) jdbcTemplate
			.queryForObject(
			
					/*
					"select u.vcodusuario login,u.vdescripcion nombre,u.ncodficha ficha,u.ncodarea area,u.vpass password,up.ncodperfil ncodperfil,ps.vdescripcion perfil,"
							+ "UPPER(a.vdescripcion) nomequipo,c.ncodcentro ncodcentro,UPPER(c.vnombre) as centro, u.nconexion as conexion from usuario u,usuario_perfil_sistema up,perfil_sistema ps, area a, centro c "
							+ "where u.nestado=1 and u.vcodusuario=up.vcodusuario and up.ncodsistema=30 and up.ncodperfil=ps.ncodperfil and up.ncodsistema=ps.ncodsistema "
							+ "and u.ncodarea=a.ncodarea and a.ncodcentro= c.ncodcentro and u.vcodusuario= ? and u.vpass= ?",
							*/
					
						
				    "select u.vcodusuario login,u.vdescripcion nombre,u.ncodficha ficha,u.ncodarea area,u.vpass password,up.ncodperfil ncodperfil,ps.vdescripcion perfil, "
					+ "UPPER(a.vdescripcion) nomequipo,c.ncodcentro ncodcentro,UPPER(c.vnombre) as centro, u.nconexion as conexion, t.vdirelectronica as correo "
					+ "from usuario u,usuario_perfil_sistema up,perfil_sistema ps, area a, centro c, trabajador t "
					+ "where u.ncodficha= t.nficha and  t.vcodestado='EIRC01' and t.ind_email=0 and "
				    + "u.nestado=1 and u.vcodusuario=up.vcodusuario and up.ncodsistema=30 and up.ncodperfil=ps.ncodperfil and up.ncodsistema=ps.ncodsistema " 
				    + "and u.ncodarea=a.ncodarea and a.ncodcentro= c.ncodcentro and u.vcodusuario= ? and u.vpass= ? ",
				    
					
					
					parametros, new RowMapper() {
						public Object mapRow(ResultSet rst, int rowNum)
								throws SQLException {
							Usuario usuario = new Usuario();
							//if(!rst.equals(null)){
								usuario.setLogin(rst.getString("login"));
								usuario.setNombre(rst.getString("nombre"));
								usuario.setPassword(rst.getString("password"));
								usuario.setPerfil(rst.getString("perfil"));
								usuario.setCodarea(rst.getInt("area"));
								usuario.setNomequipo(rst.getString("nomequipo"));
								usuario.setNcodperfil(rst.getInt("ncodperfil"));
								usuario.setCentro(rst.getString("centro"));
								usuario.setNcodcentro(rst.getInt("ncodcentro"));
								usuario.setFicha(rst.getInt("ficha"));
								usuario.setConexion(rst.getInt("conexion"));
								usuario.setCorreo(rst.getString("correo"));
							//}								
							return usuario;
						}

					});
			
			
			
			
		}catch(Exception e){
			usuario = null;
			System.out.println("Error : "+e.toString());
		}
		
		
		return usuario;
	}
	
	
	
	@Override
	public void nuevoLogingreso(Usuario usuariosBean) {
				
		Map output = storednuevologingreso.execute(usuariosBean);
		
		
	}
	
	
	
	
	public void setStorednuevologingreso(StoredNuevoLogIngreso storednuevologingreso) {
		this.storednuevologingreso = storednuevologingreso;
	}



	@Override
	public String UpdateLogs(int codsistema, String usuario, Date fecha, String ip, int area, String acceso, String ingreso, int correlativo) {
		String sql = "insert into ACCESOS (NCODSISTEMA , VCODUSUARIO, DFECFECHA, NCODIP, NCODAREA, VCODACCESO, VCODINGRESO, NCORRELATIVO )"
    		+ " values(?,?,?,?,?,?,?,?)";
    	 Object[] parametros = new Object[] { codsistema, usuario, fecha, ip, area, acceso, ingreso, correlativo};
		 int rs = jdbcTemplate.update(sql, parametros);
		return sql;
	}

	public String getSecuencial() {
		String correlativo = null;        		
		
        correlativo = (String) jdbcTemplate.queryForObject(
					"select to_char(count(ncorrelativo) + 1) as correlativo from ACCESOS where ncodsistema=30 and ncodarea=302 and vcodusuario='EDIAZH' ", new RowMapper() {
	                public Object mapRow(ResultSet rst, int rowNum) throws SQLException {
	                    String ncorrelativo = rst.getString("correlativo");
	                    return ncorrelativo;
	                }
	        });
		 		 
        return correlativo;
    }
    
		
	/*Agregado el 14/11/2013 - Eli Diaz - Marquesita */
	public String marquesita(){    	        
		String resultStr = null;
		
		resultStr = (String) jdbcTemplate.queryForObject(
					"select vobservaciones as mensajeuno, valor1 as mensajedos, valor2 as mensajetres "
				+ "from atd_parametros where vdescripcion='MARQUESITA' and nindicador=1 and nvalor=30", new RowMapper() {
	                public Object mapRow(ResultSet rst, int rowNum) throws SQLException {
	                    String mensajeuno = rst.getString("mensajeuno");
	                    return mensajeuno;
	                }
	        });
						            		
		return resultStr;
	}
	
	
	/*Agregado el 16/11/2011 - Alfredo Panitz */

	@Override
    public String getEmailByFichaUsu(int codFichaUsu) {
        String resultStr = null;

        resultStr = (String) jdbcTemplate.queryForObject(
               // "SELECT tr.vdirelectronica emailUsu"+
               // "  FROM s_guia.trabajador tr"+
               // " WHERE tr.nficha = "+codFichaUsu+" 
        		"SELECT vdirelectronica emailUsu "+
        		"FROM trabajador WHERE IND_EMAIL=0 AND VCODESTADO='EIRC01' " +
        		"AND nficha = "+codFichaUsu+" ", new RowMapper() {
                public Object mapRow(ResultSet rst, int rowNum) throws SQLException {
                    String email = rst.getString("emailUsu");
                    return email;
                }
        });
        return resultStr;
    }
	
	 @Override
	    public List<Usuario> getSecretariasByCodArea(int codArea) {
	        List<Map> auxResultList = new ArrayList();
	        List<Usuario> resultList = new ArrayList();
	        
	        auxResultList = jdbcTemplate.queryForList(
	        		"SELECT VNOMBRES ||','||VAPEPATERNO ||' '||VAPEMATERNO AS nombre,"+ 
	        		"NFICHA as ficha, "+
	        		"NCODAREA as area, "+
	        		"VCORREO as correo "+
	        		"FROM atd_secretarias "+
	        		"where ncodarea="+codArea+")");

	      
	        for (Map auxItem : auxResultList) {
	            Usuario beanUsuario = new Usuario();
	            beanUsuario.setNombre((String)auxItem.get("nombre"));
	            beanUsuario.setFicha(Integer.parseInt(((BigDecimal)auxItem.get("ficha")).toString()));
	            beanUsuario.setCodarea(Integer.parseInt(((BigDecimal)auxItem.get("area")).toString()));
	            resultList.add(beanUsuario);
		}

	        return resultList;
	    }
	
	/*Agregado por Eli Diaz 29/09/2016*/
	
	@Override
	public String getEmailJefe(int codarea) {
		String correo_jefe = null;
		
		correo_jefe = (String) jdbcTemplate.queryForObject(
					"SELECT VDIRELECTRONICA emailjefe " +
					"FROM ATD_JEFES_EQUIPOS WHERE VCODESTADO='EIRC01' AND NCODAREA="+codarea+" ",				
	        		new RowMapper() {
	                public Object mapRow(ResultSet rst, int rowNum) throws SQLException {
	                    String email = rst.getString("emailjefe");
	                    return email;
	                }
	        });
		
		return correo_jefe;
	}

    @Override
    public Usuario getUsuarioByCodUsu(String codUsu) {
        Usuario beanResult = new Usuario();
        //, int codarea
        // "   AND u.ncodarea= "+codarea+""+

        try{
            beanResult = (Usuario) jdbcTemplate.queryForObject(
                "SELECT UPPER(u.vdescripcion) nombre,"+
                "       u.ncodficha    ficha,"+
                "       u.vpass        pass,"+
                "       u.ncodarea     area" +
                "  FROM usuario u,"+
                "       usuario_perfil_sistema up"+
                " WHERE up.vcodusuario = u.vcodusuario"+
                "   AND UPPER(u.vcodusuario) = UPPER('"+codUsu+"')"+               
                "   AND u.nestado = 1"+
                "   AND up.ncodsistema = 30", new RowMapper() {
                public Object mapRow(ResultSet rst, int rowNum) throws SQLException {
                    Usuario beanResultAux = new Usuario();
                    beanResultAux.setNombre(rst.getString("nombre"));
                    beanResultAux.setFicha(rst.getInt("ficha"));
                    beanResultAux.setPassword(rst.getString("pass"));
                    beanResultAux.setCodarea(rst.getInt("area"));
                    return beanResultAux;
                }        
            });
        }catch(Exception e){
            beanResult = null;
        }

        return beanResult;
    }

   
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	

	

}
