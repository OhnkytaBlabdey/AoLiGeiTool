package org.ohnkyta.AoLiGeiTool;

import com.mojang.brigadier.Command;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

public class Help {
	public static void register() {
		CommandRegistrationCallback.EVENT.register(
				(commandDispatcher, b) -> commandDispatcher.register(
						CommandManager.literal("helpme")
								.executes(ctx -> {
											Text helpMessage = new LiteralText(
													"Usage:\n" +
															"/backup <tips>   创建一个备注为<tips>的存档副本\n" +
															"/backuplist       显示最近的存档副本\n" +
															"/helpme            显示此条帮助信息\n" +
															"/rollback <seq>  关闭服务器，并回到指定的第<seq>个存档，注意这会丢失当前进度！\n" +
															"/zzz                向服务器的其他人发送睡觉邀请\n"
											);
											ctx.getSource().sendFeedback(helpMessage,
													false);
											return Command.SINGLE_SUCCESS;
										}
								)
				));
	}
}
