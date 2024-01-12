package macedos.controlservice.repository;

import macedos.controlservice.entity.ComissaoTecnico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComissaoTecnicoRepository extends JpaRepository<ComissaoTecnico, Long> {
}
