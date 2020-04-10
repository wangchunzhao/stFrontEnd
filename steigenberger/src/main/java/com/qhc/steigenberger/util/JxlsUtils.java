package com.qhc.steigenberger.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.jexl3.JexlBuilder;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.jxls.common.Context;
import org.jxls.expression.JexlExpressionEvaluator;
import org.jxls.reader.ReaderBuilder;
import org.jxls.reader.XLSReadStatus;
import org.jxls.reader.XLSReader;
import org.jxls.transform.Transformer;
import org.jxls.transform.poi.PoiTransformer;
import org.jxls.util.JxlsHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

/**
 * 
 * Jxls 工具类，导入导出Excel
 * <p>
 * 使用Jxls库实现数据导出到Excel和从Excel读入数据公共方法
 * 
 * @author suzu
 */
public class JxlsUtils {
	static private Logger logger = LoggerFactory.getLogger(JxlsUtils.class);

	/**
	 * 
	 * 使用指定的Excel模板导出数据.
	 * 
	 * @param templatePath
	 * @param os
	 * @param bean
	 */
	public static void exportExcel(String templatePath, OutputStream os, Object bean) {
		logger.debug("Export to excel, tempalte: {}, bean: {}", templatePath, bean);
		try (InputStream in = getTemplateStream(templatePath)) {
			exportExcel(in, os, bean);
		} catch (Exception e) {
			throw new RuntimeException("Excel 模板未找到。", e);
		}
	}

	/**
	 * 
	 * 使用指定的Excel模板导出数据.
	 * 
	 * @param xls
	 * @param out
	 * @param bean
	 * @throws IOException
	 */
	public static void exportExcel(File xls, File out, Object bean) throws IOException {
		logger.debug("Export to excel, tempalte: {}, output: {}, bean: {}", xls, out, bean);
		exportExcel(new FileInputStream(xls), new FileOutputStream(out), bean);
	}

	/**
	 * 
	 * 使用指定的模板导出数据，循环生成Sheet的模板会存在模板Sheet不会删除的问题.
	 * 
	 * @param is
	 * @param os
	 * @param model 如果是Map实例，会将Map的key/value映射到Context中；否则会以变量名model存储到Context中
	 * @throws IOException
	 * 
	 * @author suzu
	 */
	public static void exportExcel(InputStream is, OutputStream os, Object model) throws IOException {
		JxlsHelper jxlsHelper = JxlsHelper.getInstance();
//    jxlsHelper.getExpressionEvaluatorFactory().createExpressionEvaluator("utils").
		Context context = initJxlsContext(model);

		// 必须要这个，否则表格函数统计会错乱
		jxlsHelper.setUseFastFormulaProcessor(false);

		Transformer transformer = jxlsHelper.createTransformer(is, os);
		// 获得配置
		JexlExpressionEvaluator evaluator = (JexlExpressionEvaluator) transformer.getTransformationConfig()
				.getExpressionEvaluator();
		// 函数强制，自定义功能
		Map<String, Object> functionMap = new HashMap<String, Object>();
		// 添加自定义功能
		functionMap.put("utils", new JxlsFunctions()); // 添加自定义功能
		// 2.6.0 以前
		// evaluator.getJexlEngine().setFunctions(functionMap);
		// since 2.6.0
		evaluator.setJexlEngine(new JexlBuilder().namespaces(functionMap).create());

		jxlsHelper.processTemplate(context, transformer);

		// 循环生成多个Sheet的时候删除模板页（其他方法不会删除），但对其他的方式有问题，特别是multi sheet的情况
		// jxlsHelper.setDeleteTemplateSheet(true);
		// // jxlsHelper.setHideTemplateSheet(true);
		// // 最后一个参数是输出内容开始的Cell，Result表示输出结果Sheet，
		// jxlsHelper.processTemplateAtCell(is, os, context, "Result!A1");
		// // jxlsHelper.processTemplateAtCell(is, os, context, "Sheet2!A1");
	}

	/**
	 * 
	 * 专用于循环生成多Sheet模板的导出数据，对其他方式的模板导出会有影响，特别是多Sheet的模板.
	 * 
	 * @param is
	 * @param os
	 * @param model 如果是Map实例，会将Map的key/value映射到Context中；否则会以变量名model存储到Context中
	 * @throws IOException
	 * 
	 * @author suzu
	 */
	public static void exportLoopSheetExcel(InputStream is, OutputStream os, Object model) throws IOException {
		JxlsHelper jxlsHelper = JxlsHelper.getInstance();
		Context context = initJxlsContext(model);

		// 必须要这个，否则表格函数统计会错乱
		jxlsHelper.setUseFastFormulaProcessor(false);

		// 循环生成多个Sheet的时候删除模板页（其他方法不会删除），但对其他的方式有问题，特别是multi sheet的情况
		jxlsHelper.setDeleteTemplateSheet(true);
		// jxlsHelper.setHideTemplateSheet(true);
		// 最后一个参数是输出内容开始的Cell，Result表示输出结果Sheet，
		jxlsHelper.processTemplateAtCell(is, os, context, "Result!A1");
		// jxlsHelper.processTemplateAtCell(is, os, context, "Sheet2!A1");
	}

