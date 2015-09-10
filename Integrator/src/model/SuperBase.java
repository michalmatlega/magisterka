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
	
	public QBase createTest(int sets){
		
//		int totalaoq = getAmountOfQuestions();
//		if(count > totalaoq){
//			System.out.println("Too many questions requested");
//			return null;
//		}
//		
//		if(count < qbcoll.size()){
//			System.out.println("Too few questions in base");
//			return null;
//		}
//		
//		QBase res = new QBase();
//		
//		int rest = count % qbcoll.size();				//reszta z ilosci pytan na baze
//		
//		ArrayList<Integer> restbasesrnd = new ArrayList<Integer>();			// tablica losujaca jest pusta, nastepuje zwolnienie blokady
//		//ArrayList<Integer> restbases = new ArrayList<Integer>();
//		
//		for(int i = 0; i < qbcoll.size(); i++){
//			restbasesrnd.add(i);										
//		}
//		
//		Collections.shuffle(restbasesrnd);						//rozpoczynamy losowanie baz, ktore dostana dodatkowe pytania z reszty
//		
//		for(int i = rest; i < restbasesrnd.size();){
//			restbasesrnd.remove(i);								//wylosowane indeksy sa pierwszymi w tabeli, reszte wywalamy
//		}
//		
//		//for(int i = 0; i < rest; i++){
//		//	restbases.add(restbasesrnd.get(i));
//		//}
//		
//		for(int j = 0; j < qbcoll.size(); j++){
//			int tmp = count * qbcoll.get(j).getQuestions().size() / totalaoq;	//ilosc pytan z pojedynczej bazy do testu
//			
//			if(restbasesrnd.contains(j)){
//				   tmp++;													//dodajemy jedno pytanie z reszty
//			}
//			
//			System.out.println(tmp);
//			ArrayList<Integer> rndtab = new ArrayList<Integer>();
//			for(int i = 0; i < qbcoll.get(j).getQuestions().size(); i++){			//cos trzeba z reszta zrobic
//				rndtab.add(i);
//			}
//			Collections.shuffle(rndtab);
//			for(int i = 0; i < tmp; i++){
//				res.getQuestions().add(qbcoll.get(j).getQuestions().get(rndtab.get(i)));
//			}
//			
//		}
//		
//		ArrayList<QBase>restab = new ArrayList<QBase>();
//		
//		
//		for(int i = 0; i < groups; i++){				//tworzenie grup
//			res.shuffleVariants();
//			restab.add(res);
//		}
//		
//		
//		System.out.println("-------------------------------------------------------------------------------");
//		
//		return restab;
		
		QBase resbase = new QBase();
		
		for(QBase qb: qbcoll){
			
			ArrayList<Integer> tmp = new ArrayList<Integer>();
			
			for(int i = 0; i < qb.getQuestions().size(); i++){
				tmp.add(i);
			}
			Collections.shuffle(tmp);
			
			for(int i = 0; i < qb.getAmountToTest(); i++){
				resbase.getQuestions().add(qb.getQuestions().get(tmp.get(i)));	//dodawanie losowo pytan z danej bazy do bazy testowej
			}
		}
		
		ArrayList<Integer> tmp = new ArrayList<Integer>();
		
		for(int i = 0; i < resbase.getQuestions().size(); i++){
			tmp.add(i);
		}
		Collections.shuffle(tmp);
		
		int div = (int) Math.round((float)resbase.getQuestions().size() / 3.0);
		
		for(int i = 0; i < resbase.getQuestions().size(); i++){
			if(i < div){
				resbase.getQuestions().get(tmp.get(i)).setCorrectThenShuffle(0);
			}
			if(div <= i && i < div*2){
				resbase.getQuestions().get(tmp.get(i)).setCorrectThenShuffle(1);
			}
			if(div*2 <= i){
				resbase.getQuestions().get(tmp.get(i)).setCorrectThenShuffle(2);
			}
		}
		
		resbase.showCorrectPercentage();
		
		return resbase;
		
	}
}
