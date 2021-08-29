package com.lukas.tiles.view.game.tab;

import com.lukas.tiles.model.Farmer;
import com.lukas.tiles.model.finance.MoneyAccount;
import com.lukas.tiles.view.Style;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.util.StringConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * This class handles the finance view
 * For simplicity, no ModelView is created
 */
public class FinanceView extends AbstractTabView {

    private final List<Farmer> farmers;
    private final LongProperty time;
    private final LineChart<Number, Number> moneyHistoryChart;
    private final NumberAxis timeAxis;
    private final NumberAxis moneyAxis;
    private final List<XYChart.Series<Number, Number>> series;

    public FinanceView(List<Farmer> farmers, LongProperty time) {
        super(new SimpleStringProperty("Finance"));
        this.farmers = farmers;
        this.time = time;
        this.timeAxis = new NumberAxis();
        this.moneyAxis = new NumberAxis();
        this.series = new ArrayList<>();
        this.moneyHistoryChart = new LineChart<>(timeAxis, moneyAxis);
        initialize();
    }

    @Override
    void initialize() {
        this.getStylesheets().add(Style.getChartStyle());

        //Set up the chart
        timeAxis.setForceZeroInRange(false);
        moneyAxis.setForceZeroInRange(false);
        moneyAxis.setTickLabelFormatter(new StringConverter<>() {
            @Override
            public String toString(Number object) {
                return MoneyAccount.format(object);
            }

            @Override
            public Number fromString(String string) {
                return null;
            }
        });

        //Instantiate series
        for (Farmer value : farmers) {
            XYChart.Series<Number, Number> temp = new XYChart.Series<>();
            temp.setName(value.getName());
            series.add(temp);
        }

        this.moneyHistoryChart.getData().addAll(series);

        //Apply style
        //Sets the symbols correctly
        StringBuilder style = new StringBuilder();
        for (int i = 0; i < farmers.size(); i++) {
            style.append("symbol-color")
                    .append(i)
                    .append(": ")
                    .append(Style.convertToWebString(farmers.get(i).getColor()))
                    .append(";");
        }

        //Sets the lines correctly
        for (int i = 0; i < farmers.size(); i++) {
            String rgb = String.format("%d, %d, %d",
                    (int) (farmers.get(i).getColor().getRed() * 255),
                    (int) (farmers.get(i).getColor().getGreen() * 255),
                    (int) (farmers.get(i).getColor().getBlue() * 255));
            series.get(i).nodeProperty().get().setStyle("-fx-stroke: rgba(" + rgb + ", 1);");
        }

        moneyHistoryChart.setStyle(style.toString());

        updateChart();

        //Every 10 seconds the chart gets updated
        time.addListener((observable, oldValue, newValue) -> {
            if (newValue.longValue() % 10 == 0) {
                updateChart();
            }
        });

        this.getChildren().add(moneyHistoryChart);
    }

    /**
     * This method loads the new money data for the last 12 months
     */
    private void updateChart() {
        for (int i = 0; i < series.size(); i++) {
            series.get(i).getData().removeIf(numberNumberData -> true);

            for (int j = 0; j < 12 && j < farmers.get(i).getMoneyHistory().size(); j++) {
                series.get(i).getData().add(new XYChart.Data<>((time.get() - j * 10L), farmers.get(i).getMoneyHistory().get(farmers.get(i).getMoneyHistory().size() - (j + 1)).getAmount()));
            }
        }
    }
}
