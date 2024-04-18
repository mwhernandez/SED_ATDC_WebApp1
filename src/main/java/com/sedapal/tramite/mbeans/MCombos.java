package com.sedapal.tramite.mbeans;

import java.util.List;


import com.sedapal.tramite.dao.IProductoDAO;

public class MCombos {

    private IProductoDAO productoDAO;
    private List items;
    private String itemSeleccionado;
    //se ejecuta al inicio de la aplicacion

    public void init() {
        //is.items = itemsTipos;
        //tem.out.println("Se cargo los combos....");
        //for(Tipos p:tipos)
        //System.out.println(p.getTipo()+"   " + p.getDescripcion());
    }

    public MCombos() {
        // TODO Auto-generated constructor stub
    }

    public void setProductoDAO(IProductoDAO productoDAO) {
        this.productoDAO = productoDAO;
    }

    public List getItems() {
        return items;
    }

    public void setItems(List items) {
        this.items = items;
    }

    public String getItemSeleccionado() {
        return itemSeleccionado;
    }

    public void setItemSeleccionado(String itemSeleccionado) {
        this.itemSeleccionado = itemSeleccionado;
    }
}
