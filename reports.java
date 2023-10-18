package com.hmkcode;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.*;

public class App 
{
    // name and destination of output file e.g. "report.pdf"
    private static String destFileName = "report.pdf";

    public static void main( String[] args ) throws FileNotFoundException, JRException {

        System.out.println( "generating jasper report..." );

        // 1. compile template ".jrxml" file
        JasperReport jasperReport = getJasperReport();

        // 2. parameters "empty"
        Map<String, Object> parameters = getParameters();

        // 3. datasource "java object"
        JRDataSource dataSource = getDataSource();

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, 
                parameters, 
                dataSource);
        JasperExportManager.exportReportToPdfFile(jasperPrint, destFileName);

    }

    private static JasperReport getJasperReport() throws FileNotFoundException, JRException {
        File template = ResourceUtils.getFile("classpath:report.jrxml");
        return JasperCompileManager.compileReport(template.getAbsolutePath());
    }
    private static Map<String, Object> getParameters(){
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "hmkcode");
        return parameters;
    }

    private static JRDataSource getDataSource(){

        List<Country> countries = new LinkedList<>();

        countries.add(new Country("IS", "Iceland", 
            "https://i.pinimg.com/originals/72/b4/49/72b44927f220151547493e528a332173.png"));

        // 9 other countries in GITHUB

        return new JRBeanCollectionDataSource(countries);
    }
}
