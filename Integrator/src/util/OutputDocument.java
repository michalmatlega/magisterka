package util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import model.QBase;
import model.Question;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class OutputDocument {

	private static float CONTENT_INDENT = 30;
	private static float VARS_INDENT = 60;
	private static float SYMBOL_INDENT = 30;
	private static String LIST_SYMBOL = ")";
	
	private static float fixedHeightOfFooterTable;
	private static PdfPTable footerTable;

	static private class HeaderFooterPageEvent extends PdfPageEventHelper {
		private char letter;

		public HeaderFooterPageEvent(char letter) {
			super();
			this.letter = letter;
		}

		public void onStartPage(PdfWriter writer, Document document) {

		}

		public void onEndPage(PdfWriter writer, Document document) {
			Rectangle rect = writer.getBoxSize("art");

			ColumnText.showTextAligned(
					writer.getDirectContent(),
					Element.ALIGN_CENTER,
					new Phrase(new Phrase(
							letter
									+ "/"
									+ String.format("%d",
											writer.getCurrentPageNumber()))),
					rect.getRight(), rect.getBottom(), 0);
		}
	}

	static private class HeaderFooterPageForEntireBaseEvent extends PdfPageEventHelper {

		public void onStartPage(PdfWriter writer, Document document) {

		}

		public void onEndPage(PdfWriter writer, Document document) {
			Rectangle rect = writer.getBoxSize("art");
			ColumnText.showTextAligned(writer.getDirectContent(),
					Element.ALIGN_CENTER, new Phrase(new Phrase(

					String.format("%d", writer.getCurrentPageNumber()))),
					rect.getRight(), rect.getBottom(), 0);
		}
	}

	public static void createDocuments(QBase qb) throws DocumentException,
			IOException {
		createQuestionsCard(qb);
		createCalque(qb);
	}

	private static void createQuestionsCard(QBase qb) throws DocumentException,
			IOException {

		Document document = new Document();
		PdfWriter writer = PdfWriter.getInstance(document,
				new FileOutputStream(qb.getProfile() + "_zestawpytan_" + qb.getLetterOfSet() + ".pdf"));
		Rectangle rect = new Rectangle(30, 30, 550, 800);
		writer.setBoxSize("art", rect);
		HeaderFooterPageEvent event = new HeaderFooterPageEvent(
				qb.getLetterOfSet());
		writer.setPageEvent(event);
		document.open();
		BaseFont bf = BaseFont.createFont("Arial Unicode MS.ttf",
				BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

		System.out.println(document.getPageSize());

		Font font = new Font(bf, 12);

		Paragraph title = new Paragraph(
				"Pytania na testowy egzamin kierunkowy", font);
		title.setAlignment(Element.ALIGN_CENTER);
		document.add(title);

		Paragraph subtitle = new Paragraph("kierunek: " + qb.getProfile()
				+ ", zestaw: " + qb.getLetterOfSet(), font);
		subtitle.setAlignment(Element.ALIGN_CENTER);
		document.add(subtitle);

		document.add(Chunk.NEWLINE);

		printQuestions(qb, document, font);

		generateAnswersOnEndOfDocument(document, qb, font);

		document.close();
	}

	private static void generateAnswersOnEndOfDocument(Document document,
			QBase qb, Font font) throws DocumentException {
		document.newPage();

		font.setSize(8);

		Paragraph paragraph = new Paragraph("Poprawne odpowiedzi:", font);
		document.add(paragraph);
		document.add(Chunk.NEWLINE);

		List correctAnswersOnEndOfDocument = new List(List.ORDERED);

		for (Question q : qb.getQuestions()) {
			ListItem answerItem = new ListItem(q.getCorrectLetter() + ")", font);
			correctAnswersOnEndOfDocument.add(answerItem);
		}

		document.add(correctAnswersOnEndOfDocument);
	}

	private static void createCalque(QBase qb) throws DocumentException,
			IOException {
		Document document = new Document();
	
		PdfWriter writer = PdfWriter.getInstance(document,
				new FileOutputStream(qb.getProfile() + "_kalka_" + qb.getLetterOfSet() + ".pdf"));
	
		document.open();
		document.setMargins(50, 50, 50, 180);

		PdfPTable table = new PdfPTable(4);
		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell cell = new PdfPCell();
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("A"));
		table.addCell(cell);
		cell = new PdfPCell(new Phrase("B"));
		table.addCell(cell);
		cell = new PdfPCell(new Phrase("C"));
		table.addCell(cell);

		table.setHeaderRows(1);

		int i = 1;
		for (Question q : qb.getQuestions()) {
			cell = new PdfPCell(new Phrase(Integer.toString(i)));
			table.addCell(cell);

			cell = new PdfPCell();
			if (!q.getVarA().isCorrect())
				cell.setBackgroundColor(new BaseColor(0, 0, 0));
			table.addCell(cell);

			cell = new PdfPCell();
			if (!q.getVarB().isCorrect())
				cell.setBackgroundColor(new BaseColor(0, 0, 0));
			table.addCell(cell);

			cell = new PdfPCell();
			if (!q.getVarC().isCorrect())
				cell.setBackgroundColor(new BaseColor(0, 0, 0));
			table.addCell(cell);

			i++;
		}

		ColumnText column = new ColumnText(writer.getDirectContent());

		float[][] x = { { document.left(), document.left() + 250 },
				{ document.right() - 250, document.right() } };

		column.addElement(table);
		int count = 0;
		float height = 0;
		int status = ColumnText.START_COLUMN;
		while (ColumnText.hasMoreText(status)) {
			if (count == 0) {
				addCalqueHeaderTable(document, qb);
				height = 92;
			}
			column.setSimpleColumn(x[count][0], document.bottom(), x[count][1],
					document.top() - height - 10);
			status = column.go();
			if (++count > 1) {
				count = 0;
				document.newPage();
			}
		}

		document.close();
	}

	static private float addHeaderTable(Document document, QBase qb)
			throws DocumentException, IOException {
		PdfPTable header = new PdfPTable(1);

		header.setWidthPercentage(100);
		header.getDefaultCell().setBackgroundColor(BaseColor.WHITE);

		BaseFont bf = BaseFont.createFont("Arial Unicode MS.ttf",
				BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		Font font = new Font(bf, 12);

		Phrase p = new Phrase("KARTA ODPOWIEDZI TESTU KIERUNKOWEGO", font);
		PdfPCell cell = new PdfPCell(p);
		cell.setBorder(Rectangle.NO_BORDER);
		header.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		header.addCell(cell);

		p = new Phrase("kierunek:  " + qb.getProfile(), font);

		cell = new PdfPCell(p);
		cell.setBorder(Rectangle.NO_BORDER);
		header.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		header.addCell(cell);

		p = new Phrase(
				"Zestaw:.................           Nr kodowy:..............",
				font);
		cell = new PdfPCell(p);
		cell.setBorder(Rectangle.NO_BORDER);
		header.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		header.addCell(cell);

		p = new Phrase(
				"Maksymalna liczba punkt�w dla potrzeb egzaminu kierunkowego oraz WKR: 100 pkt",
				font);
		cell = new PdfPCell(p);
		cell.setBorder(Rectangle.NO_BORDER);
		header.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		header.addCell(cell);

		p = new Phrase(
				"(Uzyskanie mniej ni\u017C 50 % mo\u017Cliwych punkt�w eliminuje studenta/kandydata z dalszego post\u0119powania)",
				font);
		cell = new PdfPCell(p);
		cell.setBorder(Rectangle.NO_BORDER);
		header.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		header.addCell(cell);

		document.add(header);
		return header.getTotalHeight();
	}
	
	static private float addCalqueHeaderTable(Document document, QBase qb)
			throws DocumentException, IOException {
		PdfPTable header = new PdfPTable(1);

		header.setWidthPercentage(100);
		header.getDefaultCell().setBackgroundColor(BaseColor.WHITE);

		BaseFont bf = BaseFont.createFont("Arial Unicode MS.ttf",
				BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		Font font = new Font(bf, 12);

		Phrase p = new Phrase("ZESTAW " + qb.getLetterOfSet(), font);
		PdfPCell cell = new PdfPCell(p);
		
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		header.addCell(cell);


		document.add(header);
		return header.getTotalHeight();
	}

	static public void createDocumentOfEntireBase(QBase qb) throws DocumentException,
			IOException {
	
		Document document = new Document();
	
		PdfWriter writer = PdfWriter.getInstance(document,
				new FileOutputStream("entireBase.pdf"));
		Rectangle rect = new Rectangle(30, 30, 550, 800);
		writer.setBoxSize("art", rect);
	
		HeaderFooterPageForEntireBaseEvent event = new HeaderFooterPageForEntireBaseEvent();
		writer.setPageEvent(event);
		document.open();
	
		BaseFont bf = BaseFont.createFont("Arial Unicode MS.ttf",
				BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

		System.out.println(document.getPageSize());

		Font font = new Font(bf, 12);

		Paragraph title = new Paragraph(
				"Pytania na testowy egzamin kierunkowy", font);
		title.setAlignment(Element.ALIGN_CENTER);
		document.add(title);

		Paragraph subtitle = new Paragraph("Kierunek: " + qb.getProfile(), font);
		subtitle.setAlignment(Element.ALIGN_CENTER);
		document.add(subtitle);
		document.add(Chunk.NEWLINE);

		printQuestions(qb, document, font);

		document.close();
	}

	private static void printQuestions(QBase qb, Document document, Font font)
			throws BadElementException, MalformedURLException, IOException,
			DocumentException {
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

			if (q.getVarC().getImgInByte() != null)	 {
				Image img = Image.getInstance(q.getVarC().getImgInByte());
				img.scalePercent((q.getVarC().getImgScalePercent()));
				varC.add(new ListItem(new Chunk(img, 0, 0, true)));
			}
			vars.add(varC);

			document.add(content);
			document.add(vars);
			document.add(Chunk.NEWLINE);


		}
	}

	public static void createAnswerCard(QBase qb) throws DocumentException,
			IOException {
		Document document = new Document();
		document.setMargins(50, 50, 50, 180);
	
		PdfWriter writer = PdfWriter.getInstance(document,
				new FileOutputStream(qb.getProfile() + "_kartaodpowiedzi.pdf"));
	
		document.open();
		
		float heightOfHeader = addHeaderTable(document, qb);
		
		int numberOfLastPage = getNumberOfLastPage(qb);
		int pageNumber = 1;

		PdfPTable table = new PdfPTable(4);
		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell cell = new PdfPCell();
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("A"));
		table.addCell(cell);
		cell = new PdfPCell(new Phrase("B"));
		table.addCell(cell);
		cell = new PdfPCell(new Phrase("C"));
		table.addCell(cell);

		table.setHeaderRows(1);

		for (int i = 0; i < qb.getQuestions().size(); i++) {
			cell = new PdfPCell(new Phrase(Integer.toString(i + 1)));
			table.addCell(cell);

			cell = new PdfPCell();

			table.addCell(cell);
			table.addCell(cell);
			table.addCell(cell);

		}

		ColumnText column = new ColumnText(writer.getDirectContent());

		float[][] x = { { document.left(), document.left() + 250 },
				{ document.right() - 250, document.right() } };

		column.addElement(table);
		int count = 0;
		float height = 0;
		boolean footerAdded = false;
		int status = ColumnText.START_COLUMN;
		while (ColumnText.hasMoreText(status)) {
			if(pageNumber == numberOfLastPage && !footerAdded){
				OutputDocument.addFooterTable(writer);
				footerAdded = true;
			}
			if (count == 0) {
				height = heightOfHeader;
			}
			column.setSimpleColumn(x[count][0], document.bottom(), x[count][1],
					document.top() - height - 10);
			status = column.go();
			if (++count > 1) {
				count = 0;
				pageNumber++;
				
				document.newPage();
			}
		}

		

		document.close();
	}
	
	private static int getNumberOfLastPage(QBase qb){
		int rowsOnPage = 60;
		int amountOfQuestions = qb.getQuestions().size();
		
		return amountOfQuestions / rowsOnPage + 1;
	}

	private static void addFooterTable(PdfWriter writer) throws DocumentException, IOException {
		footerTable = new PdfPTable(3);
		PdfContentByte canvas = writer.getDirectContent();
		
		BaseFont bf = BaseFont.createFont("Arial Unicode MS.ttf",
				BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		Font font = new Font(bf, 10);
		Font fontSmall = new Font(bf, 8);
		
		fixedHeightOfFooterTable = 30f;
		
		footerTable.setTotalWidth(495);
		
		footerTable.setLockedWidth(true);
		footerTable.setWidths(new float[]{2, 1, 1});
		
		addFooterTableCell("Suma poprawnych odpowiedzi (1 sprawdzaj\u0105cy)", font, Element.ALIGN_MIDDLE);
		addFooterTableEmptyCell();
		addFooterTableCell("Podpis sprawdzaj\u0105cego", fontSmall, Element.ALIGN_BOTTOM);
		
		addFooterTableCell("Suma poprawnych odpowiedzi (2 sprawdzaj\u0105cy)", font, Element.ALIGN_MIDDLE);
		addFooterTableEmptyCell();
		addFooterTableCell("Podpis sprawdzaj\u0105cego", fontSmall, Element.ALIGN_BOTTOM);
		
		addFooterTableCell("WYNIK KO\u0143COWY", font, Element.ALIGN_MIDDLE);
		addFooterTableEmptyCell();
		addFooterTableCell("Podpis oceniaj\u0105cego", fontSmall, Element.ALIGN_BOTTOM);
		
		addFooterTableCell("UZYSKANA LICZBA PUNKT�W\n(wynik ko�cowy pomno�ony przez 2)", font, Element.ALIGN_MIDDLE);
		addFooterTableEmptyCell();
		addFooterTableCell("Podpis oceniaj\u0105cego", fontSmall, Element.ALIGN_BOTTOM);

		footerTable.writeSelectedRows(0, -1, 50, footerTable.getTotalHeight() + 38, canvas);

	}
	
	private static void addFooterTableCell(String content, Font font, int verticalAlign){
		PdfPCell cell;
		cell = new PdfPCell(new Phrase(
				content, font));
		cell.setFixedHeight(fixedHeightOfFooterTable);
		cell.setVerticalAlignment(verticalAlign);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		footerTable.addCell(cell);
	}
	
	private static void addFooterTableEmptyCell(){
		PdfPCell cell = new PdfPCell();
		footerTable.addCell(cell);
	}
}
