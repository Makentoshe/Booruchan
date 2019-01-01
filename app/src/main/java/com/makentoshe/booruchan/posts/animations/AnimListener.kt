package com.makentoshe.booruchan.posts.animations

import android.animation.ValueAnimator
import android.view.ViewPropertyAnimator
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class AnimListener(
    private val coroutineContext: CoroutineContext
) : android.animation.Animator.AnimatorListener {

    private var onAnimationRepeat: (suspend CoroutineScope.(android.animation.Animator?) -> Unit)? = null

    override fun onAnimationRepeat(animation: android.animation.Animator?) {
        val handler = onAnimationRepeat ?: return
        GlobalScope.launch(coroutineContext) {
            handler(animation)
        }
    }

    fun onAnimationRepeat(listener: suspend CoroutineScope.(android.animation.Animator?) -> Unit) {
        onAnimationRepeat = listener
    }

    private var onAnimationEnd: (suspend CoroutineScope.(android.animation.Animator?) -> Unit)? = null

    override fun onAnimationEnd(animation: android.animation.Animator?) {
        val handler = onAnimationEnd ?: return
        GlobalScope.launch(coroutineContext) {
            handler(animation)
        }
    }

    fun onAnimationEnd(listener: suspend CoroutineScope.(android.animation.Animator?) -> Unit) {
        onAnimationEnd = listener
    }

    private var onAnimationCancel: (suspend CoroutineScope.(android.animation.Animator?) -> Unit)? = null

    override fun onAnimationCancel(animation: android.animation.Animator?) {
        val handler = onAnimationCancel ?: return
        GlobalScope.launch(coroutineContext) {
            handler(animation)
        }
    }

    fun onAnimationCancel(listener: suspend CoroutineScope.(android.animation.Animator?) -> Unit) {
        onAnimationCancel = listener
    }

    private var onAnimationStart: (suspend CoroutineScope.(android.animation.Animator?) -> Unit)? = null

    override fun onAnimationStart(animation: android.animation.Animator?) {
        val handler = onAnimationStart ?: return
        GlobalScope.launch(coroutineContext) {
            handler(animation)
        }
    }

    fun onAnimationStart(listener: suspend CoroutineScope.(android.animation.Animator?) -> Unit) {
        onAnimationStart = listener
    }
}

fun ViewPropertyAnimator.setAnimListener(
    context: CoroutineContext = Dispatchers.Main,
    init: AnimListener.() -> Unit
): ViewPropertyAnimator {
    val listener = AnimListener(context)
    listener.init()
    return setListener(listener)
}

fun ValueAnimator.setAnimListener(
    context: CoroutineContext = Dispatchers.Main,
    init: AnimListener.() -> Unit
) {
    val listener = AnimListener(context)
    listener.init()
    addListener(listener)
}