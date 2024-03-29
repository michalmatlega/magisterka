package util;

import java.io.FileOutputStream;
import java.io.IOException;

import model.QBase;
import model.Question;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

public class OutputDocument {
	private static float CONTENT_INDENT = 30;
	private static float VARS_INDENT = 60;
	private static float SYMBOL_INDENT = 30;
	private static String LIST_SYMBOL = ")";
	
	public static void createDocument(QBase qb) throws DocumentException,
			IOException {


		Document document = new Document();

		PdfWriter.getInstance(document, new FileOutputStream("creatorPreviewEntireBase.pdf"));

		document.open();

		BaseFont bf = BaseFont.createFont("Arial Unicode MS.ttf",
				BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

		Font font = new Font(bf, 12);
		
		Paragraph title = new Paragraph("Podgl\u0105d testowy", font);
		title.setAlignment(Element.ALIGN_CENTER);
		
		document.add(title);
		document.add(Chunk.NEWLINE);
		
		int questionNumber = 1;
		
		for (Question q : qb.getQuestions()) {

			List content = new List(List.ORDERED);
			content.setFirst(questionNumber);
			questionNumber++;
			content.setAutoindent(false);
			content.setSymbolIndent(SYMBOL_INDENT);

			content.setIndentationLeft(CONTENT_INDENT);

			ListItem contentItem = new ListItem(q.getContent(), font);

			if (q.getImgInByte() != null) {
				Image img = Image.getInstance(q.getImgInByte());
				img.scalePercent((q.getImgScalePercent()));
				contentItem.add(new ListItem(new Chunk(img, 0, 0, true)));
			}

			content.add(contentItem);

			List vars = new List(List.ORDERED, List.ALPHABETICAL);
			vars.setListSymbol(LIST_SYMBOL);
			vars.setAutoindent(false);
			vars.setIndentationLeft(VARS_INDENT);
			vars.setSymbolIndent(SYMBOL_INDENT);
			vars.setLowercase(true);

			ListItem varA = new ListItem(q.getVarA().getContent(), font);

			varA.setListSymbol(new Chunk("a)"));

			if (q.getVarA().getImgInByte() != null) {
				Image img = Image.getInstance(q.getVarA().getImgInByte());
				img.scalePercent((q.getVarA().getImgScalePercent()));
				varA.add(new ListItem(new Chunk(img, 0, 0, true)));
			}
			vars.add(varA);

			ListItem varB = new ListItem(q.getVarB().getContent(), font);
			varB.setListSymbol(new Chunk("b)"));

			if (q.getVarB().getImgInByte() != null) {
				Image img = Image.getInstance(q.getVarB().getImgInByte());
				img.scalePercent((q.getVarB().getImgScalePercent()));
				varB.add(new ListItem(new Chunk(img, 0, 0, true)));
			}
			vars.add(varB);

			ListItem varC = new ListItem(q.getVarC().getContent(), font);
			varC.setListSymbol(new Chunk("c)"));

			if (q.getVarC().getImgInByte() != null) {
				Image img = Image.getInstance(q.getVarC().getImgInByte());
				img.scalePercent((q.getVarC().getImgScalePercent()));
				varC.add(new ListItem(new Chunk(img, 0, 0, true)));
			}
			vars.add(varC);

			document.add(content);
			document.add(vars);
			document.add(Chunk.NEWLINE);


		}

		document.close();
	}
	
	public static void previewSingleQuestion(Question q) throws DocumentException, IOException{

				Document document = new Document();

				PdfWriter.getInstance(document, new FileOutputStream("creatorPreviewSingleQuestion.pdf"));

				document.open();

				BaseFont bf = BaseFont.createFont("Arial Unicode MS.ttf",
						BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

				Font font = new Font(bf, 12);
				
				Paragraph title = new Paragraph("Podgl\u0105d testowy", font);
				title.setAlignment(Element.ALIGN_CENTER);
				
				document.add(title);
				document.add(Chunk.NEWLINE);
				
				
				
				

				List content = new List(List.ORDERED);
				content.setAutoindent(false);
				content.setSymbolIndent(SYMBOL_INDENT);

				content.setIndentationLeft(CONTENT_INDENT);

				ListItem contentItem = new ListItem(q.getContent(), font);

				if (q.getImgInByte() != null) {
					Image img = Image.getInstance(q.getImgInByte());
					img.scalePercent((q.getImgScalePercent()));
					contentItem.add(new ListItem(new Chunk(img, 0, 0, true)));
				} 
					
				content.add(contentItem);

				List vars = new List(List.ORDERED, List.ALPHABETICAL);
				vars.setListSymbol(LIST_SYMBOL);
				vars.setAutoindent(false);
				vars.setIndentationLeft(VARS_INDENT);
				vars.setSymbolIndent(SYMBOL_INDENT);
				vars.setLowercase(true);

				ListItem varA = new ListItem(q.getVarA().getContent(), font);

				varA.setListSymbol(new Chunk("a)"));

				if (q.getVarA().getImgInByte() != null) {
					Image img = Image.getInstance(q.getVarA().getImgInByte());
					img.scalePercent((q.getVarA().getImgScalePercent()));
					varA.add(new ListItem(new Chunk(img, 0, 0, true)));
				}
				vars.add(varA);

				ListItem varB = new ListItem(q.getVarB().getContent(), font);
				varB.setListSymbol(new Chunk("b)"));

				if (q.getVarB().getImgInByte() != null) {
					Image img = Image.getInstance(q.getVarB().getImgInByte());
					img.scalePercent((q.getVarB().getImgScalePercent()));
					varB.add(new ListItem(new Chunk(img, 0, 0, true)));
				}
				vars.add(varB);

				ListItem varC = new ListItem(q.getVarC().getContent(), font);
				varC.setListSymbol(new Chunk("c)"));

				if (q.getVarC().getImgInByte() != null) {
					Image img = Image.getInstance(q.getVarC().getImgInByte());
					img.scalePercent((q.getVarC().getImgScalePercent()));
					varC.add(new ListItem(new Chunk(img, 0, 0, true)));
				}
				vars.add(varC);

				document.add(content);
				document.add(vars);
				document.add(Chunk.NEWLINE);

				document.close();
	}

}
