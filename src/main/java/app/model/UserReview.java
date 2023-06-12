package app.model;

import java.util.Date;


public class UserReview {
    private int reviewID;
    private String username;
    private String review;
    private int rating;
    private Date date;
    
    public int showid;


    public UserReview(String r, int v, int showid, int reviewID) {
        review = r;
        rating = v;
        date = new Date();
        
        
        this.showid = showid;
        this.reviewID = reviewID;
    }
    
    public UserReview(String review,String username, int showid, int reviewID) {
    	this.review=review;
    	this.username=username;
    	this.showid=showid;
    	this.reviewID=reviewID;
    }

    public UserReview() {}


    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Date getDate() {
        return date;
    }
    
    public int getShowid() {
    	return this.showid;
    }
}
