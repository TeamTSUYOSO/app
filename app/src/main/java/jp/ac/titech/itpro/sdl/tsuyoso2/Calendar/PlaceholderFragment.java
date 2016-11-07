package jp.ac.titech.itpro.sdl.tsuyoso2.Calendar;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import jp.ac.titech.itpro.sdl.tsuyoso2.R;
import jp.ac.titech.itpro.sdl.tsuyoso2.recipeTodayActivity;

/**
 * Created by kayo on 2016/10/31.
 */
public class PlaceholderFragment extends Fragment implements
    OnDateClickListener, OnNextBackClickListener{

    CalendarView calendarView;

    private OnDateClickListener mDateListener;
    private OnNextBackClickListener mNextBackListener;

    final boolean FLEXIBLE_LINE = true;
    final boolean HAS_NEXTBACK_BUTTON = true;

    public PlaceholderFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container,
                false);
        //カレンダービューの取得
        calendarView = (CalendarView) rootView.findViewById(R.id.calendar);
        //カレンダーに日付をセット
        //第一引数：表示する年
        //第二引数：表示する月
        //第三引数：カレンダーの縦の長さを可変にするか true、６行固定にするか false
        //第四引数：前月(≪)、翌月（≫）ボタンを表示するか true
        calendarView.set(MonthlyCalendar.today(MonthlyCalendar.TODAY_YEAR),
                MonthlyCalendar.today(MonthlyCalendar.TODAY_MONTH), FLEXIBLE_LINE,
                HAS_NEXTBACK_BUTTON);
        calendarView.setOnDateClickListener(this);
        calendarView.setOnNextBackClickListener(this);

        return rootView;
    }

    /**
     * @param year
     *            month　day クリックされた、年月日。
     */
    @Override
    public void onDateClick(int year, int month, int day) throws ParseException {//day日が押されたときの挙動
        if(mDateListener != null){
            mDateListener.onDateClick(year, month, day);
        }else {
            //デフォルト動作
            Toast.makeText(
                    getActivity(),
                    Integer.toString(year) + "-" + Integer.toString(month)
                            + "-" + Integer.toString(day), Toast.LENGTH_SHORT)
                    .show();

            String dateStr = year + "-" + month + "-" + day;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date requestDate = simpleDateFormat.parse(dateStr);

            Intent intent = new Intent(getActivity(), recipeTodayActivity.class);
            intent.putExtra("Request_Date", requestDate);
            startActivity(intent);
        }
    }

    /**
     * @param year
     * @param month
     *            month year クリックした、前月（≪）　または　翌月（≫）　の、年と月がセットされる。
     *
     * @param nextback
     *            MonthlyCalendar.BACK_MONTH(前月　≪　が押された) or
     *            MonthlyCalendar.NEXT_MONTH（翌月　≫　が押された）
     */
    @Override
    public void onNextBackClick(int year, int month, int nextback) {
        if(mNextBackListener != null){
            mNextBackListener.onNextBackClick(year, month, nextback);
        }else {
            //  デフォルト動作
            //  押したボタンに応じてカレンダーを前月/来月に更新
            if (nextback == MonthlyCalendar.BACK_MONTH) {
                calendarView.set(year, month, FLEXIBLE_LINE, HAS_NEXTBACK_BUTTON);
            } else if (nextback == MonthlyCalendar.NEXT_MONTH) {
                calendarView.set(year, month, FLEXIBLE_LINE, HAS_NEXTBACK_BUTTON);
            }
        }
    }

    /**
     *  日付クリック時の動作を画面毎に変更するためのリスナーを追加する
     *  セットしない場合デフォルト動作
     *  CalendarView.mDateListener -> this.mDateListener -> hogeActivity.onDateClick
     *
     * @param listener
     */
    public void setOnDateClickListener(OnDateClickListener listener) {
        this.mDateListener = listener;
    }

    /**
     *  [<<][>>]クリック時の動作を画面毎に変更するためのリスナーを追加する
     *   セットしない場合デフォルト動作
     *  CalendarView.mNextBackListener -> this.mNextBackListener -> hogeActivity.onNextBackClick
     * @param listener
     */
    public void setOnDateClickListener(OnNextBackClickListener listener) {
        this.mNextBackListener = listener;
    }

}
