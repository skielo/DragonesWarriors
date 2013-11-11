package dr.battle.entities;


public class Turno{
	private boolean distanciaRecorrida;
	private boolean health;
	private boolean enemyHealth;
	private boolean defense;
	private static Turno instance;
	
	private Turno(){
		
	}
	
	public static Turno getInstance(){
		if(instance == null)
			instance=new Turno();
		return instance;
	}
	
	public void SetValues( boolean distancia,  boolean health, boolean enemyHealth,boolean defense){
		this.setDistanciaRecorrida(distancia);
		this.setHealth(health);
		this.setEnemyHealth(enemyHealth);
		this.setDefense(defense);
	}

	public boolean getDistanciaRecorrida() {
		return distanciaRecorrida;
	}

	public void setDistanciaRecorrida(boolean distanciaRecorrida) {
		this.distanciaRecorrida = distanciaRecorrida;
	}

	public boolean getHealth() {
		return health;
	}

	public void setHealth(boolean health) {
		this.health = health;
	}

	public boolean getEnemyHealth() {
		return enemyHealth;
	}

	public void setEnemyHealth(boolean enemyHealth) {
		this.enemyHealth = enemyHealth;
	}

	public boolean getDefense() {
		return defense;
	}

	public void setDefense(boolean defense) {
		this.defense = defense;
	}
}
