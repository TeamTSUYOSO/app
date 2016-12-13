package jp.ac.titech.itpro.sdl.tsuyoso2.NotUse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TIngredientsArrayAdapter extends ArrayAdapter<JSONObject> {
    // XMLからViewを生成するときに使う
    private LayoutInflater inflater;

    // リストアイテムのレイアウト
    private int textViewResourceId;

    // 表示するアイテム
    private ArrayList<JSONObject> items;

    /**
     * コンストラクタ
     * @param context
     * @param textViewResourceId
     * @param items
     */
    public TIngredientsArrayAdapter(Context context, int textViewResourceId, ArrayList<JSONObject> items){
        super(context, textViewResourceId, items);

        // リソースIDと表示アイテムを保持
        this.textViewResourceId = textViewResourceId;
        this.items = items;

        // ContextからLayoutInflaterを取得
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        View view;

        // convertViewに入っていれば使う
        if(convertView != null){
            view = convertView;
        }
        // convertViewがnullなら新規作成
        else{
            view = inflater.inflate(textViewResourceId, null);
        }

        try {
            // テキストを表示
            TextView quantity = (TextView)view.findViewWithTag("quantity");
            quantity.setText("    ・・・    " + items.get(position).getString("quantity"));

            TextView instruction_name = (TextView)view.findViewWithTag("ingredient_name");
            instruction_name.setText(items.get(position).getString("name"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return view;
    }

}
