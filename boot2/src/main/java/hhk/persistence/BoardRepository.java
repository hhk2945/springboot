package hhk.persistence;

import org.springframework.data.repository.CrudRepository;
import hhk.domain.Board;

public interface BoardRepository extends CrudRepository<Board, Long>{
	
}