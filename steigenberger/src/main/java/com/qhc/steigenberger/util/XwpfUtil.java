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
import org.apache.poi.xwpf.usermodel.PositionInParagraph;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTInline;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFonts;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTInd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSpacing;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTheme;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTheme.Enum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XwpfUtil {
	public static final Logger logger = LoggerFactory.getLogger(XwpfUtil.class);
	public static Map<Boolean, String> map = new HashMap<>();
	static {
		map.put(Boolean.valueOf(true), "■");
		map.put(Boolean.valueOf(false), "□");
	}

	public void replaceInPara(XWPFDocument doc, Map<String, Object> params) throws Exception {
		Iterator<XWPFParagraph> iterator = doc.getParagraphsIterator();

		while (iterator.hasNext()) {
			XWPFParagraph para = iterator.next();
//			replaceInPara(para, params);
			replaceParagraph(para, params);
		}
	}

	private void replaceInPara(XWPFParagraph para, Map<String, Object> params) throws Exception {
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
//					replaceInPara(para, params);
					replaceParagraph(para, params);
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

	public void exportTable1(XWPFDocument doc, List<Map<String, String>> params, Map<String, Object> paramSum,
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

					// 复制列及其属性和内容
					// 列属性
					cell.getCTTc().setTcPr(tmpCell.getCTTc().getTcPr());
					// 段落属性
					if (tmpCell.getParagraphs() != null && tmpCell.getParagraphs().size() > 0) {
						cell.getParagraphs().get(0).getCTP().setPPr(tmpCell.getParagraphs().get(0).getCTP().getPPr());
						if (tmpCell.getParagraphs().get(0).getRuns() != null
								&& tmpCell.getParagraphs().get(0).getRuns().size() > 0) {
							XWPFRun cellR = cell.getParagraphs().get(0).createRun();
							cellR.setText(tmpCell.getText());
							cellR.setBold(tmpCell.getParagraphs().get(0).getRuns().get(0).isBold());
						} else {
							cell.setText(tmpCell.getText());
						}
					} else {
						cell.setText(tmpCell.getText());
					}

					List<XWPFParagraph> paragraphs = cell.getParagraphs();
					for (XWPFParagraph paragraph : paragraphs) {
						// replaceInPara(paragraph, (Map) map);
						replaceParagraph(paragraph, (Map) map);
					}

//					cellText = cell.getText();
//					logger.info("cell text: " + cellText);
//					if (cellText != null && !"".equals(cellText)) {
//						int ls = cellText.indexOf("${");
//						int le = 0;
//						if (ls >= 0) {
//							le = cellText.indexOf("}", ls);
//							if (le > 0) {
//								String cellKey = cellText.substring(ls + 2, le);
//								String value = map.get(cellKey);
//								logger.info("key: " + cellKey);
//								logger.info("value: " + value);
//								value = value == null ? "" : value;
//								cellText = cellText.replace("${" + cellKey + "}", value);
//								logger.info("cell text: " + cellText);
//								cell.getParagraphs().get(0);
//								cell.setText(cellText);
//							}
//						}
//						cellTextKey = cellText.replace("$", "").replace("{", "").replace("}", "");
//						if (map.containsKey(cellTextKey)) {
					setCellText(tmpCell, cell, map.get(cellTextKey));
//							cell.setText(map.get(cellTextKey));
//						}
//					}
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
//			replaceInPara(cell.getParagraphs().get(0), totalMap);
			List<XWPFParagraph> paragraphs = cell.getParagraphs();
			for (XWPFParagraph paragraph : paragraphs) {
				replaceParagraph(paragraph, (Map) map);
			}

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
//					replaceInPara(para, params);
					replaceParagraph(para, params);
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void replaceParagraph(XWPFParagraph xWPFParagraph, Map<String, Object> parametersMap) {
		List<XWPFRun> runs = xWPFParagraph.getRuns();
		String xWPFParagraphText = xWPFParagraph.getText();
		String regEx = "\\$\\{.+?\\}";
		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher = pattern.matcher(xWPFParagraphText);// 正则匹配字符串${****}
		// parameter start tag size
		int pslen = 2;
		// parameter end tag size
		int pelen = 1;

		if (matcher.find()) {
			// 查找到有标签才执行替换
			int beginRunIndex = xWPFParagraph.searchText("${", new PositionInParagraph()).getBeginRun();// 标签开始run位置
			int endRunIndex = xWPFParagraph.searchText("}", new PositionInParagraph()).getEndRun();// 结束标签
			StringBuffer key = new StringBuffer();

			if (beginRunIndex == endRunIndex) {
				// {**}在一个run标签内
				XWPFRun beginRun = runs.get(beginRunIndex);
				String beginRunText = beginRun.text();

				int beginIndex = beginRunText.indexOf("${");
				int endIndex = beginRunText.indexOf("}");
				int length = beginRunText.length();

				if (beginIndex == 0 && endIndex == length - 1) {
					// 该run标签只有${**}
					XWPFRun insertNewRun = xWPFParagraph.insertNewRun(beginRunIndex);
					insertNewRun.getCTR().setRPr(beginRun.getCTR().getRPr());
					// 设置文本
					key.append(beginRunText.substring(pslen, endIndex));
					Object value = parametersMap.get(key.toString());
					String v = value == null ? "" : value.toString();
					insertNewRun.setText(v);
					xWPFParagraph.removeRun(beginRunIndex + 1);
				} else {
					// 该run标签为**{**}** 或者 **{**} 或者{**}**，替换key后，还需要加上原始key前后的文本
					XWPFRun insertNewRun = xWPFParagraph.insertNewRun(beginRunIndex);
					insertNewRun.getCTR().setRPr(beginRun.getCTR().getRPr());
					// 设置文本
					key.append(beginRunText.substring(beginRunText.indexOf("${") + pslen, beginRunText.indexOf("}")));
					Object value = parametersMap.get(key.toString());
					String v = value == null ? "" : value.toString();
					String textString = beginRunText.substring(0, beginIndex)
							+ v + beginRunText.substring(endIndex + pslen);

					insertNewRun.setText(textString);
					xWPFParagraph.removeRun(beginRunIndex + 1);
				}

			} else {
				// {**}被分成多个run

				// 先处理起始run标签,取得第一个{key}值
				XWPFRun beginRun = runs.get(beginRunIndex);
				String beginRunText = beginRun.text();
				int beginIndex = beginRunText.indexOf("${");
				if (beginRunText.length() > pslen) {
					key.append(beginRunText.substring(beginIndex + pslen));
				}
				ArrayList<Integer> removeRunList = new ArrayList<Integer>();// 需要移除的run
				// 处理中间的run
				for (int i = beginRunIndex + 1; i < endRunIndex; i++) {
					XWPFRun run = runs.get(i);
					String runText = run.text();
					key.append(runText);
					removeRunList.add(i);
				}

				// 获取endRun中的key值
				XWPFRun endRun = runs.get(endRunIndex);
				String endRunText = endRun.text();
				int endIndex = endRunText.indexOf("}");
				// run中**}或者**}**
				if (endRunText.length() > pelen && endIndex != 0) {
					key.append(endRunText.substring(0, endIndex));
				}

				// *******************************************************************
				// 取得key值后替换标签
				
				Object value = parametersMap.get(key.toString());
				String v = value == null ? "" : value.toString();

				// 先处理开始标签
				if (beginRunText.length() == pslen) {
					// run标签内文本{
					XWPFRun insertNewRun = xWPFParagraph.insertNewRun(beginRunIndex);
					insertNewRun.getCTR().setRPr(beginRun.getCTR().getRPr());
					// 设置文本
					insertNewRun.setText(v);
					xWPFParagraph.removeRun(beginRunIndex + 1);// 移除原始的run
				} else {
					// 该run标签为**{**或者 {** ，替换key后，还需要加上原始key前的文本
					XWPFRun insertNewRun = xWPFParagraph.insertNewRun(beginRunIndex);
					insertNewRun.getCTR().setRPr(beginRun.getCTR().getRPr());
					// 设置文本
					String textString = beginRunText.substring(0, beginRunText.indexOf("${"))
							+ v;
					// logger.info(">>>>>"+textString);
					// 分行处理
					if (textString.contains("@")) {
						String[] textStrings = textString.split("@");
						for (int i = 0; i < textStrings.length; i++) {
							// logger.info(">>>>>textStrings>>"+textStrings[i]);
							insertNewRun.setText(textStrings[i]);
							// insertNewRun.addCarriageReturn();
							insertNewRun.addBreak();// 换行
						}
					} else if (textString.endsWith(".png")) {
						CTInline inline = insertNewRun.getCTR().addNewDrawing().addNewInline();
						try {
							XWPFDocument document = xWPFParagraph.getDocument();
//							insertPicture(document, textString, inline, 500, 200);
							document.createParagraph();// 添加回车换行
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						insertNewRun.setText(textString);
					}

					xWPFParagraph.removeRun(beginRunIndex + 1);// 移除原始的run
				}

				// 处理结束标签
				if (endRunText.length() == 1) {
					// run标签内文本只有}
					XWPFRun insertNewRun = xWPFParagraph.insertNewRun(endRunIndex);
					insertNewRun.getCTR().setRPr(endRun.getCTR().getRPr());
					// 设置文本
					insertNewRun.setText("");
					xWPFParagraph.removeRun(endRunIndex + 1);// 移除原始的run

				} else {
					// 该run标签为**}**或者 }** 或者**}，替换key后，还需要加上原始key后的文本
					XWPFRun insertNewRun = xWPFParagraph.insertNewRun(endRunIndex);
					insertNewRun.getCTR().setRPr(endRun.getCTR().getRPr());
					// 设置文本
					String textString = endRunText.substring(endRunText.indexOf("}") + pelen);
					insertNewRun.setText(textString);
					xWPFParagraph.removeRun(endRunIndex + 1);// 移除原始的run
				}

				// 处理中间的run标签
				for (int i = 0; i < removeRunList.size(); i++) {
					XWPFRun xWPFRun = runs.get(removeRunList.get(i));// 原始run
					XWPFRun insertNewRun = xWPFParagraph.insertNewRun(removeRunList.get(i));
					insertNewRun.getCTR().setRPr(xWPFRun.getCTR().getRPr());
					insertNewRun.setText("");
					xWPFParagraph.removeRun(removeRunList.get(i) + 1);// 移除原始的run
				}

			} // 处理${**}被分成多个run

			replaceParagraph(xWPFParagraph, parametersMap);

		} // if 有标签

//		runs = xWPFParagraph.getRuns();
//		logger.info(String.valueOf(runs));
//		for (XWPFRun run : runs) {
//			logger.info(String.valueOf(run.getCTR()));
//			if (run.getCTR() != null && run.getCTR().getRPr() != null
//					&& run.getCTR().getRPr().getRFonts() != null
//					&& run.getCTR().getRPr().getRFonts().getAsciiTheme() == null) {
//				run.getCTR().getRPr().getRFonts().setAsciiTheme(STTheme.MAJOR_EAST_ASIA);
//			}
//		}
	}
}
