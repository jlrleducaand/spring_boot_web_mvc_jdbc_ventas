package org.iesvdm.service;

import org.iesvdm.dto.ClienteDTO;
import org.iesvdm.dto.PedidoDTO;
import org.iesvdm.modelo.Cliente;

import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

public interface ComercialServiceI {

    List<PedidoDTO> obtenerPedidosPorComercial(int idComercial);

    Optional<Cliente> obtenerClientePorId(int idCliente);

    OptionalDouble obtenerMediaPedidosComercial(int idComercial);

    OptionalDouble obtenerTotalPedidosComercial(int idComercial);

    Optional<PedidoDTO> obtenerPedidoDeMayorCuantia(int idComercial);

    Optional<PedidoDTO> obtenerPedidoDeMenorCuantia(int idComercial);

    List<ClienteDTO> obtenerListaClientesConPedidosPorIdComercial(int idComercial);

    public List<PedidoDTO> obtenerListaPedidoDeThisComercialIdCliente(int idComercial, int idCliente);
}
