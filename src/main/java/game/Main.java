package game;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.Color;

import javax.swing.border.LineBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Main extends JFrame {

	private static final long serialVersionUID = 1L;
	private final int X = 3;
	private final int Y = 3;
	private JPanel boardGame;
	private int playerTurn;
	private int drawedCells;
	private JPanel contentPane;
	private Cell cell;

	private Cell[][] cellMatrix;
	
	private JLabel lblGameResult;
	private JButton btnRestart;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Te-Te-Ti");
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		

		boardGame = new JPanel();

		lblGameResult = new JLabel("");
		lblGameResult.setBounds(117, 236, 200, 14);
		contentPane.add(lblGameResult);
		
		initGame();

		btnRestart = new JButton("Restart");
		btnRestart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				restartBoardGame();
				initGame();
			}
		});
		btnRestart.setBounds(335, 206, 89, 23);
		contentPane.add(btnRestart);



	}
	
//	public void restartBoardGame() {
//		contentPane.remove(boardGame);
//		
//		boardGame = new JPanel();
//		boardGame.setBorder(new LineBorder(new Color(0, 0, 0)));
//		boardGame.setBackground(new Color(255, 255, 255));
//		boardGame.setBounds(117, 29, 200, 200);
//		contentPane.add(boardGame);
//		boardGame.setLayout(new GridLayout(3, 3, 0, 0));
//		boardGame.removeAll();
//	}
	
	public void initGame() {
		this.cellMatrix = new Cell[X][Y];
		this.playerTurn = 0;
		this.drawedCells = 0;
		lblGameResult.setText("");
		
		boardGame.removeAll();
		boardGame.repaint();
		
		boardGame.setVisible(false);
		boardGame.setVisible(true);
		
		boardGame.setBorder(new LineBorder(new Color(0, 0, 0)));
		boardGame.setBackground(new Color(255, 255, 255));
		boardGame.setBounds(117, 29, 200, 200);
		contentPane.add(boardGame);
		boardGame.setLayout(new GridLayout(3, 3, 0, 0));

		for (int x = 0; x < X; x++) {

			for (int y = 0; y < Y; y++) {
				cell = new Cell(x, y);
				boardGame.add(cell);
				cellMatrix[x][y] = cell;
			}
		}
		
		for (Cell[] cellArr : cellMatrix) {
			for (Cell cell : cellArr) {
				cell.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						cell.drawFor(playerTurn);
						drawedCells++;
						if(checkForWin(playerTurn, cell)) {
							lblGameResult.setForeground(cell.getForeground());
							lblGameResult.setText("El Jugador " + (playerTurn + 1) + " ha Ganado!");
							stopGame();
						
						}else if(drawedCells == 9) {
							lblGameResult.setForeground(Color.BLACK);
							lblGameResult.setText("Ha habido un Empate!");
							stopGame();
							
						}

						playerTurn = 1 - playerTurn;
					}
				});
			}
		}
	}
	
	public void stopGame() {
		for (Cell[] cellArr : cellMatrix) {
			for (Cell cell : cellArr) {
				if(cell.getText().isEmpty()) {
					cell.cleanListener();
				}
			}
		}
	}
	
	public boolean checkForWin(int player, Cell targetCell) {
		String playerChar = (player == 0) ? "x" : "o";
		int substraction = targetCell.getPosition().x - targetCell.getPosition().y;
		
		if(targetCell.getPosition().x == 1 && targetCell.getPosition().y == 1) {
			return 
					checkColumnAndRow(playerChar, targetCell) ||
					checkDiaganalAD(playerChar, targetCell) ||
					checkDiagonalBC(playerChar, targetCell);
			
		}else if( substraction == 1 || substraction == -1) {
			return checkColumnAndRow(playerChar, targetCell);
			
		}else if( substraction == 2 || substraction == -2) {
			return 
					checkColumnAndRow(playerChar, targetCell) || 
					checkDiagonalBC(playerChar, targetCell);
			
		}else {
			return 
					checkColumnAndRow(playerChar, targetCell) ||
					checkDiaganalAD(playerChar, targetCell);
		}

	}

	public boolean checkColumnAndRow(String playerChar, Cell targetCell) {
		// Checkeo en fila
		for (int x = 0; x < X; x++) {
			if (!cellMatrix[x][targetCell.getPosition().y].getText().equalsIgnoreCase(playerChar)) {
				break;
			}
			if (x == 2) {
				return true;
			}
		}

		// Checkeo en columnas
		for (int y = 0; y < Y; y++) {
			if (!cellMatrix[targetCell.getPosition().x][y].getText().equalsIgnoreCase(playerChar)) {
				break;
			}
			if (y == 2) {
				return true;
			}
		}

		return false;
	}
	
	public boolean checkDiaganalAD(String playerChar, Cell targetCell) {
		for(int xy = 0; xy < X; xy++) {
			if (!cellMatrix[xy][xy].getText().equalsIgnoreCase(playerChar)) {
				break;
			}
			
			if( xy == 2 ) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean checkDiagonalBC(String playerChar, Cell targetCell) {
		int y = 2;
		
		for(int x = 0; x < X; x++) {
			if (!cellMatrix[x][y - x].getText().equalsIgnoreCase(playerChar)) {
				break;
			}
			
			if( x == 2 ) {
				return true;
			}
		}
		
		return false;
	}

}
