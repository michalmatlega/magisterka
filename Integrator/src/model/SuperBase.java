package model;

import java.util.ArrayList;

public class SuperBase {
	private ArrayList<QBase> qbcoll;

	public ArrayList<QBase> getQbcoll() {
		return qbcoll;
	}
	
	public SuperBase() {
		super();
		this.qbcoll = new ArrayList<QBase>();
	}

	public void setQbcoll(ArrayList<QBase> qbcoll) {
		this.qbcoll = qbcoll;
	}
	
	public void add(QBase qb){
		qbcoll.add(qb);
	}
	
	public void remove(int i){
		qbcoll.remove(i);
	}
	
	public int getAmountOfQuestions(){
		int res = 0;
		
		for(QBase qb: qbcoll){
			res += qb.getQuestions().size();
		}
		
		return res;
	}
	
	public QBase createTest(int count){
		int aoq = getAmountOfQuestions();
		for(QBase qb: qbcoll){
			int tmp = count * qb.getQuestions().size() / aoq;
			
		}
		return null;
	}
}
