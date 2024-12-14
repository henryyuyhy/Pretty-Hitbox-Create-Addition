package com.yu.prettyhitboxes_createaddition;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.ConfigEntry.*;

@me.shedaniel.autoconfig.annotation.Config(name = "prettyhitboxes_createaddition")
public class Config implements ConfigData {
	@Category("Features")
	@Gui.Tooltip
	public boolean hitboxesEnabledByDefault = true;

	@Category("Features")
	@Gui.Tooltip
	public boolean showBigDragonBox = true;
	@Category("Features")
	@Gui.Tooltip
	public boolean showBoundingBox = true;
	@Category("Features")
	@Gui.Tooltip
	public boolean showEyeHeight = true;
	@Category("Features")
	@Gui.Tooltip
	public boolean showEntityRotationVector = true;
	@Category("Features")
	@Gui.Tooltip
	public boolean differentColorWhenTargeted = false;
	@Category("Features")
	@Gui.Tooltip
	public boolean showItemHitboxes = true;
	@Category("Features")
	@Gui.Tooltip
	public boolean showItemFrameHitboxes = true;
	@Category("Features")
	@Gui.Tooltip
	public boolean showPaintingHitboxes = true;
	@Category("Features")
	@Gui.Tooltip
	public boolean showBoatHitboxes = true;
	@Category("Features")
	@Gui.Tooltip
	public boolean showThrowableItemHitboxes = true;
	@Category("Features")
	@Gui.Tooltip
	public boolean showContraptionHitboxes = true; //code added by yu
	@Category("Features")
	@Gui.Tooltip
	public boolean showSeatHitboxes = true;


	@Category("Colors")
	@Gui.CollapsibleObject
	public Color boundingBoxColor = new Color(255, 255, 255);
	@Category("Colors")
	@Gui.CollapsibleObject
	public Color dragonPartColor = new Color(0, 255, 0);
	@Category("Colors")
	@Gui.CollapsibleObject
	public Color eyeHeightColor = new Color(255, 0, 0);
	@Category("Colors")
	@Gui.CollapsibleObject
	public Color entityRotationVectorColor = new Color(0, 0, 255);
	@Category("Colors")
	@Gui.CollapsibleObject
	public Color entityTargetedColor = new Color(100, 100, 100);
	@Category("Colors")
	@Gui.CollapsibleObject
	public Color itemHitboxColor = new Color(255, 255, 255);


	public static class Color {
		public Color(int r, int g, int b) {
			this.red = r;
			this.green = g;
			this.blue = b;
		}

		@Gui.Tooltip
		public int red;
		@Gui.Tooltip
		public int green;
		@Gui.Tooltip
		public int blue;
		@Gui.Tooltip
		public int alpha = 100;
	}

	@Override
	public void validatePostLoad() throws ValidationException {

	}
}
