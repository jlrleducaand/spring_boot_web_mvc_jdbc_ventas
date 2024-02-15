package org.iesvdm.dao;

import lombok.AllArgsConstructor;
import org.iesvdm.dto.PedidoDTO;
import org.iesvdm.modelo.Pedido;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import lombok.extern.slf4j.Slf4j;
import org.iesvdm.mapstruct.PedidoMapper;

@Slf4j
@Repository
public class PedidoDAOImpl implements PedidoDAO{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PedidoMapper pedidoMapper;

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
            ps.setLong(idx++, pedido.getId_cliente());
            ps.setLong(idx++, pedido.getId_comercial());
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
    public List<Pedido> getAll() {
        List<Pedido> listPed = jdbcTemplate.query(
                "SELECT * FROM pedido",
                (rs, rowNum) -> new Pedido(rs.getLong("id"),
                        rs.getDouble("total"),
                        rs.getDate("fecha"),
                        rs.getInt("id_cliente"),
                        rs.getInt("id_comercial")
                )
        );

        log.info("Devueltos {} registros.", listPed.size());

        return listPed;

    }

    @Override
    public List<PedidoDTO> getAllByComercial(long idComercial) {
        List<Pedido> listPed = jdbcTemplate.query(
                "SELECT * FROM pedido WHERE id_comercial = ? "
                    ,(rs, rowNum) -> new Pedido(rs.getLong("id"),
                        rs.getDouble("total"),
                        rs.getDate("fecha"),
                        rs.getInt("id_cliente"),
                        rs.getInt("id_comercial"))
                , idComercial
        );
        // Convertir la lista de entidades Pedido a una lista de PedidoDTO usando el mapeador
        List<PedidoDTO> listPedDTO = listPed.stream()
                .map(pedidoMapper::pedidoAPedidoDTO)
                .collect(Collectors.toList());


        log.info("Devueltos {} registros.", listPed.size());

        return listPedDTO;
    }

    @Override
    public List<PedidoDTO> getAllByCliente(long idCliente) {
        List<Pedido> listPed = jdbcTemplate.query(
                "SELECT * FROM pedido WHERE id_cliente = ? "
                ,(rs, rowNum) -> new Pedido(rs.getLong("id"),
                        rs.getDouble("total"),
                        rs.getDate("fecha"),
                        rs.getInt("id_cliente"),
                        rs.getInt("id_comercial"))
                , idCliente
        );

        List<PedidoDTO> listPedDTO = listPed.stream()
                .map(pedidoMapper::pedidoAPedidoDTO)
                .collect(Collectors.toList());

        log.info("Devueltos {} registros.", listPed.size());

        return listPedDTO;
    }

    @Override
    public Optional<Pedido> find(long id) {
        Pedido ped =  jdbcTemplate
                .queryForObject("SELECT * FROM pedido WHERE id = ?"
                        , (rs, rowNum) -> new Pedido(rs.getLong("id"),
                                rs.getDouble("total"),
                                rs.getDate("fecha"),
                                rs.getInt("id_cliente"),
                                rs.getInt("id_comercial"))
                        , id
                );

        if (ped != null) {
            return Optional.of(ped);}
        else {
            log.info("Cliente no encontrado.");
            return Optional.empty(); }
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
                , pedido.getId_cliente()
                , pedido.getId_comercial()
                , pedido.getId());


        log.info("Update de Pedido con {} registros actualizados.", rows);

    }

    @Override
    public void delete(long id) {
        int rows = jdbcTemplate.update("DELETE FROM pedido WHERE id = ?", id);

        log.info("Delete de Pedido con {} registros eliminados.", rows);
    }



}
