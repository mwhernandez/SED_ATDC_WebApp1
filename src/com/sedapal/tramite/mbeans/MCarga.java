package com.sedapal.tramite.mbeans;

import com.icesoft.faces.component.ext.HtmlCommandButton;
import com.icesoft.faces.component.inputfile.InputFile;
import com.icesoft.faces.component.inputfile.FileInfo;
import com.icesoft.faces.context.Resource;
import com.icesoft.faces.context.Resource.Options;
import com.sedapal.tramite.dao.SecuencialDAO;

import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import java.util.*;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.File;

/**
 * <p> The InputFileController is responsible for the file upload logic as well
 * as the file deletion object. A users file uploads are only visible to them
 * and are deleted when the session is destroyed. </p>
 *
 * @since 1.7
 */
public class MCarga implements Serializable {

    public static final Logger log = Logger.getLogger(MCarga.class);
    private String nombrePdf;
    Calendar c = Calendar.getInstance();
    // File sizes used to generate formatted label
    public static final long MEGABYTE_LENGTH_BYTES = 1048000l;
    public static final long KILOBYTE_LENGTH_BYTES = 1024l;
    public static final long MAX_LENGTH_BYTES = 1024000;
    private boolean verConfirmacion = false;
    private String msg;
    private boolean verAlerta = false;
    ;
	private String msgAlerta;
    private final List fileList = Collections.synchronizedList(new ArrayList());
    private InputFileData currentFile;
    private int fileProgress;
    private String componentStatus = "";
    private HtmlCommandButton botonCarga;
    private static Logger logger = Logger.getLogger("R1");
    private Resource imgResource;
    private Resource pdfResource;
    private Resource pdfResourceDynFileName;
    private String fileName = "Choose-a-new-file-name";
    public static final String RESOURCE_PATH = "/WEB-INF/classes/org/icefaces/application/showcase/view/resources/";
    
    private SecuencialDAO secuencialDAO;
    String nombre_archivo;    
    String ubicacion;

    public void uploadFile(ActionEvent event) {


        //OJO aqui llamar al DAO para que genere el nombre correlativo
        InputFile inputFile = (InputFile) event.getSource();

        // fileInfo puede obtener informacion del archivo como su nombre etc...
        FileInfo fileInfo = inputFile.getFileInfo();

        // System.out.println(fileInfo.getPhysicalPath()+"\\"+fileInfo.getFileName());
        System.out.println("SIZE FILE!!!:" + fileInfo.getSize());
        String nombreFile = fileInfo.getFileName();
        String extension = nombreFile.substring(nombreFile.lastIndexOf("."), nombreFile.length());		
		System.out.println("Estoy en el uploadFile");
		System.out.println("EXTENSION!!!:" + extension);
        
			if (extension.equals(".pdf") || (extension.equals(".PDF"))) {
	        if (fileInfo.getStatus() == FileInfo.SAVED) {
	            ResourceBundle bundle = ResourceBundle.getBundle("com.sedapal.tramite.files.parametros");
	            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	            String secuencial = this.secuencialDAO.correlativo();
	            //String secuencial = (String) session.getAttribute("nombrePdf");
	            String tipodoc = (String) session.getAttribute("tipodoc");
	            int indicador = (Integer) session.getAttribute("indicador");
	            Date date = new Date();
	            String annio = Integer.toString(c.get(Calendar.YEAR));
	            componentStatus = "Grabado!";
	            System.out.println("Estoy evento cero");
	            try {
	            	
	                // obtenemos el archivo
	                File file = fileInfo.getFile();
	                if (indicador == 1) {
	                    file.renameTo(new File(bundle.getString("path.entrada") + "/" + annio + "" + secuencial + ".pdf"));
	                } else if (indicador == 2) {
	                    file.renameTo(new File(bundle.getString("path.salida") + "/" + annio + "" + secuencial + ".pdf"));
	                } else if (indicador == 3) {
	                    file.renameTo(new File(bundle.getString("path.seguimiento") + "/" + annio + "" + secuencial + ".pdf"));
	                } else if (indicador == 4) {
	                    file.renameTo(new File(bundle.getString("path.mesa") + "/" + annio + "" + secuencial + ".pdf"));
	                }
	                System.out.println("Antes del Update de correlativos");
	                //secuencialDAO.updatecorrelativo(secuencial,tipodoc);
	                System.out.println("Update de correlativos");
	                System.out.println("Imprimo variables Mcarga");
	                System.out.println(secuencial + tipodoc);
	                if (session != null) {
	                    System.out.println("nombre file:" + secuencial);
	                    session.setAttribute("file", secuencial + ".pdf");
	                    // botonCarga.setVisible(true);
	                }
	                System.out.println("Estoy evento uno");
	                //inputFile.reset();       
	            } catch (Exception e) {
	                logger.error("[Mcarga.uploadFile]", e);
	                String mess = "Error Interno SQL";
	                componentStatus = mess;
	                this.fileProgress = 0;
	                mess = null;
	
	            }
	
	
	        } else {
	        	System.out.println("Estoy evento dos");
	            String mess = null;
	            if (fileInfo.getStatus() == FileInfo.SIZE_LIMIT_EXCEEDED) {
	                mess = "Límite Máximo (40.0 MB) excedido !!";
	            } 
	            /*
	             * Eli comenta este codigo por la incidencia presentado 20/04/2017
	            else {
	            	System.out.println("Estoy evento tres");
	                mess = " Archivo corrupto.";
	            } */
	
	            componentStatus = "No pudo ser cargado." + mess;
	            this.fileProgress = 0;
	            mess = null;
	        } 
			} else {
				String mess = null;
				System.out.println("Estoy evento cuatro");
				mess = " No es un Archivo con formato PDF";	
				componentStatus = "No pudo ser cargado." + mess ;
				this.fileProgress = 0;
				mess = null;
			}
	
	    }

