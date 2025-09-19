def stock_buy_and_sell(list):
    # my try
    # n = len(list)
    # if not list:
    #     return
    # min_val = min(list)
    # for i in range(n):
    #     if list[i] == min_val:
    #         min_idx = i
    # if min_idx == n - 1:
    #     return 0
    # if min_idx < n:
    #     max_val = 0
    #     for j in range(min_idx+1,n):
    #         max_val = max(max_val,list[j]-list[min_idx])
    # return max_val
    # optimal approach
    min_price = float("inf")
    max_profit = 0
    for p in list:
        if p < min_price:
            min_price = p
        else:
            max_profit = max(max_profit,p-min_price)
    return max_profit
prices = [7,1,5,3,6,4]
print(stock_buy_and_sell(prices))