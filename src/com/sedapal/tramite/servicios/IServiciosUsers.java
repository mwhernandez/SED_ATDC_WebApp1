package com.sedapal.tramite.servicios;

import java.util.List;



import com.sedapal.tramite.beans.UsersBean;



public interface IServiciosUsers {
	public List<UsersBean> catalogo();
	public void actualizarUser(UsersBean usersBean);
	public void eliminarUsers(String codigo);
	public void nuevoUsers(UsersBean usersBean);

}
