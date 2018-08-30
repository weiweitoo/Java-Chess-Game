import java.util.*;
import java.lang.*;

public class MyrmidonRule extends ChessRule
{
	public MyrmidonRule() {super();}
	
	/*=====
	Round event of Myrmidon Chess:
	
		Every 3 rounds
		Each chess on board change their type
		Example: A to B, B to C, C to A
		
	=====*/
	
	public void RoundEvent(int RoundNumber, ChessBoard GameBoard)
	{
		if (RoundNumber % 3 == 0)
		{
			for(int i = 0;i < GameBoard.GetSizeX();i++)
			{
				for(int j = 0;j < GameBoard.GetSizeY();j++)
				{
					ChessPiece TempPiece = GameBoard.GetSlot(new Position(i,j)).GetChessPiece();
					if (TempPiece != null)
					{
						String PieceName = TempPiece.GetName();
						int ChessTypeSize = ChessType.size() - 1;
						for(int k = 0;k < ChessTypeSize;k++)
						{
							if (ChessType.get(i).GetName().equals(PieceName))
							{
								TempPiece.SetName(ChessType.get((i+1)%ChessTypeSize).GetName());
							}
						}
					}
				}
			}
		}
	}
	
	/*=====
	Victory case of Myrmidon Chess:
	
		If opponent's Sun is killed
		The game ended immediately
		The killing player win the game
		
	=====*/
	
	public Player CheckWin(ChessBoard GameBoard, ArrayList<Player> PlayerList)
	{
		int PlayerListSize = PlayerList.size();
		boolean[] SunPresent = new boolean[PlayerListSize];
		Arrays.fill(SunPresent, false);
		
		for(int i = 0;i < GameBoard.GetSizeX();i++)
		{
			for(int j = 0;j < GameBoard.GetSizeY();j++)
			{
				ChessPiece TempPiece = GameBoard.GetSlot(new Position(i,j)).GetChessPiece();
				if (TempPiece != null)
				{
					String PieceName = TempPiece.GetName();
					if (ChessType.get(ChessType.size()-1).GetName().equals(PieceName))
					{
						String PieceOwnerName = TempPiece.GetPlayer().GetName();
						for(int k = 0;k < PlayerListSize;k++)
						{
							if (PlayerList.get(k).GetName().equals(PieceOwnerName))
							{
								SunPresent[k] = true;
							}
						}
					}
				}
			}
		}
		
		for(int i = 0;i < PlayerListSize;i++)
		{
			if (SunPresent[i] == false)
			{
				return PlayerList.get((i+1)%PlayerListSize);
			}
		}
		return null;
	}
	
	public ChessBoard Initiallize(ArrayList<Player> PlayerList)
	{
		ChessBoard GameBoard = new ChessBoard(7,6);
		//Presets chess type for the game
		AddChessType("Plus");
		AddChessType("Triangle");
		AddChessType("Chevron");
		AddChessType("Sun");
		
		//Add movement set for each chess
		//Chess: Plus
		for(int i = 2;i <= 8;i += 2) {AddChessMove("Plus", i, 2);}
		//Chess: Triangle
		for(int i = 1;i <= 9;i += 2)
		{
			if(i != 5) {AddChessMove("Triangle", i, 2);}
		}
		//Chess: Chevron
		int a = 1;
		int b = 2;
		for(int i = 0;i < 8;i++)
		{
			if (i < 4)
			{AddChessMove("Chevron", new Position(a,b,true));}
			else
			{AddChessMove("Chevron", new Position(b,a,true));}
			
			a *= -1;
			if (i%2 != 0) {b *= -1;}
		}
		//Chess: Sun
		for(int i = 0;i <= 9;i++)
		{
			if(i != 5) {AddChessMove("Sun", i, 1);}
		}
		
		//Placing chess on the board for each player
		String[] Sequence = {"Plus","Triangle","Chevron","Sun","Chevron","Triangle","Plus"};
		for(int i = 0;i < GameBoard.GetSizeY();i += 5)
		{
			for(int j = 0;j < GameBoard.GetSizeX();j++)
			{
				int val = (i == 5) ? 1 : 0;
				GameBoard.AddPiece(new ChessPiece(Sequence[j], PlayerList.get(val)), new Position(j,i));
			}
		}
		
		return GameBoard;
	}
}