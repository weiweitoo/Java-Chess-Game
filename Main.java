import java.util.*;
import java.awt.Color;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
import javax.*;

  /*================================================//
 // <!> This class is for testing purpose only <!> //
//================================================*/

//PS: Also a demo of how to use my classes
//Terrible classes

public class Main
{
	//Instantiate the game
	public static ChessGame MyrmidonGame = new ChessGame(new MyrmidonRule());

	public static void main(String[] args)
	{
		//Set max player number of this game
		MyrmidonGame.SetMaxPlayer(2);

		//Set players name
		String P1Name = "Foo";
		String P2Name = "Bar";

		//Set players colour
		Color P1Color = new Color(255,0,0);
		Color P2Color = new Color(0,0,255);

		//Add players into the game
		MyrmidonGame.MakePlayer(P1Name, P1Color);
		MyrmidonGame.MakePlayer(P2Name, P2Color);

		//Start the game (Game board will initiate)
		MyrmidonGame.StartGame();

		//Example of moving chess turn by turn
		ChessSlot FromSlot;
		ChessSlot ToSlot;

		//Move Plus for player Foo
		FromSlot = MyrmidonGame.GetBoardSlot(0,0);
		ToSlot = MyrmidonGame.GetBoardSlot(0,2);
		//When player selected a slot
		//It will return true if there is a chess in the slot and is owned by player of current turn
		System.out.println(MyrmidonGame.SelectsSlot(FromSlot));

		//After selection, all of the valid moves will store in a buffer
		//The buffered valid move can be retrive for use
		ArrayList<ChessSlot> MovesBuffer = MyrmidonGame.GetValidMovesBuffer();
		//Print all valid move
		Iterator Itr =  MovesBuffer.iterator();
		while(Itr.hasNext())
		{
			ChessSlot TempSlot = (ChessSlot)Itr.next();
			Position TempPos = TempSlot.GetPosition();
			System.out.println("Valid move: (" + TempPos.GetX() + ", " + TempPos.GetY() + ")");
		}

		//When player attepmt to make a move
		//It will return true if the move is successful
		System.out.println(MyrmidonGame.MovesChess(ToSlot));

		//Every successful move will change player turn automatically

		//Move Triangle for player Bar
		FromSlot = MyrmidonGame.GetBoardSlot(5,5);
		ToSlot = MyrmidonGame.GetBoardSlot(3,3);
		System.out.println(MyrmidonGame.SelectsSlot(FromSlot));
		System.out.println(MyrmidonGame.MovesChess(ToSlot));

		//Move Plus for player Foo but failed
		FromSlot = MyrmidonGame.GetBoardSlot(6,0);
		ToSlot = MyrmidonGame.GetBoardSlot(5,1);
		System.out.println(MyrmidonGame.SelectsSlot(FromSlot));
		System.out.println(MyrmidonGame.MovesChess(ToSlot));

		//Print round and turn
		System.out.println("Current turn: " + MyrmidonGame.GetCurrentTurnPlayer().GetName());
		System.out.println("Current round: " + MyrmidonGame.GetRound());

		//Please read the comments in functions
		printboard();

		//Play for few rounds...
		autoplay();

		//3 rounds have passed
		//Round event handler changed chess type accordingly
		printboard();

		//Print round and turn
		System.out.println("Current turn: " + MyrmidonGame.GetCurrentTurnPlayer().GetName());
		System.out.println("Current round: " + MyrmidonGame.GetRound());

		//Saving game
		System.out.println("----- Saved game -----");
		try
		{
			ChessSaveLoad.SaveGame(MyrmidonGame, "Testing");
		}
		catch(FileNotFoundException e)
		{
			//Handle exception...
		}
		

		//Move Triangle for player Foo to attack Bar's Triangle
		FromSlot = MyrmidonGame.GetBoardSlot(1,2);
		ToSlot = MyrmidonGame.GetBoardSlot(0,3);
		System.out.println(MyrmidonGame.SelectsSlot(FromSlot));
		System.out.println(MyrmidonGame.MovesChess(ToSlot));

		//Move Chevron for player Bar to attack Foo's Sun
		FromSlot = MyrmidonGame.GetBoardSlot(4,2);
		ToSlot = MyrmidonGame.GetBoardSlot(3,0);
		System.out.println(MyrmidonGame.SelectsSlot(FromSlot));
		System.out.println(MyrmidonGame.MovesChess(ToSlot));

		//Foo's Sun is killed
		//Bar is the winner of the game
		System.out.println("The winner: " + MyrmidonGame.GetWinner().GetName());

		//Game restart with same players
		MyrmidonGame.StartGame();

		//Game reset and need to re-add players
		MyrmidonGame.ResetGame();

		//Loading game
		System.out.println("----- Loaded game -----");
		try
		{
			ChessSaveLoad.LoadGame(MyrmidonGame, "Testing");
		}
		catch(FileNotFoundException e)
		{
			//Handle exception...
		}

		//To show game is loaded
		printboard();
		//Print round and turn
		System.out.println("Current turn: " + MyrmidonGame.GetCurrentTurnPlayer().GetName());
		System.out.println("Current round: " + MyrmidonGame.GetRound());
	}

	public static void autoplay()
	{
		ChessSlot FromSlot;
		ChessSlot ToSlot;

		//Move Plus for player Foo
		FromSlot = MyrmidonGame.GetBoardSlot(0,2);
		ToSlot = MyrmidonGame.GetBoardSlot(1,2);
		System.out.println(MyrmidonGame.SelectsSlot(FromSlot));
		System.out.println(MyrmidonGame.MovesChess(ToSlot));

		//Move Triangle for player Bar
		FromSlot = MyrmidonGame.GetBoardSlot(3,3);
		ToSlot = MyrmidonGame.GetBoardSlot(4,2);
		System.out.println(MyrmidonGame.SelectsSlot(FromSlot));
		System.out.println(MyrmidonGame.MovesChess(ToSlot));

		//Move Chevron for player Foo
		FromSlot = MyrmidonGame.GetBoardSlot(2,0);
		ToSlot = MyrmidonGame.GetBoardSlot(4,1);
		System.out.println(MyrmidonGame.SelectsSlot(FromSlot));
		System.out.println(MyrmidonGame.MovesChess(ToSlot));

		//Move Plus for player Bar
		FromSlot = MyrmidonGame.GetBoardSlot(0,5);
		ToSlot = MyrmidonGame.GetBoardSlot(0,3);
		System.out.println(MyrmidonGame.SelectsSlot(FromSlot));
		System.out.println(MyrmidonGame.MovesChess(ToSlot));
	}

	public static void printboard()
	{	
		//GetBoardSize return the 1D size of game board
		for(int n = 0;n < MyrmidonGame.GetBoardSize();n++)
		{
			//IMPORTANT: GetBoardSlot also accept 1D parameter
			ChessSlot TempSlot = MyrmidonGame.GetBoardSlot(n);
			ChessPiece TempPiece = TempSlot.GetChessPiece();
			
			//Print out the board on console
			if (TempPiece == null)
			{
				System.out.print(TempSlot.GetPosition().GetX() + ", " + TempSlot.GetPosition().GetY() + ": ");
				System.out.println(TempPiece.GetName());
			}
			else
			{	
				System.out.println(TempSlot.GetPosition().GetX() + ", " + TempSlot.GetPosition().GetY() + ": Empty");
			}
		}
	}


}
