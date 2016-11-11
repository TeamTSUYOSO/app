/**
 * Created by reverent on 16/11/11.
 */
package jp.ac.titech.itpro.sdl.tsuyoso2;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.LinearLayout;

public class RowRecommendView extends LinearLayout implements Checkable {

    private View view;
    private CheckBox mCheckBox;

    public RowRecommendView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    public RowRecommendView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public RowRecommendView(Context context) {
        super(context);
        initialize();
    }

    private void initialize() {
        // レイアウトを追加する
        view = inflate(getContext(), R.layout.row_recommends, null);
        addView(view);
        mCheckBox = (CheckBox) findViewById(R.id.checkbox);
    }

    @Override
    public boolean isChecked() {
        return mCheckBox.isChecked();
    }

    @Override
    public void setChecked(boolean checked) {
        // RadioButton の表示を切り替える
        mCheckBox.setChecked(checked);
//        TextView recipeNameView = (TextView) findViewById(R.id.recipe_name);
        view.setBackgroundColor(checked ? Color.argb(0x66, 0xff, 0xcc, 0xff) : Color.WHITE);
    }

    @Override
    public void toggle() {
    }

}