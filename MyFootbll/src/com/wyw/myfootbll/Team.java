package com.wyw.myfootbll;

import java.util.ArrayList;

public class Team {

	public static final int MAX_PALYER_NUM = 11;
	
	public static final int TEAM_TYPE_NONE = 0;
	public static final int TEAM_TYPE_TOP = 1;
	public static final int TEAM_TYPE_BOTTOM = 2;
	
	public enum EPosType
	{
		ptForward,
		ptMidfield,
		ptFullback,
		ptGoalkeeper,
		ptCount,
	}
	
	public ArrayList<Player> players = new ArrayList<Player>(MAX_PALYER_NUM);
	public ArrayList<Player> forwards = new ArrayList<Player>();
	public ArrayList<Player> midfields = new ArrayList<Player>();
	public ArrayList<Player> fullbacks = new ArrayList<Player>();
	public ArrayList<Player> goalkeepers = new ArrayList<Player>(); // 正常只會有一個
	public int teamType =  TEAM_TYPE_NONE;
	
	Team(int teamType){
		for (int i = 0; i < MAX_PALYER_NUM; i++)
			players.add(new Player(teamType, Player.RADIUS));
	
		this.teamType = teamType;
	}
	
	public void setPosNum(int forwardNum, int midfieldNum, int fullbackNum){
		int total = forwardNum + midfieldNum + fullbackNum;
		if (total != 10) return;
		
		forwards.clear();
		midfields.clear();
		fullbacks.clear();
		goalkeepers.clear();
		
		int index = 0;
		for (int i = 0; i < forwardNum; i++)
		{
			forwards.add(players.get(index));
			index++;
		}
		
		for (int i = 0; i < midfieldNum; i++)
		{
			midfields.add(players.get(index));
			index++;
		}

		for (int i = 0; i < fullbackNum; i++)
		{
			fullbacks.add(players.get(index));
			index++;
		}	

		goalkeepers.add(players.get(index));
	}
	
	
	
}
