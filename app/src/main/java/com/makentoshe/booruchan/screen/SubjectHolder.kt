package com.makentoshe.booruchan.screen

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import io.reactivex.subjects.Subject

open class SubjectHolder<T2> : ViewModel() {

    lateinit var subject: Subject<T2>
        private set

    protected open class Factory<T2>(private val subject: Subject<T2>) : ViewModelProvider.NewInstanceFactory() {
        override fun <T1 : ViewModel?> create(modelClass: Class<T1>): T1 {
            val viewModel = SubjectHolder<T2>()
            viewModel.subject = subject
            return viewModel as T1
        }
    }

    companion object {
        fun <T> create(fragment: Fragment, subject: Subject<T>, key: String): SubjectHolder<T> {
            val factory = Factory(subject)
            return ViewModelProviders.of(
                fragment,
                factory
            )[key, SubjectHolder::class.java] as SubjectHolder<T>
        }
    }
}