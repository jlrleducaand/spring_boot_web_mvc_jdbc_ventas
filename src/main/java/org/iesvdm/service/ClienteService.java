package org.iesvdm.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.iesvdm.dao.ClienteDAO;
import org.iesvdm.dao.ComercialDAO;
import org.iesvdm.dao.PedidoDAO;
import org.iesvdm.dao.PedidoDAOImpl;
import org.iesvdm.dto.ClienteDTO;
import org.iesvdm.dto.PedidoDTO;
import org.iesvdm.mapstruct.ClienteMapper;
import org.iesvdm.mapstruct.PedidoMapper;
import org.iesvdm.modelo.Cliente;
import org.iesvdm.modelo.Comercial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service  //solo espero logica de negocios
public class ClienteService {

    private ClienteDAO clienteDAO;
    private PedidoDAO pedidoDAOImpl;
    private ComercialDAO comercialDAO;
    private ClienteDTO clienteDTO;
    private ClienteMapper clienteMapper;
    private PedidoMapper pedidoMapper;


    @Autowired
    public ClienteService(ClienteDAO clienteDAO,
                          PedidoDAOImpl pedidoDAOImpl,
                          ComercialDAO comercialDAO,
                          ClienteDTO clienteDTO,
                          ClienteMapper clienteMapper,
                          PedidoMapper pedidoMapper)
    {
        this.clienteDAO = clienteDAO;
        this.pedidoDAOImpl = pedidoDAOImpl;
        this.comercialDAO = comercialDAO;
        this.clienteDTO = clienteDTO;
        this.clienteMapper = clienteMapper;
        this.pedidoMapper = pedidoMapper;
    }

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

    public Cliente detalle(Integer idCliente) {

        return clienteDAO.find(idCliente)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado"));
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

    public List<PedidoDTO> listadoDePedidosDelClienteID(int idCliente){

        List<PedidoDTO> listPedidos =  pedidoDAOImpl.getAllByCliente(idCliente);

        return listPedidos;
    }


     public List<Comercial> listComercialesAsociadosSeteado(int idCliente){

        // ids de los Comerciales sin repeticiones
        Set<Integer> setCom = listadoDePedidosDelClienteID(idCliente).stream()
                .map(PedidoDTO::getId_comercial)
                .collect(Collectors.toSet());

        // List comeciales asociados para setear
        List<Comercial> lstCom = comercialDAO.getAll().stream()
                .filter(comercial -> setCom.contains(comercial.getId()))
                .collect(Collectors.toList());

        //seteo del resultado a DTO
         ClienteDTO clienteDTO = clienteMapper.clienteAClienteDTO(clienteDAO.find(idCliente).get());
         clienteDTO.setComercialesAsociados(lstCom);

        return clienteDTO.getComercialesAsociados(); //alternativa  lstCom
    }




}
