package app.controller;

import java.util.Map;

import app.controller.paths.Template;
import app.controller.utils.ViewUtil;
import app.dao.PersonDAO;
import app.dao.ShowDAO;
import app.model.Person;
import io.javalin.http.Context;
import io.javalin.http.Handler;

public class PersonPageController {
	
	public static Handler serveParsonalPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        
//        for(int i = 0; i <= 12; i++)
//        {
//        	 Person person = PersonDAO.getPersonByID(String.valueOf(i));
//        	 
//        	 model.put("personID", person.getPersonId());
//             model.put("personImg", "/img/people/" + person.getPersonId()+".jpg");
//             model.put("personName", person.getFullName());
//             model.put("personBD", person.getBirthdate());
//             model.put("personrole", person.getRole());
//             model.put("personBio", person.getBio());
//             
//             ctx.render(Template.PERSON, model);
//        }
        Person person1 = PersonDAO.getPersonByID(String.valueOf(1));
        Person person2 = PersonDAO.getPersonByID(String.valueOf(2));
        Person person3 = PersonDAO.getPersonByID(String.valueOf(3));
        Person person4 = PersonDAO.getPersonByID(String.valueOf(4));
        Person person5 = PersonDAO.getPersonByID(String.valueOf(5));
        Person person6 = PersonDAO.getPersonByID(String.valueOf(6));
        Person person7 = PersonDAO.getPersonByID(String.valueOf(7));
        Person person8 = PersonDAO.getPersonByID(String.valueOf(8));
        Person person9 = PersonDAO.getPersonByID(String.valueOf(9));
        Person person10 = PersonDAO.getPersonByID(String.valueOf(10));
        Person person11 = PersonDAO.getPersonByID(String.valueOf(11));
        Person person12 = PersonDAO.getPersonByID(String.valueOf(12));

        model.put("person1ID", person1.getPersonId());
        model.put("person1Img", "/img/people/"+person1.getPersonId()+".jpg");
        model.put("person1Name", person1.getFullName());
        model.put("person1BD", person1.getBirthdate());
        model.put("person1role", person1.getRole());
        model.put("person1Bio", person1.getBio());
        
        model.put("person2ID", person2.getPersonId());
        model.put("person2Img", "/img/people/"+person2.getPersonId()+".jpg");
        model.put("person2Name", person2.getFullName());
        model.put("person2Bio", person2.getBio());
        
        model.put("person3ID", person3.getPersonId());
        model.put("person3Img", "/img/people/"+person3.getPersonId()+".jpg");
        model.put("person3Name", person3.getFullName());
        model.put("person3Bio", person3.getBio());
        
        model.put("person4ID", person4.getPersonId());
        model.put("person4Img", "/img/people/"+person4.getPersonId()+".jpg");
        model.put("person4Name", person4.getFullName());
        model.put("person4Bio", person4.getBio());
        
        model.put("person5ID", person5.getPersonId());
        model.put("person5Img", "/img/people/"+person5.getPersonId()+".jpg");
        model.put("person5Name", person5.getFullName());
        model.put("person5Bio", person5.getBio());
        
        model.put("person6ID", person6.getPersonId());
        model.put("person6Img", "/img/people/"+person6.getPersonId()+".jpg");
        model.put("person6Name", person6.getFullName());
        model.put("person6Bio", person6.getBio());
        
        model.put("person7ID", person7.getPersonId());
        model.put("person7Img", "/img/people/"+person7.getPersonId()+".jpg");
        model.put("person7Name", person7.getFullName());
        model.put("person7Bio", person7.getBio());
        
        model.put("person8ID", person8.getPersonId());
        model.put("person8Img", "/img/people/"+person8.getPersonId()+".jpg");
        model.put("person8Name", person8.getFullName());
        model.put("person8Bio", person8.getBio());
        
        model.put("person9ID", person9.getPersonId());
        model.put("person9Img", "/img/people/"+person9.getPersonId()+".jpg");
        model.put("person9Name", person9.getFullName());
        model.put("person9Bio", person9.getBio());
        
        model.put("person10ID", person10.getPersonId());
        model.put("person10Img", "/img/people/"+person10.getPersonId()+".jpg");
        model.put("person10Name", person10.getFullName());
        model.put("person10Bio", person10.getBio());
        
        model.put("person11ID", person11.getPersonId());
        model.put("person11Img", "/img/people/"+person11.getPersonId()+".jpg");
        model.put("person11Name", person11.getFullName());
        model.put("person11Bio", person11.getBio());
        
        model.put("person12ID", person12.getPersonId());
        model.put("person12Img", "/img/people/"+person12.getPersonId()+".jpg");
        model.put("person12Name", person12.getFullName());
        model.put("person12Bio", person12.getBio());
        
        ctx.render(Template.PERSON, model);
    };
    
    public static Handler fetchPersonalPageDetails = ctx -> {
    	
    	Map<String, Object> model = ViewUtil.baseModel(ctx);

    	ctx.render(Template.PERSON, model);
    };
    
    public static String getQueryPersonName(Context ctx) {
        return ctx.formParam("personNameSearch");
    }

}
