import java.util.*;
import java.awt.Color;

public class Player
{
	private String PlayerName;
	private Color PlayerColor;
	private int KillCount = 0;
	
	public Player(String PlayerName, Color PlayerColor)
	{
		this.PlayerName = PlayerName;
		this.PlayerColor = PlayerColor;
	}
	
	//=====Getter=====//
	public Color GetColor()
	{
		return this.PlayerColor;
	}
	
	public String GetName()
	{
		return this.PlayerName;
	}

	public int GetKill()
	{
		return this.KillCount;
	}
	
	//=====Setter=====//
	public void SetColor(Color PlayerColor)
	{
		this.PlayerColor = PlayerColor;
	}
	
	public void AddKill()
	{
		this.KillCount += 1;
	}
	
	public void SubKill()
	{
		if (this.KillCount > 0)
		{
			this.KillCount -= 1;
		}
	}
}