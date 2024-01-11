package org.iesvdm.service;

import org.iesvdm.dao.PedidoDAO;
import org.iesvdm.modelo.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


    @Service  //solo espero logica de negocios
    public class PedidoService {

        @Autowired
        private PedidoDAO pedidoDAO;

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
    }