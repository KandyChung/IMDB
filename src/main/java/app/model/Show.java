package app.model;


import java.util.Date;
import java.util.List;


public class Show {
    private int showid;
    private String showTitle;
    private double length;
    private boolean isMovie;
    private boolean isSeries;
    private boolean pending;
    private String genre;
    private int year;
    private int proco_id;
    private boolean suspense;
    private Date date;

    private List<UserReview> userReviewList;
    private ProductionCompany productionCompany;

    private List<CreditsRoll> creditsRolls;


    public Show(int showid, String showTitle, double length, String genre, int year, boolean isMovie,
    		boolean isSeries, int proco_id, boolean pending, Date date, boolean suspense) {
    	
    	this.showid = showid;
    	this.showTitle = showTitle;
    	this.length = length;
    	this.genre = genre;
    	this.year = year;
    	this.isMovie = isMovie;
    	this.isSeries = isSeries;
    	this.proco_id = proco_id;
    	this.pending = pending;
    	this.date = date;
    	this.suspense = suspense;
    }
    
    public boolean isSuspense() {
		return suspense;
	}

	public Date getDate() {
		return date;
	}

	public boolean isPending() {
		return pending;
	}

	public int getShowid() {
		return showid;
	}
    
    public String getShowTitle() {
    	
    	return this.showTitle;
    }
    
    public double getLength() {
    	
    	return this.length;
    }
    
    public String getGenre() {
    	
    	return this.genre;
    }
    
    public int getYear() {
    	
    	return this.year;
    }
    
    public boolean isMovie() {
    	
    	return this.isMovie;
    }
    
    public boolean isSeries() {
    	
    	return this.isSeries;
    }
    
    public ProductionCompany getProductionCompany() {
    	
    	return this.productionCompany;
    }
    
    public int getProco_id() {
    	
    	return this.proco_id;
    }
    
    public List<CreditsRoll> getCreditsRolls() {
    	
    	return this.creditsRolls; 
    }
    
    public List<UserReview> getUserReviewList(){
    	return this.userReviewList; 
    }
}
