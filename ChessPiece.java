import java.util.*;

public class ChessPiece
{
	private String PieceName;
	private Player PieceOwner;
	private boolean IsType = false;
	
	//Default constructor
	public ChessPiece(String PieceName, Player PieceOwner)
	{
		this.PieceName = PieceName;
		this.PieceOwner = PieceOwner;
	}
	
	//Constructor for copy of piece
	public ChessPiece(ChessPiece CopyPiece)
	{
		this.PieceName = CopyPiece.GetName();
		if (CopyPiece.GetType())
		{
			this.IsType = true;
		}
		else
		{
			this.PieceOwner = CopyPiece.GetPlayer();
		}
	}
	
	//Constructor for type
	public ChessPiece(String PieceName)
	{
		this.PieceName = PieceName;
		this.IsType = true;
	}
	
	//=====Getter=====//
	public String GetName()
	{
		return this.PieceName;
	}
	
	public Player GetPlayer()
	{
		if (!IsType)
		{
			return this.PieceOwner;
		}
		return null;
	}
	
	public boolean GetType()
	{
		return this.IsType;
	}
	
	//=====Setter=====//
	public void SetName(String PieceName)
	{
		this.PieceName = PieceName;
	}
	
	public void SetPlayer(Player NewOwner)
	{
		this.PieceOwner = NewOwner;
	}
}