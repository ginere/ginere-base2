package spoonapps.util.properties.impl;

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
}
