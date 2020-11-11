package com.example.productpickingdemo.base

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.productpickingdemo.base.view_model.BaseViewModel
import com.google.android.material.snackbar.Snackbar
import java.util.concurrent.TimeUnit
import javax.inject.Inject

abstract class BaseFragment<V : BaseViewModel> : Fragment()  {
    protected lateinit var viewModel: V

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    protected lateinit var baseContext: Context

    @Inject
    lateinit var baseActivity: BaseActivity<*>
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.example.productpickingdemo.R
import com.example.productpickingdemo.utils.OnBackPressedListener

abstract class BaseFragment : Fragment(), OnBackPressedListener {
    lateinit var navController: NavController
    lateinit var navOptionsDefaultAnim: NavOptions


    protected var rootView: View? = null
    var isVisible: (fragment: Fragment) -> Boolean = { true }

    @LayoutRes
    protected abstract fun layout(): Int
    protected abstract fun provideViewModel(viewModelFactory: ViewModelProvider.Factory): V
    protected abstract fun initialization(view: View, isFirstInit: Boolean)

    override fun onAttach(context: Context) {
        super.onAttach(context)
//        try {
//            (context as BaseActivity<*>).fragmentComponent.inject(this as BaseFragment<BaseViewModel>)
//        } catch (e: UninitializedPropertyAccessException) {
//            (context as BaseActivity<*>).initFragmentComponent()
//            context.fragmentComponent.inject(this as BaseFragment<BaseViewModel>)
//        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = provideViewModel(viewModelFactory)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        navController = NavHostFragment.findNavController(this)
        navOptionsDefaultAnim = NavOptions.Builder()
            .setLaunchSingleTop(true)
            .setEnterAnim(R.anim.nav_default_enter_anim)
            .setExitAnim(R.anim.nav_default_exit_anim)
            .setPopEnterAnim(R.anim.nav_default_pop_enter_anim)
            .setPopExitAnim(R.anim.nav_default_pop_exit_anim)
            .build()

        return inflater.inflate(layout(), container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initialization(view, rootView == null)
        rootView = view
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onBackPressed(): Boolean {
        return false
    }
}