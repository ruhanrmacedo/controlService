package macedos.controlservice.repository;

import macedos.controlservice.entity.Servico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServicoRepository extends JpaRepository<Servico, Long> {

    Page<Servico> findByAtivoTrue(Pageable paginacao);

    Page<Servico> findAllByOrderByIdServicoDesc(Pageable paginacao);
}
