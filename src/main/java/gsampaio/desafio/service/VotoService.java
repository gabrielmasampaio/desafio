package gsampaio.desafio.service;

import gsampaio.desafio.model.Pauta;
import gsampaio.desafio.model.Voto;
import gsampaio.desafio.repository.VotoRepository;
import gsampaio.desafio.utils.SituacaoEnum;
import gsampaio.desafio.utils.TipoVoto;
import gsampaio.desafio.utils.VotoPK;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VotoService {

    @Autowired
    private VotoRepository votoRepository;

    @Autowired
    private PautaService pautaService;

    @Autowired
    private AssociadoService associadoService;

    public void cadastraVoto(String nomePauta, String cpf, String tipoVoto){
        Voto voto = new Voto();
        voto.setPauta(pautaService.buscaPauta(nomePauta));
        voto.setAssociado(associadoService.buscaAssociado(cpf));
        voto.setTipoVoto(converteTipoVoto(tipoVoto));
        voto.setId(new VotoPK(voto.getAssociado().getId(), voto.getPauta().getId()));

        if(!voto.getPauta().getSituacaoEnum().equals(SituacaoEnum.VOTACAO_ABERTA))
            throw new RuntimeException("Erro: Esta pauta nao esta disponivel para votacao");

        try {
            votoRepository.save(voto);
        } catch (Exception e) {
            throw new RuntimeException("Erro: Voce ja votou nessa pauta");
        }
    }

    public Pauta contabilizaVotacao(String nomePauta){
        Integer votosSim = 0;
        Integer votosNao = 0;
        Pauta pauta = pautaService.buscaPauta(nomePauta);

        if(!pauta.getSituacaoEnum().equals(SituacaoEnum.VOTACAO_ENCERRADA))
            throw new RuntimeException("Erro: Está pauta está no status " + pauta.getSituacaoEnum().toString());

        try {
            votosSim = votoRepository.countByPautaAndTipoVoto(pauta, TipoVoto.SIM);
            votosNao = votoRepository.countByPautaAndTipoVoto(pauta, TipoVoto.NAO);
        } catch (Exception e) {
            throw new RuntimeException("Erro: Erro inesperado ao contar os votos");
        }

        if(votosSim > votosNao)
            pauta.setSituacaoEnum(SituacaoEnum.APROVADA);
        else if(votosNao > votosSim)
            pauta.setSituacaoEnum(SituacaoEnum.REPROVADA);
        else
            pauta.setSituacaoEnum(SituacaoEnum.EMPATADA);

        return pautaService.updatePauta(pauta);
    }

    public TipoVoto converteTipoVoto(String voto){
        if(voto.equals("Sim"))
            return (TipoVoto.SIM);
        else if (voto.equals("Não"))
            return TipoVoto.NAO;
        else
            throw new RuntimeException("Erro: Tipo de voto inválido");
    }


}
