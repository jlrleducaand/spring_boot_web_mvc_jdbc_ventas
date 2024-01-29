package org.iesvdm.controlador;

import java.util.List;

import org.iesvdm.dto.ClienteDTO;
import org.iesvdm.dto.PedidoDTO;
import org.iesvdm.modelo.Cliente;
import org.iesvdm.modelo.Comercial;
import org.iesvdm.service.ClienteService;
import org.iesvdm.service.ComercialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;


@Controller
//Se puede fijar ruta base de las peticiones de este controlador.
//Los mappings de los métodos tendrían este valor /clientes como
//prefijo.
//@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ComercialService comercialService;

    //Se utiliza inyección automática por constructor del framework Spring.
    //Por tanto, se puede omitir la anotación Autowired
    //@Autowired ,ademas si solo hay un constructor se puede omitir tb

    /*public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }*/

    //@RequestMapping(value = "/clientes", method = RequestMethod.GET)
    //equivalente a la siguiente anotación
    @GetMapping("/clientes") //Al no tener ruta base para el controlador, cada método tiene que tener la ruta completa
    public String listar(Model model) {

        List<Cliente> listaClientes = clienteService.listAll();
        model.addAttribute("listaClientes", listaClientes);

        return "clientes";

    }

    @GetMapping("/clientes/{id}")
    public String detalle(Model model, @PathVariable Integer id) {

//        //Obtener el listado de Pedidos por Comercial y Cliente
//        List<PedidoDTO> lstPedComClie = comercialService.obtenerListaPedidoPorIdIdComercialPorIdCliente()

        // Obtener el detalle del cliente
        Cliente cliente =   clienteService.detalle(id);
        model.addAttribute("cliente", cliente);


        // Obtener el Listado de Comerciales Asociados
        List<Comercial> lstComerciales = clienteService.listComercialesAsociadosSeteado(id);
        model.addAttribute("listaComercial", lstComerciales);

        return "cliente-detalle";

    }

    @GetMapping("/clientes/crear")
    public String crear(Model model) {

        Cliente cliente = new Cliente();
        model.addAttribute("cliente", cliente);

        return "cliente-crear";

    }

    @PostMapping("/clientes/crear")
    public RedirectView submitCrear(@ModelAttribute("cliente") Cliente cliente) {

        clienteService.newCliente(cliente);

        return new RedirectView("/clientes");

    }

    @GetMapping("/clientes/editar/{id}")
    public String editar(Model model, @PathVariable Integer id) {

        Cliente cliente = clienteService.detalle(id);
        model.addAttribute("cliente", cliente);

        return "cliente-editar";

    }


    @PostMapping("/clientes/editar/{id}")
    public RedirectView submitEditar(@ModelAttribute("cliente") Cliente cliente) {

        clienteService.replaceCliente(cliente);

        return new RedirectView("/clientes");
    }

    @PostMapping("/clientes/borrar/{id}")
    public RedirectView submitBorrar(@PathVariable Integer id) {

        clienteService.deleteCliente(id);

        return new RedirectView("/clientes");
    }

}
