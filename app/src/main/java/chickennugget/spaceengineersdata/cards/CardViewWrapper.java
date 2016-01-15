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

package chickennugget.spaceengineersdata.cards;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * Common interface for CardView.
 * <p>
 * Necessary to merge the native cardview and the library cardview.
 * <p>
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public interface CardViewWrapper {

    /**
     * Return the context
     *
     * @return context
     */
    Context getContext();

    /**
     * Expand the card
     */
    void doExpand();

    /**
     * Collapse the card
     */
    void doCollapse();

    /**
     * Toggle the card
     */
    void doToggleExpand();

    /**
     * Support for longClickable
     **/
    void setLongClickable(boolean b);

    /**
     * Changes dynamically the drawable resource to override the style of MainLayout.
     *
     * @param drawableResourceId drawable resource Id
     */
    void changeBackgroundResourceId(int drawableResourceId);

    /**
     * Changes dynamically the drawable resource to override the style of MainLayout.
     *
     * @param drawableResource drawable resource
     */
    void changeBackgroundResource(Drawable drawableResource);

    /**
     * Changes dynamically the background color
     *
     * @param colorResourceId
     */
    void changeBackgroundColorResourceId(int colorResourceId);

    /**
     * Returns {@link chickennugget.spaceengineersdata.cards.Card} model
     *
     * @return {@link chickennugget.spaceengineersdata.cards.Card} model
     */
    chickennugget.spaceengineersdata.cards.Card getCard();

    /**
     * Add a {@link chickennugget.spaceengineersdata.cards.Card}.
     * It is very important to set all values and all components before launch this method.
     *
     * @param card {@link chickennugget.spaceengineersdata.cards.Card} model
     */
    void setCard(chickennugget.spaceengineersdata.cards.Card card);

    /**
     * Indicates if inner layout have to be replaced
     */
    void setForceReplaceInnerLayout(boolean forceReplaceInnerLayout);

    /**
     * Sets if view can recycle ui elements
     *
     * @param recycle <code>true</code> to recycle
     */
    void setRecycle(boolean recycle);

    /**
     * Implement to refresh the card content (it doesn't inflate layouts again)
     *
     * @param card
     */
    void refreshCard(chickennugget.spaceengineersdata.cards.Card card);

    /**
     * Returns the view used by Thumbnail
     *
     * @return {@link CardThumbnailView}
     */
    CardThumbnailView getInternalThumbnailLayout();

    void setOnTouchListener(View.OnTouchListener onTouchListener);

    /**
     * Sets the listener invoked when expand/collapse animation starts
     * It is used internally. Don't override it.
     *
     * @param onExpandListAnimatorListener listener
     */
    void setOnExpandListAnimatorListener(OnExpandListAnimatorListener onExpandListAnimatorListener);

    void setOnClickListener(View.OnClickListener advanceClickListener);

    /**
     * Sets the card as expanded or collapsed
     *
     * @param expanded <code>true</code> if the card is expanded
     */
    void setExpanded(boolean expanded);

    boolean isNative();

    /**
     * Retrieves the InternalMainCardGlobalLayout.
     *
     * @return
     */
    View getInternalMainCardLayout();

    /**
     * Interface to listen any callbacks when expand/collapse animation starts
     */
    interface OnExpandListAnimatorListener {
        void onExpandStart(CardViewWrapper viewCard, View expandingLayout);

        void onCollapseStart(CardViewWrapper viewCard, View expandingLayout);
    }
}
