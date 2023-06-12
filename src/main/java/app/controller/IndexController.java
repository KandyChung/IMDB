package app.controller;

import app.controller.paths.Template;
import app.controller.utils.ViewUtil;
import app.dao.PersonDAO;
import app.dao.ShowDAO;
import app.model.Account;
import app.model.Person;
import app.model.Show;
import io.javalin.http.Handler;
import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class IndexController {

	static Show show1;
	static Person person;
	//Show show2;
	
	public static Handler serveIndexPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        Random random = new Random();
        int number;
        //randomises which actor/actress will be accessed from the db
        if(PersonDAO.getNumPeopleInDB() > 2) {
        	number = random.nextInt(PersonDAO.getNumPeopleInDB() - 2);
        }
        else {
        	number = 0;
        }
        Person person1 = PersonDAO.getPersonByID(String.valueOf(number+1));
        Person person2 = PersonDAO.getPersonByID(String.valueOf(number+2));
        Show show1 = ShowDAO.getShowByID(1);
        Show show2 = ShowDAO.getShowByID(2);
        model.put("movie1ID", "1");
        model.put("movie1Img", "/img/shows/1.jpg");
        model.put("movie1Name", show1.getShowTitle());
        model.put("movie1desc", "<b>Genre: </b>" + show1.getGenre() + "<br>" + "<b>Year: </b>" + show1.getYear());
        model.put("movie2ID", "1");
        model.put("movie2Img", "/img/shows/2.jpg");
        model.put("movie2Name", show2.getShowTitle());
        model.put("movie2desc", "<b>Genre: </b>" + show2.getGenre() + "<br>" + "<b>Year: </b>" + show2.getYear());
        model.put("person6ID", person1.getPersonId());
        model.put("person6Img", "/img/people/"+person1.getPersonId()+".jpg");
        model.put("person6Name", person1.getFullName());
        model.put("person6Bio", person1.getBio());
        //System.out.println(person1.getBio());
        model.put("person11ID", person2.getPersonId());
        model.put("person11Img", "/img/people/"+person2.getPersonId()+".jpg");
        model.put("person11Name", person2.getFullName());
        model.put("person11Bio", person2.getBio());
        ctx.render(Template.INDEX, model);
    };
    
    public static Handler handleSearch = ctx -> {
    	Map<String, Object> model = ViewUtil.baseModel(ctx);
    	//check if input exsists
    	if (!SearchController.checkActor(getQueryActorname(ctx)) && !SearchController.checkShow(getQueryShowTitle(ctx)))
    	{
    		model.put("NoActorandMovieFound", true);
    		ctx.render(Template.RESULTSEARCH, model); 
    	}
    	else if(!SearchController.checkActor(getQueryActorname(ctx)) && SearchController.checkShow(getQueryShowTitle(ctx)))
    	{
    		model.put("OnlyShowFound", true);
    		Show show1 = ShowDAO.searchShowByShowTitle(getQueryShowTitle(ctx));
    		List<Show> shows = ShowDAO.getShowsByTitle(getQueryShowTitle(ctx));
    		IndexController.show1 = show1;
    		String showInputs = "";
    		for(Show show : shows) {
    			showInputs = showInputs + "<h2 style = \"text-align: center\">\r\n" + 
    					"	\r\n" + 
    					"			"+show.getShowTitle()+"\r\n" + 
    					"	\r\n" + 
    					"		</h2>\r\n" + 
    					"    \r\n" + 
    					"    	<div style = \" display: inline-block;\r\n" + 
    					"    					vertical-align : middle;\r\n" + 
    					"    					border-style : solid;\r\n" + 
    					"    					width : 100%;\r\n" + 
    					"    					min-width: 300px;\" "+
    					"onclick=\"setValue("+show.getShowid()+")\""+
    					">\r\n" + 
    					"    		\r\n" + 
    					"    		<h3 style = \"text-align: center\">\r\n" + 
    					"    	\r\n" + 
    					"    			Movie\r\n" + 
    					"    	\r\n" + 
    					"    		</h3>\r\n" + 
    					"    	\r\n" + 
    					"    		<img style = \" vertical-align: middle;\r\n" + 
    					"    					hidden;\r\n" + 
    					"    					max-height:300px;\r\n" + 
    					"    					max-width:60%\"; \r\n" + 
    					"    			src=\""+"/img/shows/"+show.getShowid()+".jpg\" \r\n" + 
    					"    			alt=\"$Show1Img\" \r\n" + 
    					"    			height = '300px' >\r\n" + 
    					"    		<div style = \"display: inline-block;\r\n" + 
    					"    					 vertical-align : middle;\r\n" + 
    					"    					 max-width : 100%;\r\n" + 
    					"    					 min-width: 300px;\" >\r\n" + 
    					"    			<li> Genre: "+show.getGenre()+" </li>\r\n" + 
    					"    			<li> Runtime: "+show.getLength()+" </li>\r\n" + 
    					"    			<li> Year released: "+show.getYear()+" </li>\r\n" + 
    					"    		</div>\r\n" + 
    					"    	</div>\r\n";
    					
    					
    					
    		}
    		
	    	model.put("showBody",showInputs);
    		ctx.render(Template.RESULTSEARCH, model);
    	}
    	else if(SearchController.checkActor(getQueryActorname(ctx)) && !SearchController.checkShow(getQueryShowTitle(ctx)))
    	{
    		model.put("OnlyActorFound", true);
    		List <Person> people = PersonDAO.getPeopleByName(getQueryActorname(ctx));
    		IndexController.person = people.get(0);
    		String peopleOutputs = "<h1> Result </h1>";
	    	for(Person person : people) {
	    		peopleOutputs = peopleOutputs +  
	    				"	<h2 style = \"text-align: center\">\r\n" + 
	    				"	\r\n" + 
	    				"		"+person.getFullName()+"\r\n" + 
	    				"	\r\n" + 
	    				"	</h2>\r\n" + 
	    				"    \r\n" + 
	    				"    <div style = \" display: inline-block;\r\n" + 
	    				"    				vertical-align : middle;\r\n" + 
	    				"    				border-style : solid;\r\n" + 
	    				"    				max-width : 100%\" \r\n" + 
	    				"					onclick=\"setActorValue("+person.getPersonId()+")\" >"+
	    				"    	<img style = \" vertical-align: middle;\r\n" + 
	    				"    				display: inline-block;\r\n" + 
	    				"    				max-height:300px;\r\n" + 
	    				"    				max-width:40%\" \r\n" + 
	    				"    		src=\""+"/img/people/"+person.getPersonId()+".jpg"+"\" \r\n" + 
	    				"    		alt=\"$Actor1Img\" \r\n" + 
	    				"    		height = '300px' >\r\n" + 
	    				"    		\r\n" + 
	    				"    	<p style = \"display:inline-block;\r\n" + 
	    				"    				vertical-align:middle;\r\n" + 
	    				"    				max-width:55%;\r\n" + 
	    				"    				overflow: hidden; \r\n" + 
	    				"    				max-height:200px;\" >\r\n" + 
	    				"    				\r\n" + 
	    				"    		"+person.getBio()+"\r\n" + 
	    				"    	\r\n" + 
	    				"    	</p>\r\n" + 
	    				"    </div>";
	    	}
	    	model.put("actorBody", peopleOutputs);
	    	ctx.render(Template.RESULTSEARCH, model);
    	}
    	else 
    	{
    		model.put("ActorandMovieFound", true); 	

	        Person person9 = PersonDAO.getPersonByName(getQueryActorname(ctx));
	    	model.put("Actor1Img", "/img/people/"+person9.getPersonId()+".jpg");
	    	model.put("Actor1Name", person9.getFullName());
	    	model.put("Actor1Role", person9.getRole());
	    	model.put("Actor1Bio", person9.getBio());
	    	
	    	Show show1 = ShowDAO.getShowByTitle(getQueryShowTitle(ctx));
	    	model.put("Show1Img", "/img/shows/"+show1.getShowid()+".jpg");
	    	model.put("Show1Name", show1.getShowTitle());
	    	model.put("Show1Genre", show1.getGenre());
	    	model.put("Show1Length", show1.getLength());
	    	model.put("Show1Year", show1.getYear());
    		ctx.render(Template.RESULTSEARCH, model);
    	}
    };
    
    
    public static String getQueryActorname(Context ctx)
    {
		return ctx.formParam("showActorSearch");
    }
    
    public static String getQueryShowTitle(Context ctx)
    {
    	return ctx.formParam("showTitleSearch");
    }
    
    public static Show getShow1() {
    	return show1;
    }

	public static void setShow1(Show show) {
		show1 = show;
		
	}

	public static Person getPerson() {
		return person;
	}

	public static void setPerson(Person personByID) {
		person = personByID;
		
	}

}
