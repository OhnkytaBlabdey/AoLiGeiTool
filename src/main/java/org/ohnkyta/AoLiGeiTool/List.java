package org.ohnkyta.AoLiGeiTool;

import com.mojang.brigadier.Command;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import org.ohnkyta.AoLiGeiTool.util.Config;
import org.ohnkyta.AoLiGeiTool.util.rpc;

public class List {
	public static void register() {
		CommandRegistrationCallback.EVENT.register(
				(commandDispatcher, b) -> commandDispatcher.register(
						CommandManager.literal("backuplist")
								.executes(ctx -> {
											String name = ctx.getSource().getPlayer().getName().getString();
											String json = new org.ohnkyta.AoLiGeiTool.model.List(name).toString();
											rpc.json(Config.get("rpc_url"), json);
											return Command.SINGLE_SUCCESS;
										}
								)

				));
	}
}
