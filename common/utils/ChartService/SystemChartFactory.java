package com.cms.cmsapp.common.utils.ChartService;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.stereotype.Component;

@Component
public class SystemChartFactory {

    private SystemChartFactory() {}


    public JFreeChart complaintBarChart(SystemReportDataset d) {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        var c = d.getComplaints();

        dataset.addValue(c.getOpen(), "Complaints", "Open");
        dataset.addValue(c.getResolved(), "Complaints", "Resolved");
        dataset.addValue(c.getClosed(), "Complaints", "Closed");
        dataset.addValue(c.getInProgress(), "Complaints", "In Progress");

        return ChartFactory.createBarChart(
                "System Complaint Overview",
                "Status",
                "Count",
                dataset
        );
    }

    public JFreeChart userPieChart(SystemReportDataset d) {

        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();

        var u = d.getUsers();

        dataset.setValue("Admins", u.getAdmins());
        dataset.setValue("Managers", u.getManagers());
        dataset.setValue("Agents", u.getAgents());
        dataset.setValue("Users", u.getUsers());

        return ChartFactory.createPieChart(
                "User Distribution",
                dataset,
                true,
                true,
                false
        );
    }
}
