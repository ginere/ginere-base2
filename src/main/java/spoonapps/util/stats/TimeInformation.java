package spoonapps.util.stats;

import java.io.Serializable;

public class TimeInformation implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private long number=0;    
    private long totalTime=0;    
    private long minTime=Long.MAX_VALUE;
    private long maxTime=0;    
    private double averageTime=0;    
	public long exceptionCount=0;

	public Throwable lastException=null;
	
    public TimeInformation(){    	
    }

	public synchronized void clean() {
		number=0;    
		totalTime=0;    
		minTime=Long.MAX_VALUE;
		maxTime=0;    
		averageTime=0;    
		exceptionCount=0;

		lastException=null;
	}
	
	public synchronized void add(long time) {
		number++;
		totalTime+=time;
	
		if (time>maxTime){
			maxTime=time;
		}
        
		if (time<minTime){
			minTime=time;
		}

        this.averageTime=(long)(totalTime/number);			
	}   
	
	public synchronized void addException(Throwable e) {
		this.lastException=e;
		this.exceptionCount++;
	}
	
	@Override
	public String toString(){
		return "Average:"+averageTime+",Num:"+number+" [max:"+maxTime+",min:"+minTime+"], exceptionCount:"+exceptionCount; 
	}

	public long getNumber() {
		return number;
	}

	public long getTotalTime() {
		return totalTime;
	}

	public long getMinTime() {
		return minTime;
	}

	public long getMaxTime() {
		return maxTime;
	}

	public double getAverageTime() {
		return averageTime;
	}
	   
	public long getExceptionCount() {
		return exceptionCount;
	}

	public Throwable getLastException() {
		return lastException;
	}
}
