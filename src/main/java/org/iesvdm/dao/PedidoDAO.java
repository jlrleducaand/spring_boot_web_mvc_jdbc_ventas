package org.iesvdm.dao;


import org.iesvdm.domain.Comercial;
import org.iesvdm.domain.Cliente;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public interface PedidoDAO<Pedido> extends RepositorioDAOBase<Pedido>{

public Optional<Cliente> findClienteBy(int pedidoId);

public Optional<Comercial> findComercialBy(int comercialId);

    public static org.iesvdm.domain.Pedido newPedido(ResultSet rs) throws SQLException {
        return new org.iesvdm.domain.Pedido(rs.getInt("id"),
                rs.getDouble("total"),
                rs.getDate("fecha"),
                new Cliente(rs.getInt("C.id"),
                        rs.getString("C.nombre"),
                        rs.getString("C.apellido1"),
                        rs.getString("C.apellido2"),
                        rs.getString("C.ciudad"),
                        rs.getInt("C.categoría")
                ),
                new Comercial(rs.getInt("CO.id"),
                        rs.getString("CO.nombre"),
                        rs.getString("CO.apellido1"),
                        rs.getString("CO.apellido2"),
                        rs.getFloat("CO.comisión")
                )
        );
    }
}
