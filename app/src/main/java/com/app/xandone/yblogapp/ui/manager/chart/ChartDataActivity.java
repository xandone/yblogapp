package com.app.xandone.yblogapp.ui.manager.chart;

import android.graphics.Color;
import android.graphics.drawable.Drawable;

import com.app.xandone.baselib.cache.CacheHelper;
import com.app.xandone.baselib.utils.SimpleUtils;
import com.app.xandone.yblogapp.App;
import com.app.xandone.yblogapp.R;
import com.app.xandone.yblogapp.base.BaseWallActivity;
import com.app.xandone.yblogapp.cache.UserInfoHelper;
import com.app.xandone.yblogapp.constant.IResponseCode;
import com.app.xandone.yblogapp.constant.ISpKey;
import com.app.xandone.yblogapp.model.ManagerChartModel;
import com.app.xandone.yblogapp.model.bean.ArtInfoBean;
import com.app.xandone.yblogapp.model.event.SwitchEvent;
import com.app.xandone.yblogapp.rx.IRequestCallback;
import com.app.xandone.yblogapp.utils.LineValueFormatter;
import com.app.xandone.yblogapp.viewmodel.ModelProvider;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;
import butterknife.BindView;

/**
 * author: Admin
 * created on: 2020/10/21 16:46
 * description:
 */
public class ChartDataActivity extends BaseWallActivity implements OnChartValueSelectedListener {
    @BindView(R.id.chart1)
    LineChart chart1;

    private ManagerChartModel managerChartModel;
    private LineData mLineData;


    @Override
    public int getLayout() {
        return R.layout.act_chart_data;
    }

    @Override
    protected void wallInit() {

        initChart();

    }

    @Override
    protected void initDataObserver() {
        managerChartModel = ModelProvider.getModel(this, ManagerChartModel.class, App.sContext);

        requestData();
    }

    @Override
    protected void requestData() {
        managerChartModel.getArtInfoData(UserInfoHelper.getAdminId(), new IRequestCallback<ArtInfoBean>() {
            @Override
            public void success(ArtInfoBean artInfoBean) {
                List<Integer> list1 = new ArrayList<>();
                List<Integer> list2 = new ArrayList<>();
                List<Integer> list3 = new ArrayList<>();
                for (ArtInfoBean.YearArtDataBean yearArtDataBean : artInfoBean.getYearArtData()) {
                    list1.add(yearArtDataBean.getCodeCount());
                    list2.add(yearArtDataBean.getEssayCount());
                    list3.add(Integer.parseInt(yearArtDataBean.getYear()));
                }

                addSetData(list1, list3, "lable_1", ContextCompat.getColor(App.sContext, R.color.chart_fill_color1),
                        true, R.drawable.fade_fill_blue);
                addSetData(list2, list3, "lable_2", ContextCompat.getColor(App.sContext, R.color.chart_fill_color3),
                        true, R.drawable.fade_fill_yellow);
                chart1.invalidate();
                onLoadFinish();
            }

            @Override
            public void error(String message, int statusCode) {
                onLoadStatus(statusCode);
                //token失效时，退出到登录界面
                if (statusCode == IResponseCode.TOKEN_FAIL) {
                    EventBus.getDefault().post(new SwitchEvent(SwitchEvent.MANAGER_LOGIN_RAG));
                    CacheHelper.clearDefaultSp(App.sContext, ISpKey.ADMIN_INFO_KEY);
                    finish();
                }
            }
        });
    }

    private void initChart() {
        chart1.setViewPortOffsets(50f, 40f, 10f, 20f);
//        chart.setBackgroundColor(Color.rgb(104, 241, 175));
        chart1.setBackgroundColor(Color.WHITE);
        // no description text
        chart1.getDescription().setEnabled(false);

        // enable touch gestures
        chart1.setTouchEnabled(true);

        // enable scaling and dragging
        chart1.setDragEnabled(true);
        chart1.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        chart1.setPinchZoom(false);

        chart1.setDrawGridBackground(false);
        chart1.setMaxHighlightDistance(300);
//        chart1.setOnChartValueSelectedListener(this);

        XAxis x = chart1.getXAxis();
        x.setEnabled(false);

        YAxis axisLeft = chart1.getAxisLeft();
        axisLeft.setLabelCount(6, true);
        axisLeft.setTextColor(Color.BLACK);
        axisLeft.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        axisLeft.setDrawGridLines(true);
        axisLeft.setAxisLineColor(Color.WHITE);
        axisLeft.setYOffset(-6f);
        axisLeft.setCenterAxisLabels(false);

        chart1.getAxisRight().setEnabled(false);
        chart1.getLegend().setEnabled(false);
        chart1.animateXY(1500, 1500);
    }

    /**
     * 添加表
     *
     * @param dList
     * @param lable
     * @param color
     * @param isMainValue
     */
    private void addSetData(List<Integer> dList, List<Integer> yearList, String lable,
                            int color, boolean isMainValue, @DrawableRes int fillDrawable) {
        if (SimpleUtils.isEmpty(dList)) {
            return;
        }
        ArrayList<Entry> values = new ArrayList<>();
        for (int i = 0; i < dList.size(); i++) {
            int val = dList.get(i);
            values.add(new Entry(i, val));
        }
        LineDataSet set1 = new LineDataSet(values, lable);
        set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set1.setCubicIntensity(0.2f);
        set1.setDrawCircles(false);
        set1.setLineWidth(1.8f);
        set1.setCircleRadius(4f);
        set1.setCircleColor(color);
        set1.setHighLightColor(Color.BLACK);
        set1.setColor(color);
        if (isMainValue) {
            set1.setDrawFilled(true);
        }

        if (Utils.getSDKInt() >= 18) {
            // drawables only supported on api level 18 and above
            Drawable drawable = ContextCompat.getDrawable(App.sContext, fillDrawable);
            set1.setFillDrawable(drawable);
        } else {
            set1.setFillColor(ContextCompat.getColor(App.sContext, R.color.chart_fill_color1));
        }
        set1.setFillAlpha(50);
        set1.setDrawHorizontalHighlightIndicator(false);
        set1.setFillFormatter(new IFillFormatter() {
            @Override
            public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                return chart1.getAxisLeft().getAxisMinimum();
            }
        });
        set1.setValueFormatter(new LineValueFormatter(dList, yearList));

//        XAxis xAxis = chart1.getXAxis();
//        xAxis.setValueFormatter(new LineValueFormatter(dList, yearList));

        if (mLineData == null) {
            mLineData = new LineData();
        }
        mLineData.addDataSet(set1);
        mLineData.setValueTextSize(9f);
        //是否绘制顶点数值
        mLineData.setDrawValues(false);
        // set data
        chart1.setData(mLineData);
    }


    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}
