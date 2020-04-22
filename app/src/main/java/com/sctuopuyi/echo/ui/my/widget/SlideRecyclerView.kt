package com.sctuopuyi.echo.ui.my.widget

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.*
import android.widget.Scroller
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs

class SlideRecyclerView : RecyclerView {

    private val INVALID_POSITION: Int = -1// 触摸到的点不在子View范围内
    private val INVALID_CHILD_WIDTH: Int = -1  // 子ItemView不含两个子View
    private val SNAP_VELOCITY: Int = 600  // 最小滑动速度


    private var mVelocityTracker: VelocityTracker? = null
    private var mTouchSlop: Int? = null
    private var mTouchFrame: Rect? = null
    private var mScroller: Scroller? = null
    private var mLastX: Float? = null
    private var mFirstX: Float? = null
    private var mFirstY: Float? = null
    private var mIsSlide: Boolean? = null
    private var mFlingView: ViewGroup? = null
    private var mPosition: Int? = null
    private var mMenuViewWidth: Int? = null


    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        this.mTouchSlop = ViewConfiguration.get(context).scaledDoubleTapSlop
        this.mScroller = Scroller(context)
    }


    override fun onInterceptTouchEvent(e: MotionEvent?): Boolean {
        val x = e?.x ?: 0f
        val y = e?.y ?: 0f
        obtainVelocity(e)
        when (e?.action) {
            MotionEvent.ACTION_DOWN -> {
                if (mScroller?.isFinished == false) mScroller?.abortAnimation()
                mFirstX = x
                mLastX = x
                mFirstY = y
                mPosition = pointToPosition(x, y)
                if (mPosition != INVALID_POSITION) {
                    val view = mFlingView
                    val tempView = getChildAt(
                        mPosition ?: 0
                        - (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    )
                    if (tempView != null) {
                        mFlingView =
                            tempView as ViewGroup
                    }
                    if (view != null && mFlingView != view && view.scrollX != 0) {
                        view.scrollTo(0, 0)
                    }

                    if (mFlingView?.childCount == 2) {
                        mMenuViewWidth = mFlingView?.getChildAt(1)?.width
                    } else {
                        mMenuViewWidth = INVALID_CHILD_WIDTH
                    }

                }
            }
            MotionEvent.ACTION_MOVE -> {
                val xVelocity: Float = mVelocityTracker?.xVelocity ?: 0f
                val yVelocity: Float = mVelocityTracker?.yVelocity ?: 0f
                if (abs(xVelocity) > SNAP_VELOCITY
                    && abs(xVelocity) > abs(yVelocity)
                    || abs(x - (mFirstX ?: 0f)) >= mTouchSlop?.toFloat() ?: 0f
                    && abs(x - (mFirstX ?: 0f)) > abs(y - (mFirstY ?: 0f))
                ) {
                    mIsSlide = true
                    return true
                }
            }
            MotionEvent.ACTION_UP -> {
                releaseVelocity()
            }
        }

        return super.onInterceptTouchEvent(e)
    }


    override fun onTouchEvent(e: MotionEvent?): Boolean {
        if (mIsSlide == true
            && mPosition != INVALID_POSITION
        ) {
            val x: Float = e?.x ?: 0f
            obtainVelocity(e)
            when (e?.action) {
                MotionEvent.ACTION_DOWN -> {

                }
                MotionEvent.ACTION_MOVE -> {
                    if (mMenuViewWidth != INVALID_CHILD_WIDTH) {
                        val dx: Float = mLastX ?: 0f - x
                        if (((mFlingView?.scrollX ?: 0).toFloat() + dx) <= (mMenuViewWidth
                                ?: 0).toFloat()
                            && (((mFlingView?.scrollX ?: 0).toFloat() + dx) > 0f)
                        ) {
                            mFlingView?.scrollBy(dx.toInt(), 0)
                        }
                        mLastX = x
                    }
                }
                MotionEvent.ACTION_UP -> {
                    if (mMenuViewWidth != INVALID_CHILD_WIDTH) {
                        val scrollX: Int = mFlingView?.scrollX ?: 0
                        mVelocityTracker?.computeCurrentVelocity(1000)
                        // 此处有两个原因决定是否打开菜单：
                        // 1.菜单被拉出宽度大于菜单宽度一半；
                        // 2.横向滑动速度大于最小滑动速度；
                        // 注意：之所以要小于负值，是因为向左滑则速度为负值
                        if (mVelocityTracker?.xVelocity ?: 0f < -SNAP_VELOCITY) {// 向左侧滑达到侧滑最低速度，则打开
                            mScroller?.startScroll(
                                scrollX
                                , 0
                                , mMenuViewWidth ?: 0 - scrollX
                                , 0
                                , abs(mMenuViewWidth ?: 0 - scrollX)
                            )
                        } else if (mVelocityTracker?.xVelocity ?: 0f >= SNAP_VELOCITY) {// 向右侧滑达到侧滑最低速度，则关闭
                            mScroller?.startScroll(scrollX, 0, -scrollX, 0, abs(scrollX))
                        } else if (scrollX > (mMenuViewWidth ?: 0) / 2) {// 如果超过删除按钮一半，则打开
                            mScroller?.startScroll(
                                scrollX,
                                0,
                                mMenuViewWidth ?: 0 - scrollX,
                                0,
                                abs(mMenuViewWidth ?: 0 - scrollX)
                            )
                        } else {
                            mScroller?.startScroll(scrollX, 0, -scrollX, 0, abs(scrollX));
                        }
                        invalidate()
                    }
                    mMenuViewWidth = INVALID_CHILD_WIDTH;
                    mIsSlide = false
                    mPosition = INVALID_POSITION
                    releaseVelocity()  // 这里之所以会调用，是因为如果前面拦截了，就不会执行ACTION_UP,需要在这里释放追踪
                }
            }
            return true
        } else {
            // 此处防止RecyclerView正常滑动时，还有菜单未关闭
            closeMenu()
            // Velocity，这里的释放是防止RecyclerView正常拦截了，但是在onTouchEvent中却没有被释放；
            // 有三种情况：1.onInterceptTouchEvent并未拦截，在onInterceptTouchEvent方法中，DOWN和UP一对获取和释放；
            // 2.onInterceptTouchEvent拦截，DOWN获取，但事件不是被侧滑处理，需要在这里进行释放；
            // 3.onInterceptTouchEvent拦截，DOWN获取，事件被侧滑处理，则在onTouchEvent的UP中释放。
            releaseVelocity()
        }
        return super.onTouchEvent(e)
    }

    override fun computeScroll() {
        if (mScroller?.computeScrollOffset() == true) {
            mFlingView?.scrollTo(mScroller?.currX ?: 0, mScroller?.currY ?: 0)
            invalidate()
        }
    }

    private fun closeMenu() {

        if (mFlingView != null && mFlingView?.scrollX != 0) {
            mFlingView?.scrollTo(0, 0)
        }
    }

    private fun releaseVelocity() {
        if (mVelocityTracker != null) {
            mVelocityTracker?.clear()
            mVelocityTracker?.recycle()
            mVelocityTracker = null
        }
    }

    private fun pointToPosition(x: Float?, y: Float?): Int? {
        if (null == layoutManager) return INVALID_POSITION
        val firtPosition = (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        var frame: Rect? = mTouchFrame
        if (frame == null) {
            mTouchFrame = Rect()
            frame = mTouchFrame
        }
        val count: Int = childCount
        for (index in count - 1 downTo 1) {
            val child: View = getChildAt(index)
            if (child.visibility == View.VISIBLE) {
                child.getHitRect(frame)
                if (frame?.contains(x?.toInt() ?: 0, y?.toInt() ?: 0) == true) {
                    return firtPosition + index
                }
            }
        }
        return INVALID_POSITION
    }

    private fun obtainVelocity(e: MotionEvent?) {
        if (mVelocityTracker == null) mVelocityTracker = VelocityTracker.obtain()
        mVelocityTracker?.addMovement(e)
    }
}