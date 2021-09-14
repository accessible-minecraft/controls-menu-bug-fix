package net.shoaibkhan.cmbf;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;

import java.awt.*;
import java.awt.event.InputEvent;

public class MainClass implements ClientModInitializer {
	private String screenTitle = "";
	@Override
	public void onInitializeClient() {
		System.setProperty("java.awt.headless","false");
		ClientTickEvents.END_CLIENT_TICK.register(this::clientTickEventsMethod);
	}

	private void clientTickEventsMethod(MinecraftClient minecraftClient) {
		if (minecraftClient.currentScreen != null) {
			String title = minecraftClient.currentScreen.getTitle().getString();
			if (title.equalsIgnoreCase("game menu")
					|| title.equalsIgnoreCase("controls")
					|| title.equalsIgnoreCase("options")
					|| title.equalsIgnoreCase("title screen")
					|| title.equalsIgnoreCase("video settings")) {

				if (!screenTitle.equalsIgnoreCase(title)) {
					moveMouseCursor(minecraftClient);
					screenTitle = title;
				}

				boolean isRPressed = (InputUtil.isKeyPressed(minecraftClient.getWindow().getHandle(),
						InputUtil.fromTranslationKey("key.keyboard.r").getCode()));
				if(isRPressed)
					moveMouseCursor(minecraftClient);
			}
		}
	}

	private void moveMouseCursor(MinecraftClient minecraftClient) {
		Robot mouse;
		try {
			mouse = new Robot();
			if (minecraftClient.options.fullscreen) {
				mouse.mouseMove(1, 1);
				mouse.mousePress(InputEvent.BUTTON1_DOWN_MASK);
				mouse.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
			} else {
				int x = minecraftClient.getWindow().getX();
				int y = minecraftClient.getWindow().getY();
				mouse.mouseMove(x + 1, y + 1);
				mouse.mousePress(InputEvent.BUTTON1_DOWN_MASK);
				mouse.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
			}
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
}