    public void ejecutar(ActionEvent event) {
        this.msg = "Está Seguro de Ejecutar la Carga del Archivo?";
        logger.debug("[MCarga] Viendo dialogo!!!");
        this.verConfirmacion = true;

    }

    public void okConfirm(ActionEvent actionEvent) {
        this.ejecutarCarga();
        this.verConfirmacion = false;
        this.componentStatus = "";
    }

    public void cancelar(ActionEvent actionEvent) {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        File file = (File) session.getAttribute("file");
        file.delete();
        botonCarga.setVisible(false);
        botonCarga.setVisible(false);
        this.componentStatus = "";
        this.verConfirmacion = false;
        this.fileProgress = 0;
    }

    public void ejecutarCarga() {
        BufferedReader input = null;
        int contadorUsuInsert = 0;
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        File file = (File) session.getAttribute("file");
        try {
            input = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = input.readLine()) != null) {
                // String[] columnas = Extract.getColumns(line);

                // cargaMasivaUsuariosImplement.insertar(columnas);
                contadorUsuInsert++;
            }

            // *************//
            InputStream is = new BufferedInputStream(new FileInputStream(file));

            this.msgAlerta = "Se leyeron " + contadorUsuInsert
                    + " lineas del file";
            this.verAlerta = true;

        } catch (IOException e) {
            this.msgAlerta = "No ha sido posible carga el archivo";
            this.fileProgress = 0;
            this.verAlerta = true;
            e.printStackTrace();
        } catch (Exception e) {
            this.msgAlerta = "Problemas con la base de datos";
            this.fileProgress = 0;
            this.verAlerta = true;
            e.printStackTrace();
        } finally {
            componentStatus = "";
            try {
                input.close();
            } catch (IOException e) {

                e.printStackTrace();
            }

            file.delete();
            botonCarga.setVisible(false);
        }

    }

    public void okAlerta(ActionEvent event) {
        this.verAlerta = false;
    }

    public void fileUploadProgress(EventObject event) {
        InputFile ifile = (InputFile) event.getSource();
        fileProgress = ifile.getFileInfo().getPercent();
    }

    /**
     * <p> Allows a user to remove a file from a list of uploaded files. This
     * methods assumes that a request param "fileName" has been set to a valid
     * file name that the user wishes to remove or delete </p>
     *
     * @param event jsf action event
     */
    public void removeUploadedFile(ActionEvent event) {
        // Get the inventory item ID from the context.
        FacesContext context = FacesContext.getCurrentInstance();
        Map map = context.getExternalContext().getRequestParameterMap();
        String fileName = (String) map.get("fileName");

        synchronized (fileList) {
            InputFileData inputFileData;
            for (int i = 0; i < fileList.size(); i++) {
                inputFileData = (InputFileData) fileList.get(i);
                // remove our file
                if (inputFileData.getFileInfo().getFileName().equals(fileName)) {
                    fileList.remove(i);
                    break;
                }
            }
            this.fileProgress = 0;
        }
    }

    public InputFileData getCurrentFile() {
        return currentFile;
    }

    public int getFileProgress() {
        return fileProgress;
    }

    public void setFileProgress(int fileProgress) {
        this.fileProgress = fileProgress;
    }

    public List getFileList() {
        return fileList;
    }

    public String getComponentStatus() {
        return componentStatus;
    }

    public void setComponentStatus(String componentStatus) {
        this.componentStatus = componentStatus;
    }

    public HtmlCommandButton getBotonCarga() {
        return botonCarga;
    }

    public void setBotonCarga(HtmlCommandButton botonCarga) {
        this.botonCarga = botonCarga;
    }

    public boolean isVerConfirmacion() {
        return verConfirmacion;
    }

    public void setVerConfirmacion(boolean verConfirmacion) {
        this.verConfirmacion = verConfirmacion;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsgAlerta() {
        return msgAlerta;
    }

    public void setMsgAlerta(String msgAlerta) {
        this.msgAlerta = msgAlerta;
    }

    public boolean isVerAlerta() {
        return verAlerta;
    }

    public void setVerAlerta(boolean verAlerta) {
        this.verAlerta = verAlerta;
    }

    public MCarga() {
        /*
         * try{ FacesContext fc = FacesContext.getCurrentInstance();
         * ExternalContext ec = fc.getExternalContext(); imgResource = new
         * MyResource(ec,"logo.jpg"); pdfResource = new
         * MyResource(ec,"WP_Security_Whitepaper.pdf"); pdfResourceDynFileName =
         * new MyResource(ec,"WP_Security_Whitepaper.pdf"); } catch(Exception
         * e){ e.printStackTrace(); }
         */
    }

    @PostConstruct
    public void inicia() {
        /*
         * FacesContext fc = FacesContext.getCurrentInstance(); ExternalContext
         * ec = fc.getExternalContext(); pdfResource = new
         * MyResource(ec,"WP_Security_Whitepaper.pdf");
         */
    }

    public Resource getImgResource() {
        return imgResource;
    }

    public Resource getPdfResource() {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        pdfResource = new MyResource(ec, this.nombrePdf);
        return pdfResource;
    }

    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buf = new byte[4096];
        int len = 0;
        while ((len = input.read(buf)) > -1) {
            output.write(buf, 0, len);
        }
        return output.toByteArray();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Resource getPdfResourceDynFileName() {
        return pdfResourceDynFileName;
    }

    public void setPdfResourceDynFileName(Resource pdfResourceDynFileName) {
        this.pdfResourceDynFileName = pdfResourceDynFileName;
    }

    public String getNombrePdf() {
        return nombrePdf;
    }

    public void setNombrePdf(String nombrePdf) {
        this.nombrePdf = nombrePdf;
    }

    public void setSecuencialDAO(SecuencialDAO secuencialDAO) {
        this.secuencialDAO = secuencialDAO;
    }

    class MyResource implements Resource, Serializable {

        private String resourceName;
        private InputStream inputStream;
        private final Date lastModified;
        private ExternalContext extContext;

        public MyResource(ExternalContext ec, String resourceName) {
            this.extContext = ec;
            this.resourceName = resourceName;
            this.lastModified = new Date();
        }

        /**
         * This intermediate step of reading in the files from the JAR, into a
         * byte array, and then serving the Resource from the
         * ByteArrayInputStream, is not strictly necessary, but serves to
         * illustrate that the Resource content need not come from an actual
         * file, but can come from any source, and also be dynamically
         * generated. In most cases, applications need not provide their own
         * concrete implementations of Resource, but can instead simply make use
         * of com.icesoft.faces.context.ByteArrayResource,
         * com.icesoft.faces.context.FileResource,
         * com.icesoft.faces.context.JarResource.
         */
        public InputStream open() throws IOException {
            if (inputStream == null) {

                // InputStream stream =
                // extContext.getResourceAsStream(OutputResourceBean.RESOURCE_PATH
                // + resourceName);				

                String path = "/usr/local/apache2/htdocs/joomla/entrada/"
                        + this.resourceName;
                // String path="http://1.1.194.53/entrada/"+ this.resourceName;
                InputStream stream = new BufferedInputStream(
                        new FileInputStream(path));
                byte[] byteArray = OutputResourceBean.toByteArray(stream);
                inputStream = new ByteArrayInputStream(byteArray);
            }
            return inputStream;
        }

        public String calculateDigest() {
            return resourceName;
        }

        public Date lastModified() {
            return lastModified;
        }

        public void withOptions(Options arg0) throws IOException {
        }

        public String getResourceName() {
            return resourceName;
        }

        public void setResourceName(String resourceName) {
            this.resourceName = resourceName;
        }
    }
}
