import java.util.*;

public class ChessMove
{
	private String PieceName;
	private ArrayList<ChessMoveDetail> MoveList = new ArrayList<ChessMoveDetail>();
	
	//Constructor
	public ChessMove(String PieceName)
	{
		this.PieceName = PieceName;
	}
	
	//Add move with specific position
	public void AddMove(Position MovePos)
	{
		MoveList.add(new ChessMoveDetail(MovePos));
	}
	
	//Add move with straight movement
	public void AddMove(int Direction, int Value)
	{
		MoveList.add(new ChessMoveDetail(Direction, Value));
	}
	
	public String GetName()
	{
		return this.PieceName;
	}
	
	//Calling valid move function from MoveDetail object, combine them and re-pack them into Slot object
	public ArrayList<ChessSlot> GetAllValidMoves(Position BasePosition, ChessBoard GameBoard)
	{
		ArrayList<ChessSlot> ValidMoves = new ArrayList<ChessSlot>();
		for(int i = 0;i < MoveList.size();i++)
		{
			ArrayList<Position> ValidMovesPart = MoveList.get(i).GetValidMove(BasePosition, GameBoard);
			for(int j = 0;j < ValidMovesPart.size();j++)
			{
				ValidMoves.add(GameBoard.GetSlot(ValidMovesPart.get(j)));
			}
		}
		return ValidMoves;
	}
}