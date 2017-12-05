package hhk.persistence;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import hhk.domain.Board;

public interface BoardRepository extends CrudRepository<Board, Long>, 
											QueryDslPredicateExecutor<Board>{
	
	public List<Board> findBoardByTitle(String title);

	//특정 칼럼 처
	public Collection<Board> findByWriter(String writer);
	
	//%검색어%
	public Collection<Board> findByWriterContaining(String writer);

	//Like
	public Collection<Board> findByWriterLike(String writer);

	//and or 조건 처리
	public Collection<Board> findByTitleContainingOrContentContaining(String title, String content);
	
	//title LIKE % ? % AND BNO > ?
	public Collection<Board> findByTitleContainingAndBnoGreaterThan(String keywoard, Long num);
	
	//bno > ? ORDER BY bno DESC
	public Collection<Board> findByBnoGreaterThanOrderByBnoDesc(Long bno);
	
	//bno < ? ORDER BY bno DESC limit ?,?
	public List<Board> findByBnoLessThanOrderByBnoDesc(Long bno, Pageable paging);

	//bno > ? ORDER By bno asc
	public Page<Board> findByBnoGreaterThan(Long bno, Pageable paging);
	
	@Query("SELECT b FROM Board b WHERE b.title LIKE %?1% AND b.bno > 0 ORDER BY b.bno DESC")
	public List<Board> findByTitle(String title);
	
	@Query("SELECT b FROM Board b WHERE b.title LIKE %:content% AND b.bno > 0 ORDER BY b.bno DESC")
	public List<Board> findByContent(@Param("content") String title);


}