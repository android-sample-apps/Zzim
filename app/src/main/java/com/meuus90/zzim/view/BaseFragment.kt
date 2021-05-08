package com.meuus90.zzim.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.meuus90.zzim.di.Injectable

abstract class BaseFragment<T : ViewBinding> : Fragment(), Injectable {
    companion object {
        const val FRAGMENT_TAG = "fragment_tag"
    }

    private var _binding: T? = null
    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> T

    internal val binding
        get() = _binding as T

    lateinit var baseActivity: BaseActivity<*>
    private lateinit var context: Context
    override fun getContext() = context

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = _binding ?: bindingInflater.invoke(inflater, container, false)

        return requireNotNull(_binding).root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        baseActivity = (context as BaseActivity<*>)
        this.context = context
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }
}