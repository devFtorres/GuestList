package com.ftorres.guestlist.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ftorres.guestlist.viewmodel.GuestFormViewModel
import com.ftorres.guestlist.R
import com.ftorres.guestlist.constants.DataBaseConstants
import com.ftorres.guestlist.databinding.ActivityGuestFormBinding
import com.ftorres.guestlist.model.GuestModel

class GuestFormActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityGuestFormBinding
    private lateinit var viewModel: GuestFormViewModel

    private var guestId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuestFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(GuestFormViewModel::class.java)

        binding.buttonSave.setOnClickListener(this)
        binding.radioPresent.isChecked = true

        observe()
        loadData()
    }

    private fun loadData() {
        val bundle = intent.extras
        if (bundle != null) {
            guestId = bundle.getInt(DataBaseConstants.GUEST.ID)
            viewModel.get(guestId)
        }
    }

    private fun observe(){
        viewModel.guest.observe(this, Observer{
            binding.editName.setText(it.name)
            if (it.presence){
                binding.radioPresent.isChecked = true
            }else {
                binding.radioAbsent.isChecked = true
            }
        })

        viewModel.saveGuest.observe(this,  Observer {
            if (it != "") {
                Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
                finish()
            }else {
                Toast.makeText(applicationContext, "Failed to save", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onClick(v: View) {
        if (v.id == R.id.button_save) {
            val name = binding.editName.text.toString()
            val presence = binding.radioPresent.isChecked

            val model = GuestModel().apply {
                this.id = guestId
                this.name = name
                this.presence = presence
            }


            viewModel.save(model)

        }
    }
}