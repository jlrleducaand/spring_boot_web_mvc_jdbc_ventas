package org.iesvdm.service;

import java.util.List;
import java.util.Optional;

import org.iesvdm.dao.ClienteDAO;
import org.iesvdm.domain.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service  //solo espero logica de negocios
public class ClienteService {

    @Autowired
    private ClienteDAO clienteDAO;

    //Se utiliza inyección automática por constructor del framework Spring.
    //Por tanto, se puede omitir la anotación Autowired  o NO
    //@Autowired
    //public ClienteService(ClienteDAO clienteDAO) {
    //
    //	this.clienteDAO = clienteDAO;
    //}

    public List<Cliente> listAll() {

        return clienteDAO.getAll();

    }

    public Cliente detalle(Integer id) {
        Optional<Cliente> optClie = clienteDAO.find(id);
        if (optClie.isPresent())
            return optClie.get();
        else
            return null;
    }

    public void newCliente(Cliente cliente) {

        clienteDAO.create(cliente);

    }

    public void replaceCliente(Cliente cliente) {

        clienteDAO.update(cliente);

    }

    public void deleteCliente(int id) {

        clienteDAO.delete(id);

    }

}
