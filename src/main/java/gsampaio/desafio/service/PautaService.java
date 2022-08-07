package gsampaio.desafio.service;

import gsampaio.desafio.model.Pauta;
import gsampaio.desafio.repository.PautaRepository;
import gsampaio.desafio.utils.SituacaoEnum;
import gsampaio.desafio.utils.Validacoes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

@Service
public class PautaService {

  @Autowired
  private PautaRepository pautaRepository;

  @Autowired
  private AssociadoService associadoService;

  public Pauta criarPauta(String cpf, Pauta pauta) {
    Pauta pautaCriada;

    Validacoes.cpfValido(cpf);
    pauta.setSituacaoEnum(SituacaoEnum.CRIADA);
    pauta.setAssociado(associadoService.buscaAssociado(cpf));
    validaPauta(pauta);
    try {
      pautaCriada = pautaRepository.save(pauta);
    } catch (Exception e) {
      throw new RuntimeException("Erro: pauta com esse nome já cadastrada");
    }

    return pautaCriada;
  }

  public Pauta buscaPauta(String nomePauta) {
    Pauta pauta = pautaRepository.findByNome(nomePauta);
    if (Objects.isNull(pauta))
      throw new RuntimeException("Erro: pauta com esse nome não encontrada");
    return pauta;
  }

  public Pauta iniciarVotacao(String nomePauta, Integer tempoSessao) {
    Pauta pauta = buscaPauta(nomePauta);
    try {
      pauta.setSituacaoEnum(SituacaoEnum.VOTACAO_ABERTA);
      pautaRepository.save(pauta);
    } catch (Exception e) {
      throw new RuntimeException("Erro: erro ao salvar a situacao da pauta");
    }
    Timer timer = new Timer("Timer");
    timer.schedule(new EncerraVotacaoTask(pauta), Objects.isNull(tempoSessao) ? (60 * 1000) : (tempoSessao * 60 * 1000));
    return pauta;
  }

  public Pauta updatePauta(Pauta pauta) {
    try {
      pauta = pautaRepository.save(pauta);
    } catch (Exception e) {
      throw new RuntimeException("Erro: erro ao salvar pauta");
    }
    return pauta;
  }

  private void validaPauta(Pauta pauta) {
    if (pauta.getNome().isEmpty())
      throw new RuntimeException("Erro: nome da pauta não pode estar em branco");
    else if (pauta.getNome().length() > 50)
      throw new RuntimeException("Erro: nome da pauta não pode ultrapassar 50 caracteres");
  }

  private class EncerraVotacaoTask extends TimerTask {

    Pauta pauta;

    EncerraVotacaoTask(Pauta pauta) {
      this.pauta = pauta;
    }

    @Override
    public void run() {
      this.pauta.setSituacaoEnum(SituacaoEnum.VOTACAO_ENCERRADA);
      pautaRepository.save(pauta);
    }
  }
}
