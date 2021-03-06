package spoonapps.util.enumeration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * @version $Id$
 * 
 * This allows the creation of enum with some nice functionalities like:
 * This can be used with == operator.
 * This can also be used safelly as keys of Maps, ....
 * We can have the list of all the enums of one type.
 * Warning on serialization, the serialVersionUID must be the same
 */
public abstract class AppEnum implements Serializable {
	/**
	 * This is the ID based on the file Id
	 */
	private static final long serialVersionUID = "$Id$".hashCode();

	public static final Logger log = LoggerFactory.getLogger(AppEnum.class);
	
	private static long lastModified=System.currentTimeMillis();

	static private final Hashtable<Class<? extends AppEnum>,List<AppEnum>> valuesList=new Hashtable<Class<? extends AppEnum>,List<AppEnum>>();

	static private final Hashtable<Class<? extends AppEnum>,Map<String,AppEnum>> valuesCache=new Hashtable<Class<? extends AppEnum>,Map<String,AppEnum>>();

	
	protected final String id;
	@XmlTransient
	protected final String name;
	@XmlTransient
	protected final String description;	
	private final Class<? extends AppEnum> clazz;

	protected AppEnum(String id,String name,String description,Class<? extends AppEnum> clazz){
		this.id=id;
		this.name=name;
		this.description=description;
		this.clazz=clazz;

		init();
	}


	protected AppEnum(String id,String name,String description){
		this.id=id;
		this.name=name;
		this.description=description;
		this.clazz=this.getClass();
		
		init();
	}
	
	private void init(){		
		synchronized(valuesList){
			if (!valuesList.containsKey(clazz)){
				List <AppEnum>list=new ArrayList<AppEnum>();
				valuesList.put(clazz,list);
				
				Map<String,AppEnum> map=new Hashtable<String,AppEnum>();
				valuesCache.put(clazz,map);
			}
		}
		
		Map<String,AppEnum> map=valuesCache.get(clazz);
		if (!map.containsKey(id)){
			map.put(id,this);	
			
			List <AppEnum> list=valuesList.get(clazz);
			list.add(this);
		} 
		
		lastModified=System.currentTimeMillis();
	}
	
	
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
	
	public String toString() {
		return id;
	}
	
	public static AppEnum fromString(Class<? extends AppEnum> clazz,String value){
		AppEnum ret=value(clazz, value, null);

//		if (ret==null){
//			throw new IllegalAccessException("Can not get object od type:"+clazz.getName()+" from value:'"+value+"'");
//		} else {
//			return ret;
//		}
		return ret;
	}
	/**
	 * Hay un problema se puede llamar a este metodo antes de cargar la clase hija por lo que habria un proiblema
	 * @param clazz The class
	 * @param value The value to look for
	 * @return
	 */
	public static AppEnum value(Class<? extends AppEnum> clazz,String value,AppEnum defaultValule) {
		if (value == null) {
			return defaultValule;
		}
		
		value=value.trim();
		
		if (valuesCache.containsKey(clazz)){
			Map<String,AppEnum> map2=valuesCache.get(clazz);
			
			if (map2.containsKey(value)){
				return map2.get(value);
			}
		}

		return defaultValule;
	}

	public static List<? extends AppEnum> values(Class<? extends AppEnum> clazz) {
		if (valuesList.containsKey(clazz)){
			return valuesList.get(clazz);
		} else {
			return null;
		}
	}

	public static boolean equals(AppEnum a,AppEnum b){
		if (a==null){
			if (b==null){
				return true;
			} else {
				return false;
			}
		} if (b == null){
			return false;
		} else {
			if (!StringUtils.equals(a.id, b.id)){
				return false;
			} else {
				if (a.clazz == null){
					if (b.clazz==null){
						return true;
					} else {
						return false;
					}
				} else if (b.clazz == null){
					return false;
				} else {
					return a.clazz == b.clazz;
				}
			}
		}
	}


	public static long getLastModified() {
		return lastModified;
	}
	

	public boolean equals(Object obj){
		if (obj==null){
			return false;
		} if (obj instanceof AppEnum){
			return AppEnum.equals(this,(AppEnum)obj);
		} else {
			return false;
		}
	}
	
	public int hashCode(){
		return getId().hashCode();
	}
}
