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

	public void replacePara(XWPFDocument doc, Map<String, Object> params) throws Exception {
		Iterator<XWPFParagraph> iterator = doc.getParagraphsIterator();
		while (iterator.hasNext()) {
			XWPFParagraph para = iterator.next();
			replaceParagraph(para, params);
		}
	}

	public void replaceInTable(XWPFTable table, Map<String, Object> params) throws Exception {
		List<XWPFTableRow> rows = table.getRows();
		for (XWPFTableRow row : rows) {
			List<XWPFTableCell> cells = row.getTableCells();
			for (XWPFTableCell cell : cells) {
				List<XWPFParagraph> paras = cell.getParagraphs();
				for (XWPFParagraph para : paras) {
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
						replaceParagraph(paragraph, (Map) map);
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
			List<XWPFParagraph> paragraphs = cell.getParagraphs();
			for (XWPFParagraph paragraph : paragraphs) {
				replaceParagraph(paragraph, (Map) map);
			}

			table.addRow(row);
			table.removeRow(1);
		}
	}

	/**
	 * 替换指定行的参数
	 * 
	 * @param rows
	 * @param params
	 * @throws Exception
	 */
	private void replaceInRow(List<XWPFTableRow> rows, Map<String, Object> params) throws Exception {
		for (XWPFTableRow row : rows) {
			List<XWPFTableCell> cells = row.getTableCells();
			for (XWPFTableCell cell : cells) {
				List<XWPFParagraph> paras = cell.getParagraphs();
				for (XWPFParagraph para : paras) {
					replaceParagraph(para, params);
				}
			}
		}
	}

	public void replaceTable2(XWPFDocument doc, Map<String, Object> params, int tableIndex) throws Exception {
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

	/**
	 * 
	 * 通用替换段落中参数的方法
	 * 
	 * @param xWPFParagraph
	 * @param parametersMap
	 */
	public void replaceParagraph(XWPFParagraph xWPFParagraph, Map<String, Object> parametersMap) {
		String startTag = "${";
		String endTag = "}";
		// parameter start tag size
		int pslen = startTag.length();
		// parameter end tag size
		int pelen = endTag.length();
		String regEx = "\\$\\{.+?\\}";
		regEx = startTag.replace("$", "\\$").replace("{", "\\{") + ".+?" + endTag.replace("}", "\\}");
		
		List<XWPFRun> runs = xWPFParagraph.getRuns();
		String xWPFParagraphText = xWPFParagraph.getText();
		
		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher = pattern.matcher(xWPFParagraphText);// 正则匹配字符串${****}

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
	}

	/**
	 * 
	 * 使用xdocreport包来实现word to pdf
	 * <br>但存在问题，对于表格新增行，总是会报ST_Theme为空的错误。每个单元格替换的值会有两个（两个XWPFRun），一个显示一个隐藏，第二个XWPFRun造成该错误。
	 * 
	 * @param doc
	 * @param out
	 */
	public boolean transferToPDF(InputStream doc, OutputStream out) {
		XWPFDocument document = null;
		try {
			document = new XWPFDocument(doc);
		} catch (IOException e1) {
			e1.printStackTrace();
			return false;
		}

		PdfOptions options = PdfOptions.create();
		try {
			PdfConverter.getInstance().convert(document, out, options);
			return true;
		} catch (XWPFConverterException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
