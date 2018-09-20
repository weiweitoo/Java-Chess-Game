import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.*;
import java.io.*;
import javax.*;
import java.text.SimpleDateFormat;

public class Main extends JFrame implements ActionListener{
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
	private static JMenuItem eMenuItemSaveExit = new JMenuItem("Save Game and Exit");
	private static JMenuItem eMenuItemLoad = new JMenuItem("Load Game");
	private static JMenuItem eMenuItemRest = new JMenuItem("Restart");
	private static JFileChooser fileChooser = new JFileChooser();

	//Game variable
	private static ChessGame MyrmidonGame = ChessGame.InitiallizeChessGame(new MyrmidonRule());
	private static boolean Selected = false; 

    Main()
    {	

    	super("Chess application");
    	dispose();
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
		initMenu();
        setSize(500,500);
        setVisible(true);

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener( new WindowAdapter()
		{
		    public void windowClosing(WindowEvent e)
		    {
		        JFrame frame = (JFrame)e.getSource();
		 
		        int result = JOptionPane.showConfirmDialog(
		            mainPanel,
		            "Are you sure you want to exit the application?",
		            "Exit Application",
		            JOptionPane.YES_NO_OPTION);
		 
		        if (result == JOptionPane.YES_OPTION)
		            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    }
		});
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
	
	private void initMenu(){
		menuItemGame.add(eMenuItemSave);
		menuItemGame.add(eMenuItemSaveExit);
		menuItemGame.add(eMenuItemLoad);
		menuItemGame.add(eMenuItemRest);
		menuBar.add(menuItemGame);
		subPanel.add(msgLable);
		topPanel.add(menuBar);
		this.setLayout(new BorderLayout());
		this.add(mainPanel, BorderLayout.CENTER);
		this.add(subPanel, BorderLayout.SOUTH);
		this.add(topPanel, BorderLayout.NORTH);

		eMenuItemSave.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try{
					ChessSaveLoad.SaveGame(MyrmidonGame,"MyrmidonGame Saved " + new SimpleDateFormat("HH.mm.ss-dd-MM-yyyy").format(new Date()));
					JOptionPane.showMessageDialog(mainPanel,
					    "Game saved successfully",
					    "Success",
					    JOptionPane.PLAIN_MESSAGE);	
				}
				catch(Exception error){
					JOptionPane.showMessageDialog(mainPanel,
						"Error. Game cannot be saved! ",
						"Warning",
						JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		eMenuItemSaveExit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try{
					ChessSaveLoad.SaveGame(MyrmidonGame,"MyrmidonGame Saved " + new SimpleDateFormat("HH.mm.ss-dd-MM-yyyy").format(new Date()));
					JOptionPane.showMessageDialog(mainPanel,
					    "Game saved successfully",
					    "Success",
					    JOptionPane.PLAIN_MESSAGE);	
				}
				catch(Exception error){
					JOptionPane.showMessageDialog(mainPanel,
						"Error. Game cannot be saved! ",
						"Warning",
						JOptionPane.WARNING_MESSAGE);
				}

				System.exit(0);
			}
		});

		eMenuItemLoad.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try{
					fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
					int result = fileChooser.showOpenDialog(mainPanel);
					if (result == JFileChooser.APPROVE_OPTION) {
					    File selectedFile = fileChooser.getSelectedFile();
					    ChessSaveLoad.LoadGame(MyrmidonGame,selectedFile.getAbsolutePath());
					    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
					}
					refresh(MyrmidonGame.GetPlayerTurn());
					if (MyrmidonGame.GetPlayerTurn() == 0)
					{
						msgLable.setText("Game loaded! Red Player turn.");
					}
					else
					{
						msgLable.setText("Game loaded! Blue Player turn.");
					}
					JOptionPane.showMessageDialog(mainPanel,
					    "Game loaded is successfully",
					    "Success",
					    JOptionPane.PLAIN_MESSAGE);
				}
				catch(Exception error){
					JOptionPane.showMessageDialog(mainPanel,
						"Error. Game cannot be load! ",
						"Warning",
						JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		eMenuItemRest.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				MyrmidonGame.StartGame();
				refresh(MyrmidonGame.GetPlayerTurn());
				msgLable.setText("Game restarted! Red Player turn.");
			}
		});
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
		new Main();
   }
	
}

