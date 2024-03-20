package reversi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class GUIView implements IView
{
	IModel model;
	IController controller;
	
	JButton button1_1 = new JButton("Greedy AI (Play White)");
	JButton button1_2 = new JButton("Restart");
	JButton button2_1 = new JButton("Greedy AI (Play Black)");
	JButton button2_2 = new JButton("Restart");
	
	JPanel button_holder1 = new JPanel();
	JPanel button_holder2 = new JPanel();

	JLabel message1 = new JLabel();
	JLabel message2 = new JLabel();
	JPanel board1 = new JPanel();
	JPanel board2 = new JPanel();
	JFrame frame1 = new JFrame();
	JFrame frame2 = new JFrame();
	
	@Override
	public void initialise(IModel model, IController controller)
	{
		// TODO Auto-generated method stub
		this.model = model;
		this.controller = controller;
		
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.setTitle("Player 1 - White");
		frame2.setTitle("Player 2 - Black");
		frame1.setLocationRelativeTo(null); // centre on screen
		frame2.setLocationRelativeTo(null); // centre on screen
		
		frame1.getContentPane().setLayout(new BorderLayout());
		frame2.getContentPane().setLayout(new BorderLayout());

		frame1.getContentPane().add(message1,BorderLayout.NORTH);
		frame2.getContentPane().add(message2,BorderLayout.NORTH);
		
		board1.setLayout( new GridLayout(8,8) );
		board2.setLayout( new GridLayout(8,8) );
		
		frame1.getContentPane().add(board1,BorderLayout.CENTER);
		frame2.getContentPane().add(board2,BorderLayout.CENTER);
		
		button_holder1.setLayout( new GridLayout(1,2) );
		button_holder2.setLayout( new GridLayout(1,2) );

		button_holder1.add(button1_1);
		button_holder1.add(button1_2);
		button_holder2.add(button2_1);
		button_holder2.add(button2_2);
		
		button1_1.addActionListener(new AI());
		button1_2.addActionListener(new RestartPressed());
		button2_1.addActionListener(new AI());
		button2_2.addActionListener(new RestartPressed());
		
		frame1.getContentPane().add(button_holder1,BorderLayout.SOUTH);
		frame2.getContentPane().add(button_holder2,BorderLayout.SOUTH);
		
		int width = model.getBoardWidth();
		int height = model.getBoardHeight();

		
		GUIViewBoardSq[][] grid = buildGrid();
		GUIViewBoardSq[][] reverseGrid = buildReverseGrid();
		
		for ( int y = 0 ; y < height ; y++ )
		{
			for ( int x = 0 ; x < width ; x++ )
			{
				board1.add(grid[x][y]);
			}
		}
		for ( int y = height-1 ; y >=0; y-- )
		{
			for ( int x = width-1 ; x >= 0; x-- )
			{
				board2.add(reverseGrid[x][y]);
			}
		}
		
		
		frame1.pack(); // Resize frame to fit content
		frame1.setLocationRelativeTo(null);
		frame1.setVisible(true);
		
		frame2.pack(); // Resize frame to fit content
		frame2.setLocationRelativeTo(null);
		frame2.setVisible(true);
	}

	public GUIViewBoardSq[][] buildGrid()
	{
		int width = model.getBoardWidth();
		int height = model.getBoardHeight();
		GUIViewBoardSq[][] returnArray = new GUIViewBoardSq[height][width];
		for ( int y = 0 ; y < height; y++ )
		{
			final int finaly = (Integer) y;
			for ( int x = 0 ; x < width; x++ )
			{
				final int finalx = (Integer) x;
				returnArray[x][y]=new GUIViewBoardSq(50, 50, Color.green, 1, Color.green,finalx,finaly);
				returnArray[x][y].addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e)
					{
						GUIViewBoardSq source = ((GUIViewBoardSq) e.getSource());
						
						//if (!(model.getBoardContents(source.getX_coord(), source.getY_coord())==2)) {
						
						controller.squareSelected(1,finalx,finaly);
						//}
//							source.setBorderColor(Color.green);
//							source.setDrawColor(Color.white);
//							int num = (source.getX_coord()+8*(source.getY_coord()));
//							((GUIViewBoardSq)board2.getComponents()[num]).setBorderColor(Color.green);
//							((GUIViewBoardSq)board2.getComponents()[num]).setDrawColor(Color.white);
						
						
						//((GUIViewBoardSq)board2.getComponentAt(7-source.getX_coord(),7- source.getY_coord())).setBorderColor(Color.green);
						//((GUIViewBoardSq)board2.getComponentAt(7-source.getX_coord(),7- source.getY_coord())).setDrawColor(Color.white);
						//System.out.println("X is: " + source.getX_coord() + "inverted X is: "+ (7-source.getX_coord()));

						
						
						//((GUIViewBoardSq) e.getSource()).add(new ButtonLabel(50,50,Color.black,1,Color.white));
						frame1.pack(); // Resize frame to fit content
						frame1.setVisible(true);
						frame1.repaint();
						
						frame2.pack(); // Resize frame to fit content
						frame2.setVisible(true);
						frame2.repaint();
					}
					
				});
//				JButton button = new JButton();
//				button.add(new GUIViewBoardSq();
//				button.addActionListener(e->{System.out.println("1");});
				
			}
		}
		return returnArray;
		
	}
	public GUIViewBoardSq[][] buildReverseGrid()
	{
		int width = model.getBoardWidth();
		int height = model.getBoardHeight();
		GUIViewBoardSq[][] returnArray = new GUIViewBoardSq[height][width];
		for ( int y = height-1 ; y >=0; y-- )
		{
			final int finaly = (Integer) y;
			for ( int x = width-1 ; x >= 0; x-- )
			{
				final int finalx = (Integer) x;
				returnArray[x][y]=new GUIViewBoardSq(50, 50, Color.green, 1, Color.green,finalx,finaly); 
				returnArray[x][y].addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e)
					{
						GUIViewBoardSq source = ((GUIViewBoardSq) e.getSource());
						
						//if (!(model.getBoardContents(source.getX_coord(),source.getY_coord())==1)) {
						controller.squareSelected(2,finalx,finaly);
						//}
						

//							source.setBorderColor(Color.green);
//							source.setDrawColor(Color.black);
//							int num = (64-(source.getX_coord()+1+8*(source.getY_coord())));
//							((GUIViewBoardSq)board1.getComponents()[num]).setBorderColor(Color.green);
//							((GUIViewBoardSq)board1.getComponents()[num]).setDrawColor(Color.black);					
						
						
						//((GUIViewBoardSq)board1.getComponentAt(7-source.getX_coord(),7- source.getY_coord())).setBorderColor(Color.green);
						//((GUIViewBoardSq)board1.getComponentAt(7-source.getX_coord(),7- source.getY_coord())).setDrawColor(Color.black);
						
						//System.out.println("X is: " + source.getX_coord() + "inverted X is: "+ (7-source.getX_coord()));
						//System.out.println("Y is: " + source.getY_coord() + "inverted Y is: "+ (7-source.getY_coord()));

						
						//((GUIViewBoardSq) e.getSource()).add(new ButtonLabel(50,50,Color.black,1,Color.white));
						frame1.pack(); // Resize frame to fit content
						frame1.setVisible(true);
						frame1.repaint();
						
						frame2.pack(); // Resize frame to fit content
						frame2.setVisible(true);
						frame2.repaint();
					}
					
				});
			}
		}
		return returnArray;
		
	}
	
	@Override
	public void refreshView()
	{
		
		for(int i=0;i<64;i++) {
			GUIViewBoardSq source = ((GUIViewBoardSq)board1.getComponents()[i]);
			
			
			
			if (model.getBoardContents(source.getX_coord(),source.getY_coord())==1) {
				source.setBorderColor(Color.green);
				source.setDrawColor(Color.white);
				int num = (64-(source.getX_coord()+1+8*(source.getY_coord())));
				((GUIViewBoardSq)board2.getComponents()[num]).setBorderColor(Color.green);
				((GUIViewBoardSq)board2.getComponents()[num]).setDrawColor(Color.white);
			}
			else if(model.getBoardContents(source.getX_coord(),source.getY_coord())==2){
				source.setBorderColor(Color.green);
				source.setDrawColor(Color.black);
				int num = (64-(source.getX_coord()+1+8*(source.getY_coord())));
				((GUIViewBoardSq)board2.getComponents()[num]).setBorderColor(Color.green);
				((GUIViewBoardSq)board2.getComponents()[num]).setDrawColor(Color.black);					
			}
			else {
				source.setBorderColor(Color.green);
				source.setDrawColor(Color.green);
				int num = (64-(source.getX_coord()+1+8*(source.getY_coord())));
				((GUIViewBoardSq)board2.getComponents()[num]).setBorderColor(Color.green);
				((GUIViewBoardSq)board2.getComponents()[num]).setDrawColor(Color.green);		
			}
		}
		
		
		
		
		frame1.pack(); // Resize frame to fit content
		frame1.setVisible(true);
		frame1.repaint();
		
		frame2.pack(); // Resize frame to fit content
		frame2.setVisible(true);
		frame2.repaint();

	}

	@Override
	public void feedbackToUser(int player, String message)
	{
		if ( player == 1 )
			message1.setText(message);
		else if ( player == 2 )
			message2.setText(message);	
	}
	
	public class SquarePressed implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			Component[] list = board1.getComponents();
			for(int i=0;i<list.length;i++) {
				if (e.getSource()==list[i])
				{
					controller.squareSelected(1,i%8,i/8);
				}
			}
			Component[] list2 = board2.getComponents();
			for(int i=0;i<list2.length;i++) {
				if (e.getSource()==list2[i])
				{
					controller.squareSelected(2,7-i%8,7-i/8);
				}
			}
			
		}
	}
	public class RestartPressed implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			controller.startup();
		}
	}
	public class AI implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if (((JButton)e.getSource()).getText() == "Greedy AI (Play White)") {
				controller.doAutomatedMove(1);
			}else if(((JButton)e.getSource()).getText() == "Greedy AI (Play Black)"){
				controller.doAutomatedMove(2);
			}
		}
	}
}
