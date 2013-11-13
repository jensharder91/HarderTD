package towerDefense;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JOptionPane;

public class MouseHandler implements MouseListener, MouseMotionListener{
	
	public static int turmAuswahlIndex = 0;
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
		boolean clicked = false;
	
		
		//Turm ausw�hlen
		if(!clicked && !Frame.knTurmL�schen.getAction() && Spielablauf.spielfeld.getBounds().contains(e.getX(), e.getY())){
			if(Spielablauf.spielfeldarray[e.getX()/Spielablauf.quadratGr��e][e.getY()/Spielablauf.quadratGr��e] == 1){
				clicked = true;
				for(int i = 0; i<Spielablauf.t�rme.size(); i++){
					if(((Spielablauf.t�rme.get(i).getX() == (e.getX()/Spielablauf.quadratGr��e)*Spielablauf.quadratGr��e+1 ) && (Spielablauf.t�rme.get(i).getY() == (e.getY()/Spielablauf.quadratGr��e)*Spielablauf.quadratGr��e+1))){
						turmAuswahlIndex = i;
						//ausgew�hlter turm:
						Spielablauf.t�rme.get(i).setTurmAusgew�hlt(true);
					}else{
						//alle anderen deaktivieren
						Spielablauf.t�rme.get(i).setTurmAusgew�hlt(false);
					}
				}
			}
		}
		
		if(Frame.turmmenu){
			//Turmmenu beenden
			if(!clicked && Frame.knTurmmenu.getBounding().contains(e.getX(), e.getY())){
				clicked = true;
				Spielablauf.t�rme.get(turmAuswahlIndex).setTurmAusgew�hlt(false);
				Frame.turmmenu = false;
			}
			
			if(!Frame.knSpawn.getAction()){
				
				//UpgradeKn�pfe
				if(!clicked && Frame.knUGDmg.getBounding().contains(e.getX(), e.getY())){
					Spielablauf.t�rme.get(turmAuswahlIndex).dmgUP();
				}
				if(!clicked && Frame.knUGSpeed.getBounding().contains(e.getX(), e.getY())){
					Spielablauf.t�rme.get(turmAuswahlIndex).speedUP();
				}
				if(!clicked && Frame.knUGRange.getBounding().contains(e.getX(), e.getY())){
					Spielablauf.t�rme.get(turmAuswahlIndex).rangeUP();
				}
				if(!clicked && Frame.knUGCrit.getBounding().contains(e.getX(), e.getY())){
					Spielablauf.t�rme.get(turmAuswahlIndex).critUP();
				}	
			}
		}
		
