package towerDefense;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BilderLaden {
	
	private static BufferedImage turmBild;
	private static BufferedImage failBild;
	private static BufferedImage schussBild;
	private static BufferedImage schussExploBild;
	private static BufferedImage gegner_1_Bild;
	private static BufferedImage gegner_1_schild_Bild;
	private static BufferedImage gegner_2_Bild;
	private static BufferedImage gegner_3_Bild;
	private static BufferedImage gegner_4_Bild;
	
	public BilderLaden() {
		laden();
	}
	
	public  void laden(){
		try {
			turmBild = ImageIO.read(getClass().getClassLoader().getResourceAsStream("Bilder/Turm 1.png"));
			failBild = ImageIO.read(getClass().getClassLoader().getResourceAsStream("Bilder/Failbildschirm.png"));
			schussBild = ImageIO.read(getClass().getClassLoader().getResourceAsStream("Bilder/Schuss.png"));
			schussExploBild = ImageIO.read(getClass().getClassLoader().getResourceAsStream("Bilder/Schuss_Explosion_test.png"));
			gegner_1_Bild = ImageIO.read(getClass().getClassLoader().getResourceAsStream("Bilder/Gegner1_test.png"));
			gegner_1_schild_Bild = ImageIO.read(getClass().getClassLoader().getResourceAsStream("Bilder/Gegner1_schild_test.png"));
			gegner_2_Bild = ImageIO.read(getClass().getClassLoader().getResourceAsStream("Bilder/Gegner2_test.png"));
			gegner_3_Bild = ImageIO.read(getClass().getClassLoader().getResourceAsStream("Bilder/Gegner3_test.png"));
			gegner_4_Bild = ImageIO.read(getClass().getClassLoader().getResourceAsStream("Bilder/Gegner4_test.png"));
		} catch (IOException e) {e.printStackTrace();}
	}
	
	public static BufferedImage getTurmBild(){
		return turmBild;
	}
	public static BufferedImage getFailBild(){
		return failBild;
	}
	public static BufferedImage getSchuss(){
		return schussBild;
	}
	public static BufferedImage getSchussExplo(){
		return schussExploBild;
	}
	public static BufferedImage getGegner1(){
		return gegner_1_Bild;
	}
	public static BufferedImage getGegner1schild(){
		return gegner_1_schild_Bild;
	}
	public static BufferedImage getGegner2(){
		return gegner_2_Bild;
	}
	public static BufferedImage getGegner3(){
		return gegner_3_Bild;
	}
	public static BufferedImage getGegner4(){
		return gegner_4_Bild;
	}

}
