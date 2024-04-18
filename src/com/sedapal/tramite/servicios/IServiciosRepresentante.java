package com.sedapal.tramite.servicios;

import java.util.List;

import com.sedapal.tramite.beans.RepresentanteBean;




public interface IServiciosRepresentante {
	public List<RepresentanteBean> catalogo(int codigo);
	public void actualizarRepresentante(RepresentanteBean representanteBean);
	public String nuevoRemitente(RepresentanteBean representanteBean);	
	public void eliminarRepresentante(int codremite, int codrepresentante);
	

}
