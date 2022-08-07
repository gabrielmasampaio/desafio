package gsampaio.desafio.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import gsampaio.desafio.model.Associado;
import gsampaio.desafio.service.AssociadoService;
import gsampaio.desafio.service.VotoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AssociadoController.class)
public class AssociadoControllerTest {

  @Autowired
  MockMvc mockMvc;
  @Autowired
  ObjectMapper objectMapper;

  @MockBean
  AssociadoService associadoService;
  @MockBean
  VotoService votoService;

  Associado associado1 = new Associado(1, "Associado1", "11111111111");
  Associado associado2 = new Associado(2, "Associado2", "22222222222");

  private final String associadoPath = "/api/associado/";

  @Test
  @DisplayName("Busca associado corretamente")
  void buscaAssociado() throws Exception {
    Mockito.when(associadoService.buscaAssociado("11111111111")).thenReturn(associado1);
    mockMvc.perform(MockMvcRequestBuilders
                    .get(associadoPath + "11111111111")
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
            .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Associado1"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value("11111111111"));
  }

  @Test
  @DisplayName("Cria associado corretamente")
  void criarAssociado() throws Exception {
    Associado associadoRequest = new Associado(null, "Associado Criado", "22222222222");
    Mockito.when(associadoService.criaAssociado(associadoRequest)).thenReturn(associado2);
    mockMvc.perform(MockMvcRequestBuilders
                    .post(associadoPath)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(associadoRequest))
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());
  }

  @Test
  @DisplayName("Cadastra Voto do usuario")
  void cadastraVoto() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders
                    .post(associadoPath + "voto/{nomePauta}/{cpf}/{tipoVoto}", "pauta", "11111111111", "Sim")
                    .accept(MediaType.TEXT_PLAIN_VALUE))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("Voto cadastrado com sucesso"));
  }

  public static String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
