package com.sedapal.tramite.servicios;

import java.util.List;



import com.sedapal.tramite.beans.UsersBean;
import com.sedapal.tramite.dao.IUsersDAO;


public class ServiciosUsers implements IServiciosUsers{
	private IUsersDAO usersDAO;


	public List<UsersBean> catalogo() {
		return usersDAO.usersSP();
		
	}
	
	public void setUsersDAO(IUsersDAO usersDAO) {
		this.usersDAO = usersDAO;
	}

	@Override
	public void actualizarUser(UsersBean usersBean) {		
		usersDAO.actualizarUsersSP(usersBean);
		
	}

	@Override
	public void eliminarUsers(String codigo) {
		usersDAO.eliminarSP(codigo);
		
	}

	@Override
	public void nuevoUsers(UsersBean usersBean) {		
		usersDAO.nuevoSP(usersBean);
		
	}

	



}
