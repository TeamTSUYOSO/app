package jp.ac.titech.itpro.sdl.tsuyoso2.Calendar;

import android.annotation.SuppressLint;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by kayo on 2016/10/31.
 */
public class MonthlyCalendar {
    public static final int SUNDAY = Calendar.SUNDAY;
    public static final int MONDAY = Calendar.MONDAY;
    public static final int TUESDAY = Calendar.TUESDAY;
    public static final int WEDNESDAY = Calendar.WEDNESDAY;
    public static final int THURSDAY = Calendar.THURSDAY;
    public static final int FRIDAY = Calendar.FRIDAY;
    public static final int SATURDAY = Calendar.SATURDAY;

    // 定数
    public static final int TODAY_YEAR = 11;
    public static final int TODAY_MONTH = 12;
    public static final int TODAY_DAY = 13;

    public static final int BACK_MONTH = 14;
    public static final int NEXT_MONTH = 15;

    // 一週間の日数
    public static final int WEEKDAYS = 7;

    // 週行の最大行数
    public static final int MAX_WEEK = 6;

    // BEGINNING_DAY_OF_WEEK の値をsetterで変更することで、週の頭の曜日を変更可。
    private static int BEGINNING_DAY_OF_WEEK = SUNDAY;

    private int year;
    private int month;

    private int[][] calendarMatrix = new int[MAX_WEEK][WEEKDAYS];

    private int startDate;//月の始まりの曜日　日：1、月：2、土：7
    private int lastDate;//最後の日付
    private int nextStartDate;//翌月の始まりの曜日　日：1、月：2、土：7
    private int backLastDate;//前月の最後の日付
    private int maxWeekLine;//最大週の行数　0：１行～5：６行

    /**
     * カレンダー表作成の為のコンストラクタ
     *
     * @param year
     *            西暦年(..., 2013, 2014, 2015, ...)
     * @param month
     *            月(1, 2, 3, ..., 10, 11, 12)
     */
    public MonthlyCalendar(int year, int month) {
        this.year = year;
        this.month = month;
        calcFields(true);
    }

    // インスタンス生成時の初期化。主に、カレンダー用の二次元配列の作成。
    private void calcFields(boolean hasDaysOfNextBackMonth) {

        Calendar calendar = Calendar.getInstance();
        calendar.clear();

        // 月の初めの曜日を求めます。
        calendar.set(year, month - 1, 1);
        startDate = calendar.get(Calendar.DAY_OF_WEEK);

        // 月末の日付を求めます。
        calendar.add(Calendar.MONTH, 1);
        nextStartDate = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.add(Calendar.DATE, -1);
        lastDate = calendar.get(Calendar.DATE);

        // カレンダー表を作成します。
        int row = 0;
        int column = startDate - BEGINNING_DAY_OF_WEEK;
        if (column < 0)
            column = 7 + column;

        for (int date = 1; date <= lastDate; date++) {
            calendarMatrix[row][column] = date;
            if (column == MAX_WEEK) {
                row++;
                column = 0;
            } else {
                column++;
            }
            maxWeekLine = row;
        }

        if (hasDaysOfNextBackMonth) {

            // 前月の月終わりの日付を付加します。当月日付以外はマイナスを付ける。
            calendar.set(year, month - 1, 1);
            calendar.add(Calendar.DATE, -1);
            backLastDate = - calendar.get(Calendar.DATE);

            int back_column = startDate - BEGINNING_DAY_OF_WEEK;
            if (back_column < 0) back_column = 7 + back_column;
            if (back_column != 0) {
                for (int j = back_column - 1; j >= 0; j--) {
                    calendarMatrix[0][j] = backLastDate;
                    backLastDate = backLastDate + 1;
                }
            }
            //翌月の月初めの日付を付加します。当月日付以外はマイナスを付ける。
            int next_column = (BEGINNING_DAY_OF_WEEK - nextStartDate);
            if(next_column < 0) next_column = next_column + 7;
            int date = -1;
            if (next_column != 0) {
                for (int k = next_column - 1; k >= 0; k--) {
                    calendarMatrix[maxWeekLine][(WEEKDAYS-1)-k] = date;
                    date = date - 1;
                }
            }
            if(maxWeekLine < 5){
                for(int weekLineRow = maxWeekLine + 1;weekLineRow < 6;weekLineRow++){
                    for (int n = 0; n < 7; n++) {
                        calendarMatrix[weekLineRow][n] = date;
                        date = date - 1;
                    }
                }
            }
        }

    }

    /**
     * 週の見出し配列取得
     *
     * @param
     *
     * @return String[] 左から並べる週の見出しのＳｔｒｉｎｇ配列　0～6
     */
    @SuppressLint("SimpleDateFormat")
    public static String[] getWeeks() {
        String[] strweek = new String[7];
        Calendar week = Calendar.getInstance();
        week.set(Calendar.DAY_OF_WEEK, BEGINNING_DAY_OF_WEEK); // 週の頭をセット
        SimpleDateFormat weekFormatter = new SimpleDateFormat("E");
        for (int i = 0; i < WEEKDAYS; i++) {
            strweek[i] = weekFormatter.format(week.getTime());
            week.add(Calendar.DAY_OF_MONTH, 1);
        }
        return strweek;
    }

