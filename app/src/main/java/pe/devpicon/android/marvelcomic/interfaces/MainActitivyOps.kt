package pe.devpicon.android.marvelcomic.interfaces

import pe.devpicon.android.marvelcomic.Comic

/**
 * Created by Armando on 7/2/2017.
 */
open interface Operations {
    open interface RequiredOps {
        fun showComics(comicList: List<Comic>)
    }
}