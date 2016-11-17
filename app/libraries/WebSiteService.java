package libraries;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.DocumentType;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import play.Logger;
import play.api.libs.ws.WSClient;
import javax.inject.Inject;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebSiteService {
    private Document document;
    @Inject
    WSClient ws;


    public WebSiteService(Document document) {
        this.document = document;
    }

    /**
     * Process the website and return the model.
     *
     * @return WebsiteModel
     */
    public WebsiteModel processDocument() {
        WebsiteModel model = new WebsiteModel();
        model.setTitle(this.document.title());
        model.setHtmlVersion(getDocumentHTMLVersion());
        model.sethTagModel(getHeadingsAndLevels());
        model.setLinkModel(getLinks());

        return model;
    }

    /**
     * Get HTML version based on Document type
     */
    private String getDocumentHTMLVersion() {
        String htmlVersion = "HTML 5";
        List<Node> nodes = this.document.childNodes();
        for (Node node : nodes) {
            if (node instanceof DocumentType) {
                DocumentType documentType = (DocumentType) node;
                String publicid = documentType.attr("publicid");
                if (!publicid.isEmpty()) {
                    htmlVersion = this.sanitiseHtmlVersion(documentType.attr("publicid"));
                }
            }
        }

        return htmlVersion;
    }

    /**
     * Get all headings and the heading levels.
     *
     * @return Elements
     */
    private HTagModel getHeadingsAndLevels() {
        Map<String, Integer> hTagPairMap = new HashMap<>();
        Elements hTags = this.document.select("h1, h2, h3, h4, h5, h6");
        hTagPairMap.put("h1", this.document.select("h1").toArray().length);
        hTagPairMap.put("h2", this.document.select("h2").toArray().length);
        hTagPairMap.put("h3", this.document.select("h3").toArray().length);
        hTagPairMap.put("h4", this.document.select("h4").toArray().length);
        hTagPairMap.put("h5", this.document.select("h5").toArray().length);
        hTagPairMap.put("h6", this.document.select("h6").toArray().length);

        HTagModel hTagModel = new HTagModel();
        hTagModel.setTotal(hTags.toArray().length);
        hTagModel.sethTags(hTagPairMap);

        return hTagModel;
    }

    /**
     * Get internal, external links
     */
    private LinkModel getLinks() {
        LinkModel linkModel = new LinkModel();
        // Internal link handling
        Elements internalLinks = this.document.select("a[href^=#], a[href^=/], a[href^=./], a[href^=../]");
        Map<String, String> internalLinksMap = new HashMap<>();
        for (Element link : internalLinks) {
            String url = link.attr("abs:href");
            if (isURLAccessible(url)) {
                internalLinksMap.put(url, "ACCESSIBLE");
            } else {
                internalLinksMap.put(url, "NOT ACCESSIBLE");
            }
        }

        // External link handing
        Integer totalExternalLinkCount = 0;
        Elements externalLinks = this.document.select("a[href]");
        Map<String, String> externalLinksMap = new HashMap<>();
        for (Element link : externalLinks) {
            String hrefLink = link.attr("href");
            if (hrefLink.startsWith("#") || hrefLink.startsWith("./") || hrefLink.contains("../") || hrefLink.startsWith("/")) {
                // skip
            } else {
                totalExternalLinkCount++;
                String url = link.attr("href");
                if (isURLAccessible(url)) {
                    externalLinksMap.put(url, "ACCESSIBLE");
                    Logger.info("URL "+url+ " ACCESSIBLE");
                } else {
                    externalLinksMap.put(url, "NOT ACCESSIBLE");
                }
            }
        }

        linkModel.setTotalInternalLinks(internalLinks.toArray().length);
        linkModel.setTotalExternalLinks(totalExternalLinkCount);
        linkModel.setInteralLinks(internalLinksMap);
        linkModel.setExternalLinks(externalLinksMap);

        return linkModel;
    }

    /**
     * @param htmlVersion HTML Version string
     * @return String
     */
    private String sanitiseHtmlVersion(String htmlVersion) {
        htmlVersion = htmlVersion.replace("//W3C//DTD", "");
        htmlVersion = htmlVersion.replace("//EN", "");

        return htmlVersion;
    }

    /**
     *
     * @param url URL
     * @return boolean
     */
    private Boolean isURLAccessible(String url) {
        // Check whether the link is accessible
        try {
            Connection connection = Jsoup.connect(url).userAgent("Mozilla").timeout(3000);
            connection.ignoreHttpErrors(true);
            Document doc = connection.get();
            if (connection.response().statusCode() != 200) {
                return false;
            } else {
                return true;
            }
        } catch (IOException e) {
            e.getMessage();
        }

        return false;
    }
}
