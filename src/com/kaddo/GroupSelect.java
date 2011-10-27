package com.kaddo;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AbsoluteLayout;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;

public class GroupSelect extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_select);

        // Reference the Gallery view
        Gallery g = (Gallery) findViewById(R.id.g_level);
        // Set the adapter to our custom adapter (below)
        g.setAdapter(new ImageAdapter(this));

        // Set a item click listener, and just Toast the clicked position
        g.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(GroupSelect.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });

        // We also want to show context menu for longpressed items in the gallery
        registerForContextMenu(g);
    }

    public class ImageAdapter extends BaseAdapter {
        private static final int ITEM_WIDTH = 136;
        private static final int ITEM_HEIGHT = 88;

        private final int mGalleryItemBackground;
        private final Context mContext;
     

        private final Integer[] mImageIds = {
        		R.drawable.level_1,
        		R.drawable.level_2,
        		R.drawable.level_3
        };

        private final float mDensity;

        public ImageAdapter(Context c) {
            mContext = c;
            // See res/values/attrs.xml for the <declare-styleable> that defines
            // Gallery1.
            TypedArray a = obtainStyledAttributes(R.styleable.level_wrap);
            mGalleryItemBackground = a.getResourceId(R.styleable.level_wrap_android_galleryItemBackground, 0);
            a.recycle();

            mDensity = c.getResources().getDisplayMetrics().density;
        }

        public int getCount() {
            return mImageIds.length;
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
        	ImageView imageView;
            if (convertView == null) {
                convertView = new ImageView(mContext);

                imageView = (ImageView) convertView;
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//                imageView.setLayoutParams(new Gallery.LayoutParams(
//                        (int) (ITEM_WIDTH * mDensity + 0.5f),
//                        (int) (ITEM_HEIGHT * mDensity + 0.5f)));


                imageView.setBackgroundResource(mGalleryItemBackground);
            } else {
                imageView = (ImageView) convertView;
            }

            imageView.setImageResource(mImageIds[position]);

            return imageView;
        }
    }
}