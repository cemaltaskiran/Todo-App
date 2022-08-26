package tr.com.tradesoft.todoapp.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tr.com.tradesoft.todoapp.R
import tr.com.tradesoft.todoapp.data.Todo

class TodoListAdapter(private val dataSet: List<Todo>) :
    RecyclerView.Adapter<TodoListAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView
        val description: TextView

        init {
            // Define click listener for the ViewHolder's View.
            title = view.findViewById(R.id.listItemTitle)
            description = view.findViewById(R.id.listItemDescription)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_todo_list, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val todo = dataSet[position]
        viewHolder.title.text = todo.title
        viewHolder.description.text = todo.description
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}