package com.chikeandroid.pagingtutsplus.adapter

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.chikeandroid.pagingtutsplus.R
import com.chikeandroid.pagingtutsplus.data.Dog
import com.chikeandroid.pagingtutsplus.data.Person
import com.chikeandroid.pagingtutsplus.data.TableItem
import kotlinx.android.synthetic.main.item_person.view.*
import android.R.attr.data
import android.app.ActionBar
import android.text.method.TextKeyListener.clear
import android.support.v7.util.DiffUtil

private const val DOG_VIEW_TYPE = 0
private const val PERSON_VIEW_TYPE = 1

interface OnDeleteButtonClickListener {
    fun onDeleteButtonClicked(item: TableItem)
}

class ItemAdapter(val context: Context, val listener: OnDeleteButtonClickListener) : RecyclerView.Adapter<ItemAdapter.PersonViewHolder>() {

    val items = mutableListOf<TableItem>()

    //class ItemAdapter(val context: Context) : PagedListAdapter<MergePersonOrDog, ItemAdapter.PersonViewHolder>(ItemDiffCallback()) {
    override fun getItemCount(): Int = items.count()

    override fun onBindViewHolder(holderPerson: PersonViewHolder, position: Int) {
        var person = items[position]
        if (person == null) {
            holderPerson.clear()
        } else {
            holderPerson.bind(person, listener)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        return PersonViewHolder(onCreateItemView(parent, viewType))
    }

    class PersonViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var tvName: TextView = view.name
        var btnDelete: Button = view.delete

        fun bind(item: TableItem, listener: OnDeleteButtonClickListener) {

            when (item) {
                is Person -> tvName.text = item.name
                is Dog -> tvName.text = item.name
            }

            btnDelete.setOnClickListener { listener.onDeleteButtonClicked(item) }
        }

        fun clear() {
            tvName.text = null
        }
    }

    override fun getItemViewType(position: Int) = getViewTypeFromItem(items[position])

    private fun onCreateItemView(parent: ViewGroup, viewType: Int): View = getViewFromType(viewType).apply { layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT) }

    private fun getViewTypeFromItem(item: TableItem): Int {
        return when (item) {
            is Person -> PERSON_VIEW_TYPE
            is Dog -> DOG_VIEW_TYPE
            else -> throw IllegalStateException("item: $item, is not recognized")
        }
    }

    private fun getViewFromType(viewType: Int): View =
        when (viewType) {
            PERSON_VIEW_TYPE -> LayoutInflater.from(context).inflate(R.layout.item_person, null, false)
            DOG_VIEW_TYPE -> LayoutInflater.from(context).inflate(R.layout.item_dog, null, false)
            else -> throw IllegalStateException("ViewType: $viewType, is not recognized")
        }


    fun setData(newData: List<TableItem>) {
        val postDiffCallback = ItemDiffCallback(items, newData)
        val diffResult = DiffUtil.calculateDiff(postDiffCallback)
        items.clear()
        items.addAll(newData)
        diffResult.dispatchUpdatesTo(this)
    }
}
