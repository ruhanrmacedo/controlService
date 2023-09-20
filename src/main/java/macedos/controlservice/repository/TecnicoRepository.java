package macedos.controlservice.repository;

import macedos.controlservice.entity.Tecnico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TecnicoRepository extends JpaRepository<Tecnico, Long> {

    Page<Tecnico> findAllByOrderByNome(Pageable paginacao);

    Page<Tecnico> findByDataDesligamentoIsNull(Pageable paginacao);
}
