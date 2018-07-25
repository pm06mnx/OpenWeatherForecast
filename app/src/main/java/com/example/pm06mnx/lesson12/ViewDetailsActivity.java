package com.example.pm06mnx.lesson12;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.pm06mnx.lesson12.list.dto.DayWeather;
import com.example.pm06mnx.lesson12.service.dto.WeatherItem;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class ViewDetailsActivity extends ListActivity {

    private final static String IMAGE_HTTP_PATH = "https://openweathermap.org/img/w/%s.png";
    private final static String IMAGE_ASSETS_PATH = "file:///android_asset/images/%s.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int pos = getIntent().getIntExtra("DAY", 0);
        DayWeather dayForecast = DataStorage.INSTANCE.getForecast().get(pos);
        ListAdapter listAdapter = new DetailsAdapter(dayForecast.getDetails());
        setListAdapter(listAdapter);
    }

    public static class DetailsAdapter extends BaseAdapter {

        private final List<WeatherItem> data;

        public DetailsAdapter(List<WeatherItem> data) {
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int i) {
            return data.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            WeatherItem item = data.get(i);
            View detailsView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.details_item_layout, viewGroup, false);
            TextView timeView = detailsView.findViewById(R.id.details_date);
            timeView.setText(SimpleDateFormat.getTimeInstance(DateFormat.SHORT).format(item.getDate()));
            TextView tempView = detailsView.findViewById(R.id.details_temp);
            tempView.setText(item.getMain().getTemp().toString()+" C");
            TextView windView = detailsView.findViewById(R.id.details_wind);
            windView.setText(item.getWind().getSpeed().toString()+" м/с");
            TextView descriptionView = detailsView.findViewById(R.id.details_description);
            descriptionView.setText(item.getWeather().get(0).getDescription());
            ImageView imageView = detailsView.findViewById(R.id.details_img);
            final String icon = item.getWeather().get(0).getIcon();
            Picasso.get()
                    .load(String.format(IMAGE_ASSETS_PATH, icon))
                    .into(imageView, new Callback.EmptyCallback() {
                        @Override
                        public void onError(Exception e) {
                            Picasso.get().load(String.format(IMAGE_HTTP_PATH, icon)).into(imageView);
                        }
                    });
            return detailsView;
        }
    }
}
