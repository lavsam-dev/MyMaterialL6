package com.learn.lavsam.mymaterial.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.learn.lavsam.mymaterial.R
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity() {

    private val adapter: ItemAdapter by lazy { ItemAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val messageList = mutableListOf(
            IncomeMessage("Hello", "Emmy"),
            OutcomeMessage("Hello", "Ron"),
            IncomeMessage("Hello", "Emmy"),
            OutcomeMessage("Hello", "Ron"),
            IncomeMessage("Hello", "Emmy"),
            IncomeMessage("Hello", "Emmy"),
            OutcomeMessage("Hello", "Ron")
        )

        chatRV.adapter = adapter
        adapter.items = messageList

        sendMessage.setOnClickListener() {
            adapter.addMessage(IncomeMessage("mee", "Anonim"))
        }
    }
}

abstract class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view)

private class ItemAdapter : RecyclerView.Adapter<BaseViewHolder>() {

    companion object {
        private const val INCOME_TYPE = 1
        private const val OUTCOME_TYPE = 2
    }

    var items = mutableListOf<ItemData>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
        if (viewType == INCOME_TYPE) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_income_message, parent, false)
            IncomeVH(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_outcome_message, parent, false)
            OutcomeVH(view)
        }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val currentMessage = items[position]

        if (getItemViewType(position) == INCOME_TYPE) {
            (holder as IncomeVH).bind(currentMessage as IncomeMessage)
        } else {
            (holder as OutcomeVH).bind(currentMessage as OutcomeMessage)
        }
    }

    fun addMessage(message: ItemData) {
        items.add(message)
        notifyItemInserted(items.size - 1)
//        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int =
        if (items[position] is IncomeMessage) {
            INCOME_TYPE
        } else {
            OUTCOME_TYPE
        }

    private fun deleteMessage(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
//        notifyDataSetChanged()
    }

    inner class IncomeVH(view: View) : BaseViewHolder(view) {
        private val author = view.findViewById<TextView>(R.id.author)
        private val messageText = view.findViewById<TextView>(R.id.text)
        private val img = view.findViewById<ImageView>(R.id.img)

        fun bind(message: IncomeMessage) {
            author.text = message.author
            messageText.text = message.isImageVisible.toString()
            img.setOnClickListener {
                layoutPosition.takeIf { it > 1 }?.also { currentPosition ->
                    items.removeAt(currentPosition).apply {
                        items.add(currentPosition - 1, this)
                    }
                    notifyItemMoved(currentPosition, currentPosition - 1)
                }
            }
            author.setOnClickListener {
                messageText.text = message.isImageVisible.toString()
                val model = (items[layoutPosition] as IncomeMessage)
                model.isImageVisible = !model.isImageVisible
                notifyItemChanged(layoutPosition)
            }
        }
    }

    private class OutcomeVH(view: View) : BaseViewHolder(view) {
        private val author = view.findViewById<TextView>(R.id.author)
        private val messageText = view.findViewById<TextView>(R.id.text)

        fun bind(message: OutcomeMessage) {
            author.text = message.author
            messageText.text = message.text
        }
    }
}


open class ItemData

data class IncomeMessage(
    val text: String,
    val author: String,
    var isImageVisible: Boolean = false
) : ItemData()

data class OutcomeMessage(
    val text: String,
    val author: String
) : ItemData()
