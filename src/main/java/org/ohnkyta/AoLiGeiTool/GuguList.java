package org.ohnkyta.AoLiGeiTool;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import org.ohnkyta.AoLiGeiTool.util.Config;
import org.ohnkyta.AoLiGeiTool.util.rpc;

import static com.mojang.brigadier.arguments.IntegerArgumentType.getInteger;
import static com.mojang.brigadier.arguments.IntegerArgumentType.integer;
import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class GuguList {
	private static final String rpcUrl = Config.get("rpc_url");

	public static void register() {
		CommandRegistrationCallback.EVENT.register(
				(commandDispatcher, b) -> commandDispatcher.register(
						CommandManager.literal("gugulist")
								.then(literal("add").then(
										argument("desc", StringArgumentType.greedyString())
												.executes(
														ctx -> {
															String name = ctx.getSource().getPlayer().getName().getString();
															String desc = getString(ctx, "desc");
															String json = new org.ohnkyta.AoLiGeiTool.model.TodoItem(name, desc, "add").toString();
															rpc.json(rpcUrl, json);
															return Command.SINGLE_SUCCESS;
														}
												)
										)
								)
								.then(literal("done").then(
										argument("index", IntegerArgumentType.integer(0))
												.executes(
														ctx -> {
															String name = ctx.getSource().getPlayer().getName().getString();
															String index = Integer.toString(getInteger(ctx, "index"));
															String json = new org.ohnkyta.AoLiGeiTool.model.TodoItem(name, index, "done").toString();
															rpc.json(rpcUrl, json);
															return Command.SINGLE_SUCCESS;
														}
												)
										)
								)
								.then(literal("list").executes(
										ctx -> {
											String name = ctx.getSource().getPlayer().getName().getString();
											String json = new org.ohnkyta.AoLiGeiTool.model.TodoItem(name, "list").toString();
											rpc.json(rpcUrl, json);
											return Command.SINGLE_SUCCESS;
										}
										)
								)
				)
		);
	}
}
