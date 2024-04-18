package com.sedapal.tramite.servicios;

import java.util.List;

import com.sedapal.tramite.beans.RemitenteBPM;
import com.sedapal.tramite.beans.TiposDocumentosBean;
import com.sedapal.tramite.nova.util.CasillaElectronicaResponse;
import com.sedapal.tramite.nova.util.DocumentoDTO;
import com.sedapal.tramite.nova.util.UsuarioExternoResponse;

public interface IServiciosAPI {
	public RemitenteBPM actualizarPorTipoDocumento(String tipoDocumento, String numeroDocumento, List<TiposDocumentosBean> td);
	public boolean consultaCasilla(DocumentoDTO documento, List<TiposDocumentosBean> td);
	public String enviarNotificacionBPM(CasillaElectronicaResponse ce);
}
