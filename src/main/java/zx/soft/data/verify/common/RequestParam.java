package zx.soft.data.verify.common;

import java.io.Serializable;
import java.util.List;

/**
 * 验证数据需要的参数
 */
public class RequestParam implements Serializable {

    private static final long serialVersionUID = 3371695124531477865L;

    private String filename;
    private List<Record> recs;

    public RequestParam() {}
    
    public RequestParam(String filename, List<Record> recs) {
        super();
        this.filename = filename;
        this.recs = recs;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public List<Record> getRecs() {
        return recs;
    }

    public void setRecs(List<Record> recs) {
        this.recs = recs;
    }

}
