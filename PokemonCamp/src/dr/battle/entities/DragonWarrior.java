package dr.battle.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dr.battle.Algorithm.AStarPathFinder;
import dr.battle.Structure.Square;
import ia.battle.camp.Action;
import ia.battle.camp.Attack;
import ia.battle.camp.BattleField;
import ia.battle.camp.ConfigurationManager;
import ia.battle.camp.FieldCell;
import ia.battle.camp.Warrior;
import ia.battle.camp.WarriorData;
import ia.exceptions.OutOfMapException;
import ia.exceptions.RuleException;

public class DragonWarrior extends Warrior {

	//private PathFinder pathFinder=null;
	private DragonWarriorMove pendingMoves = new DragonWarriorMove();
	private Random random = new Random();
	
	public DragonWarrior(String name, int health, int defense, int strength,
			int speed, int range) throws RuleException {
		super(name, health, defense, strength, speed, range);
		//pathFinder = new PathFinder();
	}

	@Override
	public Action playTurn(long tick, int actionNumber) {
		DragonWarriorMove ewm = new DragonWarriorMove();
		Action retval = null;
		WarriorData wd = BattleField.getInstance().getEnemyData();
		boolean clean=false;
		//int distancia=0;
		//boolean hasAttacked=false;
		
		int x = this.getPosition().getX();
		int y = this.getPosition().getY();
		
		do
		{
			if(clean)
				pendingMoves.steps.clear();
			if(pendingMoves.steps.size()==0 || actionNumber==0){
				Square s = Map.getInstance().getMaze().getSquare(x, y);
				Square e = this.GetEndPosition();//Map.getInstance().getMaze().getSquare(xdestino, ydestino);
				Map.getInstance().getMaze().setStart(s);
				Map.getInstance().getMaze().setEnd(e);
				AStarPathFinder astar = new AStarPathFinder(Map.getInstance().getMaze());
				ewm.setSteps(astar.getResult());
				/*ewm.setSteps(pathFinder.find(new Node(x, y),new Node(xdestino,ydestino)));
				
				System.out.println("Yendo de: [" + x + ";" + y + "] a:[" + e.getX() + ";" + e.getY() + "]");
				System.out.println("----------------");
				*/
			}
			else
				ewm = pendingMoves;
			clean=true;
		}while(!this.IsPathValid(ewm.steps));

		clean=false;
		if(this.isEnemyInRange(wd) && actionNumber%2==0){
			retval = new Attack(wd.getFieldCell());
			//hasAttacked = true;
		}
		else
			retval = this.calculateMove(ewm);
		/*
		try {
			Resultado.getInstance().SetValues(ewm.steps.size(), this.getHealth(), this.getSpeed(), wd.getHealth(), (int) tick, actionNumber, 
					hasAttacked, this.getRange(), this.getDefense());
			Resultado.getInstance().SaveData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return retval;
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
		/**/
		System.out.println("Posicion actual: [" + this.getPosition().getX() + ":" + this.getPosition().getY() + "]");
		for (FieldCell field : retval.steps) {
			System.out.println("[" + field.getX() + ":" + field.getY() + "]");
		}
		
		return retval;
	}
	
	private Square GetEndPosition()
	{
		Square e = null;
		do
		{
			e = Map.getInstance().getMaze().getSquare(random.nextInt(ConfigurationManager.getInstance().getMapWidth()), 
					random.nextInt(ConfigurationManager.getInstance().getMapHeight()));
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