package org.iesvdm.controlador;

import java.text.DecimalFormat;
import java.util.*;


import org.iesvdm.dto.ClienteDTO;
import org.iesvdm.dto.PedidoDTO;
import org.iesvdm.modelo.Cliente;
import org.iesvdm.modelo.Comercial;
import org.iesvdm.service.ClienteService;
import org.iesvdm.service.ComercialService;
import org.iesvdm.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;


@Controller
public class ComercialController {

    @Autowired
    private ComercialService comercialService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private PedidoService pedidoService;



    //Se utiliza inyección automática por constructor del framework Spring.
    //Por tanto, se puede omitir la anotación Autowired
    //@Autowired  ,ademas si solo hay un constructor se puede omitir tb


    //@RequestMapping(value = "/clientes", method = RequestMethod.GET)
    //equivalente a la siguiente anotación
    @GetMapping("/comerciales")
    //Al no tener ruta base para el controlador, cada método tiene que tener la ruta completa
    public String listar(Model model) {

        List<Comercial> listaComerciales = comercialService.listAll();
        model.addAttribute("listaComerciales", listaComerciales);

        return "comerciales";

    }

    @GetMapping("/comerciales/{id}")
    public String detalle(Model model, @PathVariable Integer id) {

        DecimalFormat df = new DecimalFormat("#.##");

        // Obtener el detalle del comercial.
        Comercial comercial = comercialService.detalle(id);
        model.addAttribute("comercial", comercial);

        // Obtener la lista de pedidos del comercial y añadirla al modelo.
        List<PedidoDTO> listaPedidos = comercialService.obtenerPedidosPorComercial(id);
        model.addAttribute("listaPedidos", listaPedidos);

        // Obtener el detalle del cliente.
        Cliente cliente = clienteService.detalle(id);
        model.addAttribute("cliente", cliente);

        // Obtiene  el Total de los pedidos del comercial
        OptionalDouble totalDTO = comercialService.obtenerTotalPedidosComercial(id);
        String totalFormateada = df.format(totalDTO.orElse(0.0));
        model.addAttribute("total", totalFormateada);

        // Obtine la media de los pedidos del comercial
        OptionalDouble mediaDTO = comercialService.obtenerMediaPedidosComercial(id);
        String mediaFormateada = df.format(mediaDTO.orElse(0.0));
        model.addAttribute("media", mediaFormateada);

        // Crear un mapa para almacenar los clientes por su ID
        Map<Integer, Cliente> clientes = new HashMap<>();
        //recorro la lista de pedidos  que tengo Arriba
        for (PedidoDTO pedido : listaPedidos) {
            // Obtener el cliente del pedido
            Cliente clie = comercialService.obtenerClientePorId(pedido.getId_cliente()).get();
            //conversion tipos
            int idInt = Math.toIntExact(clie.getId());
            // Solo si aún no está en el mapa lo añado
            if (!clientes.containsKey(clie)) {
                clientes.put(idInt, clie);
            }
        }
        model.addAttribute("Clientes", clientes);

        //Obtener el Pedido de MAYOR cuantía
        Optional<PedidoDTO> pedidoMaximo = comercialService.obtenerPedidoDeMayorCuantia(id);
        PedidoDTO pedidoMax = pedidoMaximo.orElse(null);
        model.addAttribute("PedidoMax", pedidoMax);

        //Obtener el Pedido de MENOR cuantía
        Optional<PedidoDTO> pedidoMinimo = comercialService.obtenerPedidoDeMenorCuantia(id);
        PedidoDTO pedidoMin = pedidoMinimo.orElse(null);
        model.addAttribute("PedidoMin", pedidoMin);

        //Obtener los clientesUnicosConPedidos
        List<ClienteDTO> clientesConPedidos = comercialService.obtenerListaClientesConPedidosPorIdComercial(id);
        model.addAttribute("ClientesUnicos", clientesConPedidos);

        // Nombre de la plantilla
        return "comercial-detalle";

    }

    @GetMapping("/comerciales/crear")
    public String crear(Model model) {

        Comercial comercial = new Comercial();
        model.addAttribute("comercial", comercial);
        return "comercial-crear";

    }

    @PostMapping("/comerciales/crear")
    public RedirectView submitCrear(@ModelAttribute("comercial") Comercial comercial) {

        comercialService.newComercial(comercial);
        return new RedirectView("/comerciales");

    }

    @GetMapping("/comerciales/editar/{id}")
    public String editar(Model model, @PathVariable Integer id) {

        Comercial comercial = comercialService.detalle(id);
        model.addAttribute("comercial", comercial);
        return "comercial-editar";
    }

    @PostMapping("/comerciales/editar/{id}")
    public RedirectView submitEditar(@ModelAttribute("comercial") Comercial comercial) {

        comercialService.replaceComercial(comercial);
        return new RedirectView("/comerciales");
    }

    @PostMapping("/comerciales/borrar/{id}")
    public RedirectView submitBorrar(@PathVariable Integer id) {

        comercialService.deleteComercial(id);
        return new RedirectView("/comerciales");
    }


}
