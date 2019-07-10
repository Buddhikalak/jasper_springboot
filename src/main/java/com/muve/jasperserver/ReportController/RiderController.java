package com.muve.jasperserver.ReportController;

import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.HashMap;

@RestController
@RequestMapping("api/riders")

public class RiderController {

    @Autowired
    @Qualifier("jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @RequestMapping("/all")
    public void getAllRiders(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("application/x-download");
            response.setHeader("Content-Disposition", String.format("attachment; filename=\"users.pdf\""));
            Connection conn = jdbcTemplate.getDataSource().getConnection();
            String report = "src/main/resources/jrmx/report1.jrxml";
            JasperReport jreport = JasperCompileManager.compileReport(report);
            //JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource();
            HashMap params = new HashMap();
            JasperPrint jprint = JasperFillManager.fillReport(jreport, params,conn);
            OutputStream out = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jprint, out);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
