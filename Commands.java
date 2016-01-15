import java.awt.*;
import java.awt.event.*;
import java.lang.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;


public class Commands implements ActionListener, AdjustmentListener, ItemListener, MouseListener, MouseMotionListener
	{
	Button b, bx, by, RedB, GreenB, BlueB, ScaleX, ScaleY;
	Checkbox Box, Ran;
	Scrollbar Red, Green, Blue;
	TextArea T, RedT, GreenT, BlueT;
	KochCurve Win_;
	
	int MouseInit_2 = 0;
	int X = 0, Y = 0;
		
	public Commands(KochCurve Win)
			{
			Win_ = Win;
			
			Win_.InitP_InitD((double)(KochCurve.dimx)/5.0+60.0, 415.0, 3.0*((double)(KochCurve.dimx)/5.0)-120.0);			
			
			if (MouseInit_2 == 0)
				{
				MouseInit_2 = 1;
				Win_.addMouseMotionListener(this);
				Win_.addMouseListener(this);
				}
			b=new Button("Draw");
			b.setActionCommand("Draw");
			b.setBounds(670,130,70,30);
			b.setBackground(Color.ORANGE);
			b.setForeground(Color.BLACK);
			b.addActionListener(this);
			Win.getContentPane().add(b);
			
			b=new Button("Clear");
			b.setActionCommand("Clear");
			b.setBounds(670,170,70,30);
			b.setBackground(Color.GREEN);
			b.setForeground(Color.BLACK);
			b.addActionListener(this);
			Win.getContentPane().add(b);
			
			b=new Button("+");
			b.setActionCommand("+");
			b.setBounds(723,209,45,20);
			b.setBackground(Color.LIGHT_GRAY);
			b.setForeground(Color.BLACK);
			b.setFont(new Font("1",Font.PLAIN,16));
			b.addActionListener(this);
			Win.getContentPane().add(b);
			
			b=new Button("-");
			b.setActionCommand("-");
			b.setBounds(723,235,45,20);
			b.setBackground(Color.LIGHT_GRAY);
			b.setForeground(Color.BLACK);
			b.setFont(new Font("1",Font.PLAIN,16));
			b.addActionListener(this);
			Win.getContentPane().add(b);
			
			Integer CooX = new Integer(X);
			Integer CooY = new Integer(Y);
						
			bx=new Button(" X : "+CooX.toString());
			bx.setBounds( 358, 535, 75, 20);
			bx.setBackground(Color.LIGHT_GRAY);
			bx.setForeground(Color.BLACK);
			bx.setFont(new Font("1",Font.PLAIN,14));
			bx.addActionListener(this);
			Win.getContentPane().add(bx);
			
			by=new Button(" Y : "+CooY.toString());
			by.setBounds( 67, 270, 75, 20);
			by.setBackground(Color.LIGHT_GRAY);
			by.setForeground(Color.BLACK);
			by.setFont(new Font("1",Font.PLAIN,14));
			by.addActionListener(this);
			Win.getContentPane().add(by);
			
			Red = new Scrollbar(Scrollbar.VERTICAL, 0, 1, 0, 256);
			Red.setBounds( 655, 430, 20, 60);
			Red.setUnitIncrement(1);
			Red.addAdjustmentListener(this);
			Win.getContentPane().add(Red);
			
			Green = new Scrollbar(Scrollbar.VERTICAL, 0, 1, 0, 256);
			Green.setBounds( 690, 430, 20, 60);
			Green.setUnitIncrement(1);
			Green.addAdjustmentListener(this);
			Win.getContentPane().add(Green);
			
			Blue = new Scrollbar(Scrollbar.VERTICAL, 0, 1, 0, 256);
			Blue.setBounds( 725, 430, 20, 60);
			Blue.setUnitIncrement(1);
			Blue.addAdjustmentListener(this);
			Win.getContentPane().add(Blue);
			
			RedB = new Button("Red");
			RedB.setBounds(645,500,35,20);
			RedB.setBackground(Color.RED);
			RedB.setForeground(Color.BLACK);
			Win.getContentPane().add(RedB);
			
			GreenB = new Button("Green");
			GreenB.setBounds(685,500,35,20);
			GreenB.setBackground(Color.GREEN);
			GreenB.setForeground(Color.BLACK);
			Win.getContentPane().add(GreenB);
			
			BlueB = new Button("Blue");
			BlueB.setBounds(725,500,35,20);
			BlueB.setBackground(Color.BLUE);
			BlueB.setForeground(Color.BLACK);
			Win.getContentPane().add(BlueB);
			
			RedT = new TextArea("0",1,1,TextArea.SCROLLBARS_NONE);
			RedT.setBounds(650,400,30,20);
			RedT.setEditable(false);
			RedT.setBackground(Color.LIGHT_GRAY);
			Win.getContentPane().add(RedT);			
			
			GreenT = new TextArea("0",1,1,TextArea.SCROLLBARS_NONE);
			GreenT.setBounds(685,400,30,20);
			GreenT.setEditable(false);
			GreenT.setBackground(Color.LIGHT_GRAY);
			Win.getContentPane().add(GreenT);			
			
			BlueT = new TextArea("0",1,1,TextArea.SCROLLBARS_NONE);
			BlueT.setBounds(720,400,30,20);
			BlueT.setEditable(false);
			BlueT.setBackground(Color.LIGHT_GRAY);
			Win.getContentPane().add(BlueT);			
						
			ScaleX = new Button("Scale of X Axis = 1 : 1.0");
			ScaleX.setBounds(171,20,441,20);
			ScaleX.setBackground(Color.RED);
			ScaleX.setForeground(Color.BLACK);
			Win.getContentPane().add(ScaleX);
			
			ScaleY = new Button("Scale of Y Axis = 1 : 1.0");
			ScaleY.setBounds(171,50,441,20);
			ScaleY.setBackground(Color.YELLOW);
			ScaleY.setForeground(Color.BLACK);
			Win.getContentPane().add(ScaleY);
			
			Box = new Checkbox("Triangles", null, false);
			Box.setBounds(655, 280, 75, 25);
			Box.setBackground(Color.LIGHT_GRAY);
			Box.addItemListener(this);
			Win.getContentPane().add(Box);
			
			Ran = new Checkbox("Randomize Colors", null, false);
			Ran.setBounds(655, 310, 125, 25);
			Ran.setBackground(Color.LIGHT_GRAY);
			Ran.addItemListener(this);
			Win.getContentPane().add(Ran);
			
			T = new TextArea("1",1,1,TextArea.SCROLLBARS_NONE);
			T.setBounds(672,220,45,25);
			T.setBackground(Color.white);
			Win.getContentPane().add(T);			
			
			}

				
	public void actionPerformed(ActionEvent ev)
		{
		String label;
		label = ev.getActionCommand();
		
		if (label=="Draw")
			{
			Win_.n_ = 1;
			Win_.refresh = 1;
			Win_.m = 3*(int)(Math.pow(4.0,(double)Win_.n - 1));
			Win_.count = 0;
			if (Win_.RandomizeC == 0)
				{
				Win_.red = Red.getValue();
				Win_.green = Green.getValue();
				Win_.blue = Blue.getValue();
				}
			if (Win_.zoomOk == 1) 
				{
				Win_.zoomIn = 1;
				Win_.setCurrRect();
				Win_.a = Win_.a*(double)(Win_.rect.width) / 441.0;
				Win_.b = Win_.b*(double)(Win_.rect.height) / 441.0;									
				}
			T.setEditable(false);
			
			ScaleX.setLabel("Scale of X Axis = 1 : "+1.0/Win_.a);
			ScaleY.setLabel("Scale of Y Axis = 1 : "+1.0/Win_.b);
			T.setBackground(Color.LIGHT_GRAY);
			Win_.mpressed = false;
			Win_.repaint();
			T.setEditable(true);
			T.setBackground(Color.WHITE);
			
			
			}
		if (label=="Clear")
			{
			String Text = T.getText();
			Integer val = new Integer(1);
			Text = val.toString();
			ScaleX.setLabel("Scale of X Axis = 1 : 1.0");
			ScaleY.setLabel("Scale of Y Axis = 1 : 1.0");
			T.setText(Text);
			Win_.reset();																		
			Win_.repaint();
			}
		
			
		if (label=="+")
			{
			int i,k = 0;
			int n = T.getText().length();
			String Text = T.getText();
			for(i=0;i<=n-1;i++) if(((int)(Text.charAt(i))<(int)('0'))||((int)(Text.charAt(i))>(int)('9'))) k = 1;
			if (k==0)
				{
				Integer value = new Integer(Text); 
				k = value.intValue() + 1;
				Win_.n = k;
				}
			Integer val = new Integer(k);
			Text = val.toString();
			T.setText(Text);
			
			}
		if (label=="-")
			{
			int i,k = 0;
			int n = T.getText().length();
			String Text = T.getText();
			
			for(i=0;i<=n-1;i++) if(((int)(Text.charAt(i))<(int)('0'))||((int)(Text.charAt(i))>(int)('9'))) k = 1;
			if (k==0)
				{
				Integer value = new Integer(Text);
				k = value.intValue() - 1;
				if(k<=0) k = 1;
				Win_.n = k;
				}
			
			Integer val = new Integer(k);
			Text = val.toString();
			T.setText(Text);
			
			}
		}
	
	
	public void adjustmentValueChanged(AdjustmentEvent e) 
		{
		String Source, labelItem;
		Source = ""+e.getSource();
		
		labelItem = Source.substring(30, 33);
		if (Win_.RandomizeC == 0)
			{				
			if (labelItem.compareTo("655") == 0)
				{
				Integer R = new Integer(Red.getValue());
				Win_.red = Red.getValue();
				RedT.setText(R.toString());
				}
			if (labelItem.compareTo("690") == 0)
				{
				Integer G = new Integer(Green.getValue());
				Win_.green = Green.getValue();
				GreenT.setText(G.toString());
				}
			if (labelItem.compareTo("725") == 0)
				{
				Integer B = new Integer(Blue.getValue());
				Win_.blue = Blue.getValue();
				BlueT.setText(B.toString());
				}
			}
		Win_.repaint();
		}
		
		
	public void itemStateChanged(ItemEvent e)
		{
		
		String labelItem;
		labelItem = ""+e.getItem();
		
		if (labelItem.compareTo("Triangles") == 0)
			{		
			if (e.getStateChange() == ItemEvent.SELECTED ) Win_.Triangle = 1;
			else if ( e.getStateChange() == ItemEvent.DESELECTED ) Win_.Triangle = 0;		
			}
		if (labelItem.compareTo("Randomize Colors") == 0)
			{		
			if (e.getStateChange() == ItemEvent.SELECTED ) 
				{
				Win_.RandomizeC = 1;
				Red.setEnabled(false);
				Green.setEnabled(false);
				Blue.setEnabled(false);
				
				}
			else if ( e.getStateChange() == ItemEvent.DESELECTED ) 
				{
				Win_.RandomizeC = 0;		
				Win_.RandomInC = 0;
				Red.setEnabled(true);
				Green.setEnabled(true);
				Blue.setEnabled(true);
				
				}
			}
			
		} 
 		
		// Handles the event of the user pressing down the mouse button.
	public void mousePressed(MouseEvent e){}
	
		// Handles the event of a user dragging the mouse while holding
		// down the mouse button.
	public void mouseDragged(MouseEvent e){}
	
		// Handles the event of a user releasing the mouse button.
	public void mouseReleased(MouseEvent e){}
		   
		 // This method is required by MouseListener.
	 public void mouseMoved(MouseEvent e)
		 	{
		 	if (((e.getX()>=175)&&(e.getX()<=614))&&(e.getY()>=95)&&(e.getY()<=534)) 
		 		{
		 		X = e.getX() - 175;
		 		Y = 534 - e.getY();
				
//				System.out.println("e.getX()"+e.getX()+"e.getY()"+e.getY());
				
//		 		X = (int)((double)(e.getX() - 175)/Win_.a);
//		 		Y = (int)((double)(534 - e.getY())/Win_.b);
				
		 		Integer CooX = new Integer(X);
		 		Integer CooY = new Integer(Y);
				 
				bx.setLabel(" X : "+CooX.toString());
				by.setLabel(" Y : "+CooY.toString());
				
		 		}			
		 	}
	
		 // These methods are required by MouseMotionListener.
	 public void mouseClicked(MouseEvent e){}
	 public void mouseExited(MouseEvent e){}
	 public void mouseEntered(MouseEvent e){}
		                     
		 // Updates the coordinates representing the location of the current rectangle.
	 public void updateLocation(MouseEvent e){}
		
		
		
	}
