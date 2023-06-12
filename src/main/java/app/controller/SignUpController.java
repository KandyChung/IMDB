package app.controller;

import java.util.Map;

import app.controller.paths.Template;
import app.controller.utils.ViewUtil;
import app.dao.AccountDAO;
import io.javalin.http.Context;
import io.javalin.http.Handler;

public class SignUpController {

	public static Handler serverSignUpPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        
        
        ctx.render(Template.SIGNUP, model);
    };
    
    public static Handler signUpButton = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        
        String username = getQueryUserName(ctx);
        String password = getQueryPassword(ctx);
        String password1 = getQueryPassword1(ctx);
        String firstname = getQueryFirstName(ctx);
        String lastname = getQueryLastName(ctx);
        String email = getQueryEmail(ctx);
        String country = getQueryCountry(ctx);
        String gender = getQueryGender(ctx);
        String type = getQueryType(ctx);
        
        if(UserController.duplicatedUserName(username) && UserController.checkTwoPasswords(password, password1))
        {
        	AccountDAO.creatingNewAccount(username, password, email, country, gender, firstname, lastname, type);
        	model.put("authenticationSucceeded", true);
        	ctx.render(Template.SIGNUP, model);
        }
        else if(!UserController.duplicatedUserName(username))
        {
        	model.put("authenticationFailedDuplication", true);
        	ctx.render(Template.SIGNUP, model);
        }
        else if (!UserController.checkTwoPasswords(password, password1))
        {
        	model.put("passwordNotMatch", true);
        	ctx.render(Template.SIGNUP, model);
        }
        else
        {
        	model.put("authenticationFailed", true);
        	ctx.render(Template.SIGNUP, model);
        }
        
        ctx.render(Template.SIGNUP, model);
    };
    
    public static String getQueryFirstName(Context ctx)
    {
		return ctx.formParam("firstname");
    }
    
    public static String getQueryLastName(Context ctx)
    {
		return ctx.formParam("lastname");
    }
    
    public static String getQueryPassword(Context ctx)
    {
		return ctx.formParam("password");
    }
    
    public static String getQueryPassword1(Context ctx)
    {
		return ctx.formParam("password1");
    }
    
    public static String getQueryUserName(Context ctx)
    {
		return ctx.formParam("username");
    }
    
    public static String getQueryEmail(Context ctx)
    {
		return ctx.formParam("email");
    }
    
    public static String getQueryCountry(Context ctx)
    {
		return ctx.formParam("country");
    }
    
    public static String getQueryGender(Context ctx)
    {
		return ctx.formParam("gender");
    }
    
    public static String getQueryType(Context ctx)
    {
		return ctx.formParam("type");
    }
    
    
}
