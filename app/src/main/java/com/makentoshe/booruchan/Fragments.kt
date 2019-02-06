package com.makentoshe.booruchan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.makentoshe.viewmodel.ViewModel
import org.jetbrains.anko.support.v4.intentFor

interface Backpressable {
    fun onBackPressed(): Boolean
}


abstract class Fragment<VM : ViewModel> : Fragment(), Backpressable {
    protected lateinit var viewModel: VM

    abstract fun buildViewModel(arguments: Bundle): VM

    open val argumentInitializer: String = this::class.java.simpleName

    /**
     * @param arguments fragment arguments before clearing
     * @return fragment arguments after clearing
     */
    open fun clearArguments(arguments: Bundle?): Bundle? {
        return arguments
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        //get arguments from holder
        var argumentsFromHolder = ArgumentsHolder.getArgument(argumentInitializer)
        //if holder does not contains arguments
        if (argumentsFromHolder == null) {
            argumentsFromHolder = arguments ?: Bundle.EMPTY
            //put arguments
            ArgumentsHolder.putArgument(argumentInitializer, arguments ?: Bundle.EMPTY)
            //Clear inner arguments
            arguments = clearArguments(arguments)
        }

        this.viewModel = buildViewModel(argumentsFromHolder ?: Bundle.EMPTY)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel.onCreateView(this)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onBackPressed() = false

    override fun onDestroy() {
        //check that fragment is no more use
        val activity = activity
        val isChangingConfigurations = activity != null && activity.isChangingConfigurations
        //remove unused arguments from holder
        if (!isChangingConfigurations) {
            ArgumentsHolder.removeArgument(argumentInitializer)
        }

        super.onDestroy()
    }

}