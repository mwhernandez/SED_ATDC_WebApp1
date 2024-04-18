package com.sedapal.tramite.dao;

import com.sedapal.tramite.beans.EntranteBean;
import com.sedapal.tramite.beans.LogMailBean;
import com.sedapal.tramite.beans.Usuario;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Admin
 */
public class LogMailDAO implements ILogMailDAO, Serializable{   
  
    private JdbcTemplate jdbcTemplate;
    private StoredNuevoEmail storednuevoemail;
    Calendar c = Calendar.getInstance();
    
     

    @Override
    public Boolean getLogMailbyFichaAndFecha(int codFicha, String fechaEnvio) {
        Boolean bExiste = false;
        String sExiste = "";
        sExiste = (String) jdbcTemplate.queryForObject(
                "SELECT NVL(COUNT(*),0) existe"+
                "  FROM atd_log_mail alm"+
                " WHERE alm.ncod_ficha ="+ codFicha +
                "   AND alm.vfecha_envio = "+ fechaEnvio, new RowMapper() {
                public Object mapRow(ResultSet rst, int rowNum) throws SQLException {
                    String existe = new String();
                    existe = rst.getString(1);
                    return existe;
                }
        });
        if (Integer.parseInt(sExiste)==0) bExiste = false;
        else bExiste = true;
        return bExiste;
    }
    
    @Override
	public String nuevologemail(Usuario usuarioBean) {		
		Map output = storednuevoemail.execute(usuarioBean);
		String out = (String) output.get("out");
		return out;
			
	}

    public Boolean insertLogMail(int codFicha, int codResponsable) {
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
		String ls_reponsable = "";
        //ls_reponsable = String.valueOf(codResponsable);
		ls_reponsable = usuario.getLogin();
		
    	Date date = new Date();
    	String annio = Integer.toString(c.get(Calendar.DAY_OF_YEAR));
        Boolean result = false;
        
        //codResponsable
        System.out.println("Insertar Log Mail estoy dentro del DAO");
        System.out.println(codFicha);
        System.out.println(ls_reponsable);
        System.out.println(annio);
        System.out.println(ls_reponsable);
        
        
        try{
            //jdbcTemplate.execute("INSERT INTO s_guia.atd_log_mail"+
            //                     "  (ncod_ficha, vfecha_envio, dfecha_envio, dfeccre, vrescre)"+
            //                     "VALUES"+
            //                     "  ("+codFicha+", to_char(sysdate,'yyyyMMdd'), sysdate, sysdate, "+ls_reponsable+")");
        	
        	String sql = "insert into s_guia.atd_log_mail (ncod_ficha, vfecha_envio, dfecha_envio, dfeccre, vrescre)"
        		+ " values(?,?,?,?,?)";
        	
        	 //Object[] parametros = new Object[] { codFicha, annio, date, date, codResponsable};
        	 Object[] parametros = new Object[] { codFicha, annio, date, date, ls_reponsable};
    		 int rs = jdbcTemplate.update(sql, parametros);
    		 if (rs==0){
    			 System.out.println("Inserta atd_log_mail");
    	         System.out.println(codFicha);            
    	         System.out.println(codResponsable);
    	         result = true;
    		 } else {
    			 System.out.println("Error en insertar atd_log_mail");
    			 result = false;
    		 }
            
        }catch(Exception ex){
            result = false;
        }
        
        return result;
    }

    /**
     * @return the jdbcTemplate
     */
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    /**
     * @param jdbcTemplate the jdbcTemplate to set
     */
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

	public void setStorednuevoemail(StoredNuevoEmail storednuevoemail) {
		this.storednuevoemail = storednuevoemail;
	}
    
    

}