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

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "valor1")
    private Double valor1;

    @Column(name = "valor2")
    private Double valor2;

    @Column(name = "tipo_servico")
    @Enumerated(EnumType.STRING)
    private TipoServico tipoServico;

    @Column(name = "ativo")
    private boolean ativo;

    public Servico(CadastroServicoDTO dados) {
        this.ativo = true;
        this.descricao = dados.descricao();
        this.valor1 = dados.valor1();
        this.valor2 = dados.valor2();
        this.tipoServico = dados.tipoServico();
    }

    public void atualizarInformacoes(EditarServicosDTO dados) {
        if (dados.descricao() != null) {
            this.descricao = dados.descricao();
        }
        if (dados.valor1() != null) {
            this.valor1 = dados.valor1();
        }
        if (dados.valor2() != null) {
            this.valor2 = dados.valor2();
        }
        if (dados.tipoServico() != null) {
            this.tipoServico = dados.tipoServico();
        }
    }

    public void excluir() {
        this.ativo = false;
    }
}
