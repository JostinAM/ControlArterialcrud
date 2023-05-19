package cr.ac.una.controlarterial.view

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cr.ac.una.controlarterial.R
import cr.ac.una.controlarterial.adapter.TomaArterialAdapter
import cr.ac.una.controlarterial.databinding.FragmentListaTomaArterialBinding
import cr.ac.una.controlarterial.entity.TomaArterial
import cr.ac.una.controlarterial.viewModel.TomaArterialViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ListaTomaArterialFragment : Fragment() {

    private var _binding: FragmentListaTomaArterialBinding? = null
    private val binding get() = _binding!!
    private lateinit var tomaArterialViewModel : TomaArterialViewModel
    private lateinit var tomasArteriales :List<TomaArterial>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentListaTomaArterialBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //binding.buttonFirst.setOnClickListener {
         //   findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        //}

        //Se inicializa el Adapter con una lista vacía
        val listView = view.findViewById<RecyclerView>(R.id.list_view)
        tomasArteriales = mutableListOf<TomaArterial>()
        var adapter =  TomaArterialAdapter(tomasArteriales as ArrayList<TomaArterial>)
        listView.adapter = adapter
        listView.layoutManager = LinearLayoutManager(requireContext())

        //Se atacha al viewModel de la activiad principal
        tomaArterialViewModel = ViewModelProvider(requireActivity()).get(TomaArterialViewModel::class.java)

        //cada vez que el observador nota reporta un cambio en los datos se actualiza la lista de elementos
        tomaArterialViewModel.tomasArteriales.observe(viewLifecycleOwner) { elementos ->

            adapter.updateData(elementos as ArrayList<TomaArterial>)
            tomasArteriales = elementos

        }
        // Se llama el código del ViewModel que cargan los datos
        GlobalScope.launch(Dispatchers.Main) {
            tomaArterialViewModel.listTomaArterial()!!
        }

        // Crea el ItemTouchHelper
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                if (position!=0) {
                    // Elimina el elemento cuando se detecta el deslizamiento hacia la derecha
                    //! GET UUID AND DELETE
                    val ins = tomasArteriales[position]
                    Log.d("ins", ins._uuid.toString())

                    (tomasArteriales as MutableList<TomaArterial>).removeAt(position)
                    adapter.updateData(tomasArteriales as ArrayList<TomaArterial>)
                    deleteToma(ins._uuid)


                }
            }

            // Sobrescribe el método para dibujar la etiqueta al deslizar
            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                if (viewHolder is TomaArterialAdapter.ViewHolder) {
                    super.onChildDraw(
                        c,
                        recyclerView,
                        viewHolder,
                        dX,
                        dY,
                        actionState,
                        isCurrentlyActive
                    )
                    if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                        val itemView = viewHolder.itemView
                        val paint = Paint()
                        paint.color = Color.RED
                        val deleteIcon = ContextCompat.getDrawable(
                            requireContext(),
                            android.R.drawable.ic_menu_delete
                        )
                        val iconMargin = (itemView.height - deleteIcon!!.intrinsicHeight) / 2
                        val iconTop =
                            itemView.top + (itemView.height - deleteIcon.intrinsicHeight) / 2
                        val iconBottom = iconTop + deleteIcon.intrinsicHeight

                        // Dibuja el fondo rojo
                        c.drawRect(
                            itemView.left.toFloat(),
                            itemView.top.toFloat(),
                            itemView.right.toFloat(),
                            itemView.bottom.toFloat(),
                            paint
                        )

                        // Calcula las posiciones del icono de eliminar
                        val iconLeft = itemView.right - iconMargin - deleteIcon.intrinsicWidth
                        val iconRight = itemView.right - iconMargin
                        deleteIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom)

                        // Dibuja el icono de eliminar
                        deleteIcon.draw(c)
                    }
                }
            }
        })

        // Adjunta el ItemTouchHelper al RecyclerView
        itemTouchHelper.attachToRecyclerView(listView)


    }

    private fun deleteToma(_uuid: String?) {
        GlobalScope.launch(Dispatchers.Main) {
            if (_uuid != null) {
                tomaArterialViewModel.deleteTomaArterial(_uuid)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}