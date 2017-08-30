package spoonapps.module.dao;

import java.util.List;

import spoonapps.util.exception.ApplicationException;

public interface AbstractKeyDAOInterface<T extends KeyBean> extends AbstractDAOInterface{

//	public List<T> getAll() throws ApplicationException;

	public void delete(String id) throws ApplicationException;

	public void update(T obj) throws ApplicationException;

	public void create(T obj) throws ApplicationException;

	public T get(String id) throws ApplicationException;

	public T get(String id, T obj) throws ApplicationException;
//	public T get(String id, T obj) ;
	
	public boolean exists(String id) throws ApplicationException;

	public List<T> getAll();
}
