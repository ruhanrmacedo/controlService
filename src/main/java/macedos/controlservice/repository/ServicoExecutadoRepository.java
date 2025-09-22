package macedos.controlservice.repository;

import macedos.controlservice.entity.ServicoExecutado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

    public interface ServicoExecutadoRepository extends JpaRepository<ServicoExecutado, Long > {

        @EntityGraph(value = "ServicoExecutado.graph", type = EntityGraph.EntityGraphType.LOAD)
        // filtrar por id decrescente
        Page<ServicoExecutado> findAllByOrderByIdDesc(Pageable paginacao);

        @Query("SELECT se FROM ServicoExecutado se " +
                "JOIN FETCH se.servico " +
                "JOIN FETCH se.tecnico " +
                "WHERE MONTH(se.data) = :mes AND YEAR(se.data) = :ano " +
                "ORDER BY se.id DESC")
        List<ServicoExecutado> encontrarPorMesEAno(@Param("mes") int mes,
                                                   @Param("ano") int ano);

        //Consulta para buscar por serviços executados dentro de um intervalo de datas
        @Query("SELECT se FROM ServicoExecutado se " +
                "JOIN FETCH se.servico " +
                "JOIN FETCH se.tecnico " +
                "WHERE se.data BETWEEN :dataInicio AND :dataFim " +
                "ORDER BY se.id DESC")
        List<ServicoExecutado> encontrarPorPeriodo(@Param("dataInicio") LocalDate dataInicio,
                                                   @Param("dataFim") LocalDate dataFim);

        @Query("SELECT COUNT(se) FROM ServicoExecutado se WHERE se.tecnico.idTecnico = :tecnicoId " +
                "AND EXTRACT(MONTH FROM se.data) = :mes " +
                "AND EXTRACT(YEAR FROM se.data) = :ano")
        int contarServicosPorTecnicoMesEAno(Long tecnicoId, int mes, int ano);

        @Query("SELECT SUM(se.servico.valor2) FROM ServicoExecutado se WHERE se.tecnico.idTecnico = :tecnicoId " +
                "AND EXTRACT(MONTH FROM se.data) = :mes " +
                "AND EXTRACT(YEAR FROM se.data) = :ano")
        BigDecimal somarValor2PorTecnicoMesEAno(Long tecnicoId, int mes, int ano);

        @Query("SELECT SUM(se.valorTotal) FROM ServicoExecutado se WHERE se.tecnico.idTecnico = :tecnicoId " +
                "AND EXTRACT(MONTH FROM se.data) = :mes " +
                "AND EXTRACT(YEAR FROM se.data) = :ano")
        BigDecimal somarValorTotalPorTecnicoMesEAno(Long tecnicoId, int mes, int ano);

        // Buscar serviços executados por técnico, mês e ano
        @Query("SELECT se FROM ServicoExecutado se " +
                "JOIN FETCH se.tecnico " +
                "JOIN FETCH se.servico " +
                "WHERE EXTRACT(MONTH FROM se.data) = :mes AND " +
                "EXTRACT(YEAR FROM se.data) = :ano AND " +
                "se.tecnico.idTecnico = :tecnicoId")
        List<ServicoExecutado> encontrarPorTecnicoMesEAno(@Param("tecnicoId") Long tecnicoId,
                                                          @Param("mes") int mes,
                                                          @Param("ano") int ano);

        // Soma dos valores 1 para um técnico em um mês e ano específicos
        @Query("SELECT SUM(se.servico.valor1) FROM ServicoExecutado se WHERE " +
                "se.tecnico.idTecnico = :tecnicoId AND " +
                "EXTRACT(MONTH FROM se.data) = :mes AND " +
                "EXTRACT(YEAR FROM se.data) = :ano")
        BigDecimal somarValor1PorTecnicoMesEAno(@Param("tecnicoId") Long tecnicoId,
                                                    @Param("mes") int mes,
                                                    @Param("ano") int ano);

        // Busca a evolução do valor total mensal dos serviços executados por um técnico dentro de um intervalo de datas
        @Query("SELECT EXTRACT(MONTH FROM se.data), " +
                "EXTRACT(YEAR FROM se.data), " +
                "SUM(se.valorTotal) " +
                "FROM ServicoExecutado se WHERE " +
                "se.data BETWEEN :inicio AND :fim AND " +
                "se.tecnico.idTecnico = :tecnicoId " +
                "GROUP BY EXTRACT(MONTH FROM se.data), EXTRACT(YEAR FROM se.data) " +
                "ORDER BY EXTRACT(YEAR FROM se.data), EXTRACT(MONTH FROM se.data)")
        List<Object[]> buscarEvolucaoValorMensalPorTecnico(@Param("tecnicoId") Long tecnicoId,
                                                           @Param("inicio") LocalDate inicio,
                                                           @Param("fim") LocalDate fim);

        // Busca a evolução da quantidade de contratos executados por um técnico dentro de um intervalo de datas
        @Query("SELECT EXTRACT(MONTH FROM se.data), " +
                "EXTRACT(YEAR FROM se.data), " +
                "COUNT(se.id) " +
                "FROM ServicoExecutado se WHERE " +
                "se.data BETWEEN :inicio AND :fim " +
                "AND se.tecnico.idTecnico = :tecnicoId " +
                "GROUP BY EXTRACT(MONTH FROM se.data), EXTRACT(YEAR FROM se.data) " +
                "ORDER BY EXTRACT(YEAR FROM se.data), EXTRACT(MONTH FROM se.data)")
        List<Object[]> buscarEvoContExePorTecnico(@Param("tecnicoId") Long tecnicoId,
                                                  @Param("inicio") LocalDate inicio,
                                                  @Param("fim") LocalDate fim);

}
