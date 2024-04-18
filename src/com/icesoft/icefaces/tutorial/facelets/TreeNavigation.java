/*
 * Version: MPL 1.1/GPL 2.0/LGPL 2.1
 *
 * "The contents of this file are subject to the Mozilla Public License
 * Version 1.1 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations under
 * the License.
 *
 * The Original Code is ICEfaces 1.5 open source software code, released
 * November 5, 2006. The Initial Developer of the Original Code is ICEsoft
 * Technologies Canada, Corp. Portions created by ICEsoft are Copyright (C)
 * 2004-2006 ICEsoft Technologies Canada, Corp. All Rights Reserved.
 *
 * Contributor(s): _____________________.
 *
 * Alternatively, the contents of this file may be used under the terms of
 * the GNU Lesser General Public License Version 2.1 or later (the "LGPL"
 * License), in which case the provisions of the LGPL License are
 * applicable instead of those above. If you wish to allow use of your
 * version of this file only under the terms of the LGPL License and not to
 * allow others to use your version of this file under the MPL, indicate
 * your decision by deleting the provisions above and replace them with
 * the notice and other provisions required by the LGPL License. If you do
 * not delete the provisions above, a recipient may use your version of
 * this file under either the MPL or the LGPL License."
 *
 */
package com.icesoft.icefaces.tutorial.facelets;

import com.icesoft.faces.component.tree.Tree;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import java.io.Serializable;

import com.sedapal.tramite.beans.EntranteBean;
import com.sedapal.tramite.beans.Usuario;
import com.sedapal.tramite.mbeans.MBeanSegDocuEntrates;
import com.sedapal.tramite.mbeans.MLogin;

/**
 * The TreeNavigation class is the backing bean for the navigation
 * tree on the left hand side of the application. Each node in the tree is made
 * up of a PageContent which is responsible for the navigation action when a
 * tree node is selected.
 * 
 * When the Tree component binding takes place the tree nodes are initialized
 * and the tree is built.  Any addition to the tree navigation must be made to
 * this class.
 *
 */

/*
 * Realizado por el Ing. Eli Diaz Horna -- 16-12-2010
 * Modificado por : Eli Diaz Horna  ------ 10-12-2012
 * Modificado por : Eli Diaz Horna  -----  29-11-2013
 * Modificado por : Eli Diaz Horna  -----  28-01-2014 ---MENU JEFES EQUIPOS
 * Modificado Por : Eli Diaz Horna  -----  13-10-2014 ---MENU GERENTES
 * Modificado Por : Eli Diaz Horna  -----  18-03-2015 ---MENU REPORTES GERENCIALES
 * Modificado Por : Eli Diaz Horna  -----  05-10-2016 ---MENU ESTADISTICOS GERENCIALES
 * Modificado Por : Eli Diaz Horna  -----  31-05-2017 ---MENU ASIGNACION DE DOCUMENTOS
 * Modificado Por : Eli Diaz Horna  -----  01-11-2018 ---MENU BUSQUEDA DE DOCUMENTOS EXTERNOS
 * Modificado Por : Eli Diaz Horna  -----  24-09-2019 ---MENU NUEVO DE REPORTE GERENCIALES DOCUMENTOS ASIGNADOS
 * Modificado Por : Eli Diaz Horna  -----  03-07-2020 ---MENU DE MESA DE PARTES VIRTUAL
 * Modificado Por : Eli Diaz Horna  -----  20-04-2021 ---MENU DE LINEA DE DIGITALIZACION y LECTOR DE CODIGO DE BARRA
 * Modificado Por : Eli Diaz Horna  -----  14-10-2021 ---MENU DE REPORTE DE MESA DE PARTES VIRTUAL
 */

public class TreeNavigation implements Serializable{
    
    // binding to component
    private Tree treeComponent;
    

    // bound to components value attribute
    private DefaultTreeModel model;

    // root node of tree, for delayed construction
    private DefaultMutableTreeNode rootTreeNode;

    // map of all navigation backing beans.
    private NavigationBean navigationBean;

    // initialization flag
    private boolean isInitiated;
   

    // folder icons for branch nodes
    private String themeBranchContractedIcon;
    private String themeBranchExpandedIcon;
    
    
    private MLogin mlogin;
   
