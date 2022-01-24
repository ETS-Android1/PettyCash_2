package com.example.pettycash;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.List;







public class HomeActivity extends Fragment {
    private PieChart pieChart;
    private LineChart lineChart;
    private LinearLayout viewBar;
    private Typeface tf;

    private int  redColor = Color.rgb(255,0,0);
    private int  redDarkColor = Color.parseColor("#980117");

    protected final String[] parties = new String[] {
            "Party A", "Party B", "Party C", "Party D", "Party E", "Party F", "Party G", "Party H",
            "Party I", "Party J", "Party K", "Party L", "Party M", "Party N", "Party O", "Party P",
            "Party Q", "Party R", "Party S", "Party T", "Party U", "Party V", "Party W", "Party X",
            "Party Y", "Party Z"
    };

    public HomeActivity() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home,container,false);
        pieChart = view.findViewById(R.id.chart11);
        lineChart = view.findViewById(R.id.home_transactions_data_layout_chart);
        viewBar = view.findViewById(R.id.home_statistics_basic_info_bar);







        prepareChart();

        prepareBar(150);
        prepareTransactionChart();

        return view;

    }

    private void prepareTransactionChart() {

        {   // // Chart Style // //

            // background color
           lineChart.setBackgroundColor(Color.WHITE);

            // disable description text
           lineChart.getDescription().setEnabled(false);

            // enable touch gestures
           lineChart.setTouchEnabled(true);

            // set listeners
           lineChart.setDrawGridBackground(false);

            // create marker to display box when values are selected
//            MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);

            // Set the marker to thelineChart
//            mv.setChartView(chart);
//           lineChart.setMarker(mv);

            // enable scaling and dragging
           lineChart.setDragEnabled(true);
           lineChart.setScaleEnabled(true);
            //lineChart.setScaleXEnabled(true);
            //lineChart.setScaleYEnabled(true);

            // force pinch zoom along both axis
           lineChart.setPinchZoom(true);
        }

        XAxis xAxis;
        {   // // X-Axis Style // //
            xAxis =lineChart.getXAxis();

            // vertical grid lines
            xAxis.enableGridDashedLine(10f, 10f, 0f);
        }

        YAxis yAxis;
        {   // // Y-Axis Style // //
            yAxis =lineChart.getAxisLeft();

            // disable dual axis (only use LEFT axis)
           lineChart.getAxisRight().setEnabled(false);

            // horizontal grid lines
            yAxis.enableGridDashedLine(10f, 10f, 0f);

            // axis range
            yAxis.setAxisMaximum(200f);
            yAxis.setAxisMinimum(-50f);
        }


        {   // // Create Limit Lines // //
            LimitLine llXAxis = new LimitLine(9f, "Index 10");
            llXAxis.setLineWidth(4f);
            llXAxis.enableDashedLine(10f, 10f, 0f);
            llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
            llXAxis.setTextSize(10f);
//            llXAxis.setTypeface(tfRegular);

            LimitLine ll1 = new LimitLine(150f, "Upper Limit");
            ll1.setLineWidth(4f);
            ll1.enableDashedLine(10f, 10f, 0f);
            ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
            ll1.setTextSize(10f);
//            ll1.setTypeface(tfRegular);

            LimitLine ll2 = new LimitLine(-30f, "Lower Limit");
            ll2.setLineWidth(4f);
            ll2.enableDashedLine(10f, 10f, 0f);
            ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
            ll2.setTextSize(10f);
//            ll2.setTypeface(tfRegular);

            // draw limit lines behind data instead of on top
            yAxis.setDrawLimitLinesBehindData(true);
            xAxis.setDrawLimitLinesBehindData(true);

            // add limit lines
            yAxis.addLimitLine(ll1);
            yAxis.addLimitLine(ll2);
            //xAxis.addLimitLine(llXAxis);
        }

        // add data
//        seekBarX.setProgress(45);
//        seekBarY.setProgress(180);
        setData(45, 180);

        // draw points over time
       lineChart.animateX(1500);

        // get the legend (only possible after setting data)
        Legend l =lineChart.getLegend();

        // draw legend entries as lines
        l.setForm(Legend.LegendForm.LINE);


    }

    private void setData(int count, float range) {

        ArrayList<Entry> values = new ArrayList<>();

        for (int i = 0; i < count; i++) {

            float val = (float) (Math.random() * range) - 30;
            values.add(new Entry(i, val, getResources().getDrawable(R.drawable.ic_launcher_foreground)));
        }

        LineDataSet set1;

        if (lineChart.getData() != null &&
               lineChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet)lineChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            set1.notifyDataSetChanged();
           lineChart.getData().notifyDataChanged();
           lineChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, "DataSet 1");

            set1.setDrawIcons(false);

            // draw dashed line
            set1.enableDashedLine(10f, 5f, 0f);

            // black lines and points
            set1.setColor(Color.BLACK);
            set1.setCircleColor(Color.BLACK);

            // line thickness and point size
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);

            // draw points as solid circles
            set1.setDrawCircleHole(false);

            // customize legend entry
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);

            // text size of values
            set1.setValueTextSize(9f);

            // draw selection line as dashed
            set1.enableDashedHighlightLine(10f, 5f, 0f);

            // set the filled area
            set1.setDrawFilled(true);
