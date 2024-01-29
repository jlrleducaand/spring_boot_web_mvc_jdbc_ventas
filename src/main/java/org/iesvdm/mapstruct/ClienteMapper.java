package org.iesvdm.mapstruct;

import org.iesvdm.dto.ClienteDTO;
import org.iesvdm.dto.ComercialDTO;
import org.iesvdm.dto.PedidoDTO;
import org.iesvdm.modelo.Cliente;
import org.iesvdm.modelo.Comercial;
import org.iesvdm.modelo.Pedido;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    public ClienteDTO clienteAClienteDTO(Cliente cliente);

    public Cliente clienteDTOACliente(ClienteDTO clienteDTO);


    public List<Pedido> listPedidoDTOALisPedido(List<PedidoDTO> listaPedidoDTO);

    public List<PedidoDTO> listPedidoALisPedidoDTO(List<Pedido> listaPedido);


    public PedidoDTO pedidoAPedidoDTO(Pedido pedido);

    public Pedido PedidoDTOAPedido(PedidoDTO pedidoDTO);

    public List<ComercialDTO> listComercialAListComercialDTO(List<Comercial> listaComercial);

    public List<Comercial> listComerciaDTOlAListComercial(List<ComercialDTO> listaComercial);

}
