package reversi;

public class ReversiController implements IController
{
	IModel model;
	IView view;

	java.util.Random rand = new java.util.Random();


	@Override
	public void initialise(IModel model, IView view)
	{
		this.model = model;
		this.view = view;
	}

	@Override
	public void startup()
	{
		model.clear(0);
		view.refreshView();
		
//		board1.setVisible(true);
//		board2.setVisible(true);
//		frame1.repaint();
//		frame2.repaint();
		
		model.setFinished(false);
		model.setPlayer(1);		
		
		model.setBoardContents(3, 4, 2);
		model.setBoardContents(4, 3, 2);
		model.setBoardContents(3, 3, 1);
		model.setBoardContents(4, 4, 1);

		// Consider setting up any initial pieces here in your own controller
		
		// Refresh all messages and frames
		view.feedbackToUser(model.getPlayer(), "White player - choose where to put your piece");
		view.feedbackToUser(notPlayer(model.getPlayer()), "Black player - not your turn");
		view.refreshView();
	}

	@Override
	public void update()
	{
		
		if(model.hasFinished())
		{
			finishedGame();
		}
		else
		{
			int player = model.getPlayer();
			
			if(!areThereValidMoves(notPlayer(player)))
			{
				if(!areThereValidMoves(player))
				{
					
					finishedGame();
				}
				else
				{
					defaultMessages(notPlayer(player));
//					view.feedbackToUser(notPlayer(player), "You have no valid moves left. Switching player." );
				}
			}
			else if(!areThereValidMoves(player))
			{
				if(!areThereValidMoves(notPlayer(player)))
				{
					finishedGame();
				}
				else
				{
					defaultMessages(player);
//					view.feedbackToUser(player, "You have no valid moves left. Switching player." );
				}
			}
			else
			{
				defaultMessages(notPlayer(player));
			}
		}	
		view.refreshView();
	}
	
	public String playerString(int player)
	{
		if(player == 1)
			return "White";
		else
			return "Black";
	}
	
	public int notPlayer(int player)
	{
		if (player == 1)
			return 2;
		else
			return 1;
	}
	
	public String getPlayerPieces(int player)
	{		
		StringBuffer buf = new StringBuffer();
		
		for ( int x = 0 ; x < model.getBoardWidth() ; x++ ) 
			for ( int y = 0 ; y < model.getBoardHeight() ; y++ )
				if(model.getBoardContents(x,y) == player)
					buf.append(x+","+y+";");
		
//		int countAgain = 0;
//		
//		int [][] pieces = new int[count][2];
//
//		for ( int x = 0 ; x < model.getBoardWidth() ; x++ ) 
//			for ( int y = 0 ; y < model.getBoardHeight() ; y++ )
//				if(model.getBoardContents(x,y) == player) {
//					pieces[countAgain][0] = x;
//					pieces[countAgain++][1] = y;
//				}
		return buf.toString();
	}
	
	public int countSquares(int player, int x, int y, boolean flag)
	{
		int numSquares = 0;
//		System.out.println("**Selected "+x+","+y);
		for (int xoffset =  -1 ; xoffset <= 1 ; xoffset ++)
		{
			for (int yoffset =  -1 ; yoffset <= 1 ; yoffset ++)
			{
				if(!(xoffset==0 && yoffset==0))
				{
					int localSquares = 0;
					int localx = x + xoffset;
					int localy = y + yoffset;
//					System.out.println("- Testing in direction of" +localx +"," + localy);
					while(0<= localx && localx < model.getBoardWidth() && 
							0 <= localy && localy < model.getBoardHeight() &&
							model.getBoardContents(localx,localy) == notPlayer(player))
					{
//						System.out.println("searching across to "+localx+"and"+localy);
						localSquares++;
						localx += xoffset;
						localy += yoffset;
					}
					
					if(0<= localx && localx < model.getBoardWidth() && 
							0 <= localy && localy < model.getBoardHeight() &&
							localSquares > 0 &&
							model.getBoardContents(localx,localy) == player)
					{	
						localSquares++;
						numSquares += localSquares;
						if(flag)
						{
							
							while(localx != x || localy != y)
							{
								
								localx -= xoffset;
								localy -= yoffset;
//								System.out.println("this would set: "+ localx +"," +localy);
								model.setBoardContents(localx, localy, player);
							}
						}
					}
				}
			}
		}
		return numSquares;
	}
	
