package com.uma.example.springuma.integration.base;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uma.example.springuma.model.Medico;
import com.uma.example.springuma.model.Paciente;

public class PacienteControllerIT extends AbstractIntegration {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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
        this.mockMvc.perform(post("/medico")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(medico)));
        
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
        this.mockMvc.perform(post("/paciente")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(paciente)));
        
        return paciente;
    }

    @Test
    @DisplayName("Creación de un paciente")
    public void crearPaciente_Test() throws Exception {

        Paciente paciente = new Paciente();
        paciente.setDni("1234B");
        paciente.setCita("Revision");
        paciente.setEdad(33);
        paciente.setId(1);
        paciente.setNombre("Rodolfa");
        
        // Crear paciente
        this.mockMvc.perform(post("/paciente")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(paciente)))
            .andExpect(status().isCreated())
            .andExpect(status().is2xxSuccessful());
            
        // Obtenemos el Paciente
        this.mockMvc.perform(get("/paciente/{id}",paciente.getId()))
            .andDo(print())
            .andExpect(status().is2xxSuccessful())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.dni").value(paciente.getDni()));
    }

    @Test
    @DisplayName("Edición de un paciente")
    public void editarPaciente_Test () throws Exception {
        Paciente paciente = crearPaciente();

        paciente.setCita("Revisión atrasada");
        paciente.setNombre("Mi madre");

        this.mockMvc.perform(put("/paciente")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(paciente)))
            .andExpect(status().isNoContent())
            .andExpect(status().is2xxSuccessful());

        this.mockMvc.perform(get("/paciente/{id}", paciente.getId()))
            .andDo(print())
            .andExpect(status().is2xxSuccessful())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.cita").value(paciente.getCita()))
            .andExpect(jsonPath("$.nombre").value(paciente.getNombre()));
    }

    @Test
    @DisplayName("Prueba de borrado de paciente")
    public void borrarPaciente_Test() throws Exception {
        Paciente paciente = crearPaciente();

        this.mockMvc.perform(delete("/paciente/{id}", paciente.getId())
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(paciente)))
            .andExpect(status().isOk())
            .andExpect(status().is2xxSuccessful());
        
        this.mockMvc.perform(get("/paciente/{id}", paciente.getId()))
            .andDo(print())
            .andExpect(status().is5xxServerError());
    }

    @Test
    @DisplayName("Prueba de asignación de médico a paciente")
    public void asociarPacienteAMedico_Test() throws Exception {
        Medico medico = crearMedico();
        Paciente paciente = crearPaciente();

        paciente.setMedico(medico);

        this.mockMvc.perform(put("/paciente")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(paciente)))
            .andExpect(status().isNoContent())
            .andExpect(status().is2xxSuccessful());
        
        this.mockMvc.perform(get("/paciente/{id}", paciente.getId()))
            .andDo(print())
            .andExpect(status().is2xxSuccessful())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.medico.id").value(medico.getId()));
    }

    @Test
    @DisplayName("Obtención de la lista de pacientes de un médico")
    public void listaPacientesMedico_Test() throws Exception {
        Medico medico = crearMedico();
        Paciente paciente = crearPaciente(medico, "3342N", "Revision", "Rodolfo", 1, 33);
        Paciente paciente2 = crearPaciente(medico, "3344N", "Segunda Revision", "Rodolfita", 2, 34);

        this.mockMvc.perform(get("/paciente/medico/{id}", medico.getId()))
            .andDo(print())
            .andExpect(status().is2xxSuccessful())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].id").value(paciente.getId()))
            .andExpect(jsonPath("$[1].id").value(paciente2.getId()));
    }

    @Test
    @DisplayName("Cambiar medico de los pacientes")
    public void cambiarMedicosPaciente_Test() throws Exception {
        
        Medico medico = crearMedico();
        Medico medico2 = crearMedico("1133G", "Especial", 2, "Confucio");
        Paciente paciente = crearPaciente(medico, "3342N", "Revision", "Rodolfa", 1, 33);
        Paciente paciente2 = crearPaciente(medico2, "3344N", "Segunda Revision", "Rodolfita", 2, 34);

        // Comprobar los primeros médicos
        this.mockMvc.perform(get("/paciente/{id}", paciente.getId()))
            .andDo(print())
            .andExpect(status().is2xxSuccessful())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.medico.id").value(medico.getId()));

        this.mockMvc.perform(get("/paciente/{id}", paciente2.getId()))
            .andDo(print())
            .andExpect(status().is2xxSuccessful())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.medico.id").value(medico2.getId()));
            
        // Cambiar los médicos
        paciente.setMedico(medico2);
        paciente2.setMedico(medico);

        this.mockMvc.perform(put("/paciente")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(paciente)))
            .andExpect(status().isNoContent())
            .andExpect(status().is2xxSuccessful());
        
        this.mockMvc.perform(get("/paciente/{id}", paciente.getId()))
            .andDo(print())
            .andExpect(status().is2xxSuccessful())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.medico.id").value(medico2.getId()));

        this.mockMvc.perform(put("/paciente")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(paciente2)))
            .andExpect(status().isNoContent())
            .andExpect(status().is2xxSuccessful());
        
        this.mockMvc.perform(get("/paciente/{id}", paciente2.getId()))
            .andDo(print())
            .andExpect(status().is2xxSuccessful())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.medico.id").value(medico.getId()));
    }

    @Test
    @DisplayName("Desasignar un paciente de un medico")
    public void desasignarPacienteDeMedico_Test() throws Exception {
        
        Medico medico = crearMedico();
        Paciente paciente = crearPaciente(medico, "3342N", "Revision", "Rodolfa", 1, 33);
        Paciente paciente2 = crearPaciente(medico, "3344N", "Segunda Revision", "Rodolfita", 2, 34);

        this.mockMvc.perform(delete("/paciente/{id}", paciente.getId()))
            .andDo(print())
            .andExpect(status().isOk());

        this.mockMvc.perform(get("/paciente/medico/{id}", medico.getId()))
            .andDo(print())
            .andExpect(status().is2xxSuccessful())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].id").value(paciente2.getId()));
    }
}
