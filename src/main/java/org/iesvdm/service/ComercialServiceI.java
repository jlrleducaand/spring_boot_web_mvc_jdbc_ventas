package org.iesvdm.service;

import org.iesvdm.dto.PedidoDTO;
import java.util.List;

public interface ComercialServiceI {

    List<PedidoDTO> obtenerPedidosPorComercial(int idComercial);
}
