public class TicTacToeGame {
    private CellValue[] board;
    private int level;
    private GameState gameState;
    private int lines;
    private int columns;
    private int sizeWin;


    public TicTacToeGame() {
        board = new CellValue[3 * 3];
        for (int a = 0; a < board.length; a++) {
            board[a] = CellValue.EMPTY;
        }
        level = 0;
        gameState = GameState.PLAYING;
        lines = 3;
        columns = 3;
        sizeWin = 3;
    }


    public TicTacToeGame(int lines, int columns) {
        board = new CellValue[lines * columns];
        for (int a = 0; a < board.length; a++) {
            board[a] = CellValue.EMPTY;
        }
        level = 0;
        gameState = GameState.PLAYING;
        this.lines = lines;
        this.columns = columns;
        sizeWin = 3;
    }

    public TicTacToeGame(int lines, int columns, int sizeWin) {
        board = new CellValue[lines * columns];
        for (int a = 0; a < board.length; a++) {
            board[a] = CellValue.EMPTY;
        }
        level = 0;
        gameState = GameState.PLAYING;
        this.lines = lines;
        this.columns = columns;
        this.sizeWin = sizeWin;
    }

    public int getLines() {
        return lines;
    }

    public int getColumns() {
        return columns;
    }

    public int getLevel() {
        return level;
    }

    public int getSizeWin() {
        return sizeWin;
    }

    public GameState getGameState() {
        return gameState;
    }

    public CellValue nextCellValue() {
        if (level % 2 == 0) {
            CellValue val = CellValue.X;
            return val;
        } else {
            CellValue val = CellValue.O;
            return val;
        }
    }

    public CellValue valueAt(int i) {
        if (i < board.length) {
            return board[i];
        } else {
            System.out.println("Error cell does not exist");
        }
        return CellValue.EMPTY;
    }

    public void play(int i) {
        if (i >= board.length || i <= -1) {
            System.out.println("Error cell does not exist");
        } else if (i < board.length && board[i] == CellValue.EMPTY && gameState == GameState.PLAYING) {
            if (nextCellValue() == CellValue.X) {
                board[i] = CellValue.X;
                setGameState(i);
                level++;
            } else if (nextCellValue() == CellValue.O) {
                board[i] = CellValue.O;
                setGameState(i);
                level++;
            }
        } else if (board[i] != CellValue.EMPTY) {
            System.out.println("Error cell is already occupied");
        } else if (gameState == GameState.XWIN || gameState == GameState.OWIN) {
            if (nextCellValue() == CellValue.X) {
                board[i] = CellValue.X;
                System.out.println("Player X already won");
                level++;
            } else if (nextCellValue() == CellValue.O) {
                board[i] = CellValue.O;
                System.out.println("Player O already won");
                level++;
            }
        } else if (gameState == GameState.DRAW) {
            System.out.println("Draw");
        }
    }

