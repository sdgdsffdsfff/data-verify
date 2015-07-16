package zx.soft.data.verify.io;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import zx.soft.data.verify.common.VerifiedData;
import zx.soft.data.verify.common.VerifiedDataCollection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MysqlClient {

	private JdbcTemplate jdbcTemplate;

	public MysqlClient(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public VerifiedDataCollection get(final String filename, int start, int rows) {
		final Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		List<VerifiedData> data = jdbcTemplate.query(
				"SELECT `keyword`,`record` FROM `verified_data` WHERE `filename` = ? LIMIT ?, ?", new Object[] {
						filename, start, rows }, new RowMapper<VerifiedData>() {
					@Override
					public VerifiedData mapRow(ResultSet rs, int rowNum) throws SQLException {
						String keyword = rs.getString("keyword");
						String json = rs.getString("record");
						Map<?, ?> record = gson.fromJson(json, Map.class);
						VerifiedData data = new VerifiedData(filename, keyword, record);
						return data;
					}
				});

		int total = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM `verified_data` WHERE `filename` = ?",
				Integer.class, filename);

		VerifiedDataCollection co = new VerifiedDataCollection(total, data);

		return co;
	}

	public void write(String filename, String keyword, String json) throws WriteException {
		if (null == json)
			return;
		jdbcTemplate.update(
				"INSERT INTO `verified_data` (`filename`,`keyword`,`record`,`lasttime`) VALUES (?,?,?,NOW())",
				filename, keyword, json);

	}

}
