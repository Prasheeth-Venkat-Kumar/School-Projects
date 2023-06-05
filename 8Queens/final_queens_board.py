import numpy as np

# Variables
# Array to store the neighbor states that are various versions of the queen board.
neighbor_states = []
# Variable to store the number of restart used by the program to complete the problem.
num_restarts = 0
# Variable to store the number of state changes of the queen board.
num_state_changes = 0


def calc_heuristic(board):
    """ Calculates the conflicts between the queens present in horizontal and diagonal ways."""
    # Variables to count conflicts
    horizontal_conflicts = 0
    diagonal_conflicts = 0
    # loop through the board to find the position of the queens.
    for row in range(8):
        for col in range(8):
            # Find the position of the queen.
            if board[row][col] == 1:
                # To make sure the queens aren't counted twice.
                horizontal_conflicts -= 2
                # Find the horizontal conflicts
                for i in range(8):
                    if board[row][i] == 1:
                        horizontal_conflicts += 1
                    if board[i][col] == 1:
                        horizontal_conflicts += 1
                # Find the diagonal conflicts
                i = row + 1
                new_col = col + 1
                while i < 8 and new_col < 8:
                    if board[i][new_col] == 1:
                        diagonal_conflicts += 1
                    i += 1
                    new_col += 1
                i = row + 1
                new_col = col - 1
                while i < 8 and new_col >= 0:
                    if board[i][new_col] == 1:
                        diagonal_conflicts += 1
                    i += 1
                    new_col -= 1
                i = row - 1
                new_col = col + 1
                while i >= 0 and new_col < 8:
                    if board[i][new_col] == 1:
                        diagonal_conflicts += 1
                    i -= 1
                    new_col += 1
                i = row - 1
                new_col = col - 1
                while i >= 0 and new_col >= 0:
                    if board[i][new_col] == 1:
                        diagonal_conflicts += 1
                    i -= 1
                    new_col -= 1
    return (diagonal_conflicts + horizontal_conflicts) // 2


def position_queen(row, col, board):
    """ Moves the queen down the rows and stores the board with the new queen positions to the variable
    neighbor_states """
    # Variables to store data.
    temp_board = np.copy(board)
    temp_row = row
    boards = []
    # loops through eight times to cover all the rows in present in the column.
    for _ in range(8):
        # if current row position is the last row, move it to the first row.
        if temp_row == 7:
            temp_board[temp_row][col] = 0
            temp_row = 0
            temp_board[temp_row][col] = 1
        else:
            temp_board[temp_row][col] = 0
            temp_row += 1
            temp_board[temp_row][col] = 1
        boards.append(np.copy(temp_board))
    return boards


def get_best_board(boards):
    """Finds and returns the board with the best heuristic value in the given parameter of boards"""
    # Sort the boards list parameter to find the lowest heuristic-valued board.
    boards.sort(key=calc_heuristic)
    return boards[0]


def add_neighbors(board_add):
    """ Add the boards present in the board_add parameter to the neighbor_states variables."""
    for x in board_add:
        neighbor_states.append(x)


def find_num_neighbors(old_board_h):
    """ Finds the number of neighbor_states that have a lower heuristic value"""
    # Variable to store the number of neighbors present that have a lower heuristic value.
    num_neighbors = 0
    for board in neighbor_states:
        if calc_heuristic(board) < old_board_h:
            num_neighbors += 1
    return num_neighbors


def print_board_info(board, old_board_h):
    """ Prints the board parameter info such as the board itself, the heuristic value and the number of neighbors
    states with a lower heuristic value """
    print(f'Current h: {old_board_h}')
    print('Current State')
    print(board)
    print(f'Neighbors found with lower h: {find_num_neighbors(old_board_h)}')


def solve_board(input_board):
    """ Solves the input_board by moving the queens to avoid the conflicts between the queens present."""
    # Variable to store if a minima has been found.
    minima_zero = False
    board = np.copy(input_board)
    # Variables to store number of restarts and state changes.
    global num_restarts
    global num_state_changes
    # Variables to store data regarding the heuristic values.
    new_boards = []
    old_board_h_value = 0
    new_board_h_value = 0
    # Loop until a minima of zero has been found.
    while not minima_zero:
        for col in range(8):
            for row in range(8):
                if board[row][col] == 1:
                    new_boards = position_queen(row, col, board)
                    add_neighbors(np.copy(new_boards))
        # Calculate the heuristic values of the current board and best neighbor board.
        old_board_h_value = calc_heuristic(board)
        print_board_info(board, old_board_h_value)
        new_board_h_value = calc_heuristic(get_best_board(neighbor_states))
        # If best neighbor board has a heuristic value of zero then a solution has been found.
        if new_board_h_value == 0:
            minima_zero = True
            board = np.copy(get_best_board(neighbor_states))
            neighbor_states.clear()
            print('Setting new current state')
            print('\n Current State')
            print(board)
            print('Solution Found!')
            print(f'State changes: {num_state_changes}')
            print(f'Restarts: {num_restarts}')
            exit()
        elif new_board_h_value < old_board_h_value:
            # if the best neighbor board has a better heuristic value than the current board. Set the current board to best neighbord board.
            board = np.copy(get_best_board(neighbor_states))
            neighbor_states.clear()
            num_state_changes += 1
            print('Setting new current state \n')
        else:
            # if the best neighbor board doesn't have a better heuristic value than the current board then a minima has been found that isn't zero.
            neighbor_states.clear()
            print('RESTART \n')
            num_restarts += 1
            # restart: make a new board with new queen positions and continue the solving process.
            board = np.copy(create_queen_board())


def create_queen_board():
    """Creates and returns a board that has a queen present in every column with a randomization in the placement of
    the row """
    # Create a 8x8 array to represent a 8x8 board.
    # Initialize the board with 0s
    queen_board = np.zeros((8, 8), dtype=int)

    # Place a queen in each collum in a random row.
    for y in range(8):
        rand_index = np.random.randint(0, 8)
        queen_board[rand_index][y] = 1
    return queen_board


# create a new queen board
new_queen_board = np.copy(create_queen_board())
# solve the queen board
solve_board(new_queen_board)
