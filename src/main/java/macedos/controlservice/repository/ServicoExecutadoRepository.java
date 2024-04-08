package macedos.controlservice.repository;

import macedos.controlservice.dto.comissaoTecnico.EvolucaoValorDTO;
import macedos.controlservice.entity.ServicoExecutado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ServicoExecutadoRepository extends JpaRepository<ServicoExecutado, Long > {

    Page<ServicoExecutado> findAllByOrderByData(Pageable paginacao);

    @Query("SELECT se FROM ServicoExecutado se WHERE " +
            "MONTH(se.data) = :mes AND YEAR(se.data) = :ano")
    List<ServicoExecutado> encontrarPorMesEAno(int mes, int ano);

    @Query("SELECT COUNT(se) FROM ServicoExecutado se WHERE se.tecnico.idTecnico = :tecnicoId " +
            "AND EXTRACT(MONTH FROM se.data) = :mes " +
            "AND EXTRACT(YEAR FROM se.data) = :ano")
    int contarServicosPorTecnicoMesEAno(Long tecnicoId, int mes, int ano);

    @Query("SELECT SUM(se.servico.valorMacedo) FROM ServicoExecutado se WHERE se.tecnico.idTecnico = :tecnicoId " +
            "AND EXTRACT(MONTH FROM se.data) = :mes " +
            "AND EXTRACT(YEAR FROM se.data) = :ano")
    BigDecimal somarValorMacedoPorTecnicoMesEAno(Long tecnicoId, int mes, int ano);

    // Buscar serviços executados por técnico, mês e ano
    @Query("SELECT se FROM ServicoExecutado se WHERE " +
            "EXTRACT(MONTH FROM se.data) = :mes AND " +
            "EXTRACT(YEAR FROM se.data) = :ano AND " +
            "se.tecnico.idTecnico = :tecnicoId")
    List<ServicoExecutado> encontrarPorTecnicoMesEAno(@Param("tecnicoId") Long tecnicoId,
                                                      @Param("mes") int mes,
                                                      @Param("ano") int ano);

    // Soma dos valores Claro para um técnico em um mês e ano específicos
    @Query("SELECT SUM(se.servico.valorClaro) FROM ServicoExecutado se WHERE " +
            "se.tecnico.idTecnico = :tecnicoId AND " +
            "EXTRACT(MONTH FROM se.data) = :mes AND " +
            "EXTRACT(YEAR FROM se.data) = :ano")
    BigDecimal somarValorClaroPorTecnicoMesEAno(@Param("tecnicoId") Long tecnicoId,
                                                @Param("mes") int mes,
                                                @Param("ano") int ano);

    // Para calcular a evolução do valor ao longo do tempo, você pode precisar de uma série de queries,
    // dependendo de como você quer representar a evolução (diária, mensal, etc.). Aqui está um exemplo mensal:
    @Query("SELECT new macedos.controlservice.dto.comissaoTecnico.EvolucaoValorDTO(" +
            "EXTRACT(MONTH FROM se.data), " +
            "EXTRACT(YEAR FROM se.data), " +
            "SUM(se.servico.valorMacedo)) " +
            "FROM ServicoExecutado se WHERE " +
            "se.data BETWEEN :inicio AND :fim AND " +
            "se.tecnico.idTecnico = :tecnicoId " +
            "GROUP BY EXTRACT(MONTH FROM se.data), EXTRACT(YEAR FROM se.data) " +
            "ORDER BY EXTRACT(YEAR FROM se.data), EXTRACT(MONTH FROM se.data)")
    List<EvolucaoValorDTO> buscarEvolucaoValorMensalPorTecnico(@Param("tecnicoId") Long tecnicoId,
                                                               @Param("inicio") LocalDate inicio,
                                                               @Param("fim") LocalDate fim);


}
