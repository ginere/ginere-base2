package spoonapps.util.properties.impl;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class PropertyDefinition {
    public final String name;
    public final String description;
    public final String value;

    public PropertyDefinition(String name,
                              String description,
                              String value){
        this.name=name;
        this.description=description;
        this.value=value;        
    }   
    
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}    
}
