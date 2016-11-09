package jp.ac.titech.itpro.sdl.tsuyoso2.Calendar;

import java.util.EventListener;
/**
 * Created by kayo on 2016/10/31.
 */
public interface OnNextBackClickListener extends EventListener{
    public void onNextBackClick(int year,int month,int nextback);
}
