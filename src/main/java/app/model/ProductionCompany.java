package app.model;

import java.util.List;

public class ProductionCompany {
    private String name;
    private int id;
    
    private List<Show> shows;

    public ProductionCompany(int id, String n) {
        this.id = id;
        this.name = n;
    }

    public String getName() {
        return name;
    }
    
    public int getId()
    {
    	return id;
    }
}
