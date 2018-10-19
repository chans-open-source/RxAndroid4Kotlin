package com.chansos.rxandroid.kotlin.utils.broadcast

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.support.v4.content.LocalBroadcastManager
import com.chansos.rxandroid.kotlin.utils.AppManager
import com.chansos.rxandroid.kotlin.utils.ui.UIHelper

class BroadcastSupport {
  internal val receiverManager: LocalBroadcastManager by lazy {
    LocalBroadcastManager.getInstance(UIHelper.getContext())
  }

  fun register(receiver: BroadcastReceiver, vararg actions: String): Boolean {
    return try {
      val filter = IntentFilter()
      actions.forEach { action -> filter.addAction(action) }
      receiverManager.registerReceiver(receiver, filter)
      true
    } catch (e: Exception) {
      e.printStackTrace()
      false
    }
  }

  fun unregister(receiver: BroadcastReceiver): Boolean {
    return try {
      receiverManager.unregisterReceiver(receiver)
      true
    } catch (e: Exception) {
      e.printStackTrace()
      false
    }
  }

  private fun createIntent(clazz: Class<*>, action: String): Intent {
    val intent = Intent()
    intent.setClass(AppManager.getContext(), clazz)
    intent.action = action
    return intent
  }

  fun send(clazz: Class<*>, action: String) {
    receiverManager.sendBroadcast(createIntent(clazz, action))
  }

  fun sendSync(clazz: Class<*>, action: String) {
    receiverManager.sendBroadcastSync(createIntent(clazz, action))
  }
}