package zx.soft.data.verify.api;

import zx.soft.data.verify.common.RequestParam;
import zx.soft.data.verify.common.RetCode;
import zx.soft.data.verify.common.VerifiedDataCollection;

public interface BotManager {

	public RetCode upload(RequestParam param);

	public VerifiedDataCollection download(String filename, int start, int rows);

}
