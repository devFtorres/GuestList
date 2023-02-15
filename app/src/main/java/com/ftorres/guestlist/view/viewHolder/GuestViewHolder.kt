package com.ftorres.guestlist.view.viewHolder

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ftorres.guestlist.databinding.RowGuestBinding
import com.ftorres.guestlist.model.GuestModel
import com.ftorres.guestlist.view.listener.OnGuestListener

class GuestViewHolder(private val bind: RowGuestBinding, private val listener: OnGuestListener) :
    RecyclerView.ViewHolder(bind.root) {

    fun bind(guest: GuestModel) {
        bind.textName.text = guest.name


        bind.textName.setOnClickListener {
            listener.onClick(guest.id)
        }

        bind.textName.setOnLongClickListener(View.OnLongClickListener {

            AlertDialog.Builder(itemView.context)
                .setTitle("Remove a guest")
                .setMessage("Are you sure you want to remove ?")
                .setPositiveButton("Yes") { dialog, which ->
                    listener.onDelete(guest.id)
                }
                .setNegativeButton("No", null)
                .create()
                .show()


            true
        })

    }


}