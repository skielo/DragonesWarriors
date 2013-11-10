package dr.battle.entities;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class Resultado{
	private int distanciaRecorrida;
	private int health;
	private int speed;
	private int enemyHealth;
	private int enemyHealthPrevious;
	private int turno;
	private int actionNumber;
	private boolean hasAttacked;
	private int range;
	private int defense;
	private String resultado;
	private static Resultado instance;
	private int name=0;
	
	private Resultado(){
		
	}
	
	public static Resultado getInstance(){
		if(instance == null)
			instance=new Resultado();
		return instance;
	}
	
	public void SetValues( int distancia,  int health,  int speed,  int enemyHealth,  int turno,  int actionNumber,  boolean hasAttacked,
			 int range,  int defense){
		this.distanciaRecorrida=distancia;
		this.health=health;
		this.speed=speed;
		this.enemyHealth=enemyHealth;
		this.turno=turno;
		this.actionNumber=actionNumber;
		this.hasAttacked=hasAttacked;
		this.range=range;
		this.defense=defense;
	}
	
	public void SetEnemyData(int health){
		this.enemyHealthPrevious = health;
	}
	
	public void SaveData() throws IOException{
		FileWriter outputB = null;
		BufferedWriter bwA = null;
		String ataco="no";
		name++;
		try {
			outputB = new FileWriter("outputB.txt",true);
			bwA = new BufferedWriter(outputB);
			String linea = "";
			if(this.enemyHealthPrevious==0)
				this.enemyHealthPrevious=this.enemyHealth;
			if(hasAttacked)
				ataco="yes";
			if(this.health > 0 && this.enemyHealth < this.enemyHealthPrevious)
				this.resultado = "gana";
			else if(this.health > 0 && this.enemyHealth == this.enemyHealthPrevious)
				this.resultado = "no gana";
			else if(this.health == 0 && ((this.enemyHealth == this.enemyHealthPrevious) ||
					(this.enemyHealth < this.enemyHealthPrevious)))
				this.resultado = "pierde";
			linea += name + " " + distanciaRecorrida + " " + health + " "  + speed + " "  + enemyHealth + " "  + turno + " "  + actionNumber
					 + " " + ataco + " "  + range + " "  + defense + " "  + resultado + "\n";
			bwA.append(linea);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			bwA.close();
			outputB.close();
		}		
	}
}