	/**
	 * 
	 * 专用于循环生成多Sheet模板的导出数据，对其他方式的模板导出会有影响，特别是多Sheet的模板.
	 * 
	 * @param is
	 * @param os
	 * @param model 如果是Map实例，会将Map的key/value映射到Context中；否则会以变量名model存储到Context中
	 * @throws IOException
	 * 
	 * @author suzu
	 */
	public static void exportDynamicGridExcel(InputStream is, OutputStream os, Object model, String properties)
			throws IOException {
		JxlsHelper jxlsHelper = JxlsHelper.getInstance();
		Context context = initJxlsContext(model);

		// 必须要这个，否则表格函数统计会错乱
		jxlsHelper.setUseFastFormulaProcessor(false);

		// 循环生成多个Sheet的时候删除模板页（其他方法不会删除），但对其他的方式有问题，特别是multi sheet的情况
		jxlsHelper.setDeleteTemplateSheet(true);
		// jxlsHelper.setHideTemplateSheet(true);
		// 最后一个参数是输出内容开始的Cell，Result表示输出结果Sheet，
		jxlsHelper.processGridTemplateAtCell(is, os, context, properties, "Sheet1!A1");
	}

	/**
	 * 
	 * 初始化JxlsContext对象，设置model数据和工具类.
	 * 
	 * @param model
	 * @return
	 */
	private static Context initJxlsContext(Object model) {
		Context context = new Context();
		context = PoiTransformer.createInitialContext();
		context.putVar("nowdate", new Date());
		if (model != null) {
			if (model instanceof Map) {
				Map<?, ?> map = ((Map<?, ?>) model);
				for (Object key : map.keySet()) {
					context.putVar(String.valueOf(key), map.get(key));
				}
			} else {
				context.putVar("bean", model);
			}
		}

		// 设置静默模式，不报警告
		// evaluator.getJexlEngine().setSilent(true);

		// 获得配置
//    JexlExpressionEvaluator evaluator = new JexlExpressionEvaluator();
//    evaluator = (JexlExpressionEvaluator) transformer.getTransformationConfig().getExpressionEvaluator();
		// 函数强制，自定义功能
//    Map<String, Object> funcs = new HashMap<String, Object>();
		// 添加自定义功能
//    funcs.put("dutils", new DateHelper()); // 添加自定义功能
//    funcs.put("cutils", new CommonHelper()); // 添加自定义功能
		// 2.6.0 以前
//    evaluator.getJexlEngine().setFunctions(funcs);

		return context;
	}

	// 获取jxls模版文件
//	public static File getTemplate(String path) {
//		URL url = JxlsUtils.class.getResource(path);
//		if (url == null) {
//			url = ClassLoader.getSystemResource(path);
//		}
//		logger.info("Template url : {}", url);
//		try {
//			return url == null ? null : new File(url.toURI());
//		} catch (URISyntaxException e) {
//			return null;
//		}
//	}

	// 获取jxls模版文件
	public static InputStream getTemplateStream(String name) {
		InputStream templateStream = JxlsUtils.class.getResourceAsStream(name);
		if (templateStream == null) {
			templateStream = ClassLoader.getSystemResourceAsStream(name);
		}
		logger.info("Template url : {}", templateStream);
		return templateStream;
	}

	/**
	 * 
	 * 导入Excel数据.
	 * 
	 * @param config
	 * @param xls
	 * @param bean
	 * @return Boolean 成功（true）或异常（false），List error message list
	 */
	public static Object importExcel(String config, String xls, Object bean) {
		try {
			return importExcel(new FileInputStream(config), new FileInputStream(xls), bean);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 导入Excle数据
	 * 
	 * @param configStream
	 * @param xlsStream
	 * @param bean
	 * @return Boolean 成功（true）或异常（false），List error message list
	 */
	public static Object importExcel(InputStream configStream, InputStream xlsStream, Object bean) {
		Map map = null;
		if (bean instanceof Map) {
			map = (Map) bean;
		} else {
			map = new HashMap<>();
			map.put("bean", bean);
		}

		try (InputStream inputXML = new BufferedInputStream(configStream);
				InputStream inputXLS = new BufferedInputStream(xlsStream);) {
			XLSReader mainReader = ReaderBuilder.buildFromXML(inputXML);

			XLSReadStatus readStatus = mainReader.read(inputXLS, map);

			if (readStatus.isStatusOK()) {
				return true;
			} else {
				return readStatus.getReadMessages();
			}
		} catch (InvalidFormatException | IOException | SAXException e) {
			e.printStackTrace();
			return false;
		}
	}
}