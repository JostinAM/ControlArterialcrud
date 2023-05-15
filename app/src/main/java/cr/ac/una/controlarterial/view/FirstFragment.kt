package cr.ac.una.controlarterial.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import cr.ac.una.controlarterial.AuthInterceptor
import cr.ac.una.controlarterial.R
import cr.ac.una.controlarterial.adapter.TomaArterialAdapter
import cr.ac.una.controlarterial.databinding.FragmentFirstBinding
import cr.ac.una.controlarterial.entity.TomaArterial
import cr.ac.una.controlarterial.viewModel.TomaArterialViewModel
import cr.ac.una.jsoncrud.dao.TomaArterialDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private lateinit var tomaArterialViewModel : TomaArterialViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        //Se inicializa el Adapter con una lista vacía
        val listView = view.findViewById<ListView>(R.id.list_view)
        var adapter =  TomaArterialAdapter(requireContext(), mutableListOf<TomaArterial>())
        listView.adapter = adapter

        //Se atacha al viewModel de la activiad principal
        tomaArterialViewModel = ViewModelProvider(requireActivity()).get(TomaArterialViewModel::class.java)

        //cada vez que el observador nota reporta un cambio en los datos se actualiza la lista de elementos
        tomaArterialViewModel.tomasArteriales.observe(viewLifecycleOwner) { elementos ->
            adapter.clear()
            adapter.addAll(elementos)
            adapter.notifyDataSetChanged()
        }

        // Se llama el código del ViewModel que cargan los datos
        GlobalScope.launch(Dispatchers.Main) {
            tomaArterialViewModel.listTomaArterial()!!
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}