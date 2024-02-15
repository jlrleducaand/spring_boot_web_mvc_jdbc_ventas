package org.iesvdm.mapstruct;


import org.iesvdm.dto.PedidoDTO;
import org.iesvdm.modelo.Pedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface PedidoMapper {


    public PedidoDTO pedidoAPedidoDTO(Pedido pedido);

    public Pedido pedidoDTOAPedido(PedidoDTO pedidoDTO);

    public List<Pedido> listPedidoDTOAListPedido (List<PedidoDTO> listPedidoDTO);

    public List<PedidoDTO> listPedidoAListPedidoDTO (List<Pedido> listPedido);

}
