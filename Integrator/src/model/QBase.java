package model;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;


public class QBase implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String author;
	ArrayList<Question> questions;
	
	transient private boolean toDelete;
	transient private int amountToTest;
	
	
	public int getAmountToTest() {
		return amountToTest;
	}

	public void setAmountToTest(int amountToTest) {
		this.amountToTest = amountToTest;
	}

	public QBase(String name, String author) {
		super();
		this.name = name;	
		this.author = author;
		this.toDelete = false;
		this.amountToTest = 0;
		questions = new ArrayList<Question>();
		addQuestion();
	}
	
	public QBase() {
		super();
		this.toDelete = false;
		questions = new ArrayList<Question>();
	}
	
	public boolean isToDelete() {
		return toDelete;
	}

	public void setToDelete(boolean toDelete) {
		this.toDelete = toDelete;
	}

	public void addQuestion(){
		questions.add(new Question());
	}
	
	public void setContent(int i, String var){
		if(i < questions.size())
			questions.get(i).setContent(var);
		else
			System.out.println("Poza tablico");
	}
	
	public void setVarA(int i, String var){
		if(i < questions.size())
			questions.get(i).setVarA(new QuestionVariant(var));
		else
			System.out.println("Poza tablico");
	}
	
	public void setVarB(int i, String var){
		if(i < questions.size())
			questions.get(i).setVarB(new QuestionVariant(var));
		else
			System.out.println("Poza tablico");
	}
	
	public void setVarC(int i, String var){
		if(i < questions.size())
			questions.get(i).setVarC(new QuestionVariant(var));
		else
			System.out.println("Poza tablico");
	}
	
	public void removeQuestion(int i){
		questions.remove(i);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public ArrayList<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(ArrayList<Question> questions) {
		this.questions = questions;
	}
	
	public void byteArrayToImg() throws IOException{
		for(Question q: questions){
			q.byteArrayToImg();
			q.getVarA().byteArrayToImg();
			q.getVarB().byteArrayToImg();
			q.getVarC().byteArrayToImg();
		}
	}
	
	public void shuffleVariants(){
		for(Question q: questions){
			q.setCorrectThenShuffle(1);
		}
	}
	
	public void showCorrectPercentage(){
		int a = 0;
		int b = 0;
		int c = 0;
		int total = questions.size();
		
		for(Question q: questions){
			if(q.getVarA().isCorrect()){
				a++;
			}
			if(q.getVarB().isCorrect()){
				b++;
			}
			if(q.getVarC().isCorrect()){
				c++;
			}
		}
		
		System.out.println((float)a/(float)total + " " + (float)b/(float)total + " " + (float)c/(float)total);
	}
	
	
	
	
	
}
