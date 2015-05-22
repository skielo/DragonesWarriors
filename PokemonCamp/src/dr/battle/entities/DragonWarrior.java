package dr.battle.entities;

import java.util.ArrayList;
import java.util.List;

import dr.battle.DecisionTree.*;
import dr.battle.Algorithm.AStarPathFinder;
import dr.battle.Structure.Square;
import ia.battle.camp.*;
import ia.exceptions.*;

public class DragonWarrior extends Warrior {

	//private PathFinder pathFinder=null;
	private DragonWarriorMove pendingMoves = new DragonWarriorMove();
	String decision = "";

	public DragonWarrior(String name, int health, int defense, int strength,
			int speed, int range) throws RuleException {
		super(name, health, defense, strength, speed, range);
		//pathFinder = new PathFinder();
		if(!DecisionTreeApp.GetInstance().isTreeBuilded())
			DecisionTreeApp.GetInstance().generateTree();
	}

	@Override
	public Action playTurn(long tick, int actionNumber) {
		Action retval = null;
		WarriorData wd = BattleField.getInstance().getEnemyData();

		if(actionNumber==0)
		{
			this.ArmarDatosTurno();
			decision = DecisionTreeApp.GetInstance().queryTree();
		}
		//Es hora de escapar!
		if(decision.equals("huir")){
			/*
			if((this.getHealth()<this.getInitialHealth()/2) && this.isEnemyInRange(wd)){
				retval = new Suicide();
				//System.out.println("Comelaaaaaaa!");
			}
			else{*/
				retval=this.Huir(actionNumber);
			//}
			
			/*
			System.out.println("Me escapo");
			for (FieldCell item : ((DragonWarriorMove)retval).steps) {
				System.out.println("[" + item.getX() +":"+item.getY()+"]");
			}*/
		}
		//Ataco, Ataco y me las pico!
		if(decision.equals("atacar-huir")){
			if((actionNumber==0 || actionNumber==1) && this.isEnemyInRange(wd)){
				retval = new Attack(wd.getFieldCell());
				//System.out.println("Por ahora ataco");
			}	
			else if(actionNumber==2){
				retval=this.Huir(actionNumber);
				/*
				System.out.println("Me las pico");
				for (FieldCell item : ((DragonWarriorMove)retval).steps) {
					System.out.println("[" + item.getX() +":"+item.getY()+"]");
				}*/
			}
				
			else{
				retval=this.Acercarse();
				/*
				System.out.println("Me acerco");
				for (FieldCell item : ((DragonWarriorMove)retval).steps) {
					System.out.println("[" + item.getX() +":"+item.getY()+"]");
				}*/
			}	
		}
		//Ataco, Ataco y Ataco!
		if(decision.equals("atacar")){
			if(this.isEnemyInRange(wd)){
				retval = new Attack(wd.getFieldCell());
				//System.out.println("ataco");
			}	
			else{
				retval = this.Acercarse();
				/*
				System.out.println("Me acerco");
				for (FieldCell item : ((DragonWarriorMove)retval).steps) {
					System.out.println("[" + item.getX() +":"+item.getY()+"]");
				}*/
			}	
		}

		return retval;
	}
	
	private DragonWarriorMove Huir(int actionNumber){
		DragonWarriorMove ewm = new DragonWarriorMove();
		boolean clean=false;
		int x = this.getPosition().getX();
		int y = this.getPosition().getY();
		
		do
		{
			if(clean)
				pendingMoves.steps.clear();
			// || actionNumber==0
			if(pendingMoves.steps.size()==0){
				Square s = Map.getInstance().getMaze().getSquare(x, y);
				Square e = this.GetEndPosition();
				Map.getInstance().getMaze().setStart(s);
				Map.getInstance().getMaze().setEnd(e);
				AStarPathFinder astar = new AStarPathFinder(Map.getInstance().getMaze());
				ewm.setSteps(astar.getResult());
			}
			else
				ewm = pendingMoves;
			clean=true;
		}while(!this.IsPathValid(ewm.steps));
		
		return this.calculateMove(ewm);
	}
	
	private DragonWarriorMove Acercarse(){
		DragonWarriorMove ewm = new DragonWarriorMove();
		int x = this.getPosition().getX();
		int y = this.getPosition().getY();
		int i =1;
		int xDestino=0;
		int yDestino=0;
		Square e = null;
		do
		{
			do{
				xDestino=((BattleField.getInstance().getEnemyData().getFieldCell().getX()-this.getRange())+i);
				yDestino=((BattleField.getInstance().getEnemyData().getFieldCell().getY()-this.getRange())+i);
				xDestino=(xDestino>=0)?xDestino:xDestino*-1;
				yDestino=(yDestino>=0)?yDestino:yDestino*+1;
				e = Map.getInstance().getMaze().getSquare(xDestino,yDestino);
				i++;
			}while(!e.isTraversable());

			Square s = Map.getInstance().getMaze().getSquare(x, y);
			
			Map.getInstance().getMaze().setStart(s);
			Map.getInstance().getMaze().setEnd(e);
			AStarPathFinder astar = new AStarPathFinder(Map.getInstance().getMaze());
			ewm.setSteps(astar.getResult());
			i++;
		}while(!this.IsPathValid(ewm.steps));
		
		return this.calculateMove(ewm);
	}
	
