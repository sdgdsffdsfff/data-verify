package zx.soft.data.verify.common;

import java.io.Serializable;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class RecordInfoCollection implements Serializable {

    /**
      * 
      */
    private static final long serialVersionUID = 6786843073412454964L;

    private int num;

    private List<RecordInfo> records;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public List<RecordInfo> getRecords() {
        return records;
    }

    public void setRecords(List<RecordInfo> records) {
        this.records = records;
    }

    public String toString() {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String json = gson.toJson(this);
        return json;
    }
}
