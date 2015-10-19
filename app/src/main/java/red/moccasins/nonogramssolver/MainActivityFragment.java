package red.moccasins.nonogramssolver;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import red.moccasins.nonogramssolver.nono.NonoView;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private static class Stage {
        public static final int SIZING = 0;
        public static final int FILLING = 1;
        public static final int SOLVING = 2;
    }

    private int currentStage = -1;

    private View mRootView;

    private EditText editWidth;
    private EditText editHeight;
    private Button sizingDone;

    public MainActivityFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_main, container, false);

        editWidth = (EditText) mRootView.findViewById(R.id.editWidth);
        editHeight = (EditText) mRootView.findViewById(R.id.editHeight);
        sizingDone = (Button) mRootView.findViewById(R.id.btn_sizing_done);

        editWidth.setOnKeyListener(sizeEditorListener);
        editHeight.setOnKeyListener(sizeEditorListener);
        sizingDone.setOnClickListener(buttonClickListener);

        return mRootView;
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentStage", currentStage);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            //probably orientation change
            currentStage = savedInstanceState.getInt("currentStage");
        } else {
            if (currentStage != -1) {
                //returning from backstack, data is fine, do nothing
            } else {
                //newly created, compute data
                currentStage = Stage.SIZING;
            }
        }

        switch (currentStage) {
            case Stage.SIZING:
                mRootView.findViewById(R.id.area_sizing).setVisibility(View.VISIBLE);
                break;
            case Stage.FILLING:
                mRootView.findViewById(R.id.area_filling).setVisibility(View.VISIBLE);
            default:
                break;
        }
    }

    private View.OnKeyListener sizeEditorListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
            if (keyEvent.getAction() == KeyEvent.ACTION_DOWN)
                checkSizeParams();
            return false;
        }
    };

    private boolean checkSizeParams() {
        int currentWidth = 0;
        int currentHeight = 0;

        if (!editWidth.getText().toString().equals(""))
            currentWidth = Integer.valueOf(editWidth.getText().toString());

        if (!editHeight.getText().toString().equals(""))
            currentHeight = Integer.valueOf(editHeight.getText().toString());

        sizingDone.setEnabled( currentWidth > 0 && currentHeight > 0 );

        return currentWidth > 0 && currentHeight > 0;
    }

    private View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_sizing_done:
                    if ( checkSizeParams() ) {
                        currentStage = Stage.FILLING;
                        mRootView.findViewById(R.id.area_sizing).setVisibility(View.GONE);
                        initializeNonogram( Integer.valueOf(editWidth.getText().toString()),
                                Integer.valueOf(editHeight.getText().toString()) );
                        mRootView.findViewById(R.id.area_filling).setVisibility(View.VISIBLE);
                    }
            }
        }
    };

    private void initializeNonogram(int width, int height) {
        ((NonoView)mRootView.findViewById(R.id.fill_nonogram)).InitNewNonogram(width, height);
    }

}
