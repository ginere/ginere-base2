package spoonapps.util.file;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spoonapps.util.exception.ApplicationException;

public class Fileutils {

	public static final Logger log = LoggerFactory.getLogger(Fileutils.class);

//	public static File canWriteDir(String dirPath) {
//		if (StringUtils.isBlank(dirPath)) {
//			log.warn("The directory passed is blank");
//			return null;
//		} else {
//			File dir = new File(dirPath);
//
//			return canWriteDir(dir);
//		}
//	}


	
//	public static String getSafeFileName(String name,String defaultValue){
//		if (StringUtils.isBlank(name)){
//			return defaultValue;
//		} else {
//			
//			String textWithAccents = Normalizer.normalize(name, Normalizer.Form.NFD);
//			
//			return textWithAccents.replaceAll("\\p{InCombiningDiacriticalMarks}+","");
//		}
//		
//	}
	
	public static void canWriteIntoDirectoryException(File dir) throws ApplicationException{
		String ret=canWriteDir(dir);

		if (ret == null){
			return ;
		} else {
			throw new ApplicationException(ret);
		}
	}

	public static String canWriteDir(File dir) {
        if (dir==null){
            return "The directory passed is null";
        } else {        	
        	if (!dir.exists()){
                return "The directory passed:"+dir.getAbsolutePath()+" does not exits.";
        	} else if (!dir.isDirectory()){
                return "The directory passed:"+dir.getAbsolutePath()+" is not a directory.";
        	} else if (!dir.canWrite()){
                return "The directory passed:"+dir.getAbsolutePath()+" can not be written.";
        	} else {
                return null;
            }        	
        }
	}

//	public static File canReadDir(String dirPath) {
//		if (StringUtils.isBlank(dirPath)) {
//			log.warn("The directory passed is blank");
//			return null;
//		} else {
//			File dir = new File(dirPath);
//
//			return canReadDir(dir);
//		}
//	}
	
	public static String canReadDir(File dir) {
        if (dir==null){
            return "The directory passed is null";
        } else {
        	
        	if (!dir.exists()){
                return "The directory passed:"+dir.getAbsolutePath()+" does not exits.";
        	} else if (!dir.isDirectory()){
                return "The directory passed:"+dir.getAbsolutePath()+" is not a directory.";
        	} else if (!dir.canRead()){
                return "The directory passed:"+dir.getAbsolutePath()+" can not be readed.";
        	} else {
                return null;
            }        	
        }
	}



}
