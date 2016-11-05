package jp.ac.titech.itpro.sdl.tsuyoso2;

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

/**
 * Created by kayo on 2016/10/31.
 */
public class PlaceholderFragment extends Fragment implements
    OnDateClickListener, OnNextBackClickListener{

    CalendarView calendarView;

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
                MonthlyCalendar.today(MonthlyCalendar.TODAY_MONTH), true,
                true);
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
        Toast.makeText(
                getActivity(),
                Integer.toString(year) + "-" + Integer.toString(month)
                        + "-" + Integer.toString(day), Toast.LENGTH_SHORT)
                .show();

        String dateStr = year + "-" + month + "-" + day;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date requestDate = simpleDateFormat.parse(dateStr);

        Intent intent = new Intent( getActivity(), recipeTodayActivity.class);
        intent.putExtra("Request_Date",requestDate);
        startActivity(intent);

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
        if(nextback==MonthlyCalendar.BACK_MONTH) {
            Toast.makeText(
                    getActivity(),
                    Integer.toString(year) + "-" + Integer.toString(month)
                            + "----" + Integer.toString(nextback),
                    Toast.LENGTH_SHORT).show();

        }else if(nextback==MonthlyCalendar.NEXT_MONTH){
            Toast.makeText(
                    getActivity(),
                    Integer.toString(year) + "-" + Integer.toString(month)
                            + "++++" + Integer.toString(nextback),
                    Toast.LENGTH_SHORT).show();
           // setCalendarView(year, month+1);
        }
    }

  //  public void setCalendarView(int year, int month){
    //    CalendarView calendarView = (CalendarView) rootView.findViewById(R.id.frag_calendar);
      //  calendarView.set(year, month, true, true);
    //}


}
