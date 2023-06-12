package app;

import app.controller.AccountController;
import app.controller.ActorController;
import app.controller.AdminAccessController;
import app.controller.AllShowsForUserController;
import app.controller.AllShowsPageController;
import app.controller.IndexController;
import app.controller.LoginController;
import app.controller.MovieShowEntry;
import app.controller.PcoEditDataController;
import app.controller.PendingAccountsController;
import app.controller.PersonPageController;
import app.controller.ResultSearchController;
import app.controller.ShowPageController;
import app.controller.SignUpController;
import app.controller.paths.Web;
import app.controller.utils.ViewUtil;
import io.javalin.Javalin;
import io.javalin.core.util.RouteOverviewPlugin;
import static io.javalin.apibuilder.ApiBuilder.*;

public class Main {

    public static void main(String[] args) {
        Javalin app = Javalin.create(config -> {
            config.addStaticFiles("/public");
            config.registerPlugin(new RouteOverviewPlugin("/routes"));
        }).start(getHerokuAssignedPort());

        app.routes(() -> {

            get(Web.INDEX, IndexController.serveIndexPage);
                                    
            get(Web.LOGIN, LoginController.serveLoginPage);
            
            get(Web.SIGNUP, SignUpController.serverSignUpPage);
                                   
            get(Web.ENTRYPAGE, MovieShowEntry.serveMovieShowEntryPage);
            
            get(Web.ACTORPAGE, ActorController.fetchShowPageDetails);
            
            get(Web.ADMIN_ACCESS, AdminAccessController.serveAdminAccessPage);
            
            get(Web.PERSON, PersonPageController.serveParsonalPage);
            
            get(Web.SHOWPAGE, ShowPageController.fetchShowPageDetails);
            
            get(Web.SHOWSFORUSER, AllShowsForUserController.serveAllShowsPageForUser);

            get(Web.ACCOUNT, AccountController.serveAccountPage);

            get(Web.RESULTSEARCH, ResultSearchController.serveResultSearchPage);
            
            get(Web.SHOWS, AllShowsPageController.serveAllShowsPage);
            
            get(Web.PENDINGACCOUNTS, PendingAccountsController.servePendingAccountPage);
            
            get(Web.EDITDATA, PcoEditDataController.serveEditDataPage);
            
            post(Web.PENDINGACCOUNTS, PendingAccountsController.handleApproveAccounts);
            
            post(Web.RESULTSEARCH, IndexController.handleSearch);
            
            post(Web.SHOWS, AllShowsPageController.suspenseShow);
            
            post(Web.SIGNUP, SignUpController.signUpButton);
            
            post(Web.ENTRYPAGE, MovieShowEntry.handleShowsMoviesEntrySubmit);
            
            post(Web.LOGIN, LoginController.handleLoginPost);
            
            post(Web.LOGOUT, LoginController.handleLogoutPost);
            
            post(Web.SHOWPAGE, ShowPageController.handleRatingAndReview);
            
            post(Web.ADMIN_ACCESS, AdminAccessController.handleApproveShows);
            
            post(Web.EDITDATA, PcoEditDataController.handleEditData);
            
        });

        app.error(404, ViewUtil.notFound);
    }

    public static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 7000;
    }



}
