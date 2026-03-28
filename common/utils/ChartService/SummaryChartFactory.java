package com.cms.cmsapp.common.utils.ChartService;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.stereotype.Component;

@Component
public class SummaryChartFactory {

    private SummaryChartFactory() {}

    public JFreeChart barChart(SummaryDataset d) {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        dataset.addValue(d.getOpen(), "Complaints", "Open");
        dataset.addValue(d.getAssigned(), "Complaints", "Assigned");
        dataset.addValue(d.getInProgress(), "Complaints", "In Progress");
        dataset.addValue(d.getResolved(), "Complaints", "Resolved");
        dataset.addValue(d.getForwarded(), "Complaints", "Forwarded");
        dataset.addValue(d.getReopened(), "Complaints", "Reopened");
        dataset.addValue(d.getClosed(), "Complaints", "Closed");
        dataset.addValue(d.getCanceled(), "Complaints", "Canceled");

        return ChartFactory.createBarChart(
                "Complaint Status Distribution",
                "Status",
                "Count",
                dataset
        );
    }

    public JFreeChart pieChart(SummaryDataset d) {

        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();

        dataset.setValue("Open", d.getOpen());
        dataset.setValue("Assigned", d.getAssigned());
        dataset.setValue("In Progress", d.getInProgress());
        dataset.setValue("Resolved", d.getResolved());
        dataset.setValue("Forwarded", d.getForwarded());
        dataset.setValue("Reopened", d.getReopened());
        dataset.setValue("Closed", d.getClosed());
        dataset.setValue("Canceled", d.getCanceled());

        return ChartFactory.createPieChart(
                "Complaint Status Breakdown",
                dataset,
                true,
                true,
                false
        );
    }

    public JFreeChart departmentChart(SummaryDataset d) {

        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();

        d.getDepartmentCounts().forEach(dataset::setValue);

        return ChartFactory.createPieChart(
                "Complaints by Department",
                dataset,
                true,
                true,
                false
        );
    }

    public JFreeChart categoryChart(SummaryDataset d) {

        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();

        d.getCategoryCounts().forEach(dataset::setValue);

        return ChartFactory.createPieChart(
                "Complaints by Category",
                dataset,
                true,
                true,
                false
        );
    }
}
