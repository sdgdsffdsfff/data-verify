package zx.soft.data.verify.common;

import java.io.Serializable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class RecordInfo implements Serializable, Cloneable {

	private static final long serialVersionUID = -3811740582144807331L;

	private String id = "";
	private int platform = 0;
	private String mid = "";
	private String username = "";
	private String nickname = "";
	private String original_id = "";
	private String original_uid = "";
	private String original_name = "";
	private String original_title = "";
	private String original_url = "";
	private String url = "";
	private String home_url = "";
	private String title = "";
	//        private int type_ids = 0;
	private String type = "";
	private boolean isharmful;
	private String content = "";
	private int comment_count = 0;
	private int read_count = 0;
	private int favorite_count;
	private int attitude_count = 0;
	private int repost_count = 0;
	private String video_url = "";
	private String pic_url = "";
	private String voice_url = "";
	private long timestamp = 0;
	private int source_id = 0;
	private long lasttime = 0;
	private int server_id = 0;
	private long identify_id = 0;
	private String identify_md5 = "xiayun";
	private String keyword = "";
	private long first_time = 0;
	private long update_time = 0;
	private String ip = "";
	private String location = "";
	private String geo = "";
	private String receive_addr = "";
	private String append_addr = "";
	private String send_addr = "";
	private String source_name = "";
	private int source_type = 0;
	private int country_code = 0;
	private int location_code = 0;
	private int province_code = 0;
	private int city_code = 0;

	public RecordInfo(int source_id, String type, int server_id, String identify_md5, String keyword, String ip,
			String location, String source_name, int source_type, int country_code, int location_code,
			int province_code, int city_code) {
		super();
		this.type = type;
		this.server_id = server_id;
		this.identify_md5 = identify_md5;
		this.keyword = keyword;
		this.ip = ip;
		this.location = location;
		this.source_name = source_name;
		this.source_type = source_type;
		this.country_code = country_code;
		this.location_code = location_code;
		this.province_code = province_code;
		this.city_code = city_code;
		this.source_id = source_id;

		this.timestamp = System.currentTimeMillis();
		this.lasttime = timestamp;
		this.first_time = this.lasttime;
	}

	@Override
	public RecordInfo clone() {
		try {
			return (RecordInfo) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return new RecordInfo();
		}
	}

	public RecordInfo(String url,/* int sectionId, */String type, int platform, String ip, int country_code,
			int province_code, int city_code, int location_code, String location, int source_id, String source_name,
			int server_id, int source_type) {
		this.timestamp = System.currentTimeMillis();
		this.lasttime = timestamp;
		this.first_time = this.lasttime;
		this.url = url;
		//                this.type_ids = sectionId;
		this.type = type;
		this.platform = platform;
		this.ip = ip;
		this.country_code = country_code;
		this.province_code = province_code;
		this.city_code = city_code;
		this.location_code = location_code;
		this.location = location;
		this.source_id = source_id;
		this.source_name = source_name;
		this.server_id = server_id;
		this.source_type = source_type;
	}

	public RecordInfo() {
		this.lasttime = System.currentTimeMillis();
		this.first_time = this.lasttime;
	}

	@Override
	public String toString() {
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		String json = gson.toJson(this);
		return json;
	}

	//        public int getType_ids() {
	//                return type_ids;
	//        }
	//
	//        public void setType_ids(int type_ids) {
	//                this.type_ids = type_ids;
	//        }

	public int getFavorite_count() {
		return favorite_count;
	}

	public void setFavorite_count(int favorite_count) {
		this.favorite_count = favorite_count;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getPlatform() {
		return platform;
	}

	public void setPlatform(int platform) {
		this.platform = platform;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getOriginal_id() {
		return original_id;
	}

	public void setOriginal_id(String original_id) {
		this.original_id = original_id;
	}

	public String getOriginal_uid() {
		return original_uid;
	}

	public void setOriginal_uid(String original_uid) {
		this.original_uid = original_uid;
	}

	public String getOriginal_name() {
		return original_name;
	}

	public void setOriginal_name(String original_name) {
		this.original_name = original_name;
	}

	public String getOriginal_title() {
		return original_title;
	}

	public void setOriginal_title(String original_title) {
		this.original_title = original_title;
	}

	public String getOriginal_url() {
		return original_url;
	}

	public void setOriginal_url(String original_url) {
		this.original_url = original_url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getHome_url() {
		return home_url;
	}

	public void setHome_url(String home_url) {
		this.home_url = home_url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isIsharmful() {
		return isharmful;
	}

	public void setIsharmful(boolean isharmful) {
		this.isharmful = isharmful;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getComment_count() {
		return comment_count;
	}

	public void setComment_count(int comment_count) {
		this.comment_count = comment_count;
	}

	public int getRead_count() {
		return read_count;
	}

	public void setRead_count(int read_count) {
		this.read_count = read_count;
	}

	public int getAttitude_count() {
		return attitude_count;
	}

	public void setAttitude_count(int attitude_count) {
		this.attitude_count = attitude_count;
	}

	public int getRepost_count() {
		return repost_count;
	}

	public void setRepost_count(int repost_count) {
		this.repost_count = repost_count;
	}

	public String getVideo_url() {
		return video_url;
	}

	public void setVideo_url(String video_url) {
		this.video_url = video_url;
	}

	public String getPic_url() {
		return pic_url;
	}

	public void setPic_url(String pic_url) {
		this.pic_url = pic_url;
	}

	public String getVoice_url() {
		return voice_url;
	}

	public void setVoice_url(String voice_url) {
		this.voice_url = voice_url;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public int getSource_id() {
		return source_id;
	}

	public void setSource_id(int source_id) {
		this.source_id = source_id;
	}

	public long getLasttime() {
		return lasttime;
	}

	public void setLasttime(long lasttime) {
		this.lasttime = lasttime;
	}

	public int getServer_id() {
		return server_id;
	}

	public void setServer_id(int server_id) {
		this.server_id = server_id;
	}

	public long getIdentify_id() {
		return identify_id;
	}

	public void setIdentify_id(long identify_id) {
		this.identify_id = identify_id;
	}

	public String getIdentify_md5() {
		return identify_md5;
	}

	public void setIdentify_md5(String identify_md5) {
		this.identify_md5 = identify_md5;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public long getFirst_time() {
		return first_time;
	}

	public void setFirst_time(long first_time) {
		this.first_time = first_time;
	}

	public long getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(long update_time) {
		this.update_time = update_time;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getGeo() {
		return geo;
	}

	public void setGeo(String geo) {
		this.geo = geo;
	}

	public String getReceive_addr() {
		return receive_addr;
	}

	public void setReceive_addr(String receive_addr) {
		this.receive_addr = receive_addr;
	}

	public String getAppend_addr() {
		return append_addr;
	}

	public void setAppend_addr(String append_addr) {
		this.append_addr = append_addr;
	}

	public String getSend_addr() {
		return send_addr;
	}

	public void setSend_addr(String send_addr) {
		this.send_addr = send_addr;
	}

	public String getSource_name() {
		return source_name;
	}

	public void setSource_name(String source_name) {
		this.source_name = source_name;
	}

	public int getSource_type() {
		return source_type;
	}

	public void setSource_type(int source_type) {
		this.source_type = source_type;
	}

	public int getCountry_code() {
		return country_code;
	}

	public void setCountry_code(int country_code) {
		this.country_code = country_code;
	}

	public int getLocation_code() {
		return location_code;
	}

	public void setLocation_code(int location_code) {
		this.location_code = location_code;
	}

	public int getProvince_code() {
		return province_code;
	}

	public void setProvince_code(int province_code) {
		this.province_code = province_code;
	}

	public int getCity_code() {
		return city_code;
	}

	public void setCity_code(int city_code) {
		this.city_code = city_code;
	}

}
