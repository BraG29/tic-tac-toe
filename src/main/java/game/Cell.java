package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class Cell extends JLabel {
	
//	Posicion de la Celda
	private Point position;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Cell(int x, int y) {
		this.position = new Point(x, y);
		
		this.setText("");
		this.setFont(new Font("Tahoma", Font.PLAIN, 40));
		this.setHorizontalAlignment(SwingConstants.CENTER);
		this.setBorder(new LineBorder(new Color(0, 0, 0)));
//		this.setCaretColor(this.getBackground());
//        this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR)); 
	}
	
	public Point getPosition() {
		return position;
	}
	
	//Si es el turno del jugador 0 dibuja una X verde
	//Si es el turno del jugador 1 dibuja una O roja
	public void drawFor(int player) {
		if(player == 0) {
			drawX();
		}
		else {
			drawO();
		}
		cleanListener();
	}
	
	public void cleanListener() {
		MouseListener listener = this.getMouseListeners()[0];
		this.removeMouseListener(listener);
	}

	public void drawX() {
		setText("X");
		setForeground(Color.GREEN);
	}
	
	public void drawO() {
		setText("O");
		setForeground(Color.RED);
	}
}
