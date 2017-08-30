package spoonapps.module.dao;

public class KeyBean extends Bean{

	public final String id;

	protected KeyBean (String id) {
		this.id=id;
	}

	public String getId(){
		return id;
	}
	
}
