package com.fs.dishes.module.common.report.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;

import org.springframework.util.FileCopyUtils;

/**
 * 报表基类
 */
public class ReportUtil {

    /**
     * jasperFile 模板 文件
     *
     * @param request    请求
     * @param fileName   文件名称
     * @param parameters 参数信息
     * @param dataSource 数据源
     * @return
     * @throws Exception
     */
    public static JasperPrint getJasperFile(HttpServletRequest request, String fileName, Map parameters, JRDataSource dataSource) throws Exception {
        String fileRealPath =  fileName;
        File reportFile = new File(fileRealPath);
        if (!reportFile.exists()) {
            JasperCompileManager.compileReportToFile(fileRealPath);
            reportFile = new File(fileRealPath);
        }

        String print_url = JasperFillManager.fillReportToFile(fileRealPath, parameters, dataSource);
        File sourceFile = new File(print_url);

        JasperPrint jasperPrint = (JasperPrint) JRLoader.loadObject(sourceFile);
        return jasperPrint;
    }

    //--------------------Print Button-----------------------------------------------------
    public static PrintWriter getPrintWriter(HttpServletResponse response) throws Exception {
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>报表</title>");
        out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"../stylesheet.css\" title=\"Style\">");
        out.println("<style type=\"text/css\">");
        out.println("@media print {");
        out.println(".noprint {display: none ;}}");
        out.println("</style>");
        out.println("</head>");
        out.println("<body bgcolor=\"white\">");
        out.println("<div align='center' class='noprint'><input type='button' value='Print' onclick='javascript:window.print();'/></div>");
        out.println("</body>");
        out.println("</html>");
        return out;
    }


    /**
     * 查看分页页面
     *
     * @param request
     * @param response
     * @param jasperPrint
     * @param pageIndex
     * @return
     * @throws Exception
     */
    public static StringBuffer viewHtmlByPage(HttpServletRequest request, HttpServletResponse response, JasperPrint jasperPrint, int pageIndex) throws Exception {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        request.getSession().setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jasperPrint);
        StringBuffer sbuffer = new StringBuffer();
        JRHtmlExporter exporter = new JRHtmlExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STRING_BUFFER, sbuffer);
        exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, "../servlets/image?image=");
        exporter.setParameter(JRExporterParameter.PAGE_INDEX, Integer.valueOf(pageIndex));
        exporter.setParameter(JRHtmlExporterParameter.HTML_HEADER, "");
        exporter.setParameter(JRHtmlExporterParameter.BETWEEN_PAGES_HTML, "");
        exporter.setParameter(JRHtmlExporterParameter.HTML_FOOTER, "");
        exporter.exportReport();// 导出
        return sbuffer;
    }


    /**
     * 查看页面
     *
     * @param request
     * @param response
     * @param jasperPrint
     * @throws Exception
     */
    public static void viewHtml(HttpServletRequest request, HttpServletResponse response, JasperPrint jasperPrint) throws Exception {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        request.getSession().setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jasperPrint);
        JRHtmlExporter exporter = new JRHtmlExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        PrintWriter out = ReportUtil.getPrintWriter(response);
        exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
        exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, "../servlets/image?image=");
        exporter.exportReport();// 导出
    }


    /**
     * 浏览器中打开PDF
     *
     * @param res
     * @param path
     * @param parameters
     * @param dataSource
     * @param out
     * @throws Exception
     */
    public static void viewPDFByHtml(HttpServletResponse res, String path, Map<String, Object> parameters, JRDataSource dataSource, OutputStream out) throws Exception {

        String s1 = JasperFillManager.fillReportToFile(path + ".jasper", parameters, dataSource);
        String s2 = JasperExportManager.exportReportToPdfFile(path + ".jrprint");

        File sourceFile = new File(s1);
        File destFile = new File(s2);

        // 设置文件头
        res.setHeader("Content-Disposition", "inline;fileName=" + new String(destFile.getName().getBytes("gbk"), "iso-8859-1"));
        res.setContentType("application/pdf; charset=gbk");
        // 从后台下载
        FileCopyUtils.copy(new FileInputStream(new File(sourceFile.getParent(), destFile.getName())), out);

    }

    /**
     * 导出word文档
     *
     * @param res
     * @param path
     * @param parameters
     * @param dataSource
     * @param out
     * @throws Exception
     */
    public static void exportDoc(HttpServletResponse res, String path, Map<String, Object> parameters, JRDataSource dataSource, OutputStream out) throws Exception {

        // 数据填充
        String jrprint_url = JasperFillManager.fillReportToFile(path + ".jasper", parameters, dataSource);
        File sourceFile = new File(jrprint_url);

        JasperPrint jasperPrint = (JasperPrint) JRLoader.loadObject(sourceFile);

        File destFile = new File(sourceFile.getParent(), jasperPrint.getName() + ".docx");

        JRDocxExporter exporter = new JRDocxExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(destFile));

        exporter.exportReport();// 导出到doc

        // 设置文件头
        res.setHeader("Content-Disposition", "attachment;fileName=" + new String(destFile.getName().getBytes("gbk"), "iso-8859-1"));
        // 从后台下载
        FileCopyUtils.copy(new FileInputStream(new File(sourceFile.getParent(), destFile.getName())), out);

    }

    /**
     * 导出excel文件
     *
     * @param res
     * @param path
     * @param parameters
     * @param dataSource
     * @param out
     * @throws Exception
     */
    public static void exportXls(HttpServletResponse res, String path, Map<String, Object> parameters, JRDataSource dataSource, OutputStream out) throws Exception {

        // 数据填充
        String print_url = JasperFillManager.fillReportToFile(path + ".jasper", parameters, dataSource);
        File sourceFile = new File(print_url);

        JasperPrint jasperPrint = (JasperPrint) JRLoader.loadObject(sourceFile);

        File destFile = new File(sourceFile.getParent(), jasperPrint.getName() + ".xls");

        JRXlsExporter exporter = new JRXlsExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(destFile));
        SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
        configuration.setOnePagePerSheet(false);
        exporter.setConfiguration(configuration);

        exporter.exportReport();

        // 设置文件头
        res.setHeader("Content-Disposition", "attachment;fileName=" + new String(destFile.getName().getBytes("gbk"), "iso-8859-1"));
        // 从后台下载
        FileCopyUtils.copy(new FileInputStream(new File(sourceFile.getParent(), destFile.getName())), out);
    }

}
