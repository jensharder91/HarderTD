package towerDefense;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;

public class Frame extends JFrame {

	private BufferStrategy strat;
	static boolean turmmenu = false;

	//Knöpfe
	static Knopf knBeenden = new Knopf(Spielablauf.spielfeldBreite*Spielablauf.quadratGröße+250, 0, 50, 50, Color.BLACK);
	static Knopf knTurmmenu = new Knopf(Spielablauf.spielfeldBreite*Spielablauf.quadratGröße+250, Spielablauf.spielfeldHöhe*Spielablauf.quadratGröße-50, 50, 50, Color.GREEN);
	static Knopf knTurmbau = new Knopf((Spielablauf.spielfeldBreite+1)*Spielablauf.quadratGröße, Spielablauf.quadratGröße*3, Spielablauf.quadratGröße*3, Spielablauf.quadratGröße, Color.GRAY, Color.GREEN);
	static Knopf knWallbau = new Knopf((Spielablauf.spielfeldBreite+1)*Spielablauf.quadratGröße + Spielablauf.quadratGröße*4, Spielablauf.quadratGröße*3, Spielablauf.quadratGröße*3, Spielablauf.quadratGröße, Color.GRAY, Color.GREEN);
	static Knopf knSpawn = new Knopf((Spielablauf.spielfeldBreite+1)*Spielablauf.quadratGröße, Spielablauf.quadratGröße*5, Spielablauf.quadratGröße*3, Spielablauf.quadratGröße, Color.BLUE, Color.RED);
	static Knopf knTurmLöschen = new Knopf((Spielablauf.spielfeldBreite+1)*Spielablauf.quadratGröße + Spielablauf.quadratGröße*4, Spielablauf.quadratGröße*5, Spielablauf.quadratGröße*3, Spielablauf.quadratGröße, Color.BLUE, Color.RED);
	
	//UpgradeKnöpfe
	static Knopf knUGDmg =  new Knopf((Spielablauf.spielfeldBreite+1)*Spielablauf.quadratGröße+ 150, Spielablauf.quadratGröße*6 - 15, 50, 15, Color.green);
	static Knopf knUGSpeed =  new Knopf((Spielablauf.spielfeldBreite+1)*Spielablauf.quadratGröße+ 150, Spielablauf.quadratGröße*7 - 15, 50, 15, Color.green);
	static Knopf knUGRange =  new Knopf((Spielablauf.spielfeldBreite+1)*Spielablauf.quadratGröße+ 150, Spielablauf.quadratGröße*8 - 15, 50, 15, Color.green);
	static Knopf knUGCrit =  new Knopf((Spielablauf.spielfeldBreite+1)*Spielablauf.quadratGröße+ 150, Spielablauf.quadratGröße*9 - 15, 50, 15, Color.green);
	
	
	public Frame() {
		//super("MoveTest");
		addKeyListener(new KeyHandler());
		addMouseListener(new MouseHandler());
		addMouseMotionListener(new MouseHandler());
	}

	public void makeStrat() {
		createBufferStrategy(2);
		strat = getBufferStrategy();
	}

	public void repaintScreen() {
		Graphics g = strat.getDrawGraphics();
		draw(g);
		g.dispose();
		strat.show();
	}

	private void draw(Graphics g) {
		
		//HintergrungSpielfeld
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, Spielablauf.spielfeld.width, Spielablauf.spielfeld.height);
		//HintergrundAktionsleiste
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(Spielablauf.spielfeld.width, 0, Spielablauf.aktionsleisteBreite, Spielablauf.spielfeld.height);
		
		//Grundgitter
		g.setColor(Color.RED);
		for(int i = 0; i<Spielablauf.spielfeldarray.length; i++){
			for(int j = 0; j<Spielablauf.spielfeldarray[i].length; j++){
				g.drawRect(i*Spielablauf.quadratGröße, j*Spielablauf.quadratGröße, Spielablauf.quadratGröße, Spielablauf.quadratGröße);
			}
		}
				
