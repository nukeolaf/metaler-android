package com.bnvs.metaler.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bnvs.metaler.data.homeposts.model.HomePosts
import com.bnvs.metaler.data.homeposts.source.repository.HomePostsRepository
import com.bnvs.metaler.data.profile.model.Profile
import com.bnvs.metaler.data.profile.source.repository.ProfileRepository
import com.bnvs.metaler.network.NetworkUtil
import com.bnvs.metaler.util.constants.NO_ERROR_TO_HANDLE

class ViewModelHome(
    private val profileRepository: ProfileRepository,
    private val homePostsRepository: HomePostsRepository
) : ViewModel() {

    // errorMessage Handling
    private val _errorToastMessage = MutableLiveData<String>().apply { value = "" }
    val errorToastMessage: LiveData<String> = _errorToastMessage
    private val _errorDialogMessage = MutableLiveData<String>().apply { value = "" }
    val errorDialogMessage: LiveData<String> = _errorDialogMessage
    private val _errorCode = MutableLiveData<Int>().apply { value = NO_ERROR_TO_HANDLE }
    val errorCode: LiveData<Int> = _errorCode

    // tapBarButtons
    private val _openMaterialsActivity = MutableLiveData<Boolean>().apply { value = false }
    val openMaterialsActivity: LiveData<Boolean> = _openMaterialsActivity
    private val _openManufacturesActivity = MutableLiveData<Boolean>().apply { value = false }
    val openManufacturesActivity: LiveData<Boolean> = _openManufacturesActivity
    private val _openBookmarksActivity = MutableLiveData<Boolean>().apply { value = false }
    val openBookmarksActivity: LiveData<Boolean> = _openBookmarksActivity
    private val _openMyPageActivity = MutableLiveData<Boolean>().apply { value = false }
    val openMyPageActivity: LiveData<Boolean> = _openMyPageActivity

    private val _openDetailActivity = MutableLiveData<Boolean>().apply { value = false }
    val openDetailActivity: LiveData<Boolean> = _openDetailActivity
    private val _postId = MutableLiveData<Int>()
    val postId: LiveData<Int> = _postId

    private val _homeErrorVisibility = MutableLiveData<Boolean>().apply { value = false }
    val homeErrorVisibility: LiveData<Boolean> = _homeErrorVisibility

    private val _profile = MutableLiveData<Profile>()
    val profile: LiveData<Profile> = _profile

    private val _homePosts = MutableLiveData<HomePosts>()
    val homePosts: LiveData<HomePosts> = _homePosts

    init {
        loadProfile()
        loadHomePosts()
    }

    private fun loadProfile() {
        profileRepository.getProfile(
            onProfileLoaded = { profile ->
                _profile.value = profile
            },
            onProfileNotExist = {
                _errorToastMessage.apply {
                    value = "프로필 데이터를 불러오는데 실패했습니다"
                    value = clearStringValue()
                }
            }
        )
    }

    private fun loadHomePosts() {
        homePostsRepository.getHomePosts(
            onSuccess = { response ->
                if (response.materials.isEmpty() || response.manufactures.isEmpty()) {
                    _homeErrorVisibility.value = true
                } else {
                    _homePosts.value = response
                }
            },
            onFailure = { e ->
                _homeErrorVisibility.value = true
                _errorToastMessage.apply {
                    value = NetworkUtil.getErrorMessage(e)
                    value = clearStringValue()
                }
            },
            handleError = { e ->
                _errorCode.apply {
                    value = e
                    value = NO_ERROR_TO_HANDLE
                }
            }
        )
    }

    fun startMaterialsActivity() {
        _openMaterialsActivity.apply {
            value = true
            value = false
        }
    }

    fun startManufacturesActivity() {
        _openManufacturesActivity.apply {
            value = true
            value = false
        }
    }

    fun startBookmarksActivity() {
        _openBookmarksActivity.apply {
            value = true
            value = false
        }
    }

    fun startMyPageActivity() {
        _openMyPageActivity.apply {
            value = true
            value = false
        }
    }

    fun openPostDetail(postId: Int) {
        _postId.value = postId
        startDetailActivity()
    }

    private fun startDetailActivity() {
        _openDetailActivity.apply {
            value = true
            value = false
        }
    }

    private fun clearStringValue(): String {
        return ""
    }

}