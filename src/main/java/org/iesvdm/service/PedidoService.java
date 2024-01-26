package org.iesvdm.service;

import java.util.List;
import java.util.Optional;

import org.iesvdm.dao.PedidoDAO;
import org.iesvdm.modelo.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service  //solo espero logica de negocios
public class PedidoService {

    @Autowired
    private PedidoDAO pedidoDAO;
    //Si los pedidos tuvieran mas objetos dentro en vez de atributos  habria que
    // ponerlos aqui tambien.  con @Autowired  (un Cliente en ved de id_Cliente)

    //Se utiliza inyección automática por constructor del framework Spring.
    //Por tanto, se puede omitir la anotación Autowired
    //@Autowired
    //public PedidoService(PedidoDAO pedidoDAO) {
    //
    //	this.pedidoDAO = pedidoDAO;
    //}

    public List<Pedido> listAll() {

        return pedidoDAO.getAll();
    }

    public Pedido detalle(Integer id) {
        Optional<Pedido> optPed = pedidoDAO.find(id);
        if (optPed.isPresent())
            return optPed.get();
        else
            return null;
    }

    public void newPedido(Pedido pedido) {

        pedidoDAO.create(pedido);

    }

    public void replacePedido(Pedido pedido) {

        pedidoDAO.update(pedido);

    }

    public void deletePedido(int id) {

        pedidoDAO.delete(id);

    }
}