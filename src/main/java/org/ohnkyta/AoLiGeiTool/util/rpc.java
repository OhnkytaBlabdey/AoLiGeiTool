package org.ohnkyta.AoLiGeiTool.util;

import org.junit.jupiter.api.Test;
import org.ohnkyta.AoLiGeiTool.model.Backup;

import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class rpc {
	public static String json(String httpUrl, String json) {
		// dump log to console
		System.out.println("post to " + httpUrl + " with " + json);
		HttpURLConnection connection = null;
		InputStream is = null;
		OutputStream os = null;
		BufferedReader br = null;
		String result = null;
		try {
			URL url = new URL(httpUrl);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(15000);
			connection.setReadTimeout(60000);

			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestProperty("Content-Type", "application/json");
			os = connection.getOutputStream();
			os.write(json.getBytes());
			if (connection.getResponseCode() == 200) {

				is = connection.getInputStream();
				br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

				StringBuilder sbf = new StringBuilder();
				String temp;
				while ((temp = br.readLine()) != null) {
					sbf.append(temp);
					sbf.append("\r\n");
				}
				result = sbf.toString();
			}
		} catch (ConnectException e) {
			System.err.println(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != br) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != os) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != is) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (connection != null)
				connection.disconnect();
		}
		return result;
	}


	@Test
	public void testJson() {
		Backup backUp = new Backup("qwq", "me");
		String json = backUp.toString();
		System.out.println(json);
		String result = rpc.json("http://localhost:9999", json + "..");
		System.out.println(result);
	}
}
