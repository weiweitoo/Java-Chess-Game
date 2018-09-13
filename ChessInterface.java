import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.*;
import java.io.*;
import javax.*;

public class ChessInterface extends JFrame implements ActionListener{

		public static ChessGame MyrmidonGame = new ChessGame(new MyrmidonRule());
		JButton[] btn = new JButton[MyrmidonGame.GetBoardSize()];
		final ImageIcon[] chessIcon = {
			new ImageIcon(ImageIO.read(new File("Assets/PlusR.png"))),
			new ImageIcon(ImageIO.read(new File("Assets/TriangleR.png"))),
			new ImageIcon(ImageIO.read(new File("Assets/ChevronR.png"))),
			new ImageIcon(ImageIO.read(new File("Assets/SunR.png"))),
			new ImageIcon(ImageIO.read(new File("Assets/PlusB.png"))),
			new ImageIcon(ImageIO.read(new File("Assets/TriangleB.png"))),
			new ImageIcon(ImageIO.read(new File("Assets/ChevronB.png"))),
			new ImageIcon(ImageIO.read(new File("Assets/SunB.png")))
		};

	    ChessInterface()
	    {
	        super("Chess application");
			JPanel mainPanel = new JPanel(new GridLayout(6,7));
			try{
				for(int i = 0;i < MyrmidonGame.GetBoardSize();i++) {

					ChessSlot TempSlot = MyrmidonGame.GetBoardSlot(i);
					ChessPiece TempPiece = TempSlot.GetChessPiece();
					
					if(TempPiece == null){
						chessIcon[i] = new ImageIcon(ImageIO.read(new File("Assets/Empty.png")));
					}
					else{
						String pname = TempPiece.GetName();
						String pcolor = TempPiece.GetPlayer().GetName();
						chessIcon[i] = new ImageIcon(ImageIO.read(new File("Assets/"+pname+pcolor+".png")));
					}
					
					btn[i] = new JButton(){
						@Override
						public Dimension getPreferredSize(){
							return new Dimension(90, 50);
						}
					};

					btn[i].addComponentListener(new ComponentAdapter() {
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

	                        Image iconImage = ((ImageIcon)btn.getIcon()).getImage();
	                        Image scaled = iconImage.getScaledInstance(size.width, size.height, java.awt.Image.SCALE_SMOOTH);
	                        //btn.setIcon((ImageIcon)scaled);
	                    }
	                });

	                btn[i].setIcon(chessIcon[i]);
	               	btn[i].setBackground(new Color(255,255,255));
					btn[i].addActionListener(this);
					mainPanel.add(btn[i]);
				}
			}
			catch (IOException ex) {
	        // handle exception...
	   		}
			
			this.setLayout(new BorderLayout());
			this.add(mainPanel, BorderLayout.CENTER);
			
	        setSize(500,500);
	        setVisible(true);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
	    }
	
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton)e.getSource();
		try{
			for(int i = 0;i < MyrmidonGame.GetBoardSize();i++) {
				ChessSlot TempSlot = MyrmidonGame.GetBoardSlot(i);
				ChessPiece TempPiece = TempSlot.GetChessPiece();
				
				if(TempPiece == null){
					chessIcon[i] =  new ImageIcon(ImageIO.read(new File("Assets/Empty.png")));
				}
				else{
					String pname = TempPiece.GetName();
					String pcolor = TempPiece.GetPlayer().GetName();
					chessIcon[i] =  new ImageIcon(ImageIO.read(new File("Assets/"+pname+"B.png")));
				}
	    		this.btn[i].setIcon(chessIcon[i]);
			}
		}
		catch (IOException ex) {
	        // handle exception...
	   	}
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

