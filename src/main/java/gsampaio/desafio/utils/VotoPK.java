package gsampaio.desafio.utils;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class VotoPK implements Serializable {

  @Column(name = "associadoId", nullable = false)
  private Integer associadoId;

  @Column(name = "pautaId", nullable = false)
  private Integer pautaId;

  public VotoPK() {
  }

  public VotoPK(Integer associadoId, Integer pautaId) {
    this.associadoId = associadoId;
    this.pautaId = pautaId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    VotoPK votoPK = (VotoPK) o;

    if (!Objects.equals(associadoId, votoPK.associadoId)) return false;
    return Objects.equals(pautaId, votoPK.pautaId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(associadoId, pautaId);
  }
}
