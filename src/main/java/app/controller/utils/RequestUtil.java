package app.controller.utils;


import io.javalin.http.Context;

public class RequestUtil {


    public static String getQueryLoginRedirect(Context ctx) {
        return ctx.queryParam("loginRedirect");
    }

    public static String getSessionCurrentUser(Context ctx) {
        return (String) ctx.sessionAttribute("currentUser");
    }
    
    public static String getSessionAdminUser(Context ctx) {
        return (String) ctx.sessionAttribute("adminUser");
    }
    
    public static String getSessionPcoUser(Context ctx) {
        return (String) ctx.sessionAttribute("pcoUser");
    }




}
