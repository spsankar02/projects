def searchMatrixBruteForce(matrix,target):
    n = len(matrix)
    m = len(matrix[0])

    for i in range(n):
        for j in range(m):
            if matrix[i][j] == target:
                return i,j
    return -1


matrix = [[1, 2, 3, 4], [5, 6, 7, 8], [9, 10, 11, 12]]
row,col = searchMatrixBruteForce(matrix, 8)
print("row:",row,"col:",col)