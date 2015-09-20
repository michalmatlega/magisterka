package model;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import com.itextpdf.text.DocumentException;

public class SuperBase implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	
	public void generateDocuments(ArrayList<QBase> tests) throws DocumentException, IOException{
		for(QBase qb: tests){
			OutputDocument.createDocumentAndCalque(qb);
		}
	}
	
	public void createSets(char setStartLetter, int count) throws DocumentException, IOException{
		ArrayList<QBase> res = new ArrayList<QBase>();
		
		for(int i = (int)setStartLetter; i < (int)setStartLetter + count; i++){
			res.add(createTest((char)i));
		}
		
		generateDocuments(res);
		
		//return res;
	}
	
	public QBase createTest(char setletter){
		
		QBase resbase = new QBase();
		
		resbase.setSetLetter(setletter);
		
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
		
		ArrayList<Integer> tmp2 = new ArrayList<Integer>();
		
		for(int i = 0; i < 3; i++){
			tmp2.add(i);
		}
		Collections.shuffle(tmp2);
		
		int div = (int) Math.round((float)resbase.getQuestions().size() / 3.0);
		
		
		
		for(int i = 0; i < resbase.getQuestions().size(); i++){
			if(i < div){
				resbase.getQuestions().get(tmp.get(i)).setCorrectThenShuffle(tmp2.get(0));
			}
			if(div <= i && i < div*2){
				resbase.getQuestions().get(tmp.get(i)).setCorrectThenShuffle(tmp2.get(1));
			}
			if(div*2 <= i){
				resbase.getQuestions().get(tmp.get(i)).setCorrectThenShuffle(tmp2.get(2));
			}
		}
		
		resbase.showCorrectPercentage();
		
		return resbase;
		
	}
}
