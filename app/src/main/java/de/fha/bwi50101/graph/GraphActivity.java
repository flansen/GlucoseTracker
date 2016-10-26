package de.fha.bwi50101.graph;

import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.AxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.fha.bwi50101.R;
import de.fha.bwi50101.common.impl.AppSettingsImpl;
import de.fha.bwi50101.common.persistance.impl.RepositoryImpl;
import de.fha.bwi50101.graph.impl.GraphPresenterImpl;
import de.flhn.cleanboilerplate.MainThreadImpl;
import de.flhn.cleanboilerplate.domain.executor.impl.ThreadExecutor;

public class GraphActivity extends AppCompatActivity implements OnChartGestureListener, GraphPresenter.View {
    private static final float MIN_X_RANGE = 1000 * 60 * 60 * 18;
    private static final float MAX_X_RANGE = 1000 * 60 * 60 * 60;
    @BindView(R.id.graph)
    LineChart mChart;

    //private Typeface typeface;
    private GraphPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        ButterKnife.bind(this);
        // typeface = Typeface.createFromAsset(getAssets(), "fonts/Dosis-Medium-Zero.ttf");
        presenter = new GraphPresenterImpl(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), new AppSettingsImpl(PreferenceManager.getDefaultSharedPreferences(this)), this, RepositoryImpl.getInstance());
        presenter.viewCreated();
    }

    private float calculateYMax(List<Entry> entries) {
        float max = 0;
        for (Entry e : entries) {
            max = max > e.getY() ? max : e.getY();
        }
        return max + 15f;
    }

    private void createDayLimiter(XAxis xAxis, List<Entry> entries) {
        Set<String> days = new HashSet<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (Entry e : entries) {
            Date date = new Date((long) e.getX());
            String d = sdf.format(date);
            if (days.contains(d))
                continue;
            days.add(d);
            long timestamp = (long) e.getX();
            xAxis.addLimitLine(createLimitLine(timestamp, "", LimitLine.LimitLabelPosition.LEFT_BOTTOM, Color.LTGRAY, 2));
        }
    }

    private LimitLine createLimitLine(long limitValue, String limitText, LimitLine.LimitLabelPosition position, int color, int lineWidth) {
        LimitLine limitLine = new LimitLine(limitValue, limitText);
        limitLine.setLineWidth(lineWidth);
        limitLine.setLineColor(color);
        //limitLine.setTypeface(typeface);
        limitLine.setTextSize(12f);
        limitLine.enableDashedLine(10f, 10f, 0f);
        limitLine.setLabelPosition(position);
        return limitLine;
    }

    private void setData(List<com.github.mikephil.charting.data.Entry> values) {
        LineDataSet set1;
        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = new LineDataSet(values, "Glucose Set");
            set1.enableDashedLine(10f, 5f, 0f);
            set1.enableDashedHighlightLine(10f, 5f, 0f);
            set1.setColor(Color.BLACK);
            set1.setCircleColor(Color.BLACK);
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);
            set1.setDrawCircleHole(false);
            set1.setValueTextSize(12f);
            set1.setDrawFilled(false);
            // set1.setValueTypeface(typeface);
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            LineData data = new LineData(dataSets);
            mChart.setData(data);
        }
    }

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartLongPressed(MotionEvent me) {

    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {

    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {

    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {

    }

    @Override
    public void displayGraph(List<Entry> entryList, int lowerBound, int upperBound) {
        mChart.setOnChartGestureListener(this);
        mChart.setDrawGridBackground(false);
        // no description text
        mChart.setDescription("");
        mChart.setNoDataTextDescription(getString(R.string.no_graph_data));
        mChart.setTouchEnabled(true);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(false);
        setData(entryList);

        mChart.setPinchZoom(true);

        YAxis yAxis = mChart.getAxisLeft();
        yAxis.removeAllLimitLines();
        yAxis.addLimitLine(createLimitLine(upperBound, "Upper Limit", LimitLine.LimitLabelPosition.RIGHT_TOP, Color.RED, 4));
        yAxis.addLimitLine(createLimitLine(lowerBound, "Lower Limit", LimitLine.LimitLabelPosition.RIGHT_BOTTOM, Color.BLUE, 4));
        yAxis.setAxisMaxValue(calculateYMax(entryList));
        yAxis.setAxisMinValue(0);
        yAxis.enableGridDashedLine(10f, 10f, 0f);
        yAxis.setDrawZeroLine(false);
        yAxis.setDrawGridLines(false);
        yAxis.setDrawLimitLinesBehindData(true);
        // yAxis.setTypeface(typeface);
        mChart.getAxisRight().setEnabled(false);
        mChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        // mChart.getXAxis().setTypeface(typeface);
        mChart.getXAxis().setDrawGridLines(false);
        mChart.getXAxis().setValueFormatter(new MyAxisValueFormatter());
        createDayLimiter(mChart.getXAxis(), entryList);
        mChart.setVisibleXRange(MIN_X_RANGE, MAX_X_RANGE);
        mChart.moveViewToX(mChart.getXChartMax());
    }

    class MyAxisValueFormatter implements AxisValueFormatter {
        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            long v = (long) value;
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd HH", Locale.GERMAN);
            return sdf.format(new Date(v)) + " h";
        }

        @Override
        public int getDecimalDigits() {
            return 0;
        }
    }
}
