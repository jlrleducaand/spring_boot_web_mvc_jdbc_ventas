package org.iesvdm.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.iesvdm.dao.*;
import org.iesvdm.dto.ClienteDTO;
import org.iesvdm.dto.ComercialDTO;
import org.iesvdm.dto.PedidoDTO;
import org.iesvdm.mapstruct.ClienteMapper;
import org.iesvdm.mapstruct.ComercialMapper;
import org.iesvdm.mapstruct.PedidoMapper;
import org.iesvdm.modelo.Cliente;
import org.iesvdm.modelo.Comercial;
import org.iesvdm.modelo.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service  //solo espero logica de negocios
public class ClienteService implements ClienteServiceI{

    private  ClienteDAO clienteDAO;
    private  PedidoDAO pedidoDAOImpl;
    private  ComercialDAO comercialDAO;
    private  ClienteMapper clienteMapper;
    private  PedidoMapper pedidoMapper;
    private  ClienteDAOImpl clienteDAOImpl;
    private ComercialMapper comercialMapper;
    private ClienteDTO clienteDTO;



    @Autowired //inyeccion automatica por constructor
    public ClienteService(ClienteDAO clienteDAO,
                          PedidoDAOImpl pedidoDAOImpl,
                          ComercialDAO comercialDAO,
                          ClienteMapper clienteMapper,
                          PedidoMapper pedidoMapper,
                          ClienteDAOImpl clienteDAOImpl)
    {
        this.clienteDAO = clienteDAO;
        this.pedidoDAOImpl = pedidoDAOImpl;
        this.comercialDAO = comercialDAO;
        this.clienteMapper = clienteMapper;
        this.pedidoMapper = pedidoMapper;
        this.clienteDAOImpl = clienteDAOImpl;
    }

    //Se utiliza inyección automática por constructor del framework Spring.
    //Por tanto, se puede omitir la anotación Autowired o NO
    //@Autowired
    //public ClienteService(ClienteDAO clienteDAO) {
    //
    //	this.clienteDAO = clienteDAO;
    //}

    public List<Cliente> listAll() {

        return clienteDAOImpl.getAll();

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


    @Override
    public List<Pedido> obtenerPedidosPorComercial(int idComercial) {
        return pedidoMapper.listPedidoDTOAListPedido(comercialDAO.listaPedidosComercial(idComercial));
    }

    @Override
     public List<Comercial> obtenerListComercialesAsociadosConPedidos(int idCliente){
        // Obtener la lista de pedidos del cliente
        List<PedidoDTO> listPedidosCliente = pedidoDAOImpl.getAllByCliente(idCliente);


        // Obtener los IDs únicos de los comerciales asociados al cliente
        Set<Long> setComerciales  = listPedidosCliente.stream()
                .map(PedidoDTO::getId_comercial)
                .collect(Collectors.toSet());

        // Obtener lista de ComercialDTO del cliente específico
        List<Comercial> lstComerciales = setComerciales.stream()
                .map(idComercial -> comercialDAO.find(idComercial).orElse(null))
                .collect(Collectors.toList());

        //seteo del resultado a DTO de Cliente
         ClienteDTO clienteDTO = clienteMapper.clienteAClienteDTO(clienteDAO.find(idCliente).get());
         clienteDTO.setComercialesAsociados(lstComerciales);



        return lstComerciales; //alternativa  lstCom
    }

    @Override
    public List<ComercialDTO> obtenerEstadisticaYComerciales(int idCliente) {

        return clienteDAOImpl.ComercialesConEstadisticasPedidosDeCliente(idCliente);
    }


    public int obtenerNumPedidosPorComercialYCliente(int idComercial, int idCliente) {
        List<Pedido> lstPorComer = obtenerPedidosPorComercial(idComercial);

        // Filtrar la lista de pedidos para obtener solo los relacionados con el cliente específico
        List<Pedido> lstPorComerPorClie = lstPorComer.stream()
                .filter(pedido -> pedido.getId_cliente() == idCliente)
                .toList();

        return lstPorComerPorClie.size();
    }



}
