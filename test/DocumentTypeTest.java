import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.DocumentType;
import org.jsoup.nodes.Node;
import play.Logger;

import java.util.List;

public class DocumentTypeTest {

    /**
     * Example to test HTML4 document type with Public ID
     */
    private void html4()
    {
        StringBuffer html = new StringBuffer();

        html.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd");
        html.append("<html lang=\"en\">");
        html.append("<head>");
        html.append("<meta charset=\"UTF-8\" />");
        html.append("<title>ImmobilienScout24/title>");
        html.append("<meta name=\"description\" content=\"Das gr&ouml;&szlig;te Angebot an Immobilien finden Sie bei ImmobilienScout24. Jetzt einfach, effizient und stressfrei Wohnungen &amp; H&auml;user mieten, kaufen oder anbieten\" />");
        html.append("<meta name=\"keywords\" content=\"Immobilien, Wohnungen, Wohnung, Immobilie, Mietwohnung, Eigentumswohnung, Grundst&uuml;ck, Haus, H&auml;user, mieten, kaufen\" />");
        html.append("</head>");
        html.append("<body>");
        html.append("<div id='color'>This is red</div> />");
        html.append("</body>");
        html.append("</html>");
        Document doc = Jsoup.parse(html.toString());
        List<Node> nodes = doc.childNodes();
        String htmlVersion= "HTML 5";
        for (Node node : nodes) {
            if (node instanceof DocumentType) {
                DocumentType documentType = (DocumentType)node;
                String publicid = documentType.attr("publicid");
                if (!publicid.isEmpty()) {
                    htmlVersion = documentType.attr("publicid");
                }
                Logger.info("HTML VERSION = "+htmlVersion);
            }
        }
    }
}
