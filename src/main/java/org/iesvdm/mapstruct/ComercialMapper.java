package org.iesvdm.mapstruct;


import org.iesvdm.dto.ComercialDTO;
import org.iesvdm.dto.PedidoDTO;
import org.iesvdm.modelo.Comercial;
import org.iesvdm.modelo.Pedido;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ComercialMapper {


    public ComercialDTO comercialAComercialDTO(Comercial comercial);

    public Comercial comercialDTOAComercial(ComercialDTO comercialDTO);

    public List<Pedido> listPedidoDTOALisPedido(List<PedidoDTO> listaPedidoDTO);

    public List<PedidoDTO> listPedidoALisPedidoDTO(List<Pedido> listaPedido);

    public PedidoDTO pedidoAPedidoDTO(Pedido pedido);

    public Pedido PedidoDTOAPedido(PedidoDTO pedidoDTO);

}
