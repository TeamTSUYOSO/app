package jp.ac.titech.itpro.sdl.tsuyoso2.Calendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import jp.ac.titech.itpro.sdl.tsuyoso2.R;

@SuppressWarnings("deprecation")
@SuppressLint("NewApi")

/**
 * Created by kayo on 2016/10/31.
 */
public class CalendarView extends LinearLayout{

    private OnDateClickListener mDateListener;
    private OnNextBackClickListener mNextBackListener;

    private Context context;

    // 色は、attrs.xmlで定義された、属性値でxmlに指定可能
    // 今日のフォント色
    private static int TODAY_COLOR;
    // 通常のフォント色
    private static int DEFAULT_COLOR;
    // 日曜のフォント色
    private static int SUN_COLOR;
    // 土曜のフォント色
    private static int SAT_COLOR;
    // 今週の背景色
    private static int TODAY_BACKGROUND_COLOR;
    // 通常の背景色
    private static int DEFAULT_BACKGROUND_COLOR;

    // ≫　and　≪　の背景色
    private static int FOCUSED_BACKGROUND_COLOR;

    // クリック状態時の背景色
    private static int BUTTON_BACKGROUND_COLOR;

    //週のはじめの曜日
    private static int BEGINNING_DAY_OF_WEEK;

    // タイトル部分の構成Ｖｉｅｗ
    private LinearLayout mTitleLayout;
    private TextView mTitleView;
    private Button btnFwd;
    private Button btnBack;

    // 曜日の見出し部分の構成Ｖｉｅｗ
    private LinearLayout mWeekLayout;

    // 日付の表示部分、一つのLinearLayoutの要素として一行（週毎）ずつ配列へ代入。
    private ArrayList<LinearLayout> mWeeks;

    public CalendarView(Context context) {
        this(context, null);
    }

    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;
        this.setOrientation(VERTICAL);
        mWeeks = new ArrayList<LinearLayout>();
        LinearLayout weeklineLayout;


