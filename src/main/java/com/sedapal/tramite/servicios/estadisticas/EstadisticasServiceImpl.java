package com.sedapal.tramite.servicios.estadisticas;

import com.keypoint.PngEncoder;
import com.sedapal.tramite.beans.LlavesBean;
import com.sedapal.tramite.beans.estadisticas.DatoGraficoBarraBean;
import com.sedapal.tramite.beans.estadisticas.DatoGraficoPieBean;
import com.sedapal.tramite.beans.estadisticas.EstadisticaDocEntrandaBean;
import com.sedapal.tramite.beans.estadisticas.PieRenderer;
import com.sedapal.tramite.dao.EstadisticasDAO;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.springframework.stereotype.Service;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;import org.jfree.data.xy.XYSeries;
import org.jfree.ui.RectangleEdge;
import org.springframework.beans.factory.annotation.Autowired;
;

/**
 *
 * @author Eli Diaz Horna
 */
@Service
public class EstadisticasServiceImpl implements EstadisticasService {

    private static final Logger logger = Logger.getLogger(EstadisticasServiceImpl.class);
    private static final EntityCollection EntityCollection = null;
    @Autowired
    private EstadisticasDAO estadisticasDao;
    String titulo;

    public byte[] obtenerGraficoPie(List<DatoGraficoPieBean> listadoValores, Color colorBg, Integer ancho, Integer alto, String titulo, String subtitulo, Boolean leyendaAct, Boolean tooltipsAct) {
        logger.debug("Ingresando al metodo '" + this.getClass().getName() + ".obtenerGraficoPie'");
        byte[] imgPie;

        final DefaultPieDataset dataset = new DefaultPieDataset();

        double sumaTotal = 0.0;
        int cantidad = 0;
        for(int i=0;i<listadoValores.size(); i++){
            DatoGraficoPieBean beanDatoPie = listadoValores.get(i);
            sumaTotal = sumaTotal + beanDatoPie.getMontoColumna();
            cantidad = cantidad ++;
        }

        for(int i=0;i<listadoValores.size(); i++){
            DatoGraficoPieBean beanDatoPie = listadoValores.get(i);

            Double prcElem = (beanDatoPie.getMontoColumna() * 100.00)/sumaTotal;
            DecimalFormat dfDec = new DecimalFormat("#,##0.0#");
            DecimalFormat dfNDe = new DecimalFormat("#,##0");
                                   
            String nomColumna = beanDatoPie.getNomColumna()+ " - "+dfNDe.format(beanDatoPie.getMontoColumna())+" ("+dfDec.format(prcElem)+"%)";

            dataset.setValue(nomColumna, beanDatoPie.getMontoColumna());
        }
        
        final JFreeChart chart = ChartFactory.createPieChart3D(titulo, dataset, leyendaAct, tooltipsAct, false);
        LegendTitle legend = chart.getLegend();
        legend.setPosition(RectangleEdge.BOTTOM);

        chart.setBackgroundPaint(colorBg);
        final TextTitle subtitle = new TextTitle(subtitulo);
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 12));

		PiePlot plot = (PiePlot)chart.getPlot();
        Color[] colors = {Color.green, Color.yellow, Color.red};
        PieRenderer renderer = new PieRenderer(colors);
        renderer.setColor(plot, dataset);
        chart.addSubtitle(subtitle);
        chart.setBackgroundPaint(Color.ORANGE);
        ChartRenderingInfo info = null;
        info = new ChartRenderingInfo(new StandardEntityCollection());
        BufferedImage chartImage = chart.createBufferedImage(ancho, alto, info);

        PngEncoder encoder = new PngEncoder(chartImage, false, 0, 9);
        imgPie = encoder.pngEncode();

        return imgPie;
    }
    
    
    
    public byte[] obtenerGraficoBarra(List<DatoGraficoBarraBean> listadoValores, Color colorBg, Integer ancho, Integer alto, String titulo, String subtitulo, Boolean leyendaAct, Boolean tooltipsAct) {
        logger.debug("Ingresando al metodo '" + this.getClass().getName() + ".obtenerGraficoBarra'");
        System.out.println("Ingresando al metodo '" + this.getClass().getName() + ".obtenerGraficoBarra'");
        byte[] imgBarra;        
        XYSeries series = new XYSeries("titulo de la serie");
        
        DefaultCategoryDataset datos = new DefaultCategoryDataset();

        int sumaTotal = 0;
        int cantidad = 0;
        
        for(int i=0;i<listadoValores.size(); i++){
        	DatoGraficoBarraBean beanDatoBarra = listadoValores.get(i);
            sumaTotal = sumaTotal + beanDatoBarra.getCantidadColumna() ;
            cantidad = cantidad ++;
        }
       

        for(int i=0;i<listadoValores.size(); i++){
        	DatoGraficoBarraBean beanDatoBarra = listadoValores.get(i);
        	
            int prcElem = (beanDatoBarra.getCantidadColumna());
            DecimalFormat dfDec = new DecimalFormat("#,##0.0#");
            DecimalFormat dfNDe = new DecimalFormat("#,##0");
            
            String nomColumna = beanDatoBarra.getNomColumna();
            int valor = beanDatoBarra.getCantidadColumna();
            String barra = nomColumna + " - "+ valor;
            datos.setValue(valor, barra, nomColumna);
            
            
        }
        
        String ls_nombre_reporte="";
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        LlavesBean beans = null;
        beans = (LlavesBean) session.getAttribute("sLlaves");        
        String tiporeporte = beans.getTipo_reporte();        
        //System.out.println("Quiero ver el tipo de reporte");
        //System.out.println(tiporeporte);
        
        if (tiporeporte.equals("EST002")) {
        	ls_nombre_reporte = "Documentos Derivados Por Area";
        } else if (tiporeporte.equals("EST003")) {
        	ls_nombre_reporte = "Documentos De Salida Por Area";
        } else if (tiporeporte.equals("EST004")) {
        	ls_nombre_reporte = "";
        } else {
        	ls_nombre_reporte = "";
        }

        final JFreeChart chart = ChartFactory.createBarChart3D(ls_nombre_reporte, "EQUIPOS", "CANTIDAD", datos, PlotOrientation.VERTICAL, true, true, false);
        
        LegendTitle legend = chart.getLegend();
        legend.setPosition(RectangleEdge.BOTTOM);
        

        chart.setBackgroundPaint(colorBg);
        final TextTitle subtitle = new TextTitle(subtitulo);
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 12));
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 8));
        
        CategoryPlot plot = chart.getCategoryPlot();
        BarRenderer render = (BarRenderer)plot.getRenderer();
        render.setSeriesPaint(0, Color.BLUE);
        render.setSeriesPaint(0, Color.RED);
        render.setSeriesPaint(0, Color.GREEN);
        render.setSeriesPaint(0, Color.GRAY);
        render.setSeriesPaint(0, Color.CYAN);
        render.setSeriesPaint(0, Color.MAGENTA);
        render.setSeriesPaint(0, Color.YELLOW);
        render.setSeriesPaint(0, Color.DARK_GRAY);
        render.setSeriesPaint(0, Color.PINK);
        chart.setBackgroundPaint(Color.ORANGE);
        //chart.addSubtitle(subtitle);
        render.setDrawBarOutline(true); //Borde a las barras
        
        ChartRenderingInfo info = null;       
        info = new ChartRenderingInfo(new StandardEntityCollection());        
        BufferedImage chartImage = chart.createBufferedImage(ancho, alto, info);
        

        PngEncoder encoder = new PngEncoder(chartImage, false, 0, 8);
        imgBarra = encoder.pngEncode();
       

        return imgBarra;
    }
   
    public byte[] obtenerGraficoBarraTres(List<DatoGraficoBarraBean> listadoValores, Color colorBg, Integer ancho, Integer alto, String titulo, String subtitulo, 
    	Boolean leyendaAct, Boolean tooltipsAct) {
        logger.debug("Ingresando al metodo '" + this.getClass().getName() + ".obtenerGraficoBarra'");
        System.out.println("Ingresando al metodo '" + this.getClass().getName() + ".obtenerGraficoBarra'");
        byte[] imgBarra;        
        XYSeries series = new XYSeries("titulo de la serie");
        
        DefaultCategoryDataset datos = new DefaultCategoryDataset();

        int sumaTotal = 0;
        int cantidad = 0;
        
        for(int i=0;i<listadoValores.size(); i++){
        	DatoGraficoBarraBean beanDatoBarra = listadoValores.get(i);
            sumaTotal = sumaTotal + beanDatoBarra.getCantidadColumna() ;
            cantidad = cantidad ++;
        }
       

        for(int i=0;i<listadoValores.size(); i++){
        	DatoGraficoBarraBean beanDatoBarra = listadoValores.get(i);
        	            
            String mes = beanDatoBarra.getNomMes();
            String estado =  beanDatoBarra.getNomEstado();
            int valor = Integer.parseInt(beanDatoBarra.getCantidad());
            
            System.out.println("Quiero ver los datos de obtener grafico");
            System.out.println(mes);
            System.out.println(estado);
            System.out.println(valor);
            datos.setValue(valor, estado, mes);
            
            
        }

        //final JFreeChart chart = ChartFactory.createBarChart3D("Numero de Materias Por Expedientes", "MATERIAS", "CANTIDAD", datos, PlotOrientation.VERTICAL, true, true, false);
        //final JFreeChart chart = ChartFactory.createBarChart("Estado de Documentos Por Equipos", "Equipos", "Cantidad", datos, PlotOrientation.HORIZONTAL, true, true, true);
        final JFreeChart chart = ChartFactory.createBarChart("Estado de Documentos de Entrada Por Equipos", "Equipos", "Cantidad", datos, PlotOrientation.VERTICAL, true, true, true);
        
        //JFreeChart chartArchivo= ChartFactory.createBarChart("Tabla de Equipos", "Equipos", "Partidos", datasetArchivo, PlotOrientation.HORIZONTAL, true, true, true);
        //aqui es donde generamos el archivo jpeg
        //ChartUtilities.saveChartAsJPEG(new File("imagen.jpeg"),chart,500,500);
        
        LegendTitle legend = chart.getLegend();
        legend.setPosition(RectangleEdge.BOTTOM);
        

        chart.setBackgroundPaint(colorBg);
        final TextTitle subtitle = new TextTitle(subtitulo);
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 12));
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 8));
        
        CategoryPlot plot = chart.getCategoryPlot();
        BarRenderer render = (BarRenderer)plot.getRenderer();
        render.setSeriesPaint(0, Color.RED);
        render.setSeriesPaint(0, Color.BLUE);
        render.setSeriesPaint(0, Color.GREEN);      
        chart.setBackgroundPaint(Color.ORANGE);
        //chart.addSubtitle(subtitle);
        render.setDrawBarOutline(true); //Borde a las barras
        
        ChartRenderingInfo info = null;       
        info = new ChartRenderingInfo(new StandardEntityCollection());        
        BufferedImage chartImage = chart.createBufferedImage(ancho, alto, info);
        

        PngEncoder encoder = new PngEncoder(chartImage, false, 0, 8);
        imgBarra = encoder.pngEncode();
       

        return imgBarra;
    }
   
    
    

    public List<EstadisticaDocEntrandaBean> getListadoEstadisticaDocsEntrantesByArea(int codArea, String fechaIni, String fechaFin) {
        logger.debug("Ingresando al metodo '" + this.getClass().getName() + ".getListadoEstadisticaDocsEntrantesByArea'");
        List resultList = new ArrayList();
        resultList = estadisticasDao.getListadoResumenDocEntrada(fechaIni, fechaFin, codArea);
        
        return resultList;
    }
    
    public List<EstadisticaDocEntrandaBean> getListadoEstadisticaporsituacionByArea(int codArea, String fechaIni, String fechaFin) {
        logger.debug("Ingresando al metodo '" + this.getClass().getName() + ".getListadoEstadisticaDocsEntrantesByArea'");
        List resultList = new ArrayList();
        //resultList = estadisticasDao.getListadoResumenDocEntrada(fechaIni, fechaFin, codArea);
        System.out.println("Estoy en el evento Estadistica Service");
        System.out.println("fecha inicial " + fechaIni);
        System.out.println("fecha final " + fechaFin);
        System.out.println("Area " + codArea);
        resultList = estadisticasDao.getListadoResumenporsituacion(fechaIni, fechaFin, codArea);
        return resultList;
    }
    
    public List<EstadisticaDocEntrandaBean> getListadoEstadisticaMateriasExpedientes(String fechaIni, String fechaFin, String tiporeporte, int ncodarea) {
	 	logger.debug("Ingresando al metodo '" + this.getClass().getName() + ".getListadoEstadisticaMateriasExpedientes'");
        System.out.println("Ingresando al metodo '" + this.getClass().getName() + ".getListadoEstadisticaMateriasExpedientes'");
        List resultList = new ArrayList();
        System.out.println("Viendo el tipo de reporte : " + tiporeporte);
       

        resultList = estadisticasDao.getListadoResumenMateriaExpDoc(fechaIni, fechaFin, tiporeporte, ncodarea);
        return resultList;        
    }
    
    
    
    
    @Override
	public List<EstadisticaDocEntrandaBean> getListadoEstadisticaEstadoExp(String fechaIni, String fechaFin, int ncodarea) {
		// TODO Auto-generated method stub
    	System.out.println("Quiero ver el parametros paso dos");
		//System.out.println(vano);
        List resultList = new ArrayList();
        resultList = estadisticasDao.getListadoResumenEstados(fechaIni, fechaFin, ncodarea);       
        return resultList;
	}
    
        
   
    public byte[] getImgEstadisticaDocsEntrantesByArea(int codArea, String strFechaIni, String strFechaFin) {
        logger.debug("Ingresando al metodo '" + this.getClass().getName() + ".getImgEstadisticaDocsEntrantes'");
        byte[] imgEstDocsEntr;
        List<EstadisticaDocEntrandaBean> listadoEstDocEntrantes = new ArrayList();
        listadoEstDocEntrantes = getListadoEstadisticaDocsEntrantesByArea(codArea, strFechaIni, strFechaFin);

        List<DatoGraficoPieBean> listadoValores = new ArrayList();
        for(int i=0; i<listadoEstDocEntrantes.size(); i++){
            DatoGraficoPieBean beanDatoPie = new DatoGraficoPieBean();
            beanDatoPie.setNomColumna(listadoEstDocEntrantes.get(i).getNomColumna());
            beanDatoPie.setMontoColumna(Double.parseDouble(listadoEstDocEntrantes.get(i).getValColumna()));
            listadoValores.add(beanDatoPie);
        }
        
        String nomArea;
        if(listadoEstDocEntrantes.size()>0){
        	nomArea = listadoEstDocEntrantes.get(0).getNomArea(); 
        }else{
        	nomArea = "";
        }
        
        String titulo = "Documentos Entrantes Por Estados";
        String subtitulo = "Del area "+codArea+" - "+nomArea+" del "+strFechaIni+" al "+strFechaFin;
        
        imgEstDocsEntr = obtenerGraficoPie(listadoValores, Color.white, 600, 250, titulo, subtitulo, Boolean.TRUE, Boolean.TRUE);
        return imgEstDocsEntr;
    }


    @Override
	public byte[] getImgBarraEstadisticaDocsEntrantesByArea(String strFechaIni, String strFechaFin, String tiporeporte, int ncodarea) {
		logger.debug("Ingresando al metodo '" + this.getClass().getName() + ".getImgEstadisticaDocsEntrantes'");
        System.out.println("Ingresando al metodo '" + this.getClass().getName() + ".getImgEstadisticaDocsEntrantes'");
        byte[] imgEstDocsEntr;
        List<EstadisticaDocEntrandaBean> listadoEstDocEntrantes = new ArrayList();

        listadoEstDocEntrantes = getListadoEstadisticaMateriasExpedientes(strFechaIni, strFechaFin, tiporeporte, ncodarea);
        
        List<DatoGraficoBarraBean> listadoValores = new ArrayList();
        
        for(int i=0; i<listadoEstDocEntrantes.size(); i++){
        	DatoGraficoBarraBean beanDatoBarra = new DatoGraficoBarraBean();
        	beanDatoBarra.setNomColumna(listadoEstDocEntrantes.get(i).getNomColumna());
        	beanDatoBarra.setCantidadColumna(Integer.parseInt(listadoEstDocEntrantes.get(i).getValColumna()));        	        	
        	listadoValores.add(beanDatoBarra);
        }
        
               
        
        String subtitulo = "Del "+strFechaIni+" al "+strFechaFin;
                
        imgEstDocsEntr = obtenerGraficoBarra(listadoValores, Color.white, 600, 250, titulo, subtitulo, Boolean.TRUE, Boolean.TRUE);
        return imgEstDocsEntr;
	}

    @Override
	public byte[] getImgBarraEstadisticaEstado(String fechaIni, String fechaFin, int ncodarea) {
		logger.debug("Ingresando al metodo '" + this.getClass().getName() + ".getImgEstadisticaDocsEntrantes'");
        System.out.println("Ingresando al metodo '" + this.getClass().getName() + ".getImgEstadisticaDocsEntrantes'");
        byte[] imgEstDocsEntr;
        
        List<EstadisticaDocEntrandaBean> listadoEstDocEntrantes = new ArrayList();

        listadoEstDocEntrantes = getListadoEstadisticaEstadoExp(fechaIni, fechaFin, ncodarea);
        
        List<DatoGraficoBarraBean> listadoValores = new ArrayList();
        
        for(int i=0; i<listadoEstDocEntrantes.size(); i++){
        	DatoGraficoBarraBean beanDatoBarra = new DatoGraficoBarraBean();
        	beanDatoBarra.setNomMes(listadoEstDocEntrantes.get(i).getMes());
        	beanDatoBarra.setNomEstado(listadoEstDocEntrantes.get(i).getEstado());
        	beanDatoBarra.setCantidad(listadoEstDocEntrantes.get(i).getCantidad());
        	listadoValores.add(beanDatoBarra);
        }
        
              
        
        String nomArea="Prueba del sistema";
               
        String titulo = "Cantidad de Expedientes Por Estado";
        String subtitulo = "Prueba";
                
        imgEstDocsEntr = obtenerGraficoBarraTres(listadoValores, Color.white, 600, 250, titulo, subtitulo, Boolean.TRUE, Boolean.TRUE);
        
        return imgEstDocsEntr;
	}


	
	
	


	
	
}
