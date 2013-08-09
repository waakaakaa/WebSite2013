package cn.zx.website.servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.zx.website.domain.SystemInfo;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/sys")
public class SystemInfoServlet extends HttpServlet {
	private final static Log log = LogFactory.getLog(SystemInfoServlet.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			int[] m = getMemInfo();
			SystemInfo si = new SystemInfo(m[0], m[0] - m[1], m[2],
					m[2] - m[3], getCpuInfo(), getTemperature());
			log.info("SystemInfo = " + si);
			JSONObject json = JSONObject.fromObject(si);
			PrintWriter out = resp.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
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
	public static int[] getMemInfo() throws IOException, InterruptedException {
		File file = new File("/proc/meminfo");
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(file)));
		int[] result = new int[4];
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

		return result;
	}

	/**
	 * get memory by used info
	 * 
	 * @return float efficiency
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static float getCpuInfo() throws IOException, InterruptedException {
		File file = new File("/proc/stat");
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(file)));
		StringTokenizer token = new StringTokenizer(br.readLine());
		token.nextToken();
		int user1 = Integer.parseInt(token.nextToken());
		int nice1 = Integer.parseInt(token.nextToken());
		int sys1 = Integer.parseInt(token.nextToken());
		int idle1 = Integer.parseInt(token.nextToken());

		Thread.sleep(1000);

		br = new BufferedReader(
				new InputStreamReader(new FileInputStream(file)));
		token = new StringTokenizer(br.readLine());
		token.nextToken();
		int user2 = Integer.parseInt(token.nextToken());
		int nice2 = Integer.parseInt(token.nextToken());
		int sys2 = Integer.parseInt(token.nextToken());
		int idle2 = Integer.parseInt(token.nextToken());

		return (float) ((user2 + sys2 + nice2) - (user1 + sys1 + nice1))
				/ (float) ((user2 + nice2 + sys2 + idle2) - (user1 + nice1
						+ sys1 + idle1));
	}
}