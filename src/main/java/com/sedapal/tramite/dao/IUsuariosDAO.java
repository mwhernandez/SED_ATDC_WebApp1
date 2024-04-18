package com.sedapal.tramite.dao;

import java.util.Date;
import java.util.List;

import com.sedapal.tramite.beans.MarquesitaBean;
import com.sedapal.tramite.beans.Usuario;

public interface IUsuariosDAO {
   public Usuario autenticar(String login, String clave);   
   /*Agrega el 15/10/2013  Eli Diaz Logeo de usuario */
   //public String geinsertarLog(int codsistema, String usuario, Date fecha, String ip, int area, String acceso, String ingreso, int correlativo);
   public String UpdateLogs(int codsistema, String usuario, Date fecha, String ip, int area, String acceso, String ingreso, int correlativo);
   
   /*Agrega el 14/11/2013  Eli Diaz */
   public String marquesita();
   /*Agregado el 16/11/2011 - Alfredo Panitz */
   public String getEmailByFichaUsu(int codFichaUsu);
   public List<Usuario> getSecretariasByCodArea(int codArea);
   public Usuario getUsuarioByCodUsu(String codUsu);
   public String getSecuencial();
   public String getEmailJefe(int codarea);
   public void nuevoLogingreso(Usuario usuariosBean);
   


}
