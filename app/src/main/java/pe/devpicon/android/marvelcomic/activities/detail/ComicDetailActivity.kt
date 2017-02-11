package pe.devpicon.android.marvelcomic.activities.detail

import android.app.ProgressDialog
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_comic_detail.*
import org.jetbrains.anko.activityUiThreadWithContext
import org.jetbrains.anko.doAsync
import org.json.JSONArray
import org.json.JSONObject
import pe.devpicon.android.marvelcomic.R
import pe.devpicon.android.marvelcomic.activities.BaseActivity
import pe.devpicon.android.marvelcomic.data.DatabaseManager
import pe.devpicon.android.marvelcomic.entities.Characters
import pe.devpicon.android.marvelcomic.entities.Comic
import pe.devpicon.android.marvelcomic.entities.Creator
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat

class ComicDetailActivity : BaseActivity(), View.OnTouchListener {
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {

        if(event?.action == MotionEvent.ACTION_DOWN){
            when(v?.id){
                R.id.btn_favourite -> {
                    if (favouriteComic) {
                        if (comic != null) DatabaseManager.getInstance(this).deleteDataInDatabase(comic!!.id)
                        unmarkFavourite()
                        return false
                    }else{
                        if (comic != null) DatabaseManager.getInstance(this).insertComicIntoDatabase(comic!!)
                        markFavouriteIcon()
                        return false
                    }
                }
            }
        }
        return false
    }

    var favouriteComic : Boolean = false


    private fun unmarkFavourite() {
        Log.d(TAG, "unmarkfavourite")
        val drawable = getDrawable(R.drawable.star_outline)
        drawable.setTint(ContextCompat.getColor(this, R.color.white))
        btn_favourite.setImageDrawable(drawable)
        favouriteComic = false
    }

    private fun markFavouriteIcon() {
        Log.d(TAG, "markfavourite")
        val drawable = getDrawable(R.drawable.star)
        drawable.setTint(ContextCompat.getColor(this, R.color.yellow))
        btn_favourite.setImageDrawable(drawable)
        favouriteComic = true
    }

    val TAG: String = javaClass.simpleName
    private var comic: Comic? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comic_detail)

        setTitle("")

        btn_favourite.setOnTouchListener(this)

        val comicId = intent.getIntExtra("comicId", 0)

        Log.d(TAG, comic.toString())

        val cursor = DatabaseManager.getInstance(this).queryComicById(comicId)
        if(cursor != null && cursor!!.count > 0) markFavouriteIcon()


        if (comicId != 0) {
            showProgressDialog()
            doAsync {

                var comicJsonStr: String? = null
                val url = URL(generateURL(comicId))
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
                    comicJsonStr = buffer.toString()
                    conn.disconnect()

                    val comicJson = JSONObject(comicJsonStr)
                    val data = comicJson.getJSONObject("data")
                    val results = data.getJSONArray("results")

                    val item = if (results.length() > 0) results.getJSONObject(0) else null;



                    if (item != null) {
                        Log.d(javaClass.simpleName, "se obtendrán los datos desde el objeto JSON")


                        with(item) {
                            comic = Comic(
                                    id = getInt("id"),
                                    title = getString("title"),
                                    price = getJSONArray("prices").getJSONObject(0).getDouble("price"),
                                    imageURL = getImageURL(getJSONArray("images")),
                                    description = optString("description", ""),
                                    onSaleDate = getJSONArray("dates").getJSONObject(0).getString("date"),
                                    pageCount = getInt("pageCount"),
                                    series = getJSONObject("series").getString("name"),
                                    creators = getCreatorsList(getJSONObject("creators")),
                                    characters = getCharacterList(getJSONObject("characters"))


                            )
                        }

                        Log.d(javaClass.simpleName, "se obtuvieron los datos desde el objeto JSON")


                        activityUiThreadWithContext {
                            dismissProgressDialog()
                            if (comic != null) {

                                Log.d(javaClass.simpleName, comic.toString())

                                with(comic!!) {
                                    Glide.with(it)
                                            .load(imageURL)
                                            .fitCenter()
                                            .into(img_comic_detail_cover)
                                    txt_comic_detail_name.text = title
                                    txt_comic_detail_price.text = if (price > 0.0) getString(R.string.format_usd, price.toString()) else getString(R.string.message_soldout)
                                    txt_comic_detail_description.text = if (!description.equals("null", true)) description else getString(R.string.message_not_available)
                                    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                                    val parse = simpleDateFormat.parse(onSaleDate)
                                    val outputFormat = SimpleDateFormat("dd/MM/yyyy")
                                    txt_comic_detail_onsale_date.text = outputFormat.format(parse).toString()
                                    txt_comic_detail_pagecount.text = pageCount.toString()
                                    txt_comic_detail_series.text = series
                                    txt_comic_detail_creators.text = getCreatorsText(creators)
                                    txt_comic_detail_characters.text = getCharacterText(characters)

                                }


                            } else {
                                Log.d(javaClass.simpleName, "el objeto comic llegó nulo")
                            }

                        }

                    } else{
                        Log.d(javaClass.simpleName, "el item llegó nulo")
                    }


                }


            }
        }

    }

    private fun getImageURL(jsonArray: JSONArray?): String? {
        if(jsonArray != null && jsonArray.length() > 0 ) {
            val imageJsonObject = jsonArray.getJSONObject(0)
            return generateImageURL(imageJsonObject.getString("path"),
                    imageJsonObject.getString("extension"))
        }
        return ""
    }

    private fun getCharacterText(characters: List<Characters>?): String {
        Log.d(javaClass.simpleName, "getCharacterText")
        var characterText:String = getString(R.string.message_not_available)
        if(characters != null&& characters.size > 0){
            for(i in 0..(characters.size - 1) ){
                val character = characters.get(i)
                characterText+= "${character.name}\n"
            }
        }

        return characterText
    }

    // TODO: Provisorio
    private fun getCreatorsText(creators: List<Creator>?): String {
        Log.d(javaClass.simpleName, "getCreatorsText")
        var creatorsText:String = getString(R.string.message_not_available)
        if(creators != null && creators.size > 0){
            for(i in 0..(creators.size - 1) ){
                val creator = creators.get(i)
                creatorsText+= "${creator.name} (${creator.role}) \n"
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

    fun generateURL(comicId: Int) = "${COMIC_BASE_URL}/$comicId?ts=${RANDOM_WORD}&apikey=${PUBLIC_API_KEY}&hash=${HASH}"

    companion object {
        private val PUBLIC_API_KEY = "a953888a4098cff50b054c9375839786"
        private val RANDOM_WORD = "armando"
        private val HASH = "7f3db60eab45a2c204d69b8aafdcfbc9"
        private val COMIC_BASE_URL = "https://gateway.marvel.com/v1/public/comics"
//        private val COMPLETE_URL = "${COMIC_BASE_URL}/$comicId?ts=${RANDOM_WORD}&apikey=${PUBLIC_API_KEY}&hash=${HASH}"
    }

}
