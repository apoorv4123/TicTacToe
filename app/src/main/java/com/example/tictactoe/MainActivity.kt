package com.example.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var PLAYER = true// This will tell us the value of current player
    // If true then player 1, else player 2

    private var TURN_COUNT = 0// If turn count becomes 9, then match is draw.
    // If before that, then one player is moving forth(winning)

    private val boardStatus = Array(3) { IntArray(3) }// maintain this array to avoid traversing board array

    // 2D array to store buttons
    private lateinit var board: Array<Array<Button>>

    private var playerXName: String = "X"
    private var playerOName: String = "O"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        board = arrayOf(
            arrayOf(btnMove1, btnMove2, btnMove3),
            arrayOf(btnMove4, btnMove5, btnMove6),
            arrayOf(btnMove7, btnMove8, btnMove9)
        )

        for (i in board) {
            for (button in i) {
                button.setOnClickListener(this)// all the clicks would be invoked in onClick() function
            }
        }

        btnStart.setOnClickListener {
            btnStart.isVisible = false

            playerXName = if (etOne.text.toString() != "") (etOne.text.toString()) else playerXName
            playerOName = if (etTwo.text.toString() != "") (etTwo.text.toString()) else playerOName
            updateDisplay("Player X Turn")
        }

        initializeBoardStatus()

        btnReset.setOnClickListener {
            PLAYER = true
            TURN_COUNT = 0
            initializeBoardStatus()
            btnStart.isVisible = true
            updateDisplay("New Game")
        }

    }

    private fun initializeBoardStatus() {
        for (i in 0..2) {
            for (j in 0..2) {
                boardStatus[i][j] = -1
                // Enable the buttons& make their text empty
            }
        }

        for (i in board) {
            for (button in i) {
                button.isEnabled = true
                button.text = ""
            }
        }
        updateDisplay("New Game")
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btnMove1 -> {
                updateValue(row = 0, col = 0, player = PLAYER)
            }
            R.id.btnMove2 -> {
                updateValue(row = 0, col = 1, player = PLAYER)
            }
            R.id.btnMove3 -> {
                updateValue(row = 0, col = 2, player = PLAYER)
            }
            R.id.btnMove4 -> {
                updateValue(row = 1, col = 0, player = PLAYER)
            }
            R.id.btnMove5 -> {
                updateValue(row = 1, col = 1, player = PLAYER)
            }
            R.id.btnMove6 -> {
                updateValue(row = 1, col = 2, player = PLAYER)
            }
            R.id.btnMove7 -> {
                updateValue(row = 2, col = 0, player = PLAYER)
            }
            R.id.btnMove8 -> {
                updateValue(row = 2, col = 1, player = PLAYER)
            }
            R.id.btnMove9 -> {
                updateValue(row = 2, col = 2, player = PLAYER)
            }
        }

        // To update Display
        if (PLAYER) {
            updateDisplay("Player X Turn")
        } else {
            updateDisplay("Player O Turn")
        }

        if (TURN_COUNT == 9) {
            updateDisplay("Game Draw!!")
        }

        checkWinner()

    }

    private fun checkWinner() {
        // Check rows
        for (i in 0..2) {
            if (boardStatus[i][0] == boardStatus[i][1] && boardStatus[i][0] == boardStatus[i][2]) {
                if (boardStatus[i][0] == 1) {
                    updateDisplay("$playerXName Won!!")
                    break
                } else if (boardStatus[i][0] == 0) {
                    updateDisplay("$playerOName Won!!")
                    break
                }
            }
        }

        // Check columns
        for (i in 0..2) {
            if (boardStatus[0][i] == boardStatus[1][i] && boardStatus[0][i] == boardStatus[2][i]) {
                if (boardStatus[0][i] == 1) {
                    updateDisplay("$playerXName Won!!")
                    break
                } else if (boardStatus[0][i] == 0) {
                    updateDisplay("$playerOName Won!!")
                    break
                }
            }
        }

        // Check diagonal 1
        if (boardStatus[0][0] == boardStatus[1][1] && boardStatus[0][0] == boardStatus[2][2]) {
            if (boardStatus[0][0] == 1) {
                updateDisplay("$playerXName Won!!")
            } else if (boardStatus[0][0] == 0) {
                updateDisplay("${playerOName} Won!!")
            }
        }

        // Check diagonal 2
        if (boardStatus[0][2] == boardStatus[1][1] && boardStatus[0][2] == boardStatus[2][0]) {
            if (boardStatus[0][2] == 1) {
                updateDisplay("$playerXName Won!!")
            } else if (boardStatus[0][2] == 0) {
                updateDisplay("$playerOName Won!!")
            }
        }
    }

    private fun updateDisplay(message: String) {
        tvDisplay.text = message
        if (message.contains("Won")) {// Check if message contains "won" in it
            disableButtonsAfterWinning()
        }
    }

    private fun disableButtonsAfterWinning() {
        for (i in board) {
            for (button in i) {
                button.isEnabled = false
            }
        }
    }

    private fun updateValue(row: Int, col: Int, player: Boolean) {
        board[row][col].apply {
            isEnabled = false // once a button has been clicked, it cannot be clicked again
            text = if (player) "X" else "O"
        }
        boardStatus[row][col] = if (player) 1 else 0
        PLAYER = !PLAYER
        TURN_COUNT++
    }

}