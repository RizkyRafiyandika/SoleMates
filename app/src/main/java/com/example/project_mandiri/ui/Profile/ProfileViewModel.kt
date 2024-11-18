// ProfileViewModel.kt
import androidx.lifecycle.ViewModel

class ProfileViewModel(username: String) : ViewModel() {
    private var _username: String = username

    fun getUsername(): String {
        return _username
    }

    fun setUsername(username: String) {
        _username = username
    }
}
