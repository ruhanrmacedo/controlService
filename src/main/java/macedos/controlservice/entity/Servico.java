package macedos.controlservice.entity;

import jakarta.persistence.*;
import lombok.*;
import macedos.controlservice.dto.servico.CadastroServicoDTO;
import macedos.controlservice.dto.servico.EditarServicosDTO;
import macedos.controlservice.enums.TipoServico;

@Table(name= "servicos")
@Entity(name = "Servico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "idServico")
public class Servico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idServico;
    private String descricao;
    private Double valorClaro;
    private Double valorMacedo;
    private TipoServico tipoServico;
    private boolean ativo;

    public Servico(CadastroServicoDTO dados) {
        this.ativo = true;
        this.descricao = dados.descricao();
        this.valorClaro = dados.valorClaro();
        this.valorMacedo = dados.valorMacedo();
        this.tipoServico = dados.tipoServico();
    }

    public void atualizarInformacoes(EditarServicosDTO dados) {
        if (dados.descricao() != null) {
            this.descricao = dados.descricao();
        }
        if (dados.valorClaro() != null) {
            this.valorClaro = dados.valorClaro();
        }
        if (dados.valorMacedo() != null) {
            this.valorMacedo = dados.valorMacedo();
        }
    }

    public void excluir() {
        this.ativo = false;
    }
}
