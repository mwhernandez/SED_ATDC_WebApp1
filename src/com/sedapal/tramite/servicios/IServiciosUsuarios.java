package com.sedapal.tramite.servicios;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.sedapal.tramite.beans.EntranteBean;
import com.sedapal.tramite.beans.MarquesitaBean;
import com.sedapal.tramite.beans.Usuario;

public interface IServiciosUsuarios {
	public Usuario autenticar(String login, String clave);
	
	/* Agregado el 14/11/2011 - Eli Diaz - Marquesita */
	public String marquisita();  

	/* Agregado el 16/11/2011 - Alfredo Panitz */
	public Boolean enviarEmailByFichaUsu(int codFichaUsu, String mensaje, String asunto);

	public String getEmailByFichaUsu(int codFichaUsu);

	public List<Usuario> getSecretariasByCodArea(int codArea);

	public Map getUsuarioByCodUsu(String codUsu);

	public Boolean revisarEnvioMailDiario(int codFicha);

	public List<EntranteBean> obtenerDocEntrUrgentesByCodArea(int codArea);

	public Boolean insertarLogMailDiario(int codFicha, int codResponsable);

	public String insertarLog(int codsistema, String usuario, Date fecha, String ip, int area, String acceso, String ingreso, int correlativo);

	public Boolean enviarEmail(String correoElectronico, String mensaje, String asunto);
	
	public String  nuevologemail(Usuario usuarioBean);

	public void nuevoLogIngreso(Usuario usuarioBean);

	

	

	
}
