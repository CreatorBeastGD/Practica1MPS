package com.uma.example.springuma.integration.base;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.time.Duration;
import java.util.Calendar;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import com.uma.example.springuma.model.Imagen;
import com.uma.example.springuma.model.Informe;
import com.uma.example.springuma.model.Medico;
import com.uma.example.springuma.model.Paciente;

import jakarta.annotation.PostConstruct;
import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class InformeControllerIT extends AbstractIntegration {

    @LocalServerPort
    private Integer port;

    private WebTestClient webTestClient;

    private Imagen imagen;
    private Informe informe;
    private Medico medico;
    private Paciente paciente;
    private String prediction;

    public Medico crearMedico() throws Exception {
        return crearMedico("3333A", "Mamografia", 1, "Fernando");
    }

    public Medico crearMedico(String dni, String especialidad, int id, String nombre) throws Exception {
        Medico medico = new Medico();
        medico.setDni(dni);
        medico.setEspecialidad(especialidad);
        medico.setId(id);
        medico.setNombre(nombre);

        // Creación
        webTestClient.post().uri("/medico")
            .body(Mono.just(medico), Medico.class)
            .exchange()
            .expectStatus().isCreated()
            .expectBody().returnResult();

        
        return medico;
    }

    public Paciente crearPaciente() throws Exception {
        return crearPaciente(null, "1234B", "Revision", "Rodolfa", 1, 33);
    }

    public Paciente crearPaciente(Medico medico, String dni, String cita, String nombre, int id, int edad) throws Exception {
        Paciente paciente = new Paciente();
        paciente.setDni(dni);
        paciente.setCita(cita);
        paciente.setEdad(edad);
        paciente.setId(id);
        paciente.setNombre(nombre);
        paciente.setMedico(medico);

        // Creación
        webTestClient.post().uri("/paciente")
            .body(Mono.just(paciente), Paciente.class)
            .exchange()
            .expectStatus().isCreated()
            .expectBody().returnResult();
        
        return paciente;
    }

    public Imagen crearImagen(Paciente paciente, String imageName) throws Exception {
        Imagen imagen = new Imagen();
        imagen.setNombre("Imagen 1");
        imagen.setFecha(Calendar.getInstance());
        imagen.setId(1);
        imagen.setPaciente(paciente);

        // Cargar y guardar el archivo de la imagen
        File file = new File("src/test/resources/" + imageName);
        
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("paciente", paciente);
        builder.part("image", new FileSystemResource(file));

        // Subimos la imagen a la base de datos
        webTestClient.post()
            .uri("/imagen")
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .body(BodyInserters.fromMultipartData(builder.build()))
            .exchange()
            .expectStatus().is2xxSuccessful();


        return imagen;
    }

    @PostConstruct
    public void init() throws Exception {
        webTestClient = WebTestClient.bindToServer().baseUrl("http://localhost:" + port)
                .responseTimeout(Duration.ofMillis(30000))
                .build();

        medico = crearMedico();
        
        paciente = crearPaciente();
        paciente.setMedico(medico);

        imagen = crearImagen(paciente, "healthy.png");

        FluxExchangeResult<String> result = webTestClient.get().uri("/imagen/predict/1")
            .exchange()
            .expectStatus().isOk().returnResult(String.class); // comprueba que la respuesta es de tipo String

        String json = result.getResponseBody().blockFirst();
        prediction = json.substring(16, 36);

        if(!prediction.equals("Not cancer (label 0)"))
        {
            prediction = json.substring(16, 32);
        }
        
        informe = new Informe();
        informe.setId(1);
        informe.setImagen(imagen);
        informe.setPrediccion(prediction);
    }

    @Test
    @DisplayName("Subir un informe y obtenerlo después para ver si se insertó correctamente sus datos")
    public void submitInformeRespondsValidResponse_Test() throws Exception {

        // Creación
        webTestClient.post().uri("/informe")
            .body(Mono.just(informe), Informe.class)
            .exchange()
            .expectStatus().isCreated()
            .expectBody().returnResult();
        
        //Obtiene el informe con ID 1
        FluxExchangeResult<Informe> result = webTestClient.get().uri("/informe/1")
            .exchange()
            .expectStatus().isOk()
            .returnResult(Informe.class);

        Informe output = result.getResponseBody().blockFirst();

        assertEquals(informe.getContenido(), output.getContenido());
        assertEquals(informe.getId(), output.getId());
        String output_prediccion = output.getPrediccion().substring(0, 20);
        assertEquals(informe.getPrediccion(), output_prediccion);
        
    }

    @Test
    @DisplayName("Subir un informe y borrarlo despuéss")
    public void submitInformeAndDeleteValidResponse_Test() throws Exception {

        // Creación
        webTestClient.post().uri("/informe")
            .body(Mono.just(informe), Informe.class)
            .exchange()
            .expectStatus().isCreated()
            .expectBody().returnResult();
        
        //Elimina el informe
        FluxExchangeResult<String> result = webTestClient.delete().uri("/informe/1")
            .exchange()
            .expectStatus().isNoContent()
            .returnResult(String.class);
     
        String response = result.getResponseBody().blockFirst();
        assertEquals(null, response);
        
    }

}
