package app.controller;

import java.util.Map;

import app.controller.paths.Template;
import app.controller.utils.ViewUtil;
import app.dao.ProductionCompanyDAO;
import app.dao.ShowDAO;
import app.model.ProductionCompany;
import app.model.Show;
import io.javalin.http.Context;
import io.javalin.http.Handler;

public class MovieShowEntry {

	public static Handler serveMovieShowEntryPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
    	ctx.render(Template.ENTRYPAGE, model);        
    };
    
    public static Handler handleShowsMoviesEntrySubmit = ctx -> {
    	Map<String, Object> model = ViewUtil.baseModel(ctx);
    	
    	if (!checkShowTitleInput(getQueryShowTitle(ctx)) && !checkProductionCompanyName(getQueryProductionCompanyName(ctx)))
    	{
    		model.put("ShowTitleANDPCProblem", true);
    		ctx.render(Template.ENTRYPAGE, model);
    	}
    	else if (checkShowTitleInput(getQueryShowTitle(ctx)) && !checkProductionCompanyName(getQueryProductionCompanyName(ctx)))
    	{
    		model.put("ProductCompanyError", true);
    		ctx.render(Template.ENTRYPAGE, model);
    	}
    	else if (!checkShowTitleInput(getQueryShowTitle(ctx)) && checkProductionCompanyName(getQueryProductionCompanyName(ctx)))
    	{
    		model.put("DuplicatedShow", true);
    		ctx.render(Template.ENTRYPAGE, model);
    	}
    	else
    	{
    		model.put("Passed", true);
    		ShowDAO.insertShow(
    					getQueryShowTitle(ctx), 
    					Double.parseDouble(getQueryShowLength(ctx)), 
    					getQueryShowGenre(ctx), 
    					Integer.parseInt(getQueryShowYear(ctx)), 
    					checkShow(getQueryShowOrMovie(ctx)), 
    					checkMovie(getQueryShowOrMovie(ctx)), 
    					getQueryProductionCompanyName(ctx)
    					);
    		
    		ctx.render(Template.ENTRYPAGE, model);
    	}
    	
    };
    
    public static String getQueryShowTitle(Context ctx)
    {
		return ctx.formParam("showTitle");
    }
    
    public static String getQueryShowGenre(Context ctx)
    {
		return ctx.formParam("showGenre");
    }
    
    public static String getQueryShowLength(Context ctx)
    {
		return ctx.formParam("showLength");
    }
    
    public static String getQueryShowYear(Context ctx)
    {
		return ctx.formParam("showYear");
    }
    
    public static String getQueryProductionCompanyName(Context ctx)
    {
		return ctx.formParam("productioncompanyname");
    }
    
    public static String getQueryShowOrMovie(Context ctx)
    {
		return ctx.formParam("type");
    }
    
    public static boolean checkMovie(String movie)
    {
    	if (movie.equalsIgnoreCase("movie"))
    	{
    		return true;
    	}
    	return false;
    }
    public static boolean checkShow (String show)
    {
    	if (show.equalsIgnoreCase("show"))
    	{
    		return true;
    	}
    	return false;
    }
    
    public static boolean checkShowTitleInput(String showTitle)
    {
    	if (showTitle == null)
    	{
    		return false;
    	}
    	Show show = ShowDAO.getShowByTitle(showTitle);
    	//check duplicate shows
    	if (show == null)
    	{
    		return true;
    	}
    	return false;
    }
    
    public static boolean checkProductionCompanyName (String pdc)
    {
    	if (pdc == null)
    	{
    		return false;
    	}
    	ProductionCompany productionCompany = ProductionCompanyDAO.getProductionCompanyByName(pdc);
    	
    	if (productionCompany == null)
    	{
    		ProductionCompanyDAO.insertProductionCompanyByName(pdc);
    		return true;
    	}
    	return true;
    }
    
}
