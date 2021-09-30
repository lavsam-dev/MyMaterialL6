package com.learn.lavsam.mymaterial.ui.recycler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.learn.lavsam.mymaterial.R
import kotlinx.android.synthetic.main.activity_tasks_recycler.*
import kotlinx.android.synthetic.main.activity_tasks_recycler_item_task.view.*
import java.util.*

class TasksActivity : AppCompatActivity() {

    private val adapter: ItemAdapter by lazy { ItemAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks_recycler)

        val taskList = mutableListOf(
            DataTask(0, "Task 01", "Description task 01"),
            DataTask(1, "Task 02", "Description task 02")
        )

        tasksView.adapter = adapter
        adapter.items = taskList
        tasksActivityFAB.setOnClickListener { adapter.appendItem() }
//        tasksActivityDiffUtilFAB.setOnClickListener { changeAdapterData() }
    }
}

private class ItemAdapter : RecyclerView.Adapter<BaseViewHolderTask>() {

    companion object {
        private const val TASK_TYPE = 1
    }

    var items = mutableListOf<ItemData>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolderTask {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: BaseViewHolderTask, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    inner class ViewHolderTask(view: View) : BaseViewHolderTask(view) {
        private val title = view.findViewById<EditText>(R.id.taskHeader)
        private val description = view.findViewById<EditText>(R.id.taskDescription)

        fun bind(task: DataTask) {
            taskHeader.text
        }
    }

}

abstract class BaseViewHolderTask(view: View) : RecyclerView.ViewHolder(view)

open class ItemData

data class DataTask(
    val id: Int = 0,
    val titleTask: String = "Task title",
    val descriptionTask: String? = "Task Description",
    val isExpand: Boolean = false
): ItemData()


