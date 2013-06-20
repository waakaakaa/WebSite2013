package cn.zx.website.timertask;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import cn.zx.website.db.DaoFactory;
import cn.zx.website.domain.Weather;

public class GetHzWeatherTimerTask extends TimerTask {
	private static final String WEATHER_URL = "www.weather.com.cn";
	private static final String HANGZHOU_URL = "/data/sk/101210101.html";
	private static final String PROXY_URL = "10.12.11.105";
	private static final int PROXY_PORT = 6666;
	private static final String PROXY_USERNAME = "246";
	private static final String PROXY_PASSWD = "246";

	private final static Log log = LogFactory
			.getLog(GetHzWeatherTimerTask.class);

	@Override
	public void run() {
		String json = get();
		Weather weather = new Weather(parseTime(json), parseHumidity(json),
				parseTemperature(json));
		log.info("get weather --> " + weather);
		DaoFactory.getWeatherDao().createHz(weather);
	}

	private static String get() {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		try {
			httpclient.getCredentialsProvider().setCredentials(
					new AuthScope(PROXY_URL, PROXY_PORT),
					new UsernamePasswordCredentials(PROXY_USERNAME,
							PROXY_PASSWD));

			HttpHost targetHost = new HttpHost(WEATHER_URL);
			HttpHost proxy = new HttpHost(PROXY_URL, PROXY_PORT);
			httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
					proxy);
			HttpGet httpget = new HttpGet(HANGZHOU_URL);
			HttpResponse response = httpclient.execute(targetHost, httpget);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				return EntityUtils.toString(entity);
			}
			EntityUtils.consume(entity);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		return null;
	}

	private static int parseTemperature(String json) {
		return Integer.parseInt(json.split("\"temp\":\"")[1].split("\"")[0]);
	}

	private static int parseHumidity(String json) {
		return Integer.parseInt(json.split("\"SD\":\"")[1].split("%")[0]);
	}

	private static Timestamp parseTime(String json) {
		String time = json.split("\"time\":\"")[1].split("\"")[0];
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		date.setHours(Integer.valueOf(time.split(":")[0]));
		date.setMinutes(Integer.valueOf(time.split(":")[1]));
		date.setSeconds(0);
		String t = sdf.format(date);
		return Timestamp.valueOf(t);
	}
}