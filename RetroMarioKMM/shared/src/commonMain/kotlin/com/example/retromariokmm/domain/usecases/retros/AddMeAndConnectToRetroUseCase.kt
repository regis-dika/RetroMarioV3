package com.example.retromariokmm.domain.usecases.retros

import com.example.retromariokmm.utils.Error
import com.example.retromariokmm.utils.Loading
import com.example.retromariokmm.utils.Resource
import com.example.retromariokmm.utils.Success
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AddMeAndConnectToRetroUseCase(private val addUserToRetroRetroUseCase: AddUserToRetroRetroUseCase, private val connectUserToRetroUseCase: ConnectUserToRetroUseCase) {
    //TODO Inject repo

    suspend fun invoke(retroId: String): Flow<Resource<Unit>> {
       return addUserToRetroRetroUseCase.invoke(retroId).map{ res ->
            when(res){
                is Error -> Error(res.msg)
                is Loading -> Loading()
                is Success -> {
                    connectUserToRetroUseCase.invoke(retroId)
                    Success(Unit)
                }
            }
        }
    }
}