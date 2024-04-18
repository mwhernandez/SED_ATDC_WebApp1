package com.sedapal.tramite.servicios;

import java.util.List;




import com.sedapal.tramite.beans.SecuencialBean;
import com.sedapal.tramite.beans.TiposBean;
import com.sedapal.tramite.beans.admin.SecuencialAdmBean;



public interface IServiciosSecuencial {	
	public List<SecuencialAdmBean> catalogo();
	public String nuevoSecuencia(SecuencialBean secuencialBean);
	public void actualizarSecuencial(SecuencialBean secuencialBean);
	public void eliminarSecuencial(String codigo);
}
