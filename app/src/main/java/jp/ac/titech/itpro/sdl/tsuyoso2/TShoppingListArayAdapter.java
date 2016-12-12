package jp.ac.titech.itpro.sdl.tsuyoso2;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by reverent on 16/12/12.
 */
public class TShoppingListArayAdapter extends ArrayAdapter<TShoppingItem>{

    private final Context mContext;

    // XMLからViewを生成するときに使う
    private LayoutInflater inflater;

    // リストアイテムのレイアウト
    private int viewResourceId;

    // 表示するアイテム
    private ArrayList<TShoppingItem> items;

    // 日付処理用
    String dateFormat = "yyyy-MM-dd";
    SimpleDateFormat mSimpleDateFormat;
    private Calendar mCalendar;

    /**
     * コンストラクタ
     * @param context
     * @param viewResourceId
     * @param items
     */
    public TShoppingListArayAdapter(Context context, int viewResourceId, ArrayList<TShoppingItem> items){
        super(context, viewResourceId);

        // リソースIDと表示アイテムを保持
        this.viewResourceId = viewResourceId;
        this.items = items;

        this.mContext = context;
        this.mCalendar = Calendar.getInstance();

        mSimpleDateFormat = new SimpleDateFormat(dateFormat);

        // ContextからLayoutInflaterを取得
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public TShoppingItem getItem(int pos) {
        return items.get(pos);
    }

    /**
     * 1アイテム分のビューを取得
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Log.d("getView","position:" + position);
        View view;

        // convertViewに入っていれば使う
        if(convertView != null){
            view = convertView;
        }
        // convertViewがnullなら新規作成
        else{
            view = inflater.inflate(viewResourceId, null);

            LinearLayout list = (LinearLayout) view.findViewById(R.id.row_shopping);
            Calendar calendar = Calendar.getInstance();
            for(int i = 0; i < 7; i++){
                String date = mSimpleDateFormat.format(calendar.getTime());
                TextView quantityView = createQuantityView(date);
                list.addView(quantityView);
                calendar.add(Calendar.DATE, 1);
            }
        }

        // テキストを表示
        TShoppingItem shoppingItem = items.get(position);
        ((TextView)view.findViewById(R.id.row_shopping_name)).setText(shoppingItem.getName());

        //初期化
        Calendar calendar = Calendar.getInstance();
        for(int i = 0; i < 7; i++){
            String date = mSimpleDateFormat.format(calendar.getTime());
            TextView quantityView = (TextView) view.findViewWithTag(date);
            if(quantityView != null){
                quantityView.setText("");
            }
            calendar.add(Calendar.DATE, 1);
        }

        ArrayList<TDateAndQuantity> list = shoppingItem.getDateAndQuantityList();
        for(TDateAndQuantity row : list){
            TextView quantityView = (TextView) view.findViewWithTag(row.getDate());
            if(quantityView != null){
                quantityView.setText(row.getQuantity());
            }
        }

        return view;
    }

    private TextView createQuantityView(String dateTag){
        TextView view = new TextView(getContext());

        float width = mContext.getResources().getDimension(R.dimen.otsukai_header_width);
        float height = mContext.getResources().getDimension(R.dimen.otsukai_header_height);
        view.setLayoutParams(new ViewGroup.LayoutParams((int)width, (int)height));
        view.setGravity(Gravity.CENTER_HORIZONTAL); // 中央に表示
        view.setTextSize(20);
        view.setTag(dateTag); //日付をタグとして付与
        return view;
    }
}
