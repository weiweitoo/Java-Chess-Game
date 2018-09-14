import java.util.*;
import java.io.*;
import java.io.PrintWriter;
import java.io.File;
import java.awt.Color;

public class ChessSaveLoad
{
	/*
	File format:

	<player 1 name>
	<player 2 name>
	<player turn>
	<game rounds>
	<slot 0>
	<owner>
	<slot 1>
	<owner>
	<slot 2>
	<owner>
	...

	Slot format:
	<Chess name> (0 if empty)
	<Player name> (0 if empty)
	*/

	public static void SaveGame(ChessGame Game, String FileName)
	{
		File file = new File("./Saves/" + FileName + ".myrsav");
		PrintWriter Writer;
		try
		{
			Writer = new PrintWriter(file);
		}
		catch(FileNotFoundException e)
		{
			System.out.println(e);
			return;
		}

		Writer.println(Game.GetPlayer(0).GetName());
		Writer.println(Game.GetPlayer(1).GetName());
		Writer.println(Game.GetPlayerTurn());
		Writer.println(Game.GetRound());
		for(int i = 0;i < Game.GetBoardSize();i++)
		{
			ChessSlot TempSlot = Game.GetBoardSlot(i);
			ChessPiece TempPiece = TempSlot.GetChessPiece();
			if (TempPiece == null)
			{
				Writer.println(0);
				Writer.println(0);
				continue;
			}
			Player TempPlayer = TempPiece.GetPlayer();
			Writer.println(TempPiece.GetName());
			Writer.println(TempPlayer.GetName());
		}
		Writer.close();
	}

	public static void LoadGame(ChessGame Game, String FileName)
	{
		Game.ResetGame();
		File file = new File(FileName);
		Scanner FileRead;
		try
		{
			FileRead = new Scanner(file);
		}
		catch(FileNotFoundException e)
		{
			System.out.println(e);
			return;
		}
		
		String P1Name = FileRead.nextLine();
		String P2Name = FileRead.nextLine();

		Game.SetMaxPlayer(2);
		Color P1Color = new Color(255,0,0);
		Color P2Color = new Color(0,0,255);
		Game.MakePlayer(P1Name, P1Color);
		Game.MakePlayer(P2Name, P2Color);

		int PlayerTurn = FileRead.nextInt();
		int GameRound = FileRead.nextInt();
		//Fire a blank nextLine function to consume the line feed
		FileRead.nextLine();
		ChessBoard GameBoard = new ChessBoard(7,6);
		for(int i = 0;i < GameBoard.GetSizeY();i++)
		{
			for(int j = 0;j < GameBoard.GetSizeX();j++)
			{
				String PieceName = FileRead.nextLine();
				String OwnerName = FileRead.nextLine();
				if (PieceName.equals("0"))
				{
					continue;
				}
				GameBoard.AddPiece(new ChessPiece(PieceName, Game.GetPlayer(OwnerName)), new Position(j,i));
			}
		}

		Game.StartGame(PlayerTurn, GameRound, GameBoard);
	}
}