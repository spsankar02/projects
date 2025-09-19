def rotate_2d_array(matrix):
    n = len(matrix)
    for i in range(n):
        for j in range(i+1,n):
            matrix[i][j],matrix[j][i] = matrix[j][i],matrix[i][j]
    for row in matrix:
        # slicing -> row[:] = row[::-1]
        # in-built -> row.reverse()
        left, right = 0, len(row) - 1
        while left <= right:
            row[left],row[right] = row[right],row[left]
            left += 1
            right -= 1
    return matrix
matrix = [
    [5,1,9,11],[2,4,8,10],[13,3,6,7],[15,14,12,16]
]
rotated = rotate_2d_array(matrix)
for row in rotated:
    print(row)