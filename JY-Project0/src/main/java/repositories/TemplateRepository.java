package repositories;

import java.util.List;

public interface TemplateRepository<T> {
	
	// CREATE
	public T add(T t);
	
	// READ
	public T getById(Integer id);
	
	public List<T> getAll();
	
	// UPDATE
	public boolean update(T t);
	
	// DELETE
	public boolean delete(T t);
}
