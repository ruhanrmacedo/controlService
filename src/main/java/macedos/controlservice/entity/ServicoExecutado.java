package macedos.controlservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import macedos.controlservice.dto.servicoExecutado.EditarServicoExecutadoDTO;
import macedos.controlservice.repository.ServicoRepository;
import macedos.controlservice.repository.TecnicoRepository;

import java.math.BigDecimal;
import java.time.LocalDate;

@Table(name = "servicos_executados")
@Entity(name = "ServicoExecutado")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@NamedEntityGraph(
        name = "ServicoExecutado.graph",
        attributeNodes = {
                @NamedAttributeNode("tecnico"),
                @NamedAttributeNode("servico")
        }
)
public class ServicoExecutado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String contrato;
    private String os;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate data;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tecnico_id")
    private Tecnico tecnico;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "servico_id")
    private Servico servico;

    public void atualizarInformacoes(EditarServicoExecutadoDTO dados, TecnicoRepository tecnicoRepository, ServicoRepository servicoRepository) {
        if (dados.contrato() != null) {
            this.contrato = dados.contrato();
        }
        if (dados.os() != null) {
            this.os = dados.os();
        }
        if (dados.data() != null) {
            this.data = dados.data();
        }
        if (dados.idTecnico() != null) {
            this.tecnico = tecnicoRepository.findById(dados.idTecnico()).orElse(null);
        }
        if (dados.idServico() != null) {
            this.servico = servicoRepository.findById(dados.idServico()).orElse(null);
        }
    }

}