	public String[] validMoves(int player)
	{
		StringBuffer buf = new StringBuffer();
		StringBuffer buf2 = new StringBuffer();

		//a valid move is a move that takes the other players' pieces.
		// this will always be touching the other players' piece
		
		String[] otherplayers = getPlayerPieces(notPlayer(player)).split(";");
		
		
//		System.out.println(otherplayers.length + ": NUMBER OF OPPONENT PIECES");
		for(int i = 0;i<otherplayers.length;i++) {
			int x = Integer.parseInt(otherplayers[i].split(",")[0]);  
			int y = Integer.parseInt(otherplayers[i].split(",")[1]);  

			//up
			if(y<model.getBoardHeight() && model.getBoardContents(x,y+1) == 0) 
			{
				int y2 = y-1;
				boolean flag = false;
				while(y2>=0 && flag==false)
				{
					if(model.getBoardContents(x,y2) == notPlayer(player))
						y2--;
					else if(model.getBoardContents(x,y2) == player)
					{	
//						System.out.println(x+","+(y+1));
						buf.append(x+","+(y+1)+";");
						buf2.append(x+","+y2+";");
						flag = true;
					}
					else
						flag = true;
				}
			}
			//right
			if(x<model.getBoardWidth()-1 && model.getBoardContents(x+1,y) == 0) 
			{
				int x2 = x-1;
				boolean flag = false;
				while(x2>=0 && flag==false)
				{
					if(model.getBoardContents(x2,y) == notPlayer(player))
						x2--;
					else if(model.getBoardContents(x2,y) == player)
					{
						//ADD TO VALID MOVES!
//						System.out.println((x+1)+","+y);
						buf.append((x+1)+","+y+";");
						buf2.append(x2+","+y+";");
						flag = true;
					}
					else
						flag = true;
				}
			}
			//down
			if(y>0 && model.getBoardContents(x,y-1) == 0) 
			{
				int y2 = y+1;
				boolean flag = false;
				while(y2< model.getBoardHeight() && flag==false)
				{
					if(model.getBoardContents(x,y2) == notPlayer(player))
						y2++;
					else if(model.getBoardContents(x,y2) == player)
					{	
						//ADD TO VALID MOVES!
//						System.out.println(x+","+(y-1));
						buf.append(x+","+(y-1)+";");
						buf2.append(x+","+y2+";");
						flag = true;
					}
					else
						flag = true;
				}
			}
			//left
			if(x>0 && model.getBoardContents(x-1,y) == 0) 
			{
				int x2 = x+1;
				boolean flag = false;
				while(x2< model.getBoardWidth()-1 && flag==false)
				{
					if(model.getBoardContents(x2,y) == notPlayer(player))
						x2++;
					else if(model.getBoardContents(x2,y) == player)
					{	
						//ADD TO VALID MOVES!
//						System.out.println((x-1)+","+y);
						buf.append((x-1)+","+y+";");
						buf2.append(x2+","+y+";");
						flag = true;
					}
					else
						flag = true;
				}
			}
		}
		
		String[] array = new String[2];
		array[0] = buf.toString();
		array[1] = buf2.toString();
		return array;
	}
	
	public String validMove(int player, int x, int y)
	{
		if (!(model.getBoardContents(x, y) == 0))
			return "";
		else if(!(validMoves(player)[0].contains(x+","+y+";")))
		{
			// We have been asked not to display a message for this...
//			view.feedbackToUser(player, "Invalid move");
			return "";
		}
		else
		{
//			for(int i = 0; i<validMoves(player)[0].split(";").length; i++)
//			{
//				System.out.println(validMoves(player)[0].split(";")[i]);
//				System.out.println(validMoves(player)[1].split(";")[i]);
//
//			}
//			return "";
//		}
//		{

			int index = -1;
			for(int i = 0; i<validMoves(player)[0].split(";").length; i++)
			{
				String match = x+","+y;

				if (validMoves(player)[0].split(";")[i].compareTo(match)==0)
				{
					index = i;
				}
			}
			
			return validMoves(player)[1].split(";")[index];
		}
	}
	
	public void fillSquares(int player, int x, int y, int x2, int y2)
	{
		int xDifference = x2 - x;
		int yDifference = y2 - y;
		
//		System.out.println("difference in x is: " + xDifference + "difference in y is: "+ yDifference);
		
		if (xDifference == 0)
		{
			while(y != y2)
			{
				if (yDifference < 0 )
				{
//					System.out.println(x + "," + y--);
					model.setBoardContents(x, y--, player);
				}
				else
				{
//					System.out.println(x + "," + y++);
					model.setBoardContents(x, y++, player);

				}
			}
			
		}
		else if (yDifference == 0)
		{
			while(x != x2)
			{
				if (xDifference < 0 )
				{
//					System.out.println(x-- + "," + y);
					model.setBoardContents(x--, y, player);

				}
				else
				{
//					System.out.println(x++ + "," + y);
					model.setBoardContents(x++, y, player);

				}
			}
		}
		else 
		{
			//difference is diagonal...
		}
	}

