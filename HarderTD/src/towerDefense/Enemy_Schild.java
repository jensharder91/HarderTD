package towerDefense;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Enemy_Schild extends Enemy{
	
	private boolean schild = false;
	private long timeSinceSchild = 0;
	private long schildDauer = 1800;
	private BufferedImage bild1_schild;

	public Enemy_Schild(int posy, int width, int height, int leben, Color color) {
		super(posy, width, height, leben, color);
		this.bild1_schild = BilderLaden.getGegner1schild();
	}

	public static void spawn(){
		spawnAll();
		
		for(int i=0; i<5; i++){
			enemysCounter++;
			Spielablauf.enemys.add(new Enemy_Schild(i*3+2, Spielablauf.quadratGröße, Spielablauf.quadratGröße, lebenSpawn, Color.PINK));
		}
		Spielablauf.LebenGegner = lebenSpawn;
	}
	
	public void lebenVerlieren(int lebendsAbzug){
		
		if(getLeben() == Spielablauf.LebenGegner){
			schild = true;
			timeSinceSchild = System.currentTimeMillis();
		}else if(schild && System.currentTimeMillis() - timeSinceSchild >= schildDauer){
			schild = false;
		}
		if(schild){
			setLeben((int)(getLeben()-(lebendsAbzug*0.2)));
		}else{
			setLeben(getLeben()-lebendsAbzug);
		}
	}
	
	public BufferedImage getBild(){
		if(schild){
			return bild1_schild;
		}
		return bild1;
	}
}
