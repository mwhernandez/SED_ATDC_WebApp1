package com.sedapal.tramite.servicios.estadisticas;

import com.sedapal.tramite.beans.estadisticas.DatoGraficoPieBean;
import com.sedapal.tramite.beans.estadisticas.EstadisticaDocEntrandaBean;
import com.sedapal.tramite.beans.estadisticas.DatoGraficoBarraBean;

import java.awt.Color;
import java.util.List;

/**
 *
 * @author Eli Diaz Horna
 */
public interface EstadisticasService {
    //Metodo que Genera los graficos
    public byte[] obtenerGraficoPie(List<DatoGraficoPieBean> listadoValores, Color colorBg, Integer ancho, Integer alto, String titulo, String subtitulo, Boolean leyendaAct, Boolean tooltipsAct);
    
    public byte[] obtenerGraficoBarra(List<DatoGraficoBarraBean> listadoValores, Color colorBg, Integer ancho, Integer alto, String titulo, String subtitulo, Boolean leyendaAct, Boolean tooltipsAct);
    
    public byte[] obtenerGraficoBarraTres(List<DatoGraficoBarraBean> listadoValores, Color colorBg, Integer ancho, Integer alto, String titulo, String subtitulo, Boolean leyendaAct, Boolean tooltipsAct);
    
    
    //Metodo de Consulta generica
    public List<EstadisticaDocEntrandaBean> getListadoEstadisticaDocsEntrantesByArea(int codArea, String fechaIni, String fechaFin);
    
    public List<EstadisticaDocEntrandaBean> getListadoEstadisticaporsituacionByArea(int codArea, String fechaIni, String fechaFin);
    
    
    public List<EstadisticaDocEntrandaBean> getListadoEstadisticaMateriasExpedientes(String fechaIni, String fechaFin, String tiporeporte, int ncodarea);
    
    public List<EstadisticaDocEntrandaBean> getListadoEstadisticaEstadoExp(String fechaIni, String fechaFin, int ncodarea);
    
        
    
    //Metodos de Consulta
    public byte[] getImgEstadisticaDocsEntrantesByArea(int codArea, String fechaIni, String fechaFin);
    
    public byte[] getImgBarraEstadisticaDocsEntrantesByArea(String fechaIni, String fechaFin, String tiporeporte, int ncodarea); 
    
    public byte[] getImgBarraEstadisticaEstado(String fechaIni, String fechaFin, int ncodarea);
    
    
    
}
