package pe.devpicon.android.marvelcomic.entities

/**
 * Created by Armando on 3/3/2017.
 */
data class Player (
        val name: String = "Armando",
        var age: Int = 0,
        var lifes: Int = 5){

}

fun main(args: Array<String>){
    var player1 = Player()
    player1.age = 36

    val player2 = player1.copy()
    player2.equals(player1)

    val playerList = arrayListOf(Player(), Player(lifes = 7), player1)
    val player = playerList.find { it.lifes == 7 }
    println(player)

}