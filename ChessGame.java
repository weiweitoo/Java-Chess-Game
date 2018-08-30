import java.util.*;
import java.lang.*;
import java.awt.Color;

public class ChessGame
{
	public ChessRule CustomRule;
	
	private ArrayList<Player> PlayerList = new ArrayList<Player>();
	private int MaxPlayer = 0;
	public ChessBoard GameBoard = null;
	
	private int PlayerTurn = 0;
	private int GameRound = 0;
	private Player PlayerWinner = null;
	
	private boolean GameRunning = false;
	
	public ChessGame(ChessRule CustomRule)
	{
		this.CustomRule = CustomRule;
	}
	
	//Set max player for a game
	public void SetMaxPlayer(int MaxPlayer)
	{
		this.MaxPlayer = MaxPlayer;
	}
	
	//Add new player to the game
	public boolean MakePlayer(String PlayerName, Color PlayerColor)
	{
		if (this.PlayerList.size() >= MaxPlayer)
		{
			return false;
		}
		for(int i = 0; i < this.PlayerList.size();i++)
		{
			if (PlayerList.get(i).GetName().equals(PlayerName))
			{
				return false;
			}
		}
		PlayerList.add(new Player(PlayerName, PlayerColor));
		return true;
	}
	
	//Remove specific player by given name
	public boolean RemovePlayer(String PlayerName)
	{
		for(int i = 0; i < this.PlayerList.size();i++)
		{
			if (PlayerList.get(i).GetName().equals(PlayerName))
			{
				PlayerList.remove(i);
				return true;
			}
		}
		return false;
	}
	
	//Remove player by ID
	public void RemovePlayer(int Index)
	{
		PlayerList.remove(Index);
	}
	
	//The action taken when player attempt to move a chess
	public boolean PlayerMovesChess(ChessSlot FromSlot, ChessSlot ToSlot)
	{
		if (!this.GameRunning)
		{
			return false;
		}
		
		boolean Movable = false;
		String FromSlotOwner = FromSlot.GetChessPiece().GetPlayer().GetName();
		if (!PlayerList.get(PlayerTurn).GetName().equals(FromSlotOwner))
		{
			return false;
		}
		ArrayList<ChessSlot> ValidMoves = CustomRule.GetAllValidMoves(FromSlot, GameBoard);
		for(int i = 0;i < ValidMoves.size();i++)
		{
			if (ValidMoves.get(i).equals(ToSlot))
			{
				Movable = true;
				break;
			}
		}
		
		if (Movable)
		{
			//Move chess
			boolean HavePiece = ToSlot.RemoveChessPiece();
			ChessPiece TempChess = FromSlot.GetChessPiece();
			if (HavePiece)
			{
				Player ChessOwner = TempChess.GetPlayer();
				ChessOwner.AddKill();
			}
			ToSlot.SetChessPiece(TempChess);
			FromSlot.RemoveChessPiece();
			
			//Check win
			PlayerWinner = CustomRule.CheckWin(GameBoard, PlayerList);
			if (PlayerWinner != null)
			{
				this.GameRunning = false;
				return Movable;
			}
			
			//Change turn & Increase round
			this.PlayerTurn = (this.PlayerTurn + 1) % MaxPlayer;
			if (this.PlayerTurn == 0)
			{
				this.GameRound += 1;
				//Trigger round event
				CustomRule.RoundEvent(this.GameRound, GameBoard);
			}
		}
		return Movable;
	}
	
	public void StartGame()
	{
		this.GameBoard = CustomRule.Initiallize(PlayerList);
		GameRunning = true;
	}
	
	public void ResetGame()
	{
		PlayerList.clear();
		
		GameRunning = false;
		GameBoard = null;
		
		PlayerTurn = 0;
		GameRound = 0;
		
		PlayerWinner = null;
	}
	
	//=====Getter=====//
	public int GetPlayerTurn()
	{
		return this.PlayerTurn;
	}
	
	public int GetRound()
	{
		return this.GameRound;
	}
	
	public Player GetCurrentTurnPlayer()
	{
		return this.PlayerList.get(this.PlayerTurn);
	}
}