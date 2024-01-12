package macedos.controlservice.repository;

import macedos.controlservice.entity.IntervaloComissao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IntervaloComissaoRepository extends JpaRepository<IntervaloComissao, Long> {
}
