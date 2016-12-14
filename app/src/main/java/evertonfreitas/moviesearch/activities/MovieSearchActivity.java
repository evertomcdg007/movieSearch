package evertonfreitas.moviesearch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import evertonfreitas.moviesearch.R;
import evertonfreitas.moviesearch.entidies.Movie;
import evertonfreitas.moviesearch.fragments.MoviesFragment;
import evertonfreitas.moviesearch.utils.Constants;

@SuppressWarnings("RestrictedApi")
public class MovieSearchActivity extends AppCompatActivity implements MoviesFragment.OnFragmentInteractionListener{

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_search);
        initView();
        setSupportActionBar(toolbar);
        
        setActions();
        commitFragments(Constants.TAG_MOVIES_POPULARITY);
    }



    public void initView(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
    }

    private void setActions() {

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close){

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if(menuItem.isChecked())
                    menuItem.setChecked(false);
                else
                    menuItem.setChecked(true);

                drawerLayout.closeDrawers();

                switch (menuItem.getItemId()){
                    case R.id.nav_movie_popularity:
                        commitFragments(Constants.TAG_MOVIES_POPULARITY);
                        return true;
                    case R.id.nav_movie_accessed:
                        commitFragments(Constants.TAG_MOVIES_ACCESSED);
                        return true;
                }

                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void commitFragments(String tag){
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, MoviesFragment.newInstance(tag));
        fragmentTransaction.commit();
    }

    @Override
    public void onFragmentInteraction(Movie movie) {
        Bundle b = new Bundle();
        b.putParcelable(Constants.ARG_MOVIE, movie);

        Intent intent = new Intent(this, MovieSearchActivity.class);
        intent.putExtra(Constants.ARGS, b);
        startActivity(intent);
    }
}
