/*
 * ******************************************************************************
 *   Copyright (c) 2013-2014 Gabriele Mariotti.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *  *****************************************************************************
 */

package chickennugget.spaceengineersdata.unused.cards.card_view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import chickennugget.spaceengineersdata.R;
import chickennugget.spaceengineersdata.unused.cards.card_main.CardHeader;
import chickennugget.spaceengineersdata.unused.cards.helpers.CardViewHelperUtil;
import chickennugget.spaceengineersdata.unused.cards.interfaces.CardViewHelper;
import chickennugget.spaceengineersdata.unused.cards.interfaces.CardViewInterface;

public class CardHeaderView extends FrameLayout implements CardViewInterface {

    protected int card_header_layout_resourceID = R.layout.base_header_layout;
    protected View mInternalOuterView;
    protected View mInternalInnerView;
    protected ViewGroup mFrameInner;
    protected ViewGroup mFrameButton;
    protected ImageButton mImageButtonOverflow;
    protected ImageButton mImageButtonExpand;
    protected ImageButton mImageButtonOther;
    protected CardHeader mCardHeader;
    protected boolean mIsRecycle = false;
    protected boolean mForceReplaceInnerLayout = false;
    protected PopupMenu mPopupMenu;
    protected CardViewHelper mHelperImpl;

    public CardHeaderView(Context context) {
        this(context, null, 0);
    }