    /**
     * 日付の行数
     *
     * @param flexibleLine
     *            true:日数に応じて変化。false:最大値の６行で固定。
     * @return int 行数の返却
     */
    public int getMaxWeek(boolean flexibleLine) {
        if(flexibleLine){
            return maxWeekLine + 1;
        }else{
            return MAX_WEEK;
        }
    }

    /**
     * 今日の日付取得
     *
     * @param today_const
     *            MonthlyCalendar.TODAY_YEAR etc
     * @return int TODAY_YEARの場合は、年が返値。
     */
    public static int today(int today_const) {
        int today = 0;
        Calendar todayCalendar = Calendar.getInstance();
        switch (today_const) {
            case TODAY_YEAR:
                today = todayCalendar.get(Calendar.YEAR);
                break;
            case TODAY_MONTH:
                today = todayCalendar.get(Calendar.MONTH) + 1;
                break;
            case TODAY_DAY:
                today = todayCalendar.get(Calendar.DATE);
                break;
            default:
                today = 0;
                break;
        }
        return today;
    }

    public static int getDayOfWeek(int dayofweek) {
        switch (dayofweek) {
            case 0:
                dayofweek = SUNDAY;
                break;
            case 1:
                dayofweek = MONDAY;
                break;
            case 2:
                dayofweek = TUESDAY;
                break;
            case 3:
                dayofweek = WEDNESDAY;
                break;
            case 4:
                dayofweek = THURSDAY;
                break;
            case 5:
                dayofweek = FRIDAY;
                break;
            case 6:
                dayofweek = SATURDAY;
                break;
            default:
                dayofweek = -1;
        }
        return dayofweek;
    }

    /**
     * （土日の色を変える為の）ポジション取得
     *
     * @param day_of_week
     *            MonthlyCalendar.SUNDAY or MonthlyCalendar.SATURDAY
     * @return position 土日の位置、左から、一週間の0～6で表す。
     */
    public static int getSunSatPosition(int day_of_week) {

        int sun_position = -1;
        int sat_position = -1;

        switch (BEGINNING_DAY_OF_WEEK) {
            case SUNDAY:
                sat_position = 6;
                sun_position = 0;
                break;
            case MONDAY:
                sat_position = 5;
                sun_position = 6;
                break;
            case TUESDAY:
                sat_position = 4;
                sun_position = 5;
                break;
            case WEDNESDAY:
                sat_position = 3;
                sun_position = 4;
                break;
            case THURSDAY:
                sat_position = 2;
                sun_position = 3;
                break;
            case FRIDAY:
                sat_position = 1;
                sun_position = 2;
                break;
            case SATURDAY:
                sat_position = 0;
                sun_position = 1;
                break;
        }
        if (day_of_week == SUNDAY) {
            return sun_position;
        } else if (day_of_week == SATURDAY) {
            return sat_position;
        } else {
            return -1;
        }
    }

    // セッター(頭の曜日の設定)　・・・　MonthlyCalendarのインスタンス作成前に実施（デフォルト：日曜日）
    public static void setBeginningDayOfWeek(int BEGINNING_DAY_OF_WEEK) {
        MonthlyCalendar.BEGINNING_DAY_OF_WEEK = BEGINNING_DAY_OF_WEEK;
    }

    // 以下、ゲッター
    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public static int getBeginningDayOfWeek() {
        return BEGINNING_DAY_OF_WEEK;
    }

    public int[][] getCalendarMatrix() {
        return calendarMatrix;
    }

    public int getStartDate() {
        return startDate;
    }

    public int getLastDate() {
        return lastDate;
    }

    // 表示月と現在日時の比較
    public boolean isBackMonth(){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(today(MonthlyCalendar.TODAY_YEAR), today(MonthlyCalendar.TODAY_MONTH) - 1, 1);
        calendar.add(Calendar.MONTH, -1);
        return (year == calendar.get(Calendar.YEAR) && month == calendar.get(Calendar.MONTH) + 1);
    }

    public boolean isThisMonth(){
        return (year == MonthlyCalendar
                .today(MonthlyCalendar.TODAY_YEAR) && month == MonthlyCalendar
                .today(MonthlyCalendar.TODAY_MONTH));
    }

    public boolean isNextMonth(){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(today(MonthlyCalendar.TODAY_YEAR), today(MonthlyCalendar.TODAY_MONTH) - 1, 1);
        calendar.add(Calendar.MONTH, +1);
        return (year == calendar.get(Calendar.YEAR) && month == calendar.get(Calendar.MONTH) + 1);
    }
}
