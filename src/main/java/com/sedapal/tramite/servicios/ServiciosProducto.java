package com.sedapal.tramite.servicios;

import java.util.List;

import com.sedapal.tramite.beans.ProductoBean;
import com.sedapal.tramite.dao.IProductoDAO;

public class ServiciosProducto implements IServiciosProducto{
	private IProductoDAO productoDAO;
	
    public List<ProductoBean> catalogo(){    	
    	return productoDAO.productos();
    }

	public void setProductoDAO(IProductoDAO productoDAO) {
		this.productoDAO = productoDAO;
	}
    
}
