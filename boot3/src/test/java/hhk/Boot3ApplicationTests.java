package hhk;

import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import com.querydsl.core.BooleanBuilder;

import hhk.domain.Board;
import hhk.domain.QBoard;
import hhk.persistence.BoardRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Boot3ApplicationTests {
	
	@Autowired
	private BoardRepository repo;
	
	@Test
	public void testInsert200() {
		for (int i = 1; i<=200; i++) {
			Board board = new Board();
			board.setTitle("제목.." + i);
			board.setContent("내용 ....." + i + "채우기");
			board.setWriter("user" + (i));
			repo.save(board);
		}
	}
	
	@Test
	public void testByTitle() {
		repo.findBoardByTitle("제목...177").forEach(board -> System.out.println(board));
	}
	
	@Test
	public void testByWriter() {
		
		Collection<Board> results = repo.findByWriter("user11");
		
		results.forEach(board -> System.out.println(board));
	}
	
	@Test
	public void testByWriterContaining() {
		
		Collection<Board> results = repo.findByWriterContaining("5");
		
		results.forEach(board -> System.out.println(board));
	}
	
	@Test
	public void testByWriterLike() {
		
		Collection<Board> results = repo.findByWriterLike("5");
		
		results.forEach(board -> System.out.println(board));
	}
	
	@Test
	public void testByTitleAndBno() {
		
		Collection<Board> results = repo.findByTitleContainingAndBnoGreaterThan("5", 50L);
		
		results.forEach(board -> System.out.println(board));
	}
	
	@Test
	public void testBnoOrderBy() {
		Collection<Board> results = repo.findByBnoGreaterThanOrderByBnoDesc(80L);
		
		results.forEach(board -> System.out.println(board));
	}
	
	@Test
	public void testBnoOrderByPaging() {
		
		Pageable paging = new PageRequest(0, 10);
		
		Collection<Board> results = repo.findByBnoLessThanOrderByBnoDesc(50L, paging);
		
		results.forEach(board -> System.out.println(board));
	}
	
	@Test
	public void testBnoPagingSort() {
		
		Pageable paging = new PageRequest(0, 10, Sort.Direction.ASC, "bno");
		
		Page<Board> result = repo.findByBnoGreaterThan(100L, paging);
		
		System.out.println("PAGE SIZE : " + result.getSize());
		System.out.println("TOTAL PAGES: "  + result.getTotalPages());
		System.out.println("TOTAL COUNT : " + result.getTotalElements());
		System.out.println("NEXT : " + result.nextPageable());
		
		List<Board> list = result.getContent();
		
		list.forEach(board -> System.out.println(board));
	}
	
	@Test
	public void testByTitle2() {
		repo.findByTitle("17").forEach(board -> System.out.println(board));
	}

	@Test
	public void testPredicate() {
		
		String type = "t";
		String keyword = "17";
		
		BooleanBuilder builder = new BooleanBuilder();
		
		QBoard board = QBoard.board;
		
		if(type.equals("t")) {
			builder.and(board.title.like("%" + keyword + "%"));
		}
		
		//bno > 0
		builder.and(board.bno.gt(0L));
		
		Pageable pageable = new PageRequest(0,10);
		
		Page<Board> result = repo.findAll(builder, pageable);
		
		System.out.println("PAGE SIZE : " + result.getSize());
		System.out.println("TOTAL PAGES: "  + result.getTotalPages());
		System.out.println("TOTAL COUNT : " + result.getTotalElements());
		System.out.println("NEXT : " + result.nextPageable());
		
		List<Board> list = result.getContent();
		
		list.forEach(b -> System.out.println(b));
	}

}
