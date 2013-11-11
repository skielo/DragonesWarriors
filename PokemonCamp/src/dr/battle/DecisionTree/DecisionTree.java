package dr.battle.DecisionTree;
//DECISION TREE
//Frans Coenen
//Thursday 15 August 2002
//Department of Computer Science, University of Liverpool

import java.io.*;

import dr.battle.entities.Turno;

public class DecisionTree {
	
	 /* ------------------------------- */
	 /*                                 */
	 /*              FIELDS             */
	 /*                                 */
	 /* ------------------------------- */
	
	 /* NESTED CLASS */
	
	 private class BinTree {
	 	
		/* FIELDS */
		
		private int     nodeID;
	 	public String  questOrAns = null;
	 	private BinTree yesBranch  = null;
	 	private BinTree noBranch   = null;
		
		/* CONSTRUCTOR */
		
		public BinTree(int newNodeID, String newQuestAns) {
		    nodeID     = newNodeID;
		    questOrAns = newQuestAns;
         }
	}
	
	 /* OTHER FIELDS */
	
	 static BufferedReader    keyboardInput = new
	                        BufferedReader(new InputStreamReader(System.in));
	 BinTree rootNode = null;
	 /* ------------------------------------ */
	 /*                                      */
	 /*              CONSTRUCTORS            */
	 /*                                      */
	 /* ------------------------------------ */
	
	 /* Default Constructor */
	
	 public DecisionTree() {
	 }
	
	 /* ----------------------------------------------- */
	 /*                                                 */
	 /*               TREE BUILDING METHODS             */
	 /*                                                 */
	 /* ----------------------------------------------- */
	
	 /* CREATE ROOT NODE */
	
	 public void createRoot(int newNodeID, String newQuestAns) {
		rootNode = new BinTree(newNodeID,newQuestAns);	
		//System.out.println("Created root node " + newNodeID);	
	}
				
	 /* ADD YES NODE */
	
	 public void addYesNode(int existingNodeID, int newNodeID, String newQuestAns) {
		// If no root node do nothing
		
		if (rootNode == null) {
		    System.out.println("ERROR: No root node!");
		    return;
		    }
		
		// Search tree
		/**/
		if (searchTreeAndAddYesNode(rootNode,existingNodeID,newNodeID,newQuestAns)) {
		    //System.out.println("Added node " + newNodeID + " onto \"yes\" branch of node " + existingNodeID);
		    }
		else System.out.println("Node " + existingNodeID + " not found");
	}
	
	 /* SEARCH TREE AND ADD YES NODE */

	private boolean searchTreeAndAddYesNode(BinTree currentNode,
	 			int existingNodeID, int newNodeID, String newQuestAns) {
	 	if (currentNode.nodeID == existingNodeID) {
		    // Found node
		    if (currentNode.yesBranch == null) currentNode.yesBranch = new
		    		BinTree(newNodeID,newQuestAns);
		    else {
		        System.out.println("WARNING: Overwriting previous node " +
				"(id = " + currentNode.yesBranch.nodeID +
				") linked to yes branch of node " +
				existingNodeID);
			currentNode.yesBranch = new BinTree(newNodeID,newQuestAns);
			}		
	 	    return(true);
		    }
		else {
		    // Try yes branch if it exists
		    if (currentNode.yesBranch != null) { 	
		        if (searchTreeAndAddYesNode(currentNode.yesBranch,
			        	existingNodeID,newNodeID,newQuestAns)) {    	
		            return(true);
			    }	
			else {
	 	        // Try no branch if it exists
		    	    if (currentNode.noBranch != null) {
	 	    		return(searchTreeAndAddYesNode(currentNode.noBranch,
					existingNodeID,newNodeID,newQuestAns));
				}
			    else return(false);	// Not found here
			    }
	 		}
		    return(false);		// Not found here
	    }
	} 	
	 		
	 /* ADD NO NODE */
	
	 public void addNoNode(int existingNodeID, int newNodeID, String newQuestAns) {
		// If no root node do nothing
		
		if (rootNode == null) {
		    System.out.println("ERROR: No root node!");
		    return;
		    }
		
		// Search tree
		
		if (searchTreeAndAddNoNode(rootNode,existingNodeID,newNodeID,newQuestAns)) {
		    //System.out.println("Added node " + newNodeID +" onto \"no\" branch of node " + existingNodeID);
		    }
		else System.out.println("Node " + existingNodeID + " not found");
	}
		
