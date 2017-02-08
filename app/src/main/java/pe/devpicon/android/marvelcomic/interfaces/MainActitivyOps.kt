package pe.devpicon.android.marvelcomic.interfaces

import pe.devpicon.android.marvelcomic.entities.Comic

/**
 * Created by Armando on 7/2/2017.
 */
open interface Operations {
    open interface RequiredOps {
        fun showComics(comicList: List<Comic>)
    }
}