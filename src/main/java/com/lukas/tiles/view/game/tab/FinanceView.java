package com.lukas.tiles.view.game.tab;

import com.lukas.tiles.model.Farmer;
import com.lukas.tiles.model.Money;
import javafx.application.Platform;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.css.StyleClass;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.util.StringConverter;

import java.util.List;


public class FinanceView extends AbstractTabView {

    private final Farmer farmer;
    private final LongProperty time;
    private final LineChart<Number, Number> moneyHistoryChart;
    private final NumberAxis timeAxis;
    private final NumberAxis moneyAxis;
    private final XYChart.Series<Number, Number> series;

    // TODO: 8/27/21 show all other farmers finance
    public FinanceView(List<Farmer> farmers, LongProperty time) {
        super(new SimpleStringProperty("Finance"));
        this.farmer = farmers.get(0);
        this.time = time;
        this.timeAxis = new NumberAxis();
        this.moneyAxis = new NumberAxis();
        this.series = new XYChart.Series<>();
        this.moneyHistoryChart = new LineChart<>(timeAxis, moneyAxis);
        initialize();
    }

    @Override
    void initialize() {
        time.addListener((observable, oldValue, newValue) -> {
            if (newValue.longValue() % 10 == 0) {
                updateChart();
            }
        });
        this.moneyHistoryChart.getData().add(series);
        timeAxis.setForceZeroInRange(false);
        moneyAxis.setForceZeroInRange(false);
        moneyAxis.setTickLabelFormatter(new StringConverter<>() {
            @Override
            public String toString(Number object) {
                return Money.format(object);
            }

            @Override
            public Number fromString(String string) {
                return null;
            }
        });
        series.setName(farmer.getName());

        //Set the color
        String rgb = String.format("%d, %d, %d",
                (int) (farmer.getColor().getRed() * 255),
                (int) (farmer.getColor().getGreen() * 255),
                (int) (farmer.getColor().getBlue() * 255));
        series.nodeProperty().get().setStyle("-fx-stroke: rgba(" + rgb + ", 1);");

        // TODO: 8/27/21 make the right color 
        updateChart();

        this.getChildren().add(moneyHistoryChart);
    }

    private void updateChart() {
        series.getData().removeIf(numberNumberData -> true);

        for (int i = 0; i < 12 && i < farmer.getMoneyHistory().size(); i++) {
            series.getData().add(new XYChart.Data<>((time.get() - i * 10L), farmer.getMoneyHistory().get(farmer.getMoneyHistory().size() - (i + 1))));
        }
    }
}
