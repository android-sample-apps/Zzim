package com.meuus90.zzim.view.activity

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.annotation.GlideModule
import com.meuus90.zzim.R
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

abstract class BaseActivity<T : ViewBinding> : AppCompatActivity(), HasAndroidInjector {
    private var _binding: T? = null
    abstract val bindingInflater: (LayoutInflater) -> T

    internal val binding: T
        get() = _binding as T

    internal lateinit var glideRequestManager: RequestManager

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector() = dispatchingAndroidInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        @GlideModule
        glideRequestManager = Glide.with(this)

        _binding = _binding ?: bindingInflater.invoke(layoutInflater)
        setContentView(requireNotNull(_binding).root)
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }

    fun createDialog(message: String?) {
        AlertDialog.Builder(this)
            .setTitle(R.string.title_alert)
            .setMessage(message)
            .setPositiveButton(R.string.button_configm) { dialogInterface: DialogInterface, i: Int -> }
            .create()
            .show()
    }
}