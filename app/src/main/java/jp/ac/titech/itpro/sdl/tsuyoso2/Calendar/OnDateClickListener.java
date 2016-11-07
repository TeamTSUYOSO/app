package jp.ac.titech.itpro.sdl.tsuyoso2.Calendar;

import java.text.ParseException;
import java.util.EventListener;
/**
 * Created by kayo on 2016/10/31.
 */
public interface OnDateClickListener extends EventListener{
    public void onDateClick(int year,int month,int day) throws ParseException;
}
