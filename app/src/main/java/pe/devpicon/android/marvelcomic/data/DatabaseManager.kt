package pe.devpicon.android.marvelcomic.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import pe.devpicon.android.marvelcomic.entities.Comic
import java.sql.SQLException

/**
 * Created by Armando on 8/2/2017.
 */

class DatabaseManager private constructor(context: Context) {

    private val comicDbHelper: ComicDbHelper

    init {
        comicDbHelper = ComicDbHelper(context)
    }



    fun queryAllComics(sortOrder: String?): Cursor? {
        val database = comicDbHelper.readableDatabase
        return database.query(ComicContract.ComicEntry.TABLE_NAME, null, null, null, null, null, sortOrder ?: null)
    }

    fun queryComicById(id: Int): Cursor? {
        val database = comicDbHelper.readableDatabase
        return database.query(ComicContract.ComicEntry.TABLE_NAME, null, "${ComicContract.ComicEntry.COLUMN_NAME_COMIC_ID}=?", arrayOf(id.toString()), null, null, null)
    }


    fun insertComicIntoDatabase(comic: Comic) {
        insertComicListIntoDatabase( listOf(comic))
    }


    fun insertComicListIntoDatabase( list: List<Comic>) {

        val database = comicDbHelper.writableDatabase
        var comicValues: MutableList<ContentValues> = mutableListOf()

        for (i in 0..(list.size - 1)) {
            val comicItem = list.get(i)
            val comicValue = ContentValues()
            comicValue.put(ComicContract.ComicEntry.COLUMN_NAME_COMIC_ID, comicItem.id)
            comicValue.put(ComicContract.ComicEntry.COLUMN_NAME_TITLE, comicItem.title)
            comicValue.put(ComicContract.ComicEntry.COLUMN_NAME_DESCRIPTION, comicItem.description)
            comicValue.put(ComicContract.ComicEntry.COLUMN_NAME_PRICE, comicItem.price)
            comicValue.put(ComicContract.ComicEntry.COLUMN_NAME_IMAGE_URL, comicItem.imageURL)
            comicValue.put(ComicContract.ComicEntry.COLUMN_NAME_DATE, comicItem.onSaleDate)
            comicValue.put(ComicContract.ComicEntry.COLUMN_NAME_PAGE_COUNT, comicItem.pageCount)
            comicValue.put(ComicContract.ComicEntry.COLUMN_NAME_SERIES, comicItem.series)

            var creators: String = ""
            if (comicItem.creators != null && comicItem.creators.size > 0) {

                for (j in 0..(comicItem.creators.size - 1)) {
                    val creator = comicItem.creators.get(j)
                    creators += "${creator.name} (${creator.role})"

                    if (j < comicItem.creators.size - 1) {
                        creators += ","
                    }

                }
            } else {
                creators = "Not available"
            }

            comicValue.put(ComicContract.ComicEntry.COLUMN_NAME_CREATORS, creators)

            var characters: String = ""
            if (comicItem.characters != null && comicItem.characters.size > 0) {

                for (j in 0..(comicItem.characters.size - 1)) {
                    val character = comicItem.characters.get(j)
                    characters += "${character.name}"

                    if (j < comicItem.characters.size - 1) {
                        characters += ","
                    }

                }
            } else {
                characters = "Not available"


                comicValue.put(ComicContract.ComicEntry.COLUMN_NAME_CHARACTERS, characters)
            }

            comicValues.add(comicValue)

        }

        insertDataIntoDatabase(database, comicValues)

    }

    private fun insertDataIntoDatabase(db: SQLiteDatabase?, comicValues: MutableList<ContentValues>) {
        try {
            db?.beginTransaction()
            comicValues.forEach { db?.insert(ComicContract.ComicEntry.TABLE_NAME, null, it) }
            db?.setTransactionSuccessful()
        } catch (e: SQLException) {
            Log.e(javaClass.simpleName, "Ha ocurrido un error durante la inserción.", e )
        } finally {
            db?.endTransaction()
        }
    }

    fun deleteDataInDatabase(comicId: Int){

        val database = comicDbHelper.writableDatabase
        try {
            database.beginTransaction()
            database.delete(ComicContract.ComicEntry.TABLE_NAME, "${ComicContract.ComicEntry.COLUMN_NAME_COMIC_ID}=?", arrayOf(comicId.toString()))
            database.setTransactionSuccessful()
        }catch (e: SQLException){
            Log.e(javaClass.simpleName, "Ha ocurrido un error durante la eliminación.", e )
        } finally {
            database.endTransaction()
        }
    }



    companion object {
        private var instance: DatabaseManager? = null

        @Synchronized fun getInstance(context: Context): DatabaseManager {
            if (instance == null) {
                instance = DatabaseManager(context.applicationContext)
            }

            return instance as DatabaseManager
        }
    }
}