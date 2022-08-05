package gsampaio.desafio.utils;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Setter
@Getter
@Embeddable
public class VotoId implements Serializable {

    private Integer associadoId;

    private Integer pautaId;

    public VotoId() {}

    public VotoId(Integer associadoId, Integer pautaId) {
        this.associadoId = associadoId;
        this.pautaId = pautaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VotoId votoId = (VotoId) o;

        if (!Objects.equals(associadoId, votoId.associadoId)) return false;
        return Objects.equals(pautaId, votoId.pautaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(associadoId, pautaId);
    }
}
