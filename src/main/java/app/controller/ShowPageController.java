package app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import app.controller.paths.Template;
import app.controller.utils.ViewUtil;
import app.dao.ShowDAO;
import app.dao.UserReviewDAO;
import app.model.Show;
import app.model.UserReview;
import io.javalin.http.Handler;
import io.javalin.http.Context;


public class ShowPageController {
	
	static boolean rated = false;
	static boolean review = false;
	static List<UserReview> currentUserReview = null;
	static List<UserReview> userComment = null;
	static ArrayList<String> comment = new ArrayList<String>();
//	static List<UserReview> userReviews = new ArrayList<>();
	
	static double ratingSum = 0.0;
	static double count = 0.0;
	static double ratingAverage = 0.0;
	static String cmts ;
	
	public static Handler serveShowPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        
        ctx.render(Template.SHOWPAGE, model);
    };
    
    public static Handler fetchShowPageDetails = ctx -> {
    	
    	comment.clear();
    	ratingSum = 0.0;
    	count = 0.0;
    	ratingAverage = 0.0;
    	
    	String id = ctx.queryParam("showid");
    	Show show;
    	if(id != null) {
    		int showID = Integer.parseInt(id);
        	IndexController.setShow1(ShowDAO.getShowByID(showID));
    	}
    	show = IndexController.getShow1();
    	Map<String, Object> model = ViewUtil.baseModel(ctx);
    	    	  
    	model.put("showTitle", show.getShowTitle());
    	model.put("length", show.getLength());
    	model.put("genre", show.getGenre());
    	model.put("year", show.getYear());
    	model.put("showImage", "/img/shows/"+show.getShowid()+".jpg");
    	if(show.isMovie() == true) {
    		model.put("movie", true);
    		ctx.render(Template.SHOWPAGE, model);
    	}
    	else {
    		model.put("movie", false);
    		ctx.render(Template.SHOWPAGE, model);
    	}
    	
    	//check if there are rating in database or not
    	if (UserReviewDAO.getRatingByShowID(show.getShowid()) != null) {
    		rated = true;
    	}
    	
    	//check if there are comment in database or not
    	if (UserReviewDAO.getReviewByShowID(show.getShowid()) != null) {
    		review = true;
    	}
    	
    	//
    	if(rated == true || review == true) {
    		
    		//double check on getting review
    		userComment = UserReviewDAO.getReviewByShowID(show.getShowid());
    		if(userComment == null) {
    			
    			review = false;
    		}
    		//getting review to display
    		else if (userComment != null) {
    			review = false;
	    		for(UserReview userReview: userComment) {
	    			
	    			if(userReview != null) {
	    				if(!userReview.getReview().equals("null")) {
    						comment.add(userReview.getReview());
    						review = true;
    					}
	    			}
	    		}
    		}
    		
    		//double check on getting rate
    		currentUserReview = UserReviewDAO.getRatingByShowID(show.getShowid());
    		if(currentUserReview == null) {
    			
    			rated = false;
    		}
    		// Calculate the average rating for the current displayed show
    		else {
	    		for(UserReview userReview: currentUserReview) {
	    			
	    			if(userReview != null) {
	    				
	    				if(userReview.getShowid() == show.getShowid()) {
	    					if (userReview.getRating() == 0) {
								count = count * 1;
							}
							else {
	    					ratingSum += userReview.getRating();
	    					++count;
							}
	    					
	    				}
    				}
    			}
    		}
		}
    		
		if(count < 1) {
			
			rated = false;
		}
		else if (count >= 1) {
			
			ratingAverage = ratingSum / count;
			
			// Rounds to 2 d.p.
			ratingAverage = Math.round(ratingAverage * 100.0) / 100.0;
			rated = true;
		}
		
    	if(rated == false) {
    		model.put("rating", "No users have rated this show yet");
    		ctx.render(Template.SHOWPAGE, model);
    	}
    	else if(rated == true) {
    		model.put("rating", ratingAverage + " Stars");
    		ctx.render(Template.SHOWPAGE, model);
    	}
    	
    	
    	if(review == false) {
    		model.put("noReview", "No users have commented on this show yet!!");
    		ctx.render(Template.SHOWPAGE, model);
    	}
    	else if(review == true) {
    		model.put("review", comment);
    		model.put("eachReview", comment.toString());
    		
    		ctx.render(Template.SHOWPAGE, model);
    	}
    	
    	ctx.render(Template.SHOWPAGE, model);
    };
    
    public static Handler handleRatingAndReview = ctx -> {
		Map<String, Object> model = ViewUtil.baseModel(ctx);

		comment.clear();
		if(getRatingInput(ctx) != null) {
			
			rated = true;
			Show show = IndexController.getShow1();
			
			UserReviewDAO.createRatingIntoDatabase(show, Integer.parseInt(getRatingInput(ctx).trim()));
			currentUserReview = UserReviewDAO.getRatingByShowID(show.getShowid());
			
			// Set rating of user review recently created manually to avoid creating duplicate ratings for different reviews
//			currentUserReview.setRating(Integer.parseInt(getRatingInput(ctx).trim()));
//			userReviews.add(currentUserReview);

			// Calculate the average rating for the currently displayed show after rating input
			for(UserReview userReview: currentUserReview) {
				
				if(userReview != null) {
					
					if(userReview.getShowid() == show.getShowid()) {
    					
						if (userReview.getRating() == 0) {
							count = count * 1;
						}
						else {
    					ratingSum += userReview.getRating();
    					++count;
						}
    				}
				}
			}
			
			ratingAverage = ratingSum / count;
			
			// Rounds to 2 d.p
			ratingAverage = Math.round(ratingAverage * 100.0) / 100.0;
			
			model.put("rating", ratingAverage + " Stars");
			
			ratingSum = 0.0;
			count = 0.0;
			ratingAverage = 0.0;
			
			List<UserReview> userComment = UserReviewDAO.getReviewByShowID(show.getShowid());
			review = false;
			for(UserReview userReview: userComment) {
    			if(userReview != null) {
    				if(userReview.getShowid() == show.getShowid()) {
    					if(!userReview.getReview().equals("null")) {
    						comment.add(userReview.getReview());
    						review = true;
    					}
    				}
    			}
    		}
			if(review == false) {
	    		model.put("noReview", "No users have commented on this show yet!!");
	    		ctx.render(Template.SHOWPAGE, model);
	    	}
	    	else if(review == true) {
	    		model.put("review", comment);
	    		model.put("eachReview", comment.toString());
	    		ctx.render(Template.SHOWPAGE, model);
	    	}
			
			model.put("showTitle", show.getShowTitle());
	    	model.put("length", show.getLength());
	    	model.put("genre", show.getGenre());
	    	model.put("year", show.getYear());
	    	model.put("showImage", "/img/shows/"+show.getShowid()+".jpg");
	    	if(show.isMovie() == true) {
	    		model.put("movie", true);
	    		ctx.render(Template.SHOWPAGE, model);
	    	}
	    	else {
	    		model.put("movie", false);
	    		ctx.render(Template.SHOWPAGE, model);
	    	}
			
			ctx.render(Template.SHOWPAGE, model);
			
			
		}
		else if(getReviewInput(ctx) != null) {
			
			review = true;
			Show show = IndexController.getShow1();
			
			UserReviewDAO.addingReviewIntoDatabase(show, getReviewInput(ctx).trim());
			List<UserReview> userComment = UserReviewDAO.getReviewByShowID(show.getShowid());
			
			for(UserReview userReview: userComment) {
    			if(userReview != null) {
    				if(userReview.getShowid() == show.getShowid()) {
    					if(!userReview.getReview().equals("null")) {
    						comment.add(userReview.getReview());
    						review = true;
    					}
    				}
    			}
    		}
			if(review == false) {
	    		model.put("noReview", "No users have commented on this show yet!!");
	    		ctx.render(Template.SHOWPAGE, model);
	    	}
	    	else if(review == true) {
	    		model.put("review", comment);
	    		model.put("eachReview", comment.toString());
	    		ctx.render(Template.SHOWPAGE, model);
	    	}
			
			
			currentUserReview = UserReviewDAO.getRatingByShowID(show.getShowid());
			for(UserReview userReview: currentUserReview) {
				if(userReview != null) {
					if(userReview.getShowid() == show.getShowid()) {
						if (userReview.getRating() == 0) {
							count = count * 1;
						}
						else {
    					ratingSum += userReview.getRating();
    					++count;
						}
    				}
				}
			}
			if ( ratingSum !=  0) {
			ratingAverage = ratingSum / count;
			
			
			
			// Rounds to 2 d.p
			ratingAverage = Math.round(ratingAverage * 100.0) / 100.0;
			
			model.put("rating", ratingAverage + " Stars");
			}
			else
			{
				model.put("rating", "No users have rated this show yet");
			}
			
			ratingSum = 0.0;
			count = 0.0;
			ratingAverage = 0.0;
			
			model.put("showTitle", show.getShowTitle());
	    	model.put("length", show.getLength());
	    	model.put("genre", show.getGenre());
	    	model.put("year", show.getYear());
	    	model.put("showImage", "/img/shows/"+show.getShowid()+".jpg");
	    	if(show.isMovie() == true) {
	    		model.put("movie", true);
	    		ctx.render(Template.SHOWPAGE, model);
	    	}
	    	else {
	    		model.put("movie", false);
	    		ctx.render(Template.SHOWPAGE, model);
	    	}
			
			ctx.render(Template.SHOWPAGE, model);
			
		}
		ctx.render(Template.SHOWPAGE, model);

	};
		
	public static String getRatingInput(Context ctx)
	    {
			return ctx.formParam("showRating");
	    }
    public static String getReviewInput(Context ctx) {
    	return ctx.formParam("showReview");
    }
}
