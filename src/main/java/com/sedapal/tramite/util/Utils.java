package com.sedapal.tramite.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.sedapal.tramite.beans.AccionBean;
import com.sedapal.tramite.beans.AnioBean;
import com.sedapal.tramite.beans.AreaBean;
import com.sedapal.tramite.beans.EntranteBean;
import com.sedapal.tramite.beans.EstadosBean;
import com.sedapal.tramite.beans.OrigenBean;
import com.sedapal.tramite.beans.PerfilBean;
import com.sedapal.tramite.beans.RemitenteBean;
import com.sedapal.tramite.beans.RepresentanteBean;
import com.sedapal.tramite.beans.TipoConsulta;
import com.sedapal.tramite.beans.TiposBean;
import com.sedapal.tramite.beans.TiposDocumentosBean;
import com.sedapal.tramite.beans.TiposPersonasBean;
import com.sedapal.tramite.beans.TrabajadorBean;


public class Utils {
	
	public static String obtenerTerminal(HttpServletRequest request) {
		HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return req.getRemoteAddr();
		
		}

    public static LinkedHashMap<String, String> getAreasLink(List<AreaBean> areas) {
        LinkedHashMap<String, String> areasCombo = new LinkedHashMap<String, String>();
        for (AreaBean a : areas) {
            areasCombo.put(a.getNombre(), String.valueOf(a.getCodigo()));
        }
        return areasCombo;
    }

    public static LinkedHashMap<String, String> getRemitenteLink(List<RemitenteBean> remitentes) {
        LinkedHashMap<String, String> remitentesCombo = new LinkedHashMap<String, String>();
        for (RemitenteBean a : remitentes) {
            remitentesCombo.put(a.getDescripcion(), String.valueOf(a.getCodigo()));
        }
        return remitentesCombo;
    }

    public static LinkedHashMap<String, String> getTiposConsulta(List<TipoConsulta> tipos) {
        LinkedHashMap<String, String> tiposCombo = new LinkedHashMap<String, String>();
        for (TipoConsulta a : tipos) {
            tiposCombo.put(a.getDescripcion(), a.getTipo());
        }
        return tiposCombo;
    }
    
    public static List getTipo(List<TiposBean> tipo)
	{
		List tipoCombo = new ArrayList();
		for(TiposBean a:tipo)
		{
			tipoCombo.add(new SelectItem(a.getVcodtipo(),a.getVdescrip()));
		}
		return tipoCombo;
	}

    /**
     * Dado un value retorna su key
     *
     * @param linkedHashMap
     * @param value
     * @return
     */
    public static String getKey(LinkedHashMap linkedHashMap, String value) {

        String result = null;
        Set set = linkedHashMap.entrySet();
        Iterator i = set.iterator();
        int valor, valorL = 0;
        while (i.hasNext()) {
            Map.Entry val = (Map.Entry) (i.next());
            //System.out.println("value Hash !! : " + val.getValue());
            valor = Integer.parseInt(value);
            valorL = (Integer) val.getValue();
            if (valor == valorL) {
                result = (String) val.getKey();
                break;
            }
        }
        return result;
    }

    public static String getKey2(LinkedHashMap linkedHashMap, String value) {

        String result = null;
        Set set = linkedHashMap.entrySet();
        Iterator i = set.iterator();
        String valorL = null;
        while (i.hasNext()) {
            Map.Entry val = (Map.Entry) (i.next());
            //System.out.println("value Hash !! : " + val.getValue());
            valorL = (String) val.getValue();
            if (value.equals(valorL)) {
                result = (String) val.getKey();
                break;
            }
        }
        return result;
    }

    public static void pasaDerecha(LinkedHashMap<String, String> listaIzq, LinkedHashMap<String, String> listaDer, String[] itemsaPasar) {
        for (int i = 0; i < itemsaPasar.length; i++) {
            //System.out.println("Seleccionado:"+ itemsaPasar[i]);
            if (!listaDer.containsValue(itemsaPasar[i])) {
                //System.out.println("No est� en temporal, se pasa a adicionarlo");
                listaDer.put(Utils.getKey2(listaIzq, itemsaPasar[i]), itemsaPasar[i]);
                //System.out.println("Remove:" + Utils.getKey(listaIzq, itemsaPasar[i]));
                listaIzq.remove(Utils.getKey2(listaIzq, itemsaPasar[i]));
                //System.out.println("Personales:" + listaIzq);
                //System.out.println("Tempo:" + listaDer);

            }

        }

    }

    public static void pasaIzquierda(LinkedHashMap<String, String> listaIzq, LinkedHashMap<String, String> listaDer, String[] itemsaPasar) {
        for (int i = 0; i < itemsaPasar.length; i++) {
            //System.out.println("Seleccionado:"+ itemsaPasar);
            if (!listaIzq.containsValue(itemsaPasar[i])) {
                //System.out.println("No est� en original, se pasa a adicionarlo");
                listaIzq.put(Utils.getKey2(listaDer, itemsaPasar[i]), itemsaPasar[i]);
                //System.out.println("Personales:" + listaIzq);

            }
            //igual se remueve de temporal
            listaDer.remove(Utils.getKey2(listaDer, itemsaPasar[i]));
        }

    }

