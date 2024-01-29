package org.iesvdm.service;

import org.iesvdm.dto.PedidoDTO;
import org.iesvdm.modelo.Comercial;
import org.iesvdm.modelo.Pedido;

import java.util.List;

public interface ClienteServiceI {

    public List<Pedido> obtenerPedidosPorComercial(int idComercial);

    public List<Comercial> obtenerListComercialesAsociadosConPedidos(int idCliente);
}
