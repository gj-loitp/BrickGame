package com.example.alina.tetris.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.alina.tetris.R;
import com.example.alina.tetris.data.ScoreCounter;
import com.example.alina.tetris.utils.AnimationUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScoreActivity extends AppCompatActivity {

    @BindView(R.id.tvFirstScore)
    TextView firstScore;

    @BindView(R.id.tvSecondScore)
    TextView secondScore;

    @BindView(R.id.tvThirdScore)
    TextView thirdScore;

    @BindView(R.id.llScores)
    LinearLayout scoresLayout;

    private ScoreCounter scoreCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        scoreCounter = new ScoreCounter(getApplicationContext());
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        scoresLayout.startAnimation(AnimationUtil.getZoomIn(this));
        firstScore.setText(scoreCounter.getFirstValue());
        secondScore.setText(scoreCounter.getSecondValue());
        thirdScore.setText(scoreCounter.getThirdValue());
    }
}
