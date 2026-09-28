package com.example.steppy.viewmodel.social

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.steppy.data.model.User
import com.example.steppy.data.repository.FriendsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FriendsViewModel @Inject constructor(
    private val friendsRepository: FriendsRepository
) : ViewModel() {

    private val _searchResults = MutableStateFlow<List<User>>(emptyList())
    val searchResults: StateFlow<List<User>> = _searchResults

    fun searchUsers(query: String) {
        viewModelScope.launch {
            friendsRepository.searchUsers(query).onSuccess {
                _searchResults.value = it
            }
        }
    }

    fun sendFriendRequest(fromUserId: String, toUserId: String) {
        viewModelScope.launch {
            friendsRepository.sendFriendRequest(fromUserId, toUserId)
        }
    }
}
