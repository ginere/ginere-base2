package spoonapps.util.properties.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spoonapps.util.config.FileConfig;
import spoonapps.util.exception.ApplicationException;
import spoonapps.util.module.AbstractModule;
import spoonapps.util.runtimechecks.RuntimeCheckResult;

public class FilePropertiesImpl extends AbstractModule implements GlobalPropertiesInterface{
	
	public static final Logger log = LoggerFactory.getLogger(FilePropertiesImpl.class);

	private static final String SVN_HEADER_STRING = "$Header$";

	
	private static final ConcurrentHashMap<String, String> EMPTY_MAP =new ConcurrentHashMap<String, String>(0);

	private static final String DEFAULT_PATH = "/etc";

	private static final String DEFAULT_FILE_NAME = "GlobalProperties.prop";
	
	final File fildes;

	private FilePropertiesImpl(File file) throws ApplicationException{
		this.fildes=file;
	}
	

	File getFildes() {
		return fildes;
	}
	
	public static FilePropertiesImpl getFromFile(File file) throws ApplicationException{
		if (file==null){
            throw new ApplicationException("The file is null");
        } else if (!file.exists()){
            throw new ApplicationException(String.format("The file: %s, does not exists.",file.getAbsolutePath()));
        } else if (!file.canRead()){
            throw new ApplicationException(String.format("The file: %s, is not readeable.",file.getAbsolutePath()));
        } else {
        	try {
        		load(file);
        		
        		return new FilePropertiesImpl(file);
        	}catch(ApplicationException e){
        		 throw new ApplicationException(String.format("While reading the properties of the fileT: %s.",file.getAbsolutePath()));
        	}
        }
	}
	
	public static FilePropertiesImpl getDefaultFromFilePath(String fileName) throws ApplicationException{
		return getFromFilePath(fileName);
	}
	public static FilePropertiesImpl getFromFilePath(String filePath) throws ApplicationException{
		
		if (StringUtils.isEmpty(filePath)){
			filePath=DEFAULT_PATH+DEFAULT_FILE_NAME;
			log.warn(String.format("No file name passed using default value:%s",filePath));
		}
		
		File file=FileConfig.SINGLETON.getConfigurationFile(filePath, null);
		
		if (file!=null){
			return new FilePropertiesImpl(file);
		} else {
			throw new ApplicationException(String.format("The default file %s can not be found, you can speficy the file usind the FileConfig paths: %s",filePath,FileConfig.SINGLETON.getConfigurationPath()));
		}
//		File file=new File(filePath);
//		
//		if (file.exists() && file.canRead()) {
//			return new FilePropertiesImpl(file);
//		} else {		
//			log.warn(String.format("The default file %s can not be found, you can speficy the file usion the file property: %s",filePath,getSystemProperyName()));
//			return null;
//		}		
	}
	
//	private static String getSystemProperyName() {
//		return GlobalProperties.class.getName()+".DefaultPath";
//	}

//	public static FilePropertiesImpl getDefaultFromFileName(String fileName2) throws ApplicationException{
//		
//		if (StringUtils.isEmpty(fileName)){
//			log.warn(String.format("No file name passed using default value:%s",DEFAULT_FILE_NAME));
//			fileName=DEFAULT_FILE_NAME;
//		}
//		String systemPropertyName=getSystemProperyName();
//		
//		String defaultPath=System.getProperty(systemPropertyName);
//		
//		if (StringUtils.isEmpty(defaultPath)){
//			defaultPath=DEFAULT_PATH;
//			log.warn(String.format("No path defined in system property, %s, using default value:%s",systemPropertyName,DEFAULT_PATH));
//		}
//		
//		if (defaultPath.endsWith(File.separator)){
//			defaultPath+=fileName;
//		} else {
//			defaultPath=defaultPath+File.separator+fileName;
//		}
//		
//		return getDefaultFromFilePath(defaultPath);
//	}
	
	public static FilePropertiesImpl getDefault() throws ApplicationException{
		return getFromFilePath(DEFAULT_FILE_NAME);
	}

	@Override
	public long getLastModified() {
		return fildes.lastModified();
	}


//	public static ConcurrentHashMap<String, String> load(File fildes) throws ApplicationException {
//        if (fildes!=null) {
//        	try {
//	            InputStream in = new FileInputStream(fildes);
//	            try {
//	                Properties props = new Properties();
//	                props.load(in);
//	                
//	                ConcurrentHashMap<String, String> ret=new ConcurrentHashMap<String, String>(props.size());
//	                
//					Enumeration<?> e = props.propertyNames();
//					while (e.hasMoreElements()) {
//						String key = (String) e.nextElement();
//						String value = (String)props.get(key);
//						
//						ret.put(key,value);
//					}
//	
//	                return ret;
//	            }catch(IOException e){
//	                throw new ApplicationException(String.format("While loading file:%s", fildes.getAbsolutePath()),e);
//	            }finally {
//	                IOUtils.closeQuietly(in);
//	            }
//        	}catch(FileNotFoundException e){
//        		 throw new ApplicationException(String.format("While loading file:%s", fildes.getAbsolutePath()),e);
//        	}
//        } else {
//        	 throw new ApplicationException("The file is not defined.");
//        }
//	}
	
	public static Map<String, String> load(File fildes) throws ApplicationException {
        if (fildes!=null) {
        	try {
	            InputStream in = new FileInputStream(fildes);
	            try {
	                Properties props = new Properties();
	                props.load(in);
	                
	                HashMap<String, String> ret=new HashMap<String, String>(props.size());
	                
					Enumeration<?> e = props.propertyNames();
					while (e.hasMoreElements()) {
						String key = (String) e.nextElement();
						String value = (String)props.get(key);
						
						ret.put(key,value);
					}
	
					
//					for (String name: ret.keySet()){
//
//			            String key =name.toString();
//			            String value = ret.get(name).toString();  
//			            log.info("++++++++++++ File IMpl:'"+key + "': '" + value+"'");  
//
//
//					} 
					
	                return ret;
	            }catch(IOException e){
	                throw new ApplicationException(String.format("While loading file:%s", fildes.getAbsolutePath()),e);
	            }finally {
	                IOUtils.closeQuietly(in);
	            }
        	}catch(FileNotFoundException e){
        		 throw new ApplicationException(String.format("While loading file:%s", fildes.getAbsolutePath()),e);
        	}
        } else {
        	 throw new ApplicationException("The file is not defined.");
        }
	}
	
//	@Override
//	public ConcurrentHashMap<String, String> getAll()  {
//        if (fildes!=null) {
//        	try {
//            	return load(fildes);
//        	}catch(Throwable e){
//                log.warn(String.format("While loafing file:%s", fildes.getAbsolutePath()),e);
//        	}
//        } else {
//            log.warn("The file isnot defined.");
//        }
//
//        return EMPTY_MAP;       
//	}

	
	@Override
	public Map<String, String> getAll()  {
        if (fildes!=null) {
        	try {
            	return load(fildes);
        	}catch(Throwable e){
                log.warn(String.format("While loafing file:%s", fildes.getAbsolutePath()),e);
        	}
        } else {
            log.warn("The file isnot defined.");
        }

        return EMPTY_MAP;       
	}
	@Override
	protected RuntimeCheckResult check(RuntimeCheckResult ret) {	
		if (fildes == null){
			ret.addError("The file storing the properties is not defined.");
		} else {
			try {
				load(fildes);
			} catch (ApplicationException e) {
				ret.addError("Error loading file", e);
			}
		}
		return ret;
	}

	@Override
	protected String getSvnHeaderString() {
		return SVN_HEADER_STRING;
	}
}
