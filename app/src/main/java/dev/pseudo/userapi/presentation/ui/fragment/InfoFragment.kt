package dev.pseudo.userapi.presentation.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import dev.pseudo.userapi.databinding.FragmentInfoBinding

@AndroidEntryPoint
class InfoFragment : Fragment() {

    private lateinit var binding: FragmentInfoBinding
    private val args: InfoFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val user = args.user

        binding.nameTextView.text = "${user.name.first} ${user.name.last}"
        binding.emailTextView.text = user.email
        binding.phoneTextView.text = user.phone
        binding.addressTextView.text =
            "${user.location.street.name} ${user.location.street.number}, ${user.location.city}, ${user.location.country}"

        Glide.with(binding.imageView)
            .load(user.picture.large)
            .circleCrop()
            .into(binding.imageView)

        binding.emailTextView.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:${user.email}")
            }
            startActivity(intent)
        }

        binding.phoneTextView.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:${user.phone}")
            }
            startActivity(intent)
        }

        binding.addressTextView.setOnClickListener {
            val geoUri =
                Uri.parse("geo:${user.location.coordinates.latitude},${user.location.coordinates.longitude}")
            val intent = Intent(Intent.ACTION_VIEW, geoUri)
            startActivity(intent)
        }
    }
}