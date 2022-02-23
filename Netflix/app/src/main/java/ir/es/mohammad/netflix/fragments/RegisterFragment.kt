package ir.es.mohammad.netflix.fragments

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import ir.es.mohammad.netflix.R
import ir.es.mohammad.netflix.viewmodel.UserViewModel
import ir.es.mohammad.netflix.databinding.FragmentRegisterBinding
import java.io.ByteArrayOutputStream

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var takePictureActivityResultLauncher: ActivityResultLauncher<Void>
    private val userViewModel: UserViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)

        with(binding) {
            userViewModel.profileImgDrawable.observe(this@RegisterFragment) {
                if (it != null)
                    imgProfilePic.setImageDrawable(it)
            }

            btnAddPic.setOnClickListener { takePictureActivityResultLauncher.launch(null) }

            editTextFullName.setOnFocusChange(editTextEmail.text.toString())

            editTextEmail.setOnFocusChange(editTextFullName.text.toString())

            btnRegister.setOnClickListener {
                userViewModel.apply {
                    registered.value = true
                    fullName.value = editTextFullName.text.toString()
                    email.value = editTextEmail.text.toString()
                    username.value = editTextUsername.text?.toString() ?: ""
                    phoneNumber.value = editTextPhoneNumber.text?.toString() ?: ""
                    val bitmap = (imgProfilePic.drawable as BitmapDrawable).bitmap
                    val stream = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                    val image = stream.toByteArray()
                    uploadProfileImage(image)
                }
                navigateToShowInfo()
            }
        }
    }

    private fun navigateToShowInfo(){
        val navController = findNavController()
        val startDestination = navController.graph.startDestinationId
        val navOptions = NavOptions.Builder().setPopUpTo(startDestination, false).build()
        navController.navigate(R.id.showInfoFragment, null, navOptions)
    }

    private fun TextInputEditText.setOnFocusChange(otherText: String) {
        this.setOnFocusChangeListener { _, _ ->
            Log.d("TAGGG", otherText.isBlank().toString())
            if (this.text.toString().isBlank()) {
                this.error = "Full name can't be empty"
                binding.btnRegister.isEnabled = false
            } else binding.btnRegister.isEnabled = otherText.isBlank()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        createTakePictureIntentActivityResultLauncher()
    }

    private fun createTakePictureIntentActivityResultLauncher() {
        takePictureActivityResultLauncher =
            registerForActivityResult(ActivityResultContracts.TakePicturePreview()) {
                userViewModel.profileImgDrawable.value = it.toDrawable(resources)
            }
    }
}