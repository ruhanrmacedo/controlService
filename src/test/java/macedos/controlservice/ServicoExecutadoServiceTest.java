package macedos.controlservice;

import macedos.controlservice.entity.Servico;
import macedos.controlservice.entity.ServicoExecutado;
import macedos.controlservice.entity.Tecnico;
import macedos.controlservice.repository.ServicoExecutadoRepository;
import macedos.controlservice.service.ServicoExecutadoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ServicoExecutadoServiceTest {

    @Autowired
    private ServicoExecutadoService servicoExecutadoService;

    @Autowired
    private ServicoExecutadoRepository servicoExecutadoRepository;

    @BeforeEach
    public void setup() {
        // Aqui você pode configurar alguns dados de teste
        // Exemplo: Adicionar um serviço executado para o mês/ano que será testado
        Tecnico tecnico = new Tecnico(/* parâmetros do técnico */);
        Servico servico = new Servico(/* parâmetros do serviço */);
        servico.getValorClaro(); // Defina o valor claro para o serviço

        ServicoExecutado servicoExecutado = new ServicoExecutado(
                /* id */ null,
                /* contrato */ "1234",
                /* os */ "OS01",
                /* data */ LocalDate.of(2023, 1, 15), // Substitua pelo mês/ano que quer testar
                tecnico,
                servico
        );

        servicoExecutadoRepository.save(servicoExecutado);
    }

    @Test
    public void calcularValorClaroPorMesEAno_deveRetornarValorCorreto() {
        // Action
        Double valorCalculado = servicoExecutadoService.calcularValorClaroPorMesEAno(2, 2024); // Substitua pelo mês/ano dos dados de teste

        // Assert
        Double valorEsperado = 100.0; // Substitua pelo valor claro total esperado com base nos dados de teste
        assertEquals(valorEsperado, valorCalculado, "O valor claro calculado deve ser igual ao valor esperado.");
    }
}

