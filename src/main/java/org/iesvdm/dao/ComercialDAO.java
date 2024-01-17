package org.iesvdm.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.iesvdm.domain.Comercial;

public interface ComercialDAO<Comercial> extends RepositorioDAOBase<Comercial> {

	public static org.iesvdm.domain.Comercial newComercial(ResultSet rs) throws SQLException {
		return new org.iesvdm.domain.Comercial(rs.getInt("id")
				, rs.getString("nombre")
				, rs.getString("apellido1")
				, rs.getString("apellido2")
				, rs.getFloat("comision")
		);
	}
}