//            set1.setFillFormatter(new IFillFormatter() {
//                @Override
//                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
////                    returnlineChart.getAxisLeft().getAxisMinimum();
//                }
//            });

            // set color of filled area
            if (Utils.getSDKInt() >= 18) {
                // drawables only supported on api level 18 and above
                Drawable drawable = ContextCompat.getDrawable(getActivity(), R.drawable.fade_red);
                set1.setFillDrawable(drawable);
            } else {
                set1.setFillColor(Color.BLACK);
            }

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1); // add the data sets

            // create a data object with the data sets
            LineData data = new LineData(dataSets);

            // set data
           lineChart.setData(data);
        }
    }


    private void prepareBar(int value) {

        Drawable linebar = getResources().getDrawable(R.drawable.line_bar);
        viewBar.setLayoutParams(new LinearLayout.LayoutParams(value,10));
//        if (linebar instanceof BitmapDrawable){
//            MainActivity.createToast(this,"yes");
//        }else             MainActivity.createToast(this,"no");


//        Bitmap bitmap = ((BitmapDrawable) linebar).getBitmap();
// Scale it to 50 x 50
//        Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 50, 50, true));
//        viewBar.setBackground(d);
// Set your new, scaled drawable "d "

    }

    private void prepareChart() {
       pieChart.getDescription().setEnabled(false);
//       pieChart.setExtraOffsets(5, 10, 5, 5);

       pieChart.setDragDecelerationFrictionCoef(0.95f);
       pieChart.setDrawEntryLabels(false);

//        tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

//       pieChart.setCenterTextTypeface(Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf"));
//       pieChart.setCenterText(generateCenterSpannableText());

//       pieChart.setExtraOffsets(20.f, 0.f, 20.f, 0.f);

       pieChart.setDrawHoleEnabled(false);
//       pieChart.setHoleColor(Color.WHITE);

//       pieChart.setTransparentCircleColor(Color.WHITE);
//       pieChart.setTransparentCircleAlpha(110);

//       pieChart.setHoleRadius(58f);
//       pieChart.setTransparentCircleRadius(61f);

//       pieChart.setDrawCenterText(true);

       pieChart.setRotationAngle(0);
        // enable rotation of thepieChart by touch
       pieChart.setRotationEnabled(true);
       pieChart.setHighlightPerTapEnabled(true);

        //pieChart.setUnit(" €");
        //pieChart.setDrawUnitsInChart(true);

        // add a selection listener
//       pieChart.setOnChartValueSelectedListener(this);


       pieChart.animateY(1400, Easing.EaseInOutQuad);

        //pieChart.spin(2000, 0, 360);

        Legend l =pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setEnabled(true);
        l.setDrawInside(false);

        ArrayList<PieEntry> entries = new ArrayList<>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // thepieChart.
        PieEntry open = new PieEntry(10,"open");
        PieEntry closed = new PieEntry(90,"closed");

        entries.add(open);
        entries.add(closed);

        PieDataSet dataSet = new PieDataSet(entries,"");

//        PieData pieData = new PieData(dataSet);
        dataSet.setSliceSpace(0f);
        dataSet.setSelectionShift(0f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<>();

//        for (int c : ColorTemplate.VORDIPLOM_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.JOYFUL_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.COLORFUL_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.LIBERTY_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.PASTEL_COLORS)
//            colors.add(c);
//
//        colors.add(ColorTemplate.getHoloBlue());
        colors.add(redColor);
        colors.add(redDarkColor);

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);


        dataSet.setValueLinePart1OffsetPercentage(80.f);
        dataSet.setValueLinePart1Length(0.2f);
        dataSet.setValueLinePart2Length(0.4f);

        //dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
        data.setValueTypeface(tf);
       pieChart.setDrawCenterText(false);
       pieChart.setDrawMarkers(false);
       pieChart.getDescription().setEnabled(false);
       pieChart.setData(data);

    }





    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("MPAndroidChart\ndeveloped by Philipp Jahoda");
        s.setSpan(new RelativeSizeSpan(1.5f), 0, 14, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
        s.setSpan(new RelativeSizeSpan(.65f), 14, s.length() - 15, 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
        return s;
    }


    public class LineBar extends View {
        public LineBar(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
//            LinearGradient linearGradient = new LinearGradient(this.getLeft(),this.getTop() + (this.getHeight() / 2),this.getRight(),this.getBottom(),R.color.black);
        }
    }
//
//
//package com.xxmassdeveloper.mpchartexample.custom;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.widget.TextView;
//
//import com.github.mikephil.charting.components.MarkerView;
//import com.github.mikephil.charting.data.CandleEntry;
//import com.github.mikephil.charting.data.Entry;
//import com.github.mikephil.charting.highlight.Highlight;
//import com.github.mikephil.charting.utils.MPPointF;
//import com.github.mikephil.charting.utils.Utils;
//import com.xxmassdeveloper.mpchartexample.R;
//
//    /**
//     * Custom implementation of the MarkerView.
//     *
//     * @author Philipp Jahoda
//     */
//    @SuppressLint("ViewConstructor")
//    public class MyMarkerView extends MarkerView {
//
//        private final TextView tvContent;
//
//        public MyMarkerView(Context context, int layoutResource) {
//            super(context, layoutResource);
//
//            tvContent = findViewById(R.id.tvContent);
//        }
//
//        // runs every time the MarkerView is redrawn, can be used to update the
//        // content (user-interface)
//        @Override
//        public void refreshContent(Entry e, Highlight highlight) {
//
//            if (e instanceof CandleEntry) {
//
//                CandleEntry ce = (CandleEntry) e;
//
//                tvContent.setText(Utils.formatNumber(ce.getHigh(), 0, true));
//            } else {
//
//                tvContent.setText(Utils.formatNumber(e.getY(), 0, true));
//            }
//
//            super.refreshContent(e, highlight);
//        }
//
//        @Override
//        public MPPointF getOffset() {
//            return new MPPointF(-(getWidth() / 2), -getHeight());
//        }
//    }
//


}
