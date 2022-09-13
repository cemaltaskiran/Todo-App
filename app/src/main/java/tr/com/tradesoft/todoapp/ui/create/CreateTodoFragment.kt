package tr.com.tradesoft.todoapp.ui.create

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tr.com.tradesoft.todoapp.R
import tr.com.tradesoft.todoapp.core.AlarmReceiver
import tr.com.tradesoft.todoapp.core.DataResult
import tr.com.tradesoft.todoapp.core.NavigatorFragment
import java.util.*

class CreateTodoFragment : NavigatorFragment() {

    companion object {
        fun newInstance() = CreateTodoFragment()
    }

    private lateinit var viewModel: CreateTodoViewModel
    private lateinit var todoTitle: EditText
    private lateinit var todoDescription: EditText
    private lateinit var createTodoButton: Button
    private lateinit var dateTextView: TextView
    private lateinit var timeTextView: TextView
    private lateinit var setAlarmForTodo: CheckBox
    private lateinit var dateTimell: LinearLayout
    private val calendar = Calendar.getInstance()
    private var isDatePicked = false
    private var isTimePicked = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_todo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(CreateTodoViewModel::class.java)
        todoTitle = view.findViewById(R.id.todoTitle)
        todoDescription = view.findViewById(R.id.todoDescription)
        createTodoButton = view.findViewById(R.id.createTodoButton)
        dateTextView = view.findViewById(R.id.dateTextView)
        timeTextView = view.findViewById(R.id.timeTextView)
        setAlarmForTodo = view.findViewById(R.id.setAlarmForTodo)
        dateTimell = view.findViewById(R.id.dateTimell)

        createTodoButton.setOnClickListener {
            val setAlarm = setAlarmForTodo.isChecked

            val dueDate = if (setAlarm && isDatePicked && isTimePicked) {
                calendar.time
            } else {
                null
            }

            viewModel.viewModelScope.launch {
                when (val result = viewModel.createTodo(
                    todoTitle.text.toString(),
                    todoDescription.text.toString(),
                    setAlarm,
                    dueDate
                )) {
                    is DataResult.Error -> {
                        Toast(context).apply {
                            setText(result.exception.message)
                            duration = Toast.LENGTH_SHORT
                        }.show()
                    }
                    is DataResult.Success -> {
                        context?.run {
                            setAlarm(this, calendar, result.data)
                        }

                        navigator?.popBack()
                    }
                }
            }
        }

        setAlarmForTodo.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                dateTimell.visibility = View.VISIBLE
            } else {
                dateTimell.visibility = View.GONE
            }
        }

        dateTextView.setOnClickListener {
            context?.run {
                val datePicker = DatePickerDialog(
                    this,
                    { _, year, month, dayOfMonth ->
                        dateTextView.text = "%s: %02d.%02d.%d".format(
                            getString(R.string.date),
                            dayOfMonth,
                            month,
                            year
                        )
                        calendar.set(Calendar.YEAR, year)
                        calendar.set(Calendar.MONTH, month)
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                        isDatePicked = true
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH),
                )
                datePicker.show()
            }
        }

        timeTextView.setOnClickListener {
            context?.run {
                val timePickerDialog =
                    TimePickerDialog(
                        this,
                        { _, hourOfDay, minute ->
                            timeTextView.text = "%s: %02d:%02d".format(
                                getString(R.string.time),
                                hourOfDay,
                                minute,
                            )
                            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                            calendar.set(Calendar.MINUTE, minute)
                            isTimePicked = true
                        },
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        true
                    )
                timePickerDialog.show()
            }
        }
    }

    private fun setAlarm(context: Context, calendar: Calendar, id: Long){
        val alarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra("id", id)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
        alarmManager?.set(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    }

}