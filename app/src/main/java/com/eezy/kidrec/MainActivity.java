package com.eezy.kidrec;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.eezy.kidrec.model.Pane;
import com.eezy.kidrec.view.MultiPaneWindow;

public class MainActivity extends FragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MultiPaneWindow multiPaneWindow = (MultiPaneWindow) findViewById(R.id.multi_pane_window);
        Pane pane = new Pane("Spring");
        pane.addVertex(20, 30);
        pane.addVertex(600, 30);
        pane.addVertex(400, 700);
        pane.addVertex(20, 700);
        pane.setBackground(getResources(), R.drawable.spring_flowers);

        Pane pane2 = new Pane("Winter");
        pane2.addVertex(600, 30);
        pane2.addVertex(900, 30);
        pane2.addVertex(900, 700);
        pane2.addVertex(400, 700);
        pane2.setBackground(getResources(), R.drawable.winter);

        multiPaneWindow.addPane(pane);
        multiPaneWindow.addPane(pane2);
        multiPaneWindow.invalidate();
    }
}
