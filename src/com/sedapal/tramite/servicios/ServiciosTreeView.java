package com.sedapal.tramite.servicios;

import java.util.List;

import com.sedapal.tramite.beans.Area;
import com.sedapal.tramite.dao.TreeViewDAO;

public class ServiciosTreeView {
    private TreeViewDAO treeViewDAO;
    
	public ServiciosTreeView() {
		// TODO Auto-generated constructor stub
	}
	public List<Area> getDocumentos(String nroCorrelativo, String nanoLlave)
	{
		return treeViewDAO.getDocumentos(nroCorrelativo, nanoLlave);
	}
	public List<Area> getAreas(String nroCorrelativo, String nanoLlave)
	{
		return treeViewDAO.getAreas(nroCorrelativo, nanoLlave);
	}
	List<Area> getPersonas(String nroCorrelativo, String areaDerivado, String nanoLlave)
	{
		return treeViewDAO.getPersonas(nroCorrelativo, areaDerivado, nanoLlave);
	}
	public void setTreeViewDAO(TreeViewDAO treeViewDAO) {
		this.treeViewDAO = treeViewDAO;
	}	
}
