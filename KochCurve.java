import java.awt.*;
import java.awt.event.*;
import java.lang.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;


public class KochCurve extends JFrame implements MouseListener, MouseMotionListener
	{
	static int n = 1;
	static int dimx, dimy;
	static double gridx, gridy;
	
	boolean mpressed = false;

	int MouseInit_1 = 0;
	int Triangle = 0;
	int RandomizeC = 0;
	int RandomInC = 0;
	int red = 0, green = 0, blue = 0;
	int refresh = 1;
	int m = 3*(int)(Math.pow(4.0,(double)n - 1 ));
	int count;
	int n_ = 0;
	int last_x, last_y;
	int zoomOk = 0, zoomIn = 0;
	int rX = 0, rY = 0, rH = 0, rW = 0;
	int xAxis = 175, yAxis = 95;
	
	float dash1[] = {10.0f};
	
	double x0, y0, x3, y3, D;
	double a = 1.0;
	double b = 1.0;
			
	Rectangle rect = new Rectangle(0, 0, 0, 0);
	Rectangle sub_rect = new Rectangle(0, 0, 0, 0);
	Rectangle Clip = new Rectangle(175, 95, 440, 440);
	
	GeneralPath polygon;
	
	BufferedImage BuffIm = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
				
	BasicStroke thin = new BasicStroke(1.2f);
	BasicStroke medium = new BasicStroke(1.5f);
	BasicStroke thick = new BasicStroke(8.0f);
	
	BasicStroke dashed = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);	
			
	public KochCurve(String s)
		{
		super(s);
		}
		
	public static void main(String s[])
		{
		int i,j;
	
		KochCurve Win = new KochCurve("Koch Curve Application");				
		Win.getContentPane().setBackground(Color.GRAY);
		Win.setSize(800,600);
		Win.getContentPane().setLayout(null);
		
		dimx = Win.getWidth();
		dimy = Win.getHeight();
		Dimension Dim = new Dimension(3*(int)(dimx/5),3*(int)(dimy/5));
		gridx = Dim.getWidth();
		gridy = Dim.getHeight();
		
		Commands Comm = new Commands(Win);
		
		Win.setResizable(false);
		Win.setVisible(true);
		Win.setLocation(80,40);
		Win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		}
	
	public void setRectangle(int x, int y, int w, int h)
		{
		rect.x = x;
		rect.y = y;
		rect.height = h;
		rect.width = w;
		}
	
	public void InitP_InitD(double x_, double y_, double D_)
		{
		x0 = x_;
		y0 = y_;
		D = D_;
		}
		
	public void setScale(double a_, double b_)
		{
		a = a_;
		b = b_;
		}
	public void setCurrRect()
		{
		rX = rect.x;
		rY = rect.y;
		rW = rect.width;
		rH = rect.height;
		}

	public void reset()
		{
		n_ = 0;
		n = 1;
		mpressed = false;
		zoomOk = 0;
		zoomIn = 0;
		RandomInC = 0;
		setScale(1.0, 1.0);
		setRectangle(0, 0, 0, 0);
		setCurrRect();
		
		}