    public static List getServidores() {
        ResourceBundle bundle = ResourceBundle
                .getBundle("com.sedapal.tramite.resources.servers");

        List servers = new ArrayList();
        for (int i = 1; i <= Integer.parseInt(bundle
                .getString("six.instancias")); i++) {
            servers.add(new SelectItem(bundle.getString("six.idServer" + i),
                    bundle.getString("six.idServer" + i + ".name")));
        }
        return servers;
    }

    public static List getPerfiles(List<PerfilBean> perfiles) {
        List perfilesCombo = new ArrayList();
        for (PerfilBean p : perfiles) {
            perfilesCombo.add(new SelectItem(p.getCodigo(), p.getNombre()));
        }
        return perfilesCombo;
    }

    //de List a Lis para combo
    public static List getAreas(List<AreaBean> areas) {
        List areasCombo = new ArrayList();
        for (AreaBean a : areas) {
            areasCombo.add(new SelectItem(a.getCodigo(), a.getNombre()));
        }
        return areasCombo;
    }

    public static List getRemitentes(List<RemitenteBean> remitentes) {
        List remitentesCombo = new ArrayList();
        for (RemitenteBean r : remitentes) {
            remitentesCombo.add(new SelectItem(r.getCodigo(), r.getDescripcion()));
        }
        return remitentesCombo;
    }

    public static List getTipos(List<TiposBean> tipos) {
        List tiposCombo = new ArrayList();
        for (TiposBean t : tipos) {
            tiposCombo.add(new SelectItem(t.getTipo(), t.getDescripcion()));
        }
        return tiposCombo;
    }
    
    
    public static List getMotivos(List<TiposBean> tipos) {
        List tiposCombo = new ArrayList();
        for (TiposBean t : tipos) {
            tiposCombo.add(new SelectItem(t.getCodigo(), t.getDescripcion()));
        }
        return tiposCombo;
    }
    
    

    public static List getAccion(List<AccionBean> accion) {
        List accionCombo = new ArrayList();
        for (AccionBean a : accion) {
            accionCombo.add(new SelectItem(a.getCodigo(), a.getDescripcion()));
        }
        return accionCombo;
    }

    public static List getTrabajador(List<TrabajadorBean> trabajador) {
        List trabajadorCombo = new ArrayList();
        for (TrabajadorBean t : trabajador) {
            trabajadorCombo.add(new SelectItem(t.getFicha(), t.getNombre_completo()));
        }
        return trabajadorCombo;
    }

    public static List getTrabajador_derivador(List<TrabajadorBean> trabajador_derivador) {
        List trabajadorCombo = new ArrayList();
        for (TrabajadorBean t : trabajador_derivador) {
            trabajadorCombo.add(new SelectItem(t.getFicha(), t.getNombre_completo()));
        }
        return trabajadorCombo;
    }

    public static List getCriterios(List<TiposBean> criterio) {
        List criterioCombo = new ArrayList();
        for (TiposBean t : criterio) {
            criterioCombo.add(new SelectItem(t.getCodigo(), t.getDescripcion()));
        }
        return criterioCombo;
    }
    
    public static List getAnio(List<AnioBean> anio) {
        List anioCombo = new ArrayList();
        for (AnioBean t : anio) {
        	anioCombo.add(new SelectItem(t.getCodigo(), t.getDescripcion()));
        }
        return anioCombo;
    }

    public static List getEntrante(List<EntranteBean> entrante) {
        List entranteCombo = new ArrayList();
        for (EntranteBean e : entrante) {
            entranteCombo.add(new SelectItem(e.getVremitente()));
        }

        return entranteCombo;
    }

    public static List getRepresentante(List<RepresentanteBean> representante) {
        List representanteCombo = new ArrayList();
        for (RepresentanteBean r : representante) {
            representanteCombo.add(new SelectItem(r.getCodrepresentante(), r.getVnombre()));
        }
        return representanteCombo;
    }

    public static List getOrigen(List<OrigenBean> origen) {
        List origenCombo = new ArrayList();
        for (OrigenBean a : origen) {
            origenCombo.add(new SelectItem(a.getCodigo(), a.getDescripcion()));
        }
        return origenCombo;
    }

    public static List getEstado(List<EstadosBean> estado) {
        List estadoCombo = new ArrayList();
        for (EstadosBean a : estado) {
            estadoCombo.add(new SelectItem(a.getCodigo(), a.getDescripcion()));
        }
        return estadoCombo;
    }

    public static List getTipos_Personas(List<TiposPersonasBean> tipos_persona) {
        List tipospersonaCombo = new ArrayList();
        for (TiposPersonasBean t : tipos_persona) {
            tipospersonaCombo.add(new SelectItem(t.getCodigo(), t.getDescripcion()));
        }
        return tipospersonaCombo;
    }

    public static List getTipos_Documentos(List<TiposDocumentosBean> tipos_documentos) {
        List tiposdocumentosCombo = new ArrayList();
        for (TiposDocumentosBean t : tipos_documentos) {
            tiposdocumentosCombo.add(new SelectItem(t.getCodigo(), t.getDescripcion()));
        }
        return tiposdocumentosCombo;
    }
    
    public static String unescapeHtml(String value) {
    	return value.replaceAll("", value);
    }
}
