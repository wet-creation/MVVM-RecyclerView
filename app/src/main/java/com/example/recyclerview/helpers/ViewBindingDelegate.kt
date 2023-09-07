package com.example.recyclerview.helpers

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.reflect.KProperty

inline fun <reified B : ViewBinding> Fragment.viewBinding(): ViewBindingDelegate<B> {
    return ViewBindingDelegate(this, B::class.java)
}

class ViewBindingDelegate<B : ViewBinding>(
    private val fragment: Fragment,
    private val viewBindingClass: Class<B>
) {
    var binding: B? = null
    operator fun getValue(thisRef: Any?, property: KProperty<*>): B {
        val lifecycleOwner = fragment.viewLifecycleOwner
        if (lifecycleOwner.lifecycle.currentState == Lifecycle.State.DESTROYED) {
            throw java.lang.IllegalStateException("Called after OnDestroyView()")
        } else if (fragment.view != null) {
            return getOrCreateBinding(lifecycleOwner)
        } else {
            throw java.lang.IllegalStateException("Called before OnCreateView()")
        }


    }

    private fun getOrCreateBinding(lifecycleOwner: LifecycleOwner): B {
        return this.binding ?: let {
            val method = viewBindingClass.getMethod("bind", View::class.java)
            val binding = method.invoke(null, fragment.view) as B
            lifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
                override fun onDestroy(owner: LifecycleOwner) {
                    super.onDestroy(owner)
                    this@ViewBindingDelegate.binding = null
                }
            })
            this.binding = binding
            binding
        }
    }


}