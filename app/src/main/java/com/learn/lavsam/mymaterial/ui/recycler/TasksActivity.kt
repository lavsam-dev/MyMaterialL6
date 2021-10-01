package com.learn.lavsam.mymaterial.ui.recycler

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.learn.lavsam.mymaterial.R
import kotlinx.android.synthetic.main.activity_tasks_recycler.*

class TasksActivity : AppCompatActivity() {

    private val adapter: ItemAdapter by lazy { ItemAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks_recycler)

        val taskList: MutableList<DataTask> = mutableListOf(
//            DataTask(0,"Задачи", ""),
            DataTask(1,"Task 01", "Description task 01"),
            DataTask(2,"Task 02", "Description task 02", true),
            DataTask(3,"Task 03", "Description task 03", true),
            DataTask(4,"Task 04", "Description task 04")
        )

        tasksView.adapter = adapter
        adapter.items = taskList

        tasksActivityFAB.setOnClickListener() {
            adapter.addTask(DataTask(999,"Закончить курс Material Design", "Внимательно прослушать все лекции Георгия. Вдумываться в нюансы реализации. Творчески подходить к выполнению домашних заданий (насколько это возможно)"))
        }
    }
}

abstract class BaseViewHolderT(view: View) : RecyclerView.ViewHolder(view)

private class ItemAdapter : RecyclerView.Adapter<BaseViewHolderT>() {

    companion object {
        private const val TYPE_HEADER = 1
        private const val TYPE_TASK = 2
    }

    var items = mutableListOf<DataTask>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolderT {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_tasks_recycler_item_task, parent, false)

        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: BaseViewHolderT, position: Int) {
        val currentTask = items[position]

        if (getItemViewType(position) == TYPE_HEADER || getItemViewType(position) == TYPE_TASK) {
            (holder as TaskViewHolder).bind(currentTask)
        } else {  }
    }

    fun addTask(task: DataTask) {
        items.add(task)
        notifyItemInserted(items.size - 1)
//        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int =
        if (items[position].id == 0) {
            TYPE_HEADER
        } else {
            TYPE_TASK
        }

    private fun generateItem() = DataTask(1, "Task 88", "", false)

    inner class TaskViewHolder(view: View) : BaseViewHolderT(view) {
        private val title = view.findViewById<TextView>(R.id.taskHeader)
        private val description = view.findViewById<TextView>(R.id.taskDescription)
        private val imgSave = view.findViewById<ImageView>(R.id.taskSave)
        private val imgIcon = view.findViewById<ImageView>(R.id.taskIcon)
        private val imgUp = view.findViewById<ImageView>(R.id.moveItemUp)
        private val imgDown = view.findViewById<ImageView>(R.id.moveItemDown)
        private val imgAdd = view.findViewById<ImageView>(R.id.addItemImageView)
        private val imgRemove = view.findViewById<ImageView>(R.id.removeItemImageView)

        fun bind(task: DataTask) {
            title.text = task.title
            description.text = task.description
            if (task.isExpand) {
                description.setVisibility(View.VISIBLE)
            } else {
                description.setVisibility(View.GONE)
            }
            imgIcon.setOnClickListener { toggleText() }
            imgUp.setOnClickListener { moveUp() }
            imgDown.setOnClickListener { moveDown() }
            imgAdd.setOnClickListener { addItem() }
            imgRemove.setOnClickListener { removeItem() }
            imgSave.setOnClickListener { saveItem() }
        }

        private fun addItem() {
            items.add(layoutPosition, generateItem())
            notifyItemInserted(layoutPosition)
        }

        private fun removeItem() {
            items.removeAt(layoutPosition)
            notifyItemRemoved(layoutPosition)
        }

        private fun moveUp() {
            layoutPosition.takeIf { it > 0 }?.also { currentPosition ->
                items.removeAt(currentPosition).apply {
                    items.add(currentPosition - 1, this)
                }
                notifyItemMoved(currentPosition, currentPosition - 1)
            }
        }

        private fun moveDown() {
            layoutPosition.takeIf { it < items.size - 1 }?.also { currentPosition ->
                items.removeAt(currentPosition).apply {
                    items.add(currentPosition + 1, this)
                }
                notifyItemMoved(currentPosition, currentPosition + 1)
            }
        }

        private fun toggleText() {
            items[layoutPosition].isExpand = !items[layoutPosition].isExpand
            notifyItemChanged(layoutPosition)
        }

        private fun saveItem() {
            items[layoutPosition].title = title.text.toString()
            items[layoutPosition].description = description.text.toString()
            notifyItemChanged(layoutPosition)
//            Toast.makeText(title.context, items[layoutPosition].description, Toast.LENGTH_SHORT).show()
//            Toast.makeText(title.context, description.text, Toast.LENGTH_SHORT).show()
        }

    }
}

open class ItemData

data class DataTask(
    val id: Int,
    var title: String,
    var description: String? = "",
    var isExpand: Boolean = false
) : ItemData()



