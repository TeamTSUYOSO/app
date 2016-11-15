package jp.ac.titech.itpro.sdl.tsuyoso2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class TRecommendArrayAdapter extends ArrayAdapter<TRecommend> {
    // XMLからViewを生成するときに使う
    private LayoutInflater inflater;

    // リストアイテムのレイアウト
    private int viewResourceId;

    // 表示するアイテム
    private ArrayList<TRecommend> items;

    /**
     * コンストラクタ
     * @param context
     * @param viewResourceId
     * @param items
     */
    public TRecommendArrayAdapter(Context context, int viewResourceId, ArrayList<TRecommend> items){
        super(context, viewResourceId);

        // リソースIDと表示アイテムを保持
        this.viewResourceId = viewResourceId;
        this.items = items;

        // ContextからLayoutInflaterを取得
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public TRecommend getItem(int pos) {
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
        }

        // テキストを表示
        TRecommend recommend = items.get(position);

        ((TextView)view.findViewById(R.id.day)).setText(recommend.getDay() + "");
        ((TextView)view.findViewById(R.id.month)).setText(recommend.getMonth() + "");
        ((TextView)view.findViewById(R.id.recipe_name)).setText(recommend.getRecpeName());
        //TODO::画像差し替え
        ((ImageView)view.findViewById(R.id.recipe_image)).setImageResource(R.drawable.ic_action_restaurant_mini);

        return view;
    }
}
