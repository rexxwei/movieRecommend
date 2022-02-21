
import java.awt.*;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.ImageIcon;  
import javax.swing.JButton;  
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.io.*;
import java.nio.file.*;

public class Main extends JFrame
{
	
	boolean blackturn = true;
	boolean gameover = false;
	private int blackChess = 0, whiteChess = 0;
	private int[][] data = new int[4][4];
	int movect = 0;
    Graphics g = this.getGraphics();
	private JPanel p1 = new JPanel();
	private JButton renewB = new JButton("Restar");
	private JButton saveB = new JButton("Save");
	private JButton openB = new JButton("Open");
	private JButton display = new JButton("");
	private JPanel p2 = new JPanel();
	private JButton button[][] = new JButton[4][4];
	
	public Main()
	{
		Container c = this.getContentPane();
		c.add(p1, BorderLayout.NORTH);
		p1.add(renewB);
		p1.add(saveB);
		p1.add(openB);
		p1.add(display);
		c.add(p2, BorderLayout.CENTER);
		p2.setLayout(new GridLayout(4, 4));
		
		for (int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				button[i][j] = new JButton("");
			}
		}
		newbground();
		newgame();
		drawchess();
		renewB.addActionListener(new renewHandler());		
		saveB.addActionListener(new saveHandler());
		openB.addActionListener(new openHandler());
				
	} //end of Othello
	
	//^^^^^^^^^^ 4 buttom function ^^^^^^^^^^^
	
	// ------ [Restar buttom] ------
	class renewHandler implements ActionListener
	{
 
		public void actionPerformed(ActionEvent e) 
		{
			for (int i = 0; i < 4; i++)
			{
				for (int j = 0; j < 4; j++)
				 {
				   data[i][j] = 0;
				 }
			}
			
			for (int u = 0; u < 4; u++)
			{
				for (int w = 0; w < 4; w++)
				{
		               ImageIcon icon = new ImageIcon("image\\Gray.jpg");
		               button[u][w].setIcon(icon);
				}
			}
			
			movect = 0;
			blackturn = true;
			gameover = false;
			blackChess = 0;
			whiteChess = 0;
			drawchess();
		}
	}
	// end of Restar buttom
	
	//------ [Save buttom] --------
	class saveHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
	    	try 
	    	{ 
	    		File writeName = new File("Revrec.txt"); //designate the output file name 
	    		writeName.createNewFile(); //create new file, if exist then overwrite it
	    		try (FileWriter writer = new FileWriter(writeName); 
	    		 BufferedWriter output = new BufferedWriter(writer) ) 
	    		    { 
	    	    	for (int t = 0 ; t < 4; ++t) //oupt the result line by line to outstream
	    	    	 {
	    	           for (int p = 0 ; p < 4; ++p)
	    	    	   {
	    	             output.write(String.valueOf(data[t][p]));
	    	        	 if (p != 3)
	    	               output.write(","); 
	    	           }
	    	           output.write("\r\n");
	    	    	  }
	    	    	
	    	    	String black;
    	        	if (blackturn)
	    	        	black = "true";
	    	        else
	    	        	black = "false";
	    	        output.write(black);
	    	          
	    			output.flush(); // flush the outstream into file
	    			output.close();
	    			} 
	    	  } 
	    	catch(Exception e1)
	    	{
	    	  e1.printStackTrace();
	    	}
	    	JOptionPane.showMessageDialog(null, "OK game saved!");
		}
	} // end of Save buttom
	
	//----- [Open buttom] -------
	class openHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
	    	Path file = Paths.get("Revrec.txt"); // read the file
			try
			{
				InputStream input = new BufferedInputStream(Files.newInputStream(file));
	    		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
				String line;
				movect = 0;
				for (int num =0; num < 4; num++)
				{
				   line = reader.readLine();
				   String[] temp = line.split(",");
				   for(int i = 0; i < temp.length; i++)
					{
						data[num][i] = Integer.parseInt(temp[i]);
						if (data[num][i] != 0)
						  movect++; 
					}
				}
				line = reader.readLine();
				blackturn = Boolean.parseBoolean(line);
				
				for (int u = 0; u < 4; u++)
				{
					for (int w = 0; w < 4; w++)
					{
			               ImageIcon icon = new ImageIcon("image\\Gray.jpg");
			               button[u][w].setIcon(icon);
					}
				}
				drawchess();
				String s = new String();
				if (blackturn == true)
					s="Black";
				if (blackturn == false)
					s="White";
				JOptionPane.showMessageDialog(null, "Loading success, it is "+s+" turn");
				
			}
			catch (Exception e2) 
			{
				e2.printStackTrace();
			}
		}
	} // end of Open buttom
	
	//^^^^^^^^^^ End of 4 buttom function ^^^^^^^^^^^
	
	// &&& begin of class Handler &&&
	class Handler implements ActionListener
	{
		private int row = -1, col = -1;
		public Handler(int x, int y)
		{
			row = x;
			col = y;
		}
		
		//---### evaluate the move ###---
		private boolean isValidPosition(int x, int y) //line 841
		{
			if (data[x][y] != 0)
				return false;
			
			if (movect < 4) // first 4 moves
			{
				if (x >= 1 && x <= 2)
				{
					if (y >= 1 && y <= 2)
					//{
							return true;
					//}
					else
						return false;
				}
				else
					return false;
			}
			
			else // moves after first 4
			{			
			if (blackturn == true) //black's turn
			 {
				for (int i = 0; i < 4; i++) //evaluate vertical direction
				{
					if (data[i][y] == 1)
					{
						if ((i-x) >= 2)
						{
							int count = 0;
							for (int k = x; k < i; k++)
							{
								if (data[k][y] == -1)
									count++;
							}
							if (count == (i-x-1)) // if all white between two black
							{
								return true;
							}
						}
						if ((x-i) >= 2)
						{
							int count = 0;
							for (int k = x; k > i; k--)
							{
								if (data[k][y] == -1)
									count++;
							}
							if (count == (x-i-1))
								return true;
						}
					}
				}
				
				//evaluate horizontal direction
				for (int j = 0; j < 4; j++)
				{
					if (data[x][j] == 1)
					{
						if ((j-y)>= 2)
						{
							int count = 0;
							for (int k = y; k < j; k++)
							{
								if (data[x][k] == -1)
									count++;
							}
							if (count == (j-y-1))
								return true;
						}
						if ((y-j)>= 2)
						{
							int count = 0;
							for (int k = y;k>j;k--)
							{
								if (data[x][k] == -1)
									count++;
							}
							if (count == (y-j-1))
								return true;
						}
					}
				}
				
				//evaluate diagonal direction
				for (int i = 0; i < 4 ; i++)
				{
					 for (int j = 0; j < 4; j++)
					 {
						 if (data[i][j] == 1)
						 {
							 if ((x-i) == (y-j) && (x-i) >= 2)
							 {
								 int yy = y;
								 int count = 0;
								 for (int k = x;k>i;k--)
								 {
									 if (data[k][yy] == -1)
										 count++;
									 yy--;
								 }
								 if (count == (x-i-1))
									 return true;
							 }
							 if ((x-i) == (j-y) && (x-i) >= 2)
							 {
								 int yy = y;
								 int count = 0;
								 for (int k = x;k>i;k--)
								 {
									 if (data[k][yy] == -1)
										 count++;
									 yy++;
								 }
								 if (count == (x-i-1))
									 return true;
							 }
							 if ((i-x) == (y-j) && (i-x) >= 2)
							 {
								 int yy = y;
								 int count = 0;
								 for (int k = x;k<i;k++)
								 {
									 if (data[k][yy] == -1)
										 count++;
									 yy--;
								 }
								 if (count == (i-x-1))
									 return true;
							 }
							 if ((i-x) == (j-y) && (i-x) >= 2)
							 {
								 int yy = y;
								 int count = 0;
								 for (int k = x;k<i;k++)
								 {
									 if (data[k][yy] == -1)
										 count++;
									 yy++;
								 }
								 if (count == (i-x-1))
									 return true;
							 }
						 }
					 }
				}
				return false;
			} //end of black turn
			
			else //it's white's turn
			{
				for (int i = 0; i < 4; i++)
				{
					if (data[i][y] == -1)
					{
						if ((i - x) >=  2)
						{
							int count = 0;
							for (int k = x; k < i; k++)
							{
								if (data[k][y] == 1)
									count++;
							}
							if (count == (i - x - 1))
								return true;
						}
						if ((x - i) >=  2)
						{
							int count = 0;
							for (int k = x; k > i; k--)
							{
								if (data[k][y] == 1)
									count++;
							}
							if (count == (x - i - 1))
								return true;
						}
					}
				}
 
				for (int j = 0; j < 4; j++)
				{
					if (data[x][j] == -1)
					{
						if ((j - y) >=  2)
						{
							int count = 0;
							for (int k = y; k < j; k++)
							{
								if (data[x][k] == 1)
									count++;
							}
							if (count == (j - y - 1))
								return true;
						}
 
						if ((y - j) >=  2)
						{
							int count = 0;
							for (int k = y; k > j; k--)
							{
								if (data[x][k] == 1)
									count++;
							}
							if (count == (y - j - 1))
								return true;
						}
 
					}
				}
 
				for (int i = 0; i < 4; i++)
				{
					for (int j = 0; j < 4; j++)
					{
						if (data[i][j] == -1)
						{
							if ((x - i) == (y - j) && (x - i) >=  2)
							{
								int yy = y;
								int count = 0;
								for (int k = x; k > i; k--)
								{
									if (data[k][yy] == 1)
									{
										count++;
									}
									yy--;
 
								}
								if (count == (x - i - 1))
									return true;
							}
 
							if ((x - i) == (j - y) && (x - i) >=  2)
							{
								int yy = y;
								int count = 0;
								for (int k = x; k > i; k--)
								{
									if (data[k][yy] == 1)
									{
										count++;
									}
									yy++;
 
								}
								if (count == (x - i - 1))
									return true;
							}
 
							if ((i - x) == (y - j) && (i - x) >=  2)
							{
								int yy = y;
								int count = 0;
								for (int k = x; k < i; k++)
								{
									if (data[k][yy] == 1)
									{
										count++;
									}
									yy--;
								}
								if (count == (i - x - 1))
									return true;
							}
 
							if ((i - x) == (j - y) && (i - x) >=  2)
							{
								int yy = y;
								int count = 0;
								for (int k = x; k < i; k++)
								{
									if (data[k][yy] == 1)
									{
										count++;
									}
									yy++;
								}
								if (count == (i - x - 1))
									return true;
							}
 
						}
					}
				}
 
				return false;
			} //end of white turn
		  } //end of move after 4
		} //---### end of evaluate the move ###---

    //----------- seperate evaluate and perform part --------------
		
		// ****** Refresh (the chess and board) ***************
		private void refresh(int x, int y)
		{
			
			if (movect < 4) // perform first 4 moves
			{
				if (x >= 1 && x <= 2)
				{
					if (y >= 1 && y <= 2)
					{
						if (blackturn == true)
						{
							data[x][y] = 1;
							++movect;
						}	
						else
						{
							data[x][y] = -1;
							++movect;
						}
					}

				}
			} //end of first 4 moves
			
			else
			{ //move after first 4
			if (blackturn == true) //If it's black's turn
			{
				for (int i = 0; i < 4; i++) //change vertical direction
				{
					if (data[i][y] == 1)
					{
						if ((i - x) >=  2)
						{
							int count = 0;
							for (int k = x; k < i; k++)
							{
								if (data[k][y] == -1)
									count++;
							}
 
							if (count == (i - x - 1))
							{
								for (int k = x; k < i; k++)
									data[k][y] = 1;
							}
 
						}
 
						if ((x - i) >=  2)
						{
							int count = 0;
							for (int k = x; k > i; k--)
							{
								if (data[k][y] == -1)
									count++;
							}
 
							if (count == (x - i - 1))
							{
								for (int k = x; k > i; k--)
									data[k][y] = 1;
							}
						}
					}
				}
 
				for (int j = 0; j < 4; j++) //change horizontal direction
				{
					if (data[x][j] == 1)
					{
						if ((j - y) >=  2)
						{
							int count = 0;
							for (int k = y; k < j; k++)
							{
								if (data[x][k] == -1)
									count++;
							}
 
							if (count == (j - y - 1))
							{
								for (int k = y; k < j; k++)
									data[x][k] = 1;
							}
						}
 
						if ((y - j) >=  2)
						{
							int count = 0;
							for (int k = y; k > j; k--)
							{
								if (data[x][k] == -1)
									count++;
							}
							if (count == (y - j - 1))
							{
								for (int k = y; k > j; k--)
									data[x][k] = 1;
							}
						}
					}
				}
 
				for (int i = 0; i < 4; i++) //change diagnoal direction
				{
					for (int j = 0; j < 4; j++)
					{
						if (data[i][j] == 1)
						{
							if ((x - i) == (y - j) && (x - i) >=  2)
							{
								int yy = y;
								int count = 0;
								for (int k = x; k > i; k--)
								{
									if (data[k][yy] == -1)
									{
										count++;
									}
									yy--;
 
								}
								if (count == (x - i - 1))
								{
									yy = y;
									for (int k = x; k > i; k--)
									{
										data[k][yy] = 1;
										yy--;
									}
								}
							}
 
							if ((x - i) == (j - y) && (x - i) >=  2)
							{
								int yy = y;
								int count = 0;
								for (int k = x; k > i; k--)
								{
									if (data[k][yy] == -1)
									{
										count++;
									}
									yy++;
 
								}
								if (count == (x - i - 1))
								{
									yy = y;
									for (int k = x; k > i; k--)
									{
										data[k][yy] = 1;
										yy++;
									}
								}
							}
 
							if ((i - x) == (y - j) && (i - x) >=  2)
							{
								int yy = y;
								int count = 0;
								for (int k = x; k < i; k++)
								{
									if (data[k][yy] == -1)
									{
										count++;
									}
									yy--;
 
								}
								if (count == (i - x - 1))
								{
									yy = y;
									for (int k = x; k < i; k++)
									{
										data[k][yy] = 1;
										yy--;
									}
								}
							}
 
							if ((i - x) == (j - y) && (i - x) >=  2)
							{
								int yy = y;
								int count = 0;
								for (int k = x; k < i; k++)
								{
									if (data[k][yy] == -1)
									{
										count++;
									}
									yy++;
 
								}
								if (count == (i - x - 1))
								{
									yy = y;
									for (int k = x; k < i; k++)
									{
										data[k][yy] = 1;
										yy++;
									}
								}
							}
						}
					}
				}
			}//end of change black chess
			
			if (blackturn == false) //it's white's turn, change white chess
			{
				for (int i = 0; i < 4; i++)
				{
					if (data[i][y] == -1)
					{
						if ((i - x) >=  2)
						{
							int count = 0;
							for (int k = x; k < i; k++)
							{
								if (data[k][y] == 1)
									count++;
							}
 
							if (count == (i - x - 1))
							{
								for (int k = x; k < i; k++)
									data[k][y] = -1;
							}
 
						}
						if ((x - i) >=  2)
						{
							int count = 0;
							for (int k = x; k > i; k--)
							{
								if (data[k][y] == 1)
									count++;
							}
 
							if (count == (x - i - 1))
							{
								for (int k = x; k > i; k--)
									data[k][y] = -1;
							}
						}
					}
				}
 
				for (int j = 0; j < 4; j++)
				{
					if (data[x][j] == -1)
					{
						if ((j - y) >=  2)
						{
							int count = 0;
							for (int k = y; k < j; k++)
							{
								if (data[x][k] == 1)
									count++;
							}
 
							if (count == (j - y - 1))
							{
								for (int k = y; k < j; k++)
									data[x][k] = -1;
							}
						}
						if ((y - j) >=  2)
						{
							int count = 0;
							for (int k = y; k > j; k--)
							{
								if (data[x][k] == 1)
									count++;
							}
							if (count == (y - j - 1))
							{
								for (int k = y; k > j; k--)
									data[x][k] =-1;
							}
						}
					}
				}
 
				for (int i = 0; i < 4; i++)
				{
					for (int j = 0; j < 4; j++)
					{
						if (data[i][j] == -1)
						{
							if ((x - i) == (y - j) && (x - i) >=  2)
							{
								int yy = y;
								int count = 0;
								for (int k = x; k > i; k--)
								{
									if (data[k][yy] == 1)
									{
										count++;
									}
									yy--;
								}
								if (count == (x - i - 1))
								{
									yy = y;
									for (int k = x; k > i; k--)
									{
										data[k][yy] = -1;
										yy--;
									}
								}
							}
 
							if ((x - i) == (j - y) && (x - i) >=  2)
							{
								int yy = y;
								int count = 0;
								for (int k = x; k > i; k--)
								{
									if (data[k][yy] == 1)
									{
										count++;
									}
									yy++;
 
								}
								if (count == (x - i - 1))
								{
									yy = y;
									for (int k = x; k > i; k--)
									{
										data[k][yy] = -1;
										yy++;
									}
								}
							}
							if ((i - x) == (y - j) && (i - x) >=  2)
							{
								int yy = y;
								int count = 0;
								for (int k = x; k < i; k++)
								{
									if (data[k][yy] == 1)
									{
										count++;
									}
									yy--;
 
								}
								if (count == (i - x - 1))
								{
									yy = y;
									for (int k = x; k < i; k++)
									{
										data[k][yy] = -1;
										yy--;
									}
								}
							}
							if ((i - x) == (j - y) && (i - x) >=  2)
							{
								int yy = y;
								int count = 0;
								for (int k = x; k < i; k++)
								{
									if (data[k][yy] == 1)
									{
										count++;
									}
									yy++;
 
								}
								if (count == (i - x - 1))
								{
									yy = y;
									for (int k = x; k < i; k++)
									{
										data[k][yy] = -1;
										yy++;
 
									}
								}
							}
						}
					}
				}
			} //end of change white chess
		  } //end of moves after first 4
		} // *** END of refresh (the chess and board) *** 

		//******************* this is a sperate line :) **************************************
		
		//evaluate has valid position or not
		private boolean hasValidPosition()
		{
			for (int i = 0; i < 4; i++)
			{
				for (int j = 0; j < 4; j++)
				{
					if (isValidPosition(i, j))
						return true;
				}
			}
			return false;
		} //end of hasValidPosition
		
		// changePlayer
		public void changePlayer()
		{
			if (blackturn == true)
			  blackturn = false;
			else
			  blackturn = true;
		} 
		//end of changePlayer
		
		//determine who win
		private void whoWin()
		{
			for (int i = 0;i<4;i++)
			{
				for (int j = 0;j<4;j++)
				{
					if (data[i][j] == 1)
						blackChess++;
					if (data[i][j] == -1)
						whiteChess++;
				}
			}
			if (blackChess > whiteChess)
				JOptionPane.showMessageDialog(null, "Black Win!");
			else if (blackChess < whiteChess)
				JOptionPane.showMessageDialog(null, "White Win!");
			else 
				JOptionPane.showMessageDialog(null, "Game Over, it's EVEN!");
		} //end of whoWin
		
		//if the spot is valid, perform the move
		public void actionPerformed(ActionEvent e) 
		{
			if (gameover == true)
			{
				JOptionPane.showMessageDialog(null, "Game over, ReStart");
				return;
			}
			else
			{
				if (!isValidPosition(row, col))
				{
					JOptionPane.showMessageDialog(null, "Illegal move, try another spot!");
					return;
				}
				else
				{
					refresh(row, col);
					changePlayer();
					drawchess();
					if (!hasValidPosition())
					{
						changePlayer();
						{
							if (!hasValidPosition())
							{
								gameover = true;
								whoWin();
							}
							else
							{
								JOptionPane.showMessageDialog(null, "No move for opponent, keep going");
								return;
							}
						}
					}
					else 
						return;
				}
			}
		} //end of actionPerformed
		
	} // &&& end of Handler &&&
	
    //-----***** drawChess method *****-----
	private void drawchess()
	{
	  //display who's turn
	  if (blackturn == true)
	   {
		    ImageIcon picon = new ImageIcon("image\\Bbuttom.jpg");
		    display.setIcon(picon);
	   }
      else
	   {
		    ImageIcon picon = new ImageIcon("image\\Wbuttom.jpg");
		    display.setIcon(picon);
	    }
		  
	  //------ draw the chess -----
      for(int i = 0; i < 4; i++)
      {
         for(int j = 0; j < 4; j++)
         {
            if (data[i][j] == 1)  // draw black chess
            {
               ImageIcon icon = new ImageIcon("image\\black.jpg");
               button[i][j].setIcon(icon);
            	
             }  
            else if (data[i][j] == -1)  // draw white chess
            {
               ImageIcon icon = new ImageIcon("image\\white.jpg");
               button[i][j].setIcon(icon);
             }
          }
       }
	} // end of drawhess
	
    //new backbground
	private void newbground()
	{
		for (int i = 0;i<4;i++)
		{
			for (int j = 0;j<4;j++)
			{
				p2.add(button[i][j]);
				button[i][j].setBackground(Color.GRAY);
	            ImageIcon icon = new ImageIcon("image\\Gray.jpg");
	            button[i][j].setIcon(icon);
				button[i][j].addActionListener(new Handler(i, j));
			}
		}
	} //end of new background
	
	//--- New game
	private void newgame()
	{
	  
	  for (int i = 0; i < 4; i++)
	  {
		for (int j = 0; j < 4; j++)
		 {
		   data[i][j] = 0;
		 }
	   }
	 } //end of new game ---
	
	//--- main method ---
	public static void main(String[] args)
	{
		Main main = new Main();
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.setSize(350,400);
		main.setVisible(true);
	} //end of main
}
