package macedos.controlservice.repository;

import macedos.controlservice.entity.Tecnico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TecnicoRepository extends JpaRepository<Tecnico, Long> {

    List<Tecnico> findByDataDesligamentoIsNull();

}
