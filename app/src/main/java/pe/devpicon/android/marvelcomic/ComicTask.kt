package pe.devpicon.android.marvelcomic

import android.os.AsyncTask
import android.util.Log
import org.json.JSONObject
import pe.devpicon.android.marvelcomic.interfaces.Operations
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

/**
 * Created by Armando on 7/2/2017.
 */
class ComicTask : AsyncTask<String, Void, String>() {

    var ops : Operations.RequiredOps? = null

    override fun doInBackground(vararg p0: String?): String? {

        var comicJson : String? = null

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

        if( result != null){

            val comics : MutableList<Comic> = convertJsonToComicObject(result)

            Log.d(javaClass.simpleName, "List size: ${comics.size}")

            ops?.showComics(comics)

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

            val comic = Comic(item.getString("title"),
                    item.getJSONArray("prices").getJSONObject(0).getDouble("price"),
                    item.getJSONObject("thumbnail").getString("path")
                            + item.getJSONObject("thumbnail").getString("extension"))

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
        private val COMPLETE_URL = "$COMIC_BASE_URL?ts=$RANDOM_WORD&apikey=$PUBLIC_API_KEY&hash=$HASH"
    }

}

