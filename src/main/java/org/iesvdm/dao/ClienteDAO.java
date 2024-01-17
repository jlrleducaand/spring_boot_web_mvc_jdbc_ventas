package org.iesvdm.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ClienteDAO<Cliente> extends RepositorioDAOBase<Cliente> {


	public static org.iesvdm.domain.Cliente newCliente(ResultSet rs) throws SQLException {
		return new org.iesvdm.domain.Cliente(rs.getInt("id")
				, rs.getString("nombre")
				, rs.getString("apellido1")
				, rs.getString("apellido2")
				, rs.getString("ciudad")
				, rs.getInt("categoría")
		);
	}
}
