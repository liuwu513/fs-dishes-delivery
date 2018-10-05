package com.fs.dishes.base.view;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.*;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Liuwu on 2018/10/5.
 */
public class ExcelReportView extends AbstractView {

    private static final String CONTENT_TYPE = "application/vnd.ms-excel";

    private String templatePath;
    private String exportFileName;

    public ExcelReportView(String templatePath, String exportFileName) {
        this.templatePath = templatePath;
        if (exportFileName != null) {
            try {
                exportFileName = URLEncoder.encode(exportFileName, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        this.exportFileName = exportFileName;
        setContentType(CONTENT_TYPE);
    }

    protected Map<String, Object> getParamsMap(Map<String, Object> map) throws Exception {
        Map<String, Object> params = new HashMap<>();
        for (String key : map.keySet()) {
            Object val = map.get(key);
            if (val instanceof JRDataSource) {
                continue;
            } else {
                params.put(key, val);
            }
        }
        return params;
    }

    protected JRDataSource getDataSource(Map<String, Object> map) throws Exception {
        for (String key : map.keySet()) {
            Object val = map.get(key);
            if (val instanceof JRDataSource) {
                return (JRDataSource) map.get(key);
            }
        }
        return new JREmptyDataSource();
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> map, HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {
        response.setCharacterEncoding("utf-8");
        response.setContentType(getContentType());
        //response.setContentType("application/octet-stream");
        response.setHeader("content-disposition", "attachment; filename=" + exportFileName + ".xlsx");
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(templatePath)) {
            try (OutputStream ouputStream = response.getOutputStream()) {

                JasperPrint jasperPrint = JasperFillManager
                        .fillReport(inputStream, getParamsMap(map), getDataSource(map));

                //设置导出时参数
                SimpleXlsxReportConfiguration conf = new SimpleXlsxReportConfiguration();
                conf.setWhitePageBackground(false);
                conf.setDetectCellType(true);

                JRXlsxExporter exporter = new JRXlsxExporter();
                exporter.setConfiguration(conf);

                //设置输入项
                ExporterInput exporterInput = new SimpleExporterInput(jasperPrint);
                exporter.setExporterInput(exporterInput);

                //设置输出项
                OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(response.getOutputStream());
                exporter.setExporterOutput(exporterOutput);
                exporter.exportReport();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
