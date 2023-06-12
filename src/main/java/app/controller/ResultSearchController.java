package app.controller;

import java.util.Map;

import app.controller.paths.Template;
import app.controller.utils.ViewUtil;
import app.dao.PersonDAO;
import app.dao.ShowDAO;
import app.dao.UserReviewDAO;
import app.model.Person;
import app.model.Show;
import app.model.UserReview;
import io.javalin.http.Context;
import io.javalin.http.Handler;

public class ResultSearchController {

	 public static Handler serveResultSearchPage = ctx -> {
	        Map<String, Object> model = ViewUtil.baseModel(ctx);
	        
	    	ctx.render(Template.RESULTSEARCH, model);
	        
	        
	    };
	 
	
	    
//	    public static String getRatingInput(Context ctx)
//	    {
//			return ctx.formParam("showRating");
//	    }
	    
//	    public static int getShowID() {
//	    	
//	    }
	 
}
