package cr.ac.una.controlarterial.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import cr.ac.una.controlarterial.R
import cr.ac.una.controlarterial.databinding.FragmentSecondBinding
import cr.ac.una.controlarterial.entity.TomaArterial
import cr.ac.una.controlarterial.viewModel.TomaArterialViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var tomaArterialViewModel : TomaArterialViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tomaArterialViewModel = ViewModelProvider(requireActivity()).get(TomaArterialViewModel::class.java)

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        binding.button.setOnClickListener{
            val distolica = binding.editTextNumberDecimal.text.toString().toInt()
            val sistolica = binding.editTextNumberDecimal2.text.toString().toInt()
            val ritmo = binding.editTextNumberDecimal3.text.toString().toInt()


            var item = TomaArterial(
                _uuid = null,
                distolica = distolica,
                sistolica = sistolica,
                ritmo = ritmo
            )
            println(item)

            var items = ArrayList<TomaArterial>()

            items.add(item)

            GlobalScope.launch(Dispatchers.Main) {
                tomaArterialViewModel.postTomaArterial(items)
            }

            //show toast
            Toast.makeText(context, "Toma guardada", Toast.LENGTH_SHORT).show()

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}