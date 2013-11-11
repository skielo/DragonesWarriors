package dr.battle.DecisionTree;
//DECISION TREE  APPLICATION
//Frans Coenen
//Thursday 15 August 2002
//Department of Computer Science, University of Liverpool

import java.io.*;

public class DecisionTreeApp {
	
	 /* ------------------------------- */
	 /*                                 */
	 /*              FIELDS             */
	 /*                                 */
	 /* ------------------------------- */
	
	 static BufferedReader keyboardInput = new
	                        BufferedReader(new InputStreamReader(System.in));
	 static DecisionTree newTree;
	 private boolean isTreeBuilded=false;
	 private static DecisionTreeApp _instance;
	
	 /* --------------------------------- */
	 /*                                   */
	 /*               METHODS             */
	 /*                                   */
	 /* --------------------------------- */
	
	 private DecisionTreeApp(){
		 newTree = new DecisionTree();
	 }
	 
	 public static DecisionTreeApp GetInstance(){
		 if(_instance==null)
			 _instance=new DecisionTreeApp();
		 return _instance;
	 }
	 
	 /* MAIN */
	/*
	 public static void main(String[] args) throws IOException {
	
	     // Create instance of class DecisionTree
	
	     newTree = new DecisionTree();
	
	     // Generate tree
	
	     generateTree();
	
	     // Output tree
	
	     System.out.println("\nOUTPUT DECISION TREE");
	     System.out.println("====================");
	     newTree.outputBinTree();
	
	     // Query tree
	
	     queryTree();
     }
	*/
	 /* GENERATE TREE */
	
	 public void generateTree() {
	     //System.out.println("\nGENERATE DECISION TREE");
	     //System.out.println("======================");
	     newTree.createRoot(1,"salud");
	     newTree.addYesNode(1,2,"distancia");
	     newTree.addNoNode(1,3,"huir");
	     newTree.addYesNode(2,4,"salud enemigo");
	     newTree.addNoNode(2,5,"salud enemigo");
	     newTree.addYesNode(4,6,"defensa");
	     newTree.addNoNode(4,7,"atacar-huir"); 
	     newTree.addYesNode(5,8,"defensa");
	     newTree.addNoNode(5,9,"huir");
	     newTree.addYesNode(6,10,"atacar");
	     newTree.addNoNode(6,11,"atacar-huir");
	     newTree.addYesNode(8,12,"atacar-huir");
	     newTree.addNoNode(8,13,"huir");
	     this.isTreeBuilded=true;
	}
	
	 /* QUERY TREE */
		
	 public boolean isTreeBuilded() {
		return isTreeBuilded;
	 }

	public String queryTree()  {
	     //System.out.println("\nQUERY DECISION TREE");
	     //System.out.println("===================");
	     return newTree.queryBinTree();
	
	     // Option to exit
	
	     //optionToExit();
	 }
	
	 /* OPTION TO EXIT PROGRAM */
	
	 public void optionToExit() throws IOException {
	     System.out.println("Exit? (enter \"Yes\" or \"No\")");
	     String answer = keyboardInput.readLine();
	     if (answer.equals("Yes")) return;
	     else {
	         if (answer.equals("No")) queryTree();
	         else {
	             System.out.println("ERROR: Must answer \"Yes\" or \"No\"");
	             optionToExit();
             }
         }
     }
 }
