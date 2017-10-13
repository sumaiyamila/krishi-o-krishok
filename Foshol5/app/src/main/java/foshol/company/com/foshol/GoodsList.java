package foshol.company.com.foshol;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by User on 26-Aug-17.
 */

public class GoodsList extends ArrayAdapter<Goods> {
    private Activity context;
    private List<Goods> goodslist;


    public GoodsList(Activity context, List<Goods> goodslist) {
        super(context, R.layout.goods_list_layout,goodslist);
        this.context=context;
        this.goodslist = goodslist;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater inflater=context.getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.goods_list_layout,null,true);

            TextView textViewPrice=(TextView)listViewItem.findViewById(R.id.textViewPrice);
            TextView textViewName=(TextView)listViewItem.findViewById(R.id.textViewName);

            Goods goods = goodslist.get(position);
            textViewName.setText(goods.getName());
            textViewPrice.setText(goods.getPrice()+" টাকা");

            return  listViewItem;

    }
}
