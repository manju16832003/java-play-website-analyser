package libraries;

import java.util.Map;

public class HTagModel {
    private Integer total;
    private Map<String, Integer> hTags;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Map<String, Integer> gethTags() {
        return hTags;
    }

    public void sethTags(Map<String, Integer> hTags) {
        this.hTags = hTags;
    }
}
