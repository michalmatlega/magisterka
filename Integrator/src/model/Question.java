package model;


public class Question extends AbstractQuestion {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private QuestionVariant varA;
	private QuestionVariant varB;
	private QuestionVariant varC;

	public Question() {
		super();
		this.varA = new QuestionVariant();
		this.varB = new QuestionVariant();
		this.varC = new QuestionVariant();
		this.setCorrectA();
		
	}
	
	


	public QuestionVariant getVarA() {
		return varA;
	}




	public void setVarA(QuestionVariant varA) {
		this.varA = varA;
	}




	public QuestionVariant getVarB() {
		return varB;
	}


	public void setVarB(QuestionVariant varB) {
		this.varB = varB;
	}


	public QuestionVariant getVarC() {
		return varC;
	}


	public void setVarC(QuestionVariant varC) {
		this.varC = varC;
	}
	
	public void clearCorrect(){
		this.varA.setCorrect(false);
		this.varB.setCorrect(false);
		this.varC.setCorrect(false);
	}
	
	public void setCorrectA(){
		this.clearCorrect();
		this.varA.setCorrect(true);
	}
	
	public void setCorrectB(){
		this.clearCorrect();
		this.varB.setCorrect(true);
	}

	public void setCorrectC(){
		this.clearCorrect();
		this.varC.setCorrect(true);
	}
	
	
}