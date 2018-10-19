package com.chansos.rxandroid.kotlin.base

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.chansos.rxandroid.kotlin.utils.ImageLoader
import com.chansos.rxandroid.kotlin.utils.ObjectUtils
import com.chansos.rxandroid.kotlin.utils.ui.UIHelper

class BaseRecyclerViewHolder(itemView: View, private val context: Context) : RecyclerView.ViewHolder(itemView) {
  private val imageViewList: HashSet<Int> by lazy {
    HashSet<Int>()
  }

  companion object {
    internal fun create(itemView: View, parent: ViewGroup): BaseRecyclerViewHolder {
      return BaseRecyclerViewHolder(itemView, parent.context)
    }

    fun createView(parent: ViewGroup, rootLayoutResId: Int): View {
      return LayoutInflater.from(parent.context).inflate(rootLayoutResId, parent, false)
    }
  }

  fun <T : View?> get(viewId: Int): T {
    return UIHelper.get<T>(itemView, viewId)
  }

  fun setImage(viewId: Int, image: String) {
    if (context is Activity) {
      ImageLoader.load(get(viewId), image, context)
    } else if (context is Fragment) {
      ImageLoader.load(get(viewId), image, context)
    }
    imageViewList.add(viewId)
  }

  fun setImage(viewId: Int, image: Int) {
    if (context is Activity) {
      ImageLoader.load(get(viewId), image, context)
    } else if (context is Fragment) {
      ImageLoader.load(get(viewId), image, context)
    }
    imageViewList.add(viewId)
  }

  fun setText(viewId: Int, content: String) {
    (get(viewId) as TextView).text = content
  }

  fun setText(viewId: Int, content: Int) {
    (get(viewId) as TextView).setText(content)
  }

  fun release() {
    imageViewList.forEach { viewId ->
      run {
        if (context is Activity && !context.isDestroyed) {
          ImageLoader.release(get(viewId), context)
        } else if (context is Fragment && !context.isDetached) {
          ImageLoader.release(get(viewId), context)
        }
      }
    }
    ObjectUtils.destory(this)
  }
}