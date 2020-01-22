package com.qhc.steigenberger.util;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;

import com.qhc.steigenberger.util.ExportExcel;

/**
 * 导出Excel公共方法
 *
 */
public class ExportExcel {

	// 显示的导出表的标题
	private String title;
	// 导出表的列名
	private String[] headers;

	private List<Object[]> dataList = new ArrayList<Object[]>();
	
	private List<Object> entityList = new ArrayList<Object>();

	HttpServletResponse response;
	
	//导出的文件名称
	private String fileName;

	/**
	 * 构造方法，传入要导出的数据
	 * @param fileName 要导出的文件名称 没有的话，使用默认值
	 * @param title 工作表第一表头
	 * @param headers 导出表头的数组 {"序号","时间","发言人","类型","消息"}
	 * @param dataList List<Object[]>需要和表头字段对应的值
	 */
	public ExportExcel(String fileName,String title, String[] headers, List<Object[]> dataList) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:ss:mm");
		this.dataList = dataList;
		this.headers = headers;
		if(title==null||"".equals(title)) {
			this.title = "报表数据-"+sdf.format(new Date());
		}else {
			this.title = title;
		}
		if(fileName==null||"".equals(fileName)) {
			Long str = (new Date()).getTime();
			this.fileName = "file"+str;
		}else {
			this.fileName = fileName;
		}
	}
	
	/**
	 * 构造方法，传入要导出的数据
	 * @param fileName 要导出的文件名称 没有的话，使用默认值
	 * @param title 工作表第一表头
	 * @param headers 导出表头的数组 {"序号","时间","发言人","类型","消息"}
	 * @param dataList List<Object[]>需要和表头字段对应的值
	 * @param xxx 预设值，可不填
	 */
	public ExportExcel(String fileName,String title,String[] headers, List<Object> dataList,String xxx) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.entityList = dataList;
		this.headers = headers;
		if(title==null||"".equals(title)) {
			this.title = "报表数据-"+sdf.format(new Date());
		}else {
			this.title = title;
		}
		if(fileName==null||"".equals(fileName)) {
			Long str = (new Date()).getTime();
			this.fileName = "file"+str;
		}else {
			this.fileName = fileName;
		}
	}

	/*
	 * 导出数据(实体类中的部分数据)
	 */
//	public void export(HttpServletResponse response) throws Exception {
//		try {
//			HSSFWorkbook workbook = new HSSFWorkbook(); // 创建工作簿对象
//			HSSFSheet sheet = workbook.createSheet(title); // 创建工作表
//
//			// 产生表格标题行
//			HSSFRow rowm = sheet.createRow(0);
//			HSSFCell cellTiltle = rowm.createCell(0);
//
//			rowm.setHeight((short) (25 * 35)); // 设置高度
//
//			// sheet样式定义【getColumnTopStyle()/getStyle()均为自定义方法 - 在下面 - 可扩展】
//			HSSFCellStyle columnTopStyle = this.getColumnTopStyle(workbook);// 获取列头样式对象
//			HSSFCellStyle style = this.getStyle(workbook); // 单元格样式对象
//
//			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, (headers.length - 1)));
//			cellTiltle.setCellStyle(columnTopStyle);
//			cellTiltle.setCellValue(title);
//
//			// 定义所需列数
//			int columnNum = headers.length;
//			HSSFRow rowRowName = sheet.createRow(1); // 在索引2的位置创建行(最顶端的行开始的第二行)
//
//			rowRowName.setHeight((short) (25 * 25)); // 设置高度
//
//			// 将列头设置到sheet的单元格中
//			for (int n = 0; n < columnNum; n++) {
//				HSSFCell cellRowName = rowRowName.createCell(n); // 创建列头对应个数的单元格
//				cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING); // 设置列头单元格的数据类型
//				HSSFRichTextString text = new HSSFRichTextString(headers[n]);
//				cellRowName.setCellValue(text); // 设置列头单元格的值
//				cellRowName.setCellStyle(columnTopStyle); // 设置列头单元格样式
//			}
//
//			// 将查询出的数据设置到sheet对应的单元格中
//			for (int i = 0; i < dataList.size(); i++) {
//
//				Object[] obj = dataList.get(i);// 遍历每个对象
//				HSSFRow row = sheet.createRow(i + 2);// 创建所需的行数
//
//				row.setHeight((short) (25 * 20)); // 设置高度
//
//				for (int j = 0; j < obj.length; j++) {
//					HSSFCell cell = null; // 设置单元格的数据类型
//					if (j == 0) {
//						cell = row.createCell(j, HSSFCell.CELL_TYPE_NUMERIC);
//						cell.setCellValue(i + 1);
//					} else {
//						cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
//						if (!"".equals(obj[j]) && obj[j] != null) {
//							cell.setCellValue(obj[j].toString()); // 设置单元格的值
//						}
//					}
//					cell.setCellStyle(style); // 设置单元格样式
//				}
//			}
//			// 让列宽随着导出的列长自动适应
//			for (int colNum = 0; colNum < columnNum; colNum++) {
//				int columnWidth = sheet.getColumnWidth(colNum) / 256;
//				for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
//					HSSFRow currentRow;
//					// 当前行未被使用过
//					if (sheet.getRow(rowNum) == null) {
//						currentRow = sheet.createRow(rowNum);
//					} else {
//						currentRow = sheet.getRow(rowNum);
//					}
//					if (currentRow.getCell(colNum) != null) {
//						HSSFCell currentCell = currentRow.getCell(colNum);
//						if (currentCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
//							int length = currentCell.getStringCellValue().getBytes().length;
//							if (columnWidth < length) {
//								columnWidth = length;
//							}
//						}
//					}
//				}
//				if (colNum == 0) {
//					sheet.setColumnWidth(colNum, (columnWidth - 2) * 128);
//				} else {
//					sheet.setColumnWidth(colNum, (columnWidth + 4) * 256);
//				}
//
//			}
//
//			if (workbook != null) {
//				try {
//					response.setContentType("application/octet-stream");
//			        response.setHeader("Content-disposition", "attachment;filename="+fileName+".xls");//默认Excel名称
//			        response.flushBuffer();
//			        workbook.write(response.getOutputStream());
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
	
	/*
	 * 导出数据(实体类中的全部数据)
	 */