	private void ArmarDatosTurno()
	{
		double distancia=0;
		boolean salud = false,saludEnemigo = false,defensa = false,dist=false;
		
		int x = this.getPosition().getX();
		int y = this.getPosition().getY();
		distancia = this.CalcularDistancia(x, y, BattleField.getInstance().getEnemyData().getFieldCell().getX(), 
				BattleField.getInstance().getEnemyData().getFieldCell().getY());
		
		dist=(distancia<=this.getRange() || distancia<=this.getSpeed());
		salud=(this.getHealth()>=this.getInitialHealth()/2);
		saludEnemigo=(this.getHealth()>=BattleField.getInstance().getEnemyData().getHealth());
		defensa=(this.getDefense()>=this.getInitialDefense()/2);
		Turno.getInstance().SetValues(dist, salud, saludEnemigo, defensa);
	}
	
	private double CalcularDistancia(int x, int y, int xdestino, int ydestino){
		double difX, difY;
		difX = x-xdestino;
		difY = y-ydestino;
		difX = difX>=0?difX:difX*-1;
		difY = difY>=0?difY:difY*-1;
		return (difX+difY);
	}
	
	private boolean isEnemyInRange(WarriorData wd){
        boolean retval =false;
		int centerX = this.getPosition().getX();
        int centerY = this.getPosition().getY();

        int range = this.getRange();

        int x = wd.getFieldCell().getX();
        int y = wd.getFieldCell().getY();

        retval = ((Math.pow(centerX - x, 2)) + (Math.pow(centerY - y, 2)) <= Math.pow(range, 2));

        return retval;
	}
	
	private DragonWarriorMove calculateMove(DragonWarriorMove move){
		DragonWarriorMove retval=new DragonWarriorMove();
		@SuppressWarnings("unchecked")
		ArrayList<FieldCell> cpy = (ArrayList<FieldCell>) move.steps.clone();
		int i =0;
		
		if(cpy.size() <= this.getSpeed()/5){
			retval.steps = cpy;
			pendingMoves.steps.clear();
		}
		else{
			pendingMoves.steps.clear();
			for (FieldCell field : cpy) {
				if(i < this.getSpeed()/5)
					retval.steps.add(field);
				else
					pendingMoves.steps.add(field);
				i++;
			}
		}
		/*
		System.out.println("Posicion actual: [" + this.getPosition().getX() + ":" + this.getPosition().getY() + "]");
		for (FieldCell field : retval.steps) {
			System.out.println("[" + field.getX() + ":" + field.getY() + "]");
		}
		*/
		return retval;
	}
	
	private Square GetEndPosition()
	{
		Square e = null;
		int x,y;
		do
		{
			if(BattleField.getInstance().getEnemyData().getFieldCell().getX() >= this.getPosition().getX()
					&& this.getPosition().getX()!=0){
				x=10;
			}
			else{
				x=ConfigurationManager.getInstance().getMapWidth()-5;
			}
			if(BattleField.getInstance().getEnemyData().getFieldCell().getY() >= this.getPosition().getY()
					&& this.getPosition().getY()!=0){
				y=10;
			}
			else{
				y=ConfigurationManager.getInstance().getMapHeight()-7;
			}
			e = Map.getInstance().getMaze().getSquare(x, y);
		}while(!e.isTraversable());
		return e;
	}
	
	private boolean IsPathValid(ArrayList<FieldCell> path)
	{
		boolean retval = true;
		FieldCell previousCell = this.getPosition();
		for (FieldCell fieldCell : path) {
            if (!fieldCell.equals(previousCell)) {
                List<FieldCell> adj = getAdjacentCells(previousCell);
                if (!adj.contains(fieldCell)) {
                    retval =false;
                    break;
                }
            }
            previousCell = fieldCell;
		}
		
		return retval;
	}
	
    private List<FieldCell> getAdjacentCells(FieldCell fieldCell) {
        ArrayList<FieldCell> adjCells = new ArrayList<FieldCell>();

        int x = fieldCell.getX();
        int y = fieldCell.getY();
        try {
	        if (x < ConfigurationManager.getInstance().getMapWidth() - 1)
				adjCells.add(BattleField.getInstance().getFieldCell(x + 1, y));
	
	        if (y < ConfigurationManager.getInstance().getMapHeight() - 1)
	            adjCells.add(BattleField.getInstance().getFieldCell(x,y + 1));
	
	        if (x > 0)
	            adjCells.add(BattleField.getInstance().getFieldCell(x - 1,y));
	
	        if (y > 0)
	            adjCells.add(BattleField.getInstance().getFieldCell(x,y - 1));
	
	        if (x > 0 && y > 0)
	            adjCells.add(BattleField.getInstance().getFieldCell(x - 1,y - 1));
	
	        if (x < ConfigurationManager.getInstance().getMapWidth() - 1 && y < ConfigurationManager.getInstance().getMapHeight() - 1)
	            adjCells.add(BattleField.getInstance().getFieldCell(x + 1,y + 1));
	
	        if (x > 0 && y < ConfigurationManager.getInstance().getMapHeight() - 1)
	            adjCells.add(BattleField.getInstance().getFieldCell(x - 1,y + 1));
	
	        if (x < ConfigurationManager.getInstance().getMapWidth() - 1 && y > 0)
	            adjCells.add(BattleField.getInstance().getFieldCell(x + 1,y - 1));
		} catch (OutOfMapException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return adjCells;
    }
}