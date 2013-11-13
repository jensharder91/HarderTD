
package towerDefense;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class Turm {
	private BufferedImage bild;
	private final int posx;
	private final int posy;
	private final int arrayCoordX;
	private final int arrayCoordY;
	private final Point mittelPunkt;
	private boolean turmAusgew�hlt = false;
	private Color color1;
	private Color color2;
	private float timeSinceLastShot = 0;
	private int turmLevel = 0;
	private static int price = 100;
	private int priceGesammt = price;
	private int priceUpgrade = 150;
	private float speed = 0.5f;
	private float range = 100;
	private int schaden = 10;
	private int slow = 0;
	private float crit = 0;
	private Enemy zielEnemy = null;

	
	Turm(int x, int y, int arrayCoordX, int arrayCoordY, Color color1){
		posx = x;
		posy = y;
		this.arrayCoordX = arrayCoordX;
		this.arrayCoordY = arrayCoordY;
		this.color1 = color1;
		this.color2 = Color.RED;
		this.bild = BilderLaden.getTurmBild();
		this.mittelPunkt = new Point(posx + Spielablauf.quadratGr��e/2, posy + Spielablauf.quadratGr��e/2);
	}
	
	public void update(float timeSinceLastFrame){
		timeSinceLastShot += timeSinceLastFrame;
		
		//schuss
		if(timeSinceLastShot >= speed){
			timeSinceLastShot -= speed;
			if(timeSinceLastShot >= 2*speed){
				timeSinceLastShot = 0;
			}
			

			if(zielEnemy == null){
				for(int i = 0; i < Spielablauf.enemys.size(); i++){
					Enemy e = Spielablauf.enemys.get(i);
					
					if(new Point (e.getBounding().x+e.getBild().getWidth()/2, e.getBounding().y+e.getBild().getHeight()/2).distance(mittelPunkt) <= range){
						
						if(zielEnemy == null){
							zielEnemy = e;
						}else if(e.getIndexWeg() > zielEnemy.getIndexWeg()){
							zielEnemy = e;
						}
					}
				}
			}else if(new Point (zielEnemy.getBounding().x, zielEnemy.getBounding().y).distance(mittelPunkt) > range || zielEnemy.getLeben() <=0){
				zielEnemy = null;
			}
			
			if(zielEnemy != null && zielEnemy.getSpawned()){
				Spielablauf.sch�sse.add(new Schuss(mittelPunkt.x, mittelPunkt.y, zielEnemy, this));
			}
		}
	}
	
	public static void turmL�schen(int index){
		//setze das array zur�ck auf 0
		Spielablauf.spielfeldarray[Spielablauf.t�rme.get(index).getCoord().x][Spielablauf.t�rme.get(index).getCoord().y] = 0;
		//l�sche den ausgew�hlten turm!
		Spielablauf.t�rme.remove(index);
	}
	
	//TurmUpgrade
	private void turmUpgrade(){
		turmLevel++;
		Spielablauf.goldCounter -= priceUpgrade;
		priceGesammt += priceUpgrade;
		priceUpgrade *= 2;
		schaden += 5*turmLevel;
	}
	//AtrributeUpgraden
	public void dmgUP(){
		if(priceUpgrade <= Spielablauf.goldCounter && turmLevel <= 9){
			schaden += 0.1 * priceUpgrade;
			turmUpgrade();
		}
	}
	public void speedUP(){
		if(priceUpgrade <= Spielablauf.goldCounter && turmLevel <= 9){
			turmUpgrade();
			speed -= 0.03;
		}
	}
	public void rangeUP(){
		if(priceUpgrade <= Spielablauf.goldCounter && turmLevel <= 9){
			turmUpgrade();
			range +=15;
		}
	}
	public void critUP(){
		if(priceUpgrade <= Spielablauf.goldCounter && turmLevel <= 9){
			turmUpgrade();
			crit += 1.5*turmLevel;
		}
	}
	

	
	public Color getColor(){
		if(turmAusgew�hlt){
			return color2;
		}
		return color1;
	}
	public int getX(){
		return posx;
	}
	public int getY(){
		return posy;
	}
	public Point getCoord(){
		return new Point(arrayCoordX, arrayCoordY);
	}
	public boolean getTurmAusgew�hlt(){
		return turmAusgew�hlt;
	}
	public int getTurmLevel(){
		return turmLevel;
	}
	public int getSchaden(){
		return schaden;
	}
	public float getCrit(){
		return crit;
	}
	public int getSlow(){
		return slow;
	}
	public float getSpeed(){
		return speed;
	}
	public static int getPrice(){
		return price;
	}
	public int getPriceUpgrade(){
		return priceUpgrade;
	}
	public int getPriceGesammt(){
		return priceGesammt;
	}
	public int getRange(){
		return (int) range;
	}
	public Point getMittelPunkt(){
		return mittelPunkt;
	}
	public BufferedImage getBild(){
		return bild;
	}
	
	public void setTurmAusgew�hlt(boolean b){
		this.turmAusgew�hlt = b;
	}

}