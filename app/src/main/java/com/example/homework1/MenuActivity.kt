package com.example.homework1

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.homework1.databinding.ActivityMenuBinding
import kotlinx.android.synthetic.main.list_item.view.*



class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val context = this
        val db = DataBaseHandler(context)
        //val data = db.readDataMessages()

        // ADAPTER SETIT

        val messages = db.getDataMessages()
        val arrayAdapter = MessageArrayAdapter(this, messages)

        //Toast.makeText(this@MenuActivity, "$messages", Toast.LENGTH_SHORT).show()
        binding.listView.adapter = arrayAdapter

        binding.txtLogged.text = "Logged in as: " + loggedAs
        // Shows the user's profile

        binding.btnProfile.setOnClickListener {
            Log.d("Lab", "ProfileActivity Button CLicked")
            startActivity(
                    Intent(applicationContext, ProfileActivity::class.java)
            )
        }

        binding.btnLogOut.setOnClickListener {
            Log.d("Lab", "LogOut Button CLicked")
            startActivity(
                    Intent(applicationContext, MainActivity::class.java)
            )
            loggedAs = ""
        }

        binding.btnMenuHelp.setOnClickListener {
            Log.d("Lab", "LogOut Button CLicked")
            startActivity(
                    Intent(applicationContext, MenuHelpActivity::class.java)
            )
        }

        binding.btnNewMessage.setOnClickListener {
            startActivity(
                    Intent(applicationContext, NewMessageActivity::class.java)
            )
        }

        binding.listView.setOnItemClickListener(object: AdapterView.OnItemClickListener{
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                globalPos = position
                //Toast.makeText(context, "GLOBALPOS: " + globalPos, Toast.LENGTH_SHORT).show()
                startActivity(
                        Intent(applicationContext, MessageEditDeleteActivity::class.java)
                )
            }
        })
    }
}


class ListMessages(
        val imageResource: Int,
        val message: String,
        val date: String
)

class MessageArrayAdapter(context: Context, messages: List<ListMessages>)
    : ArrayAdapter<ListMessages>(context, 0, messages) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup)
        : View {
        val rootView = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        val currentMessage = getItem(position)

        if (currentMessage != null) {
            rootView.messageImage.setImageResource(currentMessage.imageResource)
            rootView.messageContent.text = currentMessage.message
            rootView.messageDate.text = currentMessage.date
        }


        return rootView
    }
}