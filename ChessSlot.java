import java.util.*;

public class ChessSlot
{
	private ChessPiece Piece = null;
	private Position SlotPosition;
	
	public ChessSlot(Position SlotPosition)
	{
		this.SlotPosition = SlotPosition;
	}
	
	public ChessPiece GetChessPiece()
	{
		return this.Piece; 
	}
	
	public boolean RemoveChessPiece()
	{
		if (this.Piece == null)
		{
			return false;
		}
		this.Piece = null;
		return true;
	}
	
	public boolean SetChessPiece(ChessPiece NewPiece, boolean Overwrite)
	{
		if (this.Piece == null)
		{
			if (Overwrite)
			{
				this.Piece = new ChessPiece(NewPiece);
			}
			return false;
		}
		this.Piece = new ChessPiece(NewPiece);
		return true;
	}
	
	public void SetChessPiece(ChessPiece NewPiece)
	{
		SetChessPiece(NewPiece, true);
	}
	
	public Position GetPosition()
	{
		return this.SlotPosition;
	}
	
	public boolean equals(ChessSlot OtherSlot)
	{
		return (OtherSlot.GetPosition().equals(this.SlotPosition));
	}
}