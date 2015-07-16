package zx.soft.data.verify.api;

import java.util.HashMap;
import java.util.Map;

import org.restlet.data.Form;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import zx.soft.data.verify.common.RequestParam;
import zx.soft.data.verify.common.RetCode;
import zx.soft.data.verify.common.VerifiedDataCollection;

public class BotResource extends ServerResource {

    @Get("json")
    public Object download() throws Exception {
        String filename = (String) getRequestAttributes().get("filename");
        int start = 0;
        int rows = 100;
        Form form = getQuery();
        if (form != null) {
            filename = form.getFirstValue("filename");
            start = Integer.valueOf(form.getFirstValue("start", "0").trim());
            rows = Integer.valueOf(form.getFirstValue("rows", "100").trim());
        }
        
        if (filename == null || filename.trim().length() == 0) {
            return new RetCode(-1, "no filename", null);
        }
        
        VerifiedDataCollection co = BotApp.botMgr.download(filename, start, rows);
        return new RetCode(0, "success", co);
    }

    /**
     * 创建新任务
     */
    @Post("json")
    public Object upload(RequestParam param) throws Exception {
        RetCode code = BotApp.botMgr.upload(param);
        return code;
    }
}
