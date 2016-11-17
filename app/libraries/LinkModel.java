package libraries;

import java.util.Map;

public class LinkModel {
    private Integer totalInternalLinks;
    private Integer totalExternalLinks;
    private Map<String, String> interalLinks;
    private Map<String, String> externalLinks;

    public Integer getTotalInternalLinks() {
        return totalInternalLinks;
    }

    public void setTotalInternalLinks(Integer totalInternalLinks) {
        this.totalInternalLinks = totalInternalLinks;
    }

    public Integer getTotalExternalLinks() {
        return totalExternalLinks;
    }

    public void setTotalExternalLinks(Integer totalExternalLinks) {
        this.totalExternalLinks = totalExternalLinks;
    }

    public Map<String, String> getInteralLinks() {
        return interalLinks;
    }

    public void setInteralLinks(Map<String, String> interalLinks) {
        this.interalLinks = interalLinks;
    }

    public Map<String, String> getExternalLinks() {
        return externalLinks;
    }

    public void setExternalLinks(Map<String, String> externalLinks) {
        this.externalLinks = externalLinks;
    }

    public Integer getTotal()
    {
        return getTotalExternalLinks() + getTotalInternalLinks();
    }
}
