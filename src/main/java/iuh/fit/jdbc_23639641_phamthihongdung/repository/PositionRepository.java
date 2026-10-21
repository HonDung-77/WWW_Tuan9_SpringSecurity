package iuh.fit.jdbc_23639641_phamthihongdung.repository;

import iuh.fit.jdbc_23639641_phamthihongdung.model.Position;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PositionRepository extends CrudRepository<Position, Integer> {
    @Override
    List<Position> findAll();
}