package model;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;


public class Question implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String content;
	private QuestionVariant varA;
	private QuestionVariant varB;
	private QuestionVariant varC;
	private int correct;			//1 - A, 2 - B, 3 - C
	
	transient private BufferedImage imgContent;
	private byte[] imgInByte;
	
	
	public byte[] getImgInByte() {
		return imgInByte;
	}


	public void setImgInByte(byte[] imgInByte) {
		this.imgInByte = imgInByte;
	}


	public Question() {
		super();
		this.content = new String();
		this.varA = new QuestionVariant();
		this.varB = new QuestionVariant();
		this.varC = new QuestionVariant();
		this.correct = 1;
		
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
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


	public BufferedImage getImgContent() {
		return imgContent;
	}


	public void setImgContent(BufferedImage imgContent) {
		this.imgContent = imgContent;
	}
	
	public void byteArrayToImg() throws IOException{
		if(imgInByte != null){
			imgContent = ImageIO.read(new ByteArrayInputStream(imgInByte));
		}
	}
	
	
}
