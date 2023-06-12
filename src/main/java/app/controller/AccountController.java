package app.controller;

import app.controller.paths.Template;
import app.controller.utils.ViewUtil;
import app.dao.AccountDAO;
import app.model.Account;
import io.javalin.http.Handler;

import java.util.Map;

public class AccountController {

    public static Handler serveAccountPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        // You'll have to update the model... maybe here
        String i = ctx.sessionAttribute("currentUser");
        if(i == null) {
        	i = ctx.sessionAttribute("adminUser");
        	if (i == null)
        	{
        		i = ctx.sessionAttribute("pcoUser");
        	}
        }
        Account account = AccountDAO.getUserDetailsByUsername(i);
        model.put("account", account);
        ctx.render(Template.ACCOUNT, model);
    };

}
