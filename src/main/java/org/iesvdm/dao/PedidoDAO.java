package org.iesvdm.dao;

import org.iesvdm.dto.PedidoDTO;
import org.iesvdm.modelo.Pedido;
import java.util.List;
import java.util.Optional;

public interface PedidoDAO {
    public void create(Pedido pedido);

    public List<Pedido> getAll();

    public List<PedidoDTO> getAllByComercial(long id);

    public List<PedidoDTO> getAllByCliente(long id);

    public Optional<Pedido> find(long id);

    public void update(Pedido pedido);

    public void delete(long id);

}
