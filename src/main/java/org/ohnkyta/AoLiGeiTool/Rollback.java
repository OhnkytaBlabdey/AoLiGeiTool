package org.ohnkyta.AoLiGeiTool;

import com.mojang.brigadier.Command;
import org.ohnkyta.AoLiGeiTool.util.Config;
import org.ohnkyta.AoLiGeiTool.util.rpc;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.network.MessageType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Util;

import static com.mojang.brigadier.arguments.IntegerArgumentType.getInteger;
import static com.mojang.brigadier.arguments.IntegerArgumentType.integer;
import static net.minecraft.server.command.CommandManager.argument;


public class Rollback {
	public static void register() {
		CommandRegistrationCallback.EVENT.register(
				(commandDispatcher, b) -> commandDispatcher.register(
						CommandManager.literal("rollback")
								.requires((player)-> player.hasPermissionLevel(4))
								.then(argument("seq", integer(0, 3))
										.executes((ctx) -> {
													String name = ctx.getSource().getPlayer().getName().getString();
													ctx.getSource().getMinecraftServer().getPlayerManager()
															.broadcastChatMessage(new LiteralText(
																			"将要回滚到 " + getInteger(ctx, "seq") + "号存档"),
																	MessageType.CHAT, Util.NIL_UUID);
													org.ohnkyta.AoLiGeiTool.model.Rollback rollback = new org.ohnkyta.AoLiGeiTool.model.Rollback(getInteger(ctx, "seq"), name);
													String json = rollback.toString();
													rpc.json(Config.get("rpc_url"), json);
													return Command.SINGLE_SUCCESS;
												}
										)
								)
				));
	}
}
