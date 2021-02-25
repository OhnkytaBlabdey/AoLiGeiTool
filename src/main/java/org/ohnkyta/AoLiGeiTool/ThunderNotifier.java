package org.ohnkyta.AoLiGeiTool;

import net.minecraft.server.MinecraftServer;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Util;
import org.junit.jupiter.api.Test;
import org.ohnkyta.AoLiGeiTool.util.Config;
import org.ohnkyta.AoLiGeiTool.util.rpc;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class ThunderNotifier {
	private static Timer timer = new Timer();
	private static boolean isThunder = false;
	private static String salt = Config.get("thunder_salt");
	private static String notifyBotUrl = Config.get("notify_bot_url");

	public static void unloadWeather(MinecraftServer server) {
		timer.cancel();
		server.sendSystemMessage(new LiteralText("关闭打雷提醒"),
				Util.NIL_UUID);
	}

	public static void logWeather(MinecraftServer server) {
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				String weather;
				if (server.getOverworld().isThundering()) {
					weather = "thunder";
					if (isThunder) {
						return;
					} else {
						isThunder = true;
					}
				} else {
					weather = "non-thunder";
					if (!isThunder) {
						return;
					} else {
						isThunder = false;
					}
				}
				//TODO 群聊提醒
				String date = new Date().toString();
				String code = "";
				try {
					MessageDigest digest = MessageDigest.getInstance("SHA-256");
					digest.update(weather.getBytes());
					digest.update(date.getBytes());
					digest.update(salt.getBytes());
					byte[] byteBuffer = digest.digest();
					// 將 byte 轉換爲 string
					StringBuilder strHexString = new StringBuilder();
					// 遍歷 byte buffer
					for (int i = 0; i < byteBuffer.length; i++) {
						String hex = Integer.toHexString(0xff & byteBuffer[i]);
						if (hex.length() == 1) {
							strHexString.append('0');
						}
						strHexString.append(hex);
					}
					code = strHexString.toString();
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
				//
				rpc.json(notifyBotUrl,
						"{\"val\":\"" +
								weather +
								"\",\"date\":\"" +
								date
								+ "\"," +
								"\"code\":\""
								+ code
								+ "\"}");
			}
		}, 10, 60000);
	}

	@Test
	void testThunder() {
		String weather = "thunder";
		String date = new Date().toString();
		String code = "";
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			digest.update(weather.getBytes());
			digest.update(date.getBytes());
			digest.update(salt.getBytes());
			byte[] byteBuffer = digest.digest();
			// 將 byte 轉換爲 string
			StringBuilder strHexString = new StringBuilder();
			// 遍歷 byte buffer
			for (int i = 0; i < byteBuffer.length; i++) {
				String hex = Integer.toHexString(0xff & byteBuffer[i]);
				if (hex.length() == 1) {
					strHexString.append('0');
				}
				strHexString.append(hex);
			}
			code = strHexString.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		rpc.json(notifyBotUrl,
				"{\"val\":\"thunder\",\"date\":\"" +
						date
						+ "\"," +
						"\"code\":\""
						+ code
						+ "\"}");
	}
}
