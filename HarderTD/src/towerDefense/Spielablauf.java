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
	final static int spielfeldHöhe  = 21;
	final static int quadratGröße = 30;
	final static int aktionsleisteBreite = 300;
	final static int[][] spielfeldarray = new int[spielfeldBreite][spielfeldHöhe]; 
	final static Rectangle spielfeld = new Rectangle(spielfeldBreite*quadratGröße+1, spielfeldHöhe*quadratGröße+1);
	
	static int leben;
	static int goldCounter;
	static int waveCounter;
	static int goldFürGegner;
	static int LebenGegner;
	static String waveArt;					//speed, tanky, ...
	static ArrayList<Turm> türme;
	static ArrayList<Wall> walls;
	static ArrayList<Enemy> enemys;
	static ArrayList<Schuss> schüsse;
	
	Frame f = new Frame();
	
	public void starten (){
		
		//Das startfeld wird blockiert
		spielfeldarray[Spielablauf.spielfeldBreite/2][0] = 1;


		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(spielfeldBreite*quadratGröße+1+aktionsleisteBreite, spielfeldHöhe*quadratGröße+1);
		f.setUndecorated(true);
		f.setVisible(true);
		f.setResizable(false);
		f.setLocationRelativeTo(null);
		
		f.makeStrat();
	}
	public void resetGame(){
		
		new BilderLaden();
		
		leben = 3;
		goldCounter = 250;
		waveCounter = 0;
		goldFürGegner = 20;
		türme = new ArrayList<Turm>();
		walls = new ArrayList<Wall>();
		enemys = new ArrayList<Enemy>();
		schüsse = new ArrayList<Schuss>();
		
		spielablauf();
	}

	private void spielablauf(){
		
//		//Das startfeld wird blockiert
//		spielfeldarray[Spielablauf.spielfeldBreite/2][0] = 1;
//		
////		spielfeldarrayAusgabe();
//		
//		
//		Frame f = new Frame();
//		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		f.setSize(spielfeldBreite*quadratGröße+1+aktionsleisteBreite, spielfeldHöhe*quadratGröße+1);
//		f.setUndecorated(true);
//		f.setVisible(true);
//		f.setResizable(false);
//		f.setLocationRelativeTo(null);
////		DisplayMode displayMode = new DisplayMode(800, 600, 16, 75);
////		GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
////		GraphicsDevice device = environment.getDefaultScreenDevice();
////		
////		device.setFullScreenWindow(f);
////		device.setDisplayMode(displayMode);
//		
//	
//		
//		
//		f.makeStrat();
		
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
			for(int i = 0; i < türme.size(); i++){
				Turm t = türme.get(i);
				t.update(timeSinceLastFrame);
			}
			//Update Schüsse
			for(int i = 0; i < schüsse.size(); i++){
				Schuss s = schüsse.get(i);
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
			Frame.knSpawn.setAction(false);
			resetGame();
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