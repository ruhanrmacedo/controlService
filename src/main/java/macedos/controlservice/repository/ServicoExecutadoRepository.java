package macedos.controlservice.repository;

import macedos.controlservice.entity.ServicoExecutado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ServicoExecutadoRepository extends JpaRepository<ServicoExecutado, Long > {

    Page<ServicoExecutado> findAllByOrderByData(Pageable paginacao);

    @Query("SELECT se FROM ServicoExecutado se WHERE " +
            "MONTH(se.data) = :mes AND YEAR(se.data) = :ano")
    List<ServicoExecutado> encontrarPorMesEAno(int mes, int ano);
}
