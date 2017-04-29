package spoonapps.util.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import spoonapps.util.module.AbstractModule;
import spoonapps.util.runtimechecks.RuntimeCheckResult;

public class FileConfig extends AbstractModule {

	private static final String SVN_HEADER_STRING = "$Header$";

	private static final String SYSTEM_CONFIGURATION_PATH_PROPPERTY_NAME = FileConfig.class.getName()+".ConfigurationPath";
	private static final String DEFAULT_PATH = ".";

    private final List<String> paths=new ArrayList<String>(3);
    private final List<File> dirs=new ArrayList<File>(3);
    
    
    public static final FileConfig SINGLETON=new FileConfig();
    
	private FileConfig(){
		String dirPath=System.getProperty(SYSTEM_CONFIGURATION_PATH_PROPPERTY_NAME);
		if (StringUtils.isBlank(dirPath)){
			log.warn(String.format("The system configuration path is not defined you can use -D%s the define the configuration path to store the configuration files.",SYSTEM_CONFIGURATION_PATH_PROPPERTY_NAME));
			log.info("Using the default path:"+DEFAULT_PATH);
			dirPath=DEFAULT_PATH;
		} 
		
		File dir=verifyDir(dirPath);
        if (dir!=null){
            paths.add(dirPath);
            dirs.add(dir);
            printConfigurationPath();
        } else {
            log.error(String.format("The path:%s added in system property %s is wrong.",dirPath,SYSTEM_CONFIGURATION_PATH_PROPPERTY_NAME));
        }

		
	}
	
	public boolean addPath(String dirPath){
		if (StringUtils.isBlank(dirPath)){
			log.warn("The path passed is blank.");
			return false;
		} else {
			File dir=verifyDir(dirPath);
            if (dir!=null){
                paths.add(dirPath);
                dirs.add(dir);
                printConfigurationPath();
    			return true;
            } else {
                log.error(String.format("The path:%s is wrong.",dirPath));
    			return false;
            }
        }	
	}
	
	public File getConfigurationFile(String relativePath, File defaultValue) {
		if (StringUtils.isBlank(relativePath)) {
			log.warn("The file relative path is blank.");
			return defaultValue;
		} else if (dirs.size() == 0) {
			log.error(String.format("No configuratiosn dirs has been defined. While getting configuration file:%s",
					relativePath));
			return defaultValue;
		} else {
			for (File dir:dirs){
				File file=new File(dir, relativePath);
				if (verifyFile(file)!=null){
					log.info(String.format("Configuration file:%s found in path:%s",
							relativePath,dir.getAbsolutePath()));
					return file;
				} else {
					log.info(String.format("Configuration file:%s NOT found in path:%s",
							relativePath,dir.getAbsolutePath()));
				}			
			}
			
	    	@SuppressWarnings("unchecked")
			String string=StringUtils.join(paths);
	    	
			log.info(String.format("The configuration file:%s not found in any of the paths:%s",string,getConfigurationPath()));	
			
			return null;
		}
	}
	
	private File verifyDir(String dirPath) {
		if (StringUtils.isBlank(dirPath)) {
			log.warn("The directory passed is blank");
			return null;
		} else {
			File dir = new File(dirPath);

			return verifyDir(dir);
		}
	}
	
	private File verifyDir(File dir) {
        if (dir==null){
        	log.warn("The directory passed is null");
            return null;
        } else {
        	
        	if (!dir.exists()){
            	log.warn(String.format("The directory passed:%s does not exits.",dir.getAbsolutePath()));
                return null;
        	} else if (!dir.isDirectory()){
        		log.warn(String.format("The directory passed:%s is not a directory.",dir.getAbsolutePath()));
                return null;
        	} else if (!dir.canRead()){
        		log.warn(String.format("The directory passed:%s can not be readed.",dir.getAbsolutePath()));
                return null;
        	} else {
                return dir;
            }        	
        }
	}
	
	private File verifyFile(File file) {
        if (file==null){
        	log.warn("The file passed is null");
            return null;
        } else {
        	
        	if (!file.exists()){
            	log.info(String.format("The file passed:%s does not exits.",file.getAbsolutePath()));
                return null;
        	} else if (!file.isFile()){
        		log.warn(String.format("The file passed:%s is not a file.",file.getAbsolutePath()));
                return null;
        	} else if (!file.canRead()){
        		log.warn(String.format("The file passed:%s can not be readed.",file.getAbsolutePath()));
                return null;
        	} else {
                return file;
            }        	
        }
	}


    public String getConfigurationPath() {
    	@SuppressWarnings("unchecked")
		String string=StringUtils.join(paths);
    	StringBuilder buffer=new StringBuilder(string);
    	
		log.info(String.format("The configuration system can load the files from the following paths:%s",string));	
		int i=0;
		for (File dir:dirs){
			buffer.append(String.format("%s) %s",i++,dir.getAbsolutePath()));
		}
		
		return buffer.toString();
	}
    
    public void printConfigurationPath() {
    	@SuppressWarnings("unchecked")
		String string=StringUtils.join(paths);
    	
		log.info(String.format("The configuration system can load the files from the following paths:%s",string));	
		int i=0;
		for (File dir:dirs){
			log.info(String.format("%s) %s",i++,dir.getAbsolutePath()));
		}
	}


	@Override
	protected RuntimeCheckResult check(RuntimeCheckResult ret) {		
		if (dirs.size()==0){
			ret.addError("No configuratiosn dirs has been defined.");
		} else {
			for (File dir:dirs){
				if (verifyDir(dir)==null){
					ret.addError(String.format("The file:%s can not be verified. It does not exist, can not be readed, etc ...",dir.getAbsolutePath()));
				}
			}
		}
		
		return null;
	}

	@Override
	protected String getSvnHeaderString() {
		return SVN_HEADER_STRING;
	}

	
	
}
