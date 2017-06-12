# RingStatisticsView
### 环形百分比统计图,不多说先上图  
![github](https://github.com/LiShiHui24740/RingStatisticsView/blob/master/Simple/img/1.jpg)  
![github](https://github.com/LiShiHui24740/RingStatisticsView/blob/master/Simple/img/2.jpg)
# 使用  
```
//在布局中
<com.ebanswers.lsh.RingStatisticsView
        android:id="@+id/id_rsv"
        android:layout_width="300dp"
        android:layout_height="300dp"
        app:rsv_CenterNumber="5000"
        app:rsv_CenterText="工资"
        app:rsv_CenterTextColor="#ff0000"
        app:rsv_CenterNumberColor="#11ddff"
        />  
        
//在Activity中
RingStatisticsView ringStatisticsView = (RingStatisticsView) findViewById(R.id.id_rsv);
        ringStatisticsView.setPercentAndColors(new float[]{0.2f,0.2f,0.3f,0.3f},new int[]{Color.parseColor("#F9AA28"),         Color.parseColor("#009752"), Color.parseColor("#2EC1FB"), Color.parseColor("#FA6723")});
        ringStatisticsView.refresh();
```
# 自定义属性说明  
```
    <declare-styleable name="RingStatisticsView">
        //设置环形宽度
        <attr name="rsv_RingWidth" format="reference|dimension"/>
        //设置中间标题文字
        <attr name="rsv_CenterText" format="reference|string"/>
        //设置中间数字
        <attr name="rsv_CenterNumber" format="reference|string"/>
        //设置中间标题文字颜色
        <attr name="rsv_CenterTextColor" format="reference|color"/>
        //设置中间数字颜色
        <attr name="rsv_CenterNumberColor" format="reference|color"/>
        //设置中间文字尺寸
        <attr name="rsv_CenterTextSize" format="reference|dimension"/>
        //设置中间数字尺寸
        <attr name="rsv_CenterNumberSize" format="reference|dimension"/>
        //设置百分比文字尺寸
        <attr name="rsv_PercentTextSize" format="reference|dimension"/>
    </declare-styleable>
```
# 代码中动态设置属性
```
public void setRingWidth(float ringWindth) {
        mRingWidth = ringWindth;
    }

    public void setCenterText(String text) {
        mstr_total_text = text;
    }

    public void setCenterNumber(String number) {
        mstr_total_number = number;
    }

    public void setCenterTextColor(int color) {
        mTextColor1 = color;
    }

    public void setCenterNumberColor(int color) {
        mTextColor2 = color;
    }

    public void setCenterTextSize(int size) {
        textSize1 = size;
    }

    public void setCenterNumberSize(int size) {
        textSize2 = size;
    }

    public void setPercentTextSize(int size) {
        textSize3 = size;
    }

    public void refresh() {
        postInvalidate();
    }
```
