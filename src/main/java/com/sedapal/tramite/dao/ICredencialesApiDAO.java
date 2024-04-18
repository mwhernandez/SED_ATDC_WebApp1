package com.sedapal.tramite.dao;

import com.sedapal.tramite.beans.CredencialesAPI;

public interface ICredencialesApiDAO {

	public CredencialesAPI obtener();
	public String getRuta(Integer tipo);
}
