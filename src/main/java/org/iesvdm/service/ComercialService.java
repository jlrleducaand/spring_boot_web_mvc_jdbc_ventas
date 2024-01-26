package org.iesvdm.service;

import java.util.*;
import java.util.stream.Collectors;

import org.iesvdm.dao.ClienteDAO;
import org.iesvdm.dao.ComercialDAO;
import org.iesvdm.dao.PedidoDAO;
import org.iesvdm.dto.ClienteDTO;
import org.iesvdm.dto.ComercialDTO;
import org.iesvdm.dto.PedidoDTO;
import org.iesvdm.mapstruct.ComercialMapper;
import org.iesvdm.modelo.Cliente;
import org.iesvdm.modelo.Comercial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.stereotype.Service;

@Service
public class ComercialService implements ComercialServiceI {

    private final ComercialDAO comercialDAO;
    private final PedidoDAO pedidoDAO;
    private final ClienteDAO clienteDAO;
    private final ComercialMapper comercialMapper;


    @Autowired
    public ComercialService(ComercialDAO comercialDAO, PedidoDAO pedidoDAO, ClienteDAO clienteDAO, ComercialMapper comercialMapper) {

        this.comercialDAO = comercialDAO;
        this.pedidoDAO = pedidoDAO;
        this.clienteDAO = clienteDAO;
        this.comercialMapper = comercialMapper;
    }

    public List<Comercial> listAll() {

        return comercialDAO.getAll();
    }

    public Comercial detalle(Integer id) {
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

    @Override
    public Optional<Cliente> obtenerClientePorId(int idCliente) {
        return clienteDAO.find(idCliente);
    }

    @Override
    public OptionalDouble obtenerMediaPedidosComercial(int  idcomercial) {
        // Obtener la lista de pedidos y calcular la media
        List<PedidoDTO> listaPedidosDTO = comercialDAO.listaPedidosComercial(idcomercial);
        OptionalDouble media = listaPedidosDTO.stream()
                .mapToDouble(PedidoDTO::getTotal)
                .average();
        return media;
    }

    @Override
    public OptionalDouble obtenerTotalPedidosComercial(int idComercial) {
        List<PedidoDTO> listaPedidosDTO = comercialDAO.listaPedidosComercial(idComercial);
        OptionalDouble total = listaPedidosDTO.stream()
                .mapToDouble(PedidoDTO::getTotal)
                .reduce(Double::sum);
        return total;
    }

    @Override
    public Optional<PedidoDTO> obtenerPedidoDeMayorCuantia(int idComercial) {
        return Optional.ofNullable(comercialDAO.listaPedidosComercial(idComercial).stream()
                .max(Comparator.comparing(PedidoDTO::getTotal))
                .orElse(null));

    }

    @Override
    public Optional<PedidoDTO> obtenerPedidoDeMenorCuantia(int idComercial) {
        return Optional.ofNullable(comercialDAO.listaPedidosComercial(idComercial).stream()
                .min(Comparator.comparing(PedidoDTO::getTotal))
                .orElse(null));
    }

    @Override
    public List<ClienteDTO> obtenerListadoClientesOrdenadoPorCuantia(int idComercial) {
        return null;
    }

//    @Override
//    public List<ClienteDTO> obtenerListadoClientesOrdenadoPorCuantia(int idComercial) {
//        // Obtener la lista de pedidos del comercial
//        List<PedidoDTO> listaPedidos = obtenerPedidosPorComercial(idComercial);
//
//        // Obtener los IDs de los clientes de esos pedidos
//        List<Integer> idClientes = listaPedidos.stream()
//                .map(PedidoDTO::getId_cliente)
//                .collect(Collectors.toList());
//
//        // Obtener la lista de clientes correspondientes a esos IDs
//        List<Cliente> listaClientes = idClientes.stream()
//                .map(this::obtenerClientePorId)
//                .filter(Optional::isPresent)
//                .map(Optional::get)
//                .collect(Collectors.toList());
//
//        // Ordenar la lista de clientes por la cuantía total de los pedidos
//        List<ClienteDTO> listClienteDTO = listaClientes.stream()
//                .map(cliente -> comercialMapper.)
//    }

    // Método para calcular la cuantía total de los pedidos de un cliente
//    private double calcularCuantiaTotalCliente(Cliente cliente, List<PedidoDTO> listaPedidos) {
//        return listaPedidos.stream()
//                .filter(pedido -> pedido.getId_cliente() == cliente.getId())
//                .mapToDouble(PedidoDTO::getTotal)
//                .sum();
//    }


}