    public CardHeaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CardHeaderView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
        mHelperImpl = CardViewHelperUtil.getInstance(context);
    }

    protected void init(AttributeSet attrs, int defStyle) {
        initAttrs(attrs, defStyle);
        if (!isInEditMode())
            initView();
    }

    protected void initAttrs(AttributeSet attrs, int defStyle) {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs, R.styleable.card_options, defStyle, defStyle);
        try {
            card_header_layout_resourceID = a.getResourceId(R.styleable.card_options_card_header_layout_resourceID, card_header_layout_resourceID);
        } finally {
            a.recycle();
        }
    }

    protected void initView() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInternalOuterView = inflater.inflate(card_header_layout_resourceID, this, true);
        mImageButtonExpand = (ImageButton) findViewById(R.id.card_header_button_expand);
        mImageButtonOverflow = (ImageButton) findViewById(R.id.card_header_button_overflow);
        mImageButtonOther = (ImageButton) findViewById(R.id.card_header_button_other);
        mFrameInner = (FrameLayout) findViewById(R.id.card_header_inner_frame);
        mFrameButton = (FrameLayout) findViewById(R.id.card_header_button_frame);
    }

    @Override
    public View getInternalOuterView() {
        return mInternalOuterView;
    }

    public void addCardHeader(CardHeader cardHeader) {
        mCardHeader = cardHeader;
        buildUI();
    }

    protected void buildUI() {
        if (mCardHeader == null) return;
        setupButtons();
        setupInnerView();
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    protected void setupButtons() {
        if (mCardHeader.isButtonOverflowVisible()) {
            visibilityButtonHelper(VISIBLE, GONE, GONE);
            addPopup();
            if (mPopupMenu == null && mCardHeader.getCustomOverflowAnimation() != null) {
                addCustomOverflowAnimation();
            }
        } else {
            if (mCardHeader.isButtonExpandVisible()) {
                visibilityButtonHelper(GONE, VISIBLE, GONE);
            } else {
                if (mCardHeader.isOtherButtonVisible() && mImageButtonOther != null) {
                    visibilityButtonHelper(GONE, GONE, VISIBLE);
                    if (mImageButtonOther != null) {
                        if (mCardHeader.getOtherButtonDrawable() > 0) {
                            mHelperImpl.setButtonBackground(mImageButtonOther, mCardHeader.getOtherButtonDrawable());
                        }
                        addOtherListener();
                    }
                } else {
                    visibilityButtonHelper(GONE, GONE, GONE);
                }
            }
        }
    }

    private void addCustomOverflowAnimation() {
        final CardHeader.CustomOverflowAnimation animation = mCardHeader.getCustomOverflowAnimation();
        if (animation != null && mImageButtonOverflow != null) {
            mImageButtonOverflow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    animation.doAnimation(mCardHeader.getParentCard(), v);
                }
            });
        } else {
            if (mImageButtonOverflow != null)
                mImageButtonOverflow.setVisibility(GONE);
        }
    }

    protected void addOtherListener() {
        if (mCardHeader.getOtherButtonClickListener() != null) {
            if (mImageButtonOther != null) {
                mImageButtonOther.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mCardHeader.getOtherButtonClickListener() != null)
                            mCardHeader.getOtherButtonClickListener().onButtonItemClick(mCardHeader.getParentCard(), v);
                    }
                });
            }
        } else {
            if (mImageButtonOther != null) {
                mImageButtonOther.setClickable(false);
            }
        }
    }

    protected void setupInnerView() {
        if (mFrameInner != null) {
            if (!isRecycle() || isForceReplaceInnerLayout()) {
                if (isForceReplaceInnerLayout() && mFrameInner != null && mInternalInnerView != null)
                    mFrameInner.removeView(mInternalInnerView);
                mInternalInnerView = mCardHeader.getInnerView(getContext(), mFrameInner);
            } else {
                if (mCardHeader.getInnerLayout() > -1)
                    mCardHeader.setupInnerViewElements(mFrameInner, mInternalInnerView);
            }
        }
    }

    protected void visibilityButtonHelper(int overflowButtonVisibility, int expandButtonVisibility, int otherButtonVisibility) {
        if (overflowButtonVisibility == VISIBLE || overflowButtonVisibility == GONE)
            if (mImageButtonOverflow != null)
                mImageButtonOverflow.setVisibility(overflowButtonVisibility);
        if (expandButtonVisibility == VISIBLE || expandButtonVisibility == GONE)
            if (mImageButtonExpand != null)
                mImageButtonExpand.setVisibility(expandButtonVisibility);
        if (otherButtonVisibility == VISIBLE || otherButtonVisibility == GONE)
            if (mImageButtonOther != null)
                mImageButtonOther.setVisibility(otherButtonVisibility);
    }

    protected void addPopup() {
        mPopupMenu = null;
        if (mImageButtonOverflow != null) {
            boolean prepareMenu = mCardHeader.getPopupMenu() > CardHeader.NO_POPUP_MENU ? true : false;
            if (mCardHeader.getPopupMenuPrepareListener() != null) {
                mPopupMenu = _buildPopupMenu();
                prepareMenu = mCardHeader.getPopupMenuPrepareListener().onPreparePopupMenu(mCardHeader.getParentCard(), mPopupMenu);
                if (mPopupMenu.getMenu() == null || !mPopupMenu.getMenu().hasVisibleItems())
                    prepareMenu = false;
            }
            if (prepareMenu) {
                mImageButtonOverflow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mPopupMenu == null) {
                            mPopupMenu = _buildPopupMenu();
                        }
                        if (mPopupMenu != null) {
                            mPopupMenu.show();
                            mImageButtonOverflow.setSelected(true);
                        }
                    }
                });
            } else {
                if (mCardHeader.getCustomOverflowAnimation() == null) {
                    mImageButtonOverflow.setVisibility(GONE);
                }
            }
        } else {
            if (mImageButtonOverflow != null)
                mImageButtonOverflow.setVisibility(GONE);
        }
    }

    private PopupMenu _buildPopupMenu() {
        PopupMenu popup = new PopupMenu(getContext(), mImageButtonOverflow);
        if (mCardHeader.getPopupMenu() > CardHeader.NO_POPUP_MENU) {
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(mCardHeader.getPopupMenu(), popup.getMenu());
        }
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (mCardHeader.getPopupMenuListener() != null) {
                    // This individual card has it unique menu
                    mCardHeader.getPopupMenuListener().onMenuItemClick(mCardHeader.getParentCard(), item);
                }
                return false;
            }
        });
        popup.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
                if (mImageButtonOverflow != null)
                    mImageButtonOverflow.setSelected(false);
            }
        });
        return popup;
    }

    /*  public OnClickListener getOnClickExpandCollapseActionListener() {
            return mOnClickExpandCollapseActionListener;
        }

        public void setOnClickExpandCollapseActionListener(OnClickListener onClickExpandCollapseActionListener) {
            this.mOnClickExpandCollapseActionListener = onClickExpandCollapseActionListener;
        }
    */
    public boolean isRecycle() {
        return mIsRecycle;
    }

    public void setRecycle(boolean isRecycle) {
        this.mIsRecycle = isRecycle;
    }

    public boolean isForceReplaceInnerLayout() {
        return mForceReplaceInnerLayout;
    }

    public void setForceReplaceInnerLayout(boolean forceReplaceInnerLayout) {
        this.mForceReplaceInnerLayout = forceReplaceInnerLayout;
    }

    public ImageButton getImageButtonOverflow() {
        return mImageButtonOverflow;
    }

    public ImageButton getImageButtonExpand() {
        return mImageButtonExpand;
    }

    public ImageButton getImageButtonOther() {
        return mImageButtonOther;
    }

    public ViewGroup getFrameButton() {
        return mFrameButton;
    }
}
