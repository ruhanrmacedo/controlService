package macedos.controlservice.tecnico;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Table(name= "tecnicos")
@Entity(name = "Tecnico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "idTecnico")
public class Tecnico {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTecnico;
    private String nome;
    private String cpf;
    private String login;
    private String placa;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dataAdmissao;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "data_desligamento")
    private LocalDate dataDesligamento;


    public Tecnico(DadosCadastroTecnico dados) {
        this.nome = dados.nome();
        this.cpf = dados.cpf();
        this.login = dados.login();
        this.placa = dados.placa();
        this.dataAdmissao = dados.dataAdmissao();
        this.dataDesligamento = dados.dataDesligamento();
    }

}
