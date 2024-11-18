package com.example.project_mandiri.ui.Profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.project_mandiri.LogoutActivity
import com.example.project_mandiri.MainActivity
import com.example.project_mandiri.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate layout dan binding menggunakan properti _binding
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Mendapatkan email dan nomor telepon dari intent
        val userEmail = activity?.intent?.getStringExtra("userEmail")
        val phoneNumber = getPhoneNumber()

        // Mengatur teks TextView dengan email dan nomor telepon
        val textViewUsername: TextView = binding.textUsername
        userEmail?.let {
            textViewUsername.text = "Welcome, $userEmail"
        }

        val textViewPhone: TextView = binding.noTelp
        phoneNumber?.let {
            textViewPhone.text = "Phone: $phoneNumber"
        }
        binding.btnLogout.setOnClickListener {
            logout()
        }
        val darkModeButton: ImageButton = binding.buttonDarkMode
        darkModeButton.setOnClickListener {
            val currentMode = AppCompatDelegate.getDefaultNightMode()
            if (currentMode == AppCompatDelegate.MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }

        return root
    }

   private fun logout() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish() // Tutup aktivitas ini agar pengguna tidak dapat kembali dengan tombol back
    }
    private fun getPhoneNumber(): String? {
        val sharedPreferences = requireContext().getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)
        return sharedPreferences.getString("phone", null)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
