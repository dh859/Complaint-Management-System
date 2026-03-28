package com.cms.cmsapp.common.utils.ChartService;

import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.imageio.ImageIO;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.svg.SVGGraphics2D;

@Component
public class ChartGenerator {

    private ChartGenerator() {
    }

    private static final int DEFAULT_WIDTH = 400;
    private static final int DEFAULT_HEIGHT = 300;

    private static final Color PRIMARY_BLUE = Color.decode("#2563EB");
    private static final Color LIGHT_BLUE = Color.decode("#60A5FA");
    private static final Color SUCCESS_GREEN = Color.decode("#10B981");
    private static final Color WARNING_YELLOW = Color.decode("#F59E0B");
    private static final Color DANGER_RED = Color.decode("#EF4444");
    private static final Color BACKGROUND = Color.decode("#F8FAFC");
    private static final Color BORDER = Color.decode("#E2E8F0");
    private static final Color TEXT_DARK = Color.decode("#1E293B");
    private static final Color GRID = Color.decode("#CBD5E1");

    public String generateSvg(JFreeChart chart) {
        applyCmsTheme(chart);
        return generateSvg(chart, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public byte[] generateSvgBytes(JFreeChart chart) {
        return generateSvg(chart).getBytes(StandardCharsets.UTF_8);
    }

    private String generateSvg(JFreeChart chart, int width, int height) {
        SVGGraphics2D svgGenerator = new SVGGraphics2D(width, height);
        chart.draw(svgGenerator, new Rectangle2D.Double(0, 0, width, height));

        String svg = svgGenerator.getSVGElement();

        return svg.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "");
    }

    public byte[] generatePng(JFreeChart chart) {
        return generatePng(chart, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public byte[] generatePng(JFreeChart chart, int width, int height) {

        try {
            applyCmsTheme(chart);

            BufferedImage image = chart.createBufferedImage(width, height);

            ByteArrayOutputStream out = new ByteArrayOutputStream();

            ImageIO.write(image, "png", out);

            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("PNG generation failed", e);
        }
    }

    public String generateBase64Png(JFreeChart chart) {
        return Base64.getEncoder()
                .encodeToString(generatePng(chart));
    }

    public void applyCmsTheme(JFreeChart chart) {

        chart.setBackgroundPaint(BACKGROUND);
        chart.setAntiAlias(true);

        if (chart.getTitle() != null) {
            chart.getTitle().setPaint(TEXT_DARK);
            chart.getTitle().setFont(new Font("SansSerif", Font.BOLD, 18));
        }

        Plot plot = chart.getPlot();

        plot.setBackgroundPaint(Color.WHITE);
        plot.setOutlinePaint(BORDER);

        if (plot instanceof CategoryPlot categoryPlot) {

            categoryPlot.setRangeGridlinePaint(GRID);
            categoryPlot.setDomainGridlinesVisible(false);

            categoryPlot.getDomainAxis().setLabelPaint(TEXT_DARK);
            categoryPlot.getRangeAxis().setLabelPaint(TEXT_DARK);
            categoryPlot.getDomainAxis().setTickLabelPaint(TEXT_DARK);
            categoryPlot.getRangeAxis().setTickLabelPaint(TEXT_DARK);
            categoryPlot.getDomainAxis().setCategoryLabelPositions(
                    CategoryLabelPositions.createUpRotationLabelPositions(
                            Math.PI / 2
                    ));

            if (categoryPlot.getRenderer() instanceof BarRenderer barRenderer) {
                barRenderer.setSeriesPaint(0, PRIMARY_BLUE);
                barRenderer.setSeriesPaint(1, LIGHT_BLUE);
                barRenderer.setShadowVisible(false);
                barRenderer.setDrawBarOutline(false);
                barRenderer.setBarPainter(new StandardBarPainter());
            }

            if (categoryPlot.getRenderer() instanceof LineAndShapeRenderer lineRenderer) {
                lineRenderer.setSeriesPaint(0, PRIMARY_BLUE);
                lineRenderer.setSeriesStroke(0, new BasicStroke(2.5f));
                lineRenderer.setDefaultShapesVisible(true);
            }
        }

        if (plot instanceof PiePlot piePlot) {

            piePlot.setBackgroundPaint(Color.WHITE);
            piePlot.setOutlineVisible(false);

            piePlot.setSectionPaint("Open", PRIMARY_BLUE);
            piePlot.setSectionPaint("Resolved", SUCCESS_GREEN);
            piePlot.setSectionPaint("Closed", LIGHT_BLUE);
            piePlot.setSectionPaint("In Progress", WARNING_YELLOW);
            piePlot.setSectionPaint("Reopened", DANGER_RED);

            piePlot.setLabelPaint(TEXT_DARK);
            piePlot.setSimpleLabels(true);
        }

        if (plot instanceof XYPlot xyPlot) {

            xyPlot.setRangeGridlinePaint(GRID);
            xyPlot.setDomainGridlinePaint(GRID);

            if (xyPlot.getRenderer() instanceof XYLineAndShapeRenderer xyRenderer) {
                xyRenderer.setSeriesPaint(0, PRIMARY_BLUE);
                xyRenderer.setSeriesStroke(0, new BasicStroke(2.5f));
            }
        }
    }
}
