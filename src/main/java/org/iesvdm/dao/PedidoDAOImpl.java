package org.iesvdm.dao;

import org.iesvdm.domain.Cliente;
import org.iesvdm.domain.Comercial;
import org.iesvdm.domain.Pedido;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class PedidoDAOImpl implements PedidoDAO<Pedido>{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Pedido> getAll() {
        List<Pedido> listPed = this.jdbcTemplate.query("""
                SELECT * FROM  pedido P left join cliente C on  P.id_cliente = C.id
                                        left join comercial CO on P.id_comercial = CO.id
                """, (rs, rowNum) -> PedidoDAO.newPedido(rs)
        );

        log.info("Devueltos {} registros.", listPed.size());

        return listPed;

    }

    @Override
    public void create(Pedido pedido) {
        //Desde java15+ se tiene la triple quote """ para bloques de texto como cadenas. y no tendra en cuenta los saltos de linea
        String sqlInsert = """
							INSERT INTO pedido (total, fecha, id_cliente, id_comercial) 
							VALUES  (     ?,         ?,         ?,       ?)
						   """;

        KeyHolder keyHolder = new GeneratedKeyHolder();
        //Con recuperación de id generado
        int rows = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sqlInsert, new String[] { "id" });
            int idx = 1;
            ps.setDouble(idx++, pedido.getTotal());
            ps.setDate(idx++, pedido.getFecha());
            ps.setLong(idx++, pedido.getCliente().getId());
            ps.setLong(idx++, pedido.getComercial().getId());
            return ps;
        },keyHolder);

        pedido.setId(keyHolder.getKey().intValue());


        //Sin recuperación de id generado
//		int rows = jdbcTemplate.update(sqlInsert,
//							cliente.getNombre(),
//							cliente.getApellido1(),
//							cliente.getApellido2(),
//							cliente.getCiudad(),
//							cliente.getCategoria()
//					);

        log.info("Insertados {} registros.", rows);
    }

    @Override
    public Optional<Pedido> find(int id) {
        Pedido pedido =  jdbcTemplate
                .queryForObject("""
                    select * from pedido P left join cliente C on  P.id_cliente = C.id
                                        left join comercial CO on P.id_comercial = CO.id
                                        WHERE P.id = ?
                """, (rs, rowNum) -> PedidoDAO.newPedido(rs), id);

        if (pedido != null) return Optional.of(pedido);
        log.debug("No encontrado pedido con id {} devolviendo Optional.empty()", id);
        return Optional.empty();
    }

    @Override
    public void update(Pedido pedido) {
        int rows = jdbcTemplate.update("""
										UPDATE pedido SET
														total = ?,
														fecha = ?,
														id_cliente = ?,
														id_comercial = ?
												WHERE id = ?
										"""
                , pedido.getTotal()
                , pedido.getFecha()
                , pedido.getCliente().getId()
                , pedido.getComercial().getId()
                , pedido.getId());


        log.info("Update de Pedido con {} registros actualizados.", rows);

    }

    @Override
    public void delete(long id) {
        int rows = jdbcTemplate.update("DELETE FROM pedido WHERE id = ?", id);

        log.info("Delete de Pedido con {} registros eliminados.", rows);

    }
    
    
    
    @Override
    public Optional<Cliente> findClienteBy(int pedidoId) {
        try {
            Cliente cliente = this.jdbcTemplate.queryForObject(
                    "SELECT C.* FROM Cliente C JOIN Pedido P ON C.id = P.id WHERE P.id = ?",
                    new Object[]{pedidoId},
                    (rs, rowNum) -> ClienteDAO.newCliente(rs)
            );
            return Optional.ofNullable(cliente);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Comercial> findComercialBy(int pedidoId) {
        try {
            Comercial comercial = this.jdbcTemplate.queryForObject(
                    "SELECT Co.* FROM Comercial Co JOIN Pedido P ON Co.id = P.id WHERE P.id = ?",
                    new Object[]{pedidoId},
                    (rs, rowNum) -> ComercialDAO.newComercial(rs)
            );
            return Optional.ofNullable(comercial);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }

    }
}
