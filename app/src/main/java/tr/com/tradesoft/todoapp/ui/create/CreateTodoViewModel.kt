package tr.com.tradesoft.todoapp.ui.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.withContext
import org.koin.java.KoinJavaComponent.inject
import tr.com.tradesoft.todoapp.core.DataResult
import tr.com.tradesoft.todoapp.domain.CreateTodoUseCase
import java.util.*

class CreateTodoViewModel : ViewModel() {

    private val createTodoUseCase: CreateTodoUseCase by inject(CreateTodoUseCase::class.java)

    suspend fun createTodo(
        title: String,
        description: String? = null,
        setAlarm: Boolean,
        dueDate: Date? = null
    ): DataResult<Long> {
        if (title.isBlank()) {
            return DataResult.Error(Exception("Lütfen başlık giriniz."))
        }

        if (setAlarm) {
            if (dueDate == null) {
                return DataResult.Error(Exception("Alarm kurmak için tarih ve saat seçiniz."))
            } else {
                if (Date().time >= dueDate.time) {
                    return DataResult.Error(Exception("Lütfen daha büyük bir tarih&saat seçin."))
                }
            }
        }

        return withContext(viewModelScope.coroutineContext) {
            createTodoUseCase.execute(CreateTodoUseCase.Input(title, description, dueDate))
        }
    }
}