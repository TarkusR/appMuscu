package fr.isep.APPMusculation;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import fr.isep.APPMusculation.R;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ImageView hamburgerMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        hamburgerMenu = findViewById(R.id.hamburgerMenu);

        hamburgerMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (drawerLayout.isDrawerOpen(findViewById(R.id.side_menu))) {
                    drawerLayout.closeDrawer(findViewById(R.id.side_menu));
                } else {
                    drawerLayout.openDrawer(findViewById(R.id.side_menu));
                }
            }
        });
    }
}