        // 属性値の読み取り
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CalendarView);
        TODAY_COLOR = array.getColor(R.styleable.CalendarView_TODAY_COLOR, Color.MAGENTA);
        DEFAULT_COLOR = array.getColor(R.styleable.CalendarView_DEFAULT_COLOR, Color.DKGRAY);
        SUN_COLOR = array.getColor(R.styleable.CalendarView_SUN_COLOR, Color.RED);
        SAT_COLOR = array.getColor(R.styleable.CalendarView_SAT_COLOR, Color.BLUE);
        TODAY_BACKGROUND_COLOR = array.getColor(
                R.styleable.CalendarView_TODAY_BACKGROUND_COLOR, Color.LTGRAY);
        DEFAULT_BACKGROUND_COLOR = array.getColor(
                R.styleable.CalendarView_DEFAULT_BACKGROUND_COLOR,
                Color.TRANSPARENT);
        FOCUSED_BACKGROUND_COLOR = array.getColor(
                R.styleable.CalendarView_FOCUSED_BACKGROUND_COLOR,
                Color.argb(0x66, 0, 0xff, 0));

        BUTTON_BACKGROUND_COLOR = array.getColor(
                R.styleable.CalendarView_BUTTON_BACKGROUND_COLOR,
                Color.TRANSPARENT);

        int dayofweek = array.getInt(
                R.styleable.CalendarView_BEGINNING_DAY_OF_WEEK, 0);
        if (BEGINNING_DAY_OF_WEEK != 0) {
            BEGINNING_DAY_OF_WEEK = MonthlyCalendar.getDayOfWeek(dayofweek);
            MonthlyCalendar.setBeginningDayOfWeek(BEGINNING_DAY_OF_WEEK);
        }
        array.recycle();

        // タイトル部分
        Log.d("CalendarView", "タイトル部　年月表示");
        addView(createTitleView(context), new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        // 曜日見出し部分
        addView(createWeekViews(context), new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        // 日付部分　 最大6行必要
        for (int i = 0; i < MonthlyCalendar.MAX_WEEK; i++) {
            weeklineLayout = createDayViews(context);
            mWeeks.add(weeklineLayout);
            addView(weeklineLayout, new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        }
    }

    /**
     * 年月表示用のタイトルViewを生成する
     *
     * @param context
     *            context
     */
    private LinearLayout createTitleView(Context context) {

        mTitleLayout = new LinearLayout(context);
        // << ボタン
        btnBack = new Button(context);
        btnBack.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT, 5)); // 末尾のパラメタはweight
        btnBack.setBackgroundColor(BUTTON_BACKGROUND_COLOR);
        btnBack.setText("<<");
        btnBack.setFocusable(false);
        btnBack.setFocusableInTouchMode(false);

        mTitleLayout.addView(btnBack);

        float scaleDensity = context.getResources().getDisplayMetrics().density;

        mTitleView = new TextView(context);
        mTitleView.setTextAppearance(context,android.R.style.TextAppearance_Large);
        mTitleView.setGravity(Gravity.CENTER_HORIZONTAL); // 中央に表示
        mTitleView.setTypeface(null, Typeface.BOLD); // 太字
        mTitleView.setPadding(0, 0, 0, (int) (scaleDensity * 16));
        mTitleLayout.addView(mTitleView);

        // >> ボタン
        btnFwd = new Button(context);
        btnFwd.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT, 5)); // 末尾のパラメタはweight
        btnFwd.setBackgroundColor(BUTTON_BACKGROUND_COLOR);
        btnFwd.setFocusable(false);
        btnFwd.setFocusableInTouchMode(false);
        btnFwd.setText(">>");
        mTitleLayout.addView(btnFwd);
        return mTitleLayout;
    }

    /**
     * 曜日表示用のViewを生成する
     *
     * @param context
     *            context
     */
    private LinearLayout createWeekViews(Context context) {

        mWeekLayout = new LinearLayout(context);
        float scaleDensity = context.getResources().getDisplayMetrics().density;

        for (int i = 0; i < MonthlyCalendar.WEEKDAYS; i++) {
            TextView textView = new TextView(context);
            textView.setTextAppearance(context,
                    android.R.style.TextAppearance_Medium);
            textView.setGravity(Gravity.RIGHT);
            textView.setPadding(0, 0, (int) (scaleDensity * 4), 0);
            LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(0,
                    LayoutParams.WRAP_CONTENT);
            llp.weight = 1;
            mWeekLayout.addView(textView, llp);
        }

        return mWeekLayout;
    }

    /**
     * 日付表示用のViewを生成する
     *
     * @param context
     *            context
     */
    private LinearLayout createDayViews(Context context) {

        float scaleDensity = context.getResources().getDisplayMetrics().density;
        LinearLayout weeklineLayout = new LinearLayout(context);

        // 1週間分の日付ビュー作成
        for (int i = 0; i < MonthlyCalendar.WEEKDAYS; i++) {
            //View view = context.getLayoutInflater().inflate(R.layout.row_ingredients, null);
            LinearLayout dayView = new LinearLayout(context);
            dayView.setOrientation(LinearLayout.VERTICAL);
            dayView.setClickable(true);

            //日付部分
            TextView dayNumView = new TextView(context);
            dayNumView.setGravity(Gravity.TOP | Gravity.RIGHT);
            dayNumView.setPadding(0, (int) (scaleDensity * 4), (int) (scaleDensity * 4), 0);
            LinearLayout.LayoutParams llp_day = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT);
            dayView.addView(dayNumView, llp_day);

            //その他テキスト表示部分
            TextView dayDetailView = new TextView(context);
            dayDetailView.setGravity(Gravity.CENTER);
            dayDetailView.setPadding((int) (scaleDensity * 4), (int) (scaleDensity * 4), (int) (scaleDensity * 4), (int) (scaleDensity * 4));
            LinearLayout.LayoutParams llp_recipe = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,0);
            llp_recipe.weight = 1;
            dayView.addView(dayDetailView, llp_recipe);

            LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(0,
                    (int) (scaleDensity * 70));
            llp.weight = 1;
            weeklineLayout.addView(dayView, llp);
        }
        return weeklineLayout;
    }

    /**
     * 年と月を指定して、カレンダーの表示を初期化する
     *
     * @param year
     *            年の指定
     * @param month
     *            月の指定
     * @param flexibleLine
     *            日数に応じて、行数を可変にするかどうか？true：可変
     *
     */
    public void set(int year, int month, boolean flexibleLine,boolean hasNextbackButton) {
        setTitle(year, month,hasNextbackButton);
        setWeeks();
        setDays(year, month, flexibleLine);
    }

    /**
     * タイトルを設定する
     *
     * @param year
     *            年の指定
     * @param month
     *            月の指定
     * @param hasNextbackButton
     *            ≪　や　≫　の翌前月へ遷移するボタンを付けるかの指定
     */
    private void setTitle(final int year, final int month, boolean hasNextbackButton) {

        if(hasNextbackButton){

            // クリック時に背景を変更する為に、stateの準備
            StateListDrawable stateBackDrawable = new StateListDrawable();
            StateListDrawable stateFwdDrawable = new StateListDrawable();
            Drawable tap = new ColorDrawable(FOCUSED_BACKGROUND_COLOR);
            stateBackDrawable.addState(
                    new int[] { android.R.attr.state_selected }, tap);
            stateBackDrawable.addState(
                    new int[] { android.R.attr.state_pressed }, tap);
            stateFwdDrawable.addState(
                    new int[] { android.R.attr.state_selected }, tap);
            stateFwdDrawable.addState(
                    new int[] { android.R.attr.state_pressed }, tap);
            btnBack.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    int backyear = -1;
                    int backmonth = -1;
                    if (mNextBackListener != null) {
                        if(month == 1){
                            backyear = year -1;
                            backmonth = 12;
                        } else {
                            backyear = year;
                            backmonth = month - 1;
                        }
                        mNextBackListener.onNextBackClick(backyear, backmonth, MonthlyCalendar.BACK_MONTH);
                    }
                }
            });

            // クリック時に背景を変更するselectorのセット
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                btnBack.setBackgroundDrawable(stateBackDrawable);
            } else {
                btnBack.setBackground(stateBackDrawable);
            }

            btnFwd.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    int nextyear = -1;
                    int nextmonth = -1;
                    if (mNextBackListener != null) {
                        if(month == 12){
                            nextyear = year + 1;
                            nextmonth = 1;
                        } else {
                            nextyear = year;
                            nextmonth = month + 1;
                        }
                        mNextBackListener.onNextBackClick(nextyear, nextmonth, MonthlyCalendar.NEXT_MONTH);
                    }
                }
            });

            // クリック時に背景を変更するselectorのセット
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                btnFwd.setBackgroundDrawable(stateFwdDrawable);
            } else {
                btnFwd.setBackground(stateFwdDrawable);
            }
        } else {
            btnBack.setVisibility(View.INVISIBLE);
            btnFwd.setVisibility(View.INVISIBLE);
        }
        // 年月フォーマット文字列
        mTitleView.setText(year + "年" + month + "月");
    }

    /**
     * 曜日を設定する（曜日の見出し）
     */
    private void setWeeks() {


        String[] weeks = MonthlyCalendar.getWeeks();
        for (int i = 0; i < MonthlyCalendar.WEEKDAYS; i++) {
            TextView textView = (TextView) mWeekLayout.getChildAt(i);
            textView.setText(weeks[i]); // テキストに曜日を表示
            if (i == MonthlyCalendar.getSunSatPosition(MonthlyCalendar.SUNDAY)) {
                textView.setTextColor(SUN_COLOR);
            } else if (i == MonthlyCalendar
                    .getSunSatPosition(MonthlyCalendar.SATURDAY)) {
                textView.setTextColor(SAT_COLOR);
            }
        }
    }

    /**
     * 日付を設定する
     *
     * @param year
     *            年の指定
     * @param month
     *            月の指定
     * @param flexibleLine
     *            日数に応じて、行数を可変にするかどうか？true：可変、false ６行固定
     */
    private void setDays(final int year, final int month, boolean flexibleLine) {

        MonthlyCalendar calendar = new MonthlyCalendar(year, month);

        int[][] calendarDay = calendar.getCalendarMatrix();
        boolean isNextMonth = calendar.isNextMonth();
        boolean isThisMonth = calendar.isThisMonth();
        boolean isBackMonth = calendar.isBackMonth();
        int maxWeek = calendar.getMaxWeek(flexibleLine);

        int thisDay = MonthlyCalendar.today(MonthlyCalendar.TODAY_DAY);
        int sun_position = MonthlyCalendar
                .getSunSatPosition(MonthlyCalendar.SUNDAY);
        int sat_position = MonthlyCalendar
                .getSunSatPosition(MonthlyCalendar.SATURDAY);

        // 日付のセット
        for (int i = 0; i < maxWeek; i++) {
            LinearLayout weekLayout = mWeeks.get(i);
            weekLayout.setBackgroundColor(DEFAULT_BACKGROUND_COLOR);
            for (int j = 0; j < MonthlyCalendar.WEEKDAYS; j++) {

                // クリック時に背景を変更する為に、stateの準備
                StateListDrawable stateDrawable = new StateListDrawable();
                Drawable tap = new ColorDrawable(FOCUSED_BACKGROUND_COLOR);
                stateDrawable.addState(
                        new int[] { android.R.attr.state_selected }, tap);
                stateDrawable.addState(
                        new int[] { android.R.attr.state_pressed }, tap);

                final LinearLayout dayView = (LinearLayout) weekLayout.getChildAt(j);
                final TextView dayTextView = (TextView) dayView.getChildAt(0);
                int c = calendarDay[i][j];

                if (c == 0) {
                    dayTextView.setText(" ");
                } else if (isThisMonth && c == thisDay) {
                    dayTextView.setTextAppearance(context,
                            android.R.style.TextAppearance_Medium);
                    dayTextView.setTextColor(TODAY_COLOR);
                    dayTextView.setTypeface(null, Typeface.BOLD);
                    dayTextView.setText(String.valueOf(c));

                    // クリック時に背景を変更するselectorのセット
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                        dayView.setBackgroundDrawable(stateDrawable);
                    } else {
                        dayView.setBackground(stateDrawable);
                    }
                    weekLayout.setBackgroundColor(TODAY_BACKGROUND_COLOR);

                } else if (isNextMonth && c == -thisDay && c < -20) {
                    dayTextView.setTextAppearance(context,
                            android.R.style.TextAppearance_Medium);
                    dayTextView.setTextColor(TODAY_COLOR);
                    dayTextView.setTypeface(null, Typeface.BOLD);
                    dayTextView.setText(String.valueOf(Math.abs(c)));

                    // クリック時に背景を変更するselectorのセット
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                        dayView.setBackgroundDrawable(stateDrawable);
                    } else {
                        dayView.setBackground(stateDrawable);
                    }

                    weekLayout.setBackgroundColor(TODAY_BACKGROUND_COLOR);

                } else if (isBackMonth && c == -thisDay && c >= -20) {
                    dayTextView.setTextAppearance(context,
                            android.R.style.TextAppearance_Medium);
                    dayTextView.setTextColor(TODAY_COLOR);
                    dayTextView.setTypeface(null, Typeface.BOLD);
                    dayTextView.setText(String.valueOf(Math.abs(c)));

                    // クリック時に背景を変更するselectorのセット
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                        dayView.setBackgroundDrawable(stateDrawable);
                    } else {
                        dayView.setBackground(stateDrawable);
                    }

                    weekLayout.setBackgroundColor(TODAY_BACKGROUND_COLOR);

                } else {
                    dayTextView.setTextAppearance(context,
                            android.R.style.TextAppearance_Medium);
                    if (j == sun_position) {
                        dayTextView.setTextColor(SUN_COLOR);
                    } else if (j == sat_position) {
                        dayTextView.setTextColor(SAT_COLOR);
                    } else {
                        dayTextView.setTextColor(DEFAULT_COLOR);
                    }
                    dayTextView.setTypeface(null, Typeface.NORMAL);
                    dayTextView.setText(String.valueOf(Math.abs(c)));

                    // クリック時に背景を変更するselectorのセット
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                        dayView.setBackgroundDrawable(stateDrawable);
                    } else {
                        dayView.setBackground(stateDrawable);
                    }

                }
                dayView.setTag(String.valueOf(c));
                dayView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            if (mDateListener != null) {
                                int day = Integer.valueOf((String) dayView.getTag());
                                if (day < -20) {
                                    //前月対応
                                    int backMonth = month - 1;
                                    int backYear = year;
                                    if (backMonth == 0) {
                                        backYear = backYear - 1;
                                        backMonth = 12;
                                    }
                                    mDateListener.onDateClick(v, backYear, backMonth, Math.abs(day));
                                } else if (day < 0 && day >= -20) {
                                    //翌月対応
                                    int nextMonth = month + 1;
                                    int nextYear = year;
                                    if (nextMonth == 13) {
                                        nextMonth = 1;
                                        nextYear = nextYear + 1;
                                    }
                                    mDateListener.onDateClick(v, nextYear, nextMonth, Math.abs(day));
                                } else {
                                    mDateListener.onDateClick(v, year, month, day);
                                }
                            }
                        }
                        catch (Exception e){

                        }
                    }
                });
            }
            if(maxWeek < calendarDay.length){
                //最終行が空の場合にリセット
                weekLayout = mWeeks.get(calendarDay.length-1);
                weekLayout.setBackgroundColor(DEFAULT_BACKGROUND_COLOR);
                for (int j = 0; j < MonthlyCalendar.WEEKDAYS; j++) {
                    final LinearLayout dayView = (LinearLayout) weekLayout.getChildAt(j);
                    final TextView dayTextView = (TextView) dayView.getChildAt(0);
                    dayTextView.setText(" ");
                }
            }
        }
    }

    /**
     * 指定年月のカレンダーの日付一覧を取得
     *
     * @param dateFormat
     *        日付形式の指定
     * @return String[] 表示されている日付
     */
    public String[] getDates(final int year, final int month, boolean flexibleLine, String dateFormat) {
        MonthlyCalendar calendar = new MonthlyCalendar(year, month);
        int[][] calendarDay = calendar.getCalendarMatrix();
        int maxWeek = calendar.getMaxWeek(flexibleLine);

        ArrayList<String> formatedDates = new ArrayList<String>();
        SimpleDateFormat dstDateFormat = new SimpleDateFormat(dateFormat);
        SimpleDateFormat srcDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for(int row = 0; row < maxWeek; row++){
            for(int col = 0; col < calendarDay[row].length; col++){
                int day = calendarDay[row][col];
                String date="";
                if(day > 0){
                    //当月
                    date = year + "-" + month + "-" + day;
                } else if (day < -20) {
                    //前月
                    int backMonth = month - 1;
                    int backYear = year;
                    int backDay = day * -1;
                    if (backMonth == 0) {
                        backYear = backYear - 1;
                        backMonth = 12;
                    }
                    date = backYear + "-" + backMonth + "-" + backDay;
                } else if (day < 0 && day >= -20) {
                    //翌月
                    int nextMonth = month + 1;
                    int nextYear = year;
                    int nextDay = day * -1;
                    if (nextMonth == 13) {
                        nextMonth = 1;
                        nextYear = nextYear + 1;
                    }
                    date = nextYear + "-" + nextMonth + "-" + nextDay;
                } else {
                    continue;
                }
                try {
                    //dateFormatに整形して保存
                    formatedDates.add(dstDateFormat.format(srcDateFormat.parse(date)));
                }catch(java.text.ParseException e){

                }
            }
        }

        String[] resultArray = new String[formatedDates.size()];
        formatedDates.toArray(resultArray);
        return resultArray;
    }

    /**
     * カレンダー日付部分のDetail部分にテキストを表示する
     *
     * @param detailTexts
     *        detailTexts["yyyy-MM-dd"] = detailText
     */
    public void setDetailText(int year, int month, String dateFormat, HashMap<String, String> detailTexts) {
        MonthlyCalendar calendar = new MonthlyCalendar(year, month);
        int[][] calendarDay = calendar.getCalendarMatrix();

        for (int i = 0; i < mWeeks.size(); i++) {
            LinearLayout weekLayout = mWeeks.get(i);
            for (int j = 0; j < MonthlyCalendar.WEEKDAYS; j++) {
                final LinearLayout dayView = (LinearLayout) weekLayout.getChildAt(j);
                final TextView detailTextView = (TextView) dayView.getChildAt(1);

                int day = calendarDay[i][j];
                SimpleDateFormat dstDateFormat = new SimpleDateFormat(dateFormat);
                SimpleDateFormat srcDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String formatedDate = ""; //dateFormatに合わせた日付
                String date;

                if(day > 0){
                    //当月
                    date = year + "-" + month + "-" + day;
                } else if (day < -20) {
                    //前月
                    int backMonth = month - 1;
                    int backYear = year;
                    int backDay = day * -1;
                    if (backMonth == 0) {
                        backYear = backYear - 1;
                        backMonth = 12;
                    }
                    date = backYear + "-" + backMonth + "-" + backDay;
                } else if (day < 0 && day >= -20) {
                    //翌月
                    int nextMonth = month + 1;
                    int nextYear = year;
                    int nextDay = day * -1;
                    if (nextMonth == 13) {
                        nextMonth = 1;
                        nextYear = nextYear + 1;
                    }
                    date = nextYear + "-" + nextMonth + "-" + nextDay;
                } else {
                    detailTextView.setText(" ");
                    continue;
                }
                try {
                    formatedDate = dstDateFormat.format(srcDateFormat.parse(date));
                    if(!detailTexts.containsKey(formatedDate)) {
                        detailTextView.setText(" ");
                    } else {
                        detailTextView.setText(detailTexts.get(formatedDate));
                    }
                }catch(java.text.ParseException e){

                }
            }
        }
    }

    /**
     * リスナーを追加する
     *
     * @param listener
     */
    public void setOnDateClickListener(OnDateClickListener listener) {
        this.mDateListener = listener;
    }
    public void setOnNextBackClickListener(OnNextBackClickListener listener) {
        this.mNextBackListener = listener;
    }
    /**
     * リスナーを削除する
     */
    public void removeOnDateClickListener() {
        this.mDateListener = null;
    }
    public void removeOnNextBackClickListener() {
        this.mNextBackListener = null;
    }

}
