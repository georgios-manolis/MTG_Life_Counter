package com.example.mtglifecounter

import Player
import android.os.Bundle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.foundation.clickable
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.sp
import com.example.mtglifecounter.ui.theme.MTGLifeCounterTheme

// Eine Activity ist quasi der Bildschirm der App. ": ComponentActivity()" bedeutet: MainActivity erbt von ComponentActivity (s. imports).
class MainActivity : ComponentActivity() {
    /*onCreate ist eine "Lifecycle"-Methode die aufgerufen wird, wenn der Bildschirm erstellt wird.
    savedInstanceState enthält ggf. gespeicherte Daten, wenn der Bildschirm z.B. nach einer Drehung wiederhergestellt wird.
    super.onCreate(...) ruft die Version von onCreate in der Elternklasse auf.
    */
    private val player1 = Player(); // Objekt: Spieler 1 erstellen mit Eigenschaften der Java-Klasse "Player"
    private val player2 = Player(); // Objekt: Spieler 2 erstellen mit Eigenschaften der Java-Klasse "Player"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       //enableEdgeToEdge() sorgt dafür, dass die App bis in die Systembereiche (Statusleiste oben / Navigation unten) zeichnen darf
        enableEdgeToEdge()
        //setContent { ... } ist Jetpack Compose.
        setContent {
            // MTGLifeCounterTheme ist Schriftart, Farben, Abstände, etc.
            MTGLifeCounterTheme {
                /* Modifier fillMaxSize macht, dass UI den ganzen Bildschirm einnimmt. InnerPadding ist im import Scaffold
                und sorgt dafür, dass Eingaben oder Inhalte nicht unter einer Ober- oder Untergrenze rutschen*/
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
                        Life(player= player1, Modifier.weight(1f))
                        Life(player= player2, Modifier.weight(1f))
                    }
                }
            }
        }
    }
}
@Composable
fun Life(player:Player, modifier: Modifier = Modifier) {

    var lifePoints by remember { mutableStateOf(player.getALife()) } //"muableStateof" und das "by remember" nötig, weil Kotlin keine Veränderungen in Variablem bemerkt
    var playerName by remember { mutableStateOf(player.getName()) }
    var editPlayerName by remember { mutableStateOf(false) }

    //hier wird alles zentriert
    Column (modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Wenn name verändert werden soll, Ausgabe textfeld. Hier wird das Eingabefeld ausgegeben und Werte in name von Java class ein- und ausgelagert
                if (editPlayerName) {
                    OutlinedTextField(value = playerName, onValueChange = {playerName = it}) //UI benötigt onValueChange Fkt, um playerName = newName (aus Java class) zu setzen
                    Button(onClick = {
                        player.setName(playerName)
                        editPlayerName = false
                        })
                    {
                    Text("OK")
                    }
                }
                else {
                    //Anzeige des Default Namen (Veränderbar mit Anklicken)
                    Text(text = playerName, fontSize = 24.sp, modifier = Modifier.clickable{editPlayerName = true})
                }
            }
        /*Mit Boxen erstellt man Bildschirmbereiche, die etwas machen sollen.
        In dem Fall soll Objekt player1 4 bereiche haben (Spielername, +1 Button. Lifepoint-Anzeige, -1 Button)*/

        // +1 Box "Button"
        Box(modifier = Modifier.weight(1f).fillMaxWidth().clickable{
            player.increase()                    // Java erhöht
            lifePoints = player.getALife()
        })

        // Anzeige der aktuellen Lifepoints
        Text(text = lifePoints.toString(), fontSize = 32.sp)

        // -1 bBox "Button"
        Box(modifier = Modifier.weight(1f).fillMaxWidth().clickable{
            player.decrease()              // Java verringert
            lifePoints = player.getALife()       // UI aktualisieren
        })
    }
}