    private void setGameState(int i) {
        int columnIndex = (i) % columns;
        int rowIndex = (i) / columns * columns;
        int[] diagonalLeftIndex = {i / columns, i % columns};
        int[] diagonalRightIndex = {i / columns, i % columns};

        diagonalLeftIndex = findOrigin(diagonalLeftIndex, 'l');
        diagonalRightIndex = findOrigin(diagonalRightIndex, 'r');
        int[] columnElement = {0, columnIndex};
        int[] rowElement = {i / columns, 0};

        CellValue[] vertical = new CellValue[lines];
        CellValue[] leftDiagonal = new CellValue[lines];
        CellValue[] rightDiagonal = new CellValue[lines];
        CellValue[] horizontal = new CellValue[columns];

        int indexCount = 0;
        int row = columnElement[0];
        int col = columnElement[1];
        while (row < lines) {
            vertical[indexCount] = board[row * columns + col];
            indexCount++;
            row++;
        }

        indexCount = 0;
        row = rowElement[0];
        col = rowElement[1];
        while (col < columns) {
            horizontal[indexCount] = board[row * columns + col];
            indexCount++;
            col++;
        }

        indexCount = 0;
        row = diagonalLeftIndex[0];
        col = diagonalLeftIndex[1];
        while (row < lines && col < columns) {
            leftDiagonal[indexCount] = board[row * columns + col];
            row++;
            col++;
            indexCount++;
        }

        indexCount = 0;
        row = diagonalRightIndex[0];
        col = diagonalRightIndex[1];
        while (row < lines && 0 <= columns) {
            rightDiagonal[indexCount] = board[row * columns + col];
            row++;
            col--;
            indexCount++;
        }
        CellValue[][] toCheck = {vertical, horizontal, leftDiagonal, rightDiagonal};

        for (int a = 0; a < toCheck.length && gameState == GameState.PLAYING; a++) {
            checkWin(toCheck[a]);
        }

        if (gameState == GameState.PLAYING) {
            boolean drawn = true;
            int index = 0;
            while (drawn && index < board.length) {
                if (board[index] == CellValue.EMPTY) {
                    drawn = false;
                }
                index++;
            }
            if (drawn) {
                gameState = GameState.DRAW;
            }
        }
    }

    public String toString() {
        String message, intermediate = "", boardgraphic = "", row = "";
        char[] array = new char[columns * lines];

        for (int i = 0; i < (4 * columns - 1); i++) {
            intermediate += "-";
        }


        if (gameState == GameState.XWIN) {
            message = "Result: X Wins";
        } else if (gameState == GameState.OWIN) {
            message = "Result: O Wins";
        } else if (level % 2 == 1) {
            message = "O to play: ";
        } else {
            message = "X to play: ";
        }

        for (int i = 0; i < columns * lines; i++) {
            if (board[i] == CellValue.X) {
                array[i] = 'X';
            } else if (board[i] == CellValue.O) {
                array[i] = 'O';
            } else {
                array[i] = ' ';
            }
        }

        for (int x = 0; x < lines; x++) {
            row = " " + array[x * columns];
            for (int y = 1; y < columns; y++) {
                row += " | " + array[x * columns + y];
            }
            if (x != lines - 1) {
                boardgraphic += row + "\n" + intermediate + "\n";
            } else {
                boardgraphic += row + "\n";
            }
        }
        return boardgraphic + "\n" + message;
    }

    private void checkWin(CellValue[] in) {
        int winCount = 0;
        for (int i = 0; i < in.length - 1; i++) {
            if (in[i] == in[i + 1] && in[i] != CellValue.EMPTY && in[i] != null) {
                winCount++;
            } else {
                winCount = 0;
            }

            if (winCount == sizeWin - 1) {
                if (in[i] == CellValue.X) {
                    gameState = GameState.XWIN;
                } else {
                    gameState = GameState.OWIN;
                }
            }
        }
    }

    private int[] findOrigin(int[] rc, char lr) {
        if (lr == 'l') {
            int[] lrc = rc;
            while (true) {
                if ((lrc[0] == 0 && lrc[1] == columns - 1) || (lrc[0] == lines - 1 && lrc[1] == 0)) {
                    lrc[0] = 0;
                    lrc[1] = 0;
                    return lrc;
                } else if (lrc[0] == 0 || lrc[1] == 0) {
                    return lrc;
                } else {
                    lrc[0] = rc[0] - 1;
                    lrc[1] = rc[1] - 1;
                }
            }
        }
        if (lr == 'r') {
            int[] rrc = rc;
            while (true) {
                if ((rrc[0] == 0 && rrc[1] == 0) || (rrc[0] == lines - 1 && rrc[1] == columns - 1)) {
                    rrc[0] = 0;
                    rrc[1] = columns - 1;
                    return rrc;
                } else if (rrc[0] == 0 || rrc[1] == columns - 1) {
                    return rrc;
                } else {
                    rrc[0] = rrc[0] - 1;
                    rrc[1] = rrc[1] + 1;
                }
            }
        }
        return rc;
    }
}
