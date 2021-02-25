package org.ohnkyta.AoLiGeiTool;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.server.ServerStartCallback;
import net.fabricmc.fabric.api.event.server.ServerStopCallback;

public class AoLiGeiTool implements ModInitializer {


	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		BackUp.register();
		Rollback.register();
		Help.register();
		zzz.register();
		List.register();
		GuguList.register();

		System.out.println("BackupTool loaded!");
		ServerStartCallback.EVENT.register(ThunderNotifier::logWeather);
		ServerStopCallback.EVENT.register(ThunderNotifier::unloadWeather);
	}


}