/*  In the end I found no use for this method, it works though ....
		
	public void ComputeGen(GeneralPath p, int k, int n, double rad, double D, double x, double y, double a, double b)
		{
		Point2D P2D;
		
		if (k<=n)
			{
			
			ComputeGen(p, k+1, n, rad, D/3.0, x, y, a, b);
						
			x = x + Math.cos(rad)*D/a;
			y = y - Math.sin(rad)*D/b;			

			ComputeGen(p, k+1, n, rad + Math.PI/3.0, D/3.0, x, y, a, b);
			
			x = x + Math.cos(rad + Math.PI/3.0)*D/a;
			y = y - Math.sin(rad + Math.PI/3.0)*D/b;	

			ComputeGen(p, k+1, n, rad - Math.PI/3.0, D/3.0, x, y, a, b);
			
			x = x + Math.cos(rad - Math.PI/3.0)*D/a;
			y = y - Math.sin(rad - Math.PI/3.0)*D/b;	
			
			ComputeGen(p, k+1, n, rad, D/3.0, x, y, a, b);
			
			x = x + Math.cos(rad)*D/a;
			y = y - Math.sin(rad)*D/b;
			
			P2D = p.getCurrentPoint();
						
			if (zoomIn == 1)
				{
				if((sub_rect.contains((int)x, (int)y) == true)||( ( ( sub_rect.contains( (int)P2D.getX(), (int)P2D.getY() ) == true)&&( sub_rect.contains( (int)x, (int)y) ) == false ) ) ) p.lineTo((int)x, (int)y);			
				else p.moveTo((int)x, (int)y);
				}
			else p.lineTo((int)x, (int)y);	
			}
		else
			{
			
			P2D = p.getCurrentPoint();
						
			if (zoomIn == 1)
				{
				if((sub_rect.contains((int)x, (int)y) == true)||( ( ( sub_rect.contains( (int)P2D.getX(), (int)P2D.getY() ) == true)&&( sub_rect.contains( (int)x, (int)y) ) == false ) ) ) p.lineTo((int)x, (int)y);			
				else p.moveTo((int)x, (int)y);
				}
			else p.lineTo((int)x, (int)y);	
			}
		}
*/

	public void ComputeTr(int k, int n, double D, double x0, double y0, double x3, double y3, double a, double b, double rad, Graphics2D g2, int red, int green, int blue)
		{
		double x1, y1, x2, y2, xT, yT;
		
		if (k<=n)
			{
			
			x1 = x0 + Math.cos(rad)*D/a;
			y1 = y0 - Math.sin(rad)*D/b;
			
			x2 = x3 - Math.cos(rad)*D/a;
			y2 = y3 + Math.sin(rad)*D/b;			
						
			xT = x1 + Math.cos(rad+Math.PI/3.0)*D/a;
			yT = y1 - Math.sin(rad+Math.PI/3.0)*D/b;		

			int x[] = {(int)x1, (int)xT, (int)x2};
			int y[] = {(int)y1, (int)yT, (int)y2};
			
			Polygon triangle = new Polygon(x, y, 3);
			
			if (Triangle == 1)
				{
				red+=120;
				green+=91;
				blue+=43;
				
				if (red>255) red = 0;
				if (green>255) green = 0;		
				if (blue>255) blue = 50;
				}
			
			Color FillIn = new Color(red, green, blue);				
			
			if (zoomIn == 1)
				{
				if( (triangle.intersects(sub_rect) == true) || ( ( sub_rect.contains( (int)x1 , (int)y1) )&&( sub_rect.contains( (int)xT , (int)yT) ) )&&( (sub_rect.contains( (int)x2 , (int)y2) ) ) )
					{
					if(Triangle == 1) g2.draw(triangle);
					else 
						{
						g2.setStroke(medium);
						g2.setPaint(FillIn);							
						g2.draw(triangle);
						g2.setStroke(thin);
						
						}
					g2.setPaint(FillIn);							
					g2.fill(triangle);
					g2.setPaint(Color.blue);
					}
					
				}
			else
				{
				if(Triangle == 1) g2.draw(triangle);
				else 
					{
					g2.setStroke(medium);
					g2.setPaint(FillIn);							
					g2.draw(triangle);
					g2.setStroke(thin);
					
					}
				g2.setPaint(FillIn);							
				g2.fill(triangle);
				g2.setPaint(Color.blue);
				}
				
			ComputeTr(k+1, n, D/3, x0, y0, x1, y1, a, b, rad, g2, red, green, blue);
			ComputeTr(k+1, n, D/3, x1, y1, xT, yT, a, b, rad + Math.PI/3.0, g2, red, green, blue);
			ComputeTr(k+1, n, D/3, xT, yT, x2, y2, a, b, rad - Math.PI/3.0, g2, red, green, blue);
			ComputeTr(k+1, n, D/3, x2, y2, x3, y3, a, b, rad, g2, red, green, blue);
			
			if(k == n) count++;
//			System.out.println("Loading Percentage : "+(double)(count)/(double)(m)*100.0);
//			System.out.println(""+(double)(count)/(double)(m)*100.0);
			
			if (zoomIn == 1)
				{				
				if ( ( sub_rect.contains((int)x0, (int)y0) == false ) && ( sub_rect.contains((int)x1, (int)y1) == false ) );
				else if(Triangle == 1) g2.drawLine((int)x0, (int)y0, (int)x1, (int)y1);
				
				if ( ( sub_rect.contains((int)x2, (int)y2) == false ) && ( sub_rect.contains((int)x3, (int)y3) == false ) );
				else if(Triangle == 1) g2.drawLine((int)x2, (int)y2, (int)x3, (int)y3);
					
				}
			else 
				{
				if (Triangle == 1) 
					{
					g2.drawLine((int)x0, (int)y0, (int)x1, (int)y1);
					g2.drawLine((int)x2, (int)y2, (int)x3, (int)y3);
					}
				}	
			
			}
			
		
		}

			
	public void paint(Graphics g)
		{
		int i, j, m_, k = 0;		
		
		Point2D P2D;
		
		j = 0;
		
		Graphics2D g2 = (Graphics2D)g;

        Graphics2D gBuf = BuffIm.createGraphics();

		x0 = (double)(KochCurve.dimx)/5.0+60.0;
		y0 = 415.0;
		
		g2.setClip(174,94,441,441);
		g2.setPaint(Color.black);
		g2.setStroke(thick);
		g2.draw3DRect(174, 94, 441, 441, true);	
		g2.setPaint(Color.white);
		g2.fill3DRect(175, 95, 440, 440, true);

		if (refresh == 1)
			{				
			gBuf.setClip(174,94,441,441);
			gBuf.setPaint(Color.black);
			gBuf.setStroke(thick);
			gBuf.draw3DRect(174, 94, 441, 441, true);	
			gBuf.setPaint(Color.white);
			gBuf.fill3DRect(175, 95, 440, 440, true);
			}		

//		gBuf.drawString("1");

		x0 = x0/a;
		y0 = y0/b;
										
		if (zoomIn == 1)	gBuf.translate(- (double)rX*441.0/(rW) + xAxis*zoomIn, - (double)rY*441.0/(rH) + yAxis*zoomIn);		
					
		x3 = x0 + Math.cos(Math.PI/3.0)*D/a;
		y3 = y0 - Math.sin(Math.PI/3.0)*D/b;			
		
		if (MouseInit_1 == 0)
			{
			MouseInit_1 = 1;
			addMouseMotionListener(this);
        	addMouseListener(this);
			}
				
		if (n_==1)
			{
			if (refresh == 1)
				{
				
				gBuf.setStroke(thin);
				gBuf.setPaint(Color.blue);
				
				if (zoomIn == 1)
					{
					sub_rect.x =(int)(rX*441.0/(rW));
					sub_rect.y =(int)(rY*441.0/(rH));
					}
				else
					{
					sub_rect.x =174;
					sub_rect.y =94;
					}		
				sub_rect.width =442;
				sub_rect.height =442;
						
				gBuf.setPaint(Color.red);
				
//				gBuf.draw(sub_rect);
				
				gBuf.setPaint(Color.blue);
					
				if (RandomInC == 1)
					{
						
					Color FillIn = new Color(red, green, blue);
												
					int x[] = {(int)x0, (int)x3, (int)(x0+D/a)};
					int y[] = {(int)y0, (int)y3, (int)y0};
					
					Polygon triangle = new Polygon(x,y,3);
					
					if (zoomIn == 1)
						{
						if( (triangle.intersects(sub_rect) == true) || ( ( sub_rect.contains( (int)x0 , (int)y0) )&&( sub_rect.contains( (int)x3 , (int)y3) ) )&&( (sub_rect.contains( (int)(x0+D/a) , (int)y0) ) ) )
							{
							if (Triangle == 0) 
								{
								gBuf.setStroke(medium);
								gBuf.setPaint(Color.blue);
								}
							gBuf.draw(triangle);
							gBuf.setStroke(thin);
							gBuf.setPaint(FillIn);
							gBuf.fill(triangle);							
							}
						}	
					else 
						{
						if (Triangle == 0) 
							{
							gBuf.setStroke(medium);
							gBuf.setPaint(Color.blue);
							}
						gBuf.draw(triangle);
						gBuf.setStroke(thin);							
						gBuf.setPaint(FillIn);
						gBuf.fill(triangle);							
						}
										
					}
				
				if ((RandomizeC == 1)&&(RandomInC == 0))
					{
					red = (int)(255.0*Math.random());
					green = (int)(255.0*Math.random());
					blue = (int)(255.0*Math.random());
										
					Color FillIn = new Color(red, green, blue);
												
					int x[] = {(int)x0, (int)x3, (int)(x0+D/a)};
					int y[] = {(int)y0, (int)y3, (int)y0};
					
					Polygon triangle = new Polygon(x,y,3);
					
					if (zoomIn == 1)
						{
						if( (triangle.intersects(sub_rect) == true) || ( ( sub_rect.contains( (int)x0 , (int)y0) )&&( sub_rect.contains( (int)x3 , (int)y3) ) )&&( (sub_rect.contains( (int)(x0+D/a) , (int)y0) ) ) )
							{
							if (Triangle == 0) 
								{
								gBuf.setStroke(medium);
								gBuf.setPaint(Color.blue);
								}
							gBuf.draw(triangle);
							gBuf.setStroke(thin);
							gBuf.setPaint(FillIn);
							gBuf.fill(triangle);							
							}
						}	
					else 
						{
						if (Triangle == 0) 
							{
							gBuf.setStroke(medium);
							gBuf.setPaint(Color.blue);
							}
						gBuf.draw(triangle);
						gBuf.setStroke(thin);
						gBuf.setPaint(FillIn);
						gBuf.fill(triangle);							
						}
																				
					RandomInC = 1;					
				
					}
				else if (RandomizeC == 0)
					{
					
					int x[] = {(int)x0, (int)x3, (int)(x0+D/a)};
					int y[] = {(int)y0, (int)y3, (int)y0};
										
					Polygon triangle = new Polygon(x,y,3);
					
					Color FillIn = new Color(red, green, blue);
					
					if (zoomIn == 1)
						{
						if( (triangle.intersects(sub_rect) == true) || ( ( sub_rect.contains( (int)x0 , (int)y0) )&&( sub_rect.contains( (int)x3 , (int)y3) ) )&&( (sub_rect.contains( (int)(x0+D/a) , (int)y0) ) ) )
							{
							if (Triangle == 0) 
								{
								gBuf.setStroke(medium);
								gBuf.setPaint(Color.blue);
								}
							gBuf.draw(triangle);
							gBuf.setStroke(thin);
							gBuf.setPaint(FillIn);
							gBuf.fill(triangle);
							}
						}	
					else 
						{
						if (Triangle == 0) 
							{
							gBuf.setStroke(medium);
							gBuf.setPaint(Color.blue);
							}
						gBuf.draw(triangle);
						gBuf.setStroke(thin);
						gBuf.setPaint(FillIn);
						gBuf.fill(triangle);							
						}
					gBuf.setPaint(Color.blue);
					}
				ComputeTr( k+1, n, D/3.0 , x0, y0, x3, y3, a, b, Math.PI/3.0, gBuf, red, green, blue );
				ComputeTr( k+1, n, D/3.0 , x3, y3, x0 + D/a, y0, a, b, - Math.PI/3.0, gBuf, red, green, blue );
				ComputeTr( k+1, n, D/3.0 , x0 + D/a, y0, x0, y0, a, b, - Math.PI, gBuf, red, green, blue );				
							
			}
					
			g2.drawImage(BuffIm, null, 0, 0);
			if (mpressed == true) 
				{
				if(zoomIn == 1) g2.translate(- (double)rX*441.0/(rW) + xAxis*zoomIn, - (double)rY*441.0/(rH) + yAxis*zoomIn);		
										
				g2.setPaint(Color.black);
				g2.setStroke(dashed);
				g2.draw(rect);
				}		

		}
		
		count = 0;	
		zoomOk = 0;		
		}
								
		// Handles the event of the user pressing down the mouse button.
		public void mousePressed(MouseEvent e)
			{
			if (((e.getX()>=175)&&(e.getX()<=615))&&(e.getY()>=95)&&(e.getY()<=535))
				{
				if (n_ == 1) 
					{
					mpressed = true;
										
					if (zoomIn == 1)
						{
						last_x = (int)(e.getX() + rX*441.0/(rW) - xAxis*zoomIn);
						last_y = (int)(e.getY() + rY*441.0/(rH) - yAxis*zoomIn);
						}
					else 
						{
						last_x = (int)(e.getX());
						last_y = (int)(e.getY());	
						}											
					rect.x = last_x;
					rect.y = last_y;

					rect.height = 0;
					rect.width = 0;	
								
					zoomOk = 0;
					}
				}
			}
	
		// Handles the event of a user dragging the mouse while holding
		// down the mouse button.
		public void mouseDragged(MouseEvent e) {if(mpressed == true) updateLocation(e);}
	
		// Handles the event of a user releasing the mouse button.
		public void mouseReleased(MouseEvent e)
			{
			if (((e.getX()>=174)&&(e.getX()<=615))&&(e.getY()>=94)&&(e.getY()<=535)) 
				{
				refresh = 1;
				if ((rect.width>0)&&(rect.height>0)) zoomOk = 1;
				mpressed = false;
				}
			else
				{
				refresh = 1;
				mpressed = false;
				zoomOk = 0;
				setRectangle(0, 0, 0, 0);							
				}
			}
		   
		 // This method is required by MouseListener.
		 public void mouseMoved(MouseEvent e) {}
	
		 // These methods are required by MouseMotionListener.
		 public void mouseClicked(MouseEvent e) 
		 	{
			setRectangle(0, 0, 0, 0);
			refresh = 0;
			repaint();		 
		 	}
		 public void mouseExited(MouseEvent e) {}
		 public void mouseEntered(MouseEvent e) {}
		                     
		 public void updateLocation(MouseEvent e)
		 	{
			refresh = 0;

			if (zoomIn == 1)
				{	
				if(((int)(e.getY() + rY*441.0/(rH) - yAxis*zoomIn) - last_y)>0) rect.height = (int)(e.getY() + rY*441.0/(rH) - yAxis*zoomIn) - last_y;
				else if(((int)(e.getY() + rY*441.0/(rH) - yAxis*zoomIn) - last_y)<0)
					{
					rect.height = last_y - (int)(e.getY() + rY*441.0/(rH) - yAxis*zoomIn);
					rect.y = last_y - rect.height;
					}
				if(((int)(e.getX() + rX*441.0/(rW) - xAxis*zoomIn) - last_x)>0) rect.width = (int)(e.getX() + rX*441.0/(rW) - xAxis*zoomIn) - last_x;
				else if(((int)(e.getX() + rX*441.0/(rW) - xAxis*zoomIn) - last_x)<0)
					{
					rect.width = last_x - (int)(e.getX() + rX*441.0/(rW) - xAxis*zoomIn);
					rect.x = last_x - rect.width;				
					}				
				}
			else 
				{
				if(e.getY() - last_y>0) rect.height = e.getY() - last_y;
				else if(e.getY() - last_y<0)
					{
					rect.height = last_y - e.getY();
					rect.y = last_y - rect.height;
					}
				if(e.getX() - last_x>0) rect.width = e.getX() - last_x;
				else if(e.getX() - last_x<0)
					{
					rect.width = last_x - e.getX();
					rect.x = last_x - rect.width;				
					}				
				}						
			repaint();			
			}
		
	
	}
