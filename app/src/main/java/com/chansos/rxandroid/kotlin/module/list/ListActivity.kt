package com.chansos.rxandroid.kotlin.module.list

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chansos.libs.rxkotlin.AppHelper
import com.chansos.libs.rxkotlin.anno.ModulePresenter
import com.chansos.libs.rxkotlin.anno.PageLayoutId
import com.chansos.libs.rxkotlin.base.BaseActivity
import com.chansos.libs.rxkotlin.base.BaseRecyclerViewAdapter
import com.chansos.rxandroid.kotlin.R
import kotlinx.android.synthetic.main.activity_list.*

@PageLayoutId(R.layout.activity_list)
@ModulePresenter("com.chansos.rxandroid.kotlin.module.list.Presenter")
class ListActivity : BaseActivity(), BaseRecyclerViewAdapter.OnItemClickListener, Contract.View {
    private lateinit var presenter: Presenter
    private lateinit var adapter: ImageListAdapter

    override fun initialize() {
        val layoutManager = LinearLayoutManager(self, LinearLayoutManager.VERTICAL, false)
        adapter = ImageListAdapter()
        recycler_view.layoutManager = layoutManager
        recycler_view.adapter = adapter
        adapter.onItemClickListener = this
        adapter.setDataList(presenter.imageList)
    }

    override fun onItemClick(view: View, position: Int) {
        AppHelper.UI.showToast("Clicked $position.")
    }

    override fun onDestroy() {
        adapter.release()
        super.onDestroy()
    }
}
