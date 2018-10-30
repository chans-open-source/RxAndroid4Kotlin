/*
 * Copyright (c) 2018. Create and edit by ChangedenChan.
 */

package com.chansos.rxandroid.kotlin.module.second

import com.alibaba.fastjson.JSON
import com.chansos.rxandroid.kotlin.api.test.Test
import com.chansos.rxandroid.kotlin.base.BaseFragment
import com.chansos.rxandroid.kotlin.model.ProjectModel
import com.chansos.rxandroid.kotlin.rx.RxKotlin
import com.chansos.rxandroid.kotlin.utils.support.LogUtils

class Presenter : Contract.Presenter {
  private lateinit var view: Contract.View

  override fun fetch() {
    try {
      RxKotlin
        .create<ProjectModel>(view as BaseFragment)
        .api(RxKotlin.api(Test::class.java).projectList(1, 2))
        .obs(Obs(view as BaseFragment))
    } catch (e: Exception) {
      e.printStackTrace()
    }
  }

  class Obs(fragment: BaseFragment) : RxKotlin.RxObserver<ProjectModel>(fragment) {
    override fun onNext(t: ProjectModel) {
      super.onNext(t)
      LogUtils.d(JSON.toJSONString(t))
    }

    override fun onError(e: Throwable) {
      super.onError(e)
      LogUtils.e(e)
    }
  }
}