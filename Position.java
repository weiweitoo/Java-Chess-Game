import java.util.*;

public class Position
{
	private int XPos;
	private int YPos;
	private boolean IsRelative = false;
	
	//Default constructor
	public Position(int x, int y)
	{
		this.XPos = x;
		this.YPos = y;
	}
	
	//Constructor for relative position
	public Position(int x, int y, boolean IsRelative)
	{
		this.IsRelative = IsRelative;
		this.XPos = x;
		this.YPos = y;
	}
	
	//Make 2D coordiante of position into 1D integer
	public int GetFlat(int XMax, int YMax)
	{
		if(!IsRelative)
		{
			int TempCoor = XMax*this.YPos + this.XPos;
			if ((this.XPos >= XMax)&&(this.YPos >= YMax))
			{
				return -1;
			}
			return TempCoor;
		}
		return -1;
	}
	
	//=====Getter=====//
	public int GetX()
	{
		return this.XPos;
	}
	
	public int GetY()
	{
		return this.YPos;
	}
	
	public boolean GetRelative()
	{
		return this.IsRelative;
	}
	
	//=====Math functions for position=====//
	//Check if position type and value is equals
	public boolean equals(Position OtherPos)
	{
		if (this.IsRelative == OtherPos.GetRelative())
		{
			if ((this.XPos == OtherPos.GetX())&&(this.YPos == OtherPos.GetY()))
			{
				return true;
			}
		}
		return false;
	}
	
	//Add relative position into static position
	public Position add(Position OtherPos)
	{
		if (OtherPos.GetRelative())
		{
			int TXPos = this.XPos;
			int TYPos = this.YPos;
			
			TXPos += OtherPos.GetX();
			TYPos += OtherPos.GetY();
			
			return new Position(TXPos, TYPos);
		}
		return null;
	}
}