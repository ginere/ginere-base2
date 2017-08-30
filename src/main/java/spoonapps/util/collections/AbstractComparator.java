package spoonapps.util.collections;

import java.util.Comparator;

public abstract class AbstractComparator<T> implements Comparator<T>{
    
    @Override
    public int compare(T o1, T o2) {
        if (o1 == null){
            if (o2 == null){
                return 0;
            } else {
                return 1;
            }
        } else if (o2 == null){
            return -1;
        } else {
            if (o1 == o2){
                return 0;
            } else {
                int val=innerComparator(o1,o2);
                return val;
            }
        }
    }

	protected abstract int innerComparator(T o1, T o2);
	
	
	
    @FunctionalInterface
    public interface FunctionComparatorNotNull<O> {
        public int functionCompareNotNull(O o1, O o2);
    }
    
    public static <O> int compareObjects(O o1, O o2,FunctionComparatorNotNull<O> f) {
        if (o1 == null){
            if (o2 == null){
                return 0;
            } else {
                return 1;
            }
        } else if (o2 == null){
            return -1;
        } else {
            if (o1 == o2){
                return 0;
            } else {
                int val=f.functionCompareNotNull(o1,o2);
                return val;
            }
        }
    }
    
    @FunctionalInterface
    public interface FunctionEqualsNotNull<O> {
        public boolean functionEqualsNotNull(O o1, O o2);
    }
    
    public static <O> boolean equals(O o1, Object o2,FunctionEqualsNotNull<O> f) {
        if (o1 == null){
            if (o2 == null){
                return true;
            } else {
                return false;
            }
        } else if (o2 == null){
            return false;
        } else {
        	if (o1 == o2){
                return true;
        	} else if (o1.getClass() != o1.getClass()){
            	return false;
            } else {
            	boolean val=f.functionEqualsNotNull(o1,(O)o2);
                return val;
            }
        }
    }


}
