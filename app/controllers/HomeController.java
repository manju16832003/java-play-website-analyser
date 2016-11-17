package controllers;

import libraries.WebSiteService;
import libraries.WebsiteModel;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import play.Logger;
import play.api.http.HttpErrorHandlerExceptions;
import play.api.libs.ws.WSClient;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.*;
import views.html.*;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.inject.Inject;
import javax.naming.MalformedLinkException;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {
    @Inject
    WSClient ws;
    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        Logger.info("asdf "+request().getQueryString("error"));
        return ok(index.render("Your new application is ready."));
    }

    /**
     *
     * @return
     */
    public Result analyse() {
        WebsiteModel websiteModel = null;
        DynamicForm dynamicForm = Form.form().bindFromRequest();
        String url = dynamicForm.get("website");

        try {
            Connection connection = Jsoup.connect(url).userAgent("Mozilla").timeout(3000);
            connection.ignoreHttpErrors(true);
            Document doc = connection.get();
            if (connection.response().statusCode() != 200) {
                return redirect(routes.HomeController.index()+"?error="+connection.response().statusMessage()+"&code="+connection.response().statusCode());
            }

            WebSiteService webSiteService = new WebSiteService(doc);
            websiteModel = webSiteService.processDocument();
        } catch (MalformedURLException e) {
            Logger.info(e.getMessage());
        } catch (IOException e) {
            Logger.info(e.getMessage());
        } catch (IllegalArgumentException iae) {
            return redirect(routes.HomeController.index()+"?error="+iae.getMessage()+"&code=500");
        }

        return ok(analyse.render(websiteModel, url));
    }
}
