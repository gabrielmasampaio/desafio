package gsampaio.desafio.utils;


public class Validacoes {

  private Validacoes() {
  }

  public static void cpfValido(String cpf) {
    if (cpf.replaceAll("\\D", "").length() != 11)
      throw new RuntimeException("Erro: cpf inválido");
  }

}
