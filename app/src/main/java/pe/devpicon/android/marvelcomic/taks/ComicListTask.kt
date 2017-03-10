package pe.devpicon.android.marvelcomic.taks

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import org.json.JSONArray
import org.json.JSONObject
import pe.devpicon.android.marvelcomic.R
import pe.devpicon.android.marvelcomic.entities.Characters
import pe.devpicon.android.marvelcomic.entities.Comic
import pe.devpicon.android.marvelcomic.entities.Creator
import pe.devpicon.android.marvelcomic.interfaces.Operations
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

/**
 * Created by Armando on 7/2/2017.
 */
class ComicListTask : AsyncTask<String, Void, String>() {

    var view: Operations.RequiredOps? = null
    var context: Context? = null

    override fun onPreExecute() {
        super.onPreExecute()

        view?.showProgressDialog()
    }

    override fun doInBackground(vararg p0: String?): String? {

        var comicJson: String? = null
        val url = URL(COMPLETE_URL)
        val conn: HttpURLConnection = url.openConnection() as HttpURLConnection
        conn.requestMethod = "GET"
        conn.connect()

        val inputStream: InputStream? = conn.inputStream
        val buffer = StringBuffer()

        if (inputStream != null) {
            val reader: BufferedReader = BufferedReader(InputStreamReader(inputStream))

            var line: String? = null;

            while ({ line = reader.readLine(); line }() != null) {
                Log.d(javaClass.simpleName, line)
                buffer.append(line + "\n")
            }

            reader.close()
            comicJson = buffer.toString()
            conn.disconnect()

        }

        return comicJson
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)

        if (result != null) {
            val comics: MutableList<Comic> = convertJsonToComicObject(result)
            Log.d(javaClass.simpleName, "List size: ${comics.size}")

//            DatabaseManager.getInstance(context!!).insertComicListIntoDatabase(comics)

            view?.showComics(comics)
            view?.dismissProgressDialog()

        }

    }

    private fun convertJsonToComicObject(comicJson: String): MutableList<Comic> {

        Log.d(javaClass.simpleName, "-> convertJsonToComicObject")

        val comicJson = JSONObject(comicJson)

        Log.d(javaClass.simpleName, "Return code: ${comicJson.getInt("code")}")

        val data = comicJson.getJSONObject("data")
        val results = data.getJSONArray("results")

        val comicList: MutableList<Comic> = mutableListOf()

        for (i in 0..(results.length() - 1)) {
            val item = results.getJSONObject(i)

            val comic = Comic(
                    id = item.getInt("id"),
                    title = item.getString("title"),
                    price = item.getJSONArray("prices").getJSONObject(0).getDouble("price"),
                    thumbnailUrl = "${item.getJSONObject("thumbnail").getString("path")}.${item.getJSONObject("thumbnail").getString("extension")}",
                    imageURL = getImageURL(item.getJSONArray("images")),
                    description = item.optString("description", ""),
                    onSaleDate = item.getJSONArray("dates").getJSONObject(0).getString("date"),
                    pageCount = item.getInt("pageCount"),
                    series = item.getJSONObject("series").getString("name"),
                    creators = getCreatorsList(item.getJSONObject("creators")),
                    characters = getCharacterList(item.getJSONObject("characters"))
            )

            comicList.add(comic)

        }

        Log.d(javaClass.simpleName, "List size ${comicList.size}")

        return comicList

    }

    companion object {
        private val PUBLIC_API_KEY = "a953888a4098cff50b054c9375839786"
        private val RANDOM_WORD = "armando"
        private val HASH = "7f3db60eab45a2c204d69b8aafdcfbc9"
        private val COMIC_BASE_URL = "https://gateway.marvel.com/v1/public/comics"
        private val COMPLETE_URL = "${COMIC_BASE_URL}?ts=${RANDOM_WORD}&apikey=${PUBLIC_API_KEY}&hash=${HASH}"
    }


    private fun getImageURL(jsonArray: JSONArray?): String? {
        if (jsonArray != null && jsonArray.length() > 0) {
            val imageJsonObject = jsonArray.getJSONObject(0)
            return generateImageURL(imageJsonObject.getString("path"),
                    imageJsonObject.getString("extension"))
        }
        return ""
    }

    private fun getCharacterText(characters: List<Characters>?): String {
        Log.d(javaClass.simpleName, "getCharacterText")
        var characterText: String = context!!.getString(R.string.message_not_available)
        if (characters != null && characters.size > 0) {
            for (i in 0..(characters.size - 1)) {
                val character = characters.get(i)
                characterText += "${character.name}\n"
            }
        }

        return characterText
    }

    // TODO: Provisorio
    private fun getCreatorsText(creators: List<Creator>?): String {
        Log.d(javaClass.simpleName, "getCreatorsText")
        var creatorsText: String = context!!.getString(R.string.message_not_available)
        if (creators != null && creators.size > 0) {
            for (i in 0..(creators.size - 1)) {
                val creator = creators.get(i)
                creatorsText += "${creator.name} (${creator.role}) \n"
            }
        }

        return creatorsText
    }

    private fun getCharacterList(characters: JSONObject): MutableList<Characters>? {
        Log.d(javaClass.simpleName, "getCharacterList")
        val characterList: MutableList<Characters> = mutableListOf()

        if (characters.optInt("available", 0) > 0) {
            val items = characters.getJSONArray("items")
            for (i in 0..(items.length() - 1)) {
                val item = items.getJSONObject(i)
                characterList.add(Characters(item.getString("name")))
            }
        }


        return characterList

    }


    private fun getCreatorsList(creators: JSONObject): MutableList<Creator> {
        Log.d(javaClass.simpleName, "getCreatorsList")
        val creatorList: MutableList<Creator> = mutableListOf()

        if (creators.optInt("available", 0) > 0) {
            val items = creators.getJSONArray("items")
            for (i in 0..(items.length() - 1)) {
                val item = items.getJSONObject(i)
                creatorList.add(Creator(item.getString("name"), item.getString("role")))
            }
        }

        return creatorList

    }


    fun generateImageURL(path: String, extension: String) = "$path.$extension"


}


fun main(args:Array<String>){
    var name: String? = null
    sayHello(name)
}


private fun sayHello(name: String?) {
    name?.let {
        println("Hola $it!")
    }
}