    /**
     * Default constructor of the tree.  The root node of the tree is created at
     * this point.
     */
    public TreeNavigation() {    	
        // build root node so that children can be attached
        PageContentBean rootObject = new PageContentBean();
        rootObject.setMenuDisplayText("Tramite Documentario Corporativo");
        rootObject.setMenuContentTitle("Tramite Documentario Corporativo");        
        //rootObject.setMenuContentInclusionFile("./content/main.xhtml");               
        rootObject.setTemplateName("mainPanel");
        rootObject.setNavigationSelection(navigationBean);
        rootObject.setPageContent(true);
        rootTreeNode = new DefaultMutableTreeNode(rootObject);
        rootObject.setWrapper(rootTreeNode);

        model = new DefaultTreeModel(rootTreeNode);
        
        // xp folders (default theme)
        themeBranchContractedIcon = "xmlhttp/css/xp/css-images/tree_folder_open.gif";
        themeBranchExpandedIcon = "xmlhttp/css/xp/css-images/tree_folder_close.gif";
        
        
		
    }
    
    
    private void init() {
        // set init flag
        isInitiated = true;         
        
        ///acediendo a sesion http
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		///guardando en sesion un objeto
		String perfil;
		int ncodperfil;
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
		perfil = usuario.getPerfil();
		ncodperfil = usuario.getNcodperfil();
		//System.out.println(perfil);
		//System.out.println(ncodperfil);
		
		
       
        
        if (rootTreeNode != null) {
        	
        	       	
        	

        	// get the navigation bean from the faces context
            FacesContext facesContext = FacesContext.getCurrentInstance();
            Object navigationObject =
                    facesContext.getApplication()
                            .createValueBinding("#{navigation}")
                            .getValue(facesContext);
            
           

            if (navigationObject != null &&
                navigationObject instanceof NavigationBean) {

                // set bean callback for root
                PageContentBean branchObject =
                        (PageContentBean) rootTreeNode.getUserObject();

                // assign the initialized navigation bean, so that we can enable panel navigation
                navigationBean = (NavigationBean) navigationObject;

                // set this node as the default page to view
                navigationBean.setSelectedPanel(
                        (PageContentBean) rootTreeNode.getUserObject());
                branchObject.setNavigationSelection(navigationBean);

                /**
                 * Generate the backing bean for each tree node and put it all together
                 */
             
                
                // Nodo de Documentos Entrantes
                branchObject = new PageContentBean();                
                branchObject.setExpanded(false);                
                branchObject.setMenuDisplayText("Documentos Entrantes");
                branchObject.setMenuContentTitle("Documentos Entrantes");
                branchObject.setMenuContentInclusionFile("./content/main.xhtml");
                branchObject.setTemplateName("docEntrantePanel");
                branchObject.setNavigationSelection(navigationBean);                
                branchObject.setPageContent(false);               
                DefaultMutableTreeNode branchNode = 
                        new DefaultMutableTreeNode(branchObject);
                branchObject.setWrapper(branchNode);
                // finally add the new custom component branch
                if (ncodperfil !=13){
	                if (ncodperfil !=4){
	                		if (ncodperfil !=8){ 
	                			if (ncodperfil !=7){ 
	                				if (ncodperfil !=10){
	                					if (ncodperfil !=5){
	                					rootTreeNode.add(branchNode);
	                					}
	                				}
	                			}
	                		}	
	                	
	                } 
                }
                
             // Sub Nodo de Ingreso Documentos Entrantes
                branchObject = new PageContentBean();               
                branchObject.setMenuDisplayText("Bandeja Doc. Entrantes");
                //branchObject.setMenuContentTitle("Ingreso Doc. Entrantes");
                branchObject.setMenuContentInclusionFile("./content/DE/doc_entrante.jspx");
                branchObject.setTemplateName("ingresoContentPanel");
                branchObject.setNavigationSelection(navigationBean);
                
                DefaultMutableTreeNode leafNode =    	
                new DefaultMutableTreeNode(branchObject);
               
                branchObject.setWrapper(leafNode);                
                branchObject.setLeaf(true); 
                if (ncodperfil != 13){
	                if (ncodperfil !=4){
	                	if (ncodperfil !=6){
	                		if (ncodperfil !=3){
	                			if (ncodperfil !=5){
	                				if (ncodperfil !=8){
	                					if (ncodperfil !=7){ 
	                						if (ncodperfil !=10){
	                						branchNode.add(leafNode);
	                						}
	                					}
	                				}
	                			}
	                		}
	                	}
	                } 
                }
             // Sub Nodo de Ingreso Documentos Entrantes Grupo Areas
                branchObject = new PageContentBean();               
                branchObject.setMenuDisplayText("Bandeja Doc. Entrantes");
                //branchObject.setMenuContentTitle("Ingreso Doc. Entrantes");
                branchObject.setMenuContentInclusionFile("./content/DE/doc_entrante_grupo.jspx");
                branchObject.setTemplateName("ingresoContentPanel");
                branchObject.setNavigationSelection(navigationBean);
                
                DefaultMutableTreeNode leafNode1 =    	
                new DefaultMutableTreeNode(branchObject);
               
                branchObject.setWrapper(leafNode1);                
                branchObject.setLeaf(true);  
                if (ncodperfil ==6){                
                		branchNode.add(leafNode1);                	
                } 
                
                              
                
             // Sub Nodo de Ingreso Documentos Entrantes Grupo Areas
                branchObject = new PageContentBean();               
                branchObject.setMenuDisplayText("Documento de Entranda");
                //branchObject.setMenuContentTitle("Ingreso Doc. Entrantes");
                branchObject.setMenuContentInclusionFile("./content/DE/doc_entrante_consulta.jspx");
                branchObject.setTemplateName("ingresoContentPanel");
                branchObject.setNavigationSelection(navigationBean);
                
                DefaultMutableTreeNode leafNode2 =    	
                new DefaultMutableTreeNode(branchObject);
               
                branchObject.setWrapper(leafNode2);                
                branchObject.setLeaf(true);  
                if (ncodperfil ==3 ){                	
                		branchNode.add(leafNode2);                	
                } 
                
                ////////////////////////////////////////////////////////////
               
                // Nodo de Documentos de Gerentes 
                branchObject = new PageContentBean();
                branchObject.setExpanded(false);
                branchObject.setMenuDisplayText("Documentos Entrada");
                //branchObject.setMenuContentTitle("Documentos Salientes");
                branchObject.setTemplateName("docmesadegerentesPanel");
                branchObject.setNavigationSelection(navigationBean);
                if (ncodperfil ==10){
	                branchObject.setPageContent(false);
	                branchNode = new DefaultMutableTreeNode(branchObject);
	                branchObject.setWrapper(branchNode);
	                // finally add the new custom component branch
	                rootTreeNode.add(branchNode);
                } 
                
                // Sub Nodo de Gerentes
                branchObject = new PageContentBean();
                branchObject.setMenuDisplayText("Bandeja Doc. Entrada");
                //branchObject.setMenuContentTitle("Seguimento Documentos");
                branchObject.setMenuContentInclusionFile("./content/DE/doc_entrante_gerencias.jspx");
                branchObject.setTemplateName("ingresodocGerentesContentPanel");
                branchObject.setNavigationSelection(navigationBean);
                if (ncodperfil ==10){
	                leafNode = new DefaultMutableTreeNode(branchObject);
	                branchObject.setWrapper(leafNode);
	                branchObject.setLeaf(true);
	                // finally add the new custom component branch
	                branchNode.add(leafNode);
                }
                
            
                  
                ////////////////////////////////////////////////////////////
                // Nodo de Documentos de Mesa Partes 
                branchObject = new PageContentBean();
                branchObject.setExpanded(false);
                branchObject.setMenuDisplayText("Mesa de Partes");
                //branchObject.setMenuContentTitle("Documentos Salientes");
                branchObject.setTemplateName("docmesadepartesPanel");
                branchObject.setNavigationSelection(navigationBean);
                if (ncodperfil ==4 || ncodperfil==13){
	                branchObject.setPageContent(false);
	                branchNode = new DefaultMutableTreeNode(branchObject);
	                branchObject.setWrapper(branchNode);
	                // finally add the new custom component branch
	                rootTreeNode.add(branchNode);
                } 
                
             // Sub Nodo de Mesa Partes
                branchObject = new PageContentBean();
                branchObject.setMenuDisplayText("Bandeja Mesa Virtual");
                //branchObject.setMenuContentTitle("Seguimento Documentos");
                branchObject.setMenuContentInclusionFile("./content/DE/doc_entrante_mesa_virtual.jspx");
                branchObject.setTemplateName("ingresodocMPContentPanel");
                branchObject.setNavigationSelection(navigationBean);
                if (ncodperfil ==4){
	                leafNode = new DefaultMutableTreeNode(branchObject);
	                branchObject.setWrapper(leafNode);
	                branchObject.setLeaf(true);
	                // finally add the new custom component branch
	                branchNode.add(leafNode);
                }
                
                // Sub Nodo de Mesa Partes
                branchObject = new PageContentBean();
                branchObject.setMenuDisplayText("Bandeja Mesa Partes");
                //branchObject.setMenuContentTitle("Seguimento Documentos");
                branchObject.setMenuContentInclusionFile("./content/DE/doc_entrante_mesa.jspx");
                branchObject.setTemplateName("ingresodocMPContentPanel");
                branchObject.setNavigationSelection(navigationBean);
                if (ncodperfil ==4){
	                leafNode = new DefaultMutableTreeNode(branchObject);
	                branchObject.setWrapper(leafNode);
	                branchObject.setLeaf(true);
	                // finally add the new custom component branch
	                branchNode.add(leafNode);
                }
                
                // Sub Nodo de linea de Digitalizacion 05/05/2021
                branchObject = new PageContentBean();
                branchObject.setMenuDisplayText("Bandeja Línea Digitalización");
                //branchObject.setMenuContentTitle("Seguimento Documentos");
                branchObject.setMenuContentInclusionFile("./content/DE/doc_entrante_mesa_linea.jspx");
                branchObject.setTemplateName("ingresodocMPContentPanel");
                branchObject.setNavigationSelection(navigationBean);
                if (ncodperfil==13){
	                leafNode = new DefaultMutableTreeNode(branchObject);
	                branchObject.setWrapper(leafNode);
	                branchObject.setLeaf(true);
	                // finally add the new custom component branch
	                branchNode.add(leafNode);
                }
                
                // Sub Nodo de Lector de Codigo de Barras 05/05/2021
                branchObject = new PageContentBean();
                branchObject.setMenuDisplayText("Lector de Código de Barras");
                //branchObject.setMenuContentTitle("Seguimento Documentos");
                branchObject.setMenuContentInclusionFile("./content/DE/Lectorcodigobarras.jspx");
                branchObject.setTemplateName("ingresodocMPContentPanel");
                branchObject.setNavigationSelection(navigationBean);
                if (ncodperfil==13){
	                leafNode = new DefaultMutableTreeNode(branchObject);
	                branchObject.setWrapper(leafNode);
	                branchObject.setLeaf(true);
	                // finally add the new custom component branch
	                branchNode.add(leafNode);
                }
                
                ////////////////////////////////////////////////////////////
                
                // Nodo de Documentos Derivados
                branchObject = new PageContentBean();
                branchObject.setExpanded(false);
                branchObject.setMenuDisplayText("Documentos Derivados");
                //branchObject.setMenuContentInclusionFile("./content/main.xhtml");
                //branchObject.setMenuContentTitle("Documentos Salientes");
                branchObject.setTemplateName("docderivadoPanel");
                branchObject.setNavigationSelection(navigationBean);
                if (ncodperfil==8||ncodperfil==7){
                	//if (ncodperfil==7){
	                branchObject.setPageContent(false);
	                branchNode = new DefaultMutableTreeNode(branchObject);
	                branchObject.setWrapper(branchNode);
	                // finally add the new custom component branch
	                rootTreeNode.add(branchNode);	                
                	// }
                }
                
                // Sub Nodo de Ingreso de Documentos Derivados
                branchObject = new PageContentBean();
                branchObject.setMenuDisplayText("Bandeja Doc. Derivados");
                //branchObject.setMenuContentTitle("Registro Documentos Salientes");
                branchObject.setMenuContentInclusionFile("./content/DE/doc_derivado.jspx");
                branchObject.setTemplateName("derivadoContentPanel");
                branchObject.setNavigationSelection(navigationBean);
                if (ncodperfil==8||ncodperfil==7){
                	//if (ncodperfil==7){
	                leafNode = new DefaultMutableTreeNode(branchObject);
	                branchObject.setWrapper(leafNode);
	                branchObject.setLeaf(true);               
	                // finally add the new custom component branch
	                branchNode.add(leafNode); 
                	//}
                }
                ////////////////////////////////////////////////////////////                
                // Nodo de Documentos Salientes
                branchObject = new PageContentBean();
                branchObject.setExpanded(false);
                branchObject.setMenuDisplayText("Documento Salida");
                //branchObject.setMenuContentTitle("Documentos Salientes");
                branchObject.setTemplateName("docSalientePanel");
                branchObject.setNavigationSelection(navigationBean);
                branchObject.setPageContent(false);
                branchNode = new DefaultMutableTreeNode(branchObject);
                branchObject.setWrapper(branchNode);
                if (ncodperfil!=4){ 
	                if (ncodperfil != 13){                	
	                		//if (ncodperfil !=10){
	                			if (ncodperfil !=5){
	                				rootTreeNode.add(branchNode);
	                			}
	                		//}
	                	                
	                } 
                }
                ////////////////////////////////////////////////////
                // Sub Nodo de Ingreso de Documentos Salientes
                branchObject = new PageContentBean();
                branchObject.setMenuDisplayText("Bandeja Doc.Salientes");
                //branchObject.setMenuContentTitle("Registro Documentos Salientes");
                branchObject.setMenuContentInclusionFile("./content/DS/DocumentoSalida.jspx");
                branchObject.setTemplateName("salienteContentPanel");
                branchObject.setNavigationSelection(navigationBean);              
                leafNode = new DefaultMutableTreeNode(branchObject);
                branchObject.setWrapper(leafNode);
                if (ncodperfil==1||ncodperfil==2||ncodperfil==6||ncodperfil==7||ncodperfil==10){
                		if (ncodperfil !=5){
                		branchObject.setLeaf(true);
                		branchNode.add(leafNode);                	
                		}    	
                }
            	
                
                // Sub Nodo de consulta de Documentos Salientes
                branchObject = new PageContentBean();
                branchObject.setMenuDisplayText("Bandeja Doc.Salientes");
                //branchObject.setMenuContentTitle("Registro Documentos Salientes");
                branchObject.setMenuContentInclusionFile("./content/DS/DocumentoSalidaDerivado.jspx");
                branchObject.setTemplateName("salienteContentPanelconsulta");
                branchObject.setNavigationSelection(navigationBean);              
                leafNode = new DefaultMutableTreeNode(branchObject);
                branchObject.setWrapper(leafNode);
                if (ncodperfil==8){
                	branchObject.setLeaf(true);
                	branchNode.add(leafNode);
                	}
                 
             // Sub Nodo de consulta de Documentos Salientes Consulta
                branchObject = new PageContentBean();
                branchObject.setMenuDisplayText("Bandeja Doc.Salientes");
                //branchObject.setMenuContentTitle("Registro Documentos Salientes");
                branchObject.setMenuContentInclusionFile("./content/DS/DocumentoSalida_consulta.jspx");
                branchObject.setTemplateName("salienteContentPanelconsulta");
                branchObject.setNavigationSelection(navigationBean);              
                leafNode = new DefaultMutableTreeNode(branchObject);
                branchObject.setWrapper(leafNode);
                //if (ncodperfil==3||ncodperfil==8){
                if (ncodperfil==3){
                	branchObject.setLeaf(true);
                	branchNode.add(leafNode);
                	}
             
                
             // Nodo de Mantenimiento
                branchObject = new PageContentBean();
                branchObject.setExpanded(false);
                branchObject.setMenuDisplayText("Configuración de Tablas");
                branchObject.setMenuContentTitle("Mantenimiento de Tablas");
                branchObject.setTemplateName("mantenimientoPanel");
                branchObject.setNavigationSelection(navigationBean);
                branchObject.setPageContent(false);
                if (ncodperfil!=9){
	                if (ncodperfil!=3){                		
	                			branchNode = new DefaultMutableTreeNode(branchObject);
	                            branchObject.setWrapper(branchNode);
	                            rootTreeNode.add(branchNode); 
	                	}
	                	               
                }
             // Sub nodo de Tipo Documentos
                branchObject = new PageContentBean();
                branchObject.setMenuDisplayText("Tipo Documentos");
                //branchObject.setMenuContentTitle("Tipo Documentos");
                branchObject.setMenuContentInclusionFile("./content/MAN/tipos.jspx");
                branchObject.setTemplateName("tipodocumentosContentPanel");
                branchObject.setNavigationSelection(navigationBean);
                if (ncodperfil==1){	                
		                		leafNode = new DefaultMutableTreeNode(branchObject);
		                		branchObject.setWrapper(leafNode);
		                		branchObject.setLeaf(true);
		                		// finally add the new custom component branch
		                		branchNode.add(leafNode);	                			
                }
                
             // Sub nodo de Asuntos Estandarizados
                branchObject = new PageContentBean();
                branchObject.setMenuDisplayText("Asuntos Estandarizados");
                //branchObject.setMenuContentTitle("Tipo Documentos");
                branchObject.setMenuContentInclusionFile("./content/MAN/asunto.jspx");
                branchObject.setTemplateName("tipodocumentosContentPanel");
                branchObject.setNavigationSelection(navigationBean);
                if (ncodperfil!=13){
	                if (ncodperfil!=7){
		                if (ncodperfil!=8){
		                	if (ncodperfil!=3){
		                		if (ncodperfil !=5){	                	
		                		leafNode = new DefaultMutableTreeNode(branchObject);
		                		branchObject.setWrapper(leafNode);
		                		branchObject.setLeaf(true);
		                		// finally add the new custom component branch
		                		branchNode.add(leafNode);
		                		}
		                	}
		                }
	                }
                }
             // Sub Nodo de Registro de Empresas
                branchObject = new PageContentBean();
                branchObject.setMenuDisplayText("Registro Empresas");
                //branchObject.setMenuContentTitle("Registro Empresas");
                branchObject.setMenuContentInclusionFile("./content/MAN/remitente.jspx");
                branchObject.setTemplateName("empresasContentPanel");
                branchObject.setNavigationSelection(navigationBean);
                if (ncodperfil!=13){
	                if (ncodperfil!=7){
	                	if (ncodperfil!=8){                
		                	if (ncodperfil!=3){
		                		if (ncodperfil !=5){	                			
			                        leafNode = new DefaultMutableTreeNode(branchObject);
			                        branchObject.setWrapper(leafNode);
			                        branchObject.setLeaf(true);
			                        // finally add the new custom component branch                   
			                        branchNode.add(leafNode);	                			
		                		}
		                	}
	                	}
	                }
                }
             // Sub Nodo de Registro de Feriados
                branchObject = new PageContentBean();
                branchObject.setMenuDisplayText("Registro Feriados");
                //branchObject.setMenuContentTitle("Registro Empresas");
                branchObject.setMenuContentInclusionFile("./content/MAN/feriados.jspx");
                branchObject.setTemplateName("feriadosContentPanel");
                branchObject.setNavigationSelection(navigationBean);
                if (ncodperfil!=13){
	                if (ncodperfil!=7){
		                if (ncodperfil!=8){
		                	if (ncodperfil!=3){
		                		if (ncodperfil !=5){
		                		leafNode = new DefaultMutableTreeNode(branchObject);
		                		branchObject.setWrapper(leafNode);
		                		branchObject.setLeaf(true);
		                		//finally add the new custom component branch
		                		branchNode.add(leafNode);
		                		}
		                	}
		                }
	                }
                }    
             // Sub Nodo de Cambio de Password
                branchObject = new PageContentBean();
                branchObject.setMenuDisplayText("Cambio de Password");
                //branchObject.setMenuContentTitle("Cambio de Password");
                branchObject.setMenuContentInclusionFile("./content/MAN/password.jspx");
                branchObject.setTemplateName("passwordContentPanel");
                branchObject.setNavigationSelection(navigationBean);               
                if (ncodperfil!=3){
                		leafNode = new DefaultMutableTreeNode(branchObject);
                		branchObject.setWrapper(leafNode);
                		branchObject.setLeaf(true);
                //		finally add the new custom component branch
                		branchNode.add(leafNode);
                }
                
              
                ////////////////////////////////////////////////////////////
                // Nodo de Configuracion de Despacho 
                branchObject = new PageContentBean();
                branchObject.setExpanded(false);
                branchObject.setMenuDisplayText("Configuracion Despacho");
                //branchObject.setMenuContentTitle("Documentos Salientes");
                branchObject.setTemplateName("configuracionPanel");
                branchObject.setNavigationSelection(navigationBean);
                if (ncodperfil ==2) {
                		branchObject.setPageContent(false);
                		branchNode = new DefaultMutableTreeNode(branchObject);
                		branchObject.setWrapper(branchNode);
                		// finally add the new custom component branch
                		rootTreeNode.add(branchNode);
                	
                } 
                if (ncodperfil ==6) {
                		branchObject.setPageContent(false);
                		branchNode = new DefaultMutableTreeNode(branchObject);
                		branchObject.setWrapper(branchNode);
                		//finally add the new custom component branch
                		rootTreeNode.add(branchNode);
                	
                }
                
                //Agregado el 05/01/2012 - Alfredo Panitz
                // Sub nodo de Mantenimiento de Jefes de Equipo
                branchObject = new PageContentBean();
                branchObject.setMenuDisplayText("Jefes de Equipo");
                branchObject.setMenuContentInclusionFile("./content/MAN/jefes_equipo.jspx");
                branchObject.setTemplateName("jefEquipContentPanel");
                branchObject.setNavigationSelection(navigationBean);
                if ((ncodperfil == 2) || (ncodperfil == 6)){
                    leafNode = new DefaultMutableTreeNode(branchObject);
                    branchObject.setWrapper(leafNode);
                    branchObject.setLeaf(true);
                    // finally add the new custom component branch
                    branchNode.add(leafNode);
                }
                
                
                //Agregado el 05/01/2012 - Alfredo Panitz
                // Sub nodo de Mantenimiento de Secuenciales por Areas
                branchObject = new PageContentBean();
                branchObject.setMenuDisplayText("Secuenciales");
                branchObject.setMenuContentInclusionFile("./content/MAN/secuenciales.jspx");
                branchObject.setTemplateName("secAreaContentPanel");
                branchObject.setNavigationSelection(navigationBean);
                if ((ncodperfil == 2) || (ncodperfil == 6)){
                    leafNode = new DefaultMutableTreeNode(branchObject);
                    branchObject.setWrapper(leafNode);
                    branchObject.setLeaf(true);
                    // finally add the new custom component branch
                    branchNode.add(leafNode);
                }
                
                //Agregado el 25/07/2019 - Eli Diaz Horna
                // Sub nodo de Mantenimiento de Secuenciales por Areas
                /*
                branchObject = new PageContentBean();
                branchObject.setMenuDisplayText("Correlativos");
                branchObject.setMenuContentInclusionFile("./content/MAN/correlativos.jspx");
                branchObject.setTemplateName("secAreaContentPanel");
                branchObject.setNavigationSelection(navigationBean);
                if ((ncodperfil == 2) || (ncodperfil == 6)){
                    leafNode = new DefaultMutableTreeNode(branchObject);
                    branchObject.setWrapper(leafNode);
                    branchObject.setLeaf(true);
                    branchNode.add(leafNode);
                }
                */
                
                
                //Agregado el 29/11/2013 - Eli Diaz Horna
                // Sub nodo de Mantenimiento de Grupos por Areas
                branchObject = new PageContentBean();
                branchObject.setMenuDisplayText("Grupos");
                branchObject.setMenuContentInclusionFile("./content/MAN/grupos.jspx");
                branchObject.setTemplateName("secAreaContentPanel");
                branchObject.setNavigationSelection(navigationBean);
                if ((ncodperfil == 2) || (ncodperfil == 6)){
                    leafNode = new DefaultMutableTreeNode(branchObject);
                    branchObject.setWrapper(leafNode);
                    branchObject.setLeaf(true);
                    // finally add the new custom component branch
                    branchNode.add(leafNode);
                }

                ////////////////////////////////////////////////////////////

                // Nodo de Reporte Gerencial 
                branchObject = new PageContentBean();
                branchObject.setExpanded(false);
                branchObject.setMenuDisplayText("Reportes Gerenciales");
                branchObject.setTemplateName("configuracionPanel");
                branchObject.setNavigationSelection(navigationBean);
                if (ncodperfil ==1 || ncodperfil ==2) {
                		branchObject.setPageContent(false);
                		branchNode = new DefaultMutableTreeNode(branchObject);
                		branchObject.setWrapper(branchNode);
                		// finally add the new custom component branch
                		rootTreeNode.add(branchNode);
                	
                } 
                if (ncodperfil ==6) {
                		branchObject.setPageContent(false);
                		branchNode = new DefaultMutableTreeNode(branchObject);
                		branchObject.setWrapper(branchNode);
                		//finally add the new custom component branch
                		rootTreeNode.add(branchNode);
                	
                }
                
                // Agregado el 18/03/2015 - Eli Diaz Horna
                // Sub nodo de Seguimiento de Documentos con fecha vencimiento
                branchObject = new PageContentBean();
                branchObject.setMenuDisplayText("Documentos Con Plazo");
                branchObject.setMenuContentInclusionFile("./content/RP/reporte_gerencial.jspx");
                branchObject.setTemplateName("DocVencContentPanel");
                branchObject.setNavigationSelection(navigationBean);
                if ((ncodperfil == 2) || (ncodperfil == 6 || ncodperfil == 1)){
                    leafNode = new DefaultMutableTreeNode(branchObject);
                    branchObject.setWrapper(leafNode);
                    branchObject.setLeaf(true);
                    // finally add the new custom component branch
                    branchNode.add(leafNode);
                }
                
                // Agregado el 18/03/2015 - Eli Diaz Horna
                // Sub nodo de Seguimiento de Documentos con fecha vencimiento
                branchObject = new PageContentBean();
                branchObject.setMenuDisplayText("Doc. Derivados Gerencia");
                branchObject.setMenuContentInclusionFile("./content/RP/documentos_gerencia.jspx");
                branchObject.setTemplateName("DocGerenciaContentPanel");
                branchObject.setNavigationSelection(navigationBean);
                if ((ncodperfil == 2) || (ncodperfil == 6) || (ncodperfil == 1)){
                    leafNode = new DefaultMutableTreeNode(branchObject);
                    branchObject.setWrapper(leafNode);
                    branchObject.setLeaf(true);
                    // finally add the new custom component branch
                    branchNode.add(leafNode);
                }
                
                // Agregado el 24/09/2019 - Eli Diaz Horna
                // Sub nodo reporte de documentos con plazos de la GG a la GDI
                branchObject = new PageContentBean();
                branchObject.setMenuDisplayText("Doc. de GG con Plazo");
                branchObject.setMenuContentInclusionFile("./content/RP/reporte_gerencial_plazo.jspx");
                branchObject.setTemplateName("DocGerenciaContentPanel");
                branchObject.setNavigationSelection(navigationBean);
                if ((ncodperfil == 2) || (ncodperfil == 6) || (ncodperfil == 1)){
                    leafNode = new DefaultMutableTreeNode(branchObject);
                    branchObject.setWrapper(leafNode);
                    branchObject.setLeaf(true);
                    // finally add the new custom component branch
                    branchNode.add(leafNode);
                }
                
                ////////////////////////////////////////////////////////////////////////

                // Nodo de Consultas
                branchObject = new PageContentBean();
                branchObject.setExpanded(false);
                branchObject.setMenuDisplayText("Consultas");
                branchObject.setMenuContentTitle("Consultas");
                branchObject.setTemplateName("consultaPanel");
                branchObject.setNavigationSelection(navigationBean);  
                if (ncodperfil !=5) {                	
                	branchObject.setPageContent(false);
                    branchNode = new DefaultMutableTreeNode(branchObject);
                    branchObject.setWrapper(branchNode);
                    // finally add the new custom component branch
                    rootTreeNode.add(branchNode);
                	
                }
                
             // Sub nodo de consultas informes estadisticos
                branchObject = new PageContentBean();
                branchObject.setMenuDisplayText("Registros Mesa Partes Virtual");
                //branchObject.setMenuContentTitle("Consulta");
                branchObject.setMenuContentInclusionFile("./content/RP/reporte_doc_mesa_virtual.jspx");
                branchObject.setTemplateName("informeContentPanel");
                branchObject.setNavigationSelection(navigationBean);
                if (ncodperfil ==1 || ncodperfil == 4 || ncodperfil == 13) { 
	                leafNode = new DefaultMutableTreeNode(branchObject);
	                branchObject.setWrapper(leafNode);
	                branchObject.setLeaf(true);
	                // finally add the new custom component branch
	                branchNode.add(leafNode); 
                }
                
             // Sub nodo de consultas informes estadisticos
                branchObject = new PageContentBean();
                branchObject.setMenuDisplayText("Tiempo Atención Mesa Virtual");
                //branchObject.setMenuContentTitle("Consulta");
                branchObject.setMenuContentInclusionFile("./content/RP/reporte_atencion_doc_ingresados_mesa.jspx");
                branchObject.setTemplateName("informeContentPanel");
                branchObject.setNavigationSelection(navigationBean);
                if (ncodperfil ==1 || ncodperfil == 4 || ncodperfil == 13) { 
	                leafNode = new DefaultMutableTreeNode(branchObject);
	                branchObject.setWrapper(leafNode);
	                branchObject.setLeaf(true);
	                // finally add the new custom component branch
	                branchNode.add(leafNode); 
                }
                
               
              
                
                // Sub nodo de consultas informes estadisticos
                branchObject = new PageContentBean();
                branchObject.setMenuDisplayText("Consulta");
                //branchObject.setMenuContentTitle("Consulta");
                branchObject.setMenuContentInclusionFile("./content/CO/consultas.jspx");
                branchObject.setTemplateName("informeContentPanel");
                branchObject.setNavigationSelection(navigationBean);
                if (ncodperfil !=5) { 
	                leafNode = new DefaultMutableTreeNode(branchObject);
	                branchObject.setWrapper(leafNode);
	                branchObject.setLeaf(true);
	                // finally add the new custom component branch
	                branchNode.add(leafNode); 
                }
                
             // Sub nodo de consultas de documentos de entrada
                branchObject = new PageContentBean();
                branchObject.setMenuDisplayText("Busqueda Doc.Entrada");
                //branchObject.setMenuContentTitle("Consulta");
                branchObject.setMenuContentInclusionFile("./content/CO/ConsultaDocumentoEntrada.jspx");
                branchObject.setTemplateName("consultaContentPanel");                
	            branchObject.setNavigationSelection(navigationBean);
	                if (ncodperfil !=5) { 
	                leafNode = new DefaultMutableTreeNode(branchObject);
	                branchObject.setWrapper(leafNode);
	                branchObject.setLeaf(true);
	                // finally add the new custom component branch
	                branchNode.add(leafNode); 
                }
	                
             // Sub nodo de consultas de documentos de entrada
	            branchObject = new PageContentBean();
	            branchObject.setMenuDisplayText("Busqueda Por Seguimiento");
	            //branchObject.setMenuContentTitle("Consulta");
	            branchObject.setMenuContentInclusionFile("./content/CO/ConsultaSeguiminetoDocumentoEntrada.jspx");
	            branchObject.setTemplateName("seguimientoContentPanel");                
		        branchObject.setNavigationSelection(navigationBean);
		        	if ((ncodperfil !=5) && (ncodperfil !=6) && (ncodperfil !=4) && (ncodperfil !=13)) { 
		                leafNode = new DefaultMutableTreeNode(branchObject);
		                branchObject.setWrapper(leafNode);
		                branchObject.setLeaf(true);
		                // finally add the new custom component branch
		                branchNode.add(leafNode); 
	                }
		        	
		        	
		        	 
		                
             // Sub nodo de consultas de documentos de entrada
	            /*
                branchObject = new PageContentBean();
                branchObject.setMenuDisplayText("Seguimiento Doc.Entrada");                
                branchObject.setMenuContentInclusionFile("./content/CO/SeguimentoDocumentoEntrada.jspx");
                branchObject.setTemplateName("seguimientoContentPanel");                 
                branchObject.setNavigationSelection(navigationBean);
                if (ncodperfil == 6){
                	if (ncodperfil !=5) { 
	                leafNode = new DefaultMutableTreeNode(branchObject);
	                branchObject.setWrapper(leafNode);
	                branchObject.setLeaf(true);
	                branchNode.add(leafNode); 
                	}
                }
                */
             // Sub nodo de consultas de documentos de entrada
                branchObject = new PageContentBean();
                branchObject.setMenuDisplayText("Busqueda de Reg.Entrada");
                //branchObject.setMenuContentTitle("Consulta");
                branchObject.setMenuContentInclusionFile("./content/CO/BusquedaRegistroEntrada.jspx");
                branchObject.setTemplateName("busquedaContentPanel");
                branchObject.setNavigationSelection(navigationBean); 
                if ((ncodperfil !=5) ||	(ncodperfil !=6)) {
	                leafNode = new DefaultMutableTreeNode(branchObject);
	                branchObject.setWrapper(leafNode);
	                branchObject.setLeaf(true);
	                // finally add the new custom component branch
	                branchNode.add(leafNode); 
                }
                
                // Sub nodo de consultas de documentos de entrada
                branchObject = new PageContentBean();
                branchObject.setMenuDisplayText("Busqueda de Doc.Externo");
                //branchObject.setMenuContentTitle("Consulta");
                branchObject.setMenuContentInclusionFile("./content/CO/BusquedaDocumentoExterno.jspx");
                branchObject.setTemplateName("busquedaExternoPanel");
                branchObject.setNavigationSelection(navigationBean); 
                if ((ncodperfil !=5) ||	(ncodperfil !=6)) {
	                leafNode = new DefaultMutableTreeNode(branchObject);
	                branchObject.setWrapper(leafNode);
	                branchObject.setLeaf(true);
	                // finally add the new custom component branch
	                branchNode.add(leafNode); 
                }

                // Sub nodo de consultas Trabajador menu ->
                branchObject = new PageContentBean();
                branchObject.setMenuDisplayText("Trabajador");
                //branchObject.setMenuContentTitle("Trabajador");
                branchObject.setMenuContentInclusionFile("./content/CO/trabajador.jspx");
                branchObject.setTemplateName("trabajadorContentPanel");
                branchObject.setNavigationSelection(navigationBean);
                if (ncodperfil !=5) {
	                leafNode = new DefaultMutableTreeNode(branchObject);
	                branchObject.setWrapper(leafNode);
	                branchObject.setLeaf(true);
	                // finally add the new custom component branch
	                branchNode.add(leafNode); 
                }
                
                // Sub nodo de consultas Areas menu ->
                branchObject = new PageContentBean();
                branchObject.setMenuDisplayText("Areas");
                //branchObject.setMenuContentTitle("�reas");
                branchObject.setMenuContentInclusionFile("./content/CO/areas.jspx");
                branchObject.setTemplateName("areasContentPanel");
                branchObject.setNavigationSelection(navigationBean);
                if (ncodperfil !=5) {
	                leafNode = new DefaultMutableTreeNode(branchObject);
	                branchObject.setWrapper(leafNode);
	                branchObject.setLeaf(true);
	                // finally add the new custom component branch
	                branchNode.add(leafNode);
                }
                                
                // Sub nodo de consultas Centro menu ->
                branchObject = new PageContentBean();
                branchObject.setMenuDisplayText("Centro Servicios");
                //branchObject.setMenuContentTitle("Centro Servicios");
                branchObject.setMenuContentInclusionFile("./content/CO/centro.jspx");
                branchObject.setTemplateName("centroContentPanel");
                branchObject.setNavigationSelection(navigationBean);
                if (ncodperfil !=5) {
	                leafNode = new DefaultMutableTreeNode(branchObject);
	                branchObject.setWrapper(leafNode);
	                branchObject.setLeaf(true);
	                // finally add the new custom component branch
	                branchNode.add(leafNode);
                }
                ///////////////////////////////////////////////////////
                // Eli comenta este codigo para solo haya una estaditica 16/11/2011
             // Nodo de Estadisticas
                //branchObject = new PageContentBean();
                //branchObject.setExpanded(false);
                //branchObject.setMenuDisplayText("Estadisticas");
                //branchObject.setMenuContentTitle("Estadisticas");
                //branchObject.setTemplateName("consultaPanel");
                //branchObject.setNavigationSelection(navigationBean);
                //branchObject.setPageContent(false);
                //branchNode = new DefaultMutableTreeNode(branchObject);
                //branchObject.setWrapper(branchNode);
                // finally add the new custom component branch
                //rootTreeNode.add(branchNode);
                
             // Sub nodo de consultas Centro menu ->
                //branchObject = new PageContentBean();
                //branchObject.setMenuDisplayText("Resumen por Estado");
                //branchObject.setMenuContentTitle("Centro Servicios");
                //branchObject.setMenuContentInclusionFile("./content/RP/resumen.jspx");
                //branchObject.setTemplateName("centroContentPanel");
                //branchObject.setNavigationSelection(navigationBean);
                //leafNode = new DefaultMutableTreeNode(branchObject);
                //branchObject.setWrapper(leafNode);
                //branchObject.setLeaf(true);
                // finally add the new custom component branch
                //branchNode.add(leafNode);
                
                ///////////////////////////////////////////////////////
                //Agregado el 10/11/2011 - Alfredo Panitz
                // Nodo de Estadisticas
                   branchObject = new PageContentBean();
                   branchObject.setExpanded(false);
                   branchObject.setMenuDisplayText("Estadisticas");
                   branchObject.setMenuContentTitle("Estadisticas");
                   branchObject.setTemplateName("estadisticasPanel");
                   branchObject.setNavigationSelection(navigationBean);
                   if (ncodperfil!=4){
	                   if (ncodperfil !=13){
	                	   if (ncodperfil !=5){
	                		   if (ncodperfil !=7){
	                			   if (ncodperfil !=10){
					                   branchObject.setPageContent(false);
					                   branchNode = new DefaultMutableTreeNode(branchObject);
					                   branchObject.setWrapper(branchNode);
					                   // finally add the new custom component branch
					                   rootTreeNode.add(branchNode);
	                			   }
	                		   }
	                	   }
	                   }
                   }
                // Sub nodo de estadisticas 
                   branchObject = new PageContentBean();
                   branchObject.setMenuDisplayText("Atención Doc. Entrantes");
                   branchObject.setMenuContentInclusionFile("./content/EST/est_doc_entrantes.jspx");
                   branchObject.setTemplateName("docEntrContentPanel");
                   branchObject.setNavigationSelection(navigationBean);
                   if (ncodperfil !=13){
	                   if (ncodperfil!=4){
	                	   if (ncodperfil !=5){
	                		   if (ncodperfil !=7){
	                			   if (ncodperfil !=10){
					                   leafNode = new DefaultMutableTreeNode(branchObject);
					                   branchObject.setWrapper(leafNode);
					                   branchObject.setLeaf(true);
					                   // finally add the new custom component branch
					                   branchNode.add(leafNode);
	                			   }
	                		   }
	                	   }
	                   }
                   }
                // Sub nodo de estadisticas 
                   branchObject = new PageContentBean();
                   branchObject.setMenuDisplayText("Origen Doc. Entrantes");
                   branchObject.setMenuContentInclusionFile("./content/EST/est_origen_doc_entr.jspx");
                   branchObject.setTemplateName("docEntrContentPanel");
                   branchObject.setNavigationSelection(navigationBean);
                   if (ncodperfil !=13){
	                   if (ncodperfil!=4 || ncodperfil !=13){
	                	   if (ncodperfil !=5){
	                		   if (ncodperfil !=7){
	                			   if (ncodperfil !=10){
				                   leafNode = new DefaultMutableTreeNode(branchObject);
				                   branchObject.setWrapper(leafNode);
				                   branchObject.setLeaf(true);
				                   // finally add the new custom component branch
				                   branchNode.add(leafNode);
	                			   }
	                		   }
	                	   }
	                   }
                   }
                // Sub nodo de estadisticas 
                   branchObject = new PageContentBean();
                   branchObject.setMenuDisplayText("Estadisticos Gerenciales");
                   branchObject.setMenuContentInclusionFile("./content/EST/estadisticas_gerenciales.jspx");
                   branchObject.setTemplateName("EstGerencialesContentPanel");
                   branchObject.setNavigationSelection(navigationBean);
                   if (ncodperfil!=4){
	                   if (ncodperfil !=13){
	                	   if (ncodperfil !=5){
	                		   if (ncodperfil !=7){
	                			   if (ncodperfil !=10){
				                   leafNode = new DefaultMutableTreeNode(branchObject);
				                   branchObject.setWrapper(leafNode);
				                   branchObject.setLeaf(true);
				                   // finally add the new custom component branch
				                   branchNode.add(leafNode);
	                			   }
	                		   }
	                	   }
	                   }
                   }
                   // Sub nodo de estadisticas estados 
                   branchObject = new PageContentBean();
                   branchObject.setMenuDisplayText("Estadisticos Por Estados");
                   branchObject.setMenuContentInclusionFile("./content/EST/estadisticas_estados.jspx");
                   branchObject.setTemplateName("EstGerencialesContentPanel");
                   branchObject.setNavigationSelection(navigationBean);
                   if (ncodperfil !=13){
	                   if (ncodperfil!=4){
	                	   if (ncodperfil !=5){
	                		   if (ncodperfil !=7){
	                			   if (ncodperfil !=10){
				                   leafNode = new DefaultMutableTreeNode(branchObject);
				                   branchObject.setWrapper(leafNode);
				                   branchObject.setLeaf(true);
				                   // finally add the new custom component branch
				                   branchNode.add(leafNode);
	                			   }
	                		   }
	                	   }
	                   }
                   }
                ///////////////////////////////////////////////////////
             // Nodo de Administracion
                branchObject = new PageContentBean();
                branchObject.setExpanded(false);
                branchObject.setMenuDisplayText("Administración");
                //branchObject.setMenuContentTitle("Administraci�n");
                branchObject.setTemplateName("administracionPanel");
                branchObject.setNavigationSelection(navigationBean);
                branchObject.setPageContent(false);
                if ((ncodperfil==1) || (ncodperfil == 5)) {
                	branchNode = new DefaultMutableTreeNode(branchObject);
                	branchObject.setWrapper(branchNode);
                	//finally add the new custom component branch
                	rootTreeNode.add(branchNode);
                }
                // Sub Nodo de Usuarios
                branchObject = new PageContentBean();
                branchObject.setMenuDisplayText("Usuarios");
                //branchObject.setMenuContentTitle("Usuarios");
                branchObject.setMenuContentInclusionFile("./content/ADM/users.jspx");
                branchObject.setTemplateName("usuariosContentPanel");
                branchObject.setNavigationSelection(navigationBean);
                if ((ncodperfil==1) || (ncodperfil == 5)){
                	leafNode = new DefaultMutableTreeNode(branchObject);
                	branchObject.setWrapper(leafNode);
                	branchObject.setLeaf(true);
                	// finally add the new custom component branch
                	branchNode.add(leafNode);
                }                         
                // Sub Nodo de Perfiles
                branchObject = new PageContentBean();
                branchObject.setMenuDisplayText("Perfiles");
                //branchObject.setMenuContentTitle("Perfiles");              
                branchObject.setMenuContentInclusionFile("./content/ADM/perfiles.jspx");
                branchObject.setTemplateName("perfilesContentPanel");
                branchObject.setNavigationSelection(navigationBean);
                if (ncodperfil==1){
                	leafNode = new DefaultMutableTreeNode(branchObject);
                	branchObject.setWrapper(leafNode);
                	branchObject.setLeaf(true);
                	//finally add the new custom component branch
                	branchNode.add(leafNode); 
                }
                
                
                // Nodo de Informacion
                branchObject = new PageContentBean();
                branchObject.setExpanded(false);
                branchObject.setMenuDisplayText("Información");
                //branchObject.setMenuContentTitle("Informaci�n");
                branchObject.setTemplateName("informacionPanel");
                branchObject.setNavigationSelection(navigationBean);
                branchObject.setPageContent(false);
                branchNode = new DefaultMutableTreeNode(branchObject);
                branchObject.setWrapper(branchNode);
                // finally add the new custom component branch
                rootTreeNode.add(branchNode);
                
                // Sub nodo de Informacion ->
                branchObject = new PageContentBean();
                branchObject.setMenuDisplayText("Manual de Usuario");                
                branchObject.setMenuContentInclusionFile("./content/CO/manual.jspx");
                branchObject.setTemplateName("manualContentPanel");
                branchObject.setNavigationSelection(navigationBean);
                leafNode = new DefaultMutableTreeNode(branchObject);
                branchObject.setWrapper(leafNode);
                branchObject.setLeaf(true);
                branchNode.add(leafNode);
             
            }
            
       } 
   }
       
    


	public DefaultMutableTreeNode getRootTreeNode() {
		return rootTreeNode;
	}

	public void setRootTreeNode(DefaultMutableTreeNode rootTreeNode) {
		this.rootTreeNode = rootTreeNode;
	}

	/**
     * Gets the default tree model.  This model is needed for the value
     * attribute of the tree component.
     *
     * @return default tree model used by the navigation tree
     */
    public DefaultTreeModel getModel() {
        return model;
    }

    /**
     * Sets the default tree model.
     *
     * @param model new default tree model
     */
    public void setModel(DefaultTreeModel model) {
        this.model = model;
    }

    /**
     * Gets the tree component binding.
     *
     * @return tree component binding
     */
    public Tree getTreeComponent() {
        return treeComponent;
    }

    /**
     * Sets the tree component binding.
     *
     * @param treeComponent tree component to bind to
     */
    public void setTreeComponent(Tree treeComponent) {
        this.treeComponent = treeComponent;
        if (!isInitiated) {
            init();
        }
    }
}
