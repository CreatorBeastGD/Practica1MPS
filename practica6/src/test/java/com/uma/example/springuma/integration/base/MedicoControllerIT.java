package com.uma.example.springuma.integration.base;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uma.example.springuma.model.Medico;
public class MedicoControllerIT extends AbstractIntegration {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Prueba de creación de médico")
    void CrearMedico_Test() throws Exception {
        Medico medico = new Medico();
        medico.setDni("3333A");
        medico.setEspecialidad("Mamografia");
        medico.setId(1);
        medico.setNombre("Fernando");

        // Creación
        this.mockMvc.perform(post("/medico")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(medico)))
            .andExpect(status().isCreated())
            .andExpect(status().is2xxSuccessful());
        
        // Obtención
        this.mockMvc.perform(get("/medico/1"))
            .andDo(print())
            .andExpect(status().is2xxSuccessful())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.dni").value(medico.getDni()));
    }

    public Medico crearMedico() throws Exception{
        Medico medico = new Medico();
        medico.setDni("3333A");
        medico.setEspecialidad("Mamografia");
        medico.setId(1);
        medico.setNombre("Fernando");

        // Creación
        this.mockMvc.perform(post("/medico")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(medico)));
        
        return medico;
    }

    @Test
    @DisplayName("Prueba de actualizar un médico")
    void ActualizarMedico_Test() throws Exception {
        Medico medico = crearMedico();

        // Actualizar
        medico.setDni("4444A");
        medico.setEspecialidad("Radiografo");
        medico.setNombre("Alonso");

        this.mockMvc.perform(put("/medico")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(medico)))
            .andExpect(status().isNoContent())
            .andExpect(status().is2xxSuccessful());

        this.mockMvc.perform(get("/medico/1"))
            .andDo(print())
            .andExpect(status().is2xxSuccessful())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.dni").value("4444A"))
            .andExpect(jsonPath("$.especialidad").value("Radiografo"))
            .andExpect(jsonPath("$.nombre").value("Alonso"));
    }

    @Test
    @DisplayName("Prueba de obtener un médico")
    void ObtenerMedico_Test() throws Exception {
        Medico medico = crearMedico();

        this.mockMvc.perform(get("/medico/1"))
            .andDo(print())
            .andExpect(status().is2xxSuccessful())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.dni").value(medico.getDni()));
    }
    
    @Test
    @DisplayName("Prueba de borrar un médico")
    void BorrarMedico_Test() throws Exception {
        Medico medico = crearMedico();

        this.mockMvc.perform(delete("/medico/1")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(medico)))
            .andExpect(status().isOk())
            .andExpect(status().is2xxSuccessful());
        
        this.mockMvc.perform(get("/medico/1"))
            .andDo(print())
            .andExpect(status().is5xxServerError());
    }
}
