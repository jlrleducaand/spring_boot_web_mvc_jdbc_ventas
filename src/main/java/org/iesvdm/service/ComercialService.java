package org.iesvdm.service;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

import org.iesvdm.dao.ClienteDAO;
import org.iesvdm.dao.ComercialDAO;
import org.iesvdm.dao.PedidoDAO;
import org.iesvdm.dto.ClienteDTO;
import org.iesvdm.dto.PedidoDTO;
import org.iesvdm.mapstruct.ClienteMapper;
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
    private final ClienteMapper clienteMapper;


    @Autowired
    public ComercialService(ComercialDAO comercialDAO, PedidoDAO pedidoDAO,
                            ClienteDAO clienteDAO, ComercialMapper comercialMapper,
                            ClienteMapper clienteMapper) {

        this.comercialDAO = comercialDAO;
        this.pedidoDAO = pedidoDAO;
        this.clienteDAO = clienteDAO;
        this.comercialMapper = comercialMapper;
        this.clienteMapper = clienteMapper;
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

    DecimalFormat df = new DecimalFormat("#.##");

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
    public List<ClienteDTO> obtenerListaClientesConPedidosPorIdComercial(int idComercial) {
        List<PedidoDTO> listaPedidos = obtenerPedidosPorComercial(idComercial);

        // Obtener la suma de pedidos por cliente
        Map<Integer, Double> sumaPedidosPorCliente = listaPedidos.stream()
                .collect(Collectors.groupingBy(PedidoDTO::getId_cliente,  // los id van a la key (cliente)
                        Collectors.summingDouble(PedidoDTO::getTotal)));  // las sumas al value

        // Obtener la lista de clientes correspondientes a esos IDs
        List<ClienteDTO> listaClientesConPedidos = sumaPedidosPorCliente.keySet().stream()
                .map(idCliente -> {
                    ClienteDTO clienteDTO = clienteMapper.clienteAClienteDTO(obtenerClientePorId(idCliente).orElse(null));
                    clienteDTO.setSumaPedidos(Double.parseDouble(df.format(sumaPedidosPorCliente.get(idCliente)/100).replaceAll("[^\\d.]", ""))); // setear el atributo de Cliente
                    return clienteDTO;
                })
                .sorted(Comparator.comparing(ClienteDTO::getSumaPedidos).reversed())
                .collect(Collectors.toList());

        return listaClientesConPedidos;
    }



}


