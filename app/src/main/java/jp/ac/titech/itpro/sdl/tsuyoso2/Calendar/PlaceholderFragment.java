package jp.ac.titech.itpro.sdl.tsuyoso2.Calendar;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.text.ParseException;

import jp.ac.titech.itpro.sdl.tsuyoso2.R;

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
     *  CalendarViewを複数Activityで使いまわしたので実装 by Tanaka
     *  日付クリック(OnDateClick)と[<<][>>](OnNextBackClick)の動作を
     *  Activityごとに切り替えられるようにする
     *
     *  CalendarView.mDateListener -> this.mDateListener -> hogeActivity.onDateClick
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnDateClickListener) {
            mDateListener = (OnDateClickListener) context;
        }
        if (context instanceof OnNextBackClickListener) {
            mNextBackListener = (OnNextBackClickListener) context;
        }
    }

    /**
     * @param year
     *            month　day クリックされた、年月日。
     */
    @Override
    public void onDateClick(View dayView, int year, int month, int day) throws ParseException {//day日が押されたときの挙動
        if(mDateListener != null){
            //Activity毎の挙動
            mDateListener.onDateClick(dayView, year, month, day);
        }else {
            //デフォルト動作
            Toast.makeText(
                    getActivity(),
                    Integer.toString(year) + "-" + Integer.toString(month)
                            + "-" + Integer.toString(day), Toast.LENGTH_SHORT)
                    .show();
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
            //Activityごとの挙動
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

}
