package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.exceptions.SwapTransactionException;
import com.example.model.Book;
import com.example.model.Collection;
import com.example.model.Swap;
import com.example.model.User;
import com.example.repository.SwapRepository;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SwapService
{

	@Autowired
	private SwapRepository swapRepository;

	@Autowired
	private CollectionService collectionService;

	@Autowired
	private UserService userService;

	@Autowired
	private BookService bookService;

	public List<Swap> findAll() {
		return swapRepository.findAll();
	}

	public Optional<Swap> findOne(Integer id) {
		return swapRepository.findById(id);
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = SwapTransactionException.class)
	public void swapBooks(Swap entity)
	{		
		User userTo = userService.findOne( entity.getUserTo().getId() ).get();
		Book bookTo = bookService.findOne( entity.getBookTo().getId() ).get();

		User userFrom = userService.findOne( entity.getUserFrom().getId() ).get();
		Book bookFrom = bookService.findOne( entity.getBookFrom().getId() ).get();

		Collection collectionUserTo = new Collection(userTo, bookTo);
		Collection collectionUserFrom = new Collection(userFrom, bookFrom);

		collectionService.delete(collectionUserTo);
		collectionService.delete(collectionUserFrom);
		
		collectionUserTo.setBook(bookFrom);
		collectionUserFrom.setBook(bookTo);
		
		collectionService.save(collectionUserTo);
		collectionService.save(collectionUserFrom);
	}
	
	@Transactional(readOnly = false)
	public Swap save(Swap entity) {
		return swapRepository.save(entity);
	}

	@Transactional(readOnly = false)
	public void delete(Swap entity) {
		swapRepository.delete(entity);
	}

}
	
