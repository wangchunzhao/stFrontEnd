package com.qhc.steigenberger.util;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.converter.core.XWPFConverterException;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFonts;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTInd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSpacing;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;

public class XwpfUtil {
	public static Map<Boolean, String> map = new HashMap<>();
	static {
		map.put(Boolean.valueOf(true), "■");
		map.put(Boolean.valueOf(false), "□");
	}

	public boolean export(XWPFDocument doc, List<Map<String, String>> params, Map<String, Object> paramSum,
			int tableIndex) throws Exception {
		return export(doc, params, paramSum, tableIndex, Boolean.valueOf(false));
	}

	public boolean export(XWPFDocument doc, List<Map<String, String>> params, Map<String, Object> paramSum,
			int tableIndex, Boolean hasTotalRow) throws Exception {
		insertValueToTable(doc, params, paramSum, tableIndex, hasTotalRow);
		return true;
	}

	public void replaceInPara(XWPFDocument doc, Map<String, Object> params) throws Exception {
		Iterator<XWPFParagraph> iterator = doc.getParagraphsIterator();

		while (iterator.hasNext()) {
			XWPFParagraph para = iterator.next();
			replaceInPara(para, params);
		}
	}

	public void replaceInPara(XWPFParagraph para, Map<String, Object> params) throws Exception {
		if (matcher(para.getParagraphText()).find()) {
			List<XWPFRun> runs = para.getRuns();
			Map<Integer, String> tempMap = new HashMap<>();
			int start = -1;

			String str = "";
			for (int i = 0; i < runs.size(); i++) {
				XWPFRun run = runs.get(i);
				String runText = run.toString();

				boolean begin = (runText.indexOf("$") > -1);
				boolean end = (runText.indexOf("}") > -1);

				if (begin && end) {
					tempMap.put(Integer.valueOf(i), runText);
					fillBlock(para, params, tempMap, i);
				} else if (begin && !end) {
					tempMap.put(Integer.valueOf(i), runText);
				} else if (!begin && end) {
					tempMap.put(Integer.valueOf(i), runText);
					fillBlock(para, params, tempMap, i);

				} else if (tempMap.size() > 0) {
					tempMap.put(Integer.valueOf(i), runText);
				}

			}

		} else if (matcherRow(para.getParagraphText())) {
			List list = para.getRuns();
		}
	}

	private boolean matcherRow(String str) {
		Pattern pattern = Pattern.compile("\\$\\[(.+?)\\]", 2);
		Matcher matcher = pattern.matcher(str);
		return matcher.find();
	}

	private void fillBlock(XWPFParagraph para, Map<String, Object> params, Map<Integer, String> tempMap, int index)
			throws InvalidFormatException, IOException, Exception {
		if (tempMap != null && tempMap.size() > 0) {
			String wholeText = "";
			List<Integer> tempIndexList = new ArrayList<>();
			for (Map.Entry<Integer, String> entry : tempMap.entrySet()) {
				tempIndexList.add(entry.getKey());

				wholeText = wholeText + (String) entry.getValue();
			}

			if (wholeText.equals("")) {
				return;
			}
			Matcher matcher = matcher(wholeText);
			if (matcher.find()) {
				String path = null;
				String keyText = matcher.group().substring(2, matcher.group().length() - 1);

				Object value = params.get(keyText);
				String newRunText = "";
				if (value instanceof Object) {
					newRunText = matcher.replaceFirst(String.valueOf(value));
				}

				XWPFRun tempRun = null;

				for (Integer pos : tempIndexList) {
					tempRun = para.getRuns().get(pos.intValue());
					tempRun.setText("", 0);
				}

				if (newRunText.indexOf("\n") > -1) {
					String[] textArr = newRunText.split("\n");
					if (textArr.length > 0) {

						String fontFamily = tempRun.getFontFamily();
						int fontSize = tempRun.getFontSize();

						for (int i = 0; i < textArr.length; i++) {

							if (i == 0) {

								tempRun.setText(textArr[0], 0);

							} else if (textArr[i] != null) {
								XWPFRun newRun = para.createRun();

								newRun.addBreak();

								newRun.setText(textArr[i], 0);
							}

						}
					}
				} else {

					tempRun.setText(newRunText, 0);
				}
			}

			tempMap.clear();
		}
	}

