package macedos.controlservice.repository;

import macedos.controlservice.entity.ServicoExecutado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicoExecutadoRepository extends JpaRepository<ServicoExecutado, Long > {

    Page<ServicoExecutado> findAllByOrderByData(Pageable paginacao);

}
