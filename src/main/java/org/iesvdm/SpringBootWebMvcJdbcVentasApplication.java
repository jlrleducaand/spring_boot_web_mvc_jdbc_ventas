package org.iesvdm;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.iesvdm.dao.ClienteDAO;
import org.iesvdm.dao.ComercialDAO;
import org.iesvdm.dao.PedidoDAO;
import org.iesvdm.modelo.Cliente;
import org.iesvdm.modelo.Comercial;
import org.iesvdm.modelo.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.boot.context.event.ApplicationReadyEvent;


@SpringBootApplication
public class SpringBootWebMvcJdbcVentasApplication implements CommandLineRunner {

    @Autowired
    private ClienteDAO clienteDAO;
    @Autowired
    private ComercialDAO comercialDAO;
    @Autowired
    private PedidoDAO pedidoDAO;

    private static final Logger log = LoggerFactory.getLogger(SpringBootWebMvcJdbcVentasApplication.class);

    public static void main(String[] args) {

        SpringApplication app = new SpringApplication(SpringBootWebMvcJdbcVentasApplication.class);
        app.addListeners((ApplicationListener<ApplicationReadyEvent>) event -> openBrowser());
        app.run(args);

    }

    //Los métodos son llamados en el MAIN: (run y openBrowser)
    @Override
    public void run(String... args) throws Exception {

        log.info("*******************************");
        log.info("*Prueba de arranque ClienteDAO*");
        log.info("*******************************");

        String nombreOld = ""; // Se utiliza en todas las pruebas

       clienteDAO.getAll().forEach(c -> log.info("Cliente: {}", c));


        int id = 1;
        Optional<Cliente> cliente = clienteDAO.find(id);

        if (cliente.isPresent()) {
            log.info("Cliente {}: {}", id, cliente.get());

            nombreOld = cliente.get().getNombre();
            cliente.get().setNombre("Jose M");
            clienteDAO.update(cliente.get());
            cliente = clienteDAO.find(id);

            log.info("Cliente {}: {}", id, cliente.get());

            //Volvemos a cargar el nombre antiguo..
            cliente.get().setNombre(nombreOld);
            clienteDAO.update(cliente.get());

        }
        log.info("*******************************");
        log.info("*Prueba de arranque ComercialDAO*");
        log.info("*******************************");

        comercialDAO.getAll().forEach(c -> log.info("Comercial: {}", c));

        id = 1;
        Optional<Comercial> comercial = comercialDAO.find(id);

        if (comercial.isPresent()) {
            log.info("Comercial {}: {}", id, comercial.get());

            nombreOld = comercial.get().getNombre();
            comercial.get().setNombre("Jose M");
            comercialDAO.update(comercial.get());
            comercial = comercialDAO.find(id);

            log.info("Comercial {}: {}", id, comercial.get());

            //Volvemos a cargar el nombre antiguo..
            comercial.get().setNombre(nombreOld);
            comercialDAO.update(comercial.get());

        }
        log.info("*******************************");
        log.info("*Prueba de arranque PedidoDAO*");
        log.info("*******************************");

        pedidoDAO.getAll().forEach(c -> log.info("Pedido: {}", c));


        id = 1;
        Optional<Pedido> pedido = pedidoDAO.find(id);

        if (pedido.isPresent()) {
            log.info("Pedido {}: {}", id, pedido.get());

            int pedidoOld = (int) pedido.get().getId();
            pedido.get().setTotal(25.00);
            pedidoDAO.update(pedidoDAO.find(pedidoOld).get());
            pedido = pedidoDAO.find(id);

            log.info("Pedido {}: {}", id, pedido.get());

            //Volvemos a cargar el nombre antiguo..
            pedido.get().setId(pedidoOld);
            pedidoDAO.update(pedido.get());
        }

        // Como es un cliente nuevo a persistir, id a 0
        Cliente clienteNew = new Cliente(0, "Jose M", "Martín", null, "Málaga", 100);

        //create actualiza el id
        clienteDAO.create(clienteNew);

        log.info("Cliente nuevo con id = {}", clienteNew.getId());

        clienteDAO.getAll().forEach(c -> log.info("Cliente: {}", c));

        //borrando por el id obtenido de create
        clienteDAO.delete(clienteNew.getId());
        clienteDAO.getAll().forEach(c -> log.info("Cliente: {}", c));

        log.info("************************************");
        log.info("*FIN: Prueba de arranque ClienteDAO*");
        log.info("************************************");


        // Como es un comercial nuevo a persistir, id a 0
        Comercial comercialNew = new Comercial(0, "Jose Manuel", "López", "Martín", BigDecimal.valueOf(0.1));

        //create actualiza el id
        comercialDAO.create(comercialNew);

        log.info("Cliente nuevo con id = {}", comercialNew.getId());

        comercialDAO.getAll().forEach(c -> log.info("Comercial: {}", c));

        //borrando por el id obtenido de create
        comercialDAO.delete(comercialNew.getId());

        comercialDAO.getAll().forEach(c -> log.info("Comercial: {}", c));

        log.info("************************************");
        log.info("*FIN: Prueba de arranque comercialDAO*");
        log.info("************************************");


        // Como es un Pedido nuevo a persistir, id a 0
        SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy"); // Cambiado a dd-MM-yyyy
        Date fecha = null;
        try {
            fecha = formateador.parse("02-12-2023");
        } catch (Exception e) {
            log.info("La cadena propuesta no se puede transformar en tipo Date");
        }

        // Convertir java.util.Date a java.sql.Date correctamente
        java.sql.Date sqlFecha = new java.sql.Date(fecha.getTime());

        Pedido pedidoNew = new Pedido(0, 28.90, sqlFecha, 2, 4); // Usar sqlFecha


        //create actualiza el id
        pedidoDAO.create(pedidoNew);

        log.info("Pedido nuevo con id = {}", pedidoNew.getId());

        pedidoDAO.getAll().forEach(c -> log.info("Pedido: {}", c));

        //borrando por el id obtenido de create
        pedidoDAO.delete(pedidoNew.getId());

        pedidoDAO.getAll().forEach(c -> log.info("Pedido: {}", c));

        log.info("************************************");
        log.info("*FIN: Prueba de arranque PedidoDAO*");
        log.info("************************************");


    }

    //El metodo es llamado en el MAIN junto con RUN
    private static void openBrowser() {
        String url = "http://localhost:8080/index";
        String os = System.getProperty("os.name").toLowerCase();

        try {
            if (os.contains("win")) {
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
            } else if (os.contains("mac")) {
                Runtime.getRuntime().exec("open " + url);
            } else if (os.contains("nix") || os.contains("nux")) {
                Runtime.getRuntime().exec("xdg-open " + url);
            }
        } catch (Exception e) {
            log.error("Error al abrir el navegador: ", e);
        }
    }

}
