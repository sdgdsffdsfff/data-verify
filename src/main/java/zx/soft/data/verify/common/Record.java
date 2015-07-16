package zx.soft.data.verify.common;

public class Record {
    private String recordId;
    private String keyword;
//    private String url;

    public String getRecordId() {
        return recordId;
    }

    public Record() {};
    
    public Record(String recordId, String keyword) {
        super();
        this.recordId = recordId;
        this.keyword = keyword;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

//    public String getUrl() {
//        return url;
//    }
//
//    public void setUrl(String url) {
//        this.url = url;
//    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
