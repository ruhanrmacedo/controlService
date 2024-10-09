package macedos.controlservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import macedos.controlservice.dto.tecnico.CadastroTecnicoDTO;
import macedos.controlservice.dto.tecnico.DemitirTecnicoDTO;
import macedos.controlservice.dto.tecnico.EditarTecnicosDTO;

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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataAdmissao;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name = "data_desligamento")
    private LocalDate dataDesligamento;


    public Tecnico(CadastroTecnicoDTO dados) {
        this.nome = dados.nome();
        this.cpf = dados.cpf();
        this.login = dados.login();
        this.placa = dados.placa();
        this.dataAdmissao = dados.dataAdmissao();
        this.dataDesligamento = dados.dataDesligamento();
    }

    public void atualizarInformacoes(EditarTecnicosDTO dados) {
        if(dados.nome() != null) {
            this.nome = dados.nome();
        }
        if(dados.cpf() != null) {
            this.cpf = dados.cpf();
        }
        if(dados.login() != null) {
            this.login = dados.login();
        }
        if(dados.placa() != null) {
            this.placa = dados.placa();
        }
    }

    public void desligarTecnico(DemitirTecnicoDTO dados) {
        if(dados.dataDesligamento() != null) {
            this.dataDesligamento = dados.dataDesligamento();
        }
    }

    public void readmitirTecnico() {
        this.dataAdmissao = LocalDate.now();
        this.dataDesligamento = null;
    }

}
