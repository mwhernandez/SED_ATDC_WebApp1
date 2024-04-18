package com.sedapal.tramite.dao;

import java.util.List;


import com.sedapal.tramite.beans.UsersBean;

public interface IUsersDAO {
	 public List<UsersBean> users();	 
	 public List usersSP();
	 public void actualizarUsersSP(UsersBean usersBean);
	 public void eliminarSP(String codigo);
	 public void nuevoSP(UsersBean usersBean);	

}
