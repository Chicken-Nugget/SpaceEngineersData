package chickennugget.spaceengineersdata.cards;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import chickennugget.spaceengineersdata.R;

public abstract class BaseMaterialFragment extends BaseFragment {

    TextView mTitleHeader;
    TextView mSubTitleHeader;
    ImageButton mSourceButton;
    ImageButton mDocButton;
    private View mHeaderBox;
    private View mHeaderContentBox;
    private View mHeaderBackgroundBox;
    private View mHeaderShadow;
    private int colorResId = -1;

    public static int scaleColor(int color, float factor, boolean scaleAlpha) {
        return Color.argb(scaleAlpha ? (Math.round(Color.alpha(color) * factor)) : Color.alpha(color),
                Math.round(Color.red(color) * factor), Math.round(Color.green(color) * factor),
                Math.round(Color.blue(color) * factor));
    }

    @Override
    public int getTitleResourceId() {
        return TITLE_NONE;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupBarHeader(view);
        setupBarButton(view);
        //((BaseActivity) getActivity()).getLPreviewUtils().setViewName(mHeaderBox, NativeDashFragment.VIEW_COLOR);
    }

    protected void setupBarHeader(View rootView) {
        mHeaderBox = getActivity().findViewById(R.id.header_recap);
        mHeaderContentBox = getActivity().findViewById(R.id.header_contents);
        mHeaderBackgroundBox = getActivity().findViewById(R.id.header_background);
        mHeaderShadow = getActivity().findViewById(R.id.header_shadow);
        mTitleHeader = (TextView) getActivity().findViewById(R.id.header_title);
        mSubTitleHeader = (TextView) getActivity().findViewById(R.id.header_subtitle);
    }

    protected void setupBarButton(View rootView) {
        mSourceButton = (ImageButton) getActivity().findViewById(R.id.bar_button_source);
        //mDocButton = (ImageButton) rootView.findViewById(R.id.bar_button_doc);
        final String sourceUrl = getSourceUrl();
        final String docUrl = getDocUrl();
        if (mSourceButton != null) {
            ((BaseActivity) getActivity()).getLPreviewUtils().setupCircleButton(mSourceButton);
            if (sourceUrl == null) {
                mSourceButton.setEnabled(false);
            } else {
                mSourceButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(sourceUrl));
                        startActivity(i);
                    }
                });
            }
        }
        if (mDocButton != null) {
            if (docUrl == null) {
                mDocButton.setEnabled(false);
            } else {

                mDocButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(docUrl));
                        startActivity(i);
                    }
                });
            }
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//      LPreviewUtilsBase lpu = LPreviewUtils.getInstance((BaseActivity)getActivity());
        getActivity().getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.native_background));
        float mMaxHeaderElevation = getResources().getDimensionPixelSize(
                R.dimen.carddemo_barheader_elevation);
/*      if (mHeaderShadow != null)
            mHeaderShadow.setVisibility(lpu.hasL() ? View.GONE : View.VISIBLE);
        if (mHeaderBackgroundBox != null)
            lpu.setViewElevation(mHeaderBackgroundBox,  mMaxHeaderElevation);
        if (mHeaderContentBox != null)
          lpu.setViewElevation(mHeaderContentBox,  mMaxHeaderElevation + 0.1f); */
        if (mTitleHeader != null)
            mTitleHeader.setText(getString(getTitleHeaderResourceId()));
        if (mSubTitleHeader != null)
            mSubTitleHeader.setText(getString(getSubTitleHeaderResourceId()));
        if (colorResId != -1) {
            //((mSessionColor = UIUtils.setColorAlpha(mSessionColor, 255);
            mHeaderBackgroundBox.setBackgroundColor(getResources().getColor(colorResId));
            ((BaseActivity) getActivity()).getLPreviewUtils().setStatusBarColor(
                    scaleColor(getResources().getColor(colorResId), 0.8f, false));
        }
    }

    protected abstract int getSubTitleHeaderResourceId();

    protected abstract int getTitleHeaderResourceId();

    protected abstract String getDocUrl();

    protected abstract String getSourceUrl();
}
