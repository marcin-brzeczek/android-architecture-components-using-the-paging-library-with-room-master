package com.chikeandroid.pagingtutsplus

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import com.chikeandroid.pagingtutsplus.adapter.OnDeleteButtonClickListener
import com.chikeandroid.pagingtutsplus.adapter.ItemAdapter
import com.chikeandroid.pagingtutsplus.data.Dog
import com.chikeandroid.pagingtutsplus.data.Person
import com.chikeandroid.pagingtutsplus.data.SingleLiveEvent
import com.chikeandroid.pagingtutsplus.data.TableItem
import com.chikeandroid.pagingtutsplus.viewmodels.DogsData
import com.chikeandroid.pagingtutsplus.viewmodels.MergedData
import com.chikeandroid.pagingtutsplus.viewmodels.PersonData
import com.chikeandroid.pagingtutsplus.viewmodels.PersonsViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnDeleteButtonClickListener {

    private lateinit var viewModel: PersonsViewModel

    private lateinit var adapter: ItemAdapter

    private lateinit var liveData: SingleLiveEvent<MergedData>

    val mutableItems = mutableListOf<TableItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(PersonsViewModel::class.java)
        adapter = ItemAdapter(this, this)
        findViewById<RecyclerView>(R.id.name_list).adapter = adapter

        subscribeUi(adapter)
    }

    private fun subscribeUi(adapter: ItemAdapter) {
         liveData = viewModel.fetchData()

        var dogsItems: List<Dog>? = null
        var personItems: List<Person>? = null

        liveData.observe(this, object : Observer<MergedData> {
            override fun onChanged(it: MergedData?) {
                when (it) {
                    is DogsData -> dogsItems = it.dogsItems
                    is PersonData -> personItems = it.personItems
                }

                if (personItems != null && dogsItems != null) {
                    // both data is ready, proceed to process them
                    mutableItems.clear()
                    mutableItems.addAll(personItems!!)
                    mutableItems.addAll(dogsItems!!)

                    adapter.setData(mutableItems)
                    // stop observing
//                    liveData.removeObserver(this)
//                    liveData.call()

                }
            }
        })


//        viewModel.getItemLiveData().observe(this, Observer { items ->
//            if (items != null) adapter.submitList(items)
//        })
    }

    override fun onDeleteButtonClicked(item: TableItem) {
        viewModel.deleteItem(item)
    }
}