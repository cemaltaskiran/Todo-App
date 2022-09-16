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
import tr.com.tradesoft.todoapp.MainActivity
import tr.com.tradesoft.todoapp.R
import tr.com.tradesoft.todoapp.core.ALARM_ID
import tr.com.tradesoft.todoapp.core.AlarmReceiver
import tr.com.tradesoft.todoapp.core.DataResult
import tr.com.tradesoft.todoapp.core.NavigatorFragment
import tr.com.tradesoft.todoapp.databinding.FragmentCreateTodoBinding
import java.util.*

class CreateTodoFragment : NavigatorFragment() {

    companion object {
        fun newInstance() = CreateTodoFragment()
    }

    private lateinit var viewModel: CreateTodoViewModel

    private var _binding: FragmentCreateTodoBinding? = null
    private val binding get() = _binding!!

    private val calendar = Calendar.getInstance()
    private var isDatePicked = false
    private var isTimePicked = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateTodoBinding.inflate(inflater, container, false)
        return _binding?.root
    }
    override val title: String get() = getString(R.string.create_todo_title)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(CreateTodoViewModel::class.java)
        binding.createTodoButton.setOnClickListener {
            val setAlarm = binding.setAlarmForTodo.isChecked

            val dueDate = if (setAlarm && isDatePicked && isTimePicked) {
                calendar.time
            } else {
                null
            }

            viewModel.viewModelScope.launch {
                when (val result = viewModel.createTodo(
                    binding.todoTitle.text.toString(),
                    binding.todoDescription.text.toString(),
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

        binding.setAlarmForTodo.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.dateTimell.visibility = View.VISIBLE
            } else {
                binding.dateTimell.visibility = View.GONE
            }
        }

        binding.dateTextView.setOnClickListener {
            context?.run {
                val datePicker = DatePickerDialog(
                    this,
                    { _, year, month, dayOfMonth ->
                        binding.dateTextView.text = "%s: %02d.%02d.%d".format(
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

        binding.timeTextView.setOnClickListener {
            context?.run {
                val timePickerDialog =
                    TimePickerDialog(
                        this,
                        { _, hourOfDay, minute ->
                            binding.timeTextView.text = "%s: %02d:%02d".format(
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

    private fun setAlarm(context: Context, calendar: Calendar, id: Long) {
        val alarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java).let { intent ->
            intent.putExtra(ALARM_ID, id)
            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_MUTABLE)
        }
        alarmManager?.set(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            calendar.timeInMillis,
            intent
        )
    }

}