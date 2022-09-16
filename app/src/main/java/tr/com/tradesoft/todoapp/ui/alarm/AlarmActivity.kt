package tr.com.tradesoft.todoapp.ui.alarm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tr.com.tradesoft.todoapp.core.ALARM_ID
import tr.com.tradesoft.todoapp.databinding.ActivityAlarmBinding
import java.text.SimpleDateFormat

class AlarmActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAlarmBinding
    private lateinit var alarmViewModel: AlarmViewModel
    private val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        alarmViewModel = ViewModelProvider(this).get(AlarmViewModel::class.java)

        val id = intent.getLongExtra(ALARM_ID, -1L)

        if (id != -1L) {
            alarmViewModel.getTodo(id)
        }

        initComponents()
        collectState()
    }

    private fun initComponents() {
        binding.alarmTodoClose.setOnClickListener {
            finish()
        }
    }


    private fun collectState() {
        alarmViewModel.viewModelScope.launch {
            alarmViewModel.state.collect { state ->
                state.todo?.run {
                    binding.alarmTodoTitle.text = this.title
                    binding.alarmTodoDescription.text = this.description
                    this.dueDate?.let {
                        binding.alarmTodoDueDate.text = sdf.format(it)
                    }
                }
            }
        }
    }
}