		if(!Frame.turmmenu){
			//Turm BAUEN, falls aktiviert und Feld frei bzw. g�ltig
			if(!clicked && Frame.knTurmbau.getAction() && Spielablauf.spielfeld.getBounds().contains(e.getX(), e.getY())){	
				clicked = true;
				if(Spielablauf.spielfeldarray[e.getX()/Spielablauf.quadratGr��e][e.getY()/Spielablauf.quadratGr��e] == 0){
					Spielablauf.t�rme.add(new Turm((e.getX()/Spielablauf.quadratGr��e)*Spielablauf.quadratGr��e+1, (e.getY()/Spielablauf.quadratGr��e)*Spielablauf.quadratGr��e+1, e.getX()/Spielablauf.quadratGr��e, e.getY()/Spielablauf.quadratGr��e, Color.ORANGE));
					//Frame.knTurmbau.setAction(false);
					Spielablauf.spielfeldarray[e.getX()/Spielablauf.quadratGr��e][e.getY()/Spielablauf.quadratGr��e] = 1;
					Spielablauf.goldCounter -= Turm.getPrice();
					
					/**/
					Wegfinden.wegfinden();
					//falls jetzt kein weg mehr existiert...oder zu wenig gold
					if(! Wegfinden.wegIstGefunden() || Spielablauf.goldCounter < 0){
						
						Spielablauf.goldCounter += Turm.getPrice();

						Turm.turmL�schen(Spielablauf.t�rme.size()-1);
					}
				}
			}
			
			//Wall BAUEN, falls aktiviert und Feld frei bzw. g�ltig
			if(!clicked && Frame.knWallbau.getAction() && Spielablauf.spielfeld.getBounds().contains(e.getX(), e.getY())){	
				clicked = true;
				if(Spielablauf.spielfeldarray[e.getX()/Spielablauf.quadratGr��e][e.getY()/Spielablauf.quadratGr��e] == 0){
					Spielablauf.walls.add(new Wall((e.getX()/Spielablauf.quadratGr��e)*Spielablauf.quadratGr��e+1, (e.getY()/Spielablauf.quadratGr��e)*Spielablauf.quadratGr��e+1, e.getX()/Spielablauf.quadratGr��e, e.getY()/Spielablauf.quadratGr��e));
					//Frame.knTurmbau.setAction(false);
					Spielablauf.spielfeldarray[e.getX()/Spielablauf.quadratGr��e][e.getY()/Spielablauf.quadratGr��e] = 1;
					Spielablauf.goldCounter -= Wall.getPrice();
					
					/**/
					Wegfinden.wegfinden();
					//falls jetzt kein weg mehr existiert...oder zu wenig gold
					if(! Wegfinden.wegIstGefunden() || Spielablauf.goldCounter < 0){
						
						Spielablauf.goldCounter += Wall.getPrice();

						Wall.wallL�schen(Spielablauf.walls.size()-1);
					}
				}
			}
			
			//Turm L�SCHEN, falls aktiviert und Turm besetzt
			if(!clicked && Frame.knTurmL�schen.getAction() && Spielablauf.spielfeld.getBounds().contains(e.getX(), e.getY())){	
				clicked = true;
				if(Spielablauf.spielfeldarray[e.getX()/Spielablauf.quadratGr��e][e.getY()/Spielablauf.quadratGr��e] == 1){
					//T�rme
					for(int i = 0; i<Spielablauf.t�rme.size(); i++){
						Turm t = Spielablauf.t�rme.get(i);
						if(t.getX()/Spielablauf.quadratGr��e == e.getX()/Spielablauf.quadratGr��e && t.getY()/Spielablauf.quadratGr��e == e.getY()/Spielablauf.quadratGr��e){
							Spielablauf.goldCounter += Spielablauf.t�rme.get(i).getPriceGesammt() * 0.8;
							Turm.turmL�schen(i);
							break;
						}
					}
					
					//Walls
					for(int i = 0; i<Spielablauf.walls.size(); i++){
						Wall w = Spielablauf.walls.get(i);
						if(w.getX()/Spielablauf.quadratGr��e == e.getX()/Spielablauf.quadratGr��e && w.getY()/Spielablauf.quadratGr��e == e.getY()/Spielablauf.quadratGr��e){
							Wall.wallL�schen(i);
							break;
						}
					}
				}
			}
			
			//Knopfdr�cken -> Turm bauen aktivieren
			if(!clicked && !Frame.knSpawn.getAction() && Frame.knTurmbau.getBounding().contains(e.getX(), e.getY())){
				clicked = true;
				if(Frame.knTurmbau.getAction()==true){
					Frame.knTurmbau.setAction(false);
				}
				else{
					Frame.knTurmbau.setAction(true);
					Frame.knTurmL�schen.setAction(false);
					Frame.knWallbau.setAction(false);
				}	
			}
			
			//Knopfdr�cken -> Wall bauen aktivieren
			if(!clicked && !Frame.knSpawn.getAction() && Frame.knWallbau.getBounding().contains(e.getX(), e.getY())){
				clicked = true;
				if(Frame.knWallbau.getAction()==true){
					Frame.knWallbau.setAction(false);
				}
				else{
					Frame.knWallbau.setAction(true);
					Frame.knTurmL�schen.setAction(false);
					Frame.knTurmbau.setAction(false);
				}	
			}
			
			//Knopfdr�cken -> Turm l�schen aktivieren
			if(!clicked && !Frame.knSpawn.getAction() && Frame.knTurmL�schen.getBounding().contains(e.getX(), e.getY())){
				clicked = true;
				
				if(Frame.knTurmL�schen.getAction()==true){
					Frame.knTurmL�schen.setAction(false);
				}
				else{
					Frame.knTurmL�schen.setAction(true);
					Frame.knTurmbau.setAction(false);
					Frame.knWallbau.setAction(false);
				}
			}
			
			//KnopfSpawn
			if(!clicked && Frame.knSpawn.getBounding().contains(e.getX(), e.getY())){
				clicked = true;
				if(Frame.knSpawn.getAction()==false){
					/**/Wegfinden.wegfinden();
					Enemy.spawn();
					Frame.knSpawn.setAction(true);
					Frame.knTurmbau.setAction(false);
					Frame.knTurmL�schen.setAction(false);
					Frame.knWallbau.setAction(false);
				}
			}
		}
		
		//Beendenknopf
		if(!clicked && Frame.knBeenden.getBounding().contains(e.getX(), e.getY())){
			clicked = true;
			int result = JOptionPane.showConfirmDialog(null, "Wollen Sie das Spiel beenden?", "BEENDEN", JOptionPane.YES_NO_OPTION);
			if (result == JOptionPane.YES_OPTION) {
				System.exit(0);
			} else if (result == JOptionPane.NO_OPTION) {
			}
		}
		
		clicked = false;
		
	}
	

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	//	System.out.println("CLICK: position: " + e.getX() + " " + e.getY());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	
	
	/*MouseMotion*/
	
	@Override
	public void mouseDragged(MouseEvent e) {
		//System.out.println("test123456");
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	//	System.out.println("position: " + e.getX() + " " + e.getY());
		
		
		//Schattenk�stechen
	/*	if(Turm.getTurmAusgew�hlt() && e.getX()<Spielablauf.spielfeldBreite*Spielablauf.quadratGr��e+1){
			Spielablauf.t�rme.add(new Turm((e.getX()/Spielablauf.quadratGr��e)*Spielablauf.quadratGr��e+1, (e.getY()/Spielablauf.quadratGr��e)*Spielablauf.quadratGr��e+1));
			Turm.setTurmAusgew�hlt(false);
			Spielablauf.spielfeld[e.getX()/Spielablauf.quadratGr��e][e.getY()/Spielablauf.quadratGr��e] = 1;
		}
	*/	
	}
	
}