package com.lukas.tiles.view.game.tab;

import com.lukas.tiles.model.Farmer;
import com.lukas.tiles.model.Money;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.util.StringConverter;

import java.util.function.Predicate;


public class FinanceView extends AbstractTabView {

    private final Farmer farmer;
    private final LongProperty time;
    private final LineChart<Number, Number> moneyHistoryChart;
    private final NumberAxis timeAxis;
    private final NumberAxis moneyAxis;
    private final XYChart.Series<Number, Number> series;


    public FinanceView(Farmer farmer, LongProperty time) {
        super(new SimpleStringProperty("Finance"));
        this.farmer = farmer;
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
