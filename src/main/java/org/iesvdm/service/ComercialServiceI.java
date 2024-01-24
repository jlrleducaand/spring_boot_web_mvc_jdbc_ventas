package org.iesvdm.service;

import org.iesvdm.dto.PedidoDTO;
import org.iesvdm.modelo.Cliente;

import java.util.List;
import java.util.Optional;

public interface ComercialServiceI {

    List<PedidoDTO> obtenerPedidosPorComercial(int idComercial);
    Optional<Cliente> obtenerClientePorId(int idCliente);
}
