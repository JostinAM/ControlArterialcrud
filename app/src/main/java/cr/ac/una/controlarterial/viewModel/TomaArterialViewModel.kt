package cr.ac.una.controlarterial.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cr.ac.una.controlarterial.AuthInterceptor
import cr.ac.una.controlarterial.entity.TomaArterial
import cr.ac.una.jsoncrud.dao.TomaArterialDAO
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TomaArterialViewModel: ViewModel (){
    private var _tomasArteriales: MutableLiveData<List<TomaArterial>> = MutableLiveData()
    var tomasArteriales: LiveData<List<TomaArterial>> = _tomasArteriales
    lateinit var  apiService : TomaArterialDAO

    //CREATE DELETE METHOD

    suspend fun listTomaArterial() {
        intService()
        var lista = apiService.getItems()
        _tomasArteriales.postValue(lista.items)

    }

    suspend fun deleteTomaArterial(_uuid: String){
        intService()
        apiService.deleteItem(_uuid)
    }

    suspend fun postTomaArterial(tomaArterial: List<TomaArterial>){
        intService()
        apiService.createItem(tomaArterial)

    }

    fun intService(){
        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor("oHJU241-zUkUEOKwotIRXSRsOiCmbyHuRc9zQ9c3Pyb7aS_gPg"))
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://crudapi.co.uk/api/v1/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        apiService = retrofit.create(TomaArterialDAO::class.java)
    }
}