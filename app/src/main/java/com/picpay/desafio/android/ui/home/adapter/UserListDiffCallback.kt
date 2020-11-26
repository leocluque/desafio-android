package com.picpay.desafio.android.ui.home.adapter

import androidx.recyclerview.widget.DiffUtil
import com.appfactory.domain.model.ContactModel

class UserListDiffCallback(
    private val oldList: List<ContactModel>,
    private val newList: List<ContactModel>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]

}