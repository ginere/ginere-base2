package spoonapps.util.files;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Fileutils {

	public static final Logger log = LoggerFactory.getLogger(Fileutils.class);

	public static File canWriteDir(String dirPath) {
		if (StringUtils.isBlank(dirPath)) {
			log.warn("The directory passed is blank");
			return null;
		} else {
			File dir = new File(dirPath);

			return canWriteDir(dir);
		}
	}

	
	public static File canReadDir(String dirPath) {
		if (StringUtils.isBlank(dirPath)) {
			log.warn("The directory passed is blank");
			return null;
		} else {
			File dir = new File(dirPath);

			return canReadDir(dir);
		}
	}
	
	public static File canReadDir(File dir) {
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


	public static File canWriteDir(File dir) {
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
        	} else if (!dir.canWrite()){
        		log.warn(String.format("The directory passed:%s can not be readed.",dir.getAbsolutePath()));
                return null;
        	} else {
                return dir;
            }        	
        }
	}

}