//	public void exportEntity(HttpServletResponse response) throws Exception {
//		// 声明一个工作薄
//        HSSFWorkbook workbook = new HSSFWorkbook();
//        // 生成一个表格
//        HSSFSheet sheet = workbook.createSheet();
//        // 设置表格默认列宽度为15个字节
//        sheet.setDefaultColumnWidth((short) 18);
//        HSSFRow row = sheet.createRow(0);
//        for (short i = 0; i < headers.length; i++) {
//            HSSFCell cell = row.createCell(i);
//            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
//            cell.setCellValue(text);
//        }
//        //遍历集合数据，产生数据行
//        Iterator it = entityList.iterator();
//        int index = 0;
//        while (it.hasNext()) {
//            index++;
//            row = sheet.createRow(index);
//            T t = (T) it.next();
//            //利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
//            Field[] fields = t.getClass().getDeclaredFields();
//            for (short i = 0; i < fields.length; i++) {
//                HSSFCell cell = row.createCell(i);
//                Field field = fields[i];
//                String fieldName = field.getName();
//                String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase()+ fieldName.substring(1);
//                try {
//                    Class tCls = t.getClass();
//                    Method getMethod = tCls.getMethod(getMethodName,new Class[]{});
//                    Object value = getMethod.invoke(t, new Object[]{});
//                    String textValue = null;
// 
//                    if (value instanceof Date)
//                    {
//                        Date date = (Date) value;
//                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                        textValue = sdf.format(date);
//                    }
//                    else
//                    {
//                    	if(value!=null)
//                        //其它数据类型都当作字符串简单处理
//                        textValue = value.toString();
//                    }       
//					
//                    HSSFRichTextString richString = new HSSFRichTextString(textValue);
//                    HSSFFont font3 = workbook.createFont();
//                    font3.setColor(HSSFColor.BLUE.index);//定义Excel数据颜色
//                    richString.applyFont(font3);
//                    cell.setCellValue(richString);
//                     
//                } catch (SecurityException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                } catch (NoSuchMethodException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                } catch (IllegalArgumentException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                } catch (IllegalAccessException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                } catch (InvocationTargetException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//            }
//        }
//        response.setContentType("application/octet-stream");
//        response.setHeader("Content-disposition", "attachment;filename=createList.xls");//默认Excel名称
//        response.flushBuffer();
//        workbook.write(response.getOutputStream());
//
//	}

	/*
	 * 列头单元格样式
	 */
//	public HSSFCellStyle getColumnTopStyle(HSSFWorkbook workbook) {
//
//		// 设置字体
//		HSSFFont font = workbook.createFont();
//		// 设置字体大小
//		font.setFontHeightInPoints((short) 11);
//		// 字体加粗
//		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//		// 设置字体名字
//		font.setFontName("Courier New");
//		// 设置样式;
//		HSSFCellStyle style = workbook.createCellStyle();
//		// 设置底边框;
//		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//		// 设置底边框颜色;
//		style.setBottomBorderColor(HSSFColor.BLACK.index);
//		// 设置左边框;
//		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//		// 设置左边框颜色;
//		style.setLeftBorderColor(HSSFColor.BLACK.index);
//		// 设置右边框;
//		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
//		// 设置右边框颜色;
//		style.setRightBorderColor(HSSFColor.BLACK.index);
//		// 设置顶边框;
//		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
//		// 设置顶边框颜色;
//		style.setTopBorderColor(HSSFColor.BLACK.index);
//		// 在样式用应用设置的字体;
//		style.setFont(font);
//		// 设置自动换行;
//		style.setWrapText(false);
//		// 设置水平对齐的样式为居中对齐;
//		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//		// 设置垂直对齐的样式为居中对齐;
//		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//
//		// 设置单元格背景颜色
//		style.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
//		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//
//		return style;
//
//	}

	/*
	 * 列数据信息单元格样式
	 */
//	public HSSFCellStyle getStyle(HSSFWorkbook workbook) {
//		// 设置字体
//		HSSFFont font = workbook.createFont();
//		// 设置字体大小
//		// font.setFontHeightInPoints((short)10);
//		// 字体加粗
//		// font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//		// 设置字体名字
//		font.setFontName("Courier New");
//		// 设置样式;
//		HSSFCellStyle style = workbook.createCellStyle();
//		// 设置底边框;
//		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//		// 设置底边框颜色;
//		style.setBottomBorderColor(HSSFColor.BLACK.index);
//		// 设置左边框;
//		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//		// 设置左边框颜色;
//		style.setLeftBorderColor(HSSFColor.BLACK.index);
//		// 设置右边框;
//		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
//		// 设置右边框颜色;
//		style.setRightBorderColor(HSSFColor.BLACK.index);
//		// 设置顶边框;
//		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
//		// 设置顶边框颜色;
//		style.setTopBorderColor(HSSFColor.BLACK.index);
//		// 在样式用应用设置的字体;
//		style.setFont(font);
//		// 设置自动换行;
//		style.setWrapText(false);
//		// 设置水平对齐的样式为居中对齐;
//		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//		// 设置垂直对齐的样式为居中对齐;
//		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//
//		return style;
//	}
	

}