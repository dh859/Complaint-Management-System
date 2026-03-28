package com.cms.cmsapp.common.utils.FileExporter;

import org.jfree.chart.JFreeChart;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.cms.cmsapp.common.Enums.ReportType;
import com.cms.cmsapp.common.exceptions.InvalidOperationException;
import com.cms.cmsapp.common.utils.ChartService.ChartGenerator;
import com.cms.cmsapp.common.utils.ChartService.Dataset;
import com.cms.cmsapp.common.utils.ChartService.ReportMapper;
import com.cms.cmsapp.common.utils.ChartService.SummaryChartFactory;
import com.cms.cmsapp.common.utils.ChartService.SummaryDataset;
import com.cms.cmsapp.common.utils.ChartService.SystemChartFactory;
import com.cms.cmsapp.common.utils.ChartService.SystemReportDataset;
import com.cms.cmsapp.reporting.entity.Report;

import lombok.RequiredArgsConstructor;

import java.io.ByteArrayOutputStream;

@Component
@RequiredArgsConstructor
public class ReportPdfExporter {

    private final TemplateEngine templateEngine;
    private final ReportMapper reportMapper;
    private final ChartGenerator chartGenerator;
    private final SystemChartFactory systemChartFactory;
    private final SummaryChartFactory summaryChartFactory;

    public byte[] export(Report report) {

        try {

            Context context = new Context();
            Dataset dataset = reportMapper.toDataset(report);
            String templateName;

            switch (report.getReportType()) {

                case ReportType.SUMMARY_REPORT -> {

                    SummaryDataset summary =
                            (SummaryDataset) dataset;

                    context.setVariable("summary", summary);

                    JFreeChart barChart =
                            summaryChartFactory.barChart(summary);

                    JFreeChart pieChart =
                            summaryChartFactory.pieChart(summary);

                    String barImage =
                            chartGenerator.generateBase64Png(barChart);

                    String pieImage =
                            chartGenerator.generateBase64Png(pieChart);

                    JFreeChart departmentChart =
                            summaryChartFactory.departmentChart(summary);

                    String departmentImage =
                            chartGenerator.generateBase64Png(departmentChart);

                    JFreeChart categoryChart =
                            summaryChartFactory.categoryChart(summary);

                    String categoryImage =
                            chartGenerator.generateBase64Png(categoryChart);

                    context.setVariable("barChartImage", barImage);
                    context.setVariable("pieChartImage", pieImage);
                    context.setVariable("departmentChartImage", departmentImage);
                    context.setVariable("categoryChartImage", categoryImage);

                    templateName = "complaint-summary";
                }

                case ReportType.SYSTEM_REPORT -> {

                    SystemReportDataset system =
                            (SystemReportDataset) dataset;

                    context.setVariable("summary", system);

                    JFreeChart complaintChart =
                            systemChartFactory.complaintBarChart(system);

                    JFreeChart userChart =
                            systemChartFactory.userPieChart(system);

                    String complaintImage =
                            chartGenerator.generateBase64Png(complaintChart);

                    String userImage =
                            chartGenerator.generateBase64Png(userChart);

                    context.setVariable("complaintChartImage", complaintImage);
                    context.setVariable("userChartImage", userImage);

                    templateName = "system-report";
                }

                default -> throw new IllegalArgumentException(
                        "Unsupported report type: [" + report.getReportType() + "]");
            }

            String htmlContent =
                    templateEngine.process(templateName, context);

            ByteArrayOutputStream outputStream =
                    new ByteArrayOutputStream();

            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent, null);
            renderer.layout();
            renderer.createPDF(outputStream);
            renderer.finishPDF();

            return outputStream.toByteArray();

        } catch (Exception e) {
            throw new InvalidOperationException("Failed to generate Pdf: "+e.getMessage());
        }
    }
}
