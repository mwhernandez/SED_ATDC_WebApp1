package com.sedapal.tramite.servicios;

import java.util.List;
import com.sedapal.tramite.beans.EntranteBean;

public interface IServiciosEntranteDerivado {
	public List<EntranteBean> catalogo(String ncodarea, int ficha);
	public void nuevoEntrante(EntranteBean entranteBean);
	public void actualizarEntrante(EntranteBean entranteBean);
	public void eliminarEntrante(String anno, String origen, String tipodoc, String codigo, String login);
}
