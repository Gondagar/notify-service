package club.renthash.notify.repository;

import club.renthash.notify.model.Block;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockReposotory extends JpaRepository<Block, Long> {

}