		//Türme
		for(int i = 0; i<Spielablauf.türme.size(); i++){
			Turm t = Spielablauf.türme.get(i);
			g.setColor(t.getColor());
			//g.fillRect(t.getX(), t.getY(), Spielablauf.quadratGröße-1, Spielablauf.quadratGröße-1);
			g.drawImage(t.getBild(), t.getX(), t.getY(), null);
			if(t.getTurmAusgewählt() == true){
				turmmenu = true;
			}
		}
		
		//Walls
		for(int i = 0; i<Spielablauf.walls.size(); i++){
			Wall w = Spielablauf.walls.get(i);
			g.setColor(w.getColor());
			g.fillRect(w.getX(), w.getY(), Spielablauf.quadratGröße-1, Spielablauf.quadratGröße-1);
		}
		
		//Enemys
		for(int i = 0; i<Spielablauf.enemys.size(); i++){
			Enemy e = Spielablauf.enemys.get(i);
			g.setColor(e.getColor());
			//g.fillOval(e.getBounding().x, e.getBounding().y, e.getBounding().width, e.getBounding().height);
			g.drawImage(e.getBild(), e.getBounding().x, e.getBounding().y, null);
			g.setColor(Color.BLACK);
			g.drawString(new Integer(e.getLeben()).toString(), e.getBounding().x, e.getBounding().y);
		}
		
		//Schüsse
		for(int i = 0; i<Spielablauf.schüsse.size(); i++){
			Schuss s = Spielablauf.schüsse.get(i);
			g.setColor(s.getColor());
			//g.fillOval(s.getBounding().x, s.getBounding().y, s.getBounding().width, s.getBounding().height);
			g.drawImage(s.getBild(), s.getBounding().x, s.getBounding().y, null);
		}
		
