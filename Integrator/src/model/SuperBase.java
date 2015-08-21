package model;

import java.util.ArrayList;
import java.util.Collections;

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
	
	public ArrayList<QBase> createTest(int count, int groups){
		
		int totalaoq = getAmountOfQuestions();
		if(count > totalaoq){
			System.out.println("Too many questions");
			return null;
		}
		
		if(count < qbcoll.size()){
			System.out.println();
		}
		
		QBase res = new QBase();
		
		for(QBase qb: qbcoll){
			int tmp = count * qb.getQuestions().size() / totalaoq;			//ilosc pytan z pojedynczej bazy do testu
			System.out.println(tmp);
			ArrayList<Integer> rndtab = new ArrayList<Integer>();
			for(int i = 0; i < qb.getQuestions().size(); i++){			//cos trzeba z reszta zrobic
				rndtab.add(i);
			}
			Collections.shuffle(rndtab);
			for(int i = 0; i < tmp; i++){
				res.getQuestions().add(qb.getQuestions().get(rndtab.get(i)));
			}
			
		}
		
		ArrayList<QBase>restab = new ArrayList<QBase>();
		
		
		for(int i = 0; i < groups; i++){
			res.shuffleVariants();
			restab.add(res);
		}
		
		
		
		
		return restab;
	}
}
