package org.iesvdm.mapstruct;


import org.iesvdm.dto.PedidoDTO;
import org.iesvdm.modelo.Pedido;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface PedidoMapper {

    public PedidoDTO pedidoAPedidolDTO(Pedido pedido);
    public Pedido pedidoDTOAPedido(PedidoDTO pedidoDTO);
}
