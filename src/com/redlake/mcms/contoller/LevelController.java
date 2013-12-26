package com.redlake.mcms.contoller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.redlake.mcmodeswitcher.R;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import net.zhuoweizhang.pocketinveditor.Level;
import net.zhuoweizhang.pocketinveditor.io.LevelDataConverter;

public class LevelController {

	public final File WORLDS_ROOT;
	public final Context context;
	public static LevelController instance;

	private LevelController(Context context) {
		WORLDS_ROOT = new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/games/com.mojang/minecraftWorlds/");
		this.context = context;
	}

	public static LevelController getInstance(Context context) {
		if (instance == null)
			instance = new LevelController(context);
		return instance;
	}

	public List<Level> getLevels() throws IOException {
		if (!WORLDS_ROOT.exists())
			throw new FileNotFoundException();

		List<Level> levels = new ArrayList<Level>();

		for (String folderName : WORLDS_ROOT.list()) {
			if (folderName.equals(context.getString(R.string.level_cache_dir)))
				continue;
			File levelFile = new File(WORLDS_ROOT.getAbsolutePath() + "/"
					+ folderName + "/"
					+ context.getString(R.string.levelFileName));

			Level level = LevelDataConverter.read(levelFile);
			level.setRootDirectory(levelFile);
			levels.add(level);
		}

		return levels;
	}

	/**
	 * Switches between modes.
	 * 
	 * @param level
	 *            the level to switch
	 * @param newMode
	 *            the new mode. Either 0 or 1. 0 is survival and 1 is creative.
	 * @throws IOException
	 */
	public Level toggleMode(Level level) throws IOException {
		if (level.getGameType() == 0 && level.getPlayer() != null) { // Survival
			level.getPlayer().getAbilities().setCreative();
			level.setGameType(1);
		} else if (level.getGameType() == 1 && level.getPlayer() != null) {
			level.getPlayer().getAbilities().setSurvival();
			level.setGameType(0);
			level.setSpawnMobs(true);
		}

		LevelDataConverter.write(level, level.getRootDirectory());
		return level;
	}

	
	/**
	 * Switches between modes.
	 * 
	 * @param level
	 *            the level to switch
	 * @param newMode
	 *            the new mode. Either 0 or 1. 0 is survival and 1 is creative.
	 * @throws IOException
	 */
	public Level toggleMode(File levelFile) throws IOException {
		Level level = LevelDataConverter.read(levelFile);
		level.setRootDirectory(levelFile);
		
		return toggleMode(level);
	}
	
}
