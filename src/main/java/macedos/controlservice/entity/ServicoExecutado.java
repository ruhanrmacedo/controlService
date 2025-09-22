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

import java.time.LocalDate;
import java.util.List;

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
    @ManyToMany
    @JoinTable(
            name = "servicos_executados_servico_adicional",
            joinColumns = @JoinColumn(name = "servico_executado_id"),
            inverseJoinColumns = @JoinColumn(name = "servico_adicional_id")
    )
    private List<Servico> servicosAdicionais;
    @Column(name = "valor_total")
    private Double valorTotal;
    @Column(name = "nome_cliente")
    private String nomeCliente;

    @Column(name = "metragem_cabo_drop")
    private Double metragemCaboDrop;

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
        if (dados.servicosAdicionais() != null) {
            if (this.servicosAdicionais == null) {
                this.servicosAdicionais = new java.util.ArrayList<>();
            } else {
                this.servicosAdicionais.clear();
            }
            dados.servicosAdicionais().forEach(idServicoAdicional -> {
                Servico servicoAdicional = servicoRepository.findById(idServicoAdicional).orElse(null);
                if (servicoAdicional != null) {
                    this.servicosAdicionais.add(servicoAdicional);
                }
            });
        }
        if (dados.nomeCliente() != null) {
            this.nomeCliente = dados.nomeCliente();
        }
        if (dados.metragemCaboDrop() != null) {
            this.metragemCaboDrop = dados.metragemCaboDrop();
        }

        Double valorPrincipal = (this.servico != null && this.servico.getValor1() != null) ? this.servico.getValor1() : 0d;
        java.util.List<Servico> adicionais = (this.servicosAdicionais != null) ? this.servicosAdicionais : java.util.List.of();

        double valorBase = calcularValorTotal(valorPrincipal, adicionais);
        double excedente = calcularExcedenteCabo(this.metragemCaboDrop);
        this.valorTotal = valorBase + excedente;
    }

    private Double calcularValorTotal(Double valorPrincipal, List<Servico> servicosAdicionais) {
        double base = (valorPrincipal != null) ? valorPrincipal : 0d;
        double somaAdicionais = (servicosAdicionais != null ? servicosAdicionais : java.util.List.<Servico>of())
                .stream()
                .mapToDouble(s -> s.getValor1() != null ? s.getValor1() : 0d)
                .sum();
        return base + somaAdicionais;
    }

    private static final double LIMITE_METROS_CABO = 250.0;
    private static final double VALOR_POR_METRO_EXCEDENTE = 2.0;

    private double calcularExcedenteCabo(Double metragem) {
        if (metragem == null) return 0d;
        double excedente = metragem - LIMITE_METROS_CABO;
        return excedente > 0 ? excedente * VALOR_POR_METRO_EXCEDENTE : 0d;
    }

}


