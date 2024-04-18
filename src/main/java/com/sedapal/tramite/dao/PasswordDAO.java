package com.sedapal.tramite.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;



import com.sedapal.tramite.beans.PasswordBean;
import com.sedapal.tramite.beans.Usuario;
import com.sedapal.tramite.dao.IPasswordDAO;

public class PasswordDAO implements IPasswordDAO{
	
	private JdbcTemplate jdbcTemplate;	
	
	private static Logger logger = Logger.getLogger("R1");		
		
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class,isolation=Isolation.SERIALIZABLE)
    public boolean updatePassword(String usuario, int ficha, String password) {		  
		  String sql = "update usuario set vpass='" +password+"' where VCODUSUARIO= '" +usuario+"' and  NCODFICHA="+ficha+"";
		 // System.out.println("Parametros:"+correlativo+"   "+ area);
		 // System.out.println("Update dirigido cancel:"+ sql);
		  int rs = jdbcTemplate.update(sql);
		  if (rs > 0)
		  return true;
		  return false;
	 }


	

	
	
}
