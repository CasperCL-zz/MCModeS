package net.zhuoweizhang.pocketinveditor.entity;

public class PlayerAbilities {
	public boolean mayFly = false;
	public boolean flying = false;
	public boolean instabuild = false;
	public boolean invulnerable = false;

	public void initForGameType(int gameType) {
		boolean creative = gameType == 1;
		mayFly = instabuild = invulnerable = creative;
		flying = flying && creative;
	}

	public void setSurvival() {
		mayFly = false;
		flying = false;
		instabuild = false;
		invulnerable = false;
	}

	public void setCreative() {
		mayFly = true;
		flying = false;
		instabuild = true;
		invulnerable = true;
	}
}
