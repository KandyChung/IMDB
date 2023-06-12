package app.controller;

import java.util.Map;

import app.controller.paths.Template;
import app.controller.utils.ViewUtil;
import app.dao.PersonDAO;
import app.dao.ShowDAO;
import app.model.Person;
import app.model.Show;
import app.model.UserReview;
import io.javalin.http.Handler;

public class ActorController {
	public static Handler serveShowPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        
        ctx.render(Template.ACTORPAGE, model);
    };
    
    public static Handler fetchShowPageDetails = ctx -> {
    	String id = ctx.queryParam("personid");
    	Person person;
    	if(id != null) {
        	IndexController.setPerson(PersonDAO.getPersonByID(id));
    	}
    	person = IndexController.getPerson();
    	Map<String, Object> model = ViewUtil.baseModel(ctx);
    	model.put("Actor1Img","/img/people/"+person.getPersonId()+".jpg");
    	model.put("Actor1Name",person.getFullName());
    	model.put("Actor1Bio",person.getBio());
    	ctx.render(Template.ACTORPAGE,model);
    };
}
