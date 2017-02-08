package pe.devpicon.android.marvelcomic.entities

/**
 * Created by apico on 7/02/2017.
 */
data class Comic(val id: Int, val name: String, val price: Double, val imageURL: String?,
                 val description: String? = null, val onSaleDate: String? = null, val pageCount: Int? = 0,
                 val series: String? = null,
                 val creators: List<Creator>? = null, val characters: List<Characters>? = null)

data class Creator(val name: String, val role: String)

data class Characters(val name: String)