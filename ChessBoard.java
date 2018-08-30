import java.util.*;

public class ChessBoard
{
	private int SizeX = 0;
	private int SizeY = 0;
	private ArrayList<ChessSlot> SlotList = new ArrayList<ChessSlot>();
	
	public ChessBoard(int SizeX, int SizeY)
	{
		this.SizeX = SizeX;
		this.SizeY = SizeY;
		
		for(int i = 0;i < this.SizeX;i++)
		{
			for(int j = 0;j < this.SizeY;j++)
			{
				SlotList.add(new ChessSlot(new Position(i,j)));
			}
		}
	}
	
	public ChessSlot GetSlot(Position PlaceCoor)
	{
		for(int i = 0;i < SlotList.size();i++)
		{
			if (SlotList.get(i).GetPosition().equals(PlaceCoor))
			{
				return SlotList.get(i);
			}
		}
		return null;
	}
	
	public boolean AddPiece(ChessPiece NewPiece, Position PlaceCoor)
	{
		ChessSlot TempSlot = GetSlot(PlaceCoor);
		if (TempSlot != null)
		{
			TempSlot.SetChessPiece(NewPiece);
			return true;
		}
		return false;
	}
	
	public boolean RemovePiece(Position PlaceCoor)
	{
		ChessSlot TempSlot = GetSlot(PlaceCoor);
		if (TempSlot != null)
		{
			return TempSlot.RemoveChessPiece();
		}
		return false;
	}
	
	public int GetSizeX()
	{
		return this.SizeX;
	}
	
	public int GetSizeY()
	{
		return this.SizeY;
	}
}