package com.sedapal.tramite.servicios;

import java.util.List;


import com.sedapal.tramite.beans.RepresentanteBean;
import com.sedapal.tramite.dao.IRepresentanteDAO;


public class ServiciosRepresentante implements IServiciosRepresentante{
	private IRepresentanteDAO representanteDAO;


	public List<RepresentanteBean> catalogo(int codigo) {
		return representanteDAO.representanteSP(codigo);
		
	}
	
	public void setRepresentanteDAO(IRepresentanteDAO representanteDAO) {
		this.representanteDAO = representanteDAO;
	}

	@Override
	public void actualizarRepresentante(RepresentanteBean representanteBean) {
		this.representanteDAO.actualizarRepresentanteSP(representanteBean);
	}

	@Override
	public String nuevoRemitente(RepresentanteBean representanteBean) {
		return representanteDAO.nuevoSP(representanteBean);
		
	}

	

	@Override
	public void eliminarRepresentante(int codremite, int codrepresentante) {		
		representanteDAO.eliminarSP(codremite,codrepresentante);
		
	}

	

}
