package com.depths.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.depths.game.depths;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
	      config.title = "Depths";
	      config.width = 800;
	      config.height = 480;
		new LwjglApplication(new depths(), config);
	}
}
