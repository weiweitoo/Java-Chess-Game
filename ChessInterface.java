import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.*;
import java.io.*;
import javax.*;

public class ChessInterface extends JFrame implements ActionListener{
		//GUI variable
		private static JPanel mainPanel = new JPanel(new GridLayout(6,7));
		private static JPanel subPanel = new JPanel(new FlowLayout());
		private static JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

		private static HashMap<JButton, Integer> chessButton = new HashMap<>();
		private static HashMap<Integer, JButton> chessButtonKey = new HashMap<>();
		private static HashMap<String, BufferedImage> chessIcon = new HashMap<>();
		private static JLabel msgLable = new JLabel("Game start! Red first.");
		
		private static JMenuBar menuBar = new JMenuBar();
		private static JMenu menuItemGame = new JMenu("Game");
		private static JMenuItem eMenuItemSave = new JMenuItem("Save Game");
		private static JMenuItem eMenuItemLoad = new JMenuItem("Load Game");
		private static JMenuItem eMenuItemRest = new JMenuItem("Restart");
		private static JMenuItem eMenuItemExit = new JMenuItem("Exit");

		//Game variable
		private static ChessGame MyrmidonGame = new ChessGame(new MyrmidonRule());
		private static boolean Selected = false; 

	    ChessInterface()
	    {
	    	super("Chess application");
	    	try{
	    		chessIcon.put("PlusR", 		ImageIO.read(new File("Assets/PlusR.png")));
				chessIcon.put("TriangleR", 	ImageIO.read(new File("Assets/TriangleR.png")));
				chessIcon.put("ChevronR", 	ImageIO.read(new File("Assets/ChevronR.png")));
				chessIcon.put("SunR", 		ImageIO.read(new File("Assets/SunR.png")));
				chessIcon.put("PlusB", 		ImageIO.read(new File("Assets/PlusB.png")));
				chessIcon.put("TriangleB",	ImageIO.read(new File("Assets/TriangleB.png")));
				chessIcon.put("ChevronB",	ImageIO.read(new File("Assets/ChevronB.png")));
				chessIcon.put("SunB",		ImageIO.read(new File("Assets/SunB.png")));
	    	}catch(IOException e){
	    		//handler
	    	}

		initiallize(MyrmidonGame.GetPlayerTurn());
		menuItemGame.add(eMenuItemSave);
		menuItemGame.add(eMenuItemLoad);
		menuItemGame.add(eMenuItemRest);
		menuItemGame.add(eMenuItemExit);
		menuBar.add(menuItemGame);
		subPanel.add(msgLable);
		topPanel.add(menuBar);
		this.setLayout(new BorderLayout());
		this.add(mainPanel, BorderLayout.CENTER);
		this.add(subPanel, BorderLayout.SOUTH);
		this.add(topPanel, BorderLayout.NORTH);
		
        setSize(500,500);
        setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
	
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton)e.getSource();
		ChessSlot SelectedSlot = MyrmidonGame.GetBoardSlot(chessButton.get(btn));
		if(!this.Selected)
		{
			//Selecting Chess
			this.Selected = MyrmidonGame.SelectsSlot(SelectedSlot);
			if(this.Selected)
			{
				btn.setBackground(new Color(0,255,0));
				ArrayList<ChessSlot> MovesBuffer = MyrmidonGame.GetValidMovesBuffer();
				Iterator Itr =  MovesBuffer.iterator();
				while(Itr.hasNext())
				{
					ChessSlot TempSlot = (ChessSlot)Itr.next();
					Position TempPos = TempSlot.GetPosition();
					JButton temp_btn = chessButtonKey.get(MyrmidonGame.GetFlat(TempPos.GetX(),TempPos.GetY()));
					temp_btn.setBackground(new Color(255,255,0));
				}
			}
			return;
		}
		//Moving Chess
		boolean moved = MyrmidonGame.MovesChess(SelectedSlot);
		this.Selected = false;
		refresh(MyrmidonGame.GetPlayerTurn());
		if(moved)
		{
			Player winner = MyrmidonGame.GetWinner();
			if (winner != null)
			{
				String pname = winner.GetName();
				if (pname.equals("R"))
				{pname = "Red Player";}
				else
				{pname = "Blue Player";}
				msgLable.setText("The winner is " + pname + "!");
				Position TempPos = SelectedSlot.GetPosition();
				JButton temp_btn = chessButtonKey.get(MyrmidonGame.GetFlat(TempPos.GetX(),TempPos.GetY()));
				temp_btn.setBackground(new Color(255,0,0));
				return;
			}
			String pname = MyrmidonGame.GetCurrentTurnPlayer().GetName();
			if (pname.equals("R"))
			{pname = "Red Player";}
			else
			{pname = "Blue Player";}
			msgLable.setText("Current turn for " + pname + ".");
		}
	}
	
	private void refresh(int flip)
	{
		mainPanel.removeAll();
		initiallize(flip);
		revalidate();
		repaint();
	}

	private void initiallize(int flip)
	{
		if (flip == 1)
		{
			for(int i = 0;i < MyrmidonGame.GetBoardSize();i++) {
				_init_(i);
			}
		}
		else
		{
			for(int i = MyrmidonGame.GetBoardSize()-1;i >= 0;i--) {
				_init_(i);
			}
		}
	}

	private void _init_(int i)
	{
		ChessSlot TempSlot = MyrmidonGame.GetBoardSlot(i);
		ChessPiece TempPiece = TempSlot.GetChessPiece();
		final BufferedImage chessIconFinal;
		
		if(TempPiece == null){
			chessIconFinal = null;
		}
		else{
			String pname = TempPiece.GetName();
			String pcolor = TempPiece.GetPlayer().GetName();
			chessIconFinal = chessIcon.get(pname+pcolor);
		}
		
		JButton btn = new JButton(){
			@Override
			public Dimension getPreferredSize(){
				return new Dimension(90, 50);
			}
		};
		chessButton.put(btn,i);
		chessButtonKey.put(i,btn);
		btn.addComponentListener(new ComponentAdapter() {
		@Override
	       public void componentResized(ComponentEvent e) {
	            JButton btn = (JButton) e.getComponent();
	            Dimension size = btn.getSize();
	            Insets insets = btn.getInsets();
	            size.width -= insets.left + insets.right;
	            size.height -= insets.top + insets.bottom;
	            if (size.width > size.height) {
	                size.width = -1;
	            } else {
	                size.height = -1;
	            }
	            if (chessIconFinal != null)
	            {
	         		Image iconImage = (Image)chessIconFinal;
	      			Image scaled = iconImage.getScaledInstance(size.width, size.height, java.awt.Image.SCALE_SMOOTH);
	           		btn.setIcon(new ImageIcon(scaled));
	            }
	        }
	    });
	    btn.setBackground(new Color(255,255,255));
		btn.addActionListener(this);
		mainPanel.add(btn);
	}

   	public static void main(String[] args){
   	MyrmidonGame.SetMaxPlayer(2);

	//Set players name
	String P1Name = "R";
	String P2Name = "B";

	//Set players colour
	Color P1Color = new Color(255,0,0);
	Color P2Color = new Color(0,0,255);

	//Add players into the game
	MyrmidonGame.MakePlayer(P1Name, P1Color);
	MyrmidonGame.MakePlayer(P2Name, P2Color);

	//Start the game (Game board will initiate)
	MyrmidonGame.StartGame();
   	new ChessInterface();
   }
	
}
