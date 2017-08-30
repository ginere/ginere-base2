package spoonapps.module;

import java.util.List;

import spoonapps.module.dao.AbstractKeyDAOInterface;
import spoonapps.module.dao.KeyBean;
import spoonapps.util.exception.ApplicationException;
import spoonapps.util.runtimechecks.RuntimeCheckResult;

public abstract class AbstractKeyManager<T extends KeyBean> extends AbstractManager {

	protected final AbstractKeyDAOInterface<T> DAO;
	
	private long lastModified=System.currentTimeMillis();
	
	protected AbstractKeyManager(AbstractKeyDAOInterface<T> dao){
		this.DAO=dao;
	}


	@Override
	protected RuntimeCheckResult check(RuntimeCheckResult ret) {
		ret.add(DAO.check());	
		
		return ret;
	}
	
	public void init() throws ApplicationException {
		DAO.init();		
	}
	
	public List<T> getAll() throws ApplicationException {
		return DAO.getAll();
	}

	public void delete(String id) throws ApplicationException {
		DAO.delete(id);		
		updated();
	}
	
	public T get(String id,T defaultvallue){
		try {
			return DAO.get(id,defaultvallue);
		} catch (ApplicationException e) {
			log.warn("Object id:"+id+" returning default value:"+defaultvallue,e);
			return defaultvallue;
		}		
	}
	
	public T get(String id) throws ApplicationException {
		return DAO.get(id);		
	}

	
	public long getLastModified()  {
		return lastModified;
	}
	
	public void updated()  {
		lastModified=System.currentTimeMillis();
	}
}
