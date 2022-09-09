package tr.com.tradesoft.todoapp.core

abstract class UseCase<Input, Output> {

    protected abstract suspend fun run(input: Input): DataResult<Output>

    suspend fun execute(input: Input): DataResult<Output> {
        return run(input)
    }
}