		if(Spielablauf.spielEnde){
			//g.drawImage(BilderLaden.getFailBild(), 0, 0, null);
		}else{
			if(turmmenu){
				Turm ausgewählterTurm = Spielablauf.türme.get(MouseHandler.turmAuswahlIndex);
				
				//Turm Radius
				g.setColor(Color.black);
				g.drawOval(ausgewählterTurm.getMittelPunkt().x- ausgewählterTurm.getRange(), ausgewählterTurm.getMittelPunkt().y- ausgewählterTurm.getRange(), ausgewählterTurm.getRange()*2, ausgewählterTurm.getRange()*2);
				
				//Attribute
				g.setColor(Color.black);
				g.drawString("TURMLEVEL:  "+ausgewählterTurm.getTurmLevel(), (Spielablauf.spielfeldBreite+1)*Spielablauf.quadratGröße, Spielablauf.quadratGröße*5);
				g.drawString("Upgradepreis: "+ausgewählterTurm.getPriceUpgrade()+"$", (Spielablauf.spielfeldBreite+1)*Spielablauf.quadratGröße + 150, Spielablauf.quadratGröße*5);
				g.drawString("Dmg:  "+ausgewählterTurm.getSchaden(), (Spielablauf.spielfeldBreite+1)*Spielablauf.quadratGröße, Spielablauf.quadratGröße*6);
				g.drawString("Speed:  "+1/ausgewählterTurm.getSpeed(), (Spielablauf.spielfeldBreite+1)*Spielablauf.quadratGröße, Spielablauf.quadratGröße*7);
				g.drawString("Range:  "+ausgewählterTurm.getRange(), (Spielablauf.spielfeldBreite+1)*Spielablauf.quadratGröße, Spielablauf.quadratGröße*8);
				g.drawString("Crit:  "+ausgewählterTurm.getCrit()+"%", (Spielablauf.spielfeldBreite+1)*Spielablauf.quadratGröße, Spielablauf.quadratGröße*9);
				g.drawString("Slow:  "+ausgewählterTurm.getSlow()+"%", (Spielablauf.spielfeldBreite+1)*Spielablauf.quadratGröße, Spielablauf.quadratGröße*10);
				
				//UpgrateKnöpfe
				if(Spielablauf.goldCounter >= ausgewählterTurm.getPriceUpgrade()){
					g.setColor(Color.GREEN);
				}else{
					g.setColor(Color.RED);
				}
//				g.setColor(knUGDmg.getColor());
				g.fillRect(knUGDmg.getBounding().x, knUGDmg.getBounding().y, knUGDmg.getBounding().width, knUGDmg.getBounding().height);
//				g.setColor(knUGSpeed.getColor());
				g.fillRect(knUGSpeed.getBounding().x, knUGSpeed.getBounding().y, knUGSpeed.getBounding().width, knUGSpeed.getBounding().height);
//				g.setColor(knUGCrit.getColor());
				g.fillRect(knUGCrit.getBounding().x, knUGCrit.getBounding().y, knUGCrit.getBounding().width, knUGCrit.getBounding().height);
//				g.setColor(knUGRange.getColor());
				g.fillRect(knUGRange.getBounding().x, knUGRange.getBounding().y, knUGRange.getBounding().width, knUGRange.getBounding().height);
				
				//Turmmenu beenden
				g.setColor(knTurmmenu.getColor());
				g.fillRect(knTurmmenu.getBounding().x, knTurmmenu.getBounding().y, knTurmmenu.getBounding().width, knTurmmenu.getBounding().height);
				g.setColor(Color.BLACK);
				g.drawString("Turmmenu beenden", knTurmmenu.getBounding().x, knTurmmenu.getBounding().y);
			}
			
			if(!turmmenu){
				//TurmbauKnopf
				g.setColor(knTurmbau.getColor());
				g.fillRect(knTurmbau.getBounding().x, knTurmbau.getBounding().y, knTurmbau.getBounding().width, knTurmbau.getBounding().height);
				g.setColor(Color.BLACK);
				g.drawString("Turm bauen", knTurmbau.getBounding().x, knTurmbau.getBounding().y);
				
				//WallbauKnopf
				g.setColor(knWallbau.getColor());
				g.fillRect(knWallbau.getBounding().x, knWallbau.getBounding().y, knWallbau.getBounding().width, knWallbau.getBounding().height);
				g.setColor(Color.BLACK);
				g.drawString("Wall bauen", knWallbau.getBounding().x, knWallbau.getBounding().y);
				
				//Turm löschen
				g.setColor(knTurmLöschen.getColor());
				g.fillRect(knTurmLöschen.getBounding().x, knTurmLöschen.getBounding().y, knTurmLöschen.getBounding().width, knTurmLöschen.getBounding().height);
				g.setColor(Color.BLACK);
				g.drawString("Turm löschen", knTurmLöschen.getBounding().x, knTurmLöschen.getBounding().y);
				
				//SpawnKnopf
				g.setColor(knSpawn.getColor());
				g.fillRect(knSpawn.getBounding().x, knSpawn.getBounding().y, knSpawn.getBounding().width, knSpawn.getBounding().height);
				g.setColor(Color.BLACK);
				g.drawString("Spawn", knSpawn.getBounding().x, knSpawn.getBounding().y);
			}
		}
		
		//DrawWaveNumber
		g.setColor(Color.BLACK);
		g.drawString("Wave: " + Spielablauf.waveCounter, Spielablauf.spielfeld.width, 20);
		
		//DrawLebenGegner
		g.setColor(Color.BLACK);
		g.drawString("Leben Gegner: " + Spielablauf.LebenGegner, Spielablauf.spielfeld.width + 80, 20);
		
		//DrawGoldNumber
		g.setColor(Color.BLACK);
		g.drawString("Gold: " + Spielablauf.goldCounter, Spielablauf.spielfeld.width, 40);
		
		//DrawLeben
		g.setColor(Color.BLACK);
		g.drawString("Leben: " + Spielablauf.leben, Spielablauf.spielfeld.width, 60);
		
		//BeendenKnopf
		g.setColor(knBeenden.getColor());
		g.fillRect(knBeenden.getBounding().x, knBeenden.getBounding().y, knBeenden.getBounding().width, knBeenden.getBounding().height);
	}
}