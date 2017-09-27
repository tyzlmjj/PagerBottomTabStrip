package me.majiajie.pagerbottomtabstriptest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toMaterialdesign(View view) {
        startActivity(new Intent(this,MaterialDesignActivity.class));
    }

    public void toCustom(View view) {
        startActivity(new Intent(this,CustomActivity.class));
    }

    public void toCustom2(View view) {
        startActivity(new Intent(this,Custom2Activity.class));
    }

    public void toBehavior(View view) {
        startActivity(new Intent(this,BehaviorActivity.class));
    }

    public void toHide(View view) {
        startActivity(new Intent(this,HideActivity.class));
    }

    public void toSpecial(View view) {
        startActivity(new Intent(this,SpecialActivity.class));
    }

    public void toVertical(View view) {
        startActivity(new Intent(this,VerticalActivity.class));
    }

    public void toCsutomVertical(View view) {
        startActivity(new Intent(this,VerticalCustomActivity.class));
    }
}
