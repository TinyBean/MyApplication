package com.example.joker.myapplication;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    public final static String GET_IMAGE="how_to_get_image";
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle drawerToggle;
    private ListView drawerList;
    private String[] list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("MyApplication");
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(onMenuItemClick);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawer = (DrawerLayout) findViewById(R.id.main);
        drawerList = (ListView) findViewById(R.id.left_drawer);
        list = getResources().getStringArray(R.array.planets_array);
        drawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, list));
        drawerList.setOnItemClickListener(new DrawerItemClickListener());
        drawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close){
            @Override
            public void onDrawerOpened(View view) {
                super.onDrawerOpened(view);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }
        };
//        drawerToggle.syncState();
        drawer.setDrawerListener(drawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener(){
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.camera_image:
                    getImage("1");
                    return true;
                case R.id.local_image:
                    getImage("2");
                    return true;
                default:
                    return true;
            }
        }
    };

    private void getImage(String way) {
        String imageFilePath = getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
        String timestamp = "/"+formatter.format(new Date())+".jpg";
        File imageFile = new File(imageFilePath,timestamp);
        Uri imageFileUri = Uri.fromFile(imageFile);
        if(way == "1") {
//            String path = imageFileUri.toString();
//            Intent camintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            camintent.putExtra(MediaStore.EXTRA_OUTPUT,imageFileUri);
//            camintent.putExtra(MainActivity.EXTRA_MESSAGE, path);
//            startActivityForResult(camintent, 1);
        }else {
            Intent gaintent = new Intent();
            gaintent.setAction(Intent.ACTION_PICK);
            gaintent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI, "image/*");
            startActivityForResult(gaintent, 2);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    try {
                        Log.e("CODE", "access");
                        Uri selectedImage = data.getData();
                        Log.e("PATH", selectedImage.toString());
                        Fragment fragment = new PlanetFragment();
                        Bundle args = new Bundle();
                        args.putString(PlanetFragment.ARG_GALLARY_IMG, selectedImage.toString());
                        fragment.setArguments(args);

                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                    } catch (Exception e) {
                        // TODO Auto-generatedcatch block
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    //从相册选择照片不裁切
                    try {
                        Uri selectedImage = data.getData(); //获取系统返回的照片的Uri

                        Fragment fragment = new PlanetFragment();
                        Bundle args = new Bundle();
                        args.putString(PlanetFragment.ARG_GALLARY_IMG, selectedImage.toString());
                        fragment.setArguments(args);

                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                    } catch (Exception e) {
                        // TODO Auto-generatedcatch block
                        e.printStackTrace();
                    }
                    break;
            }
        }

    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        // update the main content by replacing fragments
        Fragment fragment = new PlanetFragment();
        Bundle args = new Bundle();
        args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
        fragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        // update selected item and title, then close the drawer
        drawerList.setItemChecked(position, true);
        drawer.closeDrawer(drawerList);
    }

    public static class PlanetFragment extends Fragment {
        public static final String ARG_PLANET_NUMBER = "planet_number";
        public static final String ARG_GALLARY_IMG = "gallary_image";

        public PlanetFragment() {
            // Empty constructor required for fragment subclasses
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_planet, container, false);
            ImageView imgView = ((ImageView) rootView.findViewById(R.id.image));
            String imgPath = getArguments().getString(ARG_GALLARY_IMG);
            int i = -1;
            i = getArguments().getInt(ARG_PLANET_NUMBER);
            if(imgPath != null) {
                Uri uri = Uri.parse(imgPath);
                Glide.with(this).load(uri).into(imgView);
            } else if(i != -1) {
                String planet = getResources().getStringArray(R.array.planets_array)[i];
                int imageId = getResources().getIdentifier(planet.toLowerCase(Locale.getDefault()),
                        "drawable", getActivity().getPackageName());
                Glide.with(this).load(imageId).into(imgView);
            }
            return rootView;
        }
    }

}
