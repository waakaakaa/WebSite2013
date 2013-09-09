package cn.zx.website.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.zx.website.annotation.ActionUrl;
import cn.zx.website.domain.SystemInfo;

public class SystemInfoAction extends Action {
	private final static Log log = LogFactory.getLog(SystemInfoAction.class);

	@ActionUrl(path = "/sys/get")
	public void add(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		int[] m = getMemInfo();
		SystemInfo si = new SystemInfo(m[0], m[0] - m[1], m[2], m[2] - m[3],
				getCpuInfo(), getTemperature());
		log.info("SystemInfo = " + si);
		sendJSONFromObject(si, resp);
	}

	public static float getTemperature() throws IOException {
		File file = new File("/home/pi/timertask/SystemInfo/log");
		List<String> lines = FileUtils.readLines(file, "UTF-8");
		String t = lines.get(0).split("=")[1].split("'")[0];
		return Float.valueOf(t);
	}

	/**
	 * get memory by used info
	 * 
	 * @return int[] result
	 *         result.length==4;int[0]=MemTotal;int[1]=MemFree;int[2
	 *         ]=SwapTotal;int[3]=SwapFree;
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static int[] getMemInfo() {
		int[] result = new int[4];
		BufferedReader br = null;
		try {
			File file = new File("/proc/meminfo");
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					file)));
			String str = null;
			StringTokenizer token = null;
			while ((str = br.readLine()) != null) {
				token = new StringTokenizer(str);
				if (!token.hasMoreTokens())
					continue;

				str = token.nextToken();
				if (!token.hasMoreTokens())
					continue;

				if (str.equalsIgnoreCase("MemTotal:"))
					result[0] = Integer.parseInt(token.nextToken());
				else if (str.equalsIgnoreCase("MemFree:"))
					result[1] = Integer.parseInt(token.nextToken());
				else if (str.equalsIgnoreCase("SwapTotal:"))
					result[2] = Integer.parseInt(token.nextToken());
				else if (str.equalsIgnoreCase("SwapFree:"))
					result[3] = Integer.parseInt(token.nextToken());
			}
			if (br != null) {
				br.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return result;
	}

	/**
	 * get memory by used info
	 * 
	 * @return float efficiency
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static float getCpuInfo() {
		int user1 = 0, nice1 = 0, sys1 = 0, idle1 = 0;
		BufferedReader br = null;
		try {
			File file = new File("/proc/stat");

			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					file)));
			StringTokenizer token = new StringTokenizer(br.readLine());
			token.nextToken();
			user1 = Integer.parseInt(token.nextToken());
			nice1 = Integer.parseInt(token.nextToken());
			sys1 = Integer.parseInt(token.nextToken());
			idle1 = Integer.parseInt(token.nextToken());

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return (float) (user1 + sys1 + nice1)
				/ (float) (user1 + nice1 + sys1 + idle1);
	}
}