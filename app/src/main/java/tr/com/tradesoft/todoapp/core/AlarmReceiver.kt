package tr.com.tradesoft.todoapp.core

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import tr.com.tradesoft.todoapp.ui.alarm.AlarmActivity

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        println("AlarmReceiver.onReceive")
        if (context != null && intent != null) {
            val alarmIntent = Intent(context, AlarmActivity::class.java)
            alarmIntent.putExtra(ALARM_ID, intent.getLongExtra(ALARM_ID, -1L))
            alarmIntent.action = intent.action
            context.startActivity(alarmIntent)
        }
    }
}