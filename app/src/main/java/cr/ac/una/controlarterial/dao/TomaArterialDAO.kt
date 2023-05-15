package cr.ac.una.jsoncrud.dao


import cr.ac.una.controlarterial.entity.TomaArterial
import cr.ac.una.controlarterial.entity.TomasArteriales
import retrofit2.http.*

interface TomaArterialDAO {

        @GET("tomaarterial")
        suspend fun getItems(): TomasArteriales

        @GET("tomaarterial/{uuid}")
        suspend fun getItem(@Path("uuid") uuid: String): TomaArterial

        @POST("tomaarterial")
        suspend fun createItem( @Body items: List<TomaArterial>): TomasArteriales

        @PUT("tomaarterial/{uuid}")
        suspend fun updateItem(@Path("uuid") uuid: String, @Body item: TomaArterial): TomasArteriales

        @DELETE("tomaarterial/{uuid}")
        suspend fun deleteItem(@Path("uuid") uuid: String)
}
