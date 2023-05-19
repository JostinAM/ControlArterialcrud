package cr.ac.una.jsoncrud.dao


import cr.ac.una.controlarterial.entity.TomaArterial
import cr.ac.una.controlarterial.entity.TomasArteriales
import retrofit2.http.*

interface TomaArterialDAO {

        @GET("tomasarteriales")
        suspend fun getItems(): TomasArteriales

        @GET("tomasarteriales/{uuid}")
        suspend fun getItem(@Path("uuid") uuid: String): TomaArterial

        @POST("tomasarteriales")
        suspend fun createItem( @Body items: List<TomaArterial>): TomasArteriales

        @PUT("tomasarteriales/{uuid}")
        suspend fun updateItem(@Path("uuid") uuid: String, @Body item: TomaArterial): TomasArteriales

        @DELETE("tomasarteriales/{uuid}")
        suspend fun deleteItem(@Path("uuid") uuid: String)
}
