package org.iesvdm.service;

import java.util.List;
import java.util.Optional;

import org.iesvdm.dao.ComercialDAO;
import org.iesvdm.dao.PedidoDAO;
import org.iesvdm.dto.PedidoDTO;
import org.iesvdm.funcional.MyFunctionalInterface;
import org.iesvdm.modelo.Comercial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.stereotype.Service;

@Service
public class ComercialService implements ComercialServiceI {

    private ComercialDAO comercialDAO;

    private PedidoDAO pedidoDAO;
    private MyFunctionalInterface myLambda;

    @Autowired
    public ComercialService(ComercialDAO comercialDAO, PedidoDAO pedidoDAO) {

        this.comercialDAO = comercialDAO;
        this.pedidoDAO = pedidoDAO;
    }
        public List<Comercial> listAll(){

            return comercialDAO.getAll();
    }



    public Comercial detalle(Integer id){
        return comercialDAO.find(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comercial no encontrado"));

    }



    public void newComercial(Comercial comercial) {

        comercialDAO.create(comercial);

    }

    public void replaceComercial(Comercial comercial) {

        comercialDAO.update(comercial);

    }

    public void deleteComercial(int id) {

        comercialDAO.delete(id);

    }

    @Override
    public List<PedidoDTO> obtenerPedidosPorComercial(int idComercial) {
        return comercialDAO.listaPedidosComercial(idComercial);
    }
}
