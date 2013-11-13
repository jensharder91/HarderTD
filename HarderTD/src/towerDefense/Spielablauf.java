package towerDefense;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class Spielablauf {
	
	//Array spiegelt Spielfeld wieder
	static boolean spielEnde = false;
	static BufferedImage failbild;
	final static int spielfeldBreite = 21;
	final static int spielfeldH�he  = 21;
	final static int quadratGr��e = 30;
	final static int aktionsleisteBreite = 300;
	final static int[][] spielfeldarray = new int[spielfeldBreite][spielfeldH�he]; 
	final static Rectangle spielfeld = new Rectangle(spielfeldBreite*quadratGr��e+1, spielfeldH�he*quadratGr��e+1);
	
	static int leben;
	static int goldCounter;
	static int waveCounter;
	static int goldF�rGegner;
	static int LebenGegner;
	static String waveArt;					//speed, tanky, ...
	static ArrayList<Turm> t�rme;
	static ArrayList<Wall> walls;
	static ArrayList<Enemy> enemys;
	static ArrayList<Schuss> sch�sse;
	
	
	public void starten(){
		
		new BilderLaden();
		
		leben = 3;
		goldCounter = 100;
		waveCounter = 0;
		goldF�rGegner = 20;
		t�rme = new ArrayList<Turm>();
		walls = new ArrayList<Wall>();
		enemys = new ArrayList<Enemy>();
		sch�sse = new ArrayList<Schuss>();
		
		spielablauf();
	}

	private void spielablauf(){
		
		//Das startfeld wird blockiert
		spielfeldarray[Spielablauf.spielfeldBreite/2][0] = 1;
		
//		spielfeldarrayAusgabe();
		
		
		Frame f = new Frame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(spielfeldBreite*quadratGr��e+1+aktionsleisteBreite, spielfeldH�he*quadratGr��e+1);
		f.setUndecorated(true);
		f.setVisible(true);
		f.setResizable(false);
		f.setLocationRelativeTo(null);
//		DisplayMode displayMode = new DisplayMode(800, 600, 16, 75);
//		GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
//		GraphicsDevice device = environment.getDefaultScreenDevice();
//		
//		device.setFullScreenWindow(f);
//		device.setDisplayMode(displayMode);
		
	
		
		
		f.makeStrat();
		
		long lastFrame = System.currentTimeMillis();
		while(true){
			if(KeyHandler.isKeyDown(KeyEvent.VK_ESCAPE))System.exit(0);
						
			long thisFrame = System.currentTimeMillis();
			float timeSinceLastFrame = ((float)(thisFrame-lastFrame))/1000f;
			lastFrame=thisFrame;

			//Update Enemy
			for(int i = 0; i<enemys.size(); i++){
				Enemy e = enemys.get(i);
				e.update(timeSinceLastFrame);
			}
			//Update Turm
			for(int i = 0; i < t�rme.size(); i++){
				Turm t = t�rme.get(i);
				t.update(timeSinceLastFrame);
			}
			//Update Sch�sse
			for(int i = 0; i < sch�sse.size(); i++){
				Schuss s = sch�sse.get(i);
				s.update(timeSinceLastFrame);
			}
			
			
			f.repaintScreen();
	
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if(leben <= 0){
				System.out.println("FAAIIILL");
				spielEnde = true;
				break;
			}
		}
		
		f.repaintScreen();
		
		int result = JOptionPane.showConfirmDialog(null, "Spiel neustarten?", "Neustart", JOptionPane.YES_NO_OPTION);
		if (result == JOptionPane.YES_OPTION) {
			spielEnde = false;
			//starten();
		} else if (result == JOptionPane.NO_OPTION) {
		}
		
	}
	
	
//	public static void spielfeldarrayAusgabe(){
//		
//	
//		for(int j = 0; j<spielfeldarray[0].length; j++){
//			for(int i = 0; i<spielfeldarray.length; i++){
//				System.out.print(spielfeldarray[i][j] + "   ");	
//			}	
//			System.out.println();
//		}	
//		System.out.println("------------------------------------------------------------------------");
//		System.out.println("------------------------------------------------------------------------");
//	}

}