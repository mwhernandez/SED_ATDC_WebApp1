package com.sedapal.tramite.dao;

import com.sedapal.tramite.beans.Usuario;

/**
 *
 * @author Admin
 */
public interface ILogMailDAO {

    public Boolean getLogMailbyFichaAndFecha(int codFicha, String fechaEnvio);
    public Boolean insertLogMail(int codFicha, int codResponsable);
	public String  nuevologemail(Usuario usuarioBean);
}
