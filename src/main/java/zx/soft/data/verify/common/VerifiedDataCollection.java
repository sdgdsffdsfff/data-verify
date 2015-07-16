package zx.soft.data.verify.common;

import java.util.List;

public class VerifiedDataCollection {

    private int num;
    private List<VerifiedData> records;
    
    public VerifiedDataCollection(int size, List<VerifiedData> data) {
        num = size;
        records = data;
    }
    public int getNum() {
        return num;
    }
    public void setNum(int num) {
        this.num = num;
    }
    public List<VerifiedData> getRecords() {
        return records;
    }
    public void setRecords(List<VerifiedData> records) {
        this.records = records;
    }
    
    
}