	public boolean areThereValidMoves(int player)
	{
		for ( int x = 0 ; x < model.getBoardWidth() ; x++ ) 
			for ( int y = 0 ; y < model.getBoardHeight() ; y++ )
				if(model.getBoardContents(x, y)==0 && countSquares(player,x,y,false)!=0)
				{
					return true;
				}
		return false;
	}
	
	
	@Override
	public void squareSelected(int player, int x, int y)
	{
		// The finished flag never gets set by this controller, but the SimpleTestModel could set it
		if ( model.hasFinished() )
		{
			return; // Don't the set board contents
		}
		if (player != model.getPlayer())
		{
			view.feedbackToUser(player, "It is not your turn!" );
			return;
		}
		if(model.getBoardContents(x, y) != 0)
		{
			return;
		}
		
		if(countSquares(player, x,y,true) == 0)
		{
			//incorrect square played
			return;
		}
		else
		{
			if(!areThereValidMoves(notPlayer(player)))
			{
				if(!areThereValidMoves(player))
				{
					
					finishedGame();
				}
				else
					view.feedbackToUser(notPlayer(player), "You have no valid moves left. Switching player." );
			}
			else
			{
//				view.feedbackToUser(notPlayer(player), "Your turn." );
				defaultMessages(player);
				model.setPlayer(notPlayer(model.getPlayer()));
			}
		}
		view.refreshView();
//		
//		String finalSquare = validMove(player,x, y);
//		if (finalSquare != "")
//		{
//			int x2 = Integer.parseInt(finalSquare.split(",")[0]);
//			int y2 = Integer.parseInt(finalSquare.split(",")[1]);
//
//			fillSquares(player,x,y,x2,y2);
//			//check if next player has no moves...
//			if (validMoves(notPlayer(player))[0]=="") {
//				if (validMoves(player)[0]=="")
//				{
//					//game has finished as both players have no valid moves left.
//					model.setFinished(true);
//					//give players their score and ignore input.
//					
//				}
//				else
//				{
//					view.feedbackToUser(notPlayer(player), "You have no valid moves left. Switching player." );
//				}
//			}
//			else
//				this.player = notPlayer(player);
//			
	}
	
	public void defaultMessages(int player)
	{
		if(player == 1)
		{
			view.feedbackToUser(notPlayer(player),"Black player - choose where to put your piece");
			view.feedbackToUser(player, "White player - not your turn");
		}
		else
		{
			view.feedbackToUser(notPlayer(player),"White player - choose where to put your piece");
			view.feedbackToUser(player, "Black player - not your turn");
		}
	}
	
	public void finishedGame()
	{
		String whitesq =  getPlayerPieces(1);
		String blacksq = getPlayerPieces(2);
		int white;
		int black;
		if(whitesq == "")
			white = 0;
		else
		{
			white = getPlayerPieces(1).split(";").length;
		}
		if(blacksq == "")
			black = 0;
		else
		{
			black = getPlayerPieces(2).split(";").length;
		}
		
		if(white > black)
		{
			view.feedbackToUser(1, "White won. White " + white + " to Black " + black + ". Reset the game to replay.");
			view.feedbackToUser(2, "White won. White " + white + " to Black " + black + ". Reset the game to replay.");
		}
		else if(black > white)
		{
			view.feedbackToUser(1, "Black won. Black "+ black + " to White " +white+ ". Reset the game to replay.");
			view.feedbackToUser(2, "Black won. Black "+ black + " to White " +white+ ". Reset the game to replay.");
		}
		else
		{
			view.feedbackToUser(1, "Draw. Both players ended with "+ black + " pieces. Reset game to replay.");
			view.feedbackToUser(2, "Draw. Both players ended with "+ black + " pieces. Reset game to replay.");
		}
		model.setFinished(true);
	}

	
	
	@Override
	public void doAutomatedMove(int player)
	{
		int bestx= -1;
		int besty = -1;
		int max = 0;
		int temp = 0;
		for ( int x = 0 ; x < model.getBoardWidth(); x++ )
		{
			for ( int y = 0 ; y < model.getBoardHeight(); y++ )
			{
				temp = countSquares(player, x, y, false);
				if (model.getBoardContents(x, y)==0 && temp > max)
				{
					bestx = x;
					besty = y;
					max = temp;
				}
			}
		}
		if(bestx != -1 || besty != -1)
		{
			squareSelected(player,bestx,besty);
		}
		else
		{
			//couldn't perform move
		}
	}

}
