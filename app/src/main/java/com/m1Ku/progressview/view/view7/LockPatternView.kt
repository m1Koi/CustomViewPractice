package com.m1Ku.progressview.view.view7

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View

/**
 * Author: m1Ku
 * Email: howx172@163.com
 * Create Time: 2017/10/17
 * Description: 自定义九宫格解锁
 */
class LockPatternView : View {

    //状态值，是否初始化了
    private var isInit = false

    private var mPoints: Array<Array<Point?>> = Array(3) { Array<Point?>(3, { null }) }

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr)


    override fun onDraw(canvas: Canvas?) {
        //初始化9个点
        if (!isInit) {
            initDot()
            initPaint()
            isInit = true


        }


    }

    /**
     * 3个状态点的画笔，线的画笔
     */
    private fun initPaint() {

    }

    private fun initDot() {

    }

    /**
     * 对应的每个点的类
     */
    class Point(var centerX: Int, var centerY: Int, var Index: Int) {
        private val STATUS_NORMAL = 1
        private val STATUS_PRESSED = 2
        private val STATUS_ERROR = 3
        //当前点的状态
        private var status = STATUS_NORMAL;


    }

}