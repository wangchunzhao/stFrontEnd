package com.qhc.steigenberger.util;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;

/**
 * 效果最好的一种方法，但是需要 window 环境，而且速度是最慢的需要安装 msofficeWord 以及 SaveAsPDFandXPS.exe (
 * word 的一个插件，用来把 word 转化为 pdf，可以不用安装，本次未安装测试通过 )
 * 
 * SaveAsPDFandXPS
 * 下载地址：http://www.microsoft.com/zh-cn/download/details.aspx?id=7 
 * jacob 包下载地址：http://sourceforge.net/projects/jacob-project/
 * 
 * jacob.jar 放在 E:\jdk1.5.0_14\jre\lib\ext 
 * jacob.dll 放在 E:\jdk1.5.0_14\jre\bin
 * 
 * word转pdf的工具
 * 
 * @author 
 * @time 
 */
public class Word2PdfUtil {
	private static final Logger logger = LoggerFactory.getLogger(Word2PdfUtil.class);
    static final int wdDoNotSaveChanges = 0;// 不保存待定的更改。
    static final int wdFormatPDF = 17;// word转PDF 格式

    public static void main(String[] args) throws IOException {
        String source1 = "D:\\workspace\\haier\\steigenberger\\steigenberger\\contract\\123-1-1-2.docx";
        String target1 = "D:\\workspace\\haier\\steigenberger\\steigenberger\\contract\\123-1-1-2.pdf";
        Word2PdfUtil.word2pdf(source1, target1);
    }

    /**
     * 
     * @param source
     *            word路径
     * @param target
     *            生成的pdf路径
     * @return
     */
    public static boolean word2pdf(File source, File target) {
    	return word2pdf(source.getAbsolutePath(), target.getAbsolutePath());
    }

    /**
     * 
     * @param source
     *            word路径
     * @param target
     *            生成的pdf路径
     * @return
     */
    public static boolean word2pdf(String source, String target) {
        logger.info("Word转PDF开始启动...");
        long start = System.currentTimeMillis();
//        System.loadLibrary("./lib/jacob-1.19-x64.dll");
        ActiveXComponent app = null;
        try {
            app = new ActiveXComponent("Word.Application");
            app.setProperty("Visible", false);
            Dispatch docs = app.getProperty("Documents").toDispatch();
            logger.info("打开文档：" + source);
            Dispatch doc = Dispatch.call(docs, "Open", source, false, true).toDispatch();
            logger.info("转换文档到PDF：" + target);
            File tofile = new File(target);
            if (tofile.exists()) {
                tofile.delete();
            }
            Dispatch.call(doc, "SaveAs", target, wdFormatPDF);
            Dispatch.call(doc, "Close", false);
            long end = System.currentTimeMillis();
            logger.info("转换完成，用时：" + (end - start) + "ms");
            return true;
        } catch (Exception e) {
            logger.info("Word转PDF出错：" + e.getMessage());
            return false;
        } finally {
            if (app != null) {
                app.invoke("Quit", wdDoNotSaveChanges);
            }
        }
    }

}