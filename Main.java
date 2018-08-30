import java.util.*;
import java.awt.Color;

  /*================================================//
 // <!> This class is for testing purpose only <!> //
//================================================*/

//PS: Also a demo of how to use my classes
//Terrible classes

public class Main
{
	public static void main(String[] args)
	{
		ChessGame MyrmidonGame = new ChessGame(new MyrmidonRule());
		
		MyrmidonGame.SetMaxPlayer(2);
		MyrmidonGame.MakePlayer("Foo", new Color(255,0,0));
		MyrmidonGame.MakePlayer("Bar", new Color(0,0,255));
		
		MyrmidonGame.StartGame();
		
		ChessSlot FromSlot = MyrmidonGame.GameBoard.GetSlot(new Position(0,0));
		ChessSlot ToSlot = MyrmidonGame.GameBoard.GetSlot(new Position(0,2));
		System.out.println(MyrmidonGame.PlayerMovesChess(FromSlot, ToSlot));
		
		FromSlot = MyrmidonGame.GameBoard.GetSlot(new Position(5,5));
		ToSlot = MyrmidonGame.GameBoard.GetSlot(new Position(3,3));
		System.out.println(MyrmidonGame.PlayerMovesChess(FromSlot, ToSlot));
		
		FromSlot = MyrmidonGame.GameBoard.GetSlot(new Position(6,0));
		ToSlot = MyrmidonGame.GameBoard.GetSlot(new Position(5,1));
		System.out.println(MyrmidonGame.PlayerMovesChess(FromSlot, ToSlot));
		
		for(int i = 0;i < MyrmidonGame.GameBoard.GetSizeX();i++)
		{
			for(int j = 0;j < MyrmidonGame.GameBoard.GetSizeY();j++)
			{
				Position TempPos = new Position(i,j);
				ChessSlot TempSlot = MyrmidonGame.GameBoard.GetSlot(TempPos);
				ChessPiece TempPiece = TempSlot.GetChessPiece();
				
				if (TempPiece != null)
				{
					System.out.print(TempPos.GetX() + ", " + TempPos.GetY() + ": ");
					System.out.println(TempPiece.GetName());
				}
				else
				{
					System.out.println(TempPos.GetX() + ", " + TempPos.GetY() + ": Empty");
				}
			}
		}
		
		/*
		ArrayList<ChessSlot> ValidMoves = MyrmidonGame.CustomRule.GetAllValidMoves(MyrmidonGame.GameBoard.GetSlot(new Position(2,0)), MyrmidonGame.GameBoard);
		for(int i = 0;i < ValidMoves.size();i++)
		{
			int X = ValidMoves.get(i).GetPosition().GetX();
			int Y = ValidMoves.get(i).GetPosition().GetY();
			System.out.println("Valid Moves: ");
			System.out.print(X);
			System.out.print(",");
			System.out.print(Y);
			System.out.println();
		}
		*/
		System.out.println("Game start!");
	}
}