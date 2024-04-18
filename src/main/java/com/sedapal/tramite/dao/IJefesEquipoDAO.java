package com.sedapal.tramite.dao;

import com.sedapal.tramite.beans.admin.JefeEquipoBean;
import java.util.Map;

public interface IJefesEquipoDAO {

    public JefeEquipoBean getJefeEquipoArea(String codArea);
    public Map updateJefeEquipoArea(String codArea, String codFicha, String apePaterno, String apeMaterno, String nombres, String usuResponsable);
}