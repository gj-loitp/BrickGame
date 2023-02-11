package com.brick.ui.score;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.brick.R;
import com.brick.Values;
import com.brick.data.Pref;
import com.brick.utils.AnimationUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScoreActivity extends AppCompatActivity {

    @BindView(R.id.tvFirstScore)
    TextView firstScore;

    @BindView(R.id.tvSecondScore)
    TextView secondScore;

    @BindView(R.id.tvThirdScore)
    TextView thirdScore;

    @BindView(R.id.llScores)
    LinearLayout scoresLayout;

    @BindView(R.id.ivShare)
    ImageView shareScore;

    private Pref pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        pref = new Pref(getApplicationContext());
        ButterKnife.bind(this);
        scoresLayout.startAnimation(AnimationUtil.getZoomIn(this));
        firstScore.setText(pref.getFirstValue());
        secondScore.setText(pref.getSecondValue());
        thirdScore.setText(pref.getThirdValue());
        shareScore.startAnimation(AnimationUtil.getZoomIn(this));
    }

    @OnClick(R.id.ivShare)
    public void share() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType(Values.SHARE_INTENT_TYPE);
        String shareBody = getResources().getString(R.string.share_body_part_one)
                + pref.getFirstValue()
                + " " + getResources().getString(R.string.share_body_part_second) + "\n\n"
                + Values.PLAY_MARKET_URL;
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share_via_text)));
    }
}
