package model;


public class Question extends AbstractQuestion {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private QuestionVariant varA;
	private QuestionVariant varB;
	private QuestionVariant varC;
	private int correct;			//1 - A, 2 - B, 3 - C
	


	public Question() {
		super();
		this.varA = new QuestionVariant();
		this.varB = new QuestionVariant();
		this.varC = new QuestionVariant();
		this.correct = 1;
		
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


	public int getCorrect() {
		return correct;
	}


	public void setCorrect(int correct) {
		this.correct = correct;
	}
	
	
}