	 /* SEARCH TREE AND ADD NO NODE */
	
	 private boolean searchTreeAndAddNoNode(BinTree currentNode,
	 			int existingNodeID, int newNodeID, String newQuestAns) {
	 	if (currentNode.nodeID == existingNodeID) {
		    // Found node
		    if (currentNode.noBranch == null) currentNode.noBranch = new
		    		BinTree(newNodeID,newQuestAns);
		    else {
		        System.out.println("WARNING: Overwriting previous node " +
				"(id = " + currentNode.noBranch.nodeID +
				") linked to yes branch of node " +
				existingNodeID);
			currentNode.noBranch = new BinTree(newNodeID,newQuestAns);
			}		
	 	    return(true);
		    }
		else {
		    // Try yes branch if it exists
		    if (currentNode.yesBranch != null) { 	
		        if (searchTreeAndAddNoNode(currentNode.yesBranch,
			        	existingNodeID,newNodeID,newQuestAns)) {    	
		            return(true);
			    }	
			else {
	 	        // Try no branch if it exists
		    	    if (currentNode.noBranch != null) {
	 	    		return(searchTreeAndAddNoNode(currentNode.noBranch,
					existingNodeID,newNodeID,newQuestAns));
			}
		    else return(false);	// Not found here
		    }
		 }
	    else return(false);	// Not found here
	    }
	} 	
	
	 /* --------------------------------------------- */
	 /*                                               */
	 /*               TREE QUERY METHODS             */
	 /*                                               */
	 /* --------------------------------------------- */
	
	 public String queryBinTree()  {
	     return queryBinTree(rootNode);
     }
	
	 private String queryBinTree(BinTree currentNode)  {
	
	     // Test for leaf node (answer) and missing branches
	
	     if (currentNode.yesBranch==null) {
	         if (currentNode.noBranch==null) 
	        	 return currentNode.questOrAns;
         }
	     if (currentNode.noBranch==null) {
	         System.out.println("Error: Missing \"No\" branch at \"" +
	         		currentNode.questOrAns + "\" question");
	         return currentNode.questOrAns;
         }

	     // Question
	
	     return askQuestion(currentNode);
     }
	
	 private String askQuestion(BinTree currentNode)  {
	     //System.out.println(currentNode.questOrAns + " (enter \"Yes\" or \"No\")");
	     //String answer = keyboardInput.readLine();
		 boolean value = false;
	     switch (this.GetNumeroNode(currentNode)) {
		case 1:
			value = Turno.getInstance().getHealth();
			break;
		case 2:
			value = Turno.getInstance().getDistanciaRecorrida();
			break;
		case 3:
			value = Turno.getInstance().getEnemyHealth();
			break;
		case 4:
			value = Turno.getInstance().getDefense();
			break;
		}
	     if (value) 
	    	 return queryBinTree(currentNode.yesBranch);
	     else {
	         if (!value) 
	        	 return queryBinTree(currentNode.noBranch);
	         else {
	             System.out.println("ERROR: Must answer \"Yes\" or \"No\"");
	             return askQuestion(currentNode);
             }
         }
     }
	
	 private int GetNumeroNode(BinTree currentNode){
		 int retval=0;
		 if(currentNode.questOrAns.equals("salud"))
			 retval=1;
		 if(currentNode.questOrAns.equals("distancia"))
			 retval=2;
		 if(currentNode.questOrAns.equals("salud enemigo"))
			 retval=3;
		 if(currentNode.questOrAns.equals("defensa"))
			 retval=4;
		 return retval;
	 }
	 /* ----------------------------------------------- */
	 /*                                                 */
	 /*               TREE OUTPUT METHODS               */
	 /*                                                 */
	 /* ----------------------------------------------- */
	
	 /* OUTPUT BIN TREE */
	
	 public void outputBinTree() {
	
	     outputBinTree("1",rootNode);
	 }
	
	 private void outputBinTree(String tag, BinTree currentNode) {
	
	     // Check for empty node
	
	     if (currentNode == null) return;
	
	     // Output
	
	     System.out.println("[" + tag + "] nodeID = " + currentNode.nodeID +
	     		", question/answer = " + currentNode.questOrAns);
	     		
	     // Go down yes branch
	
	     outputBinTree(tag + ".1",currentNode.yesBranch);
	
	     // Go down no branch
	
	     outputBinTree(tag + ".2",currentNode.noBranch);
	}      		
}