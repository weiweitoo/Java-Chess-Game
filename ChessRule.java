import java.util.*;
import java.lang.*;

abstract public class ChessRule
{
	protected ArrayList<ChessMove> SetList = new ArrayList<ChessMove>();
	protected ArrayList<ChessPiece> ChessType = new ArrayList<ChessPiece>();
	
	public ChessRule(){}
	
	public boolean AddChessType(String Name)
	{
		for(int i = 0;i < ChessType.size();i++)
		{
			if (ChessType.get(i).GetName().equals(Name))
			{
				return false;
			}
		}
		ChessType.add(new ChessPiece(Name));
		return true;
	}
	
	public boolean AddChessMove(String Name, Position MovePos)
	{
		boolean PieceMissing = true;
		for(int i = 0;i < ChessType.size();i++)
		{
			if (ChessType.get(i).GetName().equals(Name))
			{
				PieceMissing = false;
				break;
			}
		}
		if (PieceMissing) {return false;}
		
		for(int i = 0;i < SetList.size();i++)
		{
			ChessMove TempMove = SetList.get(i);
			if (TempMove.GetName().equals(Name))
			{
				TempMove.AddMove(MovePos);
				return true;
			}
		}
		SetList.add(new ChessMove(Name));
		ChessMove TempMove = SetList.get(SetList.size() - 1);
		TempMove.AddMove(MovePos);
		return true;
	}
	
	public boolean AddChessMove(String Name, int Direction, int Value)
	{
		boolean PieceMissing = true;
		for(int i = 0;i < ChessType.size();i++)
		{
			if (ChessType.get(i).GetName().equals(Name))
			{
				PieceMissing = false;
				break;
			}
		}
		if (PieceMissing) {return false;}
		
		for(int i = 0;i < SetList.size();i++)
		{
			ChessMove TempMove = SetList.get(i);
			if (TempMove.GetName().equals(Name))
			{
				TempMove.AddMove(Direction, Value);
				return true;
			}
		}
		SetList.add(new ChessMove(Name));
		ChessMove TempMove = SetList.get(SetList.size() - 1);
		TempMove.AddMove(Direction, Value);
		return true;
	}
	
	public ChessMove GetChessMove(String Name)
	{
		for(int i = 0;i < SetList.size();i++)
		{
			if (SetList.get(i).GetName().equals(Name))
			{
				return SetList.get(i);
			}
		}
		return null;
	}
	
	//Get valid move from specific Slot with Chess inside
	public ArrayList<ChessSlot> GetAllValidMoves(ChessSlot SelectedSlot, ChessBoard GameBoard)
	{
		ChessPiece SelectedPiece = SelectedSlot.GetChessPiece();
		if (SelectedPiece != null)
		{
			String SelectedPieceName = SelectedPiece.GetName();
			ChessMove SelectPieceMove = GetChessMove(SelectedPieceName);
			ArrayList<ChessSlot> TempSlotList;
			TempSlotList = SelectPieceMove.GetAllValidMoves(SelectedSlot.GetPosition(), GameBoard);
			
			return TempSlotList;
		}
		return null;
	}
	
	//=====Abstract functions=====
	
	abstract public void RoundEvent(int RoundNumber, ChessBoard GameBoard);
	abstract public Player CheckWin(ArrayList<Player> PlayerList, ChessBoard GameBoard);
	abstract public void Initiallize();
	abstract public ChessBoard PlaceBoard(ArrayList<Player> PlayerList);
}