	public void replaceInTable(XWPFTable table, Map<String, Object> params) throws Exception {
		List<XWPFTableRow> rows = table.getRows();
		for (XWPFTableRow row : rows) {
			List<XWPFTableCell> cells = row.getTableCells();
			for (XWPFTableCell cell : cells) {
				List<XWPFParagraph> paras = cell.getParagraphs();
				for (XWPFParagraph para : paras) {
					replaceInPara(para, params);
				}
			}
		}
	}

	private Matcher matcher(String str) {
		Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}", 2);
		Matcher matcher = pattern.matcher(str);
		return matcher;
	}

	public void close(InputStream is) {
		if (is != null) {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void close(OutputStream os) {
		if (os != null) {
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void insertValueToTable(XWPFDocument doc, List<Map<String, String>> params, Map<String, Object> paramSum,
			int tableIndex, Boolean hasTotalRow) throws Exception {
		List<XWPFTable> tableList = doc.getTables();
		if (tableList.size() <= tableIndex) {
			throw new Exception("tableIndex对应的表格不存在");
		}
		XWPFTable table = tableList.get(tableIndex);
		List<XWPFTableRow> rows = table.getRows();
		if (rows.size() < 2) {
			throw new Exception("tableIndex对应表格应该为2行");
		}

		XWPFTableRow tmpRow = rows.get(1);
		List<XWPFTableCell> tmpCells = null;
		List<XWPFTableCell> cells = null;
		XWPFTableCell tmpCell = null;

		tmpCells = tmpRow.getTableCells();
		String cellText = null;
		String cellTextKey = null;

		Map<String, Object> totalMap = null;

		for (int i = 0, len = params.size(); i < len; i++) {
			Map<String, String> map = params.get(i);
			if (map.containsKey("total")) {
				totalMap = new HashMap<>();
				totalMap.put("total", map.get("total"));

			} else {

				XWPFTableRow row = table.createRow();
				row.setHeight(tmpRow.getHeight());
				cells = row.getTableCells();

				for (int k = 0, klen = cells.size(); k < klen; k++) {
					tmpCell = tmpCells.get(k);
					XWPFTableCell cell = cells.get(k);
					cellText = tmpCell.getText();
					if (cellText != null && !"".equals(cellText)) {

						cellTextKey = cellText.replace("$", "").replace("{", "").replace("}", "");
						if (map.containsKey(cellTextKey)) {
							setCellText(tmpCell, cell, map.get(cellTextKey));
						}
					}
				}
				table.addRow(row, 2);
				table.removeRow(table.getRows().size() - 1);
			}
		}
		table.removeRow(1);
		XWPFTableRow B2cRow = table.getRow(table.getRows().size() - 5);
		XWPFTableRow transportFeeRow = table.getRow(table.getRows().size() - 4);
		XWPFTableRow optionAmountRow = table.getRow(table.getRows().size() - 3);
		XWPFTableRow sumRow = table.getRow(table.getRows().size() - 2);
		XWPFTableRow sumCNRow = table.getRow(table.getRows().size() - 1);
		List<XWPFTableRow> listRow = new ArrayList<>();
		listRow.add(B2cRow);
		listRow.add(transportFeeRow);
		listRow.add(optionAmountRow);
		listRow.add(sumRow);
		listRow.add(sumCNRow);
		replaceInRow(listRow, paramSum);
		if (hasTotalRow.booleanValue() && totalMap != null) {
			XWPFTableRow row = table.getRow(1);

			XWPFTableCell cell = row.getCell(0);
			replaceInPara(cell.getParagraphs().get(0), totalMap);

			table.addRow(row);
			table.removeRow(1);
		}
	}

	public void updateValueToTable(XWPFDocument doc, Map<String, Object> params, int tableIndex) throws Exception {
		List<XWPFTable> tableList = doc.getTables();
		if (tableList.size() <= tableIndex) {
			throw new Exception("tableIndex对应的表格不存在");
		}
		XWPFTable table = tableList.get(tableIndex);
		List<XWPFTableRow> rows = table.getRows();
		if (rows.size() < 2) {
			throw new Exception("tableIndex对应表格应该为2行");
		}
		replaceInTable(table, params);
	}

	public void replaceInRow(List<XWPFTableRow> rows, Map<String, Object> params) throws Exception {
		for (XWPFTableRow row : rows) {
			List<XWPFTableCell> cells = row.getTableCells();
			for (XWPFTableCell cell : cells) {
				List<XWPFParagraph> paras = cell.getParagraphs();
				for (XWPFParagraph para : paras) {
					replaceInPara(para, params);
				}
			}
		}
	}

	private void setCellText(XWPFTableCell tmpCell, XWPFTableCell cell, String text) throws Exception {
		CTTc cttc2 = tmpCell.getCTTc();
		CTTcPr ctPr2 = cttc2.getTcPr();

		CTTc cttc = cell.getCTTc();
		CTTcPr ctPr = cttc.addNewTcPr();

		if (ctPr2.getTcW() != null) {
			ctPr.addNewTcW().setW(ctPr2.getTcW().getW());
		}
		if (ctPr2.getVAlign() != null) {
			ctPr.addNewVAlign().setVal(ctPr2.getVAlign().getVal());
		}
		if (cttc2.getPList().size() > 0) {
			CTP ctp = cttc2.getPList().get(0);
			if (ctp.getPPr() != null && ctp.getPPr().getJc() != null) {
				((CTP) cttc.getPList().get(0)).addNewPPr().addNewJc().setVal(ctp.getPPr().getJc().getVal());
			}
		}

		if (ctPr2.getTcBorders() != null) {
			ctPr.setTcBorders(ctPr2.getTcBorders());
		}

		XWPFParagraph tmpP = tmpCell.getParagraphs().get(0);
		XWPFParagraph cellP = cell.getParagraphs().get(0);
		XWPFRun tmpR = null;
		if (tmpP.getRuns() != null && tmpP.getRuns().size() > 0) {
			tmpR = tmpP.getRuns().get(0);
		}

		List<XWPFRun> runList = new ArrayList<>();
		if (text == null) {
			XWPFRun cellR = cellP.createRun();
			runList.add(cellR);
			cellR.setText("");

		} else if (text.indexOf("\b") > -1) {
			String[] bArr = text.split("\b");
			for (int b = 0; b < bArr.length; b++) {
				XWPFRun cellR = cellP.createRun();
				runList.add(cellR);
				if (b == 0) {
					cellR.setBold(true);
				}
				if (bArr[b].indexOf("\n") > -1) {
					String[] arr = bArr[b].split("\n");
					for (int i = 0; i < arr.length; i++) {
						if (i > 0) {
							cellR.addBreak();
						}
						cellR.setText(arr[i]);
					}
				} else {
					cellR.setText(bArr[b]);
				}
			}
		} else {
			XWPFRun cellR = cellP.createRun();
			runList.add(cellR);
			if (text.indexOf("\n") > -1) {
				String[] arr = text.split("\n");
				for (int i = 0; i < arr.length; i++) {
					if (i > 0) {
						cellR.addBreak();
					}
					cellR.setText(arr[i]);
				}
			} else {
				cellR.setText(text);
			}
		}

		if (tmpR != null) {

			for (XWPFRun cellR : runList) {
				if (!cellR.isBold()) {
					cellR.setBold(tmpR.isBold());
				}
				cellR.setItalic(tmpR.isItalic());
				cellR.setStrike(tmpR.isStrike());
				cellR.setUnderline(tmpR.getUnderline());
				cellR.setColor(tmpR.getColor());
				cellR.setTextPosition(tmpR.getTextPosition());
				if (tmpR.getFontSize() != -1) {
					cellR.setFontSize(tmpR.getFontSize());
				}
				if (tmpR.getFontFamily() != null) {
					cellR.setFontFamily(tmpR.getFontFamily());
				}
				if (tmpR.getCTR() != null && tmpR.getCTR().isSetRPr()) {
					CTRPr tmpRPr = tmpR.getCTR().getRPr();
					if (tmpRPr.isSetRFonts()) {
						CTFonts tmpFonts = tmpRPr.getRFonts();

						CTRPr cellRPr = cellR.getCTR().isSetRPr() ? cellR.getCTR().getRPr()
								: cellR.getCTR().addNewRPr();
						CTFonts cellFonts = cellRPr.isSetRFonts() ? cellRPr.getRFonts() : cellRPr.addNewRFonts();
						cellFonts.setAscii(tmpFonts.getAscii());
						cellFonts.setAsciiTheme(tmpFonts.getAsciiTheme());
						cellFonts.setCs(tmpFonts.getCs());
						cellFonts.setCstheme(tmpFonts.getCstheme());
						cellFonts.setEastAsia(tmpFonts.getEastAsia());
						cellFonts.setEastAsiaTheme(tmpFonts.getEastAsiaTheme());
						cellFonts.setHAnsi(tmpFonts.getHAnsi());
						cellFonts.setHAnsiTheme(tmpFonts.getHAnsiTheme());
					}
				}
			}
		}

		cellP.setAlignment(tmpP.getAlignment());
		cellP.setVerticalAlignment(tmpP.getVerticalAlignment());
		cellP.setBorderBetween(tmpP.getBorderBetween());
		cellP.setBorderBottom(tmpP.getBorderBottom());
		cellP.setBorderLeft(tmpP.getBorderLeft());
		cellP.setBorderRight(tmpP.getBorderRight());
		cellP.setBorderTop(tmpP.getBorderTop());
		cellP.setPageBreak(tmpP.isPageBreak());
		if (tmpP.getCTP() != null && tmpP.getCTP().getPPr() != null) {
			CTPPr tmpPPr = tmpP.getCTP().getPPr();
			CTPPr cellPPr = (cellP.getCTP().getPPr() != null) ? cellP.getCTP().getPPr() : cellP.getCTP().addNewPPr();

			CTSpacing tmpSpacing = tmpPPr.getSpacing();
			if (tmpSpacing != null) {

				CTSpacing cellSpacing = (cellPPr.getSpacing() != null) ? cellPPr.getSpacing() : cellPPr.addNewSpacing();
				if (tmpSpacing.getAfter() != null) {
					cellSpacing.setAfter(tmpSpacing.getAfter());
				}
				if (tmpSpacing.getAfterAutospacing() != null) {
					cellSpacing.setAfterAutospacing(tmpSpacing.getAfterAutospacing());
				}
				if (tmpSpacing.getAfterLines() != null) {
					cellSpacing.setAfterLines(tmpSpacing.getAfterLines());
				}
				if (tmpSpacing.getBefore() != null) {
					cellSpacing.setBefore(tmpSpacing.getBefore());
				}
				if (tmpSpacing.getBeforeAutospacing() != null) {
					cellSpacing.setBeforeAutospacing(tmpSpacing.getBeforeAutospacing());
				}
				if (tmpSpacing.getBeforeLines() != null) {
					cellSpacing.setBeforeLines(tmpSpacing.getBeforeLines());
				}
				if (tmpSpacing.getLine() != null) {
					cellSpacing.setLine(tmpSpacing.getLine());
				}
				if (tmpSpacing.getLineRule() != null) {
					cellSpacing.setLineRule(tmpSpacing.getLineRule());
				}
			}

			CTInd tmpInd = tmpPPr.getInd();
			if (tmpInd != null) {
				CTInd cellInd = (cellPPr.getInd() != null) ? cellPPr.getInd() : cellPPr.addNewInd();
				if (tmpInd.getFirstLine() != null) {
					cellInd.setFirstLine(tmpInd.getFirstLine());
				}
				if (tmpInd.getFirstLineChars() != null) {
					cellInd.setFirstLineChars(tmpInd.getFirstLineChars());
				}
				if (tmpInd.getHanging() != null) {
					cellInd.setHanging(tmpInd.getHanging());
				}
				if (tmpInd.getHangingChars() != null) {
					cellInd.setHangingChars(tmpInd.getHangingChars());
				}
				if (tmpInd.getLeft() != null) {
					cellInd.setLeft(tmpInd.getLeft());
				}
				if (tmpInd.getLeftChars() != null) {
					cellInd.setLeftChars(tmpInd.getLeftChars());
				}
				if (tmpInd.getRight() != null) {
					cellInd.setRight(tmpInd.getRight());
				}
				if (tmpInd.getRightChars() != null) {
					cellInd.setRightChars(tmpInd.getRightChars());
				}
			}
		}
	}

	public void transferToPDF(InputStream doc, OutputStream out) {
		XWPFDocument document = null;
		try {
			document = new XWPFDocument(doc);
		} catch (IOException e1) {

			e1.printStackTrace();
		}

		PdfOptions options = PdfOptions.create();
		try {
			PdfConverter.getInstance().convert(document, out, options);
		} catch (XWPFConverterException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
}
