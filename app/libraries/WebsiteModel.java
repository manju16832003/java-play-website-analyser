package libraries;

import org.jsoup.select.Elements;

import java.util.List;
import java.util.Map;

public class WebsiteModel {
    private String title;
    private String htmlVersion;
    private HTagModel hTagModel;
    private LinkModel linkModel;
    private Boolean isLoginForm;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHtmlVersion() {
        return htmlVersion;
    }

    public void setHtmlVersion(String htmlVersion) {
        this.htmlVersion = htmlVersion;
    }

    public HTagModel gethTagModel() {
        return hTagModel;
    }

    public void sethTagModel(HTagModel hTagModel) {
        this.hTagModel = hTagModel;
    }

    public Boolean getLoginForm() {
        return isLoginForm;
    }

    public void setLoginForm(Boolean loginForm) {
        isLoginForm = loginForm;
    }

    public LinkModel getLinkModel() {
        return linkModel;
    }

    public void setLinkModel(LinkModel linkModel) {
        this.linkModel = linkModel;
    }
}
