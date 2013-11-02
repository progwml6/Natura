package mods.natura.plugins.nei;

import codechicken.nei.recipe.DefaultOverlayHandler;

import codechicken.nei.api.API;
import mods.natura.gui.WorkbenchGui;

public class NEICompat {

	public static void registerNEICompat(){
		API.registerGuiOverlay(WorkbenchGui.class, "crafting");
		API.registerGuiOverlayHandler(WorkbenchGui.class, new DefaultOverlayHandler(), "crafting");
	}
	
}
