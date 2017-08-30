package spoonapps.util.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import spoonapps.util.enumeration.AppEnum;

public class CollectionUtils {

	public static <F,T> List<F> getUnmodifiableListFromList(List<T> list,Function<T,F> transformer) {
		if (list == null){
			return Collections.emptyList();
		} else {
	        List<F> ret=new ArrayList<F>(list.size());
	        for (T el:list){
	        	F f=transformer.apply(el);
	        	if (f!=null){
	        		ret.add(f);
	        	}
	        }
	
	        return Collections.unmodifiableList(ret);
		}
	}
	
	public static <F,T> List<F> transformList(List<T> list,Function<T,F> transformer) {
		if (list == null){
			return Collections.emptyList();
		} else {
	        List<F> ret=new ArrayList<F>(list.size());
	        for (T el:list){
	        	F f=transformer.apply(el);
	        	if (f!=null){
	        		ret.add(f);
	        	}
	        }
	
	        return ret;
		}
	}
	
	public static <F,T> List<F> getUnmodifiableListFromCollection(Collection<T> list,Function<T,F> transformer) {
		if (list == null){
			return Collections.emptyList();
		} else {
	        List<F> ret=new ArrayList<F>(list.size());
	        for (T el:list){
	        	F f=transformer.apply(el);
	        	if (f!=null){
	        		ret.add(f);
	        	}
	        }
	
	        return Collections.unmodifiableList(ret);
		}
	}
	

	public static <F,T> List<F> getUnmodifiableListFromCollectionList(Collection<T> collection,Function<T,List<F>> transformer) {
		if (collection == null){
			return Collections.emptyList();
		} else {
	        List<F> ret=new ArrayList<F>(collection.size());
	        for (T el:collection){
	        	List<F> listOfF=transformer.apply(el);
	        	if (listOfF!=null){
	        		ret.addAll(listOfF);
	        	}
	        }
	
	        return Collections.unmodifiableList(ret);
		}
	}
	
	public static <F,T> List<F> getUnmodifiableListFromMap(Map<String,T> map,Function<T,F> transformer) {
		if (map == null){
			return Collections.emptyList();
		} else {
	        List<F> ret=new ArrayList<F>(map.size());
	        for (T el:map.values()){
	        	F f=transformer.apply(el);
	        	if (f!=null){
	        		ret.add(f);
	        	}
	        }
	
	        return Collections.unmodifiableList(ret);
		}
	}
	
	public static <T> void sort(List<T> list,Comparator<T> comparator){
        Collections.sort(list, comparator);
	}
	
	public static <T extends AppEnum> Map<String, T> buildFromEnumarationIdList(List<String> list,Class<T> clazz) {
		if ( list==null || list.size() == 0){
			return Collections.emptyMap();
		} else {	
			HashMap<String, T> map=new HashMap<String, T>();
			
			list.stream().forEach(s -> {
				if (!map.containsKey(s)){
					T t=(T)AppEnum.value(clazz, s,null);
					if (t!=null){
						map.put(s,t);
					}
				}
			});

			return map;
		}
	}

	public static<K,V> boolean haveComonValues(Map<K, V> a, Map<K, V> b) {
		if (a == null || b == null || a.size()==0 || b.size() == 0){
			return false;
		} else {
			Map<K, V> iterator,target;
			if (a.size()<b.size()){
				iterator=a;
				target=b;
			} else {
				iterator=b;
				target=a;
			}
			
			for (K key:iterator.keySet()){
				if (target.containsKey(key)){
					return true;
				}
			}
			return false;
		}
	}
		
//	public static <T> void sort(List<T> list,Comparator[] comparatorArray){
//		if(null!=list && comparatorArray!=null){
//			Collections.sort(list, ComparatorUtils.chainedComparator(comparatorArray));	
//		}		
//	}
//		
//	public static <T> void sort(List<T> list,List<Comparator> comparatorList){		
//		if(null != list && null!=comparatorList && !comparatorList.isEmpty()){			
//			sort(list, comparatorList.toArray(new Comparator[comparatorList.size()]));
//		}
//	}
    
}
