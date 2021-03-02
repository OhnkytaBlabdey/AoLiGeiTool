package org.ohnkyta.AoLiGeiTool;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import org.ohnkyta.AoLiGeiTool.util.Config;
import org.ohnkyta.AoLiGeiTool.util.rpc;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.network.MessageType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Util;
import org.ohnkyta.AoLiGeiTool.model.Backup;


import java.io.IOException;
import java.util.Date;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static net.minecraft.server.command.CommandManager.argument;

public class BackUp {
	private static final String rpcUrl = Config.get("rpc_url");
	public static void register() {
		CommandRegistrationCallback.EVENT.register(
				(commandDispatcher, b) -> commandDispatcher.register(
						CommandManager.literal("backup")
								.requires((player)-> player.hasPermissionLevel(4))
								.then(argument("tips", StringArgumentType.greedyString())
										.executes(ctx -> {
													String name = ctx.getSource().getPlayer().getName().getString();
													ctx.getSource().getMinecraftServer().getPlayerManager()
															.broadcastChatMessage(new LiteralText(
																			"将要为【 " + getString(ctx, "tips") + "】创建一份存档"),
																	MessageType.CHAT, Util.NIL_UUID);
													Backup backUp = new Backup(
															getString(ctx, "tips")
																	+ "[" + new Date().toString() + "]",
															name
													);
													String json = backUp.toString();
													rpc.json(rpcUrl, json);
													return Command.SINGLE_SUCCESS;
												}
										)
								)
				));
	}

	/*
	如果依赖其他程序打包，请尽量不要依赖这个功能，因为不保证成功
	 */
	protected static void zip() throws IOException, InterruptedException {
		String zipFileName = "backups/" + new Date().toString() + ".7z";
		zipFileName = zipFileName.replace(':', '-').replace(' ', '_');
		System.out.println(zipFileName + "将要打包");
		Runtime.getRuntime().exec(new String[]{
				"7z",
				"a",
				"-t7z",
				zipFileName,
				"@backup.ini"
		}).waitFor();
		//没报错不一定是成功打包
		//TODO 接收进程的输出
	}
}
