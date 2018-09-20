import java.util.*;
import java.lang.*;
import java.awt.Color;

public class ChessGame
{
	private static ChessGame Instance;
	private ChessRule CustomRule;
	
	private ArrayList<Player> PlayerList = new ArrayList<Player>();
	private int MaxPlayer = 0;
	private ChessBoard GameBoard = null;
	
	private int PlayerTurn = 0;
	private int GameRound = 0;
	private Player PlayerWinner = null;

	private ChessSlot StartingSlot = null;
	private ArrayList<ChessSlot> ValidMovesBuffer;
	
	private boolean GameRunning = false;
	
	private ChessGame(ChessRule CustomRule)
	{
		this.CustomRule = CustomRule;
		this.CustomRule.Initiallize();
	}

	public static ChessGame InitiallizeChessGame(ChessRule CustomRule)
	{
		if (Instance == null)
		{
			Instance = new ChessGame(CustomRule);
		}
		return Instance;
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
	
	//When player selected a slot
	public boolean SelectsSlot(ChessSlot SelectedSlot)
	{
		if (!this.GameRunning)
		{
			return false;
		}
		ChessPiece TempPiece = SelectedSlot.GetChessPiece();
		if (TempPiece == null)
		{
			return false;
		}
		String SelectedSlotOwner = TempPiece.GetPlayer().GetName();
		if (!PlayerList.get(this.PlayerTurn).GetName().equals(SelectedSlotOwner))
		{
			return false;
		}
		this.StartingSlot = SelectedSlot;
		this.ValidMovesBuffer = CustomRule.GetAllValidMoves(this.StartingSlot, this.GameBoard);
		return true;
	}

	//When player tries to move a chess
	public boolean MovesChess(ChessSlot SelectedSlot)
	{
		if (!this.GameRunning)
		{
			return false;
		}
		if (this.StartingSlot == null)
		{
			return false;
		}

		boolean Movable = false;
		ArrayList<ChessSlot> ValidMoves = this.ValidMovesBuffer;
		for(int i = 0;i < ValidMoves.size();i++)
		{
			if (ValidMoves.get(i).equals(SelectedSlot))
			{
				Movable = true;
				break;
			}
		}

		if (Movable)
		{
			MovedChess(this.StartingSlot, SelectedSlot);
		}
		return Movable;
	}

	//The action taken when player is about to succesfully move a chess
	private void MovedChess(ChessSlot FromSlot, ChessSlot ToSlot)
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
		PlayerWinner = CustomRule.CheckWin(this.PlayerList, this.GameBoard);
		if (PlayerWinner != null)
		{
			this.GameRunning = false;
			return;
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
	
	public ArrayList<ChessSlot> GetValidMovesBuffer()
	{
		return this.ValidMovesBuffer;
	}

	public void StartGame()
	{
		StartGame(0,0,CustomRule.PlaceBoard(this.PlayerList));
	}

	public void StartGame(int PT, int GR, ChessBoard GB)
	{
		this.PlayerTurn = PT;
		this.GameRound = GR;
		this.PlayerWinner = null;
		this.GameBoard = GB;
		this.GameRunning = true;
	}
	
	public void ResetGame()
	{
		this.PlayerList.clear();
		this.GameRunning = false;
		this.GameBoard = null;
		this.PlayerTurn = 0;
		this.GameRound = 0;
		this.PlayerWinner = null;
	}
	
	//=====Getter=====//
	public boolean GetRunning()
	{
		return this.GameRunning;
	}

	public int GetPlayerTurn()
	{
		return this.PlayerTurn;
	}
	
	public int GetRound()
	{
		return this.GameRound;
	}
	
	public Player GetPlayer(int Index)
	{
		return this.PlayerList.get(Index);
	}

	public Player GetPlayer(String Name)
	{
		for(int i = 0;i < this.PlayerList.size();i++)
		{
			if (this.PlayerList.get(i).GetName().equals(Name))
			{
				return this.PlayerList.get(i);
			}
		}
		return null;
	}

	public Player GetWinner()
	{
		return this.PlayerWinner;
	}

	public Player GetCurrentTurnPlayer()
	{
		return this.PlayerList.get(this.PlayerTurn);
	}

	public ChessSlot GetBoardSlot(int X, int Y)
	{
		return this.GameBoard.GetSlot(new Position(X,Y));
	}

	public ChessSlot GetBoardSlot(int Flat)
	{
		int X = Flat % GameBoard.GetSizeX();
		int Y = (int)(Flat / GameBoard.GetSizeX());
		return GetBoardSlot(X,Y);
	}

	public int GetFlat(int X, int Y)
	{
		return Y*GameBoard.GetSizeX() + X;
	}

	public int GetBoardSize()
	{
		return GetFlat(GameBoard.GetSizeX(),GameBoard.GetSizeY()-1);
	}
}