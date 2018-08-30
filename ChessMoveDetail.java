import java.util.*;

public class ChessMoveDetail
{
	private Position MovePosition = null;
	
	private boolean IsStraight = false;
	private int MoveDirection = 5;
	/*
	1 2 3
	4 5 6
	7 8 9
	*/
	private int MoveValue = 0; //Negative for infinity value
	
	//Constructor for specific position
	public ChessMoveDetail(Position MovePosition)
	{
		this.MovePosition = MovePosition;
	}
	
	//Constructor for straight movement
	public ChessMoveDetail(int Direction, int Value)
	{
		this.MoveDirection = Direction;
		this.MoveValue = Value;
		this.IsStraight = true;
	}
	
	//Shifting position for straight movement
	private Position RelativeShift(Position ThePosition)
	{
		ArrayList<Position> ShiftPosition = new ArrayList<Position>();
		ShiftPosition.add(new Position(-1,-1,true)); //1
		ShiftPosition.add(new Position(0,-1,true));  //2
		ShiftPosition.add(new Position(1,-1,true));  //3
		ShiftPosition.add(new Position(-1,0,true));  //4
		ShiftPosition.add(new Position(0,0,true));   //5
		ShiftPosition.add(new Position(1,0,true));   //6
		ShiftPosition.add(new Position(-1,1,true));  //7
		ShiftPosition.add(new Position(0,1,true));   //8
		ShiftPosition.add(new Position(1,1,true));   //9
		
		ThePosition = ThePosition.add(ShiftPosition.get(this.MoveDirection - 1));
		return ThePosition;
	}
	
	//Get all valid move of the current movement
	public ArrayList<Position> GetValidMove(Position BasePosition, ChessBoard GameBoard)
	{
		ArrayList<Position> ValidMoves = new ArrayList<Position>();
		
		int XMax = GameBoard.GetSizeX();
		int YMax = GameBoard.GetSizeY();
		
		Position RelativePos = new Position(BasePosition.GetX(), BasePosition.GetY());
		String BasePieceOwner = GameBoard.GetSlot(BasePosition).GetChessPiece().GetPlayer().GetName();
		
		if (IsStraight)
		{
			int Count = this.MoveValue;
			boolean Con = true;
			do
			{
				if (Count == 0)
				{
					Con = false;
					continue;
				}
				RelativePos = RelativeShift(RelativePos);
				if ((RelativePos.GetX() < 0)||(RelativePos.GetY() < 0)||(RelativePos.GetX() >= XMax)||(RelativePos.GetY() >= YMax))
				{
					Con = false;
					continue;
				}
				ChessPiece TempPiece = GameBoard.GetSlot(RelativePos).GetChessPiece();
				if (TempPiece != null)
				{
					Con = false;
					if (!TempPiece.GetPlayer().GetName().equals(BasePieceOwner))
					{
						ValidMoves.add(RelativePos);
					}
				}
				else
				{
					ValidMoves.add(RelativePos);
				}
				Count -= 1;
			}while(Con);
		}
		else
		{
			boolean Valid = true;
			Position MovedPos = RelativePos.add(MovePosition);
			
			if ((MovedPos.GetX() < 0)||(MovedPos.GetY() < 0)||(MovedPos.GetX() >= XMax)||(MovedPos.GetY() >= YMax))
			{
				Valid = false;
			}
			else
			{
				ChessPiece TempPiece = GameBoard.GetSlot(MovedPos).GetChessPiece();
				if (TempPiece != null)
				{
					if (TempPiece.GetPlayer().GetName().equals(BasePieceOwner))
					{
						Valid = false;
					}
				}
			}
			
			if (Valid)
			{
				ValidMoves.add(MovedPos);
			}
		}
		return ValidMoves;
	}
}