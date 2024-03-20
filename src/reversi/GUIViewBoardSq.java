package reversi;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JButton;

public class GUIViewBoardSq extends JButton
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Color drawColor; 
	Color borderColor;
	int borderSize; 
	int x_coord;
	int y_coord;

	public GUIViewBoardSq( int width, int height, Color color,
			int borderWidth, Color borderCol ,int x, int y)
	{
		borderSize = borderWidth;
		drawColor = color;
		borderColor = borderCol;
		x_coord=x;
		y_coord=y;
		setMinimumSize( new Dimension(width, height) );
		setPreferredSize( new Dimension(width, height) );
	}

	public GUIViewBoardSq( int width, int height, Color color,int x, int y )
	{
		// Call the other constructor with some default values
		this( width, height, color, 1, new Color(0,0,0),x ,y);
	}

	public int getX_coord()
	{
		return x_coord;
	}
	
	public int getY_coord()
	{
		return y_coord;
	}
	
	public Color getDrawColor()
	{
		return drawColor;
	}

	public void setDrawColor(Color drawColor)
	{
		this.drawColor = drawColor;
	}

	public Color getBorderColor()
	{
		return borderColor;
	}

	public void setBorderColor(Color borderColor)
	{
		this.borderColor = borderColor;
	}

	public int getBorderSize()
	{
		return borderSize;
	}

	public void setBorderSize(int borderSize)
	{
		this.borderSize = borderSize;
	}

	protected void paintComponent(Graphics g)
	{
		//super.paintComponent(arg0);
		if ( borderColor != null )
		{
			g.setColor(borderColor);
			g.fillRect(0, 0, getWidth(), getHeight());
		}
		if ( drawColor != null )
		{
			g.setColor(drawColor);
			g.fillOval(borderSize, borderSize, getWidth()-borderSize*2, getHeight()-borderSize*2);
		}
	}

}
