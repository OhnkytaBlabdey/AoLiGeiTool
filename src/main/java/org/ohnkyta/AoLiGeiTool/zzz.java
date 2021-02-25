package org.ohnkyta.AoLiGeiTool;

import com.mojang.brigadier.Command;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.network.MessageType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Util;

public class zzz {
	public static void register() {
		CommandRegistrationCallback.EVENT.register(
				(commandDispatcher, b) -> commandDispatcher.register(
						CommandManager.literal("zzz")
								.executes(ctx -> {
											String name = ctx.getSource().getPlayer().getName().getString();
											Text helpMessage = new LiteralText(
													name + "喊你睡觉觉啦！"
											);
											ctx.getSource().getMinecraftServer().getPlayerManager()
													.broadcastChatMessage(helpMessage,
															MessageType.CHAT, Util.NIL_UUID);
											return Command.SINGLE_SUCCESS;
										}
								)
				));
	